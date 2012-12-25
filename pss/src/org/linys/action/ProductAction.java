package org.linys.action;

import javax.annotation.Resource;

import org.linys.model.Product;
import org.linys.service.ProductService;
import org.linys.vo.ServiceResult;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
/**
 * @Description:商品Action
 * @Copyright: 福州骏华信息有限公司 (c)2012
 * @Created Date : 2012-12-23
 * @author lys
 * @vesion 1.0
 */
@Controller
@Scope("prototype")
public class ProductAction extends BaseAction implements ModelDriven<Product> {
	
	private static final long serialVersionUID = -7463394380104260815L;
	@Resource
	private ProductService productService;
	Product model = new Product();
	public Product getModel() {
		return model;
	}
	/**
	 * @Description: 添加商品
	 * @Create: 2012-12-23 下午5:42:17
	 * @author lys
	 * @update logs
	 */
	public void add(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = productService.add(model);
		} catch (Exception e) {
			result.setMessage("添加商品失败");
			e.printStackTrace();
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	
	/**
	 * @Description: 修改商品
	 * @Create: 2012-12-18 下午10:55:46
	 * @author lys
	 * @update logs
	 */
	public void update(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = productService.update(model);
		} catch (Exception e) {
			result.setMessage("修改商品失败");
			result.setIsSuccess(false);
			e.printStackTrace();
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description: 删除商品
	 * @Create: 2012-12-18 下午10:56:44
	 * @author lys
	 * @update logs
	 */
	public void delete(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = productService.delete(model);
		} catch (Exception e) {
			result.setMessage("删除商品失败");
			result.setIsSuccess(false);
			e.printStackTrace();
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description:分组查询 商品 
	 * @Create: 2012-12-18 下午10:58:02
	 * @author lys
	 * @update logs
	 */
	public void query(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = productService.query(model,page,rows);
		} catch (Exception e) {
			result.setMessage("查询商品失败");
			result.setIsSuccess(false);
			e.printStackTrace();
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description: 统计 商品
	 * @Create: 2012-12-18 下午10:59:26
	 * @author lys
	 * @update logs
	 */
	public void getTotalCount(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = productService.getTotalCount(model);
		} catch (Exception e) {
			result.setMessage("查询商品失败");
			result.setIsSuccess(false);
			e.printStackTrace();
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}

}
