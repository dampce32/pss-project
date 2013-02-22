package org.linys.service.impl;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.linys.bean.Pager;
import org.linys.dao.CommonDAO;
import org.linys.dao.DeliverRejectDao;
import org.linys.dao.DeliverRejectDetailDao;
import org.linys.dao.PrefixDAO;
import org.linys.dao.StoreDAO;
import org.linys.model.Bank;
import org.linys.model.DataDictionary;
import org.linys.model.DeliverReject;
import org.linys.model.DeliverRejectDetail;
import org.linys.model.Product;
import org.linys.model.Store;
import org.linys.model.Warehouse;
import org.linys.service.DeliverRejectService;
import org.linys.util.JSONUtil;
import org.linys.util.StringUtil;
import org.linys.vo.GobelConstants;
import org.linys.vo.ServiceResult;
import org.springframework.stereotype.Service;

/**
 * 
 * @Description: 销售退货service的实现类
 * @Copyright: 福州骏华科技信息有限公司 (c)2013
 * @Created Date : 2013-1-25
 * @author longweier
 * @vesion 1.0
 */
@Service
public class DeliverRejectServiceImpl extends BaseServiceImpl<DeliverReject, String> implements DeliverRejectService {

	@Resource
	private DeliverRejectDao deliverRejectDao;
	@Resource
	private DeliverRejectDetailDao deliverRejectDetailDao;
	@Resource
	private StoreDAO storeDao;
	@Resource
	private PrefixDAO prefixDao;
	@Resource
	private CommonDAO commonDao;
	
	public ServiceResult addDeliverReject(DeliverReject deliverReject,
			String productIds, String colorIds, String qtys, String prices,
			String note1s, String note2s, String note3s) {
		ServiceResult result = new ServiceResult(false);
		if(deliverReject.getCustomer()==null ||StringUtils.isEmpty(deliverReject.getCustomer().getCustomerId())){
			result.setMessage("请选择客户");
			return result;
		}
		if(deliverReject.getWarehouse()==null || StringUtils.isEmpty(deliverReject.getWarehouse().getWarehouseId())){
			result.setMessage("仓库不能为空");
			return result;
		}
		if(deliverReject.getDeliverRejectDate()==null){
			result.setMessage("退货日期不能为空");
			return result;
		}
		if(deliverReject.getEmployee()==null||StringUtils.isEmpty(deliverReject.getEmployee().getEmployeeId())){
			result.setMessage("请选择经办人");
			return result;
		}
		if(StringUtils.isEmpty(productIds)){
			result.setMessage("请选择商品");
			return result;
		}
		String[] productIdArray = StringUtil.split(productIds, GobelConstants.SPLIT_SEPARATOR);
		String[] colorIdArray = StringUtil.split(colorIds, GobelConstants.SPLIT_SEPARATOR);
		String[] qtyArray = StringUtil.split(qtys, GobelConstants.SPLIT_SEPARATOR);
		String[] priceArray = StringUtil.split(prices, GobelConstants.SPLIT_SEPARATOR);
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
		deliverReject.setDeliverRejectCode(commonDao.getCode(DeliverReject.class.getName(), "deliverRejectCode", prefixDao.getPrefix("deliverReject")));
		deliverReject.setStatus(0);
		if(deliverReject.getAmount()==null){
			deliverReject.setAmount(0.0);
		}
		if(deliverReject.getPayedAmount()==null){
			deliverReject.setPayedAmount(0.0);
		}
		deliverReject.setCheckAmount(0d);
		deliverRejectDao.save(deliverReject);
		
		for (int i=0;i<productIdArray.length;i++) {
			String productId = productIdArray[i];
			String colorId = colorIdArray[i];
			String qty = qtyArray[i];
			String price = priceArray[i];
			String note1 = note1Array[i];
			String note2 = note2Array[i];
			String note3 = note3Array[i];
			
			if(StringUtils.isEmpty(price)){
				price ="0";
			}
			DeliverRejectDetail deliverRejectDetail = new DeliverRejectDetail();
			
			Product product = new Product();
			product.setProductId(productId);
			
			if(StringUtils.isNotEmpty(colorId)){
				DataDictionary color = new DataDictionary();
				color.setDataDictionaryId(colorId);
				deliverRejectDetail.setColor(color);
			}
			deliverRejectDetail.setProduct(product);
			deliverRejectDetail.setDeliverReject(deliverReject);
			deliverRejectDetail.setQty(Double.parseDouble(qty));
			deliverRejectDetail.setPrice(Double.parseDouble(price));
			deliverRejectDetail.setNote1(note1);
			deliverRejectDetail.setNote2(note2);
			deliverRejectDetail.setNote3(note3);
			
			deliverRejectDetailDao.save(deliverRejectDetail);
		}
		result.setIsSuccess(true);
		result.addData("deliverRejectId", deliverReject.getDeliverRejectId());
		return result;
	}

