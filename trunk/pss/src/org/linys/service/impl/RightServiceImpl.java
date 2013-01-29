package org.linys.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.linys.dao.RightDAO;
import org.linys.dao.RoleDAO;
import org.linys.dao.RoleRightDAO;
import org.linys.model.Right;
import org.linys.model.Role;
import org.linys.model.RoleRight;
import org.linys.model.RoleRightId;
import org.linys.service.RightService;
import org.linys.util.JSONUtil;
import org.linys.util.StringUtil;
import org.linys.util.TreeUtil;
import org.linys.vo.GobelConstants;
import org.linys.vo.ServiceResult;
import org.linys.vo.TreeNode;
import org.springframework.stereotype.Service;

@Service
public class RightServiceImpl extends BaseServiceImpl<Right, String> implements RightService {
	
	@Resource
	private RightDAO rightDAO;
	@Resource
	private RoleDAO roleDAO;
	@Resource
	private RoleRightDAO roleRightDAO;
	
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.RightServ#selectRoot()
	 */
	public String selectRoot() {
		List<Right> rootList = rightDAO.selectRoot();
		List<TreeNode> rootNodeList = TreeUtil.toTreeNodeList(rootList);
		if(rootList!=null){
			for (int i = 0; i < rootList.size(); i++) {
				Right right = rootList.get(i);
				if(!right.getIsLeaf()){
					List<Right> children = rightDAO.getChildren(right);
					List<TreeNode> childrenNodeList = TreeUtil.toTreeNodeList(children);
					rootNodeList.get(i).setChildren(childrenNodeList);
				}
			}
		}
		return TreeUtil.toJSON(rootNodeList);
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.RightServ#add(org.linys.model.Right)
	 */
	public ServiceResult add(Right model) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(model.getRightName())){
			result.setMessage("请填写权限名");
			return result;
		}
		if(StringUtils.isEmpty(model.getParentRight().getRightId())){
			model.setParentRight(null);
		}
		model.setIsLeaf(true);
		
