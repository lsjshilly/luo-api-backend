package com.lsj.luoapi.service.audit;

import com.lsj.luoapi.model.common.BaseResponse;

public interface AuditLogger {

    void auditTry();


    void authenticateFailed(BaseResponse result);


    void auditFinished(Object result);


    void auditFailed(Throwable e);

}
