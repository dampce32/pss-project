package org.linys.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.linys.dao.ProductTypeDAO;
import org.linys.model.ProductType;
import org.linys.service.ProductTypeService;
import org.linys.util.JSONUtil;
import org.linys.util.StringUtil;
import org.linys.util.TreeUtil;
import org.linys.vo.ServiceResult;
import org.linys.vo.TreeNode;
import org.springframework.stereotype.Service;

@Service
public class ProductTypeServiceImpl extends BaseServiceImpl<ProductType, String> implements ProductTypeService {
	
	@Resource
	private ProductTypeDAO productTypeDAO;
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.ProductTypeService#selectRoot()
	 */
	public String selectRoot() {
		List<ProductType> rootList = productTypeDAO.selectRoot();
		if(rootList==null){
			return null;
		}
		List<TreeNode> rootNodeList = new ArrayList<TreeNode>();
		
		for (int i = 0; i < rootList.size(); i++) {
			ProductType productType = rootList.get(i);
			rootNodeList.add(TreeUtil.toTreeNode(productType));
			if(!productType.getIsLeaf()){
				List<ProductType> children = productTypeDAO.selectTreeNode(productType);
				List<TreeNode> childrenNodeList = new ArrayList<TreeNode>();
				for (ProductType productType1 : children) {
					childrenNodeList.add(TreeUtil.toTreeNode(productType1));
				}
				rootNodeList.get(i).setChildren(childrenNodeList);
			}
			
		}
		return TreeUtil.toJSON(rootNodeList);
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.ProductTypeService#selectTreeNode(org.linys.model.ProductType)
	 */
	public String selectTreeNode(ProductType model) {
		List<ProductType> rootList = productTypeDAO.selectTreeNode(model);
		if(rootList==null){
			return null;
		}
		List<TreeNode> rootNodeList = new ArrayList<TreeNode>();
		for (ProductType productType : rootList) {
			rootNodeList.add(TreeUtil.toTreeNode(productType));
		}
		return TreeUtil.toJSON(rootNodeList);
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.ProductTypeService#add(org.linys.model.ProductType)
	 */
	public ServiceResult add(ProductType model) {
		ServiceResult result = new ServiceResult(false);
		if(model==null){
			result.setMessage("请填写商品类别信息");
			return result;
		}
		if(StringUtils.isEmpty(model.getProductTypeCode())){
			result.setMessage("请填写商品类别编号");
			return result;
		}
		if(StringUtils.isEmpty(model.getProductTypeName())){
			result.setMessage("请填写商品类别名称");
			return result;
		}
		//验证商品类别编号是否已存在
		ProductType oldProductType = productTypeDAO.load("productTypeCode", model.getProductTypeCode());
		if(oldProductType!=null){
			result.setMessage("商品类别编号已存在，请换个商品类型编号");
			return result;
		}
		model.setIsLeaf(true);
		productTypeDAO.save(model);
		
		if(model.getParentProductType()!=null){
			productTypeDAO.updateIsLeaf(model.getParentProductType().getProductTypeId(),false);
		}
		
		result.getData().put("productTypeId", model.getProductTypeId());
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.ProductTypeService#query(java.lang.Integer, java.lang.Integer, org.linys.model.ProductType)
	 */
	public String query(Integer page, Integer rows, ProductType model) {
		
		List<ProductType> list = productTypeDAO.query(page,rows,model);
		Long total=productTypeDAO.count(model);
		
		List<String> propertyList = new ArrayList<String>();
		propertyList.add("productTypeId");
		propertyList.add("productTypeCode");
		propertyList.add("productTypeName");
		
		return JSONUtil.toJson(list, propertyList, total);
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.ProductTypeService#update(org.linys.model.ProductType)
	 */
	public ServiceResult update(ProductType model) {
		ServiceResult result = new ServiceResult(false);
		if(model==null||StringUtils.isEmpty(model.getProductTypeId())){
			result.setMessage("请选择商品类别信息");
			return result;
		}
		if(StringUtils.isEmpty(model.getProductTypeCode())){
			result.setMessage("请填写商品类别编号");
			return result;
		}
		if(StringUtils.isEmpty(model.getProductTypeName())){
			result.setMessage("请填写商品类别名");
			return result;
		}
		ProductType oldModel = productTypeDAO.load(model.getProductTypeId());
		if(oldModel==null){
			result.setMessage("该商品类别已不存在");
			return result;
		}
		//如果修改了商品类别编号，则还需判断修改后的商品类别编号是否已存在
		if(!model.getProductTypeCode().equals(oldModel.getProductTypeCode())){
			//验证商品类别编号是否已存在
			ProductType oldProductType = productTypeDAO.load("productTypeCode", model.getProductTypeCode());
			if(oldProductType!=null){
				result.setMessage("商品类别编号已存在，请换个商品类型编号");
				return result;
			}
		}
		oldModel.setProductTypeCode(model.getProductTypeCode());
		oldModel.setProductTypeName(model.getProductTypeName());
		productTypeDAO.update(oldModel);
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.ProductTypeService#mulDelete(java.lang.String)
	 */
	public ServiceResult mulDelete(String ids) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(ids)){
			result.setMessage("请选择要删除的记录");
			return result;
		}
		String[] idArray = StringUtil.split(ids);
		if(idArray.length==0){
			result.setMessage("请选择要删除的记录");
			return result;
		}
		String parentID = null;
		ProductType item = productTypeDAO.load(idArray[0]);
		for (String id : idArray) {
			ProductType oldItem = productTypeDAO.load(id);
			if(oldItem==null){
				continue;
			}else{
				productTypeDAO.delete(oldItem);
			}
		}
		if(idArray.length>0){
			if(item!=null&&item.getParentProductType()!=null){
				parentID = item.getParentProductType().getProductTypeId();
				Long countChildren = productTypeDAO.countChildren(parentID);
				if(countChildren==0){
					productTypeDAO.updateIsLeaf(parentID, true);
				}
			}
		}
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.ProductTypeService#queryCombogrid(org.linys.model.ProductType, java.lang.Integer, java.lang.Integer)
	 */
	public String queryCombogrid(ProductType model, Integer page, Integer rows) {
		List<ProductType> list = productTypeDAO.queryCombogrid(model,page,rows);
		Long total = productTypeDAO.getTotalCountCombogrid(model);
		List<String> propertyList = new ArrayList<String>();
		propertyList.add("productTypeId");
		propertyList.add("productTypeCode");
		propertyList.add("productTypeName");
		String jsonString = JSONUtil.toJson(list,propertyList, total);
		return jsonString;
	}
}
