package org.linys.action;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @Description:Action基类
 * @Copyright: 福州骏华信息有限公司 (c)2012
 * @Created Date : 2012-10-28
 * @author lys
 * @vesion 1.0
 */
public class BaseAction extends ActionSupport {

	private static final long serialVersionUID = -3993971048496950513L;
	/**
	 * response
	 */
	protected HttpServletResponse response = ServletActionContext.getResponse();
	/**
	 * request
	 */
	protected HttpServletRequest request = ServletActionContext.getRequest();
	/**
	 * 接受从界面传递的ids
	 */
	protected String ids;
	/**
	 * 接受从界面传递的oldIds
	 */
	protected String oldIds;
	/**
	 * 接受jquery easyui datagrid中的page(第几页）
	 */
	protected Integer page;
	/**
	 * 接受jquery easyui datagrid中的rows(每页显示几行）
	 */
	protected Integer rows;
	/**
	 * combogrid查询时，发出的参数
	 */
	protected String q;
	
	/**
	 * 
	 * @Description: 根据字符串输出JSON，返回null
	 * @Created: 2012-9-19 上午9:41:55
	 * @Author lys
	 * @Update logs
	 * @param jsonString
	 * @return
	 * @Throws Exception
	 */
	public String ajaxJson(String jsonString) {
		return ajax(jsonString, "text/plain");
	}

	/**
	 * 
	 * @Description: 往前台返回某种类型的字符串结果
	 * @Created: 2012-9-19 上午9:42:15
	 * @Author lys
	 * @Update logs
	 * @param content
	 * @param type
	 * @return
	 * @Throws Exception
	 */
	public String ajax(String content, String type) {
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType(type + ";charset=UTF-8");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(content);
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @Description: 往Session里面设置key-value
	 * @Created: 2012-9-19 上午9:52:36
	 * @Author lys
	 * @Update logs
	 * @param name
	 * @param value
	 * @Throws Exception
	 */
	public void setSession(String name, Object value) {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		session.put(name, value);
	}

	/**
	 * @Description: 取得Session中的某个属性
	 * @Create: 2012-10-26 下午11:18:47
	 * @author lys
	 * @update logs
	 * @return
	 * @throws Exception
	 */
	public Object getSession(String name) {
		Map<String, Object> session = getSession();
		return session.get(name);
	}

	/**
	 * @Description: 取得Session
	 * @Create: 2012-10-26 下午11:18:47
	 * @author lys
	 * @update logs
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getSession() {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		return session;
	}

	/**
	 * @Description: 取得request
	 * @Create: 2012-10-26 下午11:19:48
	 * @author lys
	 * @update logs
	 * @return
	 * @throws Exception
	 */
	public HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	/**
	 * @Description: 取得response
	 * @Create: 2012-10-26 下午11:20:03
	 * @author lys
	 * @update logs
	 * @return
	 * @throws Exception
	 */
	public HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	/**
	 * @Description: 取得Parameter
	 * @Create: 2012-10-26 下午11:21:01
	 * @author lys
	 * @update logs
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public String getParameter(String name) {
		return getRequest().getParameter(name);
	}
	
	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getOldIds() {
		return oldIds;
	}

	public void setOldIds(String oldIds) {
		this.oldIds = oldIds;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = q;
	}
}
