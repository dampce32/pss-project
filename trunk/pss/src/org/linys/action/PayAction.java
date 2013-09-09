package org.linys.action;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.linys.model.Pay;
import org.linys.service.PayService;
import org.linys.vo.ServiceResult;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
/**
 * @Description:付款单Action
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-1-20
 * @author lys
 */
@Controller
@Scope("prototype")
public class PayAction extends BaseAction implements ModelDriven<Pay> {

	private static final long serialVersionUID = -6041760192728068810L;
	private static final Logger logger = Logger.getLogger(PayAction.class);
	@Resource
	private PayService payService;
	Pay model = new Pay();
	@Override
	public Pay getModel() {
		return model;
	}
	
	/**
	 * @Description:分页查询付款单 
	 * @Create: 2012-12-18 下午10:58:02
	 * @author lys
	 * @update logs
	 */
	public void query(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = payService.query(model,page,rows,beginDate,endDate);
		} catch (Exception e) {
			result.setMessage("分页查询付款单失败");
			logger.error("分页查询付款单失败", e);
			result.setIsSuccess(false);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description: 统计付款单
	 * @Create: 2012-12-18 下午10:59:26
	 * @author lys
	 * @update logs
	 */
	public void getTotalCount(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = payService.getTotalCount(model,beginDate,endDate);
		} catch (Exception e) {
			result.setMessage("统计付款单失败");
			result.setIsSuccess(false);
			logger.error("统计付款单失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	
	/**
	 * @Description:保存付款单
	 * @Create: 2013-1-3 上午10:22:16
	 * @author lys
	 * @update logs
	 */
	public void save(){
		ServiceResult result = new ServiceResult(false);
		try {
			String payDetailIds = getParameter("payDetailIds");
			String sourceIds = getParameter("sourceIds");
			String sourceCodes = getParameter("sourceCodes");
			String sourceDates = getParameter("sourceDates");
			String delPayDetailIds = getParameter("delPayDetailIds");
			String payKinds = getParameter("payKinds");
			String amounts = getParameter("amounts");
			String payedAmounts = getParameter("payedAmounts");
			String discountAmounts = getParameter("discountAmounts");
			String payAmounts = getParameter("payAmounts");
			
			result = payService.save(model,payDetailIds,delPayDetailIds,
					sourceIds,sourceCodes,sourceDates,payKinds,amounts,payedAmounts,discountAmounts,payAmounts);
			
		} catch (Exception e) {
			result.setMessage("保存付款单失败");
			result.setIsSuccess(false);
			logger.error("保存付款单失败", e);
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
			result = payService.init(model.getPayId());
		} catch (Exception e) {
			result.setMessage("打开初始化失败");
			result.setIsSuccess(false);
			logger.error("打开初始化失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	
	/**
	 * @Description: 删除付款单
	 * @Create: 2013-1-8 下午10:28:15
	 * @author lys
	 * @update logs
	 */
	public void delete(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = payService.delete(model);
		} catch (Exception e) {
			result.setMessage("删除付款单失败");
			logger.error("删除付款单失败", e);
			result.setIsSuccess(false);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	
	/**
	 * @Description: 批量删除付款单
	 * @Create: 2013-1-8 下午10:28:15
	 * @author lys
	 * @update logs
	 */
	public void mulDelete(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = payService.mulDelete(ids);
		} catch (Exception e) {
			result.setMessage("批量删除付款单失败");
			logger.error("批量删除付款单失败", e);
			result.setIsSuccess(false);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	
	/**
	 * @Description: 修改付款单状态
	 * @Create: 2013-1-16 下午9:10:54
	 * @author lys
	 * @update logs
	 */
	public void updateStatus(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = payService.updateStatus(model);
		} catch (Exception e) {
			result.setMessage("修改付款单状态失败");
			logger.error("修改付款单状态失败", e);
			result.setIsSuccess(false);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description: 批量修改付款单状态
	 * @Create: 2013-1-9 下午11:50:34
	 * @author lys
	 * @update logs
	 */
	public void mulUpdateStatus(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = payService.mulUpdateStatus(ids,model);
		} catch (Exception e) {
			result.setMessage("批量修改付款单状态失败");
			logger.error("批量修改付款单状态失败", e);
			result.setIsSuccess(false);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description: 分页查询需要对账的单子（采购单、入库单、退货单、预付单）
	 * @Create: 2013-1-24 下午2:27:31
	 * @author lys
	 * @update logs
	 */
	public void queryNeedCheck(){
		ServiceResult result = new ServiceResult(false);
		try {
			String supplierId = getParameter("supplierId");
			result = payService.queryNeedCheck(supplierId,ids,page,rows);
		} catch (Exception e) {
			result.setMessage("查询需要对账的单子（采购单、入库单、退货单、预付单）失败");
			logger.error("查询需要对账的单子（采购单、入库单、退货单、预付单）失败", e);
			result.setIsSuccess(false);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
}
