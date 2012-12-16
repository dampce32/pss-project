package org.linys.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.linys.dao.RoleDAO;
import org.linys.dao.RoleRightDAO;
import org.linys.dao.UserDAO;
import org.linys.model.Role;
import org.linys.model.RoleRight;
import org.linys.model.RoleRightId;
import org.linys.service.RoleService;
import org.linys.util.JSONUtil;
import org.linys.vo.ServiceResult;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends BaseServiceImpl<Role, String> implements
		RoleService {
	@Resource
	private RoleDAO roleDAO;
	@Resource
	private UserDAO userDAO;
	@Resource
	private RoleRightDAO roleRightDAO;
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.RoleServ#add(org.linys.model.Role)
	 */
	public ServiceResult add(Role model, String userId) {
		/*
		 *新增角色，
		 *将当前用户的权限，赋予新增的角色 
		 */
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(userId)){
			result.setMessage("对不起请先登录系统");
			return result;
		}
		if(StringUtils.isEmpty(model.getRoleName())){
			result.setMessage("请填写角色名");
			return result;
		}
		roleDAO.save(model);
		/*
		 * 新增角色，并将当前教师的权限赋值给新增的角色
		 */
		List<Map<String,Object>> list = userDAO.queryUserRight(userId);
		//取出当前教师整合后的角色权限
		for (Map<String, Object> map : list) {
			RoleRight roleRight = new RoleRight();
			
			RoleRightId roleRightId = new RoleRightId();
			roleRightId.setRoleId(model.getRoleId());
			
			String rightId = map.get("RightID").toString();
			roleRightId.setRightId(rightId);
			
			String state = map.get("State").toString();
			if("1".equals(state)){
				roleRight.setState(new Boolean(true));
			}else{
				roleRight.setState(new Boolean(false));
			}
			
			roleRight.setId(roleRightId);
			roleRightDAO.save(roleRight);
		}
		
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.RoleServ#update(org.linys.model.Role)
	 */
	public ServiceResult update(Role model) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(model.getRoleName())){
			result.setMessage("请填写角色名");
			return result;
		}
		roleDAO.update(model);
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.RoleServ#delete(org.linys.model.Role)
	 */
	public ServiceResult delete(Role model) {
		ServiceResult result = new ServiceResult(false);
		if(model==null||StringUtils.isEmpty(model.getRoleId())){
			result.setMessage("请选择要删除的角色");
			return result;
		}
		Role role  = roleDAO.load(model.getRoleId());
		roleDAO.delete(role);
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.RoleServ#query(org.linys.model.Role)
	 */
	public String query(Role model) {
		List<Role> list = roleDAO.query(model);
		List<String> propertyList = new ArrayList<String>();
		propertyList.add("roleId");
		propertyList.add("roleName");
		return JSONUtil.toJson(list,propertyList);
	}
}
