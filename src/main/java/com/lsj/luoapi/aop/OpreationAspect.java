package com.lsj.luoapi.aop;


import com.lsj.luoapi.model.constant.HttpHeader;
import com.lsj.luoapi.model.constant.SystemConstants;
import com.lsj.luoapi.model.domain.Operation;
import com.lsj.luoapi.model.domain.RequestContext;
import com.lsj.luoapi.model.dto.user.UserDTO;
import com.lsj.luoapi.service.audit.AuditAdapter;
import com.lsj.luoapi.service.user.UserService;
import com.lsj.luoapi.utils.UserHodler;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.lsj.luoapi.model.constant.RedisConstants.LOGIN_USER_TOKEN_PREFIX;
import static com.lsj.luoapi.model.constant.RedisConstants.LOGIN_USER_TOKEN_TTL;

@Aspect
@Component
@Slf4j
public class OpreationAspect {


    @Resource
    private UserService userService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private AuditAdapter auditAdapter;

    private final Map<String, Operation> operations;

    public OpreationAspect() {
        this.operations = new HashMap<>();
    }

    @Pointcut("@annotation(Operator)")
    void pointcut(){}


    @Around(value = "pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) {

        Operation operation = getOperation(joinPoint);

        ServletRequestAttributes attributes = Objects.requireNonNull((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
        HttpServletRequest request = attributes.getRequest();

        RequestContext requestContext = RequestContext.build(request);
        requestContext.setOperation(operation);
        attributes.setAttribute(SystemConstants.DEFAULT_REQUST_ATTRIBUTES, requestContext, RequestAttributes.SCOPE_REQUEST);

        log.info("{} {} {}",request.getMethod(), request.getRequestURI(), operation.getMethodSignature());
        initLoginUser(requestContext);
        OpreationExecutor executor = new OpreationExecutor(requestContext, auditAdapter, joinPoint);
        try {
            return executor.execute();
        }finally {
            UserHodler.remove();
        }

    }

    Operation getOperation(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String key = signature.toLongString();

        Operation operation;
        synchronized (operations) {
            operation =  operations.get(key);
            if (operation != null) {
                return operation;
            }
        }

        Method method = signature.getMethod();
        Operator annotation = Objects.requireNonNull(method.getAnnotation(Operator.class), "Operation cant not be null");

        operation = new Operation(key, annotation.value());
        operation.setAuditEnabled(annotation.auditEnabled());

        AuthChecker authChecker = method.getAnnotation(AuthChecker.class);
        List<String> anyRoles = new ArrayList<>();
        List<String> mustRoles = new ArrayList<>();
        if (authChecker != null) {
             anyRoles = Arrays.stream(authChecker.anyRoles()).filter(StringUtils::isNotBlank).toList();
             mustRoles = Arrays.stream(authChecker.mustRoles()).filter(StringUtils::isNotBlank).toList();
        }
        operation.setAnyRoles(anyRoles);
        operation.setMustRoles(mustRoles);
        synchronized (operations) {
            operations.put(key, operation);
        }
        return operation;
    }

    protected void initLoginUser(RequestContext context) {
        String bearertoken = context.getRequest().getHeader(HttpHeader.AUTHORIZATION_HEADER);
        if (StringUtils.isBlank(bearertoken)) {
            log.warn("bearertoken is empty");
            return;
        }
        String[] tokens = StringUtils.split(bearertoken, " ");

        if (tokens.length != 2) {
            log.warn("invalid token format:{}", bearertoken);
            return;
        }

        String id = stringRedisTemplate.opsForValue().get(LOGIN_USER_TOKEN_PREFIX + tokens[1]);

        if (StringUtils.isBlank(id)) {
            log.warn("user login state is null");
            return;
        }
        try {
            UserDTO loginUser = userService.getLoginUser(Long.parseLong(id));
            if (loginUser == null) {
                log.warn("user is not exist");
                return;
            }
            stringRedisTemplate.expire(LOGIN_USER_TOKEN_PREFIX + tokens[1], LOGIN_USER_TOKEN_TTL, TimeUnit.MINUTES);
            context.setUserDTO(loginUser);
            UserHodler.save(loginUser);
        }catch (Exception e) {
            log.error("database error", e);
        }


    }


}
