package org.linys.service.impl;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.linys.bean.Pager;
import org.linys.dao.CommonDAO;
import org.linys.dao.PackagingDao;
import org.linys.dao.PackagingDetailDao;
import org.linys.dao.PrefixDAO;
import org.linys.dao.StoreDAO;
import org.linys.model.Packaging;
import org.linys.model.PackagingDetail;
import org.linys.model.Product;
import org.linys.model.Store;
import org.linys.model.Warehouse;
import org.linys.service.PackagingService;
import org.linys.util.JSONUtil;
import org.linys.util.StringUtil;
import org.linys.vo.GobelConstants;
import org.linys.vo.ServiceResult;
import org.springframework.stereotype.Service;

/**
 * 
 * @Description: 组装service实现类
 * @Copyright: 福州骏华科技信息有限公司 (c)2013
 * @Created Date : 2013-2-3
 * @author longweier
 * @vesion 1.0
 */
@Service
public class PackagingServiceImpl extends BaseServiceImpl<Packaging, String> implements PackagingService {

	@Resource
	private PackagingDao packagingDao;
	@Resource
	private PackagingDetailDao packagingDetailDao;
	@Resource
	private CommonDAO commonDao;
	@Resource
	private PrefixDAO prefixDao;
	@Resource
	private StoreDAO storeDao;
	
