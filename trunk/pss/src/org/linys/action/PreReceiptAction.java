package org.linys.action;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.linys.model.PreReceipt;
import org.linys.service.PreReceiptService;
import org.linys.vo.ServiceResult;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * 
 * @Description: 预收action
 * @Copyright: 福州骏华科技信息有限公司 (c)2013
 * @Created Date : 2013-1-26
 * @author longweier
 * @vesion 1.0
 */
@Controller
@Scope("prototype")
public class PreReceiptAction extends BaseAction implements ModelDriven<PreReceipt> {

	private static final long serialVersionUID = -8092955484264718028L;

	private static Logger logger = Logger.getLogger(PreReceiptAction.class);
	
	private PreReceipt preReceipt = new PreReceipt();
	
	private Date beginDate;
	
	private Date endDate;
	
	@Resource
	private PreReceiptService preReceiptService;
	
	public PreReceipt getModel() {
		return preReceipt;
	}
	
	/**
	 * 
	 * @Description: 保存预收
	 * @Create: 2013-1-26 下午10:13:51
	 * @author longweier
	 * @update logs
	 * @throws Exception
	 */
	public void save(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = preReceiptService.savePreReceipt(preReceipt);
		} catch (Exception e) {
			result.setMessage("后台出错");
			logger.error("保存预收出错", e);
		}
		ajaxJson(result.toJSON());
	}
	
	/**
	 * 
	 * @Description: 删除
	 * @Create: 2013-1-26 下午10:14:45
	 * @author longweier
	 * @update logs
	 * @throws Exception
	 */
	public void delete(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = preReceiptService.deletePreReceipt(preReceipt.getPreReceiptId());
		} catch (Exception e) {
			result.setMessage("后台出错");
			logger.error("单条删除预收出错", e);
		}
		ajaxJson(result.toJSON());
	}
	
	/**
	 * 
	 * @Description: 批量删除
	 * @Create: 2013-1-26 下午10:15:33
	 * @author longweier
	 * @update logs
	 * @throws Exception
	 */
	public void mulDelete(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = preReceiptService.mulDeletePreReceipt(ids);
		} catch (Exception e) {
			result.setMessage("后台出错");
			logger.error("批量删除预收出错", e);
		}
		ajaxJson(result.toJSON());
	}

	/**
	 * 
	 * @Description: 单条修改状态
	 * @Create: 2013-1-26 下午10:16:16
	 * @author longweier
	 * @update logs
	 * @throws Exception
	 */
	public void updateStatus(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = preReceiptService.updateStatusPreReceipt(preReceipt);
		} catch (Exception e) {
			result.setMessage("后台出错");
			logger.error("单条修改预收状态出错", e);
		}
		ajaxJson(result.toJSON());
	}
	
	/**
	 * 
	 * @Description: 批量修改状态
	 * @Create: 2013-1-26 下午10:17:35
	 * @author longweier
	 * @update logs
	 * @throws Exception
	 */
	public void mulUpdateStatus(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = preReceiptService.mulUpdateStatusPreReceipt(ids, preReceipt.getStatus());
		} catch (Exception e) {
			result.setMessage("后台出错");
			logger.error("批量修改预收状态出错", e);
		}
		ajaxJson(result.toJSON());
	}
	
	
	/**
	 * 
	 * @Description: 初始化
	 * @Create: 2013-1-26 下午10:18:29
	 * @author longweier
	 * @update logs
	 * @throws Exception
	 */
	public void init(){
		String jsonString = preReceiptService.initPreReceipt(preReceipt.getPreReceiptId());
		ajaxJson(jsonString);
	}
	
	/**
	 * 
	 * @Description: 查询
	 * @Create: 2013-1-26 下午10:19:07
	 * @author longweier
	 * @update logs
	 * @throws Exception
	 */
	public void query(){
		String jsonString = preReceiptService.queryPreReceipt(page, rows, preReceipt, beginDate, endDate);
		ajaxJson(jsonString);
	}
	
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
