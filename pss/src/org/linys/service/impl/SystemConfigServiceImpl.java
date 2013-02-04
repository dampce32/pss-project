package org.linys.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.linys.dao.SystemConfigDAO;
import org.linys.model.SystemConfig;
import org.linys.service.SystemConfigService;
import org.linys.util.JSONUtil;
import org.linys.vo.ServiceResult;
import org.springframework.stereotype.Service;
/**
 * @Description:系统配置Service实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-2-4
 * @author lys
 * @vesion 1.0
 */
@Service
public class SystemConfigServiceImpl extends
		BaseServiceImpl<SystemConfig, String> implements SystemConfigService {
	@Resource
	private SystemConfigDAO systemConfigDAO;
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.SystemConfigService#save(org.linys.model.SystemConfig)
	 */
	@Override
	public ServiceResult save(SystemConfig model) {
		ServiceResult result = new ServiceResult(false);
		if(model==null||StringUtils.isEmpty(model.getSystemConfigId())){
			result.setMessage("请选择系统配置");
			return result;
		}
		SystemConfig oldModel = systemConfigDAO.load(model.getSystemConfigId());
		oldModel.setCompanyName(model.getCompanyName());
		oldModel.setCompanyPhone(model.getCompanyPhone());
		oldModel.setCompanyFax(model.getCompanyFax());
		oldModel.setCompanyAddr(model.getCompanyAddr());
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.SystemConfigService#init(org.linys.model.SystemConfig)
	 */
	@Override
	public ServiceResult init() {
		ServiceResult result = new ServiceResult(false);
		SystemConfig oldModel = systemConfigDAO.init();
		if(oldModel==null){
			result.setMessage("系统还没有系统配置信息，请联系管理员");
			return result;
		}
		String[] properties = {"systemConfigId","companyName","companyPhone","companyFax","companyAddr"};
		String systemConfigData = JSONUtil.toJson(oldModel, properties);
		result.addData("systemConfigData", systemConfigData);
		result.setIsSuccess(true);
		return result;
	}

}
