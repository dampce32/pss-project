package org.linys.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.linys.dao.PrefixDAO;
import org.linys.model.Prefix;
import org.linys.service.PrefixService;
import org.linys.util.JSONUtil;
import org.linys.vo.ServiceResult;
import org.springframework.stereotype.Service;
/**
 * @Description:编号前缀Service实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-1-21
 * @author lys
 * @vesion 1.0
 */
@Service
public class PrefixServiceImpl extends BaseServiceImpl<Prefix, String>
		implements PrefixService {
	@Resource
	private PrefixDAO prefixDAO;
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.PrefixService#save(org.linys.model.Prefix)
	 */
	@Override
	public ServiceResult save(Prefix model) {
		ServiceResult result = new ServiceResult(false);
		if(model==null){
			result.setMessage("请填写编号前缀信息");
			return result;
		}
		if(StringUtils.isEmpty(model.getPrefixName())){
			result.setMessage("请填写编号前缀名称");
			return result;
		}
		if(StringUtils.isEmpty(model.getPrefixCode())){
			result.setMessage("请填写编号前缀编号");
			return result;
		}
		
		if(StringUtils.isEmpty(model.getPrefixId())){//新增
			Prefix oldModel = prefixDAO.load("prefixCode", model.getPrefixCode());
			if(oldModel!=null){
				result.setMessage("该编号前缀已存在");
				return result;
			}
			prefixDAO.save(model);
		}else{
			Prefix oldModel = prefixDAO.load(model.getPrefixId());
			if(oldModel==null){
				result.setMessage("该编号前缀已不存在");
				return result;
			}else if(!oldModel.getPrefixCode().equals(model.getPrefixCode())){
				Prefix oldPrefix = prefixDAO.load("prefixCode", model.getPrefixCode());
				if(oldPrefix!=null){
					result.setMessage("该编号前缀已存在");
					return result;
				}
			}
			oldModel.setPrefix(model.getPrefix());
			prefixDAO.update(oldModel);
		}
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.PrefixService#delete(org.linys.model.Prefix)
	 */
	@Override
	public ServiceResult delete(Prefix model) {
		ServiceResult result = new ServiceResult(false);
		if(model==null||StringUtils.isEmpty(model.getPrefixId())){
			result.setMessage("请选择要删除的编号前缀");
			return result;
		}
		Prefix oldModel = prefixDAO.load(model.getPrefixId());
		if(oldModel==null){
			result.setMessage("该编号前缀已不存在");
			return result;
		}else{
			prefixDAO.delete(oldModel);
		}
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.PrefixService#query(org.linys.model.Prefix)
	 */
	@Override
	public ServiceResult query(Prefix model) {
		ServiceResult result = new ServiceResult(false);
		
		List<Prefix> list = prefixDAO.query(model);
		
		String[] properties = {"prefixId","prefixName","prefixCode","prefix"};
		String data = JSONUtil.toJson(list,properties);
		result.addData("datagridData", data);
		
		result.setIsSuccess(true);
		return result;
	}

}
