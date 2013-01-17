package org.linys.action;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
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
	private static final Logger logger = Logger.getLogger(ReceiveAction.class);
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
			logger.error("查询收货失败", e);
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
			logger.error("统计收货失败", e);
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
			String buyDetailIds = getParameter("buyDetailIds");
			String delReceiveDetailIds = getParameter("delReceiveDetailIds");
			String productIds = getParameter("productIds");
			String colorIds = getParameter("colorIds");
			String qtys = getParameter("qtys");
			String prices = getParameter("prices");
			String note1s = getParameter("note1s");
			String note2s = getParameter("note2s");
			String note3s = getParameter("note3s");
			String kind = getParameter("kind");
			
			result = receiveService.save(kind,model,receiveDetailIds,buyDetailIds,delReceiveDetailIds,
					productIds,colorIds,qtys,prices,note1s,note2s,note3s);
		} catch (Exception e) {
			result.setMessage("保存收货单失败");
			result.setIsSuccess(false);
			logger.error("保存收货单失败", e);
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
			logger.error("打开初始化失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	
	/**
	 * @Description: 删除
	 * @Create: 2013-1-8 下午10:28:15
	 * @author lys
	 * @update logs
	 */
	public void delete(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = receiveService.delete(model);
		} catch (Exception e) {
			result.setMessage("删除");
			result.setIsSuccess(false);
			logger.error("删除失败", e);
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
			result = receiveService.mulDelete(ids);
		} catch (Exception e) {
			result.setMessage("批量删除");
			result.setIsSuccess(false);
			logger.error("批量删除失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	
	/**
	 * @Description: 修改审核状态
	 * @Create: 2013-1-16 下午9:10:54
	 * @author lys
	 * @update logs
	 */
	public void updateStatus(){
		ServiceResult result = new ServiceResult(false);
		try {
			String kind  = getParameter("kind");
			result = receiveService.updateStatus(kind,model);
		} catch (Exception e) {
			result.setMessage("修改审核状态失败");
			result.setIsSuccess(false);
			logger.error("修改审核状态失败", e);
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
			String kind  = getParameter("kind");
			result = receiveService.mulUpdateStatus(kind,ids,model);
		} catch (Exception e) {
			result.setMessage("批量修改审核状态失败");
			result.setIsSuccess(false);
			logger.error("批量修改审核状态失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description: 清款
	 * @Create: 2013-1-16 下午9:49:39
	 * @author lys
	 * @update logs
	 */
	public void updateIsPay(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = receiveService.updateIsPay(model);
		} catch (Exception e) {
			result.setMessage("清款失败");
			logger.error("清款失败", e);
			result.setIsSuccess(false);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description: 批量清款
	 * @Create: 2013-1-12 下午3:33:29
	 * @author lys
	 * @update logs
	 */
	public void mulUpdateIsPay(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = receiveService.mulUpdateIsPay(ids,model);
		} catch (Exception e) {
			result.setMessage("批量清款失败");
			result.setIsSuccess(false);
			logger.error("批量清款失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	
	
	/**
	 * @Description: 将选择的采购单中的采购明细，添加到收货单明细中
	 * @Create: 2013-1-14 下午11:42:39
	 * @author lys
	 * @update logs
	 */
	public void querySelectBuyDetail(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = receiveService.querySelectBuyDetail(ids,ids2);
		} catch (Exception e) {
			result.setMessage("将选择的采购单中的采购明细，添加到收货单明细中失败");
			result.setIsSuccess(false);
			logger.error("将选择的采购单中的采购明细，添加到收货单明细中失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	} 
	
	
	
	
	
}
