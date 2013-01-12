package org.linys.action;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.linys.model.ProductType;
import org.linys.service.ProductTypeService;
import org.linys.vo.ServiceResult;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
/**
 * @Description:商品类型Action
 * @Copyright: 福州骏华信息有限公司 (c)2012
 * @Created Date : 2012-12-23
 * @author lys
 * @vesion 1.0
 */
@Controller
@Scope("prototype")
public class ProductTypeAction extends BaseAction implements ModelDriven<ProductType> {
	
	private static final long serialVersionUID = 7361849093296061199L;
	private static final Logger logger = Logger.getLogger(ProductTypeAction.class);
	@Resource
	private ProductTypeService productTypeService;
	ProductType model = new  ProductType();
	public ProductType getModel() {
		return model;
	}
	
	/**
	 * @Description: 选择商品类别的跟节点
	 * @Create: 2012-10-14 下午10:24:49
	 * @author lys
	 * @update logs
	 * @throws Exception
	 */
	public void selectRoot() {
		try {
			String jsonString = productTypeService.selectRoot();
			ajaxJson(jsonString);
		} catch (Exception e) {
			logger.error("选择商品类别的跟节点失败", e);
		}
	}
	/**
	 * @Description: 单击选择展开树节点
	 * @Create: 2012-10-27 下午3:21:25
	 * @author lys
	 * @update logs
	 * @throws Exception
	 */
	public void selectTreeNode(){
		String jsonString = productTypeService.selectTreeNode(model);
		ajaxJson(jsonString);
	}
	/**
	 * @Description: 添加商品类别
	 * @Create: 2012-10-26 下午10:48:00
	 * @author lys
	 * @update logs
	 * @throws Exception
	 */
	public void add(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = productTypeService.add(model);
		} catch (Exception e) {
			result.setMessage("添加商品类别失败");
			logger.error("添加商品类别失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description: 取得树节点下的孩子节点
	 * @Create: 2012-10-27 上午9:46:10
	 * @author lys
	 * @update logs
	 * @throws Exception
	 */
	public void getTreeNodeChildren(){
		try {
			String jsonArray = productTypeService.query(page, rows, model);
			ajaxJson(jsonArray);
		} catch (Throwable e) {
			logger.error("取得树节点下的孩子节点失败", e);
		}
	}
	
	/**
	 * @Description: 更新商品类别
	 * @Create: 2012-10-27 上午11:22:47
	 * @author lys
	 * @update logs
	 * @throws Exception
	 */
	public void update() {
		ServiceResult result = new ServiceResult(false);	
		try {
			result = productTypeService.update(model);
		} catch (Exception e) {
			result.setMessage("更新商品类别失败");
		}
		ajaxJson(result.toJSON());
	}
	/**
	 * @Description: 批量删除
	 * @Create: 2012-10-27 下午12:00:30
	 * @author lys
	 * @update logs
	 * @throws Exception
	 */
	public void mulDelete(){
		ServiceResult result = new ServiceResult(false);	
		try {
			result = productTypeService.mulDelete(ids);
		} catch (Throwable e) {
			logger.error("删除银行失败", e);
			result.setMessage("删除银行失败");
		}
		ajaxJson(result.toJSON());
	}
	/**
	 * @Description: 分页查询商品类型(用于combogrid)
	 * @Create: 2012-12-25 下午10:48:37
	 * @author lys
	 * @update logs
	 */
	public void queryCombogrid(){
		try {
			model.setProductTypeName(q);
			String jsonString = productTypeService.queryCombogrid(model,page,rows);
			ajaxJson(jsonString);
		} catch (Exception e) {
			logger.error("分页查询商品类型(用于combogrid)失败", e);
		}
	}
}
