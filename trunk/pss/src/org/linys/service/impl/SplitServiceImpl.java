package org.linys.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.linys.bean.Pager;
import org.linys.dao.CommonDAO;
import org.linys.dao.PrefixDAO;
import org.linys.dao.SplitDao;
import org.linys.dao.SplitDetailDao;
import org.linys.dao.StoreDAO;
import org.linys.model.Product;
import org.linys.model.Split;
import org.linys.model.SplitDetail;
import org.linys.model.Store;
import org.linys.model.Warehouse;
import org.linys.service.SplitService;
import org.linys.util.JSONUtil;
import org.linys.util.StringUtil;
import org.linys.vo.GobelConstants;
import org.linys.vo.ServiceResult;
import org.springframework.stereotype.Service;

/**
 * 
 * @Description: 拆分service实现类
 * @Copyright: 福州骏华科技信息有限公司 (c)2013
 * @Created Date : 2013-2-5
 * @author longweier
 * @vesion 1.0
 */
@Service
public class SplitServiceImpl extends BaseServiceImpl<Split, String> implements SplitService {

	@Resource
	private SplitDao splitDao;
	@Resource
	private SplitDetailDao splitDetailDao;
	@Resource
	private CommonDAO commonDao;
	@Resource
	private PrefixDAO prefixDao;
	@Resource
	private StoreDAO storeDao;
	
	public ServiceResult addSplit(Split split, String productIds, String qtys,String prices, 
			String warehouseIds, String note1s, String note2s,String note3s) {
		ServiceResult result = new ServiceResult(false);
		if(split.getWarehouse()==null || StringUtils.isEmpty(split.getWarehouse().getWarehouseId())){
			result.setMessage("仓库不能为空");
			return result;
		}
		if(split.getProduct()==null || StringUtils.isEmpty(split.getProduct().getProductId())){
			result.setMessage("拆分货品不能为空");
			return result;
		}
		if(split.getSplitDate()==null){
			result.setMessage("拆分日期不能为空");
			return result;
		}
		if(split.getQty()==null || split.getQty()<=0){
			result.setMessage("拆分数量必须大于零");
			return result;
		}
		if(split.getEmployee()==null || StringUtils.isEmpty(split.getEmployee().getEmployeeId())){
			result.setMessage("经办人不能为空");
			return result;
		}
		
		String[] productIdArray = StringUtil.split(productIds, GobelConstants.SPLIT_SEPARATOR);
		String[] qtyArray = StringUtil.split(qtys, GobelConstants.SPLIT_SEPARATOR);
		String[] priceArray = StringUtil.split(prices, GobelConstants.SPLIT_SEPARATOR);
		String[] warehouseIdArray = StringUtil.split(warehouseIds, GobelConstants.SPLIT_SEPARATOR);
		String[] note1Array = StringUtil.split(note1s, GobelConstants.SPLIT_SEPARATOR);
		String[] note2Array = StringUtil.split(note2s, GobelConstants.SPLIT_SEPARATOR);
		String[] note3Array = StringUtil.split(note3s, GobelConstants.SPLIT_SEPARATOR);
		
		for (int i = 0; i < qtyArray.length; i++) {
			String qty = qtyArray[i];
			if("0".equals(qty) ||"".equals(qty)){
				result.setMessage("第"+(i+1)+"行商品数量不能为0");
				return result;
			}
			if(StringUtils.isEmpty(warehouseIdArray[i])){
				result.setMessage("第"+(i+1)+"行商品仓库不能为空");
				return result;
			}
			if(productIdArray[i].equals(split.getProduct().getProductId())){
				result.setMessage("拆分明细不能包含需要拆分的产品");
				return result;
			}
		}
		split.setSplitCode(commonDao.getCode(Split.class.getName(), "splitCode", prefixDao.getPrefix("split")));
		split.setStatus(0);
		if(split.getPrice()==null){
			split.setPrice(0.0);
		}
		splitDao.save(split);
		
		for(int i=0;i<productIdArray.length;i++){
			
			String productId = productIdArray[i];
			String qty = qtyArray[i];
			String price = priceArray[i];
			String warehouseId = warehouseIdArray[i];
			String note1 = note1Array[i];
			String note2 = note2Array[i];
			String note3 = note3Array[i];
			
			Product product = new Product();
			product.setProductId(productId);
			
			Warehouse warehouse = new Warehouse();
			warehouse.setWarehouseId(warehouseId);
			
			
			SplitDetail splitDetail = new SplitDetail();
			splitDetail.setSplit(split);
			splitDetail.setWarehouse(warehouse);
			splitDetail.setProduct(product);
			splitDetail.setQty(Integer.parseInt(qty));
			splitDetail.setPrice(price==null?0.0:Double.parseDouble(price));
			splitDetail.setNote1(note1);
			splitDetail.setNote2(note2);
			splitDetail.setNote3(note3);
			
			splitDetailDao.save(splitDetail);
		}
		result.addData("splitId", split.getSplitId());
		result.setIsSuccess(true);
		return result;
	}

