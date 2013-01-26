package org.linys.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.linys.dao.BankStatementsDAO;
import org.linys.model.Bank;
import org.linys.model.BankStatements;
import org.linys.service.BankStatementsService;
import org.linys.util.JSONUtil;
import org.linys.util.StringUtil;
import org.linys.vo.ServiceResult;
import org.springframework.stereotype.Service;
/**
 * @Description:银行账单Service实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-1-26
 * @author lys
 * @vesion 1.0
 */
@Service
public class BankStatementsServiceImpl extends BaseServiceImpl<BankStatements, String> implements
		BankStatementsService {
	@Resource
	private BankStatementsDAO bankStatementsDAO;
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.BankStatementsService#save(org.linys.model.BankStatements)
	 */
	@Override
	public ServiceResult save(BankStatements model) {
		ServiceResult result = new ServiceResult(false);
		if(model==null){
			result.setMessage("请填写银行存取款信息");
			return result;
		}
		if(StringUtils.isEmpty(model.getBankStatementsKind())){
			result.setMessage("请选择交易类型");
			return result;
		}
		if(model.getBank()!=null&&StringUtils.isEmpty(model.getBank().getBankId())){
			result.setMessage("请选择银行账号");
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
		if(StringUtils.isEmpty(model.getBankStatementsId())){//新增
			model.setStatus(0);
			bankStatementsDAO.save(model);
		}else{
			BankStatements oldModel = bankStatementsDAO.load(model.getBankStatementsId());
			if(oldModel==null){
				result.setMessage("该银行存取款记录已不存在");
				return result;
			}
			oldModel.setBankStatementsKind(model.getBankStatementsKind());
			oldModel.setBankStatementsDate(model.getBankStatementsDate());
			oldModel.setBank(model.getBank());
			oldModel.setEmployee(model.getEmployee());
			oldModel.setAmount(model.getAmount());
		}
		result.addData("bankStatementsId", model.getBankStatementsId());
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.BankStatementsService#init(org.linys.model.BankStatements)
	 */
	@Override
	public ServiceResult init(BankStatements model) {
		ServiceResult result = new ServiceResult(false);
		if(model==null||StringUtils.isEmpty(model.getBankStatementsId())){
			result.setMessage("请选择银行存取款");
			return result;
		}
		
		BankStatements bankStatements = bankStatementsDAO.init(model.getBankStatementsId());
		String[] propertiesPay = {"bankStatementsId","bankStatementsDate","amount","bankStatementsKind",
				"bank.bankId","employee.employeeId","status"};
		String bankStatementsData = JSONUtil.toJson(bankStatements,propertiesPay);
		result.addData("bankStatementsData",bankStatementsData);
		
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.BankStatementsService#delete(org.linys.model.BankStatements)
	 */
	@Override
	public ServiceResult delete(BankStatements model) {
		ServiceResult result = new ServiceResult(false);
		if(model==null||StringUtils.isEmpty(model.getBankStatementsId())){
			result.setMessage("请选择要删除的银行存取款");
			return result;
		}
		BankStatements oldModel = bankStatementsDAO.load(model.getBankStatementsId());
		if(oldModel==null){
			result.setMessage("该银行存取款记录已不存在");
			return result;
		}
		if(oldModel.getStatus()==1){
			result.setMessage("该银行存取款已审核不能删除");
			return result;
		}
		bankStatementsDAO.delete(oldModel);
		result.setIsSuccess(true);
		return result;
		
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.BankStatementsService#mulDelete(java.lang.String)
	 */
	@Override
	public ServiceResult mulDelete(String ids) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(ids)){
			result.setMessage("请选择要删除的银行存取款");
			return result;
		}
		String[] idArray =StringUtil.split(ids);
		if(idArray.length==0){
			result.setMessage("请选择要删除的银行存取款");
			return result;
		}
		boolean haveDel = false;
		for (String id : idArray) {
			BankStatements oldBankStatements = bankStatementsDAO.load(id);
			if(oldBankStatements!=null&&oldBankStatements.getStatus().intValue()==0){
				bankStatementsDAO.delete(oldBankStatements);
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
	 * @see org.linys.service.BankStatementsService#query(org.linys.model.BankStatements, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public ServiceResult query(BankStatements model, Integer page, Integer rows) {
		ServiceResult result = new ServiceResult(false);
		
		List<BankStatements> list = bankStatementsDAO.query(model,page,rows);
		
		String[] properties = {"bankStatementsId","bankStatementsKind","bankStatementsDate","amount","status","bank.bankName","employee.employeeName"};
		String data = JSONUtil.toJson(list,properties);
		result.addData("datagridData", data);
		
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.BankStatementsService#getTotalCount(org.linys.model.BankStatements)
	 */
	@Override
	public ServiceResult getTotalCount(BankStatements model) {
		ServiceResult result = new ServiceResult(false);
		Long data = bankStatementsDAO.getTotalCount(model);
		result.addData("total", data);
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.BankStatementsService#updateStatus(org.linys.model.BankStatements)
	 */
	@Override
	public ServiceResult updateStatus(BankStatements model) {
		ServiceResult result = new ServiceResult(false);
		if(model==null||StringUtils.isEmpty(model.getBankStatementsId())){
			result.setMessage("请选择要更新状态的银行存取款");
			return result;
		}
		BankStatements oldModel = bankStatementsDAO.load(model.getBankStatementsId());
		if(oldModel==null){
			result.setMessage("要更新状态的银行存取款已不存在");
			return result;
		}
		/*
		 * 审核涉及到：
		 * 1.对应银行账户
		 */
		if(model.getStatus()==1){//从未审改为审核
			Bank oldBank = oldModel.getBank();
			if("存款".equals(oldModel.getBankStatementsKind())){
				oldBank.setAmount(oldBank.getAmount()+oldModel.getAmount());
			}else if("取款".equals(oldModel.getBankStatementsKind())){
				oldBank.setAmount(oldBank.getAmount()-oldModel.getAmount());
			}
		}else{
			Bank oldBank = oldModel.getBank();
			if("存款".equals(oldModel.getBankStatementsKind())){
				oldBank.setAmount(oldBank.getAmount()-oldModel.getAmount());
			}else if("取款".equals(oldModel.getBankStatementsKind())){
				oldBank.setAmount(oldBank.getAmount()+oldModel.getAmount());
			}
		}
		oldModel.setStatus(model.getStatus());
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.BankStatementsService#mulUpdateStatus(java.lang.String, org.linys.model.BankStatements)
	 */
	@Override
	public ServiceResult mulUpdateStatus(String ids, BankStatements model) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(ids)){
			result.setMessage("请选择要更新状态的银行存取款");
			return result;
		}
		String[] idArray =StringUtil.split(ids);
		if(idArray.length==0){
			result.setMessage("请选择要更新状态的银行存取款");
			return result;
		}
		boolean haveUpdate = false;
		for (String id : idArray) {
			BankStatements oldModel = bankStatementsDAO.load(id);
			if(oldModel!=null&&oldModel.getStatus().intValue()!=model.getStatus().intValue()){
				if(model.getStatus()==1){//从未审改为审核
					Bank oldBank = oldModel.getBank();
					if("存款".equals(oldModel.getBankStatementsKind())){
						oldBank.setAmount(oldBank.getAmount()+oldModel.getAmount());
					}else if("取款".equals(oldModel.getBankStatementsKind())){
						oldBank.setAmount(oldBank.getAmount()-oldModel.getAmount());
					}
				}else{
					Bank oldBank = oldModel.getBank();
					if("存款".equals(oldModel.getBankStatementsKind())){
						oldBank.setAmount(oldBank.getAmount()-oldModel.getAmount());
					}else if("取款".equals(oldModel.getBankStatementsKind())){
						oldBank.setAmount(oldBank.getAmount()+oldModel.getAmount());
					}
				}
				oldModel.setStatus(model.getStatus());
				haveUpdate = true;
			}
		}
		if(!haveUpdate){
			result.setMessage("没有可更新状态的银行存取款");
			return result;
		}
		result.setIsSuccess(true);
		return result;
	}

}
