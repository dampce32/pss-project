package org.linys.action;

import javax.annotation.Resource;

import org.linys.util.JSONUtil;
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
	@Resource
	/**
	 * @Description: 清空datagrid
	 * @Create: 2012-12-30 下午5:07:53
	 * @author lys
	 * @update logs
	 */
	public void clearDatagrid(){
		ajaxJson(JSONUtil.EMPTYJSON);
	}
}
