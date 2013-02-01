package org.linys.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.linys.bean.Pager;
import org.linys.dao.CommonDAO;
import org.linys.dao.PrefixDAO;
import org.linys.dao.ReceiptDao;
import org.linys.dao.ReceiptDetailDao;
import org.linys.model.Bank;
import org.linys.model.Deliver;
import org.linys.model.DeliverReject;
import org.linys.model.PreReceipt;
import org.linys.model.Receipt;
import org.linys.model.ReceiptDetail;
import org.linys.model.Sale;
import org.linys.service.ReceiptService;
import org.linys.util.JSONUtil;
import org.linys.util.StringUtil;
import org.linys.vo.ServiceResult;
import org.springframework.stereotype.Service;

/**
 * 
 * @Description: 收款service实现类
 * @Copyright: 福州骏华科技信息有限公司 (c)2013
 * @Created Date : 2013-1-28
 * @author longweier
 * @vesion 1.0
 */
@Service
public class ReceiptServiceImpl extends BaseServiceImpl<Receipt, String> implements ReceiptService {
	@Resource
	private ReceiptDao receiptDao;
	@Resource
	private CommonDAO commonDao;
	@Resource
	private ReceiptDetailDao receiptDetailDao;
	@Resource
	private PrefixDAO prefixDao;
	
	public ServiceResult addReceipt(Receipt receipt, String sourceIds,
			String sourceCodes, String sourceDates, String receiptKinds,
			String amounts, String receiptedAmounts, String discountAmounts,
			String receiptAmounts) {
		ServiceResult result = new ServiceResult(false);
		
		if(receipt.getCustomer()==null||StringUtils.isEmpty(receipt.getCustomer().getCustomerId())){
			result.setMessage("请选择客户");
			return result;
		}
		if(receipt.getReceiptDate()==null){
			result.setMessage("请选择收款日期");
			return result;
		}
		if(receipt.getEmployee()==null||StringUtils.isEmpty(receipt.getEmployee().getEmployeeId())){
			result.setMessage("请选择经办人");
			return result;
		}
		
		if(StringUtils.isEmpty(sourceIds)){
			result.setMessage("请选择收款内容");
			return result;
		}
		String[] sourceIdArray = StringUtil.split(sourceIds);
		String[] sourceCodeArray = StringUtil.split(sourceCodes);
		String[] sourceDateArray = StringUtil.split(sourceDates);
		String[] receiptKindArray = StringUtil.split(receiptKinds);
		String[] amountArray = StringUtil.split(amounts);
		String[] receiptedAmountArray = StringUtil.split(receiptedAmounts);
		String[] discountAmountArray = StringUtil.split(discountAmounts);
		String[] receiptAmountArray = StringUtil.split(receiptAmounts);
		
		for (int i = 0; i < sourceIdArray.length; i++) {
			Double amount = new Double(amountArray[i]!=null?amountArray[i]:"0");
			Double receiptedAmount = new Double(receiptedAmountArray[i]!=null?receiptedAmountArray[i]:"0");
			Double discountAmount = new Double(discountAmountArray[i]!=null?discountAmountArray[i]:"0");
			Double receiptAmount = new Double(receiptAmountArray[i]!=null?receiptAmountArray[i]:"0");
			if(receiptAmount==0){
				result.setMessage("第"+(i+1)+"行本次实收的金额为0,请输入");
				return result;
			}
			if(Math.abs(amount-receiptedAmount)<Math.abs(discountAmount+receiptAmount)){
				result.setMessage("第"+(i+1)+"行本次收款金额大于可收金额");
				return result;
			}
		}
		receipt.setStatus(0);
		receipt.setReceiptCode(commonDao.getCode(Receipt.class.getName(), "receiptCode",prefixDao.getPrefix("receipt")));
		//先保存
		receiptDao.save(receipt);
		
		Double totalDiscountAmount = 0.0;//总共优惠金额
		Double totalReceiptAmount = 0.0;//总共实付金额
		
		for(int i=0;i<sourceIdArray.length;i++){
			String sourceId = sourceIdArray[i];
			String sourceCode = sourceCodeArray[i];
			String sourceDate = sourceDateArray[i];
			String receiptKind = receiptKindArray[i];
			Double amount = new Double(amountArray[i]!=null?amountArray[i]:"0");
			Double receiptedAmount = new Double(receiptedAmountArray[i]!=null?receiptedAmountArray[i]:"0");
			Double discountAmount = new Double(discountAmountArray[i]!=null?discountAmountArray[i]:"0");
			Double receiptAmount = new Double(receiptAmountArray[i]!=null?receiptAmountArray[i]:"0");
			
			ReceiptDetail receiptDetail = new ReceiptDetail();
			
			if("销售出库".equals(receiptKind)){
				Deliver deliver = new Deliver();
				deliver.setDeliverId(sourceId);
				receiptDetail.setDeliver(deliver);
			}else if("订单预收款".equals(receiptKind)){
				Sale sale = new Sale();
				sale.setSaleId(sourceId);
				receiptDetail.setSale(sale);
			}else if("退货单".equals(receiptKind)){
				DeliverReject deliverReject = new DeliverReject();
				deliverReject.setDeliverRejectId(sourceId);
				receiptDetail.setDeliverReject(deliverReject);
			}else if("预收单预收款".equals(receiptKind)){
				PreReceipt preReceipt = new PreReceipt();
				preReceipt.setPreReceiptId(sourceId);
				receiptDetail.setPreReceipt(preReceipt);
			}
			
			receiptDetail.setReceipt(receipt);
			receiptDetail.setSourceCode(sourceCode);
			receiptDetail.setSourceDate(sourceDate);
			receiptDetail.setReceiptKind(receiptKind);
			receiptDetail.setAmount(amount);
			receiptDetail.setReceiptedAmount(receiptedAmount);
			receiptDetail.setDiscountAmount(discountAmount);
			receiptDetail.setReceiptAmount(receiptAmount);
			receiptDetailDao.save(receiptDetail);
			
			totalDiscountAmount +=discountAmount;
			totalReceiptAmount +=receiptAmount;
		}
		receipt.setDiscountAmount(totalDiscountAmount);
		receipt.setReceiptAmount(totalReceiptAmount);
		result.setIsSuccess(true);
		result.addData("receiptId", receipt.getReceiptId());
		
		return result;
	}

