package org.linys.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.linys.dao.BankDAO;
import org.linys.model.Bank;
import org.linys.service.BankService;
import org.linys.util.JSONUtil;
import org.linys.vo.ServiceResult;
import org.springframework.stereotype.Service;
@Service
public class BankServiceImpl extends BaseServiceImpl<Bank, String> implements
		BankService {
	@Resource
	private BankDAO bankDAO;
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.BankService#save(org.linys.model.Bank)
	 */
	@Override
	public ServiceResult save(Bank model) {
		ServiceResult result = new ServiceResult(false);
		if(model==null){
			result.setMessage("请填写银行信息");
			return result;
		}
		if(StringUtils.isEmpty(model.getBankShortName())){
			result.setMessage("请填写银行简称");
			return result;
		}
		
		if(StringUtils.isEmpty(model.getBankId())){//新增
			Bank oldModel = bankDAO.load("bankShortName", model.getBankShortName());
			if(oldModel!=null){
				result.setMessage("该银行简称已存在");
				return result;
			}
			if(model.getAmount()==null){
				model.setAmount(0.0);
			}
			bankDAO.save(model);
		}else{
			Bank oldModel = bankDAO.load(model.getBankId());
			if(oldModel==null){
				result.setMessage("该银行已不存在");
				return result;
			}else if(!oldModel.getBankShortName().equals(model.getBankShortName())){
				Bank oldBank = bankDAO.load("bankShortName", model.getBankShortName());
				if(oldBank!=null){
					result.setMessage("该银行简称已存在");
					return result;
				}
			}
			oldModel.setBankName(model.getBankName());
			oldModel.setBankShortName(model.getBankShortName());
			
			bankDAO.update(oldModel);
		}
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.BankService#delete(org.linys.model.Bank)
	 */
	@Override
	public ServiceResult delete(Bank model) {
		ServiceResult result = new ServiceResult(false);
		if(model==null||StringUtils.isEmpty(model.getBankId())){
			result.setMessage("请选择要删除的银行");
			return result;
		}
		Bank oldModel = bankDAO.load(model.getBankId());
		if(oldModel==null){
			result.setMessage("该银行已不存在");
			return result;
		}else{
			bankDAO.delete(oldModel);
		}
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.BankService#query(org.linys.model.Bank, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public ServiceResult query(Bank model, Integer page, Integer rows) {
		ServiceResult result = new ServiceResult(false);
		
		List<Bank> list = bankDAO.query(model,page,rows);
		
		String[] properties = {"bankId","bankShortName","bankName","amount"};
		String data = JSONUtil.toJson(list,properties);
		result.addData("datagridData", data);
		
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.BankService#getTotalCount(org.linys.model.Bank)
	 */
	@Override
	public ServiceResult getTotalCount(Bank model) {
		ServiceResult result = new ServiceResult(false);
		Long data = bankDAO.getTotalCount(model);
		result.addData("total", data);
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.BankService#queryCombobox()
	 */
	@Override
	public String queryCombobox() {
		List<Bank> list = bankDAO.queryAll();
		String[] properties = {"bankId","bankShortName"};
		String jsonString = JSONUtil.toJsonWithoutRows(list,properties);
		return jsonString;
	}

}
