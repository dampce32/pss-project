package org.linys.action;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.linys.model.Split;
import org.linys.service.SplitService;
import org.linys.vo.ServiceResult;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * 
 * @Description: 拆分action
 * @Copyright: 福州骏华科技信息有限公司 (c)2013
 * @Created Date : 2013-2-4
 * @author longweier
 * @vesion 1.0
 */
@Controller
@Scope("prototype")
public class SplitAction extends BaseAction implements ModelDriven<Split> {

	private static final long serialVersionUID = -72259956478346596L;

	private static Logger logger = Logger.getLogger(SplitAction.class);
	
	@Resource
	private SplitService splitService;
	
	private Split split = new Split();
	
	private Date beginDate;
	
	private Date endDate;
	
	public Split getModel() {
		return split;
	}

	/**
	 * 
	 * @Description: 新增
	 * @Create: 2013-2-4 上午09:58:14
	 * @author longweier
	 * @update logs
	 * @throws Exception
	 */
	public void add(){
		ServiceResult result = new ServiceResult(false);
		try {
			String productIds = getParameter("productIds");
			String qtys = getParameter("qtys");
			String prices = getParameter("prices");
			String warehouseIds = getParameter("warehouseIds");
			String note1s = getParameter("note1s");
			String note2s = getParameter("note2s");
			String note3s = getParameter("note3s");
			result = splitService.addSplit(split, productIds, qtys, prices, warehouseIds, note1s, note2s, note3s);
		} catch (Exception e) {
			result.setMessage("后台出错");
			logger.error("添加拆分单出错", e);
		}
		ajaxJson(result.toJSON());
	}

	/**
	 * 
	 * @Description: 修改
	 * @Create: 2013-2-4 上午09:58:09
	 * @author longweier
	 * @update logs
	 * @throws Exception
	 */
	public void update(){
		ServiceResult result = new ServiceResult(false);
		try {
			String splitDetailIds = getParameter("splitDetailIds");
			String delSplitDetailIds = getParameter("delSplitDetailIds");
			String productIds = getParameter("productIds");
			String qtys = getParameter("qtys");
			String prices = getParameter("prices");
			String warehouseIds = getParameter("warehouseIds");
			String note1s = getParameter("note1s");
			String note2s = getParameter("note2s");
			String note3s = getParameter("note3s");
			result = splitService.updateSplit(split, splitDetailIds, delSplitDetailIds, productIds, 
												      qtys, prices, warehouseIds, note1s, note2s, note3s);
		} catch (Exception e) {
			result.setMessage("后台出错");
			logger.error("修改拆分单出错", e);
		}
		ajaxJson(result.toJSON());
	}
	
	/**
	 * 
	 * @Description: 单独删除
	 * @Create: 2013-2-4 上午09:58:02
	 * @author longweier
	 * @update logs
	 * @throws Exception
	 */
	public void delete(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = splitService.delete(split.getSplitId());
		} catch (Exception e) {
			result.setMessage("后台出错");
			logger.error("删除拆分单出错", e);
		}
		ajaxJson(result.toJSON());
	}
	
	/**
	 * 
	 * @Description: 批量删除
	 * @Create: 2013-2-4 上午09:57:55
	 * @author longweier
	 * @update logs
	 * @throws Exception
	 */
	public void mulDelete(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = splitService.mulDelete(ids);
		} catch (Exception e) {
			result.setMessage("后台出错");
			logger.error("批量删除拆分单出错", e);
		}
		ajaxJson(result.toJSON());
	}
	
	/**
	 * 
	 * @Description: 单独修改状态
	 * @Create: 2013-2-4 上午09:57:47
	 * @author longweier
	 * @update logs
	 * @throws Exception
	 */
	public void updateStatus(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = splitService.updateStatus(split);
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			logger.error("修改拆分单状态出错", e);
		}
		ajaxJson(result.toJSON());
	}
	
	/**
	 * 
	 * @Description: 批量修改状态
	 * @Create: 2013-2-4 上午09:57:18
	 * @author longweier
	 * @update logs
	 * @throws Exception
	 */
	public void mulUpdateStatus(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = splitService.mulUpdateStatus(ids, split.getStatus());
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			logger.error("添加拆分单出错", e);
		}
		ajaxJson(result.toJSON());
	}
	
	/**
	 * 
	 * @Description: 初始化
	 * @Create: 2013-2-4 上午09:57:10
	 * @author longweier
	 * @update logs
	 * @throws Exception
	 */
	public void init(){
		String jsonString = splitService.init(split.getSplitId());
		ajaxJson(jsonString);
	}
	
	/**
	 * 
	 * @Description: 主界面查询
	 * @Create: 2013-2-4 上午09:57:03
	 * @author longweier
	 * @update logs
	 * @throws Exception
	 */
	public void query(){
		String jsonString = splitService.query(page, rows, split, beginDate, endDate);
		ajaxJson(jsonString);
	}
	
	
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}
