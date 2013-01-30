package org.linys.action;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.linys.model.Receipt;
import org.linys.service.ReceiptService;
import org.linys.vo.ServiceResult;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * 
 * @Description: 收款action
 * @Copyright: 福州骏华科技信息有限公司 (c)2013
 * @Created Date : 2013-1-29
 * @author longweier
 * @vesion 1.0
 */
@Controller
@Scope("prototype")
public class ReceiptAction extends BaseAction implements ModelDriven<Receipt> {
	
	private static final long serialVersionUID = -6140160534212718719L;

	private static Logger logger = Logger.getLogger(ReceiptAction.class);
	
	@Resource
	private ReceiptService receiptService;
	
	private Receipt receipt = new Receipt();
	
	private Date beginDate;
	
	private Date endDate;
	
	public Receipt getModel() {
		return receipt;
	}

	/**
	 * 
	 * @Description: 新增收款单
	 * @Create: 2013-1-29 下午02:14:21
	 * @author longweier
	 * @update logs
	 * @throws Exception
	 */
	public void add(){
		ServiceResult result = new ServiceResult(false);
		try {
			String sourceIds = getParameter("sourceIds");
			String sourceCodes = getParameter("sourceCodes");
			String sourceDates = getParameter("sourceDates");
			String receiptKinds = getParameter("receiptKinds");
			String amounts = getParameter("amounts");
			String receiptedAmounts = getParameter("receiptedAmounts");
			String discountAmounts = getParameter("discountAmounts");
			String receiptAmounts = getParameter("receiptAmounts");
			result = receiptService.addReceipt(receipt, sourceIds, sourceCodes, sourceDates, receiptKinds, 
					amounts, receiptedAmounts, discountAmounts, receiptAmounts);
		} catch (Exception e) {
			result.setMessage("后台出错");
			logger.error("添加收款出错", e);
		}
		ajaxJson(result.toJSON());
	}
	
	/**
	 * 
	 * @Description: 修改收款单
	 * @Create: 2013-1-29 下午02:14:44
	 * @author longweier
	 * @update logs
	 * @throws Exception
	 */
	public void update(){
		ServiceResult result = new ServiceResult(false);
		try {
			String receiptDetailIdIds = getParameter("receiptDetailIdIds");
			String delreceiptDetailIdIds = getParameter("delreceiptDetailIdIds");
			String sourceIds = getParameter("sourceIds");
			String sourceCodes = getParameter("sourceCodes");
			String sourceDates = getParameter("sourceDates");
			String receiptKinds = getParameter("receiptKinds");
			String amounts = getParameter("amounts");
			String receiptedAmounts = getParameter("receiptedAmounts");
			String discountAmounts = getParameter("discountAmounts");
			String receiptAmounts = getParameter("receiptAmounts");
			result = receiptService.updateReceipt(receipt, receiptDetailIdIds, delreceiptDetailIdIds, sourceIds, sourceCodes, sourceDates, receiptKinds, 
					amounts, receiptedAmounts, discountAmounts, receiptAmounts);
		} catch (Exception e) {
			result.setMessage("后台出错");
			logger.error("修改收款出错", e);
		}
		ajaxJson(result.toJSON());
	}
	
	/**
	 * 
	 * @Description: 单独删除收款单
	 * @Create: 2013-1-29 下午02:18:37
	 * @author longweier
	 * @update logs
	 * @throws Exception
	 */
	public void delete(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = receiptService.deleteReceipt(receipt.getReceiptId());
		} catch (Exception e) {
			result.setMessage("后台出错");
			logger.error("删除收款单出错", e);
		}
		ajaxJson(result.toJSON());
	}
	
	/**
	 * 
	 * @Description: 批量删除收款单
	 * @Create: 2013-1-29 下午02:21:35
	 * @author longweier
	 * @update logs
	 * @throws Exception
	 */
	public void mulDelete(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = receiptService.mulDeleteReceipt(ids);
		} catch (Exception e) {
			result.setMessage("后台出错");
			logger.error("批量删除收款单出错", e);
		}
		ajaxJson(result.toJSON());
	}
	
	/**
	 * 
	 * @Description: 更新状态
	 * @Create: 2013-1-29 下午02:22:37
	 * @author longweier
	 * @update logs
	 * @throws Exception
	 */
	public void updateStatus(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = receiptService.updateStatusReceipt(receipt);
		} catch (Exception e) {
			result.setMessage("后台出错");
			logger.error("更新收款单状态出错", e);
		}
		ajaxJson(result.toJSON());
	}
	
	/**
	 * 
	 * @Description: 批量修改收款单状态
	 * @Create: 2013-1-29 下午02:28:52
	 * @author longweier
	 * @update logs
	 * @throws Exception
	 */
	public void mulUpdateStatus(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = receiptService.mulUpdateStatusReceipt(ids, receipt.getStatus());
		} catch (Exception e) {
			result.setMessage("后台出错");
			logger.error("批量修改收款状态出错", e);
		}
		ajaxJson(result.toJSON());
	}
	
	/**
	 * 
	 * @Description: 初始化
	 * @Create: 2013-1-29 下午02:29:52
	 * @author longweier
	 * @update logs
	 * @throws Exception
	 */
	public void init(){
		String jsonString = receiptService.initReceipt(receipt.getReceiptId());
		ajaxJson(jsonString);
	}
	
	/**
	 * 
	 * @Description: 查询
	 * @Create: 2013-1-29 下午02:34:22
	 * @author longweier
	 * @update logs
	 * @throws Exception
	 */
	public void query(){
		String jsonString = receiptService.queryReceipt(page, rows, receipt, beginDate, endDate);
		ajaxJson(jsonString);
	}
	
	/**
	 * 
	 * @Description: 查询需要收款的单据
	 * @Create: 2013-1-29 下午02:35:08
	 * @author longweier
	 * @update logs
	 * @throws Exception
	 */
	public void queryNeedCheck(){
		String jsonString = receiptService.queryNeedReceipt(page, rows, receipt, ids);
		ajaxJson(jsonString);
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}
