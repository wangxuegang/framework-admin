package com.wangxuegang.service.log.impl;

import com.wangxuegang.dao.SysLogDao;
import com.wangxuegang.model.SysLog;
import com.wangxuegang.service.log.SysLogService;
import com.github.pagehelper.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 *  请求日志
 */
@Service
public class SysLogServiceImpl implements SysLogService {

    @Autowired
    private SysLogDao sysLogDao;

	@Override
	public void addSysLog(SysLog sysLog) {
		sysLogDao.insert(sysLog);
	}

	@Override
	public void deleteSysLogById(Integer id) {
		sysLogDao.deleteByPrimaryKey(id);
	}

	@Override
	public PageInfo<SysLog> getSysLogs(int pageNum, int pageSize) {

		return null;
	}
    
}
