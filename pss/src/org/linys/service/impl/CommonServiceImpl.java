package org.linys.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.linys.dao.CommonDAO;
import org.linys.service.CommonService;
import org.linys.vo.ServiceResult;
import org.springframework.stereotype.Service;
@Service
public class CommonServiceImpl implements CommonService {
	@Resource
	private CommonDAO commonDAO;
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.CommonService#getCode(java.lang.String, java.lang.String)
	 */
	@Override
	public ServiceResult getCode(String type, String code) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(type)){
			result.setMessage("请填写编号类型");
			return result;
		}
		result.addData("code", commonDAO.getCode(type,code));
		result.setIsSuccess(true);
		return result;
	}

}
