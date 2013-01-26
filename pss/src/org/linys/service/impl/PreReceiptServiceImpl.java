package org.linys.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.linys.bean.Pager;
import org.linys.dao.CommonDAO;
import org.linys.dao.PreReceiptDao;
import org.linys.dao.PrefixDAO;
import org.linys.model.Bank;
import org.linys.model.PreReceipt;
import org.linys.service.PreReceiptService;
import org.linys.util.JSONUtil;
import org.linys.util.StringUtil;
import org.linys.vo.GobelConstants;
import org.linys.vo.ServiceResult;
import org.springframework.stereotype.Service;

/**
 * 
 * @Description: 预收实现类
 * @Copyright: 福州骏华科技信息有限公司 (c)2013
 * @Created Date : 2013-1-26
 * @author longweier
 * @vesion 1.0
 */
@Service
public class PreReceiptServiceImpl extends BaseServiceImpl<PreReceipt, String> implements PreReceiptService {

	@Resource
	private PreReceiptDao preReceiptDao;
	@Resource
	private CommonDAO commonDao;
	@Resource
	private PrefixDAO prefixDao;
	
	public ServiceResult savePreReceipt(PreReceipt preReceipt) {
		ServiceResult result = new ServiceResult(false);
		if(preReceipt.getCustomer()==null || StringUtils.isEmpty(preReceipt.getCustomer().getCustomerId())){
			result.setMessage("客户不能为空");
			return result;
		}
		if(preReceipt.getPreReceiptDate()==null){
			result.setMessage("预收日期不能为空");
			return result;
		}
		if(preReceipt.getEmployee()==null || StringUtils.isEmpty(preReceipt.getEmployee().getEmployeeId())){
			result.setMessage("经办人不能为空");
			return result;
		}
		if(preReceipt.getAmount()==null || preReceipt.getAmount().doubleValue()<=0){
			result.setMessage("金额必须大于零");
			return result;
		}
		//新增
		if(StringUtils.isEmpty(preReceipt.getPreReceiptId())){
			preReceipt.setPreReceiptCode(commonDao.getCode(PreReceipt.class.getName(), "preReceiptCode", prefixDao.getPrefix("preReceipt")));
			preReceipt.setCheckAmount(0d);
			preReceipt.setBalanceAmount(0d);
			preReceipt.setStatus(0);
			preReceiptDao.save(preReceipt);
		}else{
			PreReceipt model = preReceiptDao.load(preReceipt.getPreReceiptId());
			model.setCustomer(preReceipt.getCustomer());
			model.setPreReceiptDate(preReceipt.getPreReceiptDate());
			model.setAmount(preReceipt.getAmount());
			model.setBank(preReceipt.getBank());
			model.setEmployee(preReceipt.getEmployee());
			model.setNote(preReceipt.getNote());
		}
		result.setIsSuccess(true);
		result.addData("preReceiptId", preReceipt.getPreReceiptId());
		result.addData("preReceiptCode", preReceipt.getPreReceiptCode());
		return result;
	}
	
	public ServiceResult deletePreReceipt(String preReceiptId) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(preReceiptId)){
			result.setMessage("参数错误");
			return result;
		}
		PreReceipt preReceipt = preReceiptDao.load(preReceiptId);
		if(preReceipt==null){
			result.setMessage("该预收单不存在");
			return result;
		}
		preReceiptDao.delete(preReceipt);
		result.setIsSuccess(true);
		return result;
	}

	public ServiceResult mulDeletePreReceipt(String ids) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(ids)){
			result.setMessage("参数错误");
			return result;
		}
		String[] idArray = StringUtil.split(ids, GobelConstants.SPLIT_SEPARATOR);
		for(String preReceiptId : idArray){
			PreReceipt preReceipt = preReceiptDao.load(preReceiptId);
			if(preReceipt==null) continue;
			preReceiptDao.delete(preReceipt);
		}
		result.setIsSuccess(true);
		return result;
	}
	
	public ServiceResult updateStatusPreReceipt(PreReceipt preReceipt) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(preReceipt.getPreReceiptId()) || preReceipt.getStatus()==null){
			result.setMessage("参数错误");
			return result;
		}
		PreReceipt model = preReceiptDao.load(preReceipt.getPreReceiptId());
		if(model==null){
			result.setMessage("该预收单已被删除");
			return result;
		}
		if(model.getStatus().intValue()==preReceipt.getStatus().intValue()){
			result.setMessage("该预收单已是修改的状态,不能重复修改");
			return result;
		}
		Bank bank = model.getBank();
		if(bank!=null){
			//反审
			if(preReceipt.getStatus()==0){
				bank.setAmount(bank.getAmount()-model.getAmount());
			//审核
			}else{
				bank.setAmount(bank.getAmount()+model.getAmount());
			}
		}
		model.setStatus(preReceipt.getStatus());
		result.setIsSuccess(true);
		return result;
	}
	
	public ServiceResult mulUpdateStatusPreReceipt(String ids, Integer status) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(ids) || status==null){
			result.setMessage("参数错误");
			return result;
		}
		String[] idArray = StringUtil.split(ids, GobelConstants.SPLIT_SEPARATOR);
		for(String preReceiptId : idArray){
			PreReceipt model = preReceiptDao.load(preReceiptId);
			if(model==null || model.getStatus().intValue()==status.intValue()) continue;
			Bank bank = model.getBank();
			if(bank!=null){
				//反审
				if(status==0){
					bank.setAmount(bank.getAmount()-model.getAmount());
				//审核
				}else{
					bank.setAmount(bank.getAmount()+model.getAmount());
				}
			}
			model.setStatus(status);
		}
		result.setIsSuccess(true);
		return result;
	}

	public String initPreReceipt(String preReceiptId) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(preReceiptId)){
			result.setMessage("参数错误");
			return result.toJSON();
		}
		PreReceipt preReceipt = preReceiptDao.load(preReceiptId);
		String[] properties = {"preReceiptId","preReceiptCode","preReceiptDate","customer.customerId",
				"customer.customerName","amount","bank.bankId","employee.employeeId","note","status"};
		String preReceiptData = JSONUtil.toJson(preReceipt,properties);
		result.addData("preReceiptData",preReceiptData);
		result.setIsSuccess(true);
		return result.toJSON();
	}
	
	public String queryPreReceipt(Integer pageNumber, Integer pageSize,PreReceipt preReceipt, Date beginDate, Date endDate) {
		Pager pager = new Pager(pageNumber, pageSize);
		pager = preReceiptDao.queryPreReceipt(pager, preReceipt, beginDate, endDate);
		
		String[] properties = {"preReceiptId","preReceiptCode","preReceiptDate","customer.customerName","amount","checkAmount","balanceAmount",
				"bank.bankName","employee.employeeName","note","status"};
		String jsonArray = JSONUtil.toJson(pager.getList(), properties, pager.getTotalCount());
		return jsonArray;
	}
}
