package org.linys.service.impl;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.linys.bean.Pager;
import org.linys.dao.CommonDAO;
import org.linys.dao.DeliverDao;
import org.linys.dao.DeliverDetailDao;
import org.linys.dao.PrefixDAO;
import org.linys.dao.StoreDAO;
import org.linys.model.Bank;
import org.linys.model.DataDictionary;
import org.linys.model.Deliver;
import org.linys.model.DeliverDetail;
import org.linys.model.Product;
import org.linys.model.SaleDetail;
import org.linys.model.Store;
import org.linys.service.DeliverService;
import org.linys.util.JSONUtil;
import org.linys.util.StringUtil;
import org.linys.vo.GobelConstants;
import org.linys.vo.ServiceResult;
import org.springframework.stereotype.Service;

/**
 * 
 * @Description:
 * @Copyright: 福州骏华科技信息有限公司 (c)2013
 * @Created Date : 2013-1-24
 * @author longweier
 * @vesion 1.0
 */
@Service
public class DeliverServiceImpl extends BaseServiceImpl<Deliver, String> implements DeliverService {

	@Resource
	private DeliverDao deliverDao;
	@Resource
	private DeliverDetailDao deliverDetailDao;
	@Resource
	private CommonDAO commonDao;
	@Resource
	private PrefixDAO prefixDao;
	@Resource
	private StoreDAO storeDao;
	
	public ServiceResult addDeliver(String type,Deliver deliver, String saleDetailIds,
			String productIds, String colorIds, String qtys, String prices,
			String discounts, String note1s, String note2s, String note3s) {
		
		ServiceResult result = new ServiceResult(false);
		
		if(!"other".equals(type)){
			if(deliver.getCustomer()==null || StringUtils.isEmpty(deliver.getCustomer().getCustomerId())){
				result.setMessage("客户不能为空");
				return result;
			}
		}
		if(deliver.getWarehouse()==null || StringUtils.isEmpty(deliver.getWarehouse().getWarehouseId())){
			result.setMessage("仓库不能为空");
			return result;
		}
		if(deliver.getDeliverDate()==null){
			result.setMessage("出库日期不能为空");
			return result;
		}
		if(deliver.getEmployee()==null||StringUtils.isEmpty(deliver.getEmployee().getEmployeeId())){
			result.setMessage("请选择经办人");
			return result;
		}
		if(StringUtils.isEmpty(productIds)){
			result.setMessage("请选择商品");
			return result;
		}
		String[] saleDetailIdArray = StringUtil.split(saleDetailIds, GobelConstants.SPLIT_SEPARATOR);
		String[] productIdArray = StringUtil.split(productIds, GobelConstants.SPLIT_SEPARATOR);
		String[] colorIdArray = StringUtil.split(colorIds, GobelConstants.SPLIT_SEPARATOR);
		String[] qtyArray = StringUtil.split(qtys, GobelConstants.SPLIT_SEPARATOR);
		String[] priceArray = StringUtil.split(prices, GobelConstants.SPLIT_SEPARATOR);
		String[] discountArray = StringUtil.split(discounts, GobelConstants.SPLIT_SEPARATOR);
		String[] note1Array = StringUtil.split(note1s, GobelConstants.SPLIT_SEPARATOR);
		String[] note2Array = StringUtil.split(note2s, GobelConstants.SPLIT_SEPARATOR);
		String[] note3Array = StringUtil.split(note3s, GobelConstants.SPLIT_SEPARATOR);
		
		for (int i = 0; i < qtyArray.length; i++) {
			String qty = qtyArray[i];
			if("0".equals(qty) ||"".equals(qty)){
				result.setMessage("第"+(i+1)+"行商品数量不能为0");
				return result;
			}
			
		}
		//取得出库单编号
		if("other".equals(type)){
			deliver.setDeliverCode(commonDao.getCode(Deliver.class.getName(), "deliverCode", prefixDao.getPrefix("deliverOther")));
		}else{
			deliver.setDeliverCode(commonDao.getCode(Deliver.class.getName(), "deliverCode", prefixDao.getPrefix("deliver")));
		}
		deliver.setCheckAmount(0.0);
		deliver.setStatus(0);
		deliver.setIsReceipt(0);
		if(deliver.getAmount()==null){
			deliver.setAmount(0.0);
		}
		if(deliver.getDiscountAmount()==null){
			deliver.setAmount(0.0);
		}
		if(deliver.getReceiptedAmount()==null){
			deliver.setReceiptedAmount(0.0);
		}
		deliverDao.save(deliver);
		
		for (int i=0;i<productIdArray.length;i++) {
			String saleDetailId = saleDetailIdArray[i];
			String productId = productIdArray[i];
			String colorId = colorIdArray[i];
			String qty = qtyArray[i];
			String price = priceArray[i];
			String discount = discountArray[i];
			String note1 = note1Array[i];
			String note2 = note2Array[i];
			String note3 = note3Array[i];
			
			if(StringUtils.isEmpty(price)){
				price ="0";
			}
			if(StringUtils.isEmpty(discount)){
				discount=null;
			}
			
			DeliverDetail deliverDetail = new DeliverDetail();
			
			if(StringUtils.isNotEmpty(saleDetailId)){
				SaleDetail saleDetail = new SaleDetail();
				saleDetail.setSaleDetailId(saleDetailId);
				deliverDetail.setSaleDetail(saleDetail);
			}
			
			Product product = new Product();
			product.setProductId(productId);
			
			if(StringUtils.isNotEmpty(colorId)){
				DataDictionary color = new DataDictionary();
				color.setDataDictionaryId(colorId);
				deliverDetail.setColor(color);
			}
			deliverDetail.setProduct(product);
			deliverDetail.setDeliver(deliver);
			deliverDetail.setQty(Double.parseDouble(qty));
			deliverDetail.setPrice(Double.parseDouble(price));
			deliverDetail.setDiscount(discount==null?null:Double.parseDouble(discount));
			deliverDetail.setNote1(note1);
			deliverDetail.setNote2(note2);
			deliverDetail.setNote3(note3);
			
			deliverDetailDao.save(deliverDetail);
		}
		result.setIsSuccess(true);
		result.addData("deliverId", deliver.getDeliverId());
		return result;
	}

