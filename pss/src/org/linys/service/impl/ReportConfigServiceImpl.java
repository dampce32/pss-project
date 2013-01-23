package org.linys.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.linys.dao.ReportConfigDAO;
import org.linys.model.ReportConfig;
import org.linys.service.ReportConfigService;
import org.linys.util.JSONUtil;
import org.linys.vo.ServiceResult;
import org.springframework.stereotype.Service;
/**
 * @Description:报表配置Service实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-1-23
 * @author lys
 * @vesion 1.0
 */
@Service
public class ReportConfigServiceImpl extends
		BaseServiceImpl<ReportConfig, String> implements ReportConfigService {
	@Resource
	private ReportConfigDAO reportConfigDAO;
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.ReportConfigService#save(org.linys.model.ReportConfig)
	 */
	@Override
	public ServiceResult save(ReportConfig model) {
		ServiceResult result = new ServiceResult(false);
		if(model==null){
			result.setMessage("请填写报表配置信息");
			return result;
		}
		if(StringUtils.isEmpty(model.getReportCode())){
			result.setMessage("请填写报表编号");
			return result;
		}
		if(StringUtils.isEmpty(model.getReportName())){
			result.setMessage("请填写报表名称");
			return result;
		}
		if(StringUtils.isEmpty(model.getReportDetailSql())){
			result.setMessage("请填写报表明细网格Sql");
			return result;
		}
		
		if(StringUtils.isEmpty(model.getReportConfigId())){//新增
			ReportConfig oldModel = reportConfigDAO.load("reportCode", model.getReportCode());
			if(oldModel!=null){
				result.setMessage("该报表编号已存在");
				return result;
			}
			reportConfigDAO.save(model);
		}else{
			ReportConfig oldModel = reportConfigDAO.load(model.getReportConfigId());
			if(oldModel==null){
				result.setMessage("该报表配置已不存在");
				return result;
			}else if(!oldModel.getReportCode().equals(model.getReportCode())){
				ReportConfig oldReportConfig = reportConfigDAO.load("reportCode", model.getReportCode());
				if(oldReportConfig!=null){
					result.setMessage("该报表编号已存在");
					return result;
				}
			}
			oldModel.setReportCode(model.getReportCode());
			oldModel.setReportName(model.getReportName());
			oldModel.setReportDetailSql(model.getReportDetailSql());
			oldModel.setReportParamsSql(model.getReportParamsSql());
			
			reportConfigDAO.update(oldModel);
		}
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.ReportConfigService#delete(org.linys.model.ReportConfig)
	 */
	@Override
	public ServiceResult delete(ReportConfig model) {
		ServiceResult result = new ServiceResult(false);
		if(model==null||StringUtils.isEmpty(model.getReportConfigId())){
			result.setMessage("请选择要删除的报表配置");
			return result;
		}
		ReportConfig oldModel = reportConfigDAO.load(model.getReportConfigId());
		if(oldModel==null){
			result.setMessage("该报表配置已不存在");
			return result;
		}else{
			reportConfigDAO.delete(oldModel);
		}
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.ReportConfigService#query(org.linys.model.ReportConfig, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public ServiceResult query(ReportConfig model, Integer page, Integer rows) {
		ServiceResult result = new ServiceResult(false);
		
		List<ReportConfig> list = reportConfigDAO.query(model,page,rows);
		
		String[] properties = {"reportConfigId","reportCode","reportName","reportDetailSql","reportParamsSql"};
		String data = JSONUtil.toJson(list,properties);
		result.addData("datagridData", data);
		
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.ReportConfigService#getTotalCount(org.linys.model.ReportConfig)
	 */
	@Override
	public ServiceResult getTotalCount(ReportConfig model) {
		ServiceResult result = new ServiceResult(false);
		Long data = reportConfigDAO.getTotalCount(model);
		result.addData("total", data);
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.ReportConfigService#getByReportCode(java.lang.String)
	 */
	@Override
	public ReportConfig getByReportCode(String reportCode) {
		return reportConfigDAO.load("reportCode", reportCode);
	}

}
