package org.linys.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.linys.dao.BankDAO;
import org.linys.dao.CommonDAO;
import org.linys.dao.PrefixDAO;
import org.linys.dao.PrepayDAO;
import org.linys.model.Bank;
import org.linys.model.Prepay;
import org.linys.service.PrepayService;
import org.linys.util.JSONUtil;
import org.linys.util.StringUtil;
import org.linys.vo.GobelConstants;
import org.linys.vo.ServiceResult;
import org.springframework.stereotype.Service;
/**
 * @Description:预付款Service实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-1-23
 * @author lys
 * @vesion 1.0
 */
@Service
public class PrepayServiceImpl extends BaseServiceImpl<Prepay, String>
		implements PrepayService {
	@Resource
	private PrepayDAO prepayDAO;
	@Resource
	private CommonDAO commonDAO;
	@Resource
	private PrefixDAO prefixDAO;
	@Resource
	private BankDAO bankDAO;
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.PrepayService#query(org.linys.model.Prepay, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public ServiceResult query(Prepay model, Integer page, Integer rows) {
		ServiceResult result = new ServiceResult(false);
		
		List<Prepay> list = prepayDAO.query(model,page,rows);
		
		String[] properties = {"prepayId","prepayCode","prepayDate",
				"supplier.supplierName","amount","checkAmount","balanceAmount","bank.bankName",
				"employee.employeeName","note","status"};
		String data = JSONUtil.toJson(list,properties);
		result.addData("datagridData", data);
		
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.PrepayService#getTotalCount(org.linys.model.Prepay)
	 */
	@Override
	public ServiceResult getTotalCount(Prepay model) {
		ServiceResult result = new ServiceResult(false);
		Long data = prepayDAO.getTotalCount(model);
		result.addData("total", data);
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.PrepayService#save(org.linys.model.Prepay)
	 */
	@Override
	public ServiceResult save(Prepay model) {
		ServiceResult result = new ServiceResult(false);
		if(model==null){
			result.setMessage("请填写预付单信息");
			return result;
		}
		
		if(model.getSupplier()==null||StringUtils.isEmpty(model.getSupplier().getSupplierId())){
			result.setMessage("请选择供应商");
			return result;
		}
		if(model.getPrepayDate()==null){
			result.setMessage("请选择付款日期");
			return result;
		}
		if(model.getAmount()==0){
			result.setMessage("请填写付款金额，付款金额不能为0");
			return result;
		}
		if(model.getBank()==null||StringUtils.isEmpty(model.getBank().getBankId())){
			result.setMessage("请选择银行");
			return result;
		}
		
		if(model.getEmployee()==null||StringUtils.isEmpty(model.getEmployee().getEmployeeId())){
			result.setMessage("请选择经办人");
			return result;
		}
		if(StringUtils.isEmpty(model.getPrepayId())){//新增
			//取得入库单号
			model.setPrepayCode(commonDAO.getCode("Prepay", "prepayCode", prefixDAO.getPrefix("prepay")));
			model.setCheckAmount(0.0);
			model.setStatus(0);
			prepayDAO.save(model);
		}else{
			//更新预付单
			Prepay oldPrepay = prepayDAO.load(model.getPrepayId());
			if(oldPrepay==null){
				result.setMessage("要更新的预付单已不存在");
				return result;
			}else{
				oldPrepay.setSupplier(model.getSupplier());
				oldPrepay.setPrepayDate(model.getPrepayDate());
				oldPrepay.setAmount(model.getAmount());
				oldPrepay.setBank(model.getBank());
				oldPrepay.setEmployee(model.getEmployee());
				oldPrepay.setNote(model.getNote());
				prepayDAO.update(oldPrepay);
			}
		}
		//返回值
		result.addData("prepayId", model.getPrepayId());
		result.addData("prepayCode", model.getPrepayCode());
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.PrepayService#init(java.lang.String)
	 */
	@Override
	public ServiceResult init(String prepayId) {
		ServiceResult result = new ServiceResult(false);
		Prepay prepay = prepayDAO.load(prepayId);
		String[] propertiesPrepay = {"prepayId","prepayCode","prepayDate",
				"supplier.supplierId","amount",
				"bank.bankId","employee.employeeId","note","status"};
		String prepayData = JSONUtil.toJson(prepay,propertiesPrepay);
		result.addData("prepayData",prepayData);
		
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.PrepayService#delete(org.linys.model.Prepay)
	 */
	@Override
	public ServiceResult delete(Prepay model) {
		ServiceResult result = new ServiceResult(false);
		if(model==null||StringUtils.isEmpty(model.getPrepayId())){
			result.setMessage("请选择要删除的预付单");
			return result;
		}
		Prepay oldModel = prepayDAO.load(model.getPrepayId());
		if(oldModel==null||oldModel.getStatus().intValue()==1){
			result.setMessage("要删除的预付单已不存在");
			return result;
		}
		if(oldModel.getStatus().intValue()==1){
			result.setMessage("要删除的预付单已审核通过已不能删除");
			return result;
		}
		prepayDAO.delete(oldModel);
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.PrepayService#mulDelele(java.lang.String)
	 */
	@Override
	public ServiceResult mulDelele(String ids) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(ids)){
			result.setMessage("请选择要删除的预付单");
			return result;
		}
		String[] idArray =StringUtil.split(ids,GobelConstants.SPLIT_SEPARATOR);
		if(idArray.length==0){
			result.setMessage("请选择要删除的预付单");
			return result;
		}
		boolean haveDel = false;
		for (String id : idArray) {
			Prepay oldPrepay = prepayDAO.load(id);
			if(oldPrepay!=null&&oldPrepay.getStatus()==0){
				prepayDAO.delete(oldPrepay);
				haveDel = true;
			}
		}
		if(!haveDel){
			result.setMessage("没有可删除的预付单");
			return result;
		}
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.PrepayService#updateStatus(org.linys.model.Prepay)
	 */
	@Override
	public ServiceResult updateStatus(Prepay model) {
		ServiceResult result = new ServiceResult(false);
		if(model==null||StringUtils.isEmpty(model.getPrepayId())){
			result.setMessage("请选择要更新状态的预付单");
			return result;
		}
		Prepay oldPrepay = prepayDAO.load(model.getPrepayId());
		Bank oldBank = bankDAO.load(oldPrepay.getBank().getBankId());
		if(oldPrepay!=null&&oldPrepay.getStatus().intValue()!=model.getStatus().intValue()){
			if(model.getStatus()==1){//如果是由未审改为已审
				oldBank.setAmount(oldBank.getAmount()-oldPrepay.getAmount());
			}else if(model.getStatus()==0){//如果是由已审改为未审
				oldBank.setAmount(oldBank.getAmount()+oldPrepay.getAmount());
			}
			bankDAO.update(oldBank);
			oldPrepay.setStatus(model.getStatus());
			prepayDAO.update(oldPrepay);
		}
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.PrepayService#mulUpdateStatus(java.lang.String, org.linys.model.Prepay)
	 */
	@Override
	public ServiceResult mulUpdateStatus(String ids, Prepay model) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(ids)){
			result.setMessage("请选择要修改状态的预付单");
			return result;
		}
		String[] idArray =StringUtil.split(ids,GobelConstants.SPLIT_SEPARATOR);
		if(idArray.length==0){
			result.setMessage("请选择要修改状态的预付单");
			return result;
		}
		if(model==null||model.getStatus()==null){
			result.setMessage("请选择要修改成的审核状态");
			return result;
		}
		boolean haveUpdateShzt = false;
		for (String id : idArray) {
			Prepay oldPrepay = prepayDAO.load(id);
			Bank oldBank = bankDAO.load(oldPrepay.getBank().getBankId());
			if(oldPrepay!=null&&oldPrepay.getStatus().intValue()!=model.getStatus().intValue()){
				if(model.getStatus()==1){//如果是由未审改为已审
					oldBank.setAmount(oldBank.getAmount()-oldPrepay.getAmount());
				}else if(model.getStatus()==0){//如果是由已审改为未审
					oldBank.setAmount(oldBank.getAmount()+oldPrepay.getAmount());
				}
				bankDAO.update(oldBank);
				oldPrepay.setStatus(model.getStatus());
				prepayDAO.update(oldPrepay);
				haveUpdateShzt = true;
			}
		}
		if(!haveUpdateShzt){
			result.setMessage("没有可修改审核状态的预付单");
			return result;
		}
		result.setIsSuccess(true);
		return result;
	}

}
