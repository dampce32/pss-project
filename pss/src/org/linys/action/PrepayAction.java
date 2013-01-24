package org.linys.action;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.linys.model.Prepay;
import org.linys.service.PrepayService;
import org.linys.vo.ServiceResult;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
/**
 * @Description:预付单Action
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-1-23
 * @author lys
 * @vesion 1.0
 */
@Controller
@Scope("prototype")
public class PrepayAction extends BaseAction implements ModelDriven<Prepay> {
	
	private static final long serialVersionUID = -7973182200199364827L;
	private static final Logger logger = Logger.getLogger(PrepayAction.class);
	@Resource
	private PrepayService prepayService;
	Prepay model = new Prepay();
	@Override
	public Prepay getModel() {
		return model;
	}

	/**
	 * @Description:分页查询预付单 
	 * @Create: 2012-12-18 下午10:58:02
	 * @author lys
	 * @update logs
	 */
	public void query(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = prepayService.query(model,page,rows);
		} catch (Exception e) {
			result.setMessage("查询预付单失败");
			logger.error("查询预付单失败", e);
			result.setIsSuccess(false);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description: 统计预付单
	 * @Create: 2012-12-18 下午10:59:26
	 * @author lys
	 * @update logs
	 */
	public void getTotalCount(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = prepayService.getTotalCount(model);
		} catch (Exception e) {
			result.setMessage("统计预付单失败");
			logger.error("统计预付单失败", e);
			result.setIsSuccess(false);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description:保存预付单
	 * @Create: 2013-1-3 上午10:22:16
	 * @author lys
	 * @update logs
	 */
	public void save(){
		ServiceResult result = new ServiceResult(false);
		try {
			
			result = prepayService.save(model);
		} catch (Exception e) {
			result.setMessage("保存预付单失败");
			logger.error("保存预付单失败", e);
			result.setIsSuccess(false);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description: 打开初始化预付单
	 * @Create: 2013-1-8 下午10:28:15
	 * @author lys
	 * @update logs
	 */
	public void init(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = prepayService.init(model.getPrepayId());
		} catch (Exception e) {
			result.setMessage("打开初始化失败");
			result.setIsSuccess(false);
			logger.error("打开初始化失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description: 删除预付单
	 * @Create: 2013-1-14 下午9:24:13
	 * @author lys
	 * @update logs
	 */
	public void delete(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = prepayService.delete(model);
		} catch (Exception e) {
			result.setMessage("删除预付单失败");
			logger.error("删除预付单失败", e);
			result.setIsSuccess(false);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description: 批量删除预付单
	 * @Create: 2013-1-8 下午10:28:15
	 * @author lys
	 * @update logs
	 */
	public void mulDelele(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = prepayService.mulDelele(ids);
		} catch (Exception e) {
			result.setMessage("批量预付单删除失败");
			logger.error("批量预付单删除失败", e);
			result.setIsSuccess(false);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description: 更新预付单状态
	 * @Create: 2013-1-14 下午9:24:13
	 * @author lys
	 * @update logs
	 */
	public void updateStatus(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = prepayService.updateStatus(model);
		} catch (Exception e) {
			result.setMessage("更新预付单状态失败");
			logger.error("更新预付单状态失败", e);
			result.setIsSuccess(false);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description: 批量更新预付单状态
	 * @Create: 2013-1-9 下午11:50:34
	 * @author lys
	 * @update logs
	 */
	public void mulUpdateStatus(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = prepayService.mulUpdateStatus(ids,model);
		} catch (Exception e) {
			result.setMessage("批量更新预付单状态失败");
			logger.error("批量更新预付单状态失败", e);
			result.setIsSuccess(false);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
}