	public ServiceResult updateDeliverReject(DeliverReject deliverReject,
			String deliverRejectDetailIds, String delDeliverRejectDetailIds,
			String productIds, String colorIds, String qtys, String prices,
			String note1s, String note2s, String note3s) {
		ServiceResult result = new ServiceResult(false);
		if(deliverReject.getCustomer()==null ||StringUtils.isEmpty(deliverReject.getCustomer().getCustomerId())){
			result.setMessage("请选择客户");
			return result;
		}
		if(deliverReject.getWarehouse()==null || StringUtils.isEmpty(deliverReject.getWarehouse().getWarehouseId())){
			result.setMessage("仓库不能为空");
			return result;
		}
		if(deliverReject.getDeliverRejectDate()==null){
			result.setMessage("退货日期不能为空");
			return result;
		}
		if(deliverReject.getEmployee()==null||StringUtils.isEmpty(deliverReject.getEmployee().getEmployeeId())){
			result.setMessage("请选择经办人");
			return result;
		}
		if(StringUtils.isEmpty(productIds)){
			result.setMessage("请选择商品");
			return result;
		}
		
		String[] deliverRejectDetailIdArray =  StringUtil.split(deliverRejectDetailIds, GobelConstants.SPLIT_SEPARATOR);
		String[] delDeliverRejectDetailIdArray =  StringUtil.split(delDeliverRejectDetailIds, GobelConstants.SPLIT_SEPARATOR);
		String[] productIdArray = StringUtil.split(productIds, GobelConstants.SPLIT_SEPARATOR);
		String[] colorIdArray = StringUtil.split(colorIds, GobelConstants.SPLIT_SEPARATOR);
		String[] qtyArray = StringUtil.split(qtys, GobelConstants.SPLIT_SEPARATOR);
		String[] priceArray = StringUtil.split(prices, GobelConstants.SPLIT_SEPARATOR);
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
		DeliverReject model = deliverRejectDao.load(deliverReject.getDeliverRejectId());
		if(model.getStatus()==1){
			result.setMessage("该退货单已审核,不能修改");
			return result;
		}else{
			deliverRejectDao.evict(model);
		}
		deliverReject.setStatus(model.getStatus());
		deliverReject.setCheckAmount(model.getCheckAmount());
		if(deliverReject.getAmount()==null){
			deliverReject.setAmount(0.0);
		}
		if(deliverReject.getPayedAmount()==null){
			deliverReject.setPayedAmount(0.0);
		}
		deliverRejectDao.evict(model);
		deliverRejectDao.update(deliverReject);
		//删除
		for(String id : delDeliverRejectDetailIdArray){
			if(StringUtils.isEmpty(id)) continue;
			deliverRejectDetailDao.delete(id);
		}
		//新增、修改
		for (int i=0;i<productIdArray.length;i++) {
			String deliverRejectDetailId = deliverRejectDetailIdArray[i];
			String productId = productIdArray[i];
			String colorId = colorIdArray[i];
			String qty = qtyArray[i];
			String price = priceArray[i];
			String note1 = note1Array[i];
			String note2 = note2Array[i];
			String note3 = note3Array[i];
			
			if(StringUtils.isEmpty(price)){
				price ="0";
			}
			Product product = new Product();
			product.setProductId(productId);
			DataDictionary color = new DataDictionary();
			color.setDataDictionaryId(colorId);
			
			if(StringUtils.isEmpty(deliverRejectDetailId)){//新增
				DeliverRejectDetail deliverRejectDetail = new DeliverRejectDetail();
				
				deliverRejectDetail.setProduct(product);
				deliverRejectDetail.setDeliverReject(deliverReject);
				
				if(StringUtils.isNotEmpty(colorId)){
					deliverRejectDetail.setColor(color);
				}
				deliverRejectDetail.setQty(Double.parseDouble(qty));
				deliverRejectDetail.setPrice(Double.parseDouble(price));
				deliverRejectDetail.setNote1(note1);
				deliverRejectDetail.setNote2(note2);
				deliverRejectDetail.setNote3(note3);
				
				deliverRejectDetailDao.save(deliverRejectDetail);
				
			}else{//修改
				DeliverRejectDetail deliverRejectDetail = deliverRejectDetailDao.load(deliverRejectDetailId);
				
				if(deliverRejectDetail==null) continue;
				
				if(StringUtils.isNotEmpty(colorId)){
					deliverRejectDetail.setColor(color);
				}else{
					deliverRejectDetail.setColor(null);
				}
				deliverRejectDetail.setQty(Double.parseDouble(qty));
				deliverRejectDetail.setPrice(Double.parseDouble(price));
				deliverRejectDetail.setNote1(note1);
				deliverRejectDetail.setNote2(note2);
				deliverRejectDetail.setNote3(note3);
			}
		}
		result.setIsSuccess(true);
		result.addData("deliverRejectId", deliverReject.getDeliverRejectId());
		return result;
	}
	