	public ServiceResult addPackaging(Packaging packaging, String productIds,String qtys, String prices,String warehouseIds,
			String note1s, String note2s,String note3s) {
		ServiceResult result = new ServiceResult(false);
		if(packaging.getWarehouse()==null || StringUtils.isEmpty(packaging.getWarehouse().getWarehouseId())){
			result.setMessage("仓库不能为空");
			return result;
		}
		if(packaging.getProduct()==null || StringUtils.isEmpty(packaging.getProduct().getProductId())){
			result.setMessage("组装货品不能为空");
			return result;
		}
		if(packaging.getPackagingDate()==null){
			result.setMessage("组装日期不能为空");
			return result;
		}
		if(packaging.getQty()==null || packaging.getQty()<=0){
			result.setMessage("组装数量必须大于零");
			return result;
		}
		if(packaging.getEmployee()==null || StringUtils.isEmpty(packaging.getEmployee().getEmployeeId())){
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
			if(productIdArray[i].equals(packaging.getProduct().getProductId())){
				result.setMessage("组装明细不能包含需要组装的产品");
				return result;
			}
		}
		packaging.setPackagingCode(commonDao.getCode(Packaging.class.getName(), "packagingCode", prefixDao.getPrefix("packaging")));
		packaging.setStatus(0);
		if(packaging.getPrice()==null){
			packaging.setPrice(0.0);
		}
		packagingDao.save(packaging);
		
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
			
			
			PackagingDetail packagingDetail = new PackagingDetail();
			packagingDetail.setPackaging(packaging);
			packagingDetail.setWarehouse(warehouse);
			packagingDetail.setProduct(product);
			packagingDetail.setQty(Integer.parseInt(qty));
			packagingDetail.setPrice(price==null?0.0:Double.parseDouble(price));
			packagingDetail.setNote1(note1);
			packagingDetail.setNote2(note2);
			packagingDetail.setNote3(note3);
			
			packagingDetailDao.save(packagingDetail);
		}
		result.addData("packagingId", packaging.getPackagingId());
		result.setIsSuccess(true);
		return result;
	}

	public ServiceResult updatePackaging(Packaging packaging,String packagingDetailIds, String delPackagingDetailIds,
			String productIds, String qtys, String prices,String warehouseIds, String note1s,
			String note2s, String note3s) {
		ServiceResult result = new ServiceResult(false);
		if(packaging.getWarehouse()==null || StringUtils.isEmpty(packaging.getWarehouse().getWarehouseId())){
			result.setMessage("仓库不能为空");
			return result;
		}
		if(packaging.getProduct()==null || StringUtils.isEmpty(packaging.getProduct().getProductId())){
			result.setMessage("组装货品不能为空");
			return result;
		}
		if(packaging.getPackagingDate()==null){
			result.setMessage("组装日期不能为空");
			return result;
		}
		if(packaging.getQty()==null || packaging.getQty()<=0){
			result.setMessage("组装数量必须大于零");
			return result;
		}
		if(packaging.getEmployee()==null || StringUtils.isEmpty(packaging.getEmployee().getEmployeeId())){
			result.setMessage("经办人不能为空");
			return result;
		}
		
		String[] packagingDetailIdArray = StringUtil.split(packagingDetailIds, GobelConstants.SPLIT_SEPARATOR);
		String[] delPackagingDetailIdArray = StringUtil.split(delPackagingDetailIds, GobelConstants.SPLIT_SEPARATOR);
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
		}
		Packaging model = packagingDao.load(packaging.getPackagingId());
		if(model.getStatus()==1){
			result.setMessage("该组装单已审核,不能修改");
			return result;
		}else{
			packagingDao.evict(model);
		}
		packaging.setStatus(0);
		if(packaging.getPrice()==null){
			packaging.setPrice(0.0);
		}
		packagingDao.update(packaging);
		
		for(String packagingDetailId : delPackagingDetailIdArray){
			if(StringUtils.isEmpty(packagingDetailId)) continue;
			PackagingDetail packagingDetail = packagingDetailDao.load(packagingDetailId);
			if(packagingDetail==null) continue;
			packagingDetailDao.delete(packagingDetail);
		}
		
		for(int i=0;i<productIdArray.length;i++){
			
			String packagingDetailId = packagingDetailIdArray[i];
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
			
			
			PackagingDetail packagingDetail = null;
			if(StringUtils.isNotEmpty(packagingDetailId)){
				packagingDetail = packagingDetailDao.load(packagingDetailId);
			}else{
				packagingDetail = new PackagingDetail();
			}
			if(packagingDetail==null) continue;
			
			packagingDetail.setPackaging(packaging);
			packagingDetail.setWarehouse(warehouse);
			packagingDetail.setProduct(product);
			packagingDetail.setQty(Integer.parseInt(qty));
			packagingDetail.setPrice(price==null?0.0:Double.parseDouble(price));
			packagingDetail.setNote1(note1);
			packagingDetail.setNote2(note2);
			packagingDetail.setNote3(note3);
			
			if(StringUtils.isEmpty(packagingDetailId)){
				packagingDetailDao.save(packagingDetail);
			}
		}
		result.addData("packagingId", packaging.getPackagingId());
		result.setIsSuccess(true);
		return result;
	}
	
	public ServiceResult deletePackaging(String packagingId) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(packagingId)){
			result.setMessage("参数错误");
			return result;
		}
		Packaging packaging = packagingDao.load(packagingId);
		if(packaging==null){
			result.setMessage("该组装单已被删除");
			return result;
		}
		if(packaging.getStatus()==1){
			result.setMessage("该组装单已审核,不能删除");
			return result;
		}
		packagingDao.delete(packaging);
		result.setIsSuccess(true);
		return result;
	}
	
	public ServiceResult mulDeletePackaging(String ids) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(ids)){
			result.setMessage("参数错误");
			return result;
		}
		String[] idArray = StringUtil.split(ids, GobelConstants.SPLIT_SEPARATOR);
		for(String packagingId : idArray){
			Packaging packaging = packagingDao.load(packagingId);
			if(packaging==null || packaging.getStatus()==1) continue;
			packagingDao.delete(packaging);
		}
		result.setIsSuccess(true);
		return result;
	}

	public ServiceResult updateStatus(Packaging packaging) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(packaging.getPackagingId()) || packaging.getStatus()==null){
			result.setMessage("参数错误");
			return result;
		}
		Packaging model = packagingDao.load(packaging.getPackagingId());
		if(model==null){
			result.setMessage("该组装单已被删除");
			return result;
		}
		if(model.getStatus().intValue()==packaging.getStatus().intValue()){
			result.setMessage("该组装单已是该状态");
			return result;
		}
		String[] propertyNames = {"warehouse","product"};
		//由未审修改为已审
		if(packaging.getStatus()==1){
			Product packProduct = model.getProduct();
			Object[] values = {model.getWarehouse(),packProduct};
			Store packStore = storeDao.load(propertyNames, values);
			if(packStore==null){
				packStore = new Store();
				packStore.setWarehouse(model.getWarehouse());
				packStore.setProduct(packProduct);
				packStore.setQty(0.0);
				packStore.setAmount(0.0);
				storeDao.save(packStore);
			}
			//更新组装商品库存
			packStore.setQty(packStore.getQty()+model.getQty());
			//更新商品数量
			packProduct.setQtyStore(packProduct.getQtyStore()+model.getQty());
			packProduct.setAmountStore(packProduct.getAmountStore()+model.getAmount());
			//修改库存单价
			Double priceStore =packProduct.getQtyStore()==0?0:packProduct.getAmountStore()/packProduct.getQtyStore();
			packProduct.setPriceStore(priceStore);
			
			List<PackagingDetail> list = packagingDetailDao.queryByPackaging(model);
			
			for(PackagingDetail packagingDetail : list){
				Product detailProduct = packagingDetail.getProduct();
				values =new Object[]{packagingDetail.getWarehouse(),detailProduct};
				Store detailStore = storeDao.load(propertyNames, values);
				
				if(detailStore==null || detailStore.getQty()-model.getQty()*packagingDetail.getQty()<0){
					throw new RuntimeException("商品"+detailProduct.getProductName()+",数量不足");
				}
				Double price = detailProduct.getPriceStore();
				if(price==null){
					price = 0.0;
				}
				//计算金额
				DecimalFormat df = new DecimalFormat("######0.00");
				Double amount = Double.parseDouble(df.format(price*model.getQty()*packagingDetail.getQty()));
				//更新组装商品明细库存
				detailStore.setQty(detailStore.getQty()-model.getQty()*packagingDetail.getQty());
				//更新商品明细数量
				detailProduct.setQtyStore(detailProduct.getQtyStore()-model.getQty()*packagingDetail.getQty());
				detailProduct.setAmountStore(detailProduct.getAmountStore()-amount);
			}
		//由已审修改为未审
		}else{
			Product packProduct = model.getProduct();
			Object[] values = {model.getWarehouse(),packProduct};
			Store packStore = storeDao.load(propertyNames, values);
			if(packStore==null || packStore.getQty()-model.getQty()<0){
				throw new RuntimeException("数量不足,不能反审");
			}
			//更新组装商品库存
			packStore.setQty(packStore.getQty()-model.getQty());
			//更新商品数量
			packProduct.setQtyStore(packProduct.getQtyStore()-model.getQty());
			packProduct.setAmountStore(packProduct.getAmountStore()-model.getAmount());
			//修改库存单价
			Double priceStore =packProduct.getQtyStore()==0?0:packProduct.getAmountStore()/packProduct.getQtyStore();
			packProduct.setPriceStore(priceStore);
			
			List<PackagingDetail> list = packagingDetailDao.queryByPackaging(model);
			
			for(PackagingDetail packagingDetail : list){
				Product detailProduct = packagingDetail.getProduct();
				values =new Object[]{packagingDetail.getWarehouse(),detailProduct};
				Store detailStore = storeDao.load(propertyNames, values);
				Double price = detailProduct.getPriceStore();
				if(price==null){
					price = 0.0;
				}
				//计算金额
				DecimalFormat df = new DecimalFormat("######0.00");
				Double amount = Double.parseDouble(df.format(price*model.getQty()*packagingDetail.getQty()));
				//更新组装商品明细库存
				detailStore.setQty(detailStore.getQty()+model.getQty()*packagingDetail.getQty());
				//更新商品明细数量
				detailProduct.setQtyStore(detailProduct.getQtyStore()+model.getQty()*packagingDetail.getQty());
				detailProduct.setAmountStore(detailProduct.getAmountStore()+amount);
			}
		}
		model.setStatus(packaging.getStatus());
		result.setIsSuccess(true);
		return result;
	}
	
	public ServiceResult mulUpdateStatus(String ids, Integer status) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(ids) || status==null){
			result.setMessage("参数错误");
			return result;
		}
		String[] idArray = StringUtil.split(ids, GobelConstants.SPLIT_SEPARATOR);
		for(String packagingId : idArray){
			Packaging model = packagingDao.load(packagingId);
			if(model==null || model.getStatus().intValue()==status.intValue()) continue;
			String[] propertyNames = {"warehouse","product"};
			//由未审修改为已审
			if(status==1){
				Product packProduct = model.getProduct();
				Object[] values = {model.getWarehouse(),packProduct};
				Store packStore = storeDao.load(propertyNames, values);
				if(packStore==null){
					packStore = new Store();
					packStore.setWarehouse(model.getWarehouse());
					packStore.setProduct(packProduct);
					packStore.setQty(0.0);
					packStore.setAmount(0.0);
					storeDao.save(packStore);
				}
				//更新组装商品库存
				packStore.setQty(packStore.getQty()+model.getQty());
				//更新商品数量
				packProduct.setQtyStore(packProduct.getQtyStore()+model.getQty());
				packProduct.setAmountStore(packProduct.getAmountStore()+model.getAmount());
				//修改库存单价
				Double priceStore =packProduct.getQtyStore()==0?0:packProduct.getAmountStore()/packProduct.getQtyStore();
				packProduct.setPriceStore(priceStore);
				
				List<PackagingDetail> list = packagingDetailDao.queryByPackaging(model);
				
				for(PackagingDetail packagingDetail : list){
					Product detailProduct = packagingDetail.getProduct();
					values =new Object[]{packagingDetail.getWarehouse(),detailProduct};
					Store detailStore = storeDao.load(propertyNames, values);
					
					if(detailStore==null || detailStore.getQty()-packagingDetail.getQty()<0){
						throw new RuntimeException("组装单号:"+model.getPackagingCode()+"商品:"+detailProduct.getProductName()+",数量不足");
					}
					Double price = detailProduct.getPriceStore();
					if(price==null){
						price = 0.0;
					}
					//计算金额
					DecimalFormat df = new DecimalFormat("######0.00");
					Double amount = Double.parseDouble(df.format(price*model.getQty()*packagingDetail.getQty()));
					//更新组装商品明细库存
					detailStore.setQty(detailStore.getQty()-model.getQty()*packagingDetail.getQty());
					//更新商品明细数量
					detailProduct.setQtyStore(detailProduct.getQtyStore()-model.getQty()*packagingDetail.getQty());
					detailProduct.setAmountStore(detailProduct.getAmountStore()-amount);
				}
			//由已审修改为未审
			}else{
				Product packProduct = model.getProduct();
				Object[] values = {model.getWarehouse(),packProduct};
				Store packStore = storeDao.load(propertyNames, values);
				if(packStore==null || packStore.getQty()-model.getQty()<0){
					throw new RuntimeException("组装单号:"+model.getPackagingCode()+",数量不足,不能反审");
				}
				//更新组装商品库存
				packStore.setQty(packStore.getQty()-model.getQty());
				//更新商品数量
				packProduct.setQtyStore(packProduct.getQtyStore()-model.getQty());
				packProduct.setAmountStore(packProduct.getAmountStore()-model.getAmount());
				//修改库存单价
				Double priceStore =packProduct.getQtyStore()==0?0:packProduct.getAmountStore()/packProduct.getQtyStore();
				packProduct.setPriceStore(priceStore);
				
				List<PackagingDetail> list = packagingDetailDao.queryByPackaging(model);
				
				for(PackagingDetail packagingDetail : list){
					Product detailProduct = packagingDetail.getProduct();
					values =new Object[]{packagingDetail.getWarehouse(),detailProduct};
					Store detailStore = storeDao.load(propertyNames, values);
					Double price = detailProduct.getPriceStore();
					if(price==null){
						price = 0.0;
					}
					//计算金额
					DecimalFormat df = new DecimalFormat("######0.00");
					Double amount = Double.parseDouble(df.format(price*model.getQty()*packagingDetail.getQty()));
					//更新组装商品明细库存
					detailStore.setQty(detailStore.getQty()+model.getQty()*packagingDetail.getQty());
					//更新商品明细数量
					detailProduct.setQtyStore(detailProduct.getQtyStore()+model.getQty()*packagingDetail.getQty());
					detailProduct.setAmountStore(detailProduct.getAmountStore()+amount);
				}
			}
			model.setStatus(status);
		}
		result.setIsSuccess(true);
		return result;
	}

	public String init(String packagingId) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(packagingId)){
			result.setMessage("参数错误");
			return result.toJSON();
		}
		Packaging packaging = packagingDao.load(packagingId);
		String[] packagingProperties = {"packagingId","employee.employeeId","warehouse.warehouseId","product.productId","amount",
									    "product.productName","packagingCode","packagingDate","qty","price","note","status"};
		
		String packagingData = JSONUtil.toJson(packaging,packagingProperties);
		result.addData("packagingData",packagingData);
		
		List<PackagingDetail> list = packagingDetailDao.queryByPackaging(packaging);
		
		String[] propertiesDetail = {"packagingDetailId","product.productId","product.productCode","product.productName",
				"product.size.dataDictionaryName:sizeName","product.unit.dataDictionaryName:unitName","product.color.dataDictionaryName:colorName",
				"warehouse.warehouseId","qty","price","amount","totalQty","note1","note2","note3"};
		
		String detailData = JSONUtil.toJson(list,propertiesDetail);
		result.addData("detailData", detailData);
		result.setIsSuccess(true);
		return result.toJSON();
	}
	
	public String query(Integer pageNumber, Integer pageSize,Packaging packaging, Date beginDate, Date endDate) {
		Pager pager = new Pager(pageNumber, pageSize);
		pager = packagingDao.queryPackaging(pager, packaging, beginDate, endDate);
		String[] packagingProperties = {"packagingId","employee.employeeName","warehouse.warehouseName","product.productName","amount",
				"product.size.dataDictionaryName:sizeName","product.unit.dataDictionaryName:unitName","product.color.dataDictionaryName:colorName",
				"packagingCode","packagingDate","qty","price","note","status"};
		String jsonArray = JSONUtil.toJson(pager.getList(), packagingProperties, pager.getTotalCount());
		return jsonArray;
	}

}