		rightDAO.save(model);
		if(model.getParentRight()!=null){
			rightDAO.updateIsLeaf(model.getParentRight().getRightId(),false);
			
			List<Role> allRole = roleDAO.queryAll();
			for (Role role : allRole) {
				RoleRight roleRight = new RoleRight();
				RoleRightId roleRightId = new RoleRightId();
				roleRightId.setRoleId(role.getRoleId());
				roleRightId.setRightId(model.getRightId());
				roleRight.setId(roleRightId);
				
				roleRight.setRole(role);
				roleRight.setRight(model);
				
				roleRight.setState(true);
				roleRightDAO.save(roleRight);
				//更新权限树
				setParentTrue(roleRight);
			}
		}
		result.getData().put("rightId", model.getRightId());
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.RightServ#query(int, int, org.linys.model.Right)
	 */
	public String query(int page, int rows, Right model) {
		if(StringUtils.isEmpty(model.getRightId())){
			List<Right> rootList = rightDAO.selectRoot();
			model.setRightId(rootList.get(0).getRightId());
		}
		
		List<Right> list = rightDAO.query(page,rows,model);
		Long total=rightDAO.count(model);
		
		List<String> propertyList = new ArrayList<String>();
		propertyList.add("rightId");
		propertyList.add("rightName");
		propertyList.add("rightUrl");
		
		return JSONUtil.toJson(list, propertyList, total);
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.RightServ#update(org.linys.model.Right)
	 */
	public ServiceResult update(Right model) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(model.getRightName())){
			result.setMessage("请填写权限名");
			return result;
		}
		Right oldModel = rightDAO.load(model.getRightId());
		oldModel.setRightName(model.getRightName());
		oldModel.setRightUrl(model.getRightUrl());
		rightDAO.update(oldModel);
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.RightServ#mulDelete(java.lang.String)
	 */
	public ServiceResult mulDelete(String ids) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(ids)){
			result.setMessage("请选择要删除的记录");
			return result;
		}
		String[] idArray = StringUtil.split(ids, GobelConstants.SPLIT_SEPARATOR);
		if(idArray.length==0){
			result.setMessage("请选择要删除的记录");
			return result;
		}
		String parentID = null;
		Right item = rightDAO.load(idArray[0]);
		for (String id : idArray) {
			rightDAO.delete(id);
		}
		if(idArray.length>0){
			if(item!=null&&item.getParentRight()!=null){
				parentID = item.getParentRight().getRightId();
				Long countChildren = rightDAO.countChildren(parentID);
				if(countChildren==0){
					rightDAO.updateIsLeaf(parentID, true);
				}
			}
		}
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.RightServ#selectTreeNode(org.linys.model.Right)
	 */
	public List<Right> selectTreeNode(Right model) {
		return rightDAO.getChildren(model);
	}
	/**
	 * @Description: 从未勾选到勾选情况下更新父节点的状态
	 * @Create: 2012-10-27 下午10:27:01
	 * @author lys
	 * @update logs
	 * @param model
	 * @throws Exception
	 */
	private void setParentTrue(RoleRight model){
		Right parentRight = rightDAO.getParentRight(model.getRight().getRightId());
		if(parentRight!=null){
			RoleRightId id = new RoleRightId();
			id.setRoleId(model.getRole().getRoleId());
			id.setRightId(parentRight.getRightId());
			
			RoleRight parentRoleRight = roleRightDAO.load(id);
			if(parentRoleRight!=null&&!parentRoleRight.getState()){
				roleRightDAO.updateState(model.getRole().getRoleId(),parentRight.getRightId(),true);
				setParentTrue(parentRoleRight);
			}
		}
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.RightService#save(org.linys.model.Right)
	 */
	@Override
	public ServiceResult save(Right model) {
		ServiceResult result = new ServiceResult(false);
		if(model==null){
			result.setMessage("请填写权限信息");
			return result;
		}
		if(StringUtils.isEmpty(model.getRightName())){
			result.setMessage("请填写权限名");
			return result;
		}
		if(StringUtils.isEmpty(model.getRightId())){//新增
			if(StringUtils.isEmpty(model.getParentRight().getRightId())){
				result.setMessage("请选择父权限");
				return result;
			}
			//查找该父权限下的权限排序最大值
			Integer maxArray = rightDAO.getMaxArray(model.getParentRight().getRightId());
			model.setArray(maxArray+1);
			model.setIsLeaf(true);
			rightDAO.save(model);
			if(model.getParentRight()!=null){
				rightDAO.updateIsLeaf(model.getParentRight().getRightId(),false);
				
				List<Role> allRole = roleDAO.queryAll();
				for (Role role : allRole) {
					RoleRight roleRight = new RoleRight();
					RoleRightId roleRightId = new RoleRightId();
					roleRightId.setRoleId(role.getRoleId());
					roleRightId.setRightId(model.getRightId());
					roleRight.setId(roleRightId);
					
					roleRight.setRole(role);
					roleRight.setRight(model);
					
					roleRight.setState(true);
					roleRightDAO.save(roleRight);
					//更新权限树
					setParentTrue(roleRight);
				}
			}
			result.getData().put("rightId", model.getRightId());
		}else{
			Right oldModel = rightDAO.load(model.getRightId());
			if(oldModel==null){
				if(StringUtils.isEmpty(model.getParentRight().getRightId())){
					result.setMessage("要修改的权限已不存在");
					return result;
				}
			}
			oldModel.setRightName(model.getRightName());
			oldModel.setRightUrl(model.getRightUrl());
			rightDAO.update(oldModel);
		}
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.RightService#updateArray(java.lang.String, java.lang.String)
	 */
	@Override
	public ServiceResult updateArray(String rightId, String updateRightId) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(rightId)||StringUtils.isEmpty(updateRightId)){
			result.setMessage("请选择要改变权限排序的权限");
			return result;
		}
		Right oldModel = rightDAO.load(rightId);
		Right updateRight = rightDAO.load(updateRightId);
		Integer oldArray = oldModel.getArray();
		Integer upateArray = updateRight.getArray();
		oldModel.setArray(upateArray);
		updateRight.setArray(oldArray);
		
		result.setIsSuccess(true);
		return result;
	}
	
}
