package org.linys.action;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.linys.model.Sale;
import org.linys.service.SaleService;
import org.linys.vo.ServiceResult;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

@Controller
@Scope("prototype")
public class SaleAction extends BaseAction implements ModelDriven<Sale> {
	
	private static final long serialVersionUID = -4507417276685138616L;
	
	private static Logger logger = Logger.getLogger(SaleAction.class);
	
	@Resource
	private SaleService saleService;
	
	private Sale sale = new Sale();
	
	private Date beginDate;
	private Date endDate;
	
	public Sale getModel() {
		return sale;
	}

	/**
	 * 
	 * @Description: 新增
	 * @Create: 2013-1-22 下午11:17:34
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
			String discounts = getParameter("discounts");
			String note1s = getParameter("note1s");
			String note2s = getParameter("note2s");
			String note3s = getParameter("note3s");
			result = saleService.addSale(sale, productIds, colorIds, qtys, prices, discounts, note1s, note2s, note3s);
		} catch (Exception e) {
			logger.error("新增订单出错", e);
			result.setMessage("后台出错");
		}
		ajaxJson(result.toJSON());
	}
	
	/**
	 * 
	 * @Description: 修改
	 * @Create: 2013-1-22 下午11:17:47
	 * @author longweier
	 * @update logs
	 * @throws Exception
	 */
	public void update(){
		ServiceResult result = new ServiceResult(false);
		try {
			String saleDetailIds = getParameter("saleDetailIds");
			String delSaleDetailIds = getParameter("delSaleDetailIds");
			String productIds = getParameter("productIds");
			String colorIds = getParameter("colorIds");
			String qtys = getParameter("qtys");
			String prices = getParameter("prices");
			String discounts = getParameter("discounts");
			String note1s = getParameter("note1s");
			String note2s = getParameter("note2s");
			String note3s = getParameter("note3s");
			result = saleService.updateSale(sale, saleDetailIds, delSaleDetailIds, productIds, colorIds, qtys, prices, discounts, note1s, note2s, note3s);
		} catch (Exception e) {
			logger.error("修改订单出错", e);
			result.setMessage("后台出错");
		}
		ajaxJson(result.toJSON());
	}
	
	/**
	 * 
	 * @Description: 单条删除
	 * @Create: 2013-1-22 下午11:18:20
	 * @author longweier
	 * @update logs
	 * @throws Exception
	 */
	public void delete(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = saleService.deleteSale(sale.getSaleId());
		} catch (Exception e) {
			logger.error("删除单条订单出错", e);
			result.setMessage("后台出错");
		}
		ajaxJson(result.toJSON());
	}
	
	/**
	 * 
	 * @Description: 批量删除
	 * @Create: 2013-1-22 下午11:19:19
	 * @author longweier
	 * @update logs
	 * @throws Exception
	 */
	public void mulDelete(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = saleService.mulDeleteSale(ids);
		} catch (Exception e) {
			logger.error("批量删除订单出错", e);
			result.setMessage("后台出错");
		}
		ajaxJson(result.toJSON());
	}
	
	/**
	 * 
	 * @Description: 单条修改状态
	 * @Create: 2013-1-22 下午11:19:56
	 * @author longweier
	 * @update logs
	 * @throws Exception
	 */
	public void updateStatus(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = saleService.updateStatus(sale);
		} catch (Exception e) {
			logger.error("修改单条订单状态出错", e);
			result.setMessage("后台出错");
		}
		ajaxJson(result.toJSON());
	}
	
	/**
	 * 
	 * @Description: 批量修改状态
	 * @Create: 2013-1-22 下午11:21:08
	 * @author longweier
	 * @update logs
	 * @throws Exception
	 */
	public void mulUpdateStatus(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = saleService.mulUpdateStatus(ids, sale.getStatus());
		} catch (Exception e) {
			logger.error("批量修改订单状态出错", e);
			result.setMessage("后台出错");
		}
		ajaxJson(result.toJSON());
	}
	
	/**
	 * 
	 * @Description: 初始化界面
	 * @Create: 2013-1-22 下午11:41:57
	 * @author longweier
	 * @update logs
	 * @throws Exception
	 */
	public void init(){
		String jsonString = saleService.initSale(sale.getSaleId());
		ajaxJson(jsonString);
	}
	
	/**
	 * 
	 * @Description: 查询主页面
	 * @Create: 2013-1-22 下午11:42:18
	 * @author longweier
	 * @update logs
	 * @throws Exception
	 */
	public void query(){
		String jsonString = saleService.querySale(page, rows, sale, beginDate, endDate);
		ajaxJson(jsonString);
	}
	
	/**
	 * 
	 * @Description: 查询出库订单
	 * @Create: 2013-1-25 上午10:21:35
	 * @author longweier
	 * @update logs
	 * @throws Exception
	 */
	public void queryDeliver(){
		String jsonString = saleService.queryDeliver(page, rows, sale, ids, beginDate, endDate);
		ajaxJson(jsonString);
	}
	
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
