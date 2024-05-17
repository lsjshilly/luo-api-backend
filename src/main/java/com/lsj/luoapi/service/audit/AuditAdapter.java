package com.lsj.luoapi.service.audit;

import com.lsj.luoapi.model.domain.OperationLog;

public interface AuditAdapter {



    void submit(OperationLog operationLog);
}