	public ServiceResult updateSplit(Split split, String splitDetailIds,String delSplitDetailIds, String productIds, 
						String qtys,String prices, String warehouseIds, String note1s, String note2s,String note3s) {
		ServiceResult result = new ServiceResult(false);
		if(split.getWarehouse()==null || StringUtils.isEmpty(split.getWarehouse().getWarehouseId())){
			result.setMessage("仓库不能为空");
			return result;
		}
		if(split.getProduct()==null || StringUtils.isEmpty(split.getProduct().getProductId())){
			result.setMessage("拆分货品不能为空");
			return result;
		}
		if(split.getSplitDate()==null){
			result.setMessage("拆分日期不能为空");
			return result;
		}
		if(split.getQty()==null || split.getQty()<=0){
			result.setMessage("拆分数量必须大于零");
			return result;
		}
		if(split.getEmployee()==null || StringUtils.isEmpty(split.getEmployee().getEmployeeId())){
			result.setMessage("经办人不能为空");
			return result;
		}
		
		String[] splitDetailIdArray = StringUtil.split(splitDetailIds, GobelConstants.SPLIT_SEPARATOR);
		String[] delSplitDetailIdArray = StringUtil.split(delSplitDetailIds, GobelConstants.SPLIT_SEPARATOR);
		String[] productIdArray = StringUtil.split(productIds, GobelConstants.SPLIT_SEPARATOR);
		String[] qtyArray = StringUtil.split(qtys, GobelConstants.SPLIT_SEPARATOR);
		String[] priceArray = StringUtil.split(prices, GobelConstants.SPLIT_SEPARATOR);
		String[] warehouseIdArray = StringUtil.split(warehouseIds, GobelConstants.SPLIT_SEPARATOR);
		String[] note1Array = StringUtil.split(note1s, GobelConstants.SPLIT_SEPARATOR);
		String[] note2Array = StringUtil.split(note2s, GobelConstants.SPLIT_SEPARATOR);
		String[] note3Array = StringUtil.split(note3s, GobelConstants.SPLIT_SEPARATOR);
		
		for (int i = 0; i < qtyArray.length; i++) {
			String qty = qtyArray[i];
			if("0".equals(qty) ||"".equals(qty)){
				result.setMessage("第"+(i+1)+"行商品数量不能为0");
				return result;
			}
			if(StringUtils.isEmpty(warehouseIdArray[i])){
				result.setMessage("第"+(i+1)+"行商品仓库不能为空");
				return result;
			}
			if(productIdArray[i].equals(split.getProduct().getProductId())){
				result.setMessage("拆分明细不能包含需要拆分的产品");
				return result;
			}
		}
		Split model = splitDao.load(split.getSplitId());
		if(model.getStatus()==1){
			result.setMessage("该拆分单已审核,不能修改");
			return result;
		}else{
			splitDao.evict(model);
		}
		split.setStatus(0);
		if(split.getPrice()==null){
			split.setPrice(0.0);
		}
		splitDao.update(split);
		
		for(String id : delSplitDetailIdArray){
			if(StringUtils.isEmpty(id)) continue;
			SplitDetail splitDetail = splitDetailDao.load(id);
			if(splitDetail==null) continue;
			splitDetailDao.delete(splitDetail);
		}
		
		for(int i=0;i<productIdArray.length;i++){
			String splitDetailId = splitDetailIdArray[i];
			String productId = productIdArray[i];
			String qty = qtyArray[i];
			String price = priceArray[i];
			String warehouseId = warehouseIdArray[i];
			String note1 = note1Array[i];
			String note2 = note2Array[i];
			String note3 = note3Array[i];
			
			Product product = new Product();
			product.setProductId(productId);
			
			Warehouse warehouse = new Warehouse();
			warehouse.setWarehouseId(warehouseId);
			
			SplitDetail splitDetail = null;
			//新增
			if(StringUtils.isEmpty(splitDetailId)){
				splitDetail = new SplitDetail(); 
			//修改
			}else{
				splitDetail = splitDetailDao.load(splitDetailId);
			}
			splitDetail.setSplit(split);
			splitDetail.setWarehouse(warehouse);
			splitDetail.setProduct(product);
			splitDetail.setQty(Integer.parseInt(qty));
			splitDetail.setPrice(price==null?0.0:Double.parseDouble(price));
			splitDetail.setNote1(note1);
			splitDetail.setNote2(note2);
			splitDetail.setNote3(note3);
			//保存
			if(StringUtils.isEmpty(splitDetailId)){
				splitDetailDao.save(splitDetail);
			}
		}
		result.addData("splitId", split.getSplitId());
		result.setIsSuccess(true);
		return result;
	}
	
