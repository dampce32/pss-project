package org.linys.service.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.linys.dao.BankDAO;
import org.linys.dao.BuyDAO;
import org.linys.dao.BuyDetailDAO;
import org.linys.dao.CommonDAO;
import org.linys.dao.PrefixDAO;
import org.linys.model.Bank;
import org.linys.model.Buy;
import org.linys.model.BuyDetail;
import org.linys.model.DataDictionary;
import org.linys.model.Product;
import org.linys.service.BuyService;
import org.linys.util.DateUtil;
import org.linys.util.JSONUtil;
import org.linys.util.StringUtil;
import org.linys.vo.GobelConstants;
import org.linys.vo.ServiceResult;
import org.springframework.stereotype.Service;

@Service
public class BuyServiceImpl extends BaseServiceImpl<Buy, String>
		implements BuyService {
	@Resource
	private BuyDAO buyDAO;
	@Resource
	private BuyDetailDAO buyDetailDAO;
	@Resource
	private BankDAO bankDAO;
	@Resource
	private CommonDAO commonDAO;
	@Resource
	private PrefixDAO prefixDAO;
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.BuyService#query(org.linys.model.Buy, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public ServiceResult query(Buy model, Integer page, Integer rows) {
		ServiceResult result = new ServiceResult(false);
		
		List<Buy> list = buyDAO.query(model,page,rows);
		
		String[] properties = {"buyId","buyCode","buyDate","sourceCode","receiveDate",
				"supplier.supplierName","amount","payAmount","otherAmount",
				"employee.employeeName","note","status","invoiceType.invoiceTypeName"};
		String data = JSONUtil.toJson(list,properties);
		result.addData("datagridData", data);
		
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.BuyService#getTotalCount(org.linys.model.Buy)
	 */
	@Override
	public ServiceResult getTotalCount(Buy model) {
		ServiceResult result = new ServiceResult(false);
		Long data = buyDAO.getTotalCount(model);
		result.addData("total", data);
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.BuyService#save(org.linys.model.Buy, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public ServiceResult save(Buy model, String buyDetailIds,
			String delBuyDetailIds, String productIds, String colorIds,
			String qtys, String prices, String note1s, String note2s,
			String note3s) {
		ServiceResult result = new ServiceResult(false);
		if(model==null){
			result.setMessage("请填写采购单信息");
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
		if(model.getInvoiceType()==null||StringUtils.isEmpty(model.getInvoiceType().getInvoiceTypeId())){
			result.setMessage("请选择发票");
			return result;
		}
		if(model.getBuyDate()==null){
			result.setMessage("请选择采购日期");
			return result;
		}
		if(model.getReceiveDate()==null){
			result.setMessage("请选择收货日期");
			return result;
		}
		if(model.getEmployee()==null||StringUtils.isEmpty(model.getEmployee().getEmployeeId())){
			result.setMessage("请选择经办人");
			return result;
		}
		if(StringUtils.isEmpty(productIds)){
			result.setMessage("请选择商品");
			return result;
		}
		String[] productIdArray = StringUtil.split(productIds, GobelConstants.SPLIT_SEPARATOR);
		String[] delBuyDetailIdArray = StringUtil.split(delBuyDetailIds, GobelConstants.SPLIT_SEPARATOR);
		String[] buyDetailIdArray = StringUtil.split(buyDetailIds, GobelConstants.SPLIT_SEPARATOR);
		String[] colorIdArray = StringUtil.split(colorIds, GobelConstants.SPLIT_SEPARATOR);
		String[] qtyArray = StringUtil.split(qtys, GobelConstants.SPLIT_SEPARATOR);
		String[] priceArray = StringUtil.split(prices, GobelConstants.SPLIT_SEPARATOR);
		String[] note1Array = StringUtil.split(note1s, GobelConstants.SPLIT_SEPARATOR);
		String[] note2Array = StringUtil.split(note2s, GobelConstants.SPLIT_SEPARATOR);
		String[] note3Array = StringUtil.split(note3s, GobelConstants.SPLIT_SEPARATOR);
		if(productIdArray==null||productIdArray.length==0){
			result.setMessage("请选择商品");
			return result;
		}
		for (int i = 0; i < qtyArray.length; i++) {
			String qty = qtyArray[i];
			if("0".equals(qty)){
				result.setMessage("第"+(i+1)+"行商品数量不能为0");
				return result;
			}
			
		}
		if(StringUtils.isEmpty(model.getBuyId())){//新增
			//取得入库单号
			model.setBuyCode(commonDAO.getCode("Buy", "buyCode", prefixDAO.getPrefix("buy")));
			
			model.setStatus(0);
			buyDAO.save(model);
			for (int i = 0; i < productIdArray.length; i++) {
				String productId = productIdArray[i];
				String colorId = colorIdArray[i];
				String qty = qtyArray[i];
				String price = priceArray[i];
				String note1 = note1Array[i];
				String note2 = note2Array[i];
				String note3 = note3Array[i];
				
				BuyDetail buyDetail = new BuyDetail();
				
				Product product = new Product();
				product.setProductId(productId);
				if(StringUtils.isNotEmpty(colorId)){
					DataDictionary color = new DataDictionary();
					color.setDataDictionaryId(colorId);
					buyDetail.setColor(color);
				}
				
				buyDetail.setBuy(model);
				buyDetail.setProduct(product);
				buyDetail.setQty(new Double(qty));
				buyDetail.setPrice(new Double(price));
				buyDetail.setNote1(note1);
				buyDetail.setNote2(note2);
				buyDetail.setNote3(note3);
				buyDetail.setReceiveQty(0.0);
				buyDetailDAO.save(buyDetail);
			}
		}else{
			//更新采购单
			Buy oldBuy = buyDAO.load(model.getBuyId());
			if(oldBuy==null){
				result.setMessage("要更新的采购单已不存在");
				return result;
			}else{
				oldBuy.setSourceCode(model.getSourceCode());
				oldBuy.setSupplier(model.getSupplier());
				oldBuy.setBuyDate(model.getBuyDate());
				oldBuy.setReceiveDate(model.getReceiveDate());
				oldBuy.setPayAmount(model.getPayAmount());
				oldBuy.setAmount(model.getAmount());
				oldBuy.setBank(model.getBank());
				oldBuy.setInvoiceType(model.getInvoiceType());
				oldBuy.setEmployee(model.getEmployee());
				oldBuy.setNote(model.getNote());
				buyDAO.update(oldBuy);
			}
			
			//删除已删的采购单明细
			if(!"".equals(delBuyDetailIds)){
				for (String delBuyDetailId : delBuyDetailIdArray) {
					BuyDetail oldModel = buyDetailDAO.load(delBuyDetailId);
					if(oldModel!=null){
						buyDetailDAO.delete(oldModel);
					}
				}
			}
			//根据采购单明细Id更新或新增
			for (int i = 0 ;i<buyDetailIdArray.length;i++) {
				String buyDetailId = buyDetailIdArray[i];
				String productId = productIdArray[i];
				String colorId = colorIdArray[i];
				String qty = qtyArray[i];
				String price = priceArray[i];
				String note1 = note1Array[i];
				String note2 = note2Array[i];
				String note3 = note3Array[i];
				if(StringUtils.isEmpty(buyDetailId)){//新增
						Product product = new Product();
						product.setProductId(productId);
						
						BuyDetail buyDetail = new BuyDetail();
						buyDetail.setBuy(model);
						buyDetail.setProduct(product);
						if(StringUtils.isNotEmpty(colorId)){
							DataDictionary color = new DataDictionary();
							color.setDataDictionaryId(colorId);
							buyDetail.setColor(color);
						}
						buyDetail.setQty(new Double(qty));
						buyDetail.setPrice(new Double(price));
						buyDetail.setNote1(note1);
						buyDetail.setNote2(note2);
						buyDetail.setNote3(note3);
						buyDetail.setReceiveQty(0d);
						buyDetailDAO.save(buyDetail);
				}else{
					BuyDetail oldModel = buyDetailDAO.load(buyDetailId);
					
					DataDictionary color = new DataDictionary();
					color.setDataDictionaryId(colorId);
					
					oldModel.setColor(color);
					oldModel.setQty(new Double(qty));
					oldModel.setPrice(new Double(price));
					oldModel.setNote1(note1);
					oldModel.setNote2(note2);
					oldModel.setNote3(note3);
					buyDetailDAO.update(oldModel);
				}
			}
		}
		//返回值
		result.addData("buyId", model.getBuyId());
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.BuyService#init(java.lang.String)
	 */
	@Override
	public ServiceResult init(String buyId) {
		ServiceResult result = new ServiceResult(false);
		Buy buy = buyDAO.init(buyId);
		String[] propertiesBuy = {"buyId","buyCode","buyDate","sourceCode","receiveDate",
				"supplier.supplierId","amount","payAmount","otherAmount",
				"bank.bankId","invoiceType.invoiceTypeId","employee.employeeId","note","status"};
		String buyData = JSONUtil.toJson(buy,propertiesBuy);
		result.addData("buyData",buyData);
		
		List<BuyDetail> buyDetailList = buyDetailDAO.queryByBuyId(buyId);
		String[] propertiesDetail = {"buyDetailId","product.productId","product.productCode","product.productName",
				"product.size.dataDictionaryName:sizeName","product.unit.dataDictionaryName:unitName","color.dataDictionaryName:colorName","color.dataDictionaryId:colorId",
				"qty","price","amount","note1","note2","note3","receiveQty"};
		String detailData = JSONUtil.toJson(buyDetailList,propertiesDetail);
		result.addData("detailData", detailData);
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.BuyService#mulDel(java.lang.String)
	 */
	@Override
	public ServiceResult mulDel(String ids) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(ids)){
			result.setMessage("请选择要删除的采购单");
			return result;
		}
		String[] idArray =StringUtil.split(ids,GobelConstants.SPLIT_SEPARATOR);
		if(idArray.length==0){
			result.setMessage("请选择要删除的采购单");
			return result;
		}
		boolean haveDel = false;
		for (String id : idArray) {
			Buy oldBuy = buyDAO.load(id);
			if(oldBuy!=null&&oldBuy.getStatus()==0){
				buyDAO.delete(oldBuy);
				haveDel = true;
			}
		}
		if(!haveDel){
			result.setMessage("没有可删除的采购单");
			return result;
		}
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.BuyService#mulUpdateShzt(java.lang.String, org.linys.model.Buy)
	 */
	@Override
	public ServiceResult mulUpdateStatus(String ids, Buy model) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(ids)){
			result.setMessage("请选择要修改审核状态的采购单");
			return result;
		}
		String[] idArray =StringUtil.split(ids,GobelConstants.SPLIT_SEPARATOR);
		if(idArray.length==0){
			result.setMessage("请选择要修改审核状态的采购单");
			return result;
		}
		if(model==null||model.getStatus()==null){
			result.setMessage("请选择要修改成的审核状态");
			return result;
		}
		boolean haveUpdateShzt = false;
		for (String id : idArray) {
			Buy oldBuy = buyDAO.load(id);
			Bank oldBank = bankDAO.load(oldBuy.getBank().getBankId());
			if(oldBuy!=null&&oldBuy.getStatus().intValue()!=model.getStatus().intValue()){
				if(model.getStatus()==1){//如果是由未审改为已审
					oldBank.setAmount(oldBank.getAmount()-oldBuy.getPayAmount());
				}else if(model.getStatus()==0){//如果是由已审改为未审
					oldBank.setAmount(oldBank.getAmount()+oldBuy.getPayAmount());
				}
				bankDAO.update(oldBank);
				oldBuy.setStatus(model.getStatus());
				buyDAO.update(oldBuy);
				haveUpdateShzt = true;
			}
		}
		if(!haveUpdateShzt){
			result.setMessage("没有可修改审核状态的采购单");
			return result;
		}
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.BuyService#delete(org.linys.model.Buy)
	 */
	@Override
	public ServiceResult delete(Buy model) {
		ServiceResult result = new ServiceResult(false);
		if(model==null||StringUtils.isEmpty(model.getBuyId())){
			result.setMessage("请选择要删除的采购单");
			return result;
		}
		Buy oldModel = buyDAO.load(model.getBuyId());
		if(oldModel==null||oldModel.getStatus().intValue()==1){
			result.setMessage("要删除的采购单已不存在");
			return result;
		}
		if(oldModel.getStatus().intValue()==1){
			result.setMessage("要删除的采购单已审核通过已不能删除");
			return result;
		}
		buyDAO.delete(oldModel);
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.BuyService#updateStatus(org.linys.model.Buy)
	 */
	@Override
	public ServiceResult updateStatus(Buy model) {
		ServiceResult result = new ServiceResult(false);
		if(model==null||StringUtils.isEmpty(model.getBuyId())){
			result.setMessage("请选择要更新审核状态的采购单");
			return result;
		}
		Buy oldBuy = buyDAO.load(model.getBuyId());
		Bank oldBank = bankDAO.load(oldBuy.getBank().getBankId());
		if(oldBuy!=null&&oldBuy.getStatus().intValue()!=model.getStatus().intValue()){
			if(model.getStatus()==1){//如果是由未审改为已审
				oldBank.setAmount(oldBank.getAmount()-oldBuy.getPayAmount());
			}else if(model.getStatus()==0){//如果是由已审改为未审
				oldBank.setAmount(oldBank.getAmount()+oldBuy.getPayAmount());
			}
			bankDAO.update(oldBank);
			oldBuy.setStatus(model.getStatus());
			buyDAO.update(oldBuy);
		}
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.BuyService#queryReceive(java.lang.String, java.lang.String, java.lang.String, java.lang.String, org.linys.model.Buy, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public ServiceResult queryReceive(String beginDate, String endDate,
			String supplierId, String ids, Buy model, Integer page, Integer rows) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(beginDate)){
			result.setMessage("请选择开始日期");
			return result;
		}
		Date beginDateDate = null;
		try {
			beginDateDate = DateUtil.toDate(beginDate);
		} catch (ParseException e) {
			result.setMessage("请输入正确的开始日期");
			return result;
		}
		Date endDateDate = null;
		try {
			endDateDate = DateUtil.toDate(endDate);
		} catch (ParseException e) {
			result.setMessage("请输入正确的结束日期");
			return result;
		}
		if(StringUtils.isEmpty(supplierId)){
			result.setMessage("请选择供应商");
			return result;
		}
		String[] idArray = {""};
		if(StringUtils.isNotEmpty(ids)){
			idArray = StringUtil.split(ids);
		}
		List<Map<String,Object>> listMap = buyDAO.queryReceive(beginDateDate,endDateDate,supplierId,idArray,
				model,page,rows);
		
		Long total = buyDAO.getTotalReceive(beginDateDate,endDateDate,supplierId,idArray,
				model);
		String datagridData = JSONUtil.toJsonFromListMap(listMap, total);
		result.addData("datagridData", datagridData); 
		result.setIsSuccess(true);
		return result;
	}
}
