package com.wangxuegang.service.log;

import com.wangxuegang.model.SysLog;
import com.github.pagehelper.PageInfo;

public interface SysLogService {

    void addSysLog(SysLog sysLog);

    void deleteSysLogById(Integer id);

    PageInfo<SysLog> getSysLogs(int pageNum, int pageSize);
}
