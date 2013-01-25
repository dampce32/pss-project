package org.linys.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.linys.dao.BankDAO;
import org.linys.dao.CommonDAO;
import org.linys.dao.PayDAO;
import org.linys.dao.PayDetailDAO;
import org.linys.dao.PrefixDAO;
import org.linys.model.Bank;
import org.linys.model.Buy;
import org.linys.model.Pay;
import org.linys.model.PayDetail;
import org.linys.model.Prepay;
import org.linys.model.Receive;
import org.linys.model.Reject;
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
			String delPayDetailIds, String sourceIds, String sourceCodes, String sourceDates, String payKinds,
			String amounts, String payedAmounts, String discountAmounts,
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
		String[] payDetailIdArray = StringUtil.split(payDetailIds);
		String[] delPayDetailIdArray = StringUtil.split(delPayDetailIds);
		String[] sourceIdArray = StringUtil.split(sourceIds);
		String[] sourceCodeArray = StringUtil.split(sourceCodes);
		String[] sourceDateArray = StringUtil.split(sourceDates);
		String[] payKindArray = StringUtil.split(payKinds);
		String[] amountArray = StringUtil.split(amounts);
		String[] payedAmountArray = StringUtil.split(payedAmounts);
		String[] discountAmountArray = StringUtil.split(discountAmounts);
		String[] payAmountArray = StringUtil.split(payAmounts);
		if(payKindArray==null||payKindArray.length==0){
			result.setMessage("请选择付款内容");
			return result;
		}
		for (int i = 0; i < payKindArray.length; i++) {
			Double amount = new Double(amountArray[i]);
			Double payedAmount = new Double(payedAmountArray[i]);
			Double discountAmount = new Double(discountAmountArray[i]);
			Double payAmount = new Double(payAmountArray[i]);
			if(payAmount==0){
				result.setMessage("第"+(i+1)+"行本次实付的金额为0,请输入");
				return result;
			}
			if(Math.abs(amount-payedAmount)<Math.abs(discountAmount+payAmount)){
				result.setMessage("第"+(i+1)+"行本次付款金额大于可付金额");
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
				String sourceId = sourceIdArray[i];
				String sourceCode = sourceCodeArray[i];
				String sourceDate = sourceDateArray[i];
				String payKind = payKindArray[i];
				String amount = amountArray[i];
				String payedAmount = payedAmountArray[i];
				Double discountAmount = new Double(discountAmountArray[i]);
				Double payAmount = new Double(payAmountArray[i]);
				PayDetail payDetail = new PayDetail();
				if("采购单预付款".equals(payKind)){
					Buy buy = new Buy();
					buy.setBuyId(sourceId);
					payDetail.setBuy(buy);
				}else if("采购入库".equals(payKind)){
					Receive receive = new Receive();
					receive.setReceiveId(sourceId);
					payDetail.setReceive(receive);
				}else if("退货单".equals(payKind)){
					Reject reject = new Reject();
					reject.setRejectId(sourceId);
					payDetail.setReject(reject);
				}else if("预付单预付款".equals(payKind)){
					Prepay prepay = new Prepay();
					prepay.setPrepayId(sourceId);
					payDetail.setPrepay(prepay);
				}
				
				payDetail.setPay(model);
				payDetail.setSourceCode(sourceCode);
				payDetail.setSourceDate(sourceDate);
				payDetail.setPayKind(payKind);
				payDetail.setAmount(new Double(amount));
				payDetail.setPayedAmount(new Double(payedAmount));
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
				String sourceId = sourceIdArray[i];
				String sourceCode = sourceCodeArray[i];
				String sourceDate = sourceDateArray[i];
				String payKind = payKindArray[i];
				String payDetailId = payDetailIdArray[i];
				String amount = amountArray[i];
				String payedAmount = payedAmountArray[i];
				Double discountAmount = new Double(discountAmountArray[i]);
				Double payAmount = new Double(payAmountArray[i]);
				
				if(StringUtils.isEmpty(payDetailId)){//新增
					
					PayDetail payDetail = new PayDetail();
					if("采购单预付款".equals(payKind)){
						Buy buy = new Buy();
						buy.setBuyId(sourceId);
						payDetail.setBuy(buy);
					}else if("采购入库".equals(payKind)){
						Receive receive = new Receive();
						receive.setReceiveId(sourceId);
						payDetail.setReceive(receive);
					}else if("退货单".equals(payKind)){
						Reject reject = new Reject();
						reject.setRejectId(sourceId);
						payDetail.setReject(reject);
					}else if("预付单预付款".equals(payKind)){
						Prepay prepay = new Prepay();
						prepay.setPrepayId(sourceId);
						payDetail.setPrepay(prepay);
					}
					payDetail.setSourceCode(sourceCode);
					payDetail.setSourceDate(sourceDate);
					payDetail.setPayKind(payKind);
					payDetail.setPay(model);
					payDetail.setAmount(new Double(amount));
					payDetail.setPayedAmount(new Double(payedAmount));
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
		String[] propertiesDetail = {"payDetailId","sourceCode","sourceDate","payKind",
				"amount","payedAmount","needPayAmount","discountAmount","payAmount"};
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
			if(oldPay!=null&&oldPay.getStatus().intValue()==0){
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
		 * 2.付款明细对应单据的对账金额
		 */
		if(model.getStatus()==1){//如果是由未审改为已审
			//更新对应银行的账户金额
			Bank oldBank = bankDAO.load(oldPay.getBank().getBankId());
			oldBank.setAmount(oldBank.getAmount()-oldPay.getPayAmount());
			bankDAO.update(oldBank);
			//更新付款明细对应的对账单据的对账金额等
			List<PayDetail> payDetailList = payDetailDAO.queryByPayId(model.getPayId());
			for (PayDetail payDetail : payDetailList) {
				String payKind = payDetail.getPayKind();
				if("采购单预付款".equals(payKind)){
					Buy oldBuy = payDetail.getBuy();
					oldBuy.setCheckAmount(oldBuy.getCheckAmount()-payDetail.getPayAmount()-payDetail.getDiscountAmount());
				}else if("采购入库".equals(payKind)){
					Receive oldReceive = payDetail.getReceive();
					oldReceive.setCheckAmount(oldReceive.getCheckAmount()+payDetail.getPayAmount()+payDetail.getDiscountAmount());
					if(oldReceive.getAmount()-oldReceive.getDiscountAmount()-oldReceive.getPayAmount()-oldReceive.getCheckAmount()<=0){//清款
						oldReceive.setIsPay(1);
					}
				}else if("退货单".equals(payKind)){
					Reject oldReject = payDetail.getReject();
					oldReject.setCheckAmount(oldReject.getCheckAmount()+payDetail.getPayAmount()+payDetail.getDiscountAmount());
				}else if("预付单预付款".equals(payKind)){
					Prepay oldPrepay = payDetail.getPrepay();
					oldPrepay.setCheckAmount(oldPrepay.getCheckAmount()+payDetail.getPayAmount()+payDetail.getDiscountAmount());
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
				String payKind = payDetail.getPayKind();
				if("采购单预付款".equals(payKind)){
					Buy oldBuy = payDetail.getBuy();
					oldBuy.setCheckAmount(oldBuy.getCheckAmount()+payDetail.getPayAmount()+payDetail.getDiscountAmount());
				}else if("采购入库".equals(payKind)){
					Receive oldReceive = payDetail.getReceive();
					oldReceive.setCheckAmount(oldReceive.getCheckAmount()-payDetail.getPayAmount()-payDetail.getDiscountAmount());
				}else if("退货单".equals(payKind)){
					Reject oldReject = payDetail.getReject();
					oldReject.setCheckAmount(oldReject.getCheckAmount()-payDetail.getPayAmount()-payDetail.getDiscountAmount());
				}else if("预付单预付款".equals(payKind)){
					Prepay oldPrepay = payDetail.getPrepay();
					oldPrepay.setCheckAmount(oldPrepay.getCheckAmount()-payDetail.getPayAmount()-payDetail.getDiscountAmount());
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
				 * 2.付款明细对应单据的对账金额
				 */
				if(model.getStatus()==1){//如果是由未审改为已审
					//更新对应银行的账户金额
					Bank oldBank = oldPay.getBank();
					oldBank.setAmount(oldBank.getAmount()-oldPay.getPayAmount());
					//更新采购入库单的isPay字段
					List<PayDetail> payDetailList = payDetailDAO.queryByPayId(oldPay.getPayId());
					for (PayDetail payDetail : payDetailList) {
						String payKind = payDetail.getPayKind();
						if("采购单预付款".equals(payKind)){
							Buy oldBuy = payDetail.getBuy();
							oldBuy.setCheckAmount(oldBuy.getCheckAmount()-payDetail.getPayAmount()-payDetail.getDiscountAmount());
						}else if("采购入库".equals(payKind)){
							Receive oldReceive = payDetail.getReceive();
							oldReceive.setCheckAmount(oldReceive.getCheckAmount()+payDetail.getPayAmount()+payDetail.getDiscountAmount());
							if(oldReceive.getAmount()-oldReceive.getDiscountAmount()-oldReceive.getPayAmount()-oldReceive.getCheckAmount()<=0){//清款
								oldReceive.setIsPay(1);
							}
						}else if("退货单".equals(payKind)){
							Reject oldReject = payDetail.getReject();
							oldReject.setCheckAmount(oldReject.getCheckAmount()-payDetail.getPayAmount()-payDetail.getDiscountAmount());
						}else if("预付单预付款".equals(payKind)){
							Prepay oldPrepay =payDetail.getPrepay();
							oldPrepay.setCheckAmount(oldPrepay.getCheckAmount()-payDetail.getPayAmount()-payDetail.getDiscountAmount());
						}
					}
				}else if(model.getStatus()==0){//如果是由已审改为未审
					//更新对应银行的账户金额
					Bank oldBank = bankDAO.load(oldPay.getBank().getBankId());
					oldBank.setAmount(oldBank.getAmount()+oldPay.getPayAmount());
					bankDAO.update(oldBank);
					//更新采购入库单的isPay字段
					List<PayDetail> payDetailList = payDetailDAO.queryByPayId(oldPay.getPayId());
					for (PayDetail payDetail : payDetailList) {
						String payKind = payDetail.getPayKind();
						if("采购单预付款".equals(payKind)){
							Buy oldBuy = payDetail.getBuy();
							oldBuy.setCheckAmount(oldBuy.getCheckAmount()+payDetail.getPayAmount()+payDetail.getDiscountAmount());
						}else if("采购入库".equals(payKind)){
							Receive oldReceive = payDetail.getReceive();
							oldReceive.setCheckAmount(oldReceive.getCheckAmount()-payDetail.getPayAmount()-payDetail.getDiscountAmount());
						}else if("退货单".equals(payKind)){
							Reject oldReject = payDetail.getReject();
							oldReject.setCheckAmount(oldReject.getCheckAmount()+payDetail.getPayAmount()+payDetail.getDiscountAmount());
						}else if("预付单预付款".equals(payKind)){
							Prepay oldPrepay =payDetail.getPrepay();
							oldPrepay.setCheckAmount(oldPrepay.getCheckAmount()+payDetail.getPayAmount()+payDetail.getDiscountAmount());
						}
					}
				}
				oldPay.setStatus(model.getStatus());
				payDAO.update(oldPay);
				haveUpdateStatus = true;
			}
		}
		if(!haveUpdateStatus){
			result.setMessage("没有可修改状态的付款单");
			return result;
		}
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.PayService#queryNeedCheck(java.lang.String, java.lang.String)
	 */
	@Override
	public ServiceResult queryNeedCheck(String supplierId, String ids, Integer page, Integer rows) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(supplierId)){
			result.setMessage("请选择供应商");
			return result;
		}
		String[] idArray={""};
		if(StringUtils.isNotEmpty(ids)){
			idArray = StringUtil.split(ids);
		}
		List<Map<String,Object>> list = payDAO.queryNeedCheck(supplierId,idArray,page,rows);
		Long total = payDAO.getTotalCountNeedCheck(supplierId,idArray);
		String[] properties = {"sourceId","sourceCode","sourceDate","payKind","amount","payedAmount","needPayAmount",
				"employee.employeeName","note"};
		String data = JSONUtil.toJson(list,properties,total);
		result.addData("datagridData", data);
		
		result.setIsSuccess(true);
		return result;
	}
	
}
