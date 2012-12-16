package org.linys.action;

import java.util.List;

import javax.annotation.Resource;

import org.linys.model.Right;
import org.linys.service.RightService;
import org.linys.util.TreeUtil;
import org.linys.vo.ServiceResult;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
/**
 * @Description: 系统权限Action
 * @Copyright: 福州骏华信息有限公司 (c)2012
 * @Created Date : 2012-10-14
 * @author lys
 * @vesion 1.0
 */
@Controller("rightAction")
@Scope("prototype")
public class RightAction extends BaseAction implements ModelDriven<Right> {

	private static final long serialVersionUID = 1L;
	private Right model = new Right();

	@Resource
	private RightService rightService;

	public Right getModel() {
		return model;
	}
	/**
	 * @Description: 选择权限的跟节点
	 * @Create: 2012-10-14 下午10:24:49
	 * @author lys
	 * @update logs
	 * @throws Exception
	 */
	public void selectRoot() {
		try {
			String jsonString = rightService.selectRoot();
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
		List<Right> children=rightService.selectTreeNode(model);
		String jsonString = TreeUtil.toJSONRightList(children);
		ajaxJson(jsonString);
	}
	/**
	 * @Description: 添加权限
	 * @Create: 2012-10-26 下午10:48:00
	 * @author lys
	 * @update logs
	 * @throws Exception
	 */
	public void add(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = rightService.add(model);
		} catch (Exception e) {
			result.setMessage("添加权限失败");
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
			String jsonArray = rightService.query(page, rows, model);
			ajaxJson(jsonArray);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	/**
	 * @Description: 更新权限
	 * @Create: 2012-10-27 上午11:22:47
	 * @author lys
	 * @update logs
	 * @throws Exception
	 */
	public void update() {
		ServiceResult result = new ServiceResult(false);	
		try {
			result = rightService.update(model);
		} catch (Exception e) {
			result.setMessage("修改系统权限出错失败");
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
			result = rightService.mulDelete(ids);
		} catch (Throwable e) {
			e.printStackTrace();
			result.setMessage("批量删除失败");
		}
		ajaxJson(result.toJSON());
	}
}
