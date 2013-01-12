package org.linys.action;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.linys.model.Buy;
import org.linys.service.BuyService;
import org.linys.vo.ServiceResult;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
/**
 * @Description:采购单Action
 * @Copyright: 福州骏华信息有限公司 (c)2012
 * @Created Date : 2012-12-29
 * @author lys
 * @vesion 1.0
 */
@Controller
@Scope("prototype")
public class BuyAction extends BaseAction implements ModelDriven<Buy> {

	private static final long serialVersionUID = 142847601850026947L;
	private static final Logger logger = Logger.getLogger(BuyAction.class);
	@Resource
	private BuyService buyService;
	Buy model = new Buy();
	@Override
	public Buy getModel() {
		return model;
	}
	/**
	 * @Description:分页查询采购单 
	 * @Create: 2012-12-18 下午10:58:02
	 * @author lys
	 * @update logs
	 */
	public void query(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = buyService.query(model,page,rows);
		} catch (Exception e) {
			result.setMessage("查询采购单失败");
			result.setIsSuccess(false);
			logger.error("查询采购单失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description: 统计采购单
	 * @Create: 2012-12-18 下午10:59:26
	 * @author lys
	 * @update logs
	 */
	public void getTotalCount(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = buyService.getTotalCount(model);
		} catch (Exception e) {
			result.setMessage("统计采购单失败");
			result.setIsSuccess(false);
			logger.error("统计采购单失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description:保存采购单单
	 * @Create: 2013-1-3 上午10:22:16
	 * @author lys
	 * @update logs
	 */
	public void save(){
		ServiceResult result = new ServiceResult(false);
		try {
			String buyDetailIds = getParameter("buyDetailIds");
			String delBuyDetailIds = getParameter("delBuyDetailIds");
			String productIds = getParameter("productIds");
			String colorIds = getParameter("colorIds");
			String qtys = getParameter("qtys");
			String prices = getParameter("prices");
			String note1s = getParameter("note1s");
			String note2s = getParameter("note2s");
			String note3s = getParameter("note3s");
			
			result = buyService.save(model,buyDetailIds,delBuyDetailIds,
					productIds,colorIds,qtys,prices,note1s,note2s,note3s);
		} catch (Exception e) {
			result.setMessage("保存采购单失败");
			result.setIsSuccess(false);
			logger.error("保存采购单失败", e);
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
			result = buyService.init(model.getBuyId());
		} catch (Exception e) {
			result.setMessage("打开初始化失败");
			result.setIsSuccess(false);
			logger.error("打开初始化失败", e);
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
			result = buyService.mulDel(ids);
		} catch (Exception e) {
			result.setMessage("批量删除");
			result.setIsSuccess(false);
			logger.error("批量删除失败", e);
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
			result = buyService.mulUpdateShzt(ids,model);
		} catch (Exception e) {
			result.setMessage("批量修改审核状态失败");
			result.setIsSuccess(false);
			logger.error("批量修改审核状态失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	
}