	public ServiceResult delete(String splitId) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(splitId)){
			result.setMessage("参数错误");
			return result;
		}
		Split split = splitDao.load(splitId);
		if(split==null){
			result.setMessage("该拆分单已被删除");
			return result;
		}
		if(split.getStatus()==1){
			result.setMessage("该拆分单已审核,不能删除");
			return result;
		}
		splitDao.delete(split);
		result.setIsSuccess(true);
		return result;
	}

	public ServiceResult mulDelete(String ids) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(ids)){
			result.setMessage("参数错误");
			return result;
		}
		String[] splitIdArray = StringUtil.split(ids, GobelConstants.SPLIT_SEPARATOR);
		for(String splitId : splitIdArray){
			Split split = splitDao.load(splitId);
			if(split==null || split.getStatus()==1) continue;
			splitDao.delete(split);
		}
		result.setIsSuccess(true);
		return result;
	
	}
	
	public ServiceResult updateStatus(Split split) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(split.getSplitId()) || split.getStatus()==null){
			result.setMessage("参数错误");
			return result;
		}
		Split model = splitDao.load(split.getSplitId());
		if(model==null){
			result.setMessage("该拆分单已被删除");
			return result;
		}
		if(model.getStatus().intValue()==split.getStatus().intValue()){
			result.setMessage("该拆分单已是该状态");
			return result;
		}
		String[] propertyNames = {"warehouse","product"};
		//由未审修改为已审
		if(split.getStatus()==1){
			Product splitProduct = model.getProduct();
			Object[] values = {model.getWarehouse(),splitProduct};
			Store splitStore = storeDao.load(propertyNames, values);
			if(splitStore==null || splitStore.getQty()<model.getQty()){
				result.setMessage("拆分产品库存数量不足");
				return result;
			}
			//更新拆分商品库存
			splitStore.setQty(splitStore.getQty()-model.getQty());
			splitStore.setAmount(splitStore.getAmount()-model.getAmount());
			//更新商品数量
			splitProduct.setQtyStore(splitProduct.getQtyStore()-model.getQty());
			splitProduct.setAmountStore(splitProduct.getAmountStore()-model.getAmount());
			
			List<SplitDetail> list = splitDetailDao.querySplitDetail(split);
			
			for(SplitDetail splitDetail : list){
				Product detailProduct = splitDetail.getProduct();
				values =new Object[]{splitDetail.getWarehouse(),detailProduct};
				Store detailStore = storeDao.load(propertyNames, values);
				if(detailStore==null){
					detailStore = new Store();
					detailStore.setWarehouse(model.getWarehouse());
					detailStore.setProduct(splitProduct);
					detailStore.setQty(0.0);
					detailStore.setAmount(0.0);
					storeDao.save(detailStore);
				}
				//更新拆分商品明细库存
				detailStore.setQty(detailStore.getQty()+model.getQty()*splitDetail.getQty());
				detailStore.setAmount(detailStore.getAmount()+model.getQty()*splitDetail.getAmount());
				//更新商品明细数量
				detailProduct.setQtyStore(detailProduct.getQtyStore()+model.getQty()*splitDetail.getQty());
				detailProduct.setAmountStore(detailProduct.getAmountStore()+model.getQty()*splitDetail.getAmount());
			}
		//由已审修改为未审
		}else{
			Product splitProduct = model.getProduct();
			Object[] values = {model.getWarehouse(),splitProduct};
			Store soplitStore = storeDao.load(propertyNames, values);
			//更新拆分商品库存
			soplitStore.setQty(soplitStore.getQty()+model.getQty());
			soplitStore.setAmount(soplitStore.getAmount()+model.getAmount());
			//更新商品数量
			splitProduct.setQtyStore(splitProduct.getQtyStore()+model.getQty());
			splitProduct.setAmountStore(splitProduct.getAmountStore()+model.getAmount());
			
			List<SplitDetail> list = splitDetailDao.querySplitDetail(split);
			
			for(SplitDetail splitDetail : list){
				Product detailProduct = splitDetail.getProduct();
				values =new Object[]{splitDetail.getWarehouse(),detailProduct};
				Store detailStore = storeDao.load(propertyNames, values);
				if(detailStore.getQty()<model.getQty()*splitDetail.getQty()){
					throw new RuntimeException("商品"+detailProduct.getProductName()+",数量不足,不能反审");
				}
				//更新拆分商品明细库存
				detailStore.setQty(detailStore.getQty()-model.getQty()*splitDetail.getQty());
				detailStore.setAmount(detailStore.getAmount()-model.getQty()*splitDetail.getAmount());
				//更新商品明细数量
				detailProduct.setQtyStore(detailProduct.getQtyStore()-model.getQty()*splitDetail.getQty());
				detailProduct.setAmountStore(detailProduct.getAmountStore()-model.getQty()*splitDetail.getAmount());
			}
		}
		model.setStatus(split.getStatus());
		result.setIsSuccess(true);
		return result;
	}

	public ServiceResult mulUpdateStatus(String ids, Integer status) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(ids) || status==null){
			result.setMessage("参数错误");
			return result;
		}
		String[] splitIdArray = StringUtil.split(ids, GobelConstants.SPLIT_SEPARATOR);
		for(String splitId : splitIdArray){
			Split model = splitDao.load(splitId);
			if(model==null || model.getStatus().intValue()==status.intValue()) continue;
			String[] propertyNames = {"warehouse","product"};
			//由未审修改为已审
			if(status==1){
				Product splitProduct = model.getProduct();
				Object[] values = {model.getWarehouse(),splitProduct};
				Store splitStore = storeDao.load(propertyNames, values);
				if(splitStore==null || splitStore.getQty()<model.getQty()){
					throw new RuntimeException("拆分单号:"+model.getSplitCode()+",数量不足");
				}
				//更新拆分商品库存
				splitStore.setQty(splitStore.getQty()-model.getQty());
				splitStore.setAmount(splitStore.getAmount()-model.getAmount());
				//更新商品数量
				splitProduct.setQtyStore(splitProduct.getQtyStore()-model.getQty());
				splitProduct.setAmountStore(splitProduct.getAmountStore()-model.getAmount());
				
				List<SplitDetail> list = splitDetailDao.querySplitDetail(model);
				
				for(SplitDetail splitDetail : list){
					Product detailProduct = splitDetail.getProduct();
					values =new Object[]{splitDetail.getWarehouse(),detailProduct};
					Store detailStore = storeDao.load(propertyNames, values);
					if(detailStore.getQty()<model.getQty()*splitDetail.getQty()){
						throw new RuntimeException("拆分单号:"+model.getSplitCode()+",商品"+detailProduct.getProductName()+",数量不足");
					}
					//更新拆分商品明细库存
					detailStore.setQty(detailStore.getQty()+model.getQty()*splitDetail.getQty());
					detailStore.setAmount(detailStore.getAmount()+model.getQty()*splitDetail.getAmount());
					//更新商品明细数量
					detailProduct.setQtyStore(detailProduct.getQtyStore()+model.getQty()*splitDetail.getQty());
					detailProduct.setAmountStore(detailProduct.getAmountStore()+model.getQty()*splitDetail.getAmount());
				}
			//由已审修改为未审
			}else{
				Product splitProduct = model.getProduct();
				Object[] values = {model.getWarehouse(),splitProduct};
				Store soplitStore = storeDao.load(propertyNames, values);
				//更新拆分商品库存
				soplitStore.setQty(soplitStore.getQty()+model.getQty());
				soplitStore.setAmount(soplitStore.getAmount()+model.getAmount());
				//更新商品数量
				splitProduct.setQtyStore(splitProduct.getQtyStore()+model.getQty());
				splitProduct.setAmountStore(splitProduct.getAmountStore()+model.getAmount());
				
				List<SplitDetail> list = splitDetailDao.querySplitDetail(model);
				
				for(SplitDetail splitDetail : list){
					Product detailProduct = splitDetail.getProduct();
					values =new Object[]{splitDetail.getWarehouse(),detailProduct};
					Store detailStore = storeDao.load(propertyNames, values);
					if(detailStore.getQty()<model.getQty()*splitDetail.getQty()){
						throw new RuntimeException("拆分单号:"+model.getSplitCode()+",商品"+detailProduct.getProductName()+",数量不足,不能反审");
					}
					//更新拆分商品明细库存
					detailStore.setQty(detailStore.getQty()-model.getQty()*splitDetail.getQty());
					detailStore.setAmount(detailStore.getAmount()-model.getQty()*splitDetail.getAmount());
					//更新商品明细数量
					detailProduct.setQtyStore(detailProduct.getQtyStore()-model.getQty()*splitDetail.getQty());
					detailProduct.setAmountStore(detailProduct.getAmountStore()-model.getQty()*splitDetail.getAmount());
				}
			}
			model.setStatus(status);
		}
		result.setIsSuccess(true);
		return result;
	}

	public String init(String splitId) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(splitId)){
			result.setMessage("参数错误");
			return result.toJSON();
		}
		Split split = splitDao.load(splitId);
		String[] splitProperties = {"splitId","employee.employeeId","warehouse.warehouseId","product.productId","amount",
			    "product.productName","splitCode","splitDate","qty","price","note","status"};

		String splitData = JSONUtil.toJson(split,splitProperties);
		result.addData("splitData",splitData);
		
		List<SplitDetail> list = splitDetailDao.querySplitDetail(split);
		String[] propertiesDetail = {"splitDetailId","product.productId","product.productCode","product.productName",
				"product.size.dataDictionaryName:sizeName","product.unit.dataDictionaryName:unitName","product.color.dataDictionaryName:colorName",
				"warehouse.warehouseId","qty","price","amount","note1","note2","note3"};
		
		String detailData = JSONUtil.toJson(list,propertiesDetail);
		result.addData("detailData", detailData);
		result.setIsSuccess(true);
		return result.toJSON();
	}
	
	public String query(Integer pageNumber, Integer pageSize, Split split,Date beginDate, Date endDate) {
		Pager pager = new Pager(pageNumber, pageSize);
		pager = splitDao.querySplit(pager, split, beginDate, endDate);
		String[] packagingProperties = {"splitId","employee.employeeName","warehouse.warehouseName","product.productName","amount",
				"product.size.dataDictionaryName:sizeName","product.unit.dataDictionaryName:unitName","product.color.dataDictionaryName:colorName",
				"splitCode","splitDate","qty","price","note","status"};
		String jsonArray = JSONUtil.toJson(pager.getList(), packagingProperties, pager.getTotalCount());
		return jsonArray;
	}
	

}
