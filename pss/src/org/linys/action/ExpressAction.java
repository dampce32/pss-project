package org.linys.action;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.linys.model.Express;
import org.linys.service.ExpressService;
import org.linys.vo.ServiceResult;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * 
 * 
 * @Description: 快递action
 * @Copyright: 福州骏华科技信息有限公司 (c)2013
 * @Created Date : 2013-1-25
 * @author longweier
 * @vesion 1.0
 */
@Controller
@Scope("prototype")
public class ExpressAction extends BaseAction implements ModelDriven<Express> {
	
	private static final long serialVersionUID = -2753186324982845884L;

	private static Logger logger = Logger.getLogger(ExpressAction.class);
	
	private Express express = new Express();

	@Resource
	private ExpressService expressService;
	
	public Express getModel() {
		return express;
	}

	/**
	 * 
	 * 
	 * @Description: 新增或修改快递
	 * @Create: 2013-1-25 上午01:07:16
	 * @author longweier
	 * @update logs
	 * @throws Exception
	 */
	public void save(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = expressService.saveExpress(express);
		} catch (Exception e) {
			logger.error("添加快递出错",e);
			result.setMessage("后台出错");
		}
		ajaxJson(result.toJSON());
	}
	
	/**
	 * 
	 * @Description: 查询快递集合
	 * @Create: 2013-1-25 上午01:09:14
	 * @author longweier
	 * @update logs
	 * @throws Exception
	 */
	public void query(){
		String jsonString = expressService.queryExpress();
		ajaxJson(jsonString);
	}
	
	/**
	 * 
	 * @Description: 查询快递combobox
	 * @Create: 2013-1-25 上午01:09:00
	 * @author longweier
	 * @update logs
	 * @throws Exception
	 */
	public void queryCombobox(){
		String jsonString = expressService.queryComboboxExpress();
		ajaxJson(jsonString);
	}
}
