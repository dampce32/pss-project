package org.linys.action;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.linys.model.Store;
import org.linys.service.StoreService;
import org.linys.vo.ServiceResult;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
/**
 * @Description:库存Action
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-1-11
 * @author lys
 * @vesion 1.0
 */
@Controller
@Scope("prototype")
public class StoreAction extends BaseAction implements ModelDriven<Store> {
	
	private static final long serialVersionUID = 2214715720182823741L;
	private static final Logger logger = Logger.getLogger(StoreAction.class);
	@Resource
	private StoreService storeService;
	Store model = new Store();
	@Override
	public Store getModel() {
		return model;
	}
	/**
	 * @Description: 分页查询当前库存
	 * @Create: 2013-1-11 下午11:00:19
	 * @author lys
	 * @update logs
	 */
	public void query(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = storeService.query(model,page,rows);
		} catch (Exception e) {
			result.setMessage("分页查询当前库存失败");
			result.setIsSuccess(false);
			logger.error("分页查询当前库存失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description: 统计当前库存
	 * @Create: 2012-12-18 下午10:59:26
	 * @author lys
	 * @update logs
	 */
	public void getTotalCount(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = storeService.getTotalCount(model);
		} catch (Exception e) {
			result.setMessage("统计当前库存失败");
			result.setIsSuccess(false);
			logger.error("统计当前库存失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description: 采购退货选择商品
	 * @Create: 2013-1-12 上午9:45:12
	 * @author lys
	 * @update logs
	 */
	public void selectReject(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = storeService.selectReject(model,page,rows);
		} catch (Exception e) {
			result.setMessage("采购退货选择商品失败");
			result.setIsSuccess(false);
			logger.error("采购退货选择商品失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description: 查询商品的在各个仓库的当前库存情况
	 * @Create: 2013-1-12 下午1:47:49
	 * @author lys
	 * @update logs
	 */
	public void queryByProduct(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = storeService.queryByProduct(model,page,rows);
		} catch (Exception e) {
			result.setMessage("查询商品的在各个仓库的当前库存情况失败");
			result.setIsSuccess(false);
			logger.error("查询商品的在各个仓库的当前库存情况失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
}
