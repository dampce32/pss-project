package org.linys.action;

import java.io.File;
import java.io.IOException;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.linys.model.User;
import org.linys.service.UserService;
import org.linys.util.JCaptchaEngine;
import org.linys.util.MD5Util;
import org.linys.vo.ServiceResult;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.octo.captcha.service.CaptchaService;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 用户管理
 * 
 * @author LYS
 * 
 */
@Controller("userAction")
// 要与Struts.xml文件中class的属性相同
@Scope("prototype")
// 多例：每个请求一个action
public class UserAction extends BaseAction implements ModelDriven<User> {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(UserAction.class);
	private User model = new User();

	@Resource
	private UserService userService;
	@Resource
	private CaptchaService captchaService;

	public User getModel() {
		return model;
	}
	
	/**
	 * 用户登录验证
	 */
	public void login() {
		ServiceResult result = new ServiceResult(false);
		String captchaID = request.getSession().getId();
		String challengeResponse = StringUtils.upperCase(request.getParameter(JCaptchaEngine.CAPTCHA_INPUT_NAME));
		
		if (StringUtils.isEmpty(challengeResponse) || captchaService.validateResponseForID(captchaID, challengeResponse) == false) {
			result.setMessage("验证码错误");
			ajaxJson(result.toJSON());
			return;
		}
		// 根据用户名和密码判断是否允许登录
		if(model!=null){
			User loginUser = userService.login(model.getUserCode(),MD5Util.getMD5(model.getUserPwd()));
			if (null == loginUser) {
				result.setMessage("用户名或密码错误!!");
			}else{
				setSession(User.LOGIN_USERID,loginUser.getUserId());
				result.setIsSuccess(true);
			}
		}else{
			result.setMessage("请输入用户名和密码");
		}
		String ajaxString = result.toJSON();
		ajaxJson(ajaxString);
	}
	/**
	 * @Description: 
	 * @Create: 2012-10-28 上午8:54:10
	 * @author lys
	 * @update logs
	 * @throws Exception
	 */
	public void add(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = userService.add(model);
		} catch (Throwable e) {
			result.setMessage("添加用户失败");
			logger.error("添加用户失败", e);
		}
		String ajaxString = result.toJSON();
		ajaxJson(ajaxString);
	}
	/**
	 * @Description: 查询用户列表
	 * @Create: 2012-10-28 上午9:14:13
	 * @author lys
	 * @update logs
	 * @throws Exception
	 */
	public void query(){
		String jsonArray = userService.query(page, rows, model);
		ajaxJson(jsonArray);
	}
	/**
	 * @Description: 修改用户
	 * @Create: 2012-10-28 上午9:35:28
	 * @author lys
	 * @update logs
	 * @throws Exception
	 */
	public void update(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = userService.update(model);
		} catch (Throwable e) {
			result.setMessage("修改用户失败");
			logger.error("修改用户失败", e);
		}
		String ajaxString = result.toJSON();
		ajaxJson(ajaxString);
	}
	/**
	 * @Description: 删除用户
	 * @Create: 2012-10-28 上午9:47:57
	 * @author lys
	 * @update logs
	 * @throws Exception
	 */
	public void delete(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = userService.delete(model);
		} catch (Throwable e) {
			result.setMessage("删除用户失败");
			logger.error("删除用户失败", e);
		}
		String ajaxString = result.toJSON();
		ajaxJson(ajaxString);
	}
	/**
	 * @Description: 查询用户的权限
	 * @Create: 2012-10-28 下午11:55:21
	 * @author lys
	 * @update logs
	 * @throws Exception
	 */
	public void queryRootRight(){
		String jsonArray = userService.queryRootRight(model);
		ajaxJson(jsonArray);
	}
	/**
	 * @Description: 
	 * @Create: 2012-10-29 下午9:51:19
	 * @author lys
	 * @update logs
	 * @throws Exception
	 */
	public void queryChildrenRight(){
		if(model==null||StringUtils.isEmpty(model.getUserId())){
			model.setUserId(getSession(User.LOGIN_USERID).toString());
		}
		String rightId = getParameter("rightId");
		String jsonArray = userService.queryChildrenRight(model,rightId);
		ajaxJson(jsonArray);
	}
	/**
	 * @Description: 取得主界面权限树的第一层权限
	 * @Create: 2012-11-15 下午11:33:47
	 * @author lys
	 * @update logs
	 */
	public void getRootRightMain(){
		try {
			/*String path = "tree.json";
			String pathname = getRequest().getSession().getServletContext().getRealPath(path);
			File file = new File(pathname);
			String tree = "";
			try {
				tree = FileUtils.readFileToString(file, "utf-8");
			} catch (IOException e) {
				e.printStackTrace();
			}
			ajaxJson(tree);*/
			String userId = getSession(User.LOGIN_USERID).toString();
			String jsonArray = userService.getRootRightMain(userId);
			ajaxJson(jsonArray);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * @Description: 退出系统
	 * @Create: 2012-11-15 上午11:07:41
	 * @author lys
	 * @update logs
	 */
	public void logout(){
		getSession().clear();
		ServiceResult result = new ServiceResult(true);
		ajaxJson(result.toJSON());
	}
	/**
	 * @Description: 取得用户个人信息
	 * @Create: 2013-1-22 下午1:54:16
	 * @author lys
	 * @update logs
	 */
	public void getSelfInfor(){
		ServiceResult result = new ServiceResult(false);
		try {
			String userId = getSession(User.LOGIN_USERID).toString();
			result = userService.getSelfInfor(userId);
		} catch (Throwable e) {
			result.setMessage("取得用户个人信息失败");
			logger.error("取得用户个人信息失败", e);
		}
		String ajaxString = result.toJSON();
		ajaxJson(ajaxString);
	}
	/**
	 * @Description: 更新用户个人信息
	 * @Create: 2013-1-22 下午2:06:57
	 * @author lys
	 * @update logs
	 */
	public void updateSelfInfo(){
		ServiceResult result = new ServiceResult(false);
		try {
			String userId = getSession(User.LOGIN_USERID).toString();
			result = userService.updateSelfInfo(userId,model);
		} catch (Throwable e) {
			result.setMessage("更新用户个人信息失败");
			logger.error("更新用户个人信息失败", e);
		}
		String ajaxString = result.toJSON();
		ajaxJson(ajaxString);
	}
	/**
	 * @Description: 修改密码
	 * @Created: 2012-10-29 上午9:29:35
	 * @Author lys
	 */
	public void modifyPwd(){
		ServiceResult result = new ServiceResult(false);
		String userId = null;
		if(getSession(User.LOGIN_USERID)!=null){
			userId = getSession(User.LOGIN_USERID).toString();
		}
		model.setUserId(userId);
		String newUserPwd = getParameter("newUserPwd");
		try {
			result = userService.modifyPwd(model,newUserPwd);
		} catch (Exception e) {
			result.setMessage("修改密码失败");
			logger.error("修改密码失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
}
