package org.linys.action;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.linys.model.Reject;
import org.linys.service.RejectService;
import org.linys.vo.ServiceResult;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
/**
 * @Description:退货Action
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-1-11
 * @author lys
 * @vesion 1.0
 */
@Controller
@Scope("prototype")
public class RejectAction extends BaseAction implements ModelDriven<Reject> {

	private static final long serialVersionUID = -3291603231648422921L;
	private static final Logger logger = Logger.getLogger(RejectAction.class);
	@Resource
	private RejectService rejectService;
	Reject model = new Reject();
	@Override
	public Reject getModel() {
		return model;
	}
	
	/**
	 * @Description:分页查询退货 
	 * @Create: 2012-12-18 下午10:58:02
	 * @author lys
	 * @update logs
	 */
	public void query(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = rejectService.query(model,page,rows);
		} catch (Exception e) {
			result.setMessage("查询退货失败");
			result.setIsSuccess(false);
			logger.error("查询退货失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description: 统计退货
	 * @Create: 2012-12-18 下午10:59:26
	 * @author lys
	 * @update logs
	 */
	public void getTotalCount(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = rejectService.getTotalCount(model);
		} catch (Exception e) {
			result.setMessage("统计退货失败");
			result.setIsSuccess(false);
			logger.error("统计退货失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description:保存退货单
	 * @Create: 2013-1-3 上午10:22:16
	 * @author lys
	 * @update logs
	 */
	public void save(){
		ServiceResult result = new ServiceResult(false);
		try {
			String rejectDetailIds = getParameter("rejectDetailIds");
			String delRejectDetailIds = getParameter("delRejectDetailIds");
			String productIds = getParameter("productIds");
			String colorIds = getParameter("colorIds");
			String qtys = getParameter("qtys");
			String prices = getParameter("prices");
			String note1s = getParameter("note1s");
			String note2s = getParameter("note2s");
			String note3s = getParameter("note3s");
			
			result = rejectService.save(model,rejectDetailIds,delRejectDetailIds,
					productIds,colorIds,qtys,prices,note1s,note2s,note3s);
		} catch (Exception e) {
			result.setMessage("保存退货单失败");
			result.setIsSuccess(false);
			logger.error("保存退货单失败", e);
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
			result = rejectService.init(model.getRejectId());
		} catch (Exception e) {
			result.setMessage("打开初始化失败");
			result.setIsSuccess(false);
			logger.error("打开初始化失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description: 删除退货单失败
	 * @Create: 2013-1-8 下午10:28:15
	 * @author lys
	 * @update logs
	 */
	public void delete(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = rejectService.delete(model);
		} catch (Exception e) {
			result.setMessage("删除退货单失败");
			logger.error("删除退货单失败", e);
			result.setIsSuccess(false);
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
	public void mulDelete(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = rejectService.mulDelete(ids);
		} catch (Exception e) {
			result.setMessage("批量删除");
			result.setIsSuccess(false);
			logger.error("批量删除失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description: 修改退货单状态
	 * @Create: 2013-1-16 下午9:10:54
	 * @author lys
	 * @update logs
	 */
	public void updateStatus(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = rejectService.updateStatus(model);
		} catch (Exception e) {
			result.setMessage("修改退货单状态失败");
			logger.error("修改退货单状态失败", e);
			result.setIsSuccess(false);
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
	public void mulUpdateStatus(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = rejectService.mulUpdateStatus(ids,model);
		} catch (Exception e) {
			result.setMessage("批量修改审核状态失败");
			result.setIsSuccess(false);
			logger.error("批量修改审核状态失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}

}
