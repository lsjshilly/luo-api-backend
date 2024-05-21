package com.lsj.luoapi.service.api;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lsj.luoapi.model.common.*;
import com.lsj.luoapi.model.dto.api.ApiAddRequest;
import com.lsj.luoapi.model.dto.api.ApiQueryRequest;
import com.lsj.luoapi.model.dto.api.ApiUpdateRequest;
import com.lsj.luoapi.model.entity.ApiInfo;
import com.lsj.luoapi.model.vo.ApiInfoVo;

/**
* @author liushijie
* @description 针对表【tb_api_info】的数据库操作Service
* @createDate 2024-05-17 22:51:44
*/
public interface ApiInfoService extends IService<ApiInfo> {

    AddOrUpdateResult addApiInfo(ApiAddRequest apiAddRequest);

    AddOrUpdateResult updateApiInfo(ApiUpdateRequest apiUpdateRequest);

    ListResult<ApiInfoVo> getApiInfoPage(ApiQueryRequest apiQueryRequest, PageRequest pageRequest);

    boolean deleteApiInfos(DeleteRequest<Long> deleteRequest);

    ApiInfoVo getApiInfoById(IdRequest<Long> idRequest);
}
