package org.linys.service.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.linys.dao.BankDAO;
import org.linys.dao.BuyDetailDAO;
import org.linys.dao.CommonDAO;
import org.linys.dao.PrefixDAO;
import org.linys.dao.ProductDAO;
import org.linys.dao.ReceiveDAO;
import org.linys.dao.ReceiveDetailDAO;
import org.linys.dao.StoreDAO;
import org.linys.dao.SystemConfigDAO;
import org.linys.model.Bank;
import org.linys.model.BuyDetail;
import org.linys.model.DataDictionary;
import org.linys.model.Product;
import org.linys.model.Receive;
import org.linys.model.ReceiveDetail;
import org.linys.model.Store;
import org.linys.model.SystemConfig;
import org.linys.service.ReceiveService;
import org.linys.util.DateUtil;
import org.linys.util.JSONUtil;
import org.linys.util.StringUtil;
import org.linys.vo.GobelConstants;
import org.linys.vo.ServiceResult;
import org.springframework.stereotype.Service;
/**
 * @Description:采购入库Service实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-8-18
 * @author lys
 * @vesion 1.0
 */
@Service
public class ReceiveServiceImpl extends BaseServiceImpl<Receive, String>
		implements ReceiveService {
	@Resource
	private ReceiveDAO receiveDAO;
	@Resource
	private ReceiveDetailDAO receiveDetailDAO;
	@Resource
	private StoreDAO storeDAO;
	@Resource
	private ProductDAO productDAO;
	@Resource
	private BankDAO bankDAO;
	@Resource
	private CommonDAO commonDAO;
	@Resource
	private BuyDetailDAO buyDetailDAO;
	@Resource
	private PrefixDAO prefixDAO;
	@Resource
	private SystemConfigDAO systemConfigDAO;
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.ReceiveService#query(org.linys.model.Receive, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public ServiceResult query(String kind,Receive model, Integer page, Integer rows, String beginDate, String endDate) {
		ServiceResult result = new ServiceResult(false);
		try {
			Date beginDateDate = null;
			Date endDateDate = null;
			if(StringUtils.isNotEmpty(beginDate)){
				beginDateDate = DateUtil.toDate(beginDate);
			}
			if(StringUtils.isNotEmpty(endDate)){
				endDateDate = DateUtil.toDate(endDate);
			}
			
			List<Receive> list = receiveDAO.query(kind,model,page,rows,beginDateDate,endDateDate);
			
			String[] properties = {"receiveId","receiveCode","receiveDate","deliverCode",
					"warehouse.warehouseName","supplier.supplierName","amount","payAmount","discountAmount","checkAmount","otherAmount",
					"employee.employeeName","note","status","invoiceType.invoiceTypeName","isPay"};
			String data = JSONUtil.toJson(list,properties);
			result.addData("datagridData", data);
		} catch (ParseException e) {
			throw new RuntimeException("时间转化错误");
		}
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.ReceiveService#getTotalCount(org.linys.model.Receive)
	 */
	@Override
	public ServiceResult getTotalCount(String kind,Receive model, String beginDate, String endDate) {
		ServiceResult result = new ServiceResult(false);
		try {
			Date beginDateDate = null;
			Date endDateDate = null;
			if(StringUtils.isNotEmpty(beginDate)){
				beginDateDate = DateUtil.toDate(beginDate);
			}
			if(StringUtils.isNotEmpty(endDate)){
				endDateDate = DateUtil.toDate(endDate);
			}
			Long data = receiveDAO.getTotalCount(kind,model,beginDateDate,endDateDate);
			result.addData("total", data);
		} catch (ParseException e) {
			throw new RuntimeException("时间转化错误");
		}
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.ReceiveService#save(org.linys.model.Receive, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public ServiceResult save(String kind,Receive model, String receiveDetailIds,String buyDetailIds,
			String delReceiveDetailIds, String productIds, String colorIds,
			String qtys, String prices, String note1s, String note2s,
			String note3s) {
		ServiceResult result = new ServiceResult(false);
		if(model==null){
			result.setMessage("请填写入库单信息");
			return result;
		}
		
		if(!"other".equals(kind)){
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
		}
		
		if(model.getWarehouse()==null||StringUtils.isEmpty(model.getWarehouse().getWarehouseId())){
			result.setMessage("请选择仓库");
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
		String[] productIdArray = StringUtil.split(productIds);
		String[] delReceiveDetailIdArray = StringUtil.split(delReceiveDetailIds);
		String[] receiveDetailIdArray = StringUtil.split(receiveDetailIds);
		String[] buyDetailIdArray = StringUtil.split(buyDetailIds);
		String[] colorIdArray = StringUtil.split(colorIds);
		String[] qtyArray = StringUtil.split(qtys);
		String[] priceArray = StringUtil.split(prices);
		String[] note1Array = StringUtil.split(note1s);
		String[] note2Array = StringUtil.split(note2s);
		String[] note3Array = StringUtil.split(note3s);
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
		if(StringUtils.isEmpty(model.getReceiveId())){//新增
			//取得入库单号
			model.setCheckAmount(0.0);
			model.setStatus(0);
			model.setIsPay(0);
			if(!"other".equals(kind)){
				model.setReceiveCode(commonDAO.getCode("Receive", "receiveCode",prefixDAO.getPrefix("receive")));
			}else{
				model.setReceiveCode(commonDAO.getCode("Receive", "receiveCode",prefixDAO.getPrefix("receiveOther")));
			}
			receiveDAO.save(model);
			for (int i = 0; i < productIdArray.length; i++) {
				String productId = productIdArray[i];
				String colorId = colorIdArray[i];
				String buyDetailId = buyDetailIdArray[i];
				String qty = qtyArray[i];
				String price = priceArray[i];
				String note1 = note1Array[i];
				String note2 = note2Array[i];
				String note3 = note3Array[i];
				
				Product product = new Product();
				product.setProductId(productId);
				
				ReceiveDetail receiveDetail = new ReceiveDetail();
				receiveDetail.setReceive(model);
				receiveDetail.setProduct(product);
				if(StringUtils.isNotEmpty(colorId)){
					DataDictionary color = new DataDictionary();
					color.setDataDictionaryId(colorId);
					receiveDetail.setColor(color); 
				}
				if(StringUtils.isNotEmpty(qty)){//数量没输入保护
					receiveDetail.setQty(new Double(qty));
				}else{
					receiveDetail.setQty(0.0);
				}
				if(StringUtils.isNotEmpty(price)){//单价没输入保护
					receiveDetail.setPrice(new Double(price));
				}else{
					receiveDetail.setPrice(0.0);
				}
				receiveDetail.setNote1(note1);
				receiveDetail.setNote2(note2);
				receiveDetail.setNote3(note3);
				if(StringUtils.isNotEmpty(buyDetailId)){
					BuyDetail buyDetail = new BuyDetail();
					buyDetail.setBuyDetailId(buyDetailId);
					receiveDetail.setBuyDetail(buyDetail);
				}
				receiveDetailDAO.save(receiveDetail);
			}
		}else{
			//更新入库单
			Receive oldReceive = receiveDAO.load(model.getReceiveId());
			if(oldReceive==null){
				result.setMessage("要更新的入库单已不存在");
				return result;
			}
			if(oldReceive.getStatus()==1){
				result.setMessage("要更新的入库单已审核已不能修改");
				return result;
			}
			oldReceive.setDeliverCode(model.getDeliverCode());
			oldReceive.setSupplier(model.getSupplier());
			oldReceive.setWarehouse(model.getWarehouse());
			oldReceive.setReceiveDate(model.getReceiveDate());
			oldReceive.setDiscountAmount(model.getDiscountAmount());
			oldReceive.setOtherAmount(model.getOtherAmount());
			oldReceive.setPayAmount(model.getPayAmount());
			oldReceive.setAmount(model.getAmount());
			oldReceive.setBank(model.getBank());
			oldReceive.setInvoiceType(model.getInvoiceType());
			oldReceive.setEmployee(model.getEmployee());
			oldReceive.setNote(model.getNote());
			receiveDAO.update(oldReceive);
			
			//删除已删的入库单明细
			if(!"".equals(delReceiveDetailIds)){
				for (String delReceiveDetailId : delReceiveDetailIdArray) {
					ReceiveDetail oldModel = receiveDetailDAO.load(delReceiveDetailId);
					if(oldModel!=null){
						receiveDetailDAO.delete(oldModel);
					}
				}
			}
			//根据入库单明细Id更新或新增
			for (int i = 0 ;i<receiveDetailIdArray.length;i++) {
				String receiveDetailId = receiveDetailIdArray[i];
				String buyDetailId = buyDetailIdArray[i];
				String productId = productIdArray[i];
				String colorId = colorIdArray[i];
				String qty = qtyArray[i];
				String price = priceArray[i];
				String note1 = note1Array[i];
				String note2 = note2Array[i];
				String note3 = note3Array[i];
				if(StringUtils.isEmpty(receiveDetailId)){//新增
						Product product = new Product();
						product.setProductId(productId);
						
						ReceiveDetail receiveDetail = new ReceiveDetail();
						receiveDetail.setReceive(model);
						receiveDetail.setProduct(product);
						if(StringUtils.isNotEmpty(colorId)){
							DataDictionary color = new DataDictionary();
							color.setDataDictionaryId(colorId);
							receiveDetail.setColor(color);
						}
						if(StringUtils.isNotEmpty(qty)){//数量没输入保护
							receiveDetail.setQty(new Double(qty));
						}else{
							receiveDetail.setQty(0.0);
						}
						if(StringUtils.isNotEmpty(price)){//单价没输入保护
							receiveDetail.setPrice(new Double(price));
						}else{
							receiveDetail.setPrice(0.0);
						}
						receiveDetail.setNote1(note1);
						receiveDetail.setNote2(note2);
						receiveDetail.setNote3(note3);
						if(StringUtils.isNotEmpty(buyDetailId)){
							BuyDetail buyDetail = new BuyDetail();
							buyDetail.setBuyDetailId(buyDetailId);
							receiveDetail.setBuyDetail(buyDetail);
						}
						receiveDetailDAO.save(receiveDetail);
				}else{
					ReceiveDetail oldModel = receiveDetailDAO.load(receiveDetailId);
					
					if(StringUtils.isNotEmpty(colorId)){
						DataDictionary color = new DataDictionary();
						color.setDataDictionaryId(colorId);
						oldModel.setColor(color);
					}
					if(StringUtils.isNotEmpty(qty)){//数量没输入保护
						oldModel.setQty(new Double(qty));
					}else{
						oldModel.setQty(0.0);
					}
					if(StringUtils.isNotEmpty(price)){//单价没输入保护
						oldModel.setPrice(new Double(price));
					}else{
						oldModel.setPrice(0.0);
					}
					oldModel.setNote1(note1);
					oldModel.setNote2(note2);
					oldModel.setNote3(note3);
					receiveDetailDAO.update(oldModel);
				}
			}
		}
		//返回值
		result.addData("receiveId", model.getReceiveId());
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.ReceiveService#init(java.lang.String)
	 */
	@Override
	public ServiceResult init(String receiveId) {
		ServiceResult result = new ServiceResult(false);
		Receive receive = receiveDAO.load(receiveId);
		String[] propertiesReceive = {"receiveId","receiveCode","receiveDate","deliverCode",
				"supplier.supplierId","supplier.supplierName","warehouse.warehouseId","amount","discountAmount","payAmount","otherAmount",
				"bank.bankId","invoiceType.invoiceTypeId","employee.employeeId","note","status","isPay"};
		String receiveData = JSONUtil.toJson(receive,propertiesReceive);
		result.addData("receiveData",receiveData);
		
		List<ReceiveDetail> receiveDetailList = receiveDetailDAO.queryByReceiveId(receiveId);
		String[] propertiesDetail = {"receiveDetailId","product.productId","product.productCode","product.productName",
				"product.size.dataDictionaryName:sizeName","product.unit.dataDictionaryName:unitName","color.dataDictionaryName:colorName","color.dataDictionaryId:colorId",
				"qty","price","amount","note1","note2","note3","buyDetail.buyDetailId","buyDetail.buy.buyCode"};
		String detailData = JSONUtil.toJson(receiveDetailList,propertiesDetail);
		result.addData("detailData", detailData);
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.ReceiveService#delete(org.linys.model.Receive)
	 */
	@Override
	public ServiceResult delete(Receive model) {
		ServiceResult result = new ServiceResult(false);
		if(model==null||StringUtils.isEmpty(model.getReceiveId())){
			result.setMessage("请选择要删除的入库单");
			return result;
		}
		Receive oldReceive = receiveDAO.load(model.getReceiveId());
		
		if(oldReceive==null){
			result.setMessage("要删除的入库单已不存在");
			return result;
		}
		if(oldReceive.getStatus().intValue()==1){
			result.setMessage("要删除的入库单已审核通过已不能删除");
			return result;
		}
		receiveDAO.delete(oldReceive);
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.ReceiveService#mulDel(java.lang.String)
	 */
	@Override
	public ServiceResult mulDelete(String ids) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(ids)){
			result.setMessage("请选择要删除的入库单");
			return result;
		}
		String[] idArray =StringUtil.split(ids,GobelConstants.SPLIT_SEPARATOR);
		if(idArray.length==0){
			result.setMessage("请选择要删除的入库单");
			return result;
		}
		boolean haveDel = false;
		for (String id : idArray) {
			Receive oldReceive = receiveDAO.load(id);
			if(oldReceive!=null&&oldReceive.getStatus()==0){
				receiveDAO.delete(oldReceive);
				haveDel = true;
			}
		}
		if(!haveDel){
			result.setMessage("没有可删除的入库单");
			return result;
		}
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.ReceiveService#updateStatus(org.linys.model.Receive)
	 */
	@Override
	public ServiceResult updateStatus(String kind,Receive model) {
		ServiceResult result = new ServiceResult(false);
		if(model==null||StringUtils.isEmpty(model.getReceiveId())){
			result.setMessage("请选择要修改审核状态的入库单");
			return result;
		}
		Receive oldReceive = receiveDAO.load(model.getReceiveId());
		
		if(oldReceive==null){
			result.setMessage("要修改审核状态的入库单已不存在");
			return result;
		}
		if(oldReceive.getStatus().intValue()==model.getStatus().intValue()){
			result.setMessage("要修改审核状态的入库单已是要修改的状态，请刷新界面");
			return result;
		}
		
		if(model.getStatus()==1){//如果是由未审改为已审
			//将该入库单下的商品入库
			List<ReceiveDetail> receiveDetailList = receiveDetailDAO.queryByReceiveId(model.getReceiveId());
			//将对应商品入库
			for (ReceiveDetail receiveDetail : receiveDetailList) {
				/*
				 * 查找商品在仓库中是否已有记录，如果有则修改库存数量和库存金额，	
				 * 如果没有，则修改新增库存记录
				*/
				String[] propertyNames = {"warehouse","product"};
				Object[] values = {oldReceive.getWarehouse(),receiveDetail.getProduct()};
				Store store = storeDAO.load(propertyNames, values);
				if(store==null){
					store = new Store();
					store.setWarehouse(oldReceive.getWarehouse());
					store.setProduct(receiveDetail.getProduct());
					store.setQty(0.0);
					storeDAO.save(store);
				}
				store.setQty(store.getQty()+receiveDetail.getQty());
				storeDAO.update(store);
				//更新商品总的库存数量和金额
				Product oldProduct = productDAO.load(receiveDetail.getProduct().getProductId());
				oldProduct.setQtyStore(oldProduct.getQtyStore()+receiveDetail.getQty());
				oldProduct.setAmountStore(oldProduct.getAmountStore()+receiveDetail.getAmount());
				Double priceStore =oldProduct.getQtyStore()==0?0:oldProduct.getAmountStore()/oldProduct.getQtyStore();
				oldProduct.setPriceStore(priceStore);
				productDAO.update(oldProduct);
				//如果该收货明细是从采购单来得，则需要更新对应采购明细的收货数
				if(receiveDetail.getBuyDetail()!=null){
					BuyDetail buyDetail = buyDetailDAO.load(receiveDetail.getBuyDetail().getBuyDetailId());
					buyDetail.setReceiveQty(buyDetail.getReceiveQty()+receiveDetail.getQty());
					buyDetailDAO.update(buyDetail);
				}
			}
			if(!"other".equals(kind)){
				if(oldReceive.getAmount()-oldReceive.getPayAmount()-oldReceive.getDiscountAmount()<=0){
					oldReceive.setIsPay(1);
				}
			}
		}else if(model.getStatus()==0){//如果是由已审改为未审
			//需要判断入库单是否已经进入付款单
			Map<String,Object> countReceiveMap = receiveDAO.countReceive(model.getReceiveId()).get(0);
			String countReceiveMsg = null;
			if(!"0".equals(countReceiveMap.get("countPay").toString())){
				countReceiveMsg = "该入库单已加入采购付款单";
			} 
			if(countReceiveMsg!=null){
				result.setMessage(countReceiveMsg+",已不能修改入库单状态");
				return result;
			}
			//将该入库单下的商品入库
			List<ReceiveDetail> receiveDetailList = receiveDetailDAO.queryByReceiveId(model.getReceiveId());
			//更新对应商品的库存数量
			for (ReceiveDetail receiveDetail : receiveDetailList) {
				String[] propertyNames = {"warehouse","product"};
				Object[] values = {oldReceive.getWarehouse(),receiveDetail.getProduct()};
				Store store = storeDAO.load(propertyNames, values);
				store.setQty(store.getQty()-receiveDetail.getQty());
				storeDAO.update(store);
				//更新商品总的库存数量和金额
				Product oldProduct = productDAO.load(receiveDetail.getProduct().getProductId());
				oldProduct.setQtyStore(oldProduct.getQtyStore()-receiveDetail.getQty());
				oldProduct.setAmountStore(oldProduct.getAmountStore()-receiveDetail.getAmount());
				Double priceStore =oldProduct.getQtyStore()==0?0:oldProduct.getAmountStore()/oldProduct.getQtyStore();
				oldProduct.setPriceStore(priceStore);
				productDAO.update(oldProduct);
				
				//如果该收货明细是从采购单来得，则需要更新对应采购明细的收货数
				if(receiveDetail.getBuyDetail()!=null){
					BuyDetail buyDetail = buyDetailDAO.load(receiveDetail.getBuyDetail().getBuyDetailId());
					buyDetail.setReceiveQty(buyDetail.getReceiveQty()-receiveDetail.getQty());
					buyDetailDAO.update(buyDetail);
				}
			}
			oldReceive.setIsPay(0);
		}
		if(!"other".equals(kind)){
			Bank oldBank = bankDAO.load(oldReceive.getBank().getBankId());
			//更新对应银行的账户金额
			if(model.getStatus()==1){//如果是由未审改为已审
				oldBank.setAmount(oldBank.getAmount()-oldReceive.getPayAmount());
			}else if(model.getStatus()==0){//如果是由已审改为未审
				oldBank.setAmount(oldBank.getAmount()+oldReceive.getPayAmount());
			}
			bankDAO.update(oldBank);
		}
		oldReceive.setStatus(model.getStatus());
		receiveDAO.update(oldReceive);
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.ReceiveService#mulUpdateStatus(java.lang.String, org.linys.model.Receive)
	 */
	@Override
	public ServiceResult mulUpdateStatus(String kind,String ids, Receive model) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(ids)){
			result.setMessage("请选择要修改审核状态的入库单");
			return result;
		}
		String[] idArray =StringUtil.split(ids,GobelConstants.SPLIT_SEPARATOR);
		if(idArray.length==0){
			result.setMessage("请选择要修改审核状态的入库单");
			return result;
		}
		if(model==null||model.getStatus()==null){
			result.setMessage("请选择要修改成的审核状态");
			return result;
		}
		boolean haveUpdateStatus = false;
		for (String id : idArray) {
			Receive oldReceive = receiveDAO.load(id);
			if(oldReceive!=null&&oldReceive.getStatus().intValue()!=model.getStatus().intValue()){
				if(model.getStatus()==1){//如果是由未审改为已审
					//将该入库单下的商品入库
					List<ReceiveDetail> receiveDetailList = receiveDetailDAO.queryByReceiveId(id);
					//将对应商品入库
					for (ReceiveDetail receiveDetail : receiveDetailList) {
						/*
						 * 查找商品在仓库中是否已有记录，如果有则修改库存数量和库存金额，	
						 * 如果没有，则修改新增库存记录
						*/
						String[] propertyNames = {"warehouse","product"};
						Object[] values = {oldReceive.getWarehouse(),receiveDetail.getProduct()};
						Store store = storeDAO.load(propertyNames, values);
						if(store==null){
							store = new Store();
							store.setWarehouse(oldReceive.getWarehouse());
							store.setProduct(receiveDetail.getProduct());
							store.setQty(0.0);
							storeDAO.save(store);
						}
						store.setQty(store.getQty()+receiveDetail.getQty());
						storeDAO.update(store);
						//更新商品总的库存数量和金额
						Product oldProduct = productDAO.load(receiveDetail.getProduct().getProductId());
						oldProduct.setQtyStore(oldProduct.getQtyStore()+receiveDetail.getQty());
						oldProduct.setAmountStore(oldProduct.getAmountStore()+receiveDetail.getAmount());
						Double priceStore =oldProduct.getQtyStore()==0?0:oldProduct.getAmountStore()/oldProduct.getQtyStore();
						oldProduct.setPriceStore(priceStore);
						productDAO.update(oldProduct);
						//如果该收货明细是从采购单来得，则需要更新对应采购明细的收货数
						if(receiveDetail.getBuyDetail()!=null){
							BuyDetail buyDetail = buyDetailDAO.load(receiveDetail.getBuyDetail().getBuyDetailId());
							buyDetail.setReceiveQty(buyDetail.getReceiveQty()+receiveDetail.getQty());
							buyDetailDAO.update(buyDetail);
						}
					}
					if(!"other".equals(kind)){
						if(oldReceive.getAmount()-oldReceive.getPayAmount()-oldReceive.getDiscountAmount()<=0){
							oldReceive.setIsPay(1);
						}
					}
				}else if(model.getStatus()==0){//如果是由已审改为未审
					//需要判断入库单是否已经进入付款单
					Map<String,Object> countReceiveMap = receiveDAO.countReceive(id).get(0);
					String countReceiveMsg = null;
					if(!"0".equals(countReceiveMap.get("countPay").toString())){
						countReceiveMsg = "该入库单已加入采购付款单";
					} 
					if(countReceiveMsg!=null){
						continue;
					}
					//将该入库单下的商品入库
					List<ReceiveDetail> receiveDetailList = receiveDetailDAO.queryByReceiveId(id);
					//更新对应商品的库存数量
					for (ReceiveDetail receiveDetail : receiveDetailList) {
						String[] propertyNames = {"warehouse","product"};
						Object[] values = {oldReceive.getWarehouse(),receiveDetail.getProduct()};
						Store store = storeDAO.load(propertyNames, values);
						store.setQty(store.getQty()-receiveDetail.getQty());
						storeDAO.update(store);
						//更新商品总的库存数量和金额
						Product oldProduct = productDAO.load(receiveDetail.getProduct().getProductId());
						oldProduct.setQtyStore(oldProduct.getQtyStore()-receiveDetail.getQty());
						oldProduct.setAmountStore(oldProduct.getAmountStore()-receiveDetail.getAmount());
						Double priceStore =oldProduct.getQtyStore()==0?0:oldProduct.getAmountStore()/oldProduct.getQtyStore();
						oldProduct.setPriceStore(priceStore);
						productDAO.update(oldProduct);
						//如果该收货明细是从采购单来得，则需要更新对应采购明细的收货数
						if(receiveDetail.getBuyDetail()!=null){
							BuyDetail buyDetail = buyDetailDAO.load(receiveDetail.getBuyDetail().getBuyDetailId());
							buyDetail.setReceiveQty(buyDetail.getReceiveQty()-receiveDetail.getQty());
							buyDetailDAO.update(buyDetail);
						}
					}
					oldReceive.setIsPay(0);
				}
				if(!"other".equals(kind)){
					Bank oldBank = bankDAO.load(oldReceive.getBank().getBankId());
					//更新对应银行的账户金额
					if(model.getStatus()==1){//如果是由未审改为已审
						oldBank.setAmount(oldBank.getAmount()-oldReceive.getPayAmount());
					}else if(model.getStatus()==0){//如果是由已审改为未审
						oldBank.setAmount(oldBank.getAmount()+oldReceive.getPayAmount());
					}
					bankDAO.update(oldBank);
				}
				oldReceive.setStatus(model.getStatus());
				receiveDAO.update(oldReceive);
				haveUpdateStatus = true;
			}
		}
		if(!haveUpdateStatus){
			result.setMessage("没有可修改审核状态的入库单");
			return result;
		}
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.ReceiveService#updateIsPay(org.linys.model.Receive)
	 */
	@Override
	public ServiceResult updateIsPay(Receive model) {
		ServiceResult result = new ServiceResult(false);
		if(model==null||StringUtils.isEmpty(model.getReceiveId())){
			result.setMessage("请选择要清款的入库单");
			return result;
		}
		Receive oldReceive = receiveDAO.load(model.getReceiveId());
		
		if(oldReceive==null){
			result.setMessage("要清款的入库单已不存在");
			return result;
		}
		//如果该入库单已进入付款单则不能进行清款了
		Map<String,Object> countReceiveMap = receiveDAO.countReceive(model.getReceiveId()).get(0);
		String countReceiveMsg = null;
		if(!"0".equals(countReceiveMap.get("countPay").toString())){
			countReceiveMsg = "该入库单已加入采购付款单";
		} 
		if(countReceiveMsg!=null){
			result.setMessage(countReceiveMsg+",已不能清款");
			return result;
		}
		oldReceive.setIsPay(1);
		receiveDAO.update(oldReceive);
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.ReceiveService#mulUpdateStatus(java.lang.String, org.linys.model.Receive)
	 */
	@Override
	public ServiceResult mulUpdateIsPay(String ids, Receive model) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(ids)){
			result.setMessage("请选择要清款的入库单");
			return result;
		}
		String[] idArray =StringUtil.split(ids,GobelConstants.SPLIT_SEPARATOR);
		if(idArray.length==0){
			result.setMessage("请选择要清款的入库单");
			return result;
		}
		if(model==null||model.getIsPay()==null){
			result.setMessage("请选择要修改成的审核状态");
			return result;
		}
		boolean haveUpdateStatus = false;
		for (String id : idArray) {
			Receive oldReceive = receiveDAO.load(id);
			if(oldReceive!=null&&oldReceive.getIsPay().intValue()!=model.getIsPay().intValue()){
				//如果该入库单已进入付款单则不能进行清款了
				Map<String,Object> countReceiveMap = receiveDAO.countReceive(id).get(0);
				String countReceiveMsg = null;
				if(!"0".equals(countReceiveMap.get("countPay").toString())){
					countReceiveMsg = "该入库单已加入采购付款单";
				} 
				if(countReceiveMsg!=null){
					continue;
				}
				oldReceive.setIsPay(model.getIsPay());
				receiveDAO.update(oldReceive);
				haveUpdateStatus = true;
			}
		}
		if(!haveUpdateStatus){
			result.setMessage("没有可清款的入库单");
			return result;
		}
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.ReceiveService#querySelectBuyDetail(java.lang.String, java.lang.String)
	 */
	@Override
	public ServiceResult querySelectBuyDetail(String ids, String ids2) {
		ServiceResult result = new ServiceResult(false);
		String[] idArray = {""};
		if(StringUtils.isNotEmpty(ids)){
			idArray = StringUtil.split(ids);
		}
		
		String[] idArray2 = {""};
		if(StringUtils.isNotEmpty(ids2)){
			idArray2 = StringUtil.split(ids2);
		}
		
		List<Map<String,Object>> listMap = receiveDAO.querySelectBuyDetail(idArray,idArray2);
		
		String datagridData = JSONUtil.toJsonFromListMapWithOutRows(listMap);
		result.addData("datagridData", datagridData); 
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.ReceiveService#queryNeedPay(java.lang.String, java.lang.String, java.lang.String, java.lang.String, org.linys.model.Receive, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public ServiceResult queryNeedPay(String beginDate, String endDate,
			String supplierId, String ids, Receive model, Integer page,
			Integer rows) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(beginDate)){
			result.setMessage("请选择开始日期");
			return result;
		}
		
		Date beginDateDate = null;
		if(StringUtils.isNotEmpty(beginDate)){
			try {
				beginDateDate = DateUtil.toDate(beginDate);
			} catch (ParseException e) {
				result.setMessage("请输入正确的开始日期");
				return result;
			}
		}
		Date endDateDate = null;
		if(StringUtils.isNotEmpty(endDate)){
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
		}
		String[] idArray = {""};
		if(StringUtils.isNotEmpty(ids)){
			idArray = StringUtil.split(ids);
		}
		List<Map<String,Object>> listMap = receiveDAO.queryNeedPay(beginDateDate,endDateDate,supplierId,idArray,
				model,page,rows);
		
		Long total = receiveDAO.getTotalReceive(beginDateDate,endDateDate,supplierId,idArray,
				model);
		String datagridData = JSONUtil.toJsonFromListMap(listMap, total);
		result.addData("datagridData", datagridData); 
		result.setIsSuccess(true);
		return result;
	}
	@Override
	public ServiceResult getSysConfig() {
		ServiceResult result = new ServiceResult(false);
		SystemConfig systemConfig = systemConfigDAO.init();
		result.addData("isAmountEqPayAmount", systemConfig.getIsAmountEqPayAmount());
		result.addData("isOtherAmountInPayAmount", systemConfig.getIsOtherAmountInPayAmount());
		result.setIsSuccess(true);
		return result;
	}
	
	
}
