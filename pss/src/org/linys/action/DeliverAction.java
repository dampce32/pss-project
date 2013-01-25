package org.linys.action;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.linys.model.Deliver;
import org.linys.service.DeliverService;
import org.linys.vo.ServiceResult;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * 
 * @Description: 出库action
 * @Copyright: 福州骏华科技信息有限公司 (c)2013 
 * @Created Date : 2013-1-24
 * @author longweier
 * @vesion 1.0
 */
@Controller
@Scope("prototype")
public class DeliverAction extends BaseAction implements ModelDriven<Deliver> {
	
	private static final long serialVersionUID = -5777332406424255744L;

	private static Logger logger = Logger.getLogger(DeliverAction.class);
	
	private Deliver deliver = new Deliver();
	
	private Date beginDate;
	
	private Date endDate;
	
	private String type;
	
	@Resource
	private DeliverService deliverService;
	
	public Deliver getModel() {
		return deliver;
	}
	
	/**
	 * 
	 * 
	 * @Description: 添加出库单
	 * @Create: 2013-1-25 上午12:04:15
	 * @author longweier
	 * @update logs
	 * @throws Exception
	 */
	public void add(){
		ServiceResult result = new ServiceResult(false);
		try {
			String saleDetailIds = getParameter("saleDetailIds");
			String productIds = getParameter("productIds");
			String colorIds = getParameter("colorIds");
			String qtys = getParameter("qtys");
			String prices = getParameter("prices");
			String discounts = getParameter("discounts");
			String note1s = getParameter("note1s");
			String note2s = getParameter("note2s");
			String note3s = getParameter("note3s");
			result = deliverService.addDeliver(type, deliver, saleDetailIds, productIds, colorIds, qtys, prices, discounts, note1s, note2s, note3s);
		} catch (RuntimeException e) {
			logger.error("添加出库单出错", e);
			result.setMessage("后台出错");
		}
		ajaxJson(result.toJSON());
	}
	
	/**
	 * 
	 * @Description: 修改出库单
	 * @Create: 2013-1-25 上午12:07:29
	 * @author longweier
	 * @update logs
	 * @throws Exception
	 */
	public void update(){
		ServiceResult result = new ServiceResult(false);
		try {
			String deliverDetailIds = getParameter("deliverDetailIds");
			String delDeliverDetailIds = getParameter("delDeliverDetailIds");
			String saleDetailIds = getParameter("saleDetailIds");
			String productIds = getParameter("productIds");
			String colorIds = getParameter("colorIds");
			String qtys = getParameter("qtys");
			String prices = getParameter("prices");
			String discounts = getParameter("discounts");
			String note1s = getParameter("note1s");
			String note2s = getParameter("note2s");
			String note3s = getParameter("note3s");
			result = deliverService.updateDeliver(type, deliver, deliverDetailIds, delDeliverDetailIds, saleDetailIds,
												  productIds, colorIds, qtys, prices, discounts, note1s, note2s, note3s);
		} catch (RuntimeException e) {
			logger.error("修改出库单出错", e);
			result.setMessage("后台出错");
		}
		ajaxJson(result.toJSON());
	}
	
	/**
	 * 
	 * @Description: 单条删除出库单
	 * @Create: 2013-1-25 上午12:10:27
	 * @author longweier
	 * @update logs
	 * @throws Exception
	 */
	public void delete(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = deliverService.deleteDeliver(deliver.getDeliverId());
		}catch (RuntimeException e) {
			logger.error("删除单条出库单出错", e);
			result.setMessage("后台出错");
		}
		ajaxJson(result.toJSON());
	}
	
	/**
	 * 
	 * @Description: 批量删除出库单
	 * @Create: 2013-1-25 上午12:11:44
	 * @author longweier
	 * @update logs
	 * @throws Exception
	 */
	public void mulDelete(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = deliverService.mulDeleteDeliver(ids);
		}catch (RuntimeException e) {
			logger.error("批量删除出库单出错", e);
			result.setMessage("后台出错");
		}
		ajaxJson(result.toJSON());
	}
	
	/**
	 * 
	 * @Description: 单条修改出库单状态
	 * @Create: 2013-1-25 上午12:12:22
	 * @author longweier
	 * @update logs
	 * @throws Exception
	 */
	public void updateStatus(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = deliverService.updateStatusDeliver(type, deliver);
		}catch (RuntimeException e) {
			logger.error("单条修改出库单状态出错", e);
			result.setMessage(e.getMessage());
		}
		ajaxJson(result.toJSON());
	}
	
	/**
	 * 
	 * @Description: 批量修改出库单状态
	 * @Create: 2013-1-25 上午12:12:32
	 * @author longweier
	 * @update logs
	 * @throws Exception
	 */
	public void mulUpdateStatus(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = deliverService.mulUpdateStatusDeliver(type, ids, deliver.getStatus());
		}catch (RuntimeException e) {
			logger.error("批量修改出库单状态出错", e);
			result.setMessage(e.getMessage());
		}
		ajaxJson(result.toJSON());
	}
	
	/**
	 * 
	 * 
	 * @Description: 初始化界面
	 * @Create: 2013-1-25 上午12:12:52
	 * @author longweier
	 * @update logs
	 * @throws Exception
	 */
	public void init(){
		String jsonString = deliverService.initDeliver(deliver.getDeliverId());
		ajaxJson(jsonString);
	}
	
	/**
	 * 
	 * @Description: 查询出库单
	 * @Create: 2013-1-25 上午12:13:01
	 * @author longweier
	 * @update logs
	 * @throws Exception
	 */
	public void query(){
		String jsonString = deliverService.queryDeliver(page, rows, deliver, beginDate, endDate, type);
		ajaxJson(jsonString);
	}

	/**
	 * 
	 * @Description: 将选择的订单中的商品明细，添加到出库单明细中
	 * @Create: 2013-1-25 下午03:17:21
	 * @author longweier
	 * @update logs
	 * @throws Exception
	 */
	public void querySelectSaleDetail(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = deliverService.querySelectSaleDetail(ids,ids2);
		} catch (Exception e) {
			result.setMessage("将选择的订单中的订单明细，添加到出库单明细中失败");
			result.setIsSuccess(false);
			logger.error("将选择的订单中的订单明细，添加到出库单明细中失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setType(String type) {
		this.type = type;
	}
}
