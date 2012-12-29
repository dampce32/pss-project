package org.linys.action;

import javax.annotation.Resource;

import org.linys.model.Receive;
import org.linys.service.ReceiveService;
import org.linys.vo.ServiceResult;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
/**
 * @Description:采购收货Action
 * @Copyright: 福州骏华信息有限公司 (c)2012
 * @Created Date : 2012-12-29
 * @author lys
 * @vesion 1.0
 */
@Controller
@Scope("prototype")
public class ReceiveAction extends BaseAction implements ModelDriven<Receive> {

	private static final long serialVersionUID = 142847601850026947L;
	@Resource
	private ReceiveService receiveService;
	Receive model = new Receive();
	@Override
	public Receive getModel() {
		return model;
	}
	
	
	/**
	 * @Description:分页查询收货 
	 * @Create: 2012-12-18 下午10:58:02
	 * @author lys
	 * @update logs
	 */
	public void query(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = receiveService.query(model,page,rows);
		} catch (Exception e) {
			result.setMessage("查询收货失败");
			result.setIsSuccess(false);
			e.printStackTrace();
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description: 统计收货
	 * @Create: 2012-12-18 下午10:59:26
	 * @author lys
	 * @update logs
	 */
	public void getTotalCount(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = receiveService.getTotalCount(model);
		} catch (Exception e) {
			result.setMessage("统计收货失败");
			result.setIsSuccess(false);
			e.printStackTrace();
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}

}
