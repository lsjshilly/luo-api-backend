package com.lsj.luoapi.service.api.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsj.luoapi.model.common.*;
import com.lsj.luoapi.model.dto.api.ApiAddRequest;
import com.lsj.luoapi.model.dto.api.ApiQueryRequest;
import com.lsj.luoapi.model.dto.api.ApiUpdateRequest;
import com.lsj.luoapi.model.dto.user.UserDTO;
import com.lsj.luoapi.model.entity.ApiInfo;
import com.lsj.luoapi.model.entity.User;
import com.lsj.luoapi.model.vo.ApiInfoVo;
import com.lsj.luoapi.service.api.ApiInfoService;
import com.lsj.luoapi.mapper.ApiInfoMapper;
import com.lsj.luoapi.service.user.UserService;
import com.lsj.luoapi.utils.FiledMapping;
import com.lsj.luoapi.utils.UserHodler;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author liushijie
 * @description 针对表【tb_api_info】的数据库操作Service实现
 * @createDate 2024-05-17 22:51:44
 */
@Service
public class ApiInfoServiceImpl extends ServiceImpl<ApiInfoMapper, ApiInfo>
        implements ApiInfoService {

    @Resource
    private UserService userService;


    @Override
    public AddOrUpdateResult addApiInfo(ApiAddRequest apiAddRequest) {
        if (apiAddRequest == null) {
            throw new BusinessExecption(ErrCode.VALIDATION_ERROR, "参数异常");
        }

        // 参数及爱哦眼
        ApiInfo apiInfo = BeanUtil.copyProperties(apiAddRequest, ApiInfo.class);
        validate(apiInfo);
        // 获取当前用户Id
        UserDTO userDTO = UserHodler.get();
        if (userDTO != null) {
            apiInfo.setUserId(userDTO.getId());
        }
        // 保存
        boolean saved = save(apiInfo);
        if (!saved) {
            throw new BusinessExecption(ErrCode.ADD_API_ERROR, "数据库异常");
        }
        return new AddOrUpdateResult(apiInfo.getId());

    }

    @Override
    public AddOrUpdateResult updateApiInfo(ApiUpdateRequest apiUpdateRequest) {
        if (apiUpdateRequest == null) {
            throw new BusinessExecption(ErrCode.VALIDATION_ERROR, "参数异常");
        }

        // 参数及爱哦眼
        ApiInfo apiInfo = BeanUtil.copyProperties(apiUpdateRequest, ApiInfo.class);
        validate(apiInfo);
        if (apiInfo.getId() == null) {
            throw new BusinessExecption(ErrCode.VALIDATION_ERROR, "参数异常id");
        }

        ApiInfo info = getById(apiInfo.getId());
        if (info == null) {
            throw new BusinessExecption(ErrCode.update_API_ERROR, "Api信息不存在");
        }

        // 保存
        boolean saved = updateById(apiInfo);
        if (!saved) {
            throw new BusinessExecption(ErrCode.update_API_ERROR, "数据库异常");
        }
        return new AddOrUpdateResult(apiInfo.getId());
    }

    @Override
    public ListResult<ApiInfoVo> getApiInfoPage(ApiQueryRequest apiQueryRequest, PageRequest pageRequest) {
        Page<ApiInfo> page = new Page<>(pageRequest.getPageNum(), pageRequest.getPageSize());
        List<String[]> sortFileds = Optional.ofNullable(pageRequest.getSortFields()).orElse(new ArrayList<>()).stream()
                .map(key -> StringUtils.split(key, "="))
                .filter(key -> {
                    if (key.length != 2) {
                        return false;
                    }
                    if (StringUtils.isBlank(key[0])) {
                        return false;
                    }
                    return Objects.equals(key[1], "ascend") || Objects.equals(key[1], "descend");
                }).toList();
        QueryChainWrapper<ApiInfo> wrapper = query()
                .eq(apiQueryRequest.getId() != null, "id", apiQueryRequest.getId())
                .like(StringUtils.isNotBlank(apiQueryRequest.getName()), "name", apiQueryRequest.getName())
                .eq(StringUtils.isNotBlank(apiQueryRequest.getMethod()), "method", apiQueryRequest.getMethod())
                .like(StringUtils.isNotBlank(apiQueryRequest.getUrl()), "url", apiQueryRequest.getUrl())
                .eq(apiQueryRequest.getStatus() != null, "status", apiQueryRequest.getStatus())
                .like(StringUtils.isNotBlank(apiQueryRequest.getDescription()), "description", apiQueryRequest.getDescription());

        for (String[] sortFiled : sortFileds) {
            if (Objects.equals(sortFiled[1] , "descend")) {
                wrapper.orderByAsc(FiledMapping.getDatabaseFiled(sortFiled[0]));
            }
            if (Objects.equals(sortFiled[1] , "ascend")) {
                wrapper.orderByAsc(FiledMapping.getDatabaseFiled(sortFiled[0]));
            }
        }

        Page<ApiInfo> apiInfoPage = wrapper.page(page);


         // 查询用户信息
        List<Long> userIdS = apiInfoPage.getRecords().stream().map(ApiInfo::getUserId).filter(Objects::nonNull).toList();
        List<User> users = new ArrayList<>();
        if (!userIdS.isEmpty()) {
            users = userService.listByIds(userIdS);
        }
        // 用户ID信息映射
        Map<Long, UserDTO> userDTOMap = users.stream().
                map(key -> BeanUtil.copyProperties(key, UserDTO.class))
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(UserDTO::getId, Function.identity()));

        List<ApiInfoVo> apiInfoVos = apiInfoPage.getRecords().stream()
                .map(key -> {
                    ApiInfoVo apiInfoVo = BeanUtil.copyProperties(key, ApiInfoVo.class);
                    if (apiInfoVo != null) {
                        UserDTO userDTO = userDTOMap.getOrDefault(key.getUserId(), null);
                        apiInfoVo.setUserInfo(userDTO);
                    }
                    return apiInfoVo;
                }).toList();


        ListResult<ApiInfoVo> result = new ListResult<>();
        result.setItems(apiInfoVos);
        result.setTotal(apiInfoPage.getTotal());
        return result;
    }

    @Override
    @Transactional
    public boolean deleteApiInfos(DeleteRequest<Long> deleteRequest) {
        if (deleteRequest.getId() == null && (Objects.isNull(deleteRequest.getIds()) || deleteRequest.getIds().isEmpty())) {
            throw new BusinessExecption(ErrCode.VALIDATION_ERROR, "参数异常");
        }

        List<Long> deleteIds = new ArrayList<>();

        if (deleteRequest.getId()!= null) {
            deleteIds.add(deleteRequest.getId());
        }
        if (deleteRequest.getId()== null&& deleteRequest.getIds()!= null) {
            deleteIds.addAll(deleteRequest.getIds());
        }

        if (!deleteIds.isEmpty()) {
            boolean removed = this.removeBatchByIds(deleteIds);
            if (!removed) {
                throw new BusinessExecption(ErrCode.DATABASE_ERROR, "数据库异常");
            }
        }

        return true;
    }

    @Override
    public ApiInfoVo getApiInfoById( IdRequest<Long> idRequest) {
        if (idRequest.getId() == null) {
            throw new BusinessExecption(ErrCode.VALIDATION_ERROR, "参数异常");
        }
        ApiInfo info = this.getById(idRequest.getId());
        if (info == null) {
            throw new BusinessExecption(ErrCode.ERR_PAGE_NOT_FOUND, "不存在");
        }
        return BeanUtil.copyProperties(info, ApiInfoVo.class);

    }


    private void validate(ApiInfo apiInfo) {

        if (StringUtils.isBlank(apiInfo.getName()) || apiInfo.getName().length() > 32) {
            throw new BusinessExecption(ErrCode.VALIDATION_ERROR, "api名称不能为空且不能超过32位");
        }
        if (StringUtils.isBlank(apiInfo.getUrl()) || apiInfo.getUrl().length() > 64) {
            throw new BusinessExecption(ErrCode.VALIDATION_ERROR, "url不能为空且不能超过64位");
        }

        if (StringUtils.isBlank(apiInfo.getDescription()) && apiInfo.getDescription().length() > 512) {
            throw new BusinessExecption(ErrCode.VALIDATION_ERROR, "描述信息过长");
        }

    }


}