	public ServiceResult updateReceipt(Receipt receipt,
			String receiptDetailIds, String delreceiptDetailIds,
			String sourceIds, String sourceCodes, String sourceDates,
			String receiptKinds, String amounts, String receiptedAmounts,
			String discountAmounts, String receiptAmounts) {
		ServiceResult result = new ServiceResult(false);
		
		if(receipt.getCustomer()==null||StringUtils.isEmpty(receipt.getCustomer().getCustomerId())){
			result.setMessage("请选择客户");
			return result;
		}
		if(receipt.getReceiptDate()==null){
			result.setMessage("请选择收款日期");
			return result;
		}
		if(receipt.getEmployee()==null||StringUtils.isEmpty(receipt.getEmployee().getEmployeeId())){
			result.setMessage("请选择经办人");
			return result;
		}
		
		if(StringUtils.isEmpty(receiptKinds)){
			result.setMessage("请选择收款内容");
			return result;
		}
		String[] receiptDetailIdArray = StringUtil.split(receiptDetailIds);
		String[] delreceiptDetailIdIdArray = StringUtil.split(delreceiptDetailIds);
		String[] sourceIdArray = StringUtil.split(sourceIds);
		String[] sourceCodeArray = StringUtil.split(sourceCodes);
		String[] sourceDateArray = StringUtil.split(sourceDates);
		String[] receiptKindArray = StringUtil.split(receiptKinds);
		String[] amountArray = StringUtil.split(amounts);
		String[] receiptedAmountArray = StringUtil.split(receiptedAmounts);
		String[] discountAmountArray = StringUtil.split(discountAmounts);
		String[] receiptAmountArray = StringUtil.split(receiptAmounts);
		
		for(String id : delreceiptDetailIdIdArray){
			if(StringUtils.isEmpty(id)) continue;
			receiptDetailDao.delete(id);
		}
		
		for (int i = 0; i < receiptKindArray.length; i++) {
			Double amount = new Double(amountArray[i]!=null?amountArray[i]:"0");
			Double receiptedAmount = new Double(receiptedAmountArray[i]!=null?receiptedAmountArray[i]:"0");
			Double discountAmount = new Double(discountAmountArray[i]!=null?discountAmountArray[i]:"0");
			Double receiptAmount = new Double(receiptAmountArray[i]!=null?receiptAmountArray[i]:"0");
			if(receiptAmount==0){
				result.setMessage("第"+(i+1)+"行本次实收的金额为0,请输入");
				return result;
			}
			if(Math.abs(amount-receiptedAmount)<Math.abs(discountAmount+receiptAmount)){
				result.setMessage("第"+(i+1)+"行本次收款金额大于可收金额");
				return result;
			}
		}
		Double totalDiscountAmount = 0.0;//总共优惠金额
		Double totalReceiptAmount = 0.0;//总共实付金额
		//先更新
		Receipt model = receiptDao.load(receipt.getReceiptId());
		receipt.setStatus(model.getStatus());
		receiptDao.evict(model);
		receiptDao.update(receipt);
		
		for(int i=0;i<receiptKindArray.length;i++){
			String receiptDetailId = receiptDetailIdArray[i];
			String sourceId = sourceIdArray[i];
			String sourceCode = sourceCodeArray[i];
			String sourceDate = sourceDateArray[i];
			String receiptKind = receiptKindArray[i];
			Double amount = new Double(amountArray[i]!=null?amountArray[i]:"0");
			Double receiptedAmount = new Double(receiptedAmountArray[i]!=null?receiptedAmountArray[i]:"0");
			Double discountAmount = new Double(discountAmountArray[i]!=null?discountAmountArray[i]:"0");
			Double receiptAmount = new Double(receiptAmountArray[i]!=null?receiptAmountArray[i]:"0");
			
			ReceiptDetail receiptDetail = null;
			//新增
			if(StringUtils.isEmpty(receiptDetailId)){
				receiptDetail = new ReceiptDetail();
			}else{
				receiptDetail = receiptDetailDao.load(receiptDetailId);
			}
			if(receiptDetail==null) continue;
			if("销售出库".equals(receiptKind) && StringUtils.isNotEmpty(sourceId)){
				Deliver deliver = new Deliver();
				deliver.setDeliverId(sourceId);
				receiptDetail.setDeliver(deliver);
			}else if("订单预收款".equals(receiptKind) && StringUtils.isNotEmpty(sourceId)){
				Sale sale = new Sale();
				sale.setSaleId(sourceId);
				receiptDetail.setSale(sale);
			}else if("退货单".equals(receiptKind) && StringUtils.isNotEmpty(sourceId)){
				DeliverReject deliverReject = new DeliverReject();
				deliverReject.setDeliverRejectId(sourceId);
				receiptDetail.setDeliverReject(deliverReject);
			}else if("预收单预收款".equals(receiptKind) && StringUtils.isNotEmpty(sourceId)){
				PreReceipt preReceipt = new PreReceipt();
				preReceipt.setPreReceiptId(sourceId);
				receiptDetail.setPreReceipt(preReceipt);
			}
			
			receiptDetail.setReceipt(receipt);
			receiptDetail.setSourceCode(sourceCode);
			receiptDetail.setSourceDate(sourceDate);
			receiptDetail.setReceiptKind(receiptKind);
			receiptDetail.setAmount(amount);
			receiptDetail.setReceiptedAmount(receiptedAmount);
			receiptDetail.setDiscountAmount(discountAmount);
			receiptDetail.setReceiptAmount(receiptAmount);
			
			if(StringUtils.isEmpty(receiptDetailId)){
				receiptDetailDao.save(receiptDetail);
			}
			
			totalDiscountAmount +=discountAmount;
			totalReceiptAmount +=receiptAmount;
		}
		receipt.setDiscountAmount(totalDiscountAmount);
		receipt.setReceiptAmount(totalReceiptAmount);
		result.setIsSuccess(true);
		result.addData("receiptId", receipt.getReceiptId());
		
		return result;
	}
	
