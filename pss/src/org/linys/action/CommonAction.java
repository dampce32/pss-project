package org.linys.action;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.linys.service.CommonService;
import org.linys.util.JSONUtil;
import org.linys.vo.ServiceResult;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 * @Description:可全局调用的action
 * @Copyright: 福州骏华信息有限公司 (c)2012
 * @Created Date : 2012-12-30
 * @author lys
 * @vesion 1.0
 */
@Controller
@Scope("prototype")
public class CommonAction extends BaseAction {

	private static final long serialVersionUID = -7485373143593963699L;
	private static final Logger logger = Logger.getLogger(CommonAction.class);
	@Resource
	private CommonService commonService;
	/**
	 * @Description: 清空datagrid
	 * @Create: 2012-12-30 下午5:07:53
	 * @author lys
	 * @update logs
	 */
	public void clearDatagrid(){
		ajaxJson(JSONUtil.EMPTYJSON);
	}
	/**
	 * @Description: 取得编号
	 * @Create: 2013-1-5 下午9:31:11
	 * @author lys
	 * @update logs
	 */
	public void getCode(){
		ServiceResult result = new ServiceResult(false);
		try {
			String type = request.getParameter("type");
			String code = request.getParameter("code");
			result = commonService.getCode(type,code);
		} catch (Throwable e) {
			result.setMessage("取得编号失败");
			logger.error("取得编号失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}

}
