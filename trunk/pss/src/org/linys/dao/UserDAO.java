package org.linys.dao;

import java.util.List;
import java.util.Map;

import org.linys.model.User;
/**
 * @Description:用户DAO
 * @Copyright: 福州骏华信息有限公司 (c)2012
 * @Created Date : 2012-10-28
 * @author lys
 * @vesion 1.0
 */
public interface UserDAO extends BaseDAO<User,String>{

	User login(User operator);
	/**
	 * @Description: 分页查询用户列表
	 * @Create: 2012-10-28 上午9:16:34
	 * @author lys
	 * @update logs
	 * @param page
	 * @param rows
	 * @param model
	 * @return
	 * @throws Exception
	 */
	List<User> query(int page, int rows, User model);
	/**
	 * @Description: 统计用户数
	 * @Create: 2012-10-28 上午9:21:04
	 * @author lys
	 * @update logs
	 * @param model
	 * @return
	 * @throws Exception
	 */
	Long count(User model);
	/**
	 * @Description: 取得用户userId的跟权限
	 * @Create: 2012-10-29 上午12:00:10
	 * @author lys
	 * @update logs
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> getRooRight(String userId);
	/**
	 * @Description: 取得用户userId下的权限rightId下的孩子权限
	 * @Create: 2012-10-29 上午12:14:30
	 * @author lys
	 * @update logs
	 * @param userId
	 * @param rightId
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> getChildrenRight(String userId, String rightId);
	/**
	 * @Description: 取得用户userId的用户权限
	 * @Create: 2012-10-29 下午10:31:06
	 * @author lys
	 * @update logs
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> queryUserRight(String userId);

}