	public ServiceResult deleteDeliverReject(String deliverRejectId) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(deliverRejectId)){
			result.setMessage("参数错误");
			return result;
		}
		DeliverReject deliverReject = deliverRejectDao.load(deliverRejectId);
		if(deliverReject==null){
			result.setMessage("该销售退货已被删除");
			return result;
		}
		if(deliverReject.getStatus()==1){
			result.setMessage("该销售退货单已审核,不能删除");
			return result;
		}
		deliverRejectDao.delete(deliverReject);
		result.setIsSuccess(true);
		return result;
	}

	public ServiceResult mulDeleteDeliverReject(String ids) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(ids)){
			result.setMessage("参数错误");
			return result;
		}
		String[] idArray = StringUtil.split(ids,GobelConstants.SPLIT_SEPARATOR);
		
		for(String deliverRejectId : idArray){
			DeliverReject deliverReject = deliverRejectDao.load(deliverRejectId);
			if(deliverReject==null || deliverReject.getStatus()==1) continue;
			deliverRejectDao.delete(deliverReject);
		}
		result.setIsSuccess(true);
		return result;
	}
	
	public ServiceResult updateStatusDeliverReject(DeliverReject deliverReject) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(deliverReject.getDeliverRejectId())){
			result.setMessage("请选择要修改状态的退货单");
			return result;
		}
		DeliverReject model = deliverRejectDao.load(deliverReject.getDeliverRejectId());
		
		if(model==null){
			result.setMessage("要修改状态的退货单已不存在");
			return result;
		}
		if(model.getStatus().intValue()==deliverReject.getStatus().intValue()){
			result.setMessage("要修改状态的退货单已是要修改的状态，请刷新界面");
			return result;
		}
		String[] propertyNames = {"warehouse","product"};
		Warehouse warehouse = model.getWarehouse();
		if(deliverReject.getStatus()==1){//如果是由未审改为已审
			//将该退货单下的商品入库
			List<DeliverRejectDetail> deliverRejectDetailList = deliverRejectDetailDao.queryDeliverRejectDetail(model);
			//更新对应商品的库存明细
			for (DeliverRejectDetail deliverRejectDetail : deliverRejectDetailList) {
				Product product = deliverRejectDetail.getProduct();
				Object[] values = {warehouse,product};
				Store store = storeDao.load(propertyNames, values);
				if(store==null){
					store = new Store();
					store.setWarehouse(model.getWarehouse());
					store.setProduct(product);
					store.setQty(0.0);
					store.setAmount(0.0);
					storeDao.save(store);
				}
				Double price = 0.0;
				if(store.getQty()!=null && store.getQty()>0){
					price = store.getAmount()/store.getQty();
				}
				//计算金额
				DecimalFormat df = new DecimalFormat("######0.00");
				Double amount = Double.parseDouble(df.format(price*deliverRejectDetail.getQty()));
				//更新库存
				store.setQty(store.getQty()+deliverRejectDetail.getQty());
				store.setAmount(store.getAmount()+amount);
				//更新商品中库存数量和金额
				product.setQtyStore(product.getQtyStore()+deliverRejectDetail.getQty());
				product.setAmountStore(product.getAmountStore()+deliverRejectDetail.getAmount());
			}
		}else if(deliverReject.getStatus()==0){//如果是由已审改为未审
			//将该退货单下的商品明细
			List<DeliverRejectDetail> deliverRejectDetailList = deliverRejectDetailDao.queryDeliverRejectDetail(model);
			//更新对应商品的库存数量
			for (DeliverRejectDetail deliverRejectDetail : deliverRejectDetailList) {
				Product product = deliverRejectDetail.getProduct();
				Object[] values = {warehouse,product};
				Store store = storeDao.load(propertyNames, values);
				if(store==null || store.getQty()-deliverRejectDetail.getQty()<0){
					throw new RuntimeException("商品:"+product.getProductName()+",超额退货");
				}
				Double price = 0.0;
				if(store.getQty()!=null && store.getQty()>0){
					price = store.getAmount()/store.getQty();
				}
				//计算金额
				DecimalFormat df = new DecimalFormat("######0.00");
				Double amount = Double.parseDouble(df.format(price*deliverRejectDetail.getQty()));
				//更新库存
				store.setQty(store.getQty()-deliverRejectDetail.getQty());
				store.setAmount(store.getAmount()-amount);
				//更新商品中库存数量和金额
				product.setQtyStore(product.getQtyStore()-deliverRejectDetail.getQty());
				product.setAmountStore(product.getAmountStore()-deliverRejectDetail.getAmount());
			}
		}
		
		Bank bank = model.getBank();
		if(bank!=null){
			//更新对应银行的账户金额
			if(deliverReject.getStatus()==1){//如果是由未审改为已审
				bank.setAmount(bank.getAmount()-model.getPayedAmount());
			}else if(model.getStatus()==0){//如果是由已审改为未审
				bank.setAmount(bank.getAmount()+model.getPayedAmount());
			}
		}
		model.setStatus(deliverReject.getStatus());
		result.setIsSuccess(true);
		return result;
	}
	
	public ServiceResult mulUpdateStatusDeliverReject(String ids, Integer status) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(ids) || status==null){
			result.setMessage("参数错误");
			return result;
		}
		String[] propertyNames = {"warehouse","product"};
		String[] idArray = StringUtil.split(ids,GobelConstants.SPLIT_SEPARATOR);
		for(String deliverRejectId : idArray){
			DeliverReject model = deliverRejectDao.load(deliverRejectId);
			//如果不存在或者状态相同,直接跳过
			if(model==null || model.getStatus().intValue()==status.intValue()) continue;
			
			if(status==1){//如果是由未审改为已审
				//将该退货单下的商品入库
				List<DeliverRejectDetail> deliverRejectDetailList = deliverRejectDetailDao.queryDeliverRejectDetail(model);
				//更新对应商品的库存明细
				for (DeliverRejectDetail deliverRejectDetail : deliverRejectDetailList) {
					Product product = deliverRejectDetail.getProduct();
					Object[] values = {model.getWarehouse(),product};
					Store store = storeDao.load(propertyNames, values);
					if(store==null){
						store = new Store();
						store.setWarehouse(model.getWarehouse());
						store.setProduct(product);
						store.setQty(0.0);
						store.setAmount(0.0);
						storeDao.save(store);
					}
					Double price = 0.0;
					if(store.getQty()!=null && store.getQty()>0){
						price = store.getAmount()/store.getQty();
					}
					//计算金额
					DecimalFormat df = new DecimalFormat("######0.00");
					Double amount = Double.parseDouble(df.format(price*deliverRejectDetail.getQty()));
					//更新库存
					store.setQty(store.getQty()+deliverRejectDetail.getQty());
					store.setAmount(store.getAmount()+amount);
					//更新商品中库存数量和金额
					product.setQtyStore(product.getQtyStore()+deliverRejectDetail.getQty());
					product.setAmountStore(product.getAmountStore()+deliverRejectDetail.getAmount());
				}
			}else if(status==0){//如果是由已审改为未审
				//将该退货单下的商品明细
				List<DeliverRejectDetail> deliverRejectDetailList = deliverRejectDetailDao.queryDeliverRejectDetail(model);
				//更新对应商品的库存数量
				for (DeliverRejectDetail deliverRejectDetail : deliverRejectDetailList) {
					Product product = deliverRejectDetail.getProduct();
					Object[] values = {model.getWarehouse(),product};
					Store store = storeDao.load(propertyNames, values);
					if(store==null || store.getQty()-deliverRejectDetail.getQty()<0){
						throw new RuntimeException("退货单号:"+deliverRejectDetail.getDeliverReject().getDeliverRejectCode()+",商品:"+product.getProductName()+",超额退货");
					}
					Double price = 0.0;
					if(store.getQty()!=null && store.getQty()>0){
						price = store.getAmount()/store.getQty();
					}
					//计算金额
					DecimalFormat df = new DecimalFormat("######0.00");
					Double amount = Double.parseDouble(df.format(price*deliverRejectDetail.getQty()));
					//更新库存
					store.setQty(store.getQty()-deliverRejectDetail.getQty());
					store.setAmount(store.getAmount()-amount);
					//更新商品中库存数量和金额
					product.setQtyStore(product.getQtyStore()-deliverRejectDetail.getQty());
					product.setAmountStore(product.getAmountStore()-deliverRejectDetail.getAmount());
				}
			}
			
			Bank bank = model.getBank();
			if(bank!=null){
				//更新对应银行的账户金额
				if(status==1){//如果是由未审改为已审
					bank.setAmount(bank.getAmount()-model.getPayedAmount());
				}else if(model.getStatus()==0){//如果是由已审改为未审
					bank.setAmount(bank.getAmount()+model.getPayedAmount());
				}
			}
			model.setStatus(status);
		}
		result.setIsSuccess(true);
		return result;
	}

	public String initDeliverReject(String deliverRejectId) {
		ServiceResult result = new ServiceResult(false);
		DeliverReject deliverReject = deliverRejectDao.load(deliverRejectId);
		String[] propertiesDeliverReject = {"deliverRejectId","deliverRejectCode","deliverRejectDate","sourceCode",
				"customer.customerId","customer.customerName","warehouse.warehouseId","amount","payedAmount","invoiceType.invoiceTypeId",
				"bank.bankId","employee.employeeId","note","status"};
		String deliverRejectData = JSONUtil.toJson(deliverReject,propertiesDeliverReject);
		result.addData("deliverRejectData",deliverRejectData);
		
		List<DeliverRejectDetail> deliverRejectDetailList = deliverRejectDetailDao.queryDeliverRejectDetail(deliverReject);
		
		String[] propertiesDetail = {"deliverRejectDetailId","product.productId","product.productCode","product.productName",
				"product.size.dataDictionaryName:sizeName","product.unit.dataDictionaryName:unitName","color.dataDictionaryName:colorName","color.dataDictionaryId:colorId",
				"qty","price","amount","note1","note2","note3"};
		String detailData = JSONUtil.toJson(deliverRejectDetailList,propertiesDetail);
		result.addData("detailData", detailData);
		result.setIsSuccess(true);
		return result.toJSON();
	}
	
	public String queryDeliverReject(Integer pageNumber, Integer pageSize, DeliverReject deliverReject, Date beginDate, Date endDate) {
		Pager pager = new Pager(pageNumber, pageSize);
		pager = deliverRejectDao.queryDeliverReject(pager, deliverReject, beginDate, endDate);
		String[] properties = {"deliverRejectId","invoiceType.invoiceTypeName","employee.employeeName","customer.customerName","warehouse.warehouseName",
				"bank.bankName","deliverRejectCode","sourceCode","deliverRejectDate","amount","payedAmount","status","note"};
		String jsonArray = JSONUtil.toJson(pager.getList(), properties, pager.getTotalCount());
		return jsonArray;
	}

}
