package org.linys.action;

import javax.annotation.Resource;

import org.linys.model.UserRole;
import org.linys.service.UserRoleService;
import org.linys.vo.ServiceResult;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * @Description:用户角色Action
 * @Copyright: 福州骏华信息有限公司 (c)2012
 * @Created Date : 2012-10-28
 * @author lys
 * @vesion 1.0
 */
@Controller
@Scope("prototype")
public class UserRoleAction extends BaseAction implements ModelDriven<UserRole>{
	
	private static final long serialVersionUID = 1L;
	private UserRole model = new UserRole();
	@Resource
	private UserRoleService userRoleService;
	public UserRole getModel() {
		return model;
	}
	
	/**
	 * @Description: 查询用户的角色
	 * @Create: 2012-10-28 下午9:50:51
	 * @author lys
	 * @update logs
	 * @throws Exception
	 */
	public void queryRole(){
		String jsonArray = userRoleService.queryRole(model);
		ajaxJson(jsonArray);
	}
	/**
	 * @Description: 更新用户角色
	 * @Create: 2012-10-28 下午11:09:42
	 * @author lys
	 * @update logs
	 * @throws Exception
	 */
	public void updateRole(){
		ServiceResult result = new ServiceResult(false);
		try {
			result =userRoleService.updateRole(model,ids,oldIds);
		} catch (RuntimeException e) {
			result.setMessage("更新用户角色失败");
		}
		ajaxJson(result.toJSON());
	}
	
}