	public ServiceResult deleteReceipt(String receiptId) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(receiptId)){
			result.setMessage("参数错误");
			return result;
		}
		Receipt receipt = receiptDao.load(receiptId);
		if(receipt==null){
			result.setMessage("该收款单已被删除");
			return result;
		}
		if(receipt.getStatus()==1){
			result.setMessage("该收款单已审核,不能删除");
			return result;
		}
		receiptDao.delete(receipt);
		result.setIsSuccess(true);
		return result;
	}

	public ServiceResult mulDeleteReceipt(String ids) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(ids)){
			result.setMessage("参数错误");
			return result;
		}
		String[] idArray = StringUtil.split(ids);
		
		for(String receiptId : idArray){
			Receipt receipt = receiptDao.load(receiptId);
			if(receipt==null || receipt.getStatus()==1) continue;
			receiptDao.delete(receipt);
		}
		result.setIsSuccess(true);
		return result;
	}
	
	public ServiceResult updateStatusReceipt(Receipt receipt) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(receipt.getReceiptId()) || receipt.getStatus()==null){
			result.setMessage("参数错误");
			return result;
		}
		Receipt model = receiptDao.load(receipt.getReceiptId());
		if(model==null){
			result.setMessage("该收款单已被删除");
			return result;
		}
		if(receipt.getStatus().intValue()==model.getStatus().intValue()){
			result.setMessage("该收款单已是该状态,不能重复修改");
			return result;
		}
		//由未审核修改为审核
		if(receipt.getStatus()==1){
			List<ReceiptDetail> list = receiptDetailDao.queryReceiptDetail(model);
			for(ReceiptDetail receiptDetail : list){
				if("销售出库".equals(receiptDetail.getReceiptKind())){
					Deliver deliver = receiptDetail.getDeliver();
					deliver.setCheckAmount(deliver.getCheckAmount()+Math.abs(receiptDetail.getReceiptAmount())+ Math.abs(receiptDetail.getDiscountAmount()));
					if(deliver.getAmount()-deliver.getDiscountAmount()-deliver.getReceiptedAmount()-deliver.getCheckAmount()<=0){
						deliver.setIsReceipt(1);
					}
				}else if("订单预收款".equals(receiptDetail.getReceiptKind())){
					Sale sale = receiptDetail.getSale();
					sale.setCheckAmount(sale.getCheckAmount()+Math.abs(receiptDetail.getReceiptAmount())+ Math.abs(receiptDetail.getDiscountAmount()));
				}else if("退货单".equals(receiptDetail.getReceiptKind())){
					DeliverReject deliverReject = receiptDetail.getDeliverReject();
					deliverReject.setCheckAmount(deliverReject.getCheckAmount()+Math.abs(receiptDetail.getReceiptAmount())+ Math.abs(receiptDetail.getDiscountAmount()));
				}else if("预收单预收款".equals(receiptDetail.getReceiptKind())){
					PreReceipt preReceipt = receiptDetail.getPreReceipt();
					preReceipt.setCheckAmount(preReceipt.getCheckAmount()+Math.abs(receiptDetail.getReceiptAmount())+ Math.abs(receiptDetail.getDiscountAmount()));
				}
			}
			Bank bank = model.getBank();
			if(bank!=null){
				bank.setAmount(bank.getAmount()+model.getReceiptAmount());
			}
		//由审核修改为未审核
		}else{
			List<ReceiptDetail> list = receiptDetailDao.queryReceiptDetail(model);
			for(ReceiptDetail receiptDetail : list){
				if("销售出库".equals(receiptDetail.getReceiptKind())){
					Deliver deliver = receiptDetail.getDeliver();
					deliver.setCheckAmount(deliver.getCheckAmount()-Math.abs(receiptDetail.getReceiptAmount())- Math.abs(receiptDetail.getDiscountAmount()));
					deliver.setIsReceipt(0);
				}else if("订单预收款".equals(receiptDetail.getReceiptKind())){
					Sale sale = receiptDetail.getSale();
					sale.setCheckAmount(sale.getCheckAmount()-Math.abs(receiptDetail.getReceiptAmount())- Math.abs(receiptDetail.getDiscountAmount()));
				}else if("退货单".equals(receiptDetail.getReceiptKind())){
					DeliverReject deliverReject = receiptDetail.getDeliverReject();
					deliverReject.setCheckAmount(deliverReject.getCheckAmount()-Math.abs(receiptDetail.getReceiptAmount())- Math.abs(receiptDetail.getDiscountAmount()));
				}else if("预收单预收款".equals(receiptDetail.getReceiptKind())){
					PreReceipt preReceipt = receiptDetail.getPreReceipt();
					preReceipt.setCheckAmount(preReceipt.getCheckAmount()-Math.abs(receiptDetail.getReceiptAmount())- Math.abs(receiptDetail.getDiscountAmount()));
				}
			}
			Bank bank = model.getBank();
			if(bank!=null){
				bank.setAmount(bank.getAmount()-model.getReceiptAmount());
			}
		}
		model.setStatus(receipt.getStatus());
		result.setIsSuccess(true);
		return result;
	}
	
	public ServiceResult mulUpdateStatusReceipt(String ids, Integer status) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(ids) || status==null){
			result.setMessage("参数错误");
			return result;
		}
		String[] idArray = StringUtil.split(ids);
		
		for(String receiptId : idArray){
			Receipt model = receiptDao.load(receiptId);
			if(model==null || status.intValue()==model.getStatus().intValue()) continue;
			//由未审核修改为审核
			if(status==1){
				List<ReceiptDetail> list = receiptDetailDao.queryReceiptDetail(model);
				for(ReceiptDetail receiptDetail : list){
					if("销售出库".equals(receiptDetail.getReceiptKind())){
						Deliver deliver = receiptDetail.getDeliver();
						deliver.setCheckAmount(deliver.getCheckAmount()+Math.abs(receiptDetail.getReceiptAmount())+ Math.abs(receiptDetail.getDiscountAmount()));
						if(deliver.getAmount()-deliver.getDiscountAmount()-deliver.getReceiptedAmount()-deliver.getCheckAmount()<=0){
							deliver.setIsReceipt(1);
						}
					}else if("订单预收款".equals(receiptDetail.getReceiptKind())){
						Sale sale = receiptDetail.getSale();
						sale.setCheckAmount(sale.getCheckAmount()+Math.abs(receiptDetail.getReceiptAmount())+ Math.abs(receiptDetail.getDiscountAmount()));
					}else if("退货单".equals(receiptDetail.getReceiptKind())){
						DeliverReject deliverReject = receiptDetail.getDeliverReject();
						deliverReject.setCheckAmount(deliverReject.getCheckAmount()+Math.abs(receiptDetail.getReceiptAmount())+ Math.abs(receiptDetail.getDiscountAmount()));
					}else if("预收单预收款".equals(receiptDetail.getReceiptKind())){
						PreReceipt preReceipt = receiptDetail.getPreReceipt();
						preReceipt.setCheckAmount(preReceipt.getCheckAmount()+Math.abs(receiptDetail.getReceiptAmount())+ Math.abs(receiptDetail.getDiscountAmount()));
					}
				}
				Bank bank = model.getBank();
				if(bank!=null){
					bank.setAmount(bank.getAmount()+model.getReceiptAmount());
				}
			//由审核修改为未审核
			}else{
				List<ReceiptDetail> list = receiptDetailDao.queryReceiptDetail(model);
				for(ReceiptDetail receiptDetail : list){
					if("销售出库".equals(receiptDetail.getReceiptKind())){
						Deliver deliver = receiptDetail.getDeliver();
						deliver.setCheckAmount(deliver.getCheckAmount()-Math.abs(receiptDetail.getReceiptAmount())- Math.abs(receiptDetail.getDiscountAmount()));
						deliver.setIsReceipt(0);
					}else if("订单预收款".equals(receiptDetail.getReceiptKind())){
						Sale sale = receiptDetail.getSale();
						sale.setCheckAmount(sale.getCheckAmount()-Math.abs(receiptDetail.getReceiptAmount())- Math.abs(receiptDetail.getDiscountAmount()));
					}else if("退货单".equals(receiptDetail.getReceiptKind())){
						DeliverReject deliverReject = receiptDetail.getDeliverReject();
						deliverReject.setCheckAmount(deliverReject.getCheckAmount()-Math.abs(receiptDetail.getReceiptAmount())- Math.abs(receiptDetail.getDiscountAmount()));
					}else if("预收单预收款".equals(receiptDetail.getReceiptKind())){
						PreReceipt preReceipt = receiptDetail.getPreReceipt();
						preReceipt.setCheckAmount(preReceipt.getCheckAmount()-Math.abs(receiptDetail.getReceiptAmount())- Math.abs(receiptDetail.getDiscountAmount()));
					}
				}
				Bank bank = model.getBank();
				if(bank!=null){
					bank.setAmount(bank.getAmount()-model.getReceiptAmount());
				}
			}
			model.setStatus(status);
		}
		result.setIsSuccess(true);
		return result;
	}

	public String initReceipt(String receiptId) {
		ServiceResult result = new ServiceResult(false);
		Receipt receipt = receiptDao.load(receiptId);
		String[] propertiesPay = {"receiptId","receiptCode","receiptDate","customer.customerId",
				"customer.customerName","amount","discountAmount","receiptAmount","receiptway",
				"bank.bankId","employee.employeeId","note","status"};
		String receiptData = JSONUtil.toJson(receipt,propertiesPay);
		result.addData("receiptData",receiptData);
		
		List<ReceiptDetail> list = receiptDetailDao.queryReceiptDetail(receipt);
		String[] propertiesDetail = {"receiptDetailId","sourceCode","sourceDate","receiptKind",
				"amount","receiptedAmount","needReceiptAmount","discountAmount","receiptAmount"};
		String detailData = JSONUtil.toJson(list,propertiesDetail);
		result.addData("detailData", detailData);
		result.setIsSuccess(true);
		return result.toJSON();
	}
	
	public String queryReceipt(Integer pageNumber, Integer pageSize,Receipt receipt, Date beginDate, Date endDate) {
		Pager pager = new Pager(pageNumber, pageSize);
		pager = receiptDao.queryReceipt(pager, receipt, beginDate, endDate);
		
		String[] properties = {"receiptId","receiptCode","receiptDate","status","customer.customerName","amount","receiptAmount","discountAmount",
				"employee.employeeName","note"};
		
		String jsonArray = JSONUtil.toJson(pager.getList(), properties, pager.getTotalCount());
		return jsonArray;
	}

	@SuppressWarnings("unchecked")
	public String queryNeedReceipt(Integer pageNumber, Integer pageSize,Receipt receipt, String ids) {
		if(receipt.getCustomer()==null || StringUtils.isEmpty(receipt.getCustomer().getCustomerId())){
			return JSONUtil.EMPTYJSON;
		}
		String[] idArray={""};
		if(StringUtils.isNotEmpty(ids)){
			idArray = StringUtil.split(ids);
		}
		Pager pager = new Pager(pageNumber, pageSize);
		pager = receiptDao.queryNeedCheck(pager, receipt.getCustomer(), idArray);
		String jsonArray = JSONUtil.toJsonFromListMap(pager.getList(), pager.getTotalCount());
		
		return jsonArray;
	}

}
