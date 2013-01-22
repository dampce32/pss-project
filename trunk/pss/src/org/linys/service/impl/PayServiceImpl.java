package org.linys.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.linys.dao.BankDAO;
import org.linys.dao.CommonDAO;
import org.linys.dao.PayDAO;
import org.linys.dao.PayDetailDAO;
import org.linys.dao.PrefixDAO;
import org.linys.dao.ReceiveDAO;
import org.linys.model.Bank;
import org.linys.model.Pay;
import org.linys.model.PayDetail;
import org.linys.model.Receive;
import org.linys.service.PayService;
import org.linys.util.JSONUtil;
import org.linys.util.StringUtil;
import org.linys.vo.GobelConstants;
import org.linys.vo.ServiceResult;
import org.springframework.stereotype.Service;
@Service
public class PayServiceImpl extends BaseServiceImpl<Pay, String> implements
		PayService {
	@Resource
	private PayDAO payDAO;
	@Resource
	private CommonDAO commonDAO;
	@Resource
	private PayDetailDAO payDetailDAO;
	@Resource
	private BankDAO bankDAO;
	@Resource
	private ReceiveDAO receiveDAO;
	@Resource
	private PrefixDAO prefixDAO;
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.PayService#query(org.linys.model.Pay, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public ServiceResult query(Pay model, Integer page, Integer rows) {
		ServiceResult result = new ServiceResult(false);
		
		List<Pay> list = payDAO.query(model,page,rows);
		
		String[] properties = {"payId","payCode","payDate","status","supplier.supplierName","payAmount","discountAmount",
				"employee.employeeName","note"};
		String data = JSONUtil.toJson(list,properties);
		result.addData("datagridData", data);
		
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.PayService#getTotalCount(org.linys.model.Pay)
	 */
	@Override
	public ServiceResult getTotalCount(Pay model) {
		ServiceResult result = new ServiceResult(false);
		Long data = payDAO.getTotalCount(model);
		result.addData("total", data);
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.PayService#save(org.linys.model.Pay, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public ServiceResult save(Pay model, String payDetailIds,
			String delPayDetailIds, String receiveIds, String payKinds,
			String amounts, String payedAmounts,String discountedAmounts, String discountAmounts,
			String payAmounts) {
		ServiceResult result = new ServiceResult(false);
		if(model==null){
			result.setMessage("请填写付款单信息");
			return result;
		}
		
		if(model.getSupplier()==null||StringUtils.isEmpty(model.getSupplier().getSupplierId())){
			result.setMessage("请选择供应商");
			return result;
		}
		if(model.getBank()==null||StringUtils.isEmpty(model.getBank().getBankId())){
			result.setMessage("请选择银行");
			return result;
		}
		
		if(model.getPayDate()==null){
			result.setMessage("请选择付款日期");
			return result;
		}
		if(model.getEmployee()==null||StringUtils.isEmpty(model.getEmployee().getEmployeeId())){
			result.setMessage("请选择经办人");
			return result;
		}
		
		if(StringUtils.isEmpty(payKinds)){
			result.setMessage("请选择付款内容");
			return result;
		}
		String[] payDetailIdArray = StringUtil.split(payDetailIds, GobelConstants.SPLIT_SEPARATOR);
		String[] delPayDetailIdArray = StringUtil.split(delPayDetailIds, GobelConstants.SPLIT_SEPARATOR);
		String[] receiveIdArray = StringUtil.split(receiveIds, GobelConstants.SPLIT_SEPARATOR);
		String[] payKindArray = StringUtil.split(payKinds, GobelConstants.SPLIT_SEPARATOR);
		String[] amountArray = StringUtil.split(amounts, GobelConstants.SPLIT_SEPARATOR);
		String[] payedAmountArray = StringUtil.split(payedAmounts, GobelConstants.SPLIT_SEPARATOR);
		String[] discountedAmountArray = StringUtil.split(discountedAmounts, GobelConstants.SPLIT_SEPARATOR);
		String[] discountAmountArray = StringUtil.split(discountAmounts, GobelConstants.SPLIT_SEPARATOR);
		String[] payAmountArray = StringUtil.split(payAmounts, GobelConstants.SPLIT_SEPARATOR);
		if(payKindArray==null||payKindArray.length==0){
			result.setMessage("请选择付款内容");
			return result;
		}
		for (int i = 0; i < payAmountArray.length; i++) {
			String qty = payAmountArray[i];
			if("0".equals(qty)){
				result.setMessage("第"+(i+1)+"行本次付款不能为0");
				return result;
			}
			
		}
		Double totalDiscountAmount = 0.0;//总共优惠金额
		Double totalPayAmount = 0.0;//总共实付金额
		if(StringUtils.isEmpty(model.getPayId())){//新增
			//取得入库单号
			model.setStatus(0);
			model.setPayCode(commonDAO.getCode("Pay", "payCode",prefixDAO.getPrefix("pay")));
			payDAO.save(model);
			for (int i = 0; i < payKindArray.length; i++) {
				String receiveId = receiveIdArray[i];
				String payKind = payKindArray[i];
				String amount = amountArray[i];
				String payedAmount = payedAmountArray[i];
				String discountedAmount = discountedAmountArray[i];
				Double discountAmount = new Double(discountAmountArray[i]);
				Double payAmount = new Double(payAmountArray[i]);
				PayDetail payDetail = new PayDetail();
				if("采购入库".equals(payKind)){
					Receive receive = new Receive();
					receive.setReceiveId(receiveId);
					payDetail.setReceive(receive);
				}
				
				payDetail.setPay(model);
				payDetail.setPayKind(payKind);
				payDetail.setAmount(new Double(amount));
				payDetail.setPayedAmount(new Double(payedAmount));
				payDetail.setDiscountedAmount(new Double(discountedAmount));
				payDetail.setDiscountAmount(discountAmount);
				payDetail.setPayAmount(payAmount);
				
				payDetailDAO.save(payDetail);
				
				totalDiscountAmount+=discountAmount;
				totalPayAmount+=payAmount;
			}
			model.setDiscountAmount(totalDiscountAmount);
			model.setPayAmount(totalPayAmount);
			payDAO.update(model);
		}else{
			//更新付款单
			Pay oldPay = payDAO.load(model.getPayId());
			if(oldPay==null){
				result.setMessage("要更新的付款单已不存在");
				return result;
			}
			if(oldPay.getStatus()==1){
				result.setMessage("要更新的付款单已审核已不能修改");
				return result;
			}
			oldPay.setPayCode(model.getPayCode());
			oldPay.setPayDate(model.getPayDate());
			oldPay.setSupplier(model.getSupplier());
			oldPay.setPayway(model.getPayway());
			oldPay.setBank(model.getBank());
			
			oldPay.setEmployee(model.getEmployee());
			oldPay.setNote(model.getNote());
			
			//删除已删的付款单明细
			if(!"".equals(delPayDetailIds)){
				for (String delPayDetailId : delPayDetailIdArray) {
					PayDetail oldModel = payDetailDAO.load(delPayDetailId);
					if(oldModel!=null){
						payDetailDAO.delete(oldModel);
					}
				}
			}
			//根据付款单明细Id更新或新增
			for (int i = 0 ;i<payDetailIdArray.length;i++) {
				String receiveId = receiveIdArray[i];
				String payKind = payKindArray[i];
				String payDetailId = payDetailIdArray[i];
				String amount = amountArray[i];
				String payedAmount = payedAmountArray[i];
				String discountedAmount = discountedAmountArray[i];
				Double discountAmount = new Double(discountAmountArray[i]);
				Double payAmount = new Double(payAmountArray[i]);
				
				if(StringUtils.isEmpty(payDetailId)){//新增
					
					PayDetail payDetail = new PayDetail();
					if("采购入库".equals(payKind)){
						Receive receive = new Receive();
						receive.setReceiveId(receiveId);
						payDetail.setReceive(receive);
					}
					payDetail.setPayKind(payKind);
					payDetail.setPay(model);
					payDetail.setAmount(new Double(amount));
					payDetail.setPayedAmount(new Double(payedAmount));
					payDetail.setDiscountedAmount(new Double(discountedAmount));
					payDetail.setDiscountAmount(discountAmount);
					payDetail.setPayAmount(payAmount);
					
					payDetailDAO.save(payDetail);
					
					totalDiscountAmount+=discountAmount;
					totalPayAmount+=payAmount;
				}else{
					PayDetail oldPayDetail = payDetailDAO.load(payDetailId);
					oldPayDetail.setDiscountAmount(discountAmount);
					oldPayDetail.setPayAmount(payAmount);
					
					payDetailDAO.update(oldPayDetail);
					
					totalDiscountAmount+=discountAmount;
					totalPayAmount+=payAmount;
				}
			}
			oldPay.setDiscountAmount(totalDiscountAmount);
			oldPay.setPayAmount(totalPayAmount);
			payDAO.update(oldPay);
		}
		//返回值
		result.addData("payId", model.getPayId());
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.PayService#init(java.lang.String)
	 */
	@Override
	public ServiceResult init(String payId) {
		ServiceResult result = new ServiceResult(false);
		Pay pay = payDAO.init(payId);
		String[] propertiesPay = {"payId","payCode","payDate",
				"supplier.supplierId","discountAmount","payAmount","payway",
				"bank.bankId","employee.employeeId","note","status"};
		String payData = JSONUtil.toJson(pay,propertiesPay);
		result.addData("payData",payData);
		
		List<PayDetail> payDetailList = payDetailDAO.queryByPayId(payId);
		String[] propertiesDetail = {"payDetailId","receive.receiveId","receive.receiveCode","receive.receiveDate","payKind",
				"amount","payedAmount","discountedAmount","needPayAmount","discountAmount","payAmount"};
		String detailData = JSONUtil.toJson(payDetailList,propertiesDetail);
		result.addData("detailData", detailData);
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.PayService#delete(org.linys.model.Pay)
	 */
	@Override
	public ServiceResult delete(Pay model) {
		ServiceResult result = new ServiceResult(false);
		if(model==null||StringUtils.isEmpty(model.getPayId())){
			result.setMessage("请选择要删除的付款单");
			return result;
		}
		Pay oldPay = payDAO.load(model.getPayId());
		
		if(oldPay==null){
			result.setMessage("要删除的付款单已不存在");
			return result;
		}
		if(oldPay.getStatus()==1){
			result.setMessage("要删除的付款单已审核，不能修改了");
			return result;
		}
		payDAO.delete(oldPay);
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.PayService#mulDelete(java.lang.String)
	 */
	@Override
	public ServiceResult mulDelete(String ids) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(ids)){
			result.setMessage("请选择要删除的付款单");
			return result;
		}
		String[] idArray =StringUtil.split(ids,GobelConstants.SPLIT_SEPARATOR);
		if(idArray.length==0){
			result.setMessage("请选择要删除的付款单");
			return result;
		}
		boolean haveDel = false;
		for (String id : idArray) {
			Pay oldPay = payDAO.load(id);
			if(oldPay!=null&&oldPay.getStatus()==0){
				payDAO.delete(oldPay);
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
	 * @see org.linys.service.PayService#updateStatus(org.linys.model.Pay)
	 */
	@Override
	public ServiceResult updateStatus(Pay model) {
		ServiceResult result = new ServiceResult(false);
		if(model==null||StringUtils.isEmpty(model.getPayId())){
			result.setMessage("请选择要修改状态的付款单");
			return result;
		}
		Pay oldPay = payDAO.load(model.getPayId());
		
		if(oldPay==null){
			result.setMessage("要修改审核状态的付款单已不存在");
			return result;
		}
		if(oldPay.getStatus().intValue()==model.getStatus().intValue()){
			result.setMessage("要修改审核状态的付款单已是要修改的状态，请刷新界面");
			return result;
		}
		/*
		 * 状态变化主要涉及到：
		 * 1.银行金额
		 * 2.如果是付款单下的付款明细是采购入库单来的，需要判断需要修改IsPay
		 */
		if(model.getStatus()==1){//如果是由未审改为已审
			//更新对应银行的账户金额
			Bank oldBank = bankDAO.load(oldPay.getBank().getBankId());
			oldBank.setAmount(oldBank.getAmount()-oldPay.getPayAmount());
			bankDAO.update(oldBank);
			//更新采购入库单的isPay字段
			List<PayDetail> payDetailList = payDetailDAO.queryByPayId(model.getPayId());
			for (PayDetail payDetail : payDetailList) {
				if(payDetail.getReceive()!=null){
					//取得本入库单已付金额
					Double needPayAmount = receiveDAO.getNeedPayAmount(payDetail.getReceive().getReceiveId());
					if(needPayAmount<=payDetail.getPayAmount()+payDetail.getDiscountAmount()){
						Receive oldReceive = receiveDAO.load(payDetail.getReceive().getReceiveId());
						oldReceive.setIsPay(1);
						receiveDAO.update(oldReceive);
					}
				}
			}
		}else if(model.getStatus()==0){//如果是由已审改为未审
			//更新对应银行的账户金额
			Bank oldBank = bankDAO.load(oldPay.getBank().getBankId());
			oldBank.setAmount(oldBank.getAmount()+oldPay.getPayAmount());
			bankDAO.update(oldBank);
			//更新采购入库单的isPay字段
			List<PayDetail> payDetailList = payDetailDAO.queryByPayId(model.getPayId());
			for (PayDetail payDetail : payDetailList) {
				if(payDetail.getReceive()!=null){
					//取得本入库单已付金额
					Double needPayAmount = receiveDAO.getNeedPayAmount(payDetail.getReceive().getReceiveId());
					if(needPayAmount<=payDetail.getPayAmount()+payDetail.getDiscountAmount()){
						Receive oldReceive = receiveDAO.load(payDetail.getReceive().getReceiveId());
						oldReceive.setIsPay(0);
						receiveDAO.update(oldReceive);
					}
				}
			}
		}
		oldPay.setStatus(model.getStatus());
		payDAO.update(oldPay);
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.PayService#mulUpdateStatus(java.lang.String, org.linys.model.Pay)
	 */
	@Override
	public ServiceResult mulUpdateStatus(String ids, Pay model) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(ids)){
			result.setMessage("请选择要修改状态的付款单");
			return result;
		}
		String[] idArray =StringUtil.split(ids,GobelConstants.SPLIT_SEPARATOR);
		if(idArray.length==0){
			result.setMessage("请选择要修改状态的付款单");
			return result;
		}
		if(model==null||model.getStatus()==null){
			result.setMessage("请选择要修改成的审核状态");
			return result;
		}
		boolean haveUpdateStatus = false;
		for (String id : idArray) {
			Pay oldPay = payDAO.load(id);
			if(oldPay!=null&&oldPay.getStatus().intValue()!=model.getStatus().intValue()){
				/*
				 * 状态变化主要涉及到：
				 * 1.银行金额
				 * 2.如果是付款单下的付款明细是采购入库单来的，需要判断需要修改IsPay
				 */
				if(model.getStatus()==1){//如果是由未审改为已审
					//更新对应银行的账户金额
					Bank oldBank = bankDAO.load(oldPay.getBank().getBankId());
					oldBank.setAmount(oldBank.getAmount()-oldPay.getPayAmount());
					bankDAO.update(oldBank);
					//更新采购入库单的isPay字段
					List<PayDetail> payDetailList = payDetailDAO.queryByPayId(model.getPayId());
					for (PayDetail payDetail : payDetailList) {
						if(payDetail.getReceive()!=null){
							//取得本入库单已付金额
							Double needPayAmount = receiveDAO.getNeedPayAmount(payDetail.getReceive().getReceiveId());
							if(needPayAmount<=payDetail.getPayAmount()+payDetail.getDiscountAmount()){
								Receive oldReceive = receiveDAO.load(payDetail.getReceive().getReceiveId());
								oldReceive.setIsPay(1);
								receiveDAO.update(oldReceive);
							}
						}
					}
				}else if(model.getStatus()==0){//如果是由已审改为未审
					//更新对应银行的账户金额
					Bank oldBank = bankDAO.load(oldPay.getBank().getBankId());
					oldBank.setAmount(oldBank.getAmount()+oldPay.getPayAmount());
					bankDAO.update(oldBank);
					//更新采购入库单的isPay字段
					List<PayDetail> payDetailList = payDetailDAO.queryByPayId(model.getPayId());
					for (PayDetail payDetail : payDetailList) {
						if(payDetail.getReceive()!=null){
							//取得本入库单已付金额
							Double needPayAmount = receiveDAO.getNeedPayAmount(payDetail.getReceive().getReceiveId());
							if(needPayAmount>payDetail.getPayAmount()+payDetail.getDiscountAmount()){
								Receive oldReceive = receiveDAO.load(payDetail.getReceive().getReceiveId());
								oldReceive.setIsPay(0);
								receiveDAO.update(oldReceive);
							}
						}
					}
				}
				oldPay.setStatus(model.getStatus());
				payDAO.update(oldPay);
			}
		}
		if(!haveUpdateStatus){
			result.setMessage("没有可修改状态的付款单");
			return result;
		}
		result.setIsSuccess(true);
		return result;
	}

}
