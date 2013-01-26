package org.linys.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.linys.dao.ExpenseDAO;
import org.linys.model.Bank;
import org.linys.model.Expense;
import org.linys.service.ExpenseService;
import org.linys.util.JSONUtil;
import org.linys.util.StringUtil;
import org.linys.vo.ServiceResult;
import org.springframework.stereotype.Service;
/**
 * @Description:费用支出Service实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-1-26
 * @author lys
 * @vesion 1.0
 */
@Service
public class ExpenseServiceImpl extends BaseServiceImpl<Expense, String>
		implements ExpenseService {
	
	@Resource
	private ExpenseDAO expenseDAO;
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.ExpenseService#save(org.linys.model.Expense)
	 */
	@Override
	public ServiceResult save(Expense model) {
		ServiceResult result = new ServiceResult(false);
		if(model==null){
			result.setMessage("请填写费用支出信息");
			return result;
		}
		if(model.getBank()!=null&&StringUtils.isEmpty(model.getBank().getBankId())){
			result.setMessage("请选择费用支出账号");
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
		if(StringUtils.isEmpty(model.getExpenseId())){//新增
			model.setStatus(0);
			expenseDAO.save(model);
		}else{
			Expense oldModel = expenseDAO.load(model.getExpenseId());
			if(oldModel==null){
				result.setMessage("该费用支出已不存在");
				return result;
			}
			oldModel.setExpenseName(model.getExpenseName());
			oldModel.setExpenseDate(model.getExpenseDate());
			oldModel.setBank(model.getBank());
			oldModel.setEmployee(model.getEmployee());
			oldModel.setAmount(model.getAmount());
			oldModel.setNote(model.getNote());
		}
		result.addData("expenseId", model.getExpenseId());
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.ExpenseService#init(org.linys.model.Expense)
	 */
	@Override
	public ServiceResult init(Expense model) {
		ServiceResult result = new ServiceResult(false);
		if(model==null||StringUtils.isEmpty(model.getExpenseId())){
			result.setMessage("请选择费用支出");
			return result;
		}
		
		Expense expense = expenseDAO.init(model.getExpenseId());
		String[] propertiesPay = {"expenseId","expenseDate","expenseName","amount",
				"bank.bankId","employee.employeeId","note","status"};
		String expenseData = JSONUtil.toJson(expense,propertiesPay);
		result.addData("expenseData",expenseData);
		
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.ExpenseService#delete(org.linys.model.Expense)
	 */
	@Override
	public ServiceResult delete(Expense model) {
		ServiceResult result = new ServiceResult(false);
		if(model==null||StringUtils.isEmpty(model.getExpenseId())){
			result.setMessage("请选择要删除的费用支出");
			return result;
		}
		Expense oldModel = expenseDAO.load(model.getExpenseId());
		if(oldModel==null){
			result.setMessage("该费用支出已不存在");
			return result;
		}
		if(oldModel.getStatus()==1){
			result.setMessage("该费用支出已审核不能删除");
			return result;
		}
		expenseDAO.delete(oldModel);
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.ExpenseService#mulDelete(java.lang.String)
	 */
	@Override
	public ServiceResult mulDelete(String ids) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(ids)){
			result.setMessage("请选择要删除的费用支出");
			return result;
		}
		String[] idArray =StringUtil.split(ids);
		if(idArray.length==0){
			result.setMessage("请选择要删除的费用支出");
			return result;
		}
		boolean haveDel = false;
		for (String id : idArray) {
			Expense oldExpense = expenseDAO.load(id);
			if(oldExpense!=null&&oldExpense.getStatus().intValue()==0){
				expenseDAO.delete(oldExpense);
				haveDel = true;
			}
		}
		if(!haveDel){
			result.setMessage("没有可删除的费用支出");
			return result;
		}
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.ExpenseService#query(org.linys.model.Expense, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public ServiceResult query(Expense model, Integer page, Integer rows) {
		ServiceResult result = new ServiceResult(false);
		
		List<Expense> list = expenseDAO.query(model,page,rows);
		
		String[] properties = {"expenseId","expenseName","expenseDate","amount","status","bank.bankName","employee.employeeName","note"};
		String data = JSONUtil.toJson(list,properties);
		result.addData("datagridData", data);
		
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.ExpenseService#getTotalCount(org.linys.model.Expense)
	 */
	@Override
	public ServiceResult getTotalCount(Expense model) {
		ServiceResult result = new ServiceResult(false);
		Long data = expenseDAO.getTotalCount(model);
		result.addData("total", data);
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.ExpenseService#updateStatus(org.linys.model.Expense)
	 */
	@Override
	public ServiceResult updateStatus(Expense model) {
		ServiceResult result = new ServiceResult(false);
		if(model==null||StringUtils.isEmpty(model.getExpenseId())){
			result.setMessage("请选择要更新状态的费用支出");
			return result;
		}
		Expense oldModel = expenseDAO.load(model.getExpenseId());
		if(oldModel==null){
			result.setMessage("要更新状态的费用支出已不存在");
			return result;
		}
		/*
		 * 审核涉及到：
		 * 1.对应银行账户
		 */
		if(model.getStatus()==1){//从未审改为审核
			Bank oldBank = oldModel.getBank();
			oldBank.setAmount(oldBank.getAmount()-oldModel.getAmount());
		}else{
			Bank oldBank = oldModel.getBank();
			oldBank.setAmount(oldBank.getAmount()+oldModel.getAmount());
		}
		oldModel.setStatus(model.getStatus());
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.ExpenseService#mulUpdateStatus(java.lang.String, org.linys.model.Expense)
	 */
	@Override
	public ServiceResult mulUpdateStatus(String ids, Expense model) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(ids)){
			result.setMessage("请选择要更新状态的费用支出");
			return result;
		}
		String[] idArray =StringUtil.split(ids);
		if(idArray.length==0){
			result.setMessage("请选择要更新状态的费用支出");
			return result;
		}
		boolean haveUpdate = false;
		for (String id : idArray) {
			Expense oldModel = expenseDAO.load(id);
			if(oldModel!=null&&oldModel.getStatus().intValue()!=model.getStatus().intValue()){
				if(model.getStatus()==1){//从未审改为审核
					Bank oldBank = oldModel.getBank();
					oldBank.setAmount(oldBank.getAmount()-oldModel.getAmount());
				}else{
					Bank oldBank = oldModel.getBank();
					oldBank.setAmount(oldBank.getAmount()+oldModel.getAmount());
				}
				oldModel.setStatus(model.getStatus());
				haveUpdate = true;
			}
		}
		if(!haveUpdate){
			result.setMessage("没有可更新状态的费用支出");
			return result;
		}
		result.setIsSuccess(true);
		return result;
	}

}
