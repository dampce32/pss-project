package org.linys.action;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.linys.model.DeliverReject;
import org.linys.service.DeliverRejectService;
import org.linys.vo.ServiceResult;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * 
 * @Description: 销售退货action
 * @Copyright: 福州骏华科技信息有限公司 (c)2013
 * @Created Date : 2013-1-26
 * @author longweier
 * @vesion 1.0
 */
@Controller
@Scope("prototype")
public class DeliverRejectAction extends BaseAction implements ModelDriven<DeliverReject> {

	private static final long serialVersionUID = -1312986354298169501L;

	private static Logger logger = Logger.getLogger(DeliverRejectAction.class);
	
	@Resource
	private DeliverRejectService deliverRejectService;
	
	private DeliverReject deliverReject = new DeliverReject();
	
	private Date beginDate;
	
	private Date endDate;
	
	public DeliverReject getModel() {
		return deliverReject;
	}

	/**
	 * 
	 * @Description: 新增
	 * @Create: 2013-1-26 上午01:20:20
	 * @author longweier
	 * @update logs
	 * @throws Exception
	 */
	public void add(){
		ServiceResult result = new ServiceResult(false);
		try {
			String productIds = getParameter("productIds");
			String colorIds = getParameter("colorIds");
			String qtys = getParameter("qtys");
			String prices = getParameter("prices");
			String note1s = getParameter("note1s");
			String note2s = getParameter("note2s");
			String note3s = getParameter("note3s");
			result = deliverRejectService.addDeliverReject(deliverReject, productIds, colorIds, qtys, prices, note1s, note2s, note3s);
		} catch (Exception e) {
			logger.error("添加销售退货出错", e);
			result.setMessage("后台出错");
		}
		ajaxJson(result.toJSON());
	}
	
	/**
	 * 
	 * @Description: 修改
	 * @Create: 2013-1-26 上午01:23:15
	 * @author longweier
	 * @update logs
	 * @throws Exception
	 */
	public void update(){
		ServiceResult result = new ServiceResult(false);
		try {
			String deliverRejectDetailIds = getParameter("deliverRejectDetailIds");
			String delDeliverRejectDetailIds = getParameter("delDeliverRejectDetailIds");
			String productIds = getParameter("productIds");
			String colorIds = getParameter("colorIds");
			String qtys = getParameter("qtys");
			String prices = getParameter("prices");
			String note1s = getParameter("note1s");
			String note2s = getParameter("note2s");
			String note3s = getParameter("note3s");
			result = deliverRejectService.updateDeliverReject(deliverReject, deliverRejectDetailIds, delDeliverRejectDetailIds, productIds,
								colorIds, qtys, prices, note1s, note2s, note3s);
		} catch (Exception e) {
			logger.error("修改销售退货出错", e);
			result.setMessage("后台出错");
		}
		ajaxJson(result.toJSON());
	}
	
	/**
	 * 
	 * 
	 * @Description: 单条数据删除
	 * @Create: 2013-1-26 上午01:23:28
	 * @author longweier
	 * @update logs
	 * @throws Exception
	 */
	public void delete(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = deliverRejectService.deleteDeliverReject(deliverReject.getDeliverRejectId());
		} catch (Exception e) {
			logger.error("单独删除销售退货出错", e);
			result.setMessage("后台出错");
		}
		ajaxJson(result.toJSON());
	}
	
	/**
	 * 
	 * 
	 * @Description: 批量删除
	 * @Create: 2013-1-26 上午01:24:11
	 * @author longweier
	 * @update logs
	 * @throws Exception
	 */
	public void mulDelete(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = deliverRejectService.mulDeleteDeliverReject(ids);
		} catch (Exception e) {
			logger.error("批量删除销售退货出错", e);
			result.setMessage("后台出错");
		}
		ajaxJson(result.toJSON());
	}
	
	/**
	 * 
	 * @Description: 单条数据状态
	 * @Create: 2013-1-26 上午01:24:34
	 * @author longweier
	 * @update logs
	 * @throws Exception
	 */
	public void updateStatus(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = deliverRejectService.updateStatusDeliverReject(deliverReject);
		} catch (Exception e) {
			logger.error("单独修改销售退货状态出错", e);
			result.setMessage("后台出错");
		}
		ajaxJson(result.toJSON());
	}
	
	/**
	 * 
	 * 
	 * @Description: 批量修改状态
	 * @Create: 2013-1-26 上午01:25:14
	 * @author longweier
	 * @update logs
	 * @throws Exception
	 */
	public void mulUpdateStatus(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = deliverRejectService.mulUpdateStatusDeliverReject(ids, deliverReject.getStatus());
		} catch (Exception e) {
			logger.error("批量修改销售退货状态出错", e);
			result.setMessage("后台出错");
		}
		ajaxJson(result.toJSON());
	}
	
	/**
	 * 
	 * @Description: 初始化
	 * @Create: 2013-1-26 上午01:26:24
	 * @author longweier
	 * @update logs
	 * @throws Exception
	 */
	public void init(){
		String jsonString = deliverRejectService.initDeliverReject(deliverReject.getDeliverRejectId());
		ajaxJson(jsonString);
	}
	
	/**
	 * 
	 * @Description: 查询
	 * @Create: 2013-1-26 上午01:26:31
	 * @author longweier
	 * @update logs
	 * @throws Exception
	 */
	public void query(){
		String jsonString = deliverRejectService.queryDeliverReject(page, rows, deliverReject, beginDate, endDate);
		ajaxJson(jsonString);
	}
	
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
