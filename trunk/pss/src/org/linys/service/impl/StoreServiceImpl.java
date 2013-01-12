package org.linys.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.linys.dao.StoreDAO;
import org.linys.model.Store;
import org.linys.service.StoreService;
import org.linys.util.JSONUtil;
import org.linys.vo.ServiceResult;
import org.springframework.stereotype.Service;
@Service
public class StoreServiceImpl extends BaseServiceImpl<Store, String> implements
		StoreService {
	@Resource
	private StoreDAO storeDAO;
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
	
	

}
