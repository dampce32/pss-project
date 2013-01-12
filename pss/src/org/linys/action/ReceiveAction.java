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
			String kind = getParameter("kind");
			result = receiveService.query(kind,model,page,rows);
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
			String kind = getParameter("kind");
			result = receiveService.getTotalCount(kind,model);
		} catch (Exception e) {
			result.setMessage("统计收货失败");
			result.setIsSuccess(false);
			e.printStackTrace();
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description:保存收货单
	 * @Create: 2013-1-3 上午10:22:16
	 * @author lys
	 * @update logs
	 */
	public void save(){
		ServiceResult result = new ServiceResult(false);
		try {
			String receiveDetailIds = getParameter("receiveDetailIds");
			String delReceiveDetailIds = getParameter("delReceiveDetailIds");
			String productIds = getParameter("productIds");
			String colorIds = getParameter("colorIds");
			String qtys = getParameter("qtys");
			String prices = getParameter("prices");
			String note1s = getParameter("note1s");
			String note2s = getParameter("note2s");
			String note3s = getParameter("note3s");
			String kind = getParameter("kind");
			
			result = receiveService.save(kind,model,receiveDetailIds,delReceiveDetailIds,
					productIds,colorIds,qtys,prices,note1s,note2s,note3s);
		} catch (Exception e) {
			result.setMessage("保存收货单失败");
			result.setIsSuccess(false);
			e.printStackTrace();
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description: 打开初始化
	 * @Create: 2013-1-8 下午10:28:15
	 * @author lys
	 * @update logs
	 */
	public void init(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = receiveService.init(model.getReceiveId());
		} catch (Exception e) {
			result.setMessage("打开初始化失败");
			result.setIsSuccess(false);
			e.printStackTrace();
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description: 批量删除
	 * @Create: 2013-1-8 下午10:28:15
	 * @author lys
	 * @update logs
	 */
	public void mulDel(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = receiveService.mulDel(ids);
		} catch (Exception e) {
			result.setMessage("批量删除");
			result.setIsSuccess(false);
			e.printStackTrace();
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description: 批量修改审核状态
	 * @Create: 2013-1-9 下午11:50:34
	 * @author lys
	 * @update logs
	 */
	public void mulUpdateShzt(){
		ServiceResult result = new ServiceResult(false);
		try {
			String kind  = getParameter("kind");
			result = receiveService.mulUpdateShzt(kind,ids,model);
		} catch (Exception e) {
			result.setMessage("批量修改审核状态失败");
			result.setIsSuccess(false);
			e.printStackTrace();
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	
}
