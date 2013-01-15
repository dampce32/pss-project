package org.linys.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.linys.dao.UserDAO;
import org.linys.model.Right;
import org.linys.model.User;
import org.linys.service.UserService;
import org.linys.util.JSONUtil;
import org.linys.util.MD5Util;
import org.linys.util.TreeUtil;
import org.linys.vo.ServiceResult;
import org.linys.vo.TreeNode;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends BaseServiceImpl<User,String> implements UserService{
	
	@Resource
	private UserDAO userDAO;
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.UserServ#login(java.lang.String, java.lang.String)
	 */
	public User login(String userCode, String userPwd) {
		String[] propertyNames = {"userCode","userPwd"};
		Object[] values = {userCode,userPwd};
		return userDAO.load(propertyNames, values);
	}
	/*
	 * 	(non-Javadoc)   
	 * @see org.linys.service.UserService#add(org.linys.model.User)
	 */
	public ServiceResult add(User model) {
		ServiceResult result = new ServiceResult(false);
		if(model==null||StringUtils.isEmpty(model.getUserName())){
			result.setMessage("请填写用户名称");
			return result;
		}
		if(StringUtils.isEmpty(model.getUserCode())){
			result.setMessage("请填写登录名称");
			return result;
		}
		User oldModel = userDAO.load("userCode", model.getUserCode());
		if(oldModel!=null){
			result.setMessage("该登录名称已存在，请换个登录名称");
			return result;
		}
		String userPwd = MD5Util.getMD5(model.getUserCode());
		model.setUserPwd(userPwd);
		userDAO.save(model);
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.UserService#query(int, int, org.linys.model.User)
	 */
	public String query(int page, int rows, User model) {
		List<User> list = userDAO.query(page,rows,model);
		Long total = userDAO.count(model);
		List<String> propertyList = new ArrayList<String>();
		propertyList.add("userId");
		propertyList.add("userName");
		propertyList.add("userCode");
		String ajaxString = JSONUtil.toJson(list, propertyList, total);
		return ajaxString;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.UserService#update(org.linys.model.User)
	 */
	public ServiceResult update(User model) {
		ServiceResult result = new ServiceResult(false);
		if(model==null||StringUtils.isEmpty(model.getUserName())){
			result.setMessage("请填写用户名称");
			return result;
		}
		if(StringUtils.isEmpty(model.getUserCode())){
			result.setMessage("请填写登录名称");
			return result;
		}
		User oldModel = userDAO.load("userCode", model.getUserCode());
		if(oldModel!=null&&!oldModel.getUserId().equals(model.getUserId())){
			result.setMessage("该登录名称已存在，请换个登录名称");
			return result;
		}
		String userPwd = MD5Util.getMD5(model.getUserCode());
		model.setUserPwd(userPwd);
		if(oldModel!=null&&oldModel.getUserId().equals(model.getUserId())){
			oldModel.setUserCode(model.getUserCode());
			oldModel.setUserPwd(model.getUserPwd());
			oldModel.setUserName(model.getUserName());
			userDAO.update(oldModel);
		}else{
			userDAO.update(model);
		}
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.UserService#delete(org.linys.model.User)
	 */
	public ServiceResult delete(User model) {
		ServiceResult result = new ServiceResult(false);
		if(model==null||StringUtils.isEmpty(model.getUserId())){
			result.setMessage("请选择要删除的用户");
			return result;
		}
		User user = userDAO.load(model.getUserId());
		userDAO.delete(user);
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.UserService#queryRootRight(org.linys.model.User)
	 */
	public String queryRootRight(User model) {
		String result = "[]";
		if(model!=null&&StringUtils.isNotEmpty(model.getUserId())){
			List<Map<String,Object>> rootList = userDAO.getRooRight(model.getUserId());
			List<Right> rootRightList = toRightList(rootList);
			List<TreeNode> rootTreeNodeList = TreeUtil.toTreeNodeList(rootRightList);
			if(rootRightList!=null){
				for (int i =0;i<rootRightList.size();i++) {
					Right rootRight = rootRightList.get(i);
					if(!rootRight.getIsLeaf()){
						List<Map<String,Object>> children = userDAO.getChildrenRight(model.getUserId(),rootRight.getRightId());
						List<Right> childrenRightList = toRightList(children);
						List<TreeNode> childrenTreeNodeList = TreeUtil.toTreeNodeList(childrenRightList);
						rootTreeNodeList.get(i).setChildren(childrenTreeNodeList);
					}
				}
			}
			result = TreeUtil.toJSON(rootTreeNodeList);
		}
		
		return result;
	}
	/**
	 * @Description:  将Map装化成Right
	 * @Create: 2012-10-29 上午12:10:38
	 * @author lys
	 * @update logs
	 * @param treeNodeMap
	 * @return
	 * @throws Exception
	 */
	private Right toRight(Map<String,Object> treeNodeMap){
		if(treeNodeMap==null){
			return null;
		}
		Right right = new Right();
		right.setRightId(treeNodeMap.get("rightId").toString());
		right.setRightName(treeNodeMap.get("rightName").toString());
		if(treeNodeMap.get("rightUrl")!=null&&StringUtils.isNotEmpty(treeNodeMap.get("rightUrl").toString())){
			right.setRightUrl(treeNodeMap.get("rightUrl").toString());
		}
		String isLeaf = treeNodeMap.get("isLeaf").toString();
		if("1".equals(isLeaf)){
			right.setIsLeaf(new Boolean(true));
		}else{
			right.setIsLeaf(new Boolean(false));
		}
		
		String state = treeNodeMap.get("state").toString();
		if("1".equals(state)){
			right.setState(new Boolean(true));
		}else{
			right.setState(new Boolean(false));
		}
		return right;
	}
	/**
	 * @Description: 将ListMap<String,Object>转化成List<Right>
	 * @Create: 2012-10-29 上午12:20:39
	 * @author lys
	 * @update logs
	 * @param list
	 * @return
	 * @throws Exception
	 */
	private List<Right> toRightList(List<Map<String,Object>> list){
		if(list==null){
			return null;
		}
		List<Right> rightList = new ArrayList<Right>();
		for (Map<String, Object> map : list) {
			Right right = toRight(map);
			if(right!=null){
				rightList.add(right);
			}
		}
		return rightList;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.UserService#queryChildrenRight(org.linys.model.User, java.lang.String)
	 */
	public String queryChildrenRight(User model, String rightId) {
		String result = "[]";
		if(model!=null&&StringUtils.isNotEmpty(model.getUserId())){
			List<Map<String,Object>> children = userDAO.getChildrenRight(model.getUserId(),rightId);
			List<Right> childrenRightList = toRightList(children);
			result = TreeUtil.toJSONRightList(childrenRightList);
		}
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.UserService#getRootRightMain(java.lang.String)
	 */
	public String getRootRightMain(String userId) {
		String result = "[]";
		List<Map<String,Object>> rootList = userDAO.getRooRight(userId); 
		if(rootList!=null&&rootList.size()!=0){
			String rightId = rootList.get(0).get("rightId").toString();
			List<Map<String,Object>> children = userDAO.getChildrenRight(userId,rightId);
			List<Right> childrenRightList = toRightList(children);
			result = TreeUtil.toJSONRightList(childrenRightList);
		}
		return result;
	}
	
}
