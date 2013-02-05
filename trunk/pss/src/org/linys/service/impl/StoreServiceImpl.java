package org.linys.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.linys.dao.ProductDAO;
import org.linys.dao.StoreDAO;
import org.linys.dao.WarehouseDAO;
import org.linys.model.Product;
import org.linys.model.Store;
import org.linys.model.Warehouse;
import org.linys.service.StoreService;
import org.linys.util.JSONUtil;
import org.linys.util.StringUtil;
import org.linys.vo.GobelConstants;
import org.linys.vo.ServiceResult;
import org.springframework.stereotype.Service;
@Service
public class StoreServiceImpl extends BaseServiceImpl<Store, String> implements StoreService {
	@Resource
	private StoreDAO storeDAO;
	@Resource
	private ProductDAO productDAO;
	@Resource
	private WarehouseDAO warehouseDAO;
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.StoreService#query(org.linys.model.Store, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public ServiceResult query(Store model, Integer page, Integer rows) {
		ServiceResult result = new ServiceResult(false);
		List<Map<String, Object>> listMap = storeDAO.query(model,page,rows);
		String datagridData = JSONUtil.toJsonFromListMap(listMap);
		result.addData("datagridData", datagridData);
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.StoreService#getTotalCount(org.linys.model.Store)
	 */
	@Override
	public ServiceResult getTotalCount(Store model) {
		ServiceResult result = new ServiceResult(false);
		Long total = storeDAO.getTotalCount(model);
		result.addData("total", total);
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.StoreService#selectReject(org.linys.model.Store, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public ServiceResult selectReject(Store model, Integer page, Integer rows) {
		ServiceResult result = new ServiceResult(false);
		List<Store> list = storeDAO.selectReject(model,page,rows);
		Long total = storeDAO.getTotalCountSelectReject(model);
		
		String[] properties = {"product.productId","product.productCode","product.productName","product.note",
				"product.productType.productTypeId","product.productType.productTypeName",
				"product.unit.dataDictionaryId:unitId","product.unit.dataDictionaryName:unitName",
				"product.color.dataDictionaryId:colorId","product.color.dataDictionaryName:colorName",
				"product.size.dataDictionaryId:sizeId","product.size.dataDictionaryName:sizeName",
				"qty","amount"};
		String datagridData = JSONUtil.toJson(list, properties, total);
		result.addData("datagridData", datagridData);
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.StoreService#queryByProduct(org.linys.model.Store, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public ServiceResult queryByProduct(Store model, Integer page, Integer rows) {
		ServiceResult result = new ServiceResult(false);
		List<Store> list = storeDAO.selectReject(model,page,rows);
		Long total = storeDAO.getTotalCountSelectReject(model);
		
		String[] properties = {"product.productId","product.productCode","product.productName","product.note",
				"product.productType.productTypeId","product.productType.productTypeName",
				"product.unit.dataDictionaryId:unitId","product.unit.dataDictionaryName:unitName",
				"product.color.dataDictionaryId:colorId","product.color.dataDictionaryName:colorName",
				"product.size.dataDictionaryId:sizeId","product.size.dataDictionaryName:sizeName",
				"qty","amount","warehouse.warehouseId","warehouse.warehouseName"};
		String datagridData = JSONUtil.toJson(list, properties, total);
		result.addData("datagridData", datagridData);
		result.setIsSuccess(true);
		return result;
	}
	public String queryProductStore(String ids, String ids2) {
		if(StringUtils.isEmpty(ids) || StringUtils.isEmpty(ids2)){
			return JSONUtil.EMPTYJSON;
		}
		String[] productIdArray = StringUtil.split(ids, GobelConstants.SPLIT_SEPARATOR);
		String[] warehouseIdArray = StringUtil.split(ids2, GobelConstants.SPLIT_SEPARATOR);
		List<Store> list = new ArrayList<Store>();
		
		for(int i=0; i<productIdArray.length;i++){
			Product product = productDAO.load(productIdArray[i]);
			Warehouse warehouse = warehouseDAO.load(warehouseIdArray[i]);
			
			Store store = storeDAO.getStore(product, warehouse);
			if(store==null){
				store = new Store();
				store.setProduct(product);
				store.setWarehouse(warehouse);
				store.setQty(0d);
			}
			list.add(store);
		}
		String[] properties = {"product.productCode","product.productName","product.productType.productTypeName",
				"product.unit.dataDictionaryName:unitName","product.color.dataDictionaryName:colorName",
				"product.size.dataDictionaryName:sizeName","qty","warehouse.warehouseName"};
		String jsonArray = JSONUtil.toJson(list, properties);
		return jsonArray;
	}
	
	

}
