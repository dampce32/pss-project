package org.linys.action;

import java.net.URLDecoder;

import org.apache.log4j.Logger;
import org.linys.util.FileUtil;
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
	private static final Logger logger = Logger.getLogger(CommonAction.class);
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
	 * 
	 * @Description: 下载模板文件
	 * @Create: 2013-2-16 下午3:32:46
	 * @author lys
	 * @update logs
	 */
	public void downloadTemplate() {
		String rootPath = getTemplatePath();
		try {
			String fileName =URLDecoder.decode(getParameter("fileName"), "utf-8"); 
			FileUtil.downloadFile(getResponse(), rootPath, fileName);
		} catch (Exception e) {
			logger.error("下载文件失败", e);
			e.printStackTrace();
		}
	}
}
