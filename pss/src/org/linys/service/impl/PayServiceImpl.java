package org.linys.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.linys.dao.CommonDAO;
import org.linys.dao.PayDAO;
import org.linys.dao.PayDetailDAO;
import org.linys.model.Pay;
import org.linys.model.PayDetail;
import org.linys.model.Receive;
import org.linys.service.PayService;
import org.linys.util.CommonUtil;
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
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.PayService#query(org.linys.model.Pay, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public ServiceResult query(Pay model, Integer page, Integer rows) {
		ServiceResult result = new ServiceResult(false);
		
		List<Pay> list = payDAO.query(model,page,rows);
		
		String[] properties = {"payId","payCode","payDate","status"};
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
		
		if(StringUtils.isEmpty(payDetailIds)){
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
		if(payKindArray==null||payDetailIdArray.length==0){
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
			model.setPayCode(commonDAO.getCode("Pay", "payCode", CommonUtil.getCodePrefix("pay")));
			payDAO.save(model);
			for (int i = 0; i < payKindArray.length; i++) {
				String payKind = payKindArray[i];
				String receiveId = receiveIdArray[i];
				String amount = amountArray[i];
				String payedAmount = payedAmountArray[i];
				String discountedAmount = discountedAmountArray[i];
				Double discountAmount = new Double(discountAmountArray[i]);
				Double payAmount = new Double(payAmountArray[i]);
				Receive receive = null;
				if("采购入库".equals(payKind)){
					receive = new Receive();
					receive.setReceiveId(receiveId);
				}
				
				PayDetail payDetail = new PayDetail();
				payDetail.setPay(model);
				payDetail.setReceive(receive);
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
			payDAO.update(oldPay);
			
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
				String payKind = payKindArray[i];
				String payDetailId = payDetailIdArray[i];
				String receiveId =receiveIdArray[i];
				String amount = amountArray[i];
				String payedAmount = payedAmountArray[i];
				String discountedAmount = discountedAmountArray[i];
				Double discountAmount = new Double(discountAmountArray[i]);
				Double payAmount = new Double(payAmountArray[i]);
				Receive receive = null;
				if("采购入库".equals(payKind)){
					receive = new Receive();
					receive.setReceiveId(receiveId);
				}
				
				if(StringUtils.isEmpty(payDetailId)){//新增
					
					PayDetail payDetail = new PayDetail();
					payDetail.setPay(model);
					payDetail.setReceive(receive);
					payDetail.setAmount(new Double(amount));
					payDetail.setPayedAmount(new Double(payedAmount));
					payDetail.setDiscountedAmount(new Double(discountedAmount));
					payDetail.setDiscountAmount(discountAmount);
					payDetail.setPayAmount(payAmount);
					
					payDetailDAO.save(payDetail);
					
					totalDiscountAmount+=discountAmount;
					totalPayAmount+=payAmount;
				}else{
					PayDetail oldModel = payDetailDAO.load(payDetailId);
					
					oldModel.setPay(model);
					oldModel.setReceive(receive);
					oldModel.setDiscountAmount(discountAmount);
					oldModel.setPayAmount(payAmount);
					
					payDetailDAO.update(oldModel);
					
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
		Pay pay = payDAO.load(payId);
		String[] propertiesPay = {"payId","payCode","payDate",
				"supplier.supplierId","discountAmount","payAmount",
				"bank.bankId","employee.employeeId","note","status"};
		String payData = JSONUtil.toJson(pay,propertiesPay);
		result.addData("payData",payData);
		
		List<PayDetail> payDetailList = payDetailDAO.queryByPayId(payId);
		String[] propertiesDetail = {"payDetailId","receive.receiveId","receive.receiveCode","receive.receiveDate",
				"amount","payedAmount","discountedAmount","discountAmount","payAmount"};
		String detailData = JSONUtil.toJson(payDetailList,propertiesDetail);
		result.addData("detailData", detailData);
		result.setIsSuccess(true);
		return result;
	}

}
