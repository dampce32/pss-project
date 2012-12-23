package org.linys.dao;

import java.util.List;

import org.linys.model.ProductType;
import org.linys.model.Right;
/**
 * @Description:商品类别DAO
 * @Copyright: 福州骏华信息有限公司 (c)2012
 * @Created Date : 2012-12-23
 * @author lys
 * @vesion 1.0
 */
public interface ProductTypeDAO extends BaseDAO<ProductType,String>{
	/**
	 * @Description: 查询商品类别树的跟节点
	 * @Create: 2012-12-23 下午3:08:27
	 * @author lys
	 * @update logs
	 * @return
	 */
	List<ProductType> selectRoot();
	/**
	 * @Description:  查询商品类别树的子节点
	 * @Create: 2012-12-23 下午3:15:24
	 * @author lys
	 * @update logs
	 * @param model
	 * @return
	 */
	List<ProductType> selectTreeNode(ProductType model);
	/**
	 * @Description: 分页查询商品类型
	 * @Create: 2012-12-23 下午3:24:16
	 * @author lys
	 * @update logs
	 * @param page
	 * @param rows
	 * @param model
	 * @return
	 */
	List<ProductType> query(Integer page, Integer rows, ProductType model);
	/**
	 * @Description: 统计商品类别
	 * @Create: 2012-12-23 下午3:24:39
	 * @author lys
	 * @update logs
	 * @param model
	 * @return
	 */
	Long count(ProductType model);
	/**
	 * @Description: 更新节点是否为叶子节点
	 * @Create: 2012-12-23 下午4:51:07
	 * @author lys
	 * @update logs
	 * @param productTypeId
	 * @param isLeaf
	 */
	void updateIsLeaf(String productTypeId, boolean isLeaf);
	/**
	 * @Description: 统计节点下的孩子节点数
	 * @Create: 2012-12-23 下午4:54:36
	 * @author lys
	 * @update logs
	 * @param parentID
	 * @return
	 */
	Long countChildren(String parentID);

}