	public String initDeliver(String deliverId) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(deliverId)){
			result.setMessage("参数错误");
			return result.toJSON();
		}
		Deliver deliver = deliverDao.load(deliverId);
		String[] propertiesDeliver = {"deliverId","deliverCode","deliverDate","sourceCode","express.expressId","invoiceType.invoiceTypeId",
				"customer.customerId","customer.customerName","warehouse.warehouseId","amount","discountAmount","receiptedAmount",
				"bank.bankId","employee.employeeId","note","status","expressCode","isReceipt"};
		String deliverData = JSONUtil.toJson(deliver,propertiesDeliver);
		result.addData("deliverData",deliverData);
		
		List<DeliverDetail> deliverDetailList = deliverDetailDao.queryDeliverDetail(deliver);
		String[] propertiesDetail = {"deliverDetailId","saleDetail.saleDetailId","product.productId","product.productCode","product.productName",
				"product.size.dataDictionaryName:sizeName","product.unit.dataDictionaryName:unitName","color.dataDictionaryName:colorName",
				"color.dataDictionaryId:colorId","saleDetail.sale.saleCode:saleCode","qty","price","discount","amount","note1","note2","note3"};
		String detailData = JSONUtil.toJson(deliverDetailList,propertiesDetail);
		result.addData("detailData", detailData);
		result.setIsSuccess(true);
		return result.toJSON();
	}

	public ServiceResult deleteDeliver(String deliverId) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(deliverId)){
			result.setMessage("参数不能为空");
			return result;
		}
		Deliver deliver = deliverDao.load(deliverId);
		if(deliver==null){
			result.setMessage("该出库单已被删除");
			return result;
		}
		if(deliver.getStatus().intValue()==1){
			result.setMessage("该出库单已审核,不能删除");
			return result;
		}
		deliverDao.delete(deliver);
		result.setIsSuccess(true);
		return result;
	}
	
	public ServiceResult mulDeleteDeliver(String ids) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(ids)){
			result.setMessage("参数不能为空");
			return result;
		}
		String[] idArray = StringUtil.split(ids, GobelConstants.SPLIT_SEPARATOR);
		for(String deliverId : idArray){
			Deliver deliver = deliverDao.load(deliverId);
			if(deliver==null || deliver.getStatus().intValue()==1) continue;
			deliverDao.delete(deliver);
		}
		result.setIsSuccess(true);
		return result;
	}

	public ServiceResult updateStatusDeliver(String type,Deliver deliver) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(deliver.getDeliverId()) || deliver.getStatus()==null){
			result.setMessage("参数错误");
			return result;
		}
		Deliver model = deliverDao.load(deliver.getDeliverId());
		if(model==null){
			result.setMessage("出库单已被删除");
			return result;
		}
		//如果由已审修改为未审需要判断是否收款
		if(model.getStatus().intValue()==deliver.getStatus().intValue()){
			result.setMessage("要修改审核状态的出库单已是要修改的状态，请刷新界面");
			return result;
		}
		if(deliver.getStatus()==1){//如果是由未审改为已审
			//将该出库单下的商品出库
			List<DeliverDetail> deliverDetailList = deliverDetailDao.queryDeliverDetail(model);
			//将对应商品出库
			for (DeliverDetail deliverDetail : deliverDetailList) {
				/*
				 * 查找商品在仓库中是否已有记录，如果有则修改库存数量和库存金额，	
				 * 如果没有，则修改新增库存记录
				*/
				String[] propertyNames = {"warehouse","product"};
				Object[] values = {model.getWarehouse(),deliverDetail.getProduct()};
				Store store = storeDao.load(propertyNames, values);
				Product product = deliverDetail.getProduct();
				if(store==null || store.getQty()-deliverDetail.getQty()<0){
					throw new RuntimeException("商品:"+product.getProductName()+",超额出库");
				}
				Double price = 0.0;
				if(product.getAmountStore()!=null && product.getQtyStore()!=null && product.getQtyStore()>0){
					price = product.getAmountStore()/product.getQtyStore();
				}
				//计算金额
				DecimalFormat df = new DecimalFormat("######0.00");
				Double amount = Double.parseDouble(df.format(price*deliverDetail.getQty()));
				//更新库存
				store.setQty(store.getQty()-deliverDetail.getQty());
				//更新商品总的库存数量和金额
				product.setQtyStore(product.getQtyStore()-deliverDetail.getQty());
				product.setAmountStore(product.getAmountStore()-amount);
				//如果该收货明细是从出库单来得，则需要更新对应出库明细的出货数
				SaleDetail saleDetail = deliverDetail.getSaleDetail();
				if(saleDetail!=null){
					saleDetail.setHadSaleQty(saleDetail.getHadSaleQty()+saleDetail.getQty());
				}
			}
			if(!"other".equals(type)){
				if(model.getAmount()-model.getReceiptedAmount()-model.getDiscountAmount()<=0){
					model.setIsReceipt(1);
				}
			}
		}else if(deliver.getStatus()==0){//如果是由已审改为未审
			//将该出库单下的商品出库
			List<DeliverDetail> deliverDetailList = deliverDetailDao.queryDeliverDetail(model);
			//更新对应商品的库存数量
			for (DeliverDetail deliverDetail : deliverDetailList) {
				String[] propertyNames = {"warehouse","product"};
				Object[] values = {model.getWarehouse(),deliverDetail.getProduct()};
				Store store = storeDao.load(propertyNames, values);
				if(store==null){
					store = new Store();
					store.setWarehouse(model.getWarehouse());
					store.setProduct(deliverDetail.getProduct());
					store.setQty(0.0);
					store.setAmount(0.0);
					storeDao.save(store);
				}
				//更新商品总的库存数量和金额
				store.setQty(store.getQty()+deliverDetail.getQty());
				//store.setAmount(store.getAmount()+amount);
				
				Product product = deliverDetail.getProduct();
				
				Double price = 0.0;
				if(product.getAmountStore()!=null && product.getQtyStore()!=null && product.getQtyStore()>0){
					price = product.getAmountStore()/product.getQtyStore();
				}
				//计算金额
				DecimalFormat df = new DecimalFormat("######0.00");
				Double amount = Double.parseDouble(df.format(price*deliverDetail.getQty()));
				//更新库存
				product.setQtyStore(product.getQtyStore()+deliverDetail.getQty());
				product.setAmountStore(product.getAmountStore()+amount);
				
				//如果该出库明细是从出库单得来，则需要更新对应订单明细的出货数
				SaleDetail saleDetail = deliverDetail.getSaleDetail();
				if(saleDetail!=null){
					saleDetail.setHadSaleQty(saleDetail.getHadSaleQty()-saleDetail.getQty());
				}
			}
			model.setIsReceipt(0);
		}
		if(!"other".equals(type)){
			Bank bank = model.getBank();
			if(bank!=null){
				//更新对应银行的账户金额
				if(deliver.getStatus()==1){//如果是由未审改为已审
					bank.setAmount(bank.getAmount()+model.getReceiptedAmount());
				}else if(model.getStatus()==0){//如果是由已审改为未审
					bank.setAmount(bank.getAmount()-model.getReceiptedAmount());
				}
			}
		}
		model.setStatus(deliver.getStatus());
		result.setIsSuccess(true);
		return result;
	}
	
	public ServiceResult mulUpdateStatusDeliver(String type,String ids, Integer status) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(ids) || status==null){
			result.setMessage("参数错误");
			return result;
		}
		String[] idArray = StringUtil.split(ids, GobelConstants.SPLIT_SEPARATOR);
		
		for(String deliverId : idArray){
			Deliver model = deliverDao.load(deliverId);
			if(model==null){
				result.setMessage("出库单已被删除");
				return result;
			}
			//如果由已审修改为未审需要判断是否收款
			if(model.getStatus().intValue()==status.intValue()) continue;
			if(status==1){//如果是由未审改为已审
				//将该出库单下的商品出库
				List<DeliverDetail> deliverDetailList = deliverDetailDao.queryDeliverDetail(model);
				//将对应商品出库
				for (DeliverDetail deliverDetail : deliverDetailList) {
					/*
					 * 查找商品在仓库中是否已有记录，如果有则修改库存数量和库存金额，	
					 * 如果没有，则修改新增库存记录
					*/
					String[] propertyNames = {"warehouse","product"};
					Object[] values = {model.getWarehouse(),deliverDetail.getProduct()};
					Store store = storeDao.load(propertyNames, values);
					Product product = deliverDetail.getProduct();
					if(store==null || store.getQty()-deliverDetail.getQty()<0){
						throw new RuntimeException("出库单号:"+model.getDeliverCode()+",商品:"+product.getProductName()+"超额出库");
					}
					//更新商品总的库存数量和金额
					store.setQty(store.getQty()-deliverDetail.getQty());
					//store.setAmount(store.getAmount()-amount);
					
					Double price = 0.0;
					if(product.getAmountStore()!=null && product.getQtyStore()!=null && product.getQtyStore()>0){
						price = product.getAmountStore()/product.getQtyStore();
					}
					//计算金额
					DecimalFormat df = new DecimalFormat("######0.00");
					Double amount = Double.parseDouble(df.format(price*deliverDetail.getQty()));
					//更新库存
					product.setQtyStore(product.getQtyStore()-deliverDetail.getQty());
					product.setAmountStore(product.getAmountStore()-amount);
					//如果该收货明细是从出库单来得，则需要更新对应出库明细的出货数
					SaleDetail saleDetail = deliverDetail.getSaleDetail();
					if(saleDetail!=null){
						saleDetail.setHadSaleQty(saleDetail.getHadSaleQty()+saleDetail.getQty());
					}
				}
				if(!"other".equals(type)){
					if(model.getAmount()-model.getReceiptedAmount()-model.getDiscountAmount()<=0){
						model.setIsReceipt(1);
					}
				}
			}else if(status==0){//如果是由已审改为未审
				//将该出库单下的商品出库
				List<DeliverDetail> deliverDetailList = deliverDetailDao.queryDeliverDetail(model);
				//更新对应商品的库存数量
				for (DeliverDetail deliverDetail : deliverDetailList) {
					String[] propertyNames = {"warehouse","product"};
					Object[] values = {model.getWarehouse(),deliverDetail.getProduct()};
					Store store = storeDao.load(propertyNames, values);
					if(store==null){
						store = new Store();
						store.setWarehouse(model.getWarehouse());
						store.setProduct(deliverDetail.getProduct());
						store.setQty(0.0);
						store.setAmount(0.0);
						storeDao.save(store);
					}
					//更新商品总的库存数量和金额
					store.setQty(store.getQty()+deliverDetail.getQty());
					//store.setAmount(store.getAmount()+amount);
					
					Product product = deliverDetail.getProduct();
					Double price = 0.0;
					if(product.getAmountStore()!=null && product.getQtyStore()!=null && product.getQtyStore()>0){
						price = product.getAmountStore()/product.getQtyStore();
					}
					//计算金额
					DecimalFormat df = new DecimalFormat("######0.00");
					Double amount = Double.parseDouble(df.format(price*deliverDetail.getQty()));
					//更新库存
					product.setQtyStore(product.getQtyStore()+deliverDetail.getQty());
					product.setAmountStore(product.getAmountStore()+amount);
					
					//如果该出库明细是从出库单得来，则需要更新对应订单明细的出货数
					SaleDetail saleDetail = deliverDetail.getSaleDetail();
					if(saleDetail!=null){
						saleDetail.setHadSaleQty(saleDetail.getHadSaleQty()-saleDetail.getQty());
					}
				}
				model.setIsReceipt(0);
			}
			if(!"other".equals(type)){
				Bank bank = model.getBank();
				if(bank!=null){
					//更新对应银行的账户金额
					if(status==1){//如果是由未审改为已审
						bank.setAmount(bank.getAmount()+model.getReceiptedAmount());
					}else{//如果是由已审改为未审
						bank.setAmount(bank.getAmount()-model.getReceiptedAmount());
					}
				}
			}
			model.setStatus(status);
		}
		result.setIsSuccess(true);
		return result;
	}

	public String queryDeliver(Integer pageNumber, Integer pageSize,Deliver deliver, Date beginDate, Date endDate,String type) {
		Pager pager = new Pager(pageNumber, pageSize);
		pager = deliverDao.queryDeliver(pager, deliver, beginDate, endDate,type);
		String[] properties = {"deliverId","deliverCode","deliverDate","sourceCode",
				"warehouse.warehouseName","customer.customerName","amount","receiptedAmount","discountAmount","checkAmount",
				"employee.employeeName","note","status","invoiceType.invoiceTypeName","isReceipt","express.expressName","expressCode"};
		String jsonArray = JSONUtil.toJson(pager.getList(), properties, pager.getTotalCount());
		return jsonArray;
	}

	public ServiceResult updateDeliver(String type,Deliver deliver,
			String deliverDetailIds, String delDeliverDetailIds,
			String saleDetailIds, String productIds, String colorIds,
			String qtys, String prices, String discounts, String note1s,
			String note2s, String note3s) {
		ServiceResult result = new ServiceResult(false);
		if(!"other".equals(type)){
			if(deliver.getCustomer()==null || StringUtils.isEmpty(deliver.getCustomer().getCustomerId())){
				result.setMessage("客户不能为空");
				return result;
			}
		}
		if(deliver.getWarehouse()==null || StringUtils.isEmpty(deliver.getWarehouse().getWarehouseId())){
			result.setMessage("仓库不能为空");
			return result;
		}
		if(deliver.getDeliverDate()==null){
			result.setMessage("出库日期不能为空");
			return result;
		}
		if(deliver.getEmployee()==null||StringUtils.isEmpty(deliver.getEmployee().getEmployeeId())){
			result.setMessage("请选择经办人");
			return result;
		}
		if(StringUtils.isEmpty(productIds)){
			result.setMessage("请选择商品");
			return result;
		}
		String[] deliverDetailIdArray =  StringUtil.split(deliverDetailIds, GobelConstants.SPLIT_SEPARATOR);
		String[] delDeliverDetailIdArray =  StringUtil.split(delDeliverDetailIds, GobelConstants.SPLIT_SEPARATOR);
		String[] saleDetailIdArray =  StringUtil.split(saleDetailIds, GobelConstants.SPLIT_SEPARATOR);
		String[] productIdArray = StringUtil.split(productIds, GobelConstants.SPLIT_SEPARATOR);
		String[] colorIdArray = StringUtil.split(colorIds, GobelConstants.SPLIT_SEPARATOR);
		String[] qtyArray = StringUtil.split(qtys, GobelConstants.SPLIT_SEPARATOR);
		String[] priceArray = StringUtil.split(prices, GobelConstants.SPLIT_SEPARATOR);
		String[] discountArray = StringUtil.split(discounts, GobelConstants.SPLIT_SEPARATOR);
		String[] note1Array = StringUtil.split(note1s, GobelConstants.SPLIT_SEPARATOR);
		String[] note2Array = StringUtil.split(note2s, GobelConstants.SPLIT_SEPARATOR);
		String[] note3Array = StringUtil.split(note3s, GobelConstants.SPLIT_SEPARATOR);
		
		for (int i = 0; i < qtyArray.length; i++) {
			String qty = qtyArray[i];
			if("0".equals(qty) ||"".equals(qty)){
				result.setMessage("第"+(i+1)+"行商品数量不能为0");
				return result;
			}
		}
		Deliver model = deliverDao.load(deliver.getDeliverId());
		if(model.getStatus()==1){
			result.setMessage("该出库单已审核,不能修改");
			return result;
		}else{
			deliverDao.evict(model);
		}
		deliver.setStatus(model.getStatus());
		deliver.setCheckAmount(model.getCheckAmount());
		deliver.setIsReceipt(model.getIsReceipt());
		if(deliver.getAmount()==null){
			deliver.setAmount(0.0);
		}
		if(deliver.getDiscountAmount()==null){
			deliver.setAmount(0.0);
		}
		if(deliver.getReceiptedAmount()==null){
			deliver.setReceiptedAmount(0.0);
		}
		deliverDao.evict(model);
		deliverDao.update(deliver);
		//删除
		for(String id : delDeliverDetailIdArray){
			if(StringUtils.isEmpty(id)) continue;
			deliverDetailDao.delete(id);
		}
		//新增、修改
		for (int i=0;i<productIdArray.length;i++) {
			String deliverDetailId = deliverDetailIdArray[i];
			String saleDetailId = saleDetailIdArray[i];
			String productId = productIdArray[i];
			String colorId = colorIdArray[i];
			String qty = qtyArray[i];
			String price = priceArray[i];
			String discount = discountArray[i];
			String note1 = note1Array[i];
			String note2 = note2Array[i];
			String note3 = note3Array[i];
			
			if(StringUtils.isEmpty(price)){
				price ="0";
			}
			if(StringUtils.isEmpty(discount)){
				discount=null;
			}
			Product product = new Product();
			product.setProductId(productId);
			DataDictionary color = new DataDictionary();
			color.setDataDictionaryId(colorId);
			
			if(StringUtils.isEmpty(deliverDetailId)){//新增
				DeliverDetail deliverDetail = new DeliverDetail();
				
				if(StringUtils.isNotEmpty(saleDetailId)){
					SaleDetail saleDetail = new SaleDetail();
					saleDetail.setSaleDetailId(saleDetailId);
					
					deliverDetail.setSaleDetail(saleDetail);
				}
				
				deliverDetail.setProduct(product);
				deliverDetail.setDeliver(deliver);
				
				if(StringUtils.isNotEmpty(colorId)){
					deliverDetail.setColor(color);
				}
				deliverDetail.setQty(Double.parseDouble(qty));
				deliverDetail.setPrice(Double.parseDouble(price));
				deliverDetail.setDiscount(discount==null?null:Double.parseDouble(discount));
				deliverDetail.setNote1(note1);
				deliverDetail.setNote2(note2);
				deliverDetail.setNote3(note3);
				
				deliverDetailDao.save(deliverDetail);
				
			}else{//修改
				DeliverDetail deliverDetail = deliverDetailDao.load(deliverDetailId);
				
				if(deliverDetail==null) continue;
				
				if(StringUtils.isNotEmpty(saleDetailId)){
					SaleDetail saleDetail = new SaleDetail();
					saleDetail.setSaleDetailId(saleDetailId);
					
					deliverDetail.setSaleDetail(saleDetail);
				}
				
				if(StringUtils.isNotEmpty(colorId)){
					deliverDetail.setColor(color);
				}else{
					deliverDetail.setColor(null);
				}
				deliverDetail.setQty(Double.parseDouble(qty));
				deliverDetail.setPrice(Double.parseDouble(price));
				deliverDetail.setDiscount(discount==null?null:Double.parseDouble(discount));
				deliverDetail.setNote1(note1);
				deliverDetail.setNote2(note2);
				deliverDetail.setNote3(note3);
			}
		}
		result.setIsSuccess(true);
		result.addData("deliverId", deliver.getDeliverId());
		return result;
	}

	public ServiceResult querySelectSaleDetail(String ids, String ids2) {
		ServiceResult result = new ServiceResult(false);
		String[] idArray = {""};
		if(StringUtils.isNotEmpty(ids)){
			idArray = StringUtil.split(ids);
		}
		
		String[] idArray2 = {""};
		if(StringUtils.isNotEmpty(ids2)){
			idArray2 = StringUtil.split(ids2);
		}
		
		List<Map<String,Object>> listMap = deliverDetailDao.querySelectSaleDetail(idArray,idArray2);
		
		String datagridData = JSONUtil.toJsonFromListMapWithOutRows(listMap);
		result.addData("datagridData", datagridData); 
		result.setIsSuccess(true);
		return result;
	}

}
