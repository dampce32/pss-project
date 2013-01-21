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
			result = payService.query(model,page,rows);
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
			result = payService.getTotalCount(model);
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
			String delPayDetailIds = getParameter("delPayDetailIds");
			String receiveIds = getParameter("receiveIds");
			String payKinds = getParameter("payKinds");
			String amounts = getParameter("amounts");
			String payedAmounts = getParameter("payedAmounts");
			String discountedAmounts = getParameter("discountedAmounts");
			String discountAmounts = getParameter("discountAmounts");
			String payAmounts = getParameter("payAmounts");
			
			result = payService.save(model,payDetailIds,delPayDetailIds,
					receiveIds,payKinds,amounts,payedAmounts,discountedAmounts,discountAmounts,payAmounts);
			
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
	

}