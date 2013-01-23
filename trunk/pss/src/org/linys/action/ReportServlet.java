package org.linys.action;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.linys.model.ReportConfig;
import org.linys.service.ReportConfigService;
import org.linys.util.GenXmlData;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
/**
 * @Description:报表生成Servlet
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-1-23
 * @author lys
 * @vesion 1.0
 */
public class ReportServlet extends HttpServlet {
	private static final long serialVersionUID = 5483286765910301562L;

	public ReportServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String reportCode = request.getParameter("reportCode");
		
		ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		ReportConfigService reportConfigService = (ReportConfigService) ac.getBean("reportConfigServiceImpl");
		ReportConfig reportConfig = reportConfigService.getByReportCode(reportCode);
		
		String parameterData = GenXmlData.GenReportParameterData(createSql(request,reportConfig.getReportParamsSql()));
		
		response.setCharacterEncoding("utf-8");
		GenXmlData.GenFullReportData(response, createSql(request,reportConfig.getReportDetailSql()), parameterData,false);
	}

	public void init() throws ServletException {
		
	}
	/**
	 * @Description: 生成Sql
	 * @Create: 2013-1-23 下午3:10:39
	 * @author lys
	 * @update logs
	 * @param request
	 * @param sql
	 * @return
	 */
	private String createSql(HttpServletRequest request,String sql){
		if(StringUtils.isEmpty(sql)){
			return null;
		}
		Pattern p = Pattern.compile("@(\\w+)[,,)]", Pattern.CASE_INSENSITIVE);// 正则表达式，后面的参数指定忽略大小写
		Matcher m = p.matcher(sql);// 匹配的字符串
		while (m.find())//
		{
			String param = m.group(1);
			sql = sql.replace("@"+param, "'%s'");
			String value =  request.getParameter(param);
			if(value==null){
				value="";
			}
			sql = String.format(sql, value);
		}
		return sql;
	}

}
