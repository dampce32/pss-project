package org.linys.service;

import java.util.List;

import org.linys.model.ProductType;
import org.linys.model.Right;
import org.linys.vo.ServiceResult;
/**
 * @Description:商品类别Service
 * @Copyright: 福州骏华信息有限公司 (c)2012
 * @Created Date : 2012-12-23
 * @author lys
 * @vesion 1.0
 */
public interface ProductTypeService extends BaseService<ProductType,String>{
	/**
	 * @Description: 选择商品类别的跟节点
	 * @Create: 2012-12-23 下午3:01:51
	 * @author lys
	 * @update logs
	 * @return
	 */
	String selectRoot();
	/**
	 * @Description: 单击选择展开树节点
	 * @Create: 2012-12-23 下午3:02:07
	 * @author lys
	 * @update logs
	 * @param model
	 * @return
	 */
	String selectTreeNode(ProductType model);
	/**
	 * @Description: 添加商品类别
	 * @Create: 2012-12-23 下午3:02:20
	 * @author lys
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult add(ProductType model);
	/**
	 * @Description: 取得树节点下的孩子节点
	 * @Create: 2012-12-23 下午3:02:33
	 * @author lys
	 * @update logs
	 * @param page
	 * @param rows
	 * @param model
	 * @return
	 */
	String query(Integer page, Integer rows, ProductType model);
	/**
	 * @Description: 更新商品类别
	 * @Create: 2012-12-23 下午3:29:19
	 * @author lys
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult update(ProductType model);
	/**
	 * @Description: 批量删除
	 * @Create: 2012-12-23 下午3:29:47
	 * @author lys
	 * @update logs
	 * @param ids
	 * @return
	 */
	ServiceResult mulDelete(String ids);

}
