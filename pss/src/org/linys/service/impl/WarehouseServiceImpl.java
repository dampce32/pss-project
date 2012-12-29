package org.linys.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.linys.dao.WarehouseDAO;
import org.linys.model.Warehouse;
import org.linys.service.WarehouseService;
import org.linys.util.JSONUtil;
import org.linys.vo.ServiceResult;
import org.springframework.stereotype.Service;
@Service
public class WarehouseServiceImpl extends BaseServiceImpl<Warehouse, String>
		implements WarehouseService {
	@Resource
	private WarehouseDAO warehouseDAO;
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.WarehouseService#save(org.linys.model.Warehouse)
	 */
	@Override
	public ServiceResult save(Warehouse model) {
		ServiceResult result = new ServiceResult(false);
		if(model==null){
			result.setMessage("请填写仓库信息");
			return result;
		}
		if(StringUtils.isEmpty(model.getWarehouseCode())){
			result.setMessage("请填写仓库编号");
			return result;
		}
		if(StringUtils.isEmpty(model.getWarehouseName())){
			result.setMessage("请填写仓库名称");
			return result;
		}
		
		if(StringUtils.isEmpty(model.getWarehouseId())){//新增
			Warehouse oldModel = warehouseDAO.load("warehouseCode", model.getWarehouseCode());
			if(oldModel!=null){
				result.setMessage("该仓库编号已存在");
				return result;
			}
			warehouseDAO.save(model);
		}else{
			Warehouse oldModel = warehouseDAO.load(model.getWarehouseId());
			if(oldModel==null){
				result.setMessage("该仓库已不存在");
				return result;
			}else if(!oldModel.getWarehouseCode().equals(model.getWarehouseCode())){
				Warehouse oldWarehouse = warehouseDAO.load("warehouseCode", model.getWarehouseCode());
				if(oldWarehouse!=null){
					result.setMessage("该仓库编号已存在");
					return result;
				}
			}
			oldModel.setWarehouseCode(model.getWarehouseCode());
			oldModel.setWarehouseName(model.getWarehouseName());
			oldModel.setWarehouseContactor(model.getWarehouseContactor());
			oldModel.setWarehouseAddr(model.getWarehouseAddr());
			oldModel.setWarehouseTel(model.getWarehouseTel());
			oldModel.setWarehouseNode(model.getWarehouseNode());
			
			warehouseDAO.update(oldModel);
		}
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.WarehouseService#delete(org.linys.model.Warehouse)
	 */
	@Override
	public ServiceResult delete(Warehouse model) {
		ServiceResult result = new ServiceResult(false);
		if(model==null||StringUtils.isEmpty(model.getWarehouseId())){
			result.setMessage("请选择要删除的仓库");
			return result;
		}
		Warehouse oldModel = warehouseDAO.load(model.getWarehouseId());
		if(oldModel==null){
			result.setMessage("该仓库已不存在");
			return result;
		}else{
			warehouseDAO.delete(oldModel);
		}
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.WarehouseService#query(org.linys.model.Warehouse, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public ServiceResult query(Warehouse model, Integer page, Integer rows) {
		ServiceResult result = new ServiceResult(false);
		
		List<Warehouse> list = warehouseDAO.query(model,page,rows);
		
		String[] properties = {"warehouseId","warehouseCode","warehouseName",
				"warehouseContactor","warehouseAddr","warehouseTel","warehouseNode"};
		String data = JSONUtil.toJson(list,properties);
		result.addData("datagridData", data);
		
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.WarehouseService#getTotalCount(org.linys.model.Warehouse)
	 */
	@Override
	public ServiceResult getTotalCount(Warehouse model) {
		ServiceResult result = new ServiceResult(false);
		Long data = warehouseDAO.getTotalCount(model);
		result.addData("total", data);
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.WarehouseService#queryCombobox()
	 */
	@Override
	public String queryCombobox() {
		List<Warehouse> list = warehouseDAO.queryAll();
		String[] properties = {"warehouseId","warehouseName"};
		String jsonString = JSONUtil.toJsonWithoutRows(list,properties);
		return jsonString;
	}
}
