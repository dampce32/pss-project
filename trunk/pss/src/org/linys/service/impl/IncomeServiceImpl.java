package org.linys.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.linys.dao.IncomeDAO;
import org.linys.model.Bank;
import org.linys.model.Income;
import org.linys.service.IncomeService;
import org.linys.util.JSONUtil;
import org.linys.util.StringUtil;
import org.linys.vo.ServiceResult;
import org.springframework.stereotype.Service;
/**
 * @Description:收入Service实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-1-25
 * @author lys
 * @vesion 1.0
 */
@Service
public class IncomeServiceImpl extends BaseServiceImpl<Income, String>
		implements IncomeService {
	@Resource
	private IncomeDAO incomeDAO;
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.IncomeService#save(org.linys.model.Income)
	 */
	@Override
	public ServiceResult save(Income model) {
		ServiceResult result = new ServiceResult(false);
		if(model==null){
			result.setMessage("请填写收入信息");
			return result;
		}
		if(model.getBank()!=null&&StringUtils.isEmpty(model.getBank().getBankId())){
			result.setMessage("请选择收入账号");
			return result;
		}
		if(model.getAmount()==null){
			result.setMessage("金额不能为空");
			return result;
		}
		if(model.getAmount()==0){
			result.setMessage("金额不能为0");
			return result;
		}
		if(StringUtils.isEmpty(model.getIncomeId())){//新增
			model.setStatus(0);
			incomeDAO.save(model);
		}else{
			Income oldModel = incomeDAO.load(model.getIncomeId());
			if(oldModel==null){
				result.setMessage("该收入已不存在");
				return result;
			}
			oldModel.setIncomeName(model.getIncomeName());
			oldModel.setIncomeDate(model.getIncomeDate());
			oldModel.setBank(model.getBank());
			oldModel.setEmployee(model.getEmployee());
			oldModel.setAmount(model.getAmount());
			oldModel.setNote(model.getNote());
		}
		result.addData("incomeId", model.getIncomeId());
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.IncomeService#delete(org.linys.model.Income)
	 */
	@Override
	public ServiceResult delete(Income model) {
		ServiceResult result = new ServiceResult(false);
		if(model==null||StringUtils.isEmpty(model.getIncomeId())){
			result.setMessage("请选择要删除的收入");
			return result;
		}
		Income oldModel = incomeDAO.load(model.getIncomeId());
		if(oldModel==null){
			result.setMessage("该收入已不存在");
			return result;
		}
		if(oldModel.getStatus()==1){
			result.setMessage("该收入已审核不能删除");
			return result;
		}
		incomeDAO.delete(oldModel);
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.IncomeService#mulDelete(java.lang.String)
	 */
	@Override
	public ServiceResult mulDelete(String ids) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(ids)){
			result.setMessage("请选择要删除的收入");
			return result;
		}
		String[] idArray =StringUtil.split(ids);
		if(idArray.length==0){
			result.setMessage("请选择要删除的收入");
			return result;
		}
		boolean haveDel = false;
		for (String id : idArray) {
			Income oldIncome = incomeDAO.load(id);
			if(oldIncome!=null&&oldIncome.getStatus().intValue()==0){
				incomeDAO.delete(oldIncome);
				haveDel = true;
			}
		}
		if(!haveDel){
			result.setMessage("没有可删除的付款单");
			return result;
		}
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.IncomeService#query(org.linys.model.Income, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public ServiceResult query(Income model, Integer page, Integer rows) {
		ServiceResult result = new ServiceResult(false);
		
		List<Income> list = incomeDAO.query(model,page,rows);
		
		String[] properties = {"incomeId","incomeName","incomeDate","amount","status","bank.bankName","employee.employeeName","note"};
		String data = JSONUtil.toJson(list,properties);
		result.addData("datagridData", data);
		
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.IncomeService#getTotalCount(org.linys.model.Income)
	 */
	@Override
	public ServiceResult getTotalCount(Income model) {
		ServiceResult result = new ServiceResult(false);
		Long data = incomeDAO.getTotalCount(model);
		result.addData("total", data);
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.IncomeService#updateStatus(org.linys.model.Income)
	 */
	@Override
	public ServiceResult updateStatus(Income model) {
		ServiceResult result = new ServiceResult(false);
		if(model==null||StringUtils.isEmpty(model.getIncomeId())){
			result.setMessage("请选择要更新状态的收入");
			return result;
		}
		Income oldModel = incomeDAO.load(model.getIncomeId());
		if(oldModel==null){
			result.setMessage("要更新状态的收入已不存在");
			return result;
		}
		/*
		 * 审核涉及到：
		 * 1.对应银行账户
		 */
		if(model.getStatus()==1){//从未审改为审核
			Bank oldBank = oldModel.getBank();
			oldBank.setAmount(oldBank.getAmount()+oldModel.getAmount());
		}else{
			Bank oldBank = oldModel.getBank();
			oldBank.setAmount(oldBank.getAmount()-oldModel.getAmount());
		}
		oldModel.setStatus(model.getStatus());
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.IncomeService#mulUpdateStatus(java.lang.String, org.linys.model.Income)
	 */
	@Override
	public ServiceResult mulUpdateStatus(String ids, Income model) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(ids)){
			result.setMessage("请选择要更新状态的收入");
			return result;
		}
		String[] idArray =StringUtil.split(ids);
		if(idArray.length==0){
			result.setMessage("请选择要更新状态的收入");
			return result;
		}
		boolean haveUpdate = false;
		for (String id : idArray) {
			Income oldModel = incomeDAO.load(id);
			if(oldModel!=null&&oldModel.getStatus().intValue()!=model.getStatus().intValue()){
				if(model.getStatus()==1){//从未审改为审核
					Bank oldBank = oldModel.getBank();
					oldBank.setAmount(oldBank.getAmount()+oldModel.getAmount());
				}else{
					Bank oldBank = oldModel.getBank();
					oldBank.setAmount(oldBank.getAmount()-oldModel.getAmount());
				}
				oldModel.setStatus(model.getStatus());
				haveUpdate = true;
			}
		}
		if(!haveUpdate){
			result.setMessage("没有可更新状态的收入");
			return result;
		}
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.IncomeService#init(org.linys.model.Income)
	 */
	@Override
	public ServiceResult init(Income model) {
		ServiceResult result = new ServiceResult(false);
		if(model==null||StringUtils.isEmpty(model.getIncomeId())){
			result.setMessage("请选择收入");
			return result;
		}
		
		Income income = incomeDAO.init(model.getIncomeId());
		String[] propertiesPay = {"incomeId","incomeDate","incomeName","amount",
				"bank.bankId","employee.employeeId","note","status"};
		String incomeData = JSONUtil.toJson(income,propertiesPay);
		result.addData("incomeData",incomeData);
		
		result.setIsSuccess(true);
		return result;
	}

}
