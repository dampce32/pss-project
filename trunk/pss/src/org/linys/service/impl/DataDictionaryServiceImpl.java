package org.linys.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.linys.dao.DataDictionaryDAO;
import org.linys.model.DataDictionary;
import org.linys.service.DataDictionaryService;
import org.linys.util.JSONUtil;
import org.linys.vo.ServiceResult;
import org.springframework.stereotype.Service;
@Service
public class DataDictionaryServiceImpl extends BaseServiceImpl<DataDictionary, String> implements DataDictionaryService {
	@Resource
	DataDictionaryDAO dataDictionaryDAO;
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.DataDictionaryService#add(org.linys.model.DataDictionary)
	 */
	public ServiceResult add(DataDictionary model) {
		ServiceResult result = new ServiceResult(false);
		if(model==null){
			result.setMessage("请填写数据字典信息");
			return result;
		}
		if(StringUtils.isEmpty(model.getDataDictionaryKind())){
			result.setMessage("请填写数据字典类型");
			return result;
		}
		if(StringUtils.isEmpty(model.getDataDictionaryName())){
			result.setMessage("请填写数据字典名称");
			return result;
		}
		dataDictionaryDAO.save(model);
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.DataDictionaryService#update(org.linys.model.DataDictionary)
	 */
	public ServiceResult update(DataDictionary model) {
		ServiceResult result = new ServiceResult(false);
		if(model==null||StringUtils.isEmpty(model.getDataDictionaryId())){
			result.setMessage("请选择数据字典信息");
			return result;
		}
		if(StringUtils.isEmpty(model.getDataDictionaryName())){
			result.setMessage("请填写数据字典名称");
			return result;
		}
		DataDictionary oldModel = dataDictionaryDAO.load(model.getDataDictionaryId());
		if(oldModel==null){
			result.setMessage("该数据字典信息已不存在");
			return result;
		}else{
			oldModel.setDataDictionaryName(model.getDataDictionaryName());
			dataDictionaryDAO.update(oldModel);
		}
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.DataDictionaryService#delete(org.linys.model.DataDictionary)
	 */
	public ServiceResult delete(DataDictionary model) {
		ServiceResult result = new ServiceResult(false);
		DataDictionary oldModel = dataDictionaryDAO.load(model.getDataDictionaryId());
		if(oldModel==null){
			result.setMessage("该数据字典信息已不存在");
			return result;
		}else{
			dataDictionaryDAO.delete(oldModel);
		}
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.DataDictionaryService#query(org.linys.model.DataDictionary, Integer, Integer)
	 */
	public ServiceResult query(DataDictionary model, Integer page, Integer rows) {
		ServiceResult result = new ServiceResult(false);
		
		if(model==null||StringUtils.isEmpty(model.getDataDictionaryKind())){
			result.setMessage("请填写数据字典类型");
			return result;
		}
		List<DataDictionary> list = dataDictionaryDAO.query(model,page,rows);
		
		List<String> propertyList = new ArrayList<String>();
		propertyList.add("dataDictionaryId");
		propertyList.add("dataDictionaryName");
		
		String data = JSONUtil.toJson(list,propertyList);
		result.addData("datagridData", data);
		
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.DataDictionaryService#getTotalCount(org.linys.model.DataDictionary)
	 */
	public ServiceResult getTotalCount(DataDictionary model) {
		ServiceResult result = new ServiceResult(false);
		Long data = dataDictionaryDAO.getTotalCount(model);
		result.addData("total", data);
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.DataDictionaryService#queryByDictionaryKind(java.lang.String)
	 */
	public String queryByDictionaryKind(String kind) {
		List<DataDictionary> list = dataDictionaryDAO.query("dataDictionaryKind", kind);
		
		List<String> propertyList = new ArrayList<String>();
		propertyList.add("dataDictionaryId");
		propertyList.add("dataDictionaryName");
		
		return JSONUtil.toJsonWithoutRows(list,propertyList);
	}

}
