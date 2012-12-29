package org.linys.action;

import javax.annotation.Resource;

import org.linys.model.Supplier;
import org.linys.service.SupplierService;
import org.linys.vo.ServiceResult;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
/**
 * @Description:供应商Action
 * @Copyright: 福州骏华信息有限公司 (c)2012
 * @Created Date : 2012-12-29
 * @author lys
 * @vesion 1.0
 */
@Controller
@Scope("prototype")
public class SupplierAction extends BaseAction implements ModelDriven<Supplier> {
	
	private static final long serialVersionUID = -1441552064249569286L;
	@Resource
	private SupplierService supplierService;
	Supplier model = new Supplier();
	@Override
	public Supplier getModel() {
		return model;
	}
	
	/**
	 * @Description: 保存供应商
	 * @Create: 2012-12-23 下午5:42:17
	 * @author lys
	 * @update logs
	 */
	public void save(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = supplierService.save(model);
		} catch (Exception e) {
			result.setMessage("保存供应商失败");
			e.printStackTrace();
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	
	/**
	 * @Description: 删除供应商
	 * @Create: 2012-12-18 下午10:56:44
	 * @author lys
	 * @update logs
	 */
	public void delete(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = supplierService.delete(model);
		} catch (Exception e) {
			result.setMessage("删除供应商失败");
			result.setIsSuccess(false);
			e.printStackTrace();
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description:分页查询 供应商 
	 * @Create: 2012-12-18 下午10:58:02
	 * @author lys
	 * @update logs
	 */
	public void query(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = supplierService.query(model,page,rows);
		} catch (Exception e) {
			result.setMessage("分页查询供应商失败");
			result.setIsSuccess(false);
			e.printStackTrace();
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description: 统计供应商
	 * @Create: 2012-12-18 下午10:59:26
	 * @author lys
	 * @update logs
	 */
	public void getTotalCount(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = supplierService.getTotalCount(model);
		} catch (Exception e) {
			result.setMessage("统计供应商失败");
			result.setIsSuccess(false);
			e.printStackTrace();
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description: combogrid查询
	 * @Create: 2012-12-29 下午6:17:47
	 * @author lys
	 * @update logs
	 */
	public void queryCombogrid(){
		model.setSupplierName(q);
		String jsonArray = supplierService.queryCombogrid(page, rows,model);
		ajaxJson(jsonArray);
	}

}
