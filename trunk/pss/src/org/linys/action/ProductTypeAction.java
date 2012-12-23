package org.linys.action;

import java.util.List;

import javax.annotation.Resource;

import org.linys.model.ProductType;
import org.linys.model.Right;
import org.linys.service.ProductTypeService;
import org.linys.util.TreeUtil;
import org.linys.vo.ServiceResult;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

@Controller
@Scope("prototype")
public class ProductTypeAction extends BaseAction implements ModelDriven<ProductType> {
	
	private static final long serialVersionUID = 7361849093296061199L;
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
			e.printStackTrace();
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
			e.printStackTrace();
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
			e.printStackTrace();
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
			result.setMessage("修改系统商品类别出错失败");
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
			e.printStackTrace();
			result.setMessage("批量删除失败");
		}
		ajaxJson(result.toJSON());
	}
}
