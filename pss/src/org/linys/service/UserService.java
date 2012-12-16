package org.linys.service;

import org.linys.model.User;
import org.linys.vo.ServiceResult;
/**
 * @Description: 用户Service
 * @Copyright: 福州骏华信息有限公司 (c)2012
 * @Created Date : 2012-10-28
 * @author lys
 * @vesion 1.0
 */
public interface UserService extends BaseService<User,String>{

	/**
	 * 
	 * @Description: 用户登录
	 * @Create: 2012-9-17 下午11:09:19
	 * @author lys
	 * @update logs
	 * @param operator
	 * @return
	 * @throws Exception
	 */
	User login(String userCode, String userPwd);
	/**
	 * @Description: 添加用户
	 * @Create: 2012-10-28 上午8:55:29
	 * @author lys
	 * @update logs
	 * @param model
	 * @return
	 * @throws Exception
	 */
	ServiceResult add(User model);
	/**
	 * @Description: 分页查询用户列表
	 * @Create: 2012-10-28 上午9:15:24
	 * @author lys
	 * @update logs
	 * @param page
	 * @param rows
	 * @param model
	 * @return
	 * @throws Exception
	 */
	String query(int page, int rows, User model);
	/**
	 * @Description: 修改用户
	 * @Create: 2012-10-28 上午9:36:06
	 * @author lys
	 * @update logs
	 * @param model
	 * @return
	 * @throws Exception
	 */
	ServiceResult update(User model);
	/**
	 * @Description: 删除用户
	 * @Create: 2012-10-28 上午9:48:22
	 * @author lys
	 * @update logs
	 * @param model
	 * @return
	 * @throws Exception
	 */
	ServiceResult delete(User model);
	/**
	 * @Description: 查询用户的跟权限
	 * @Create: 2012-10-28 下午11:56:20
	 * @author lys
	 * @update logs
	 * @param model
	 * @return
	 * @throws Exception
	 */
	String queryRootRight(User model);
	/**
	 * @Description: 当前用户的权限rightId下的孩子节点
	 * @Create: 2012-10-29 下午9:52:26
	 * @author lys
	 * @update logs
	 * @param model
	 * @param rightId
	 * @return
	 * @throws Exception
	 */
	String queryChildrenRight(User model, String rightId);
	/**
	 * @Description: 取得主界面权限树的第一层权限
	 * @Create: 2012-11-15 下午11:35:52
	 * @author lys
	 * @update logs
	 * @param userId
	 * @return
	 */
	String getRootRightMain(String userId);

}
