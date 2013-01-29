package org.linys.util;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.linys.model.ProductType;
import org.linys.model.Right;
import org.linys.model.RoleRight;
import org.linys.vo.TreeNode;
import org.linys.vo.TreeNode.StateType;
/**
 * @Description:easyui 生产树的工具类
 * @Copyright: 福州骏华信息有限公司 (c)2012
 * @Created Date : 2012-10-14
 * @author lys
 * @vesion 1.0
 */
public class TreeUtil {
	/**
	 * 空树对象
	 */
	public static final String EMPTY = "[]";
	/**
	 * @Description: 将树节点转化成JSONObject对象
	 * @Create: 2012-10-14 下午11:22:50
	 * @author lys
	 * @update logs
	 * @param treeNode
	 * @return
	 * @throws Exception
	 */
	public static JSONObject toJSONObject(TreeNode treeNode){
		JSONObject object = new JSONObject();
		try {
			object.put("id", treeNode.getId());
			object.put("text", treeNode.getText());
			object.put("iconCls", treeNode.getIconCls());
			if(treeNode.getChecked()!=null&&treeNode.getChecked()==true){
				object.put("checked", treeNode.getChecked());
			}
			object.put("state", treeNode.getState());
			if(treeNode.getAttributes()!=null && !treeNode.getAttributes().isEmpty()){
				object.put("attributes", treeNode.getAttributes());
			}
			if(treeNode.getChildren().size()>0){
				object.put("children", toJSONArray(treeNode.getChildren()));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return object;
	}
	/**
	 * @Description: 将多个树节点转化成JSONArray
	 * @Create: 2012-10-14 下午11:24:05
	 * @author lys
	 * @update logs
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public static JSONArray toJSONArray(List<TreeNode> list){
		JSONArray array = new JSONArray();
		for(TreeNode tree : list){
			array.add(toJSONObject(tree));
		}
		return array;
	}
	/**
	 * @Description: 生成单节点树
	 * @Create: 2012-10-14 下午11:25:13
	 * @author lys
	 * @update logs
	 * @param treeNode
	 * @return
	 * @throws Exception
	 */
	public static String toJSON(TreeNode treeNode){
		JSONObject object = toJSONObject(treeNode);
		return "["+object.toString()+"]";
	}
	/**
	 * @Description: 生成多节点树
	 * @Create: 2012-10-14 下午11:25:58
	 * @author lys
	 * @update logs
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public static String toJSON(List<TreeNode> list){
		JSONArray array = toJSONArray(list);
		return array.toString();
	}
	/**
	 * @Description: 将权限Right转化成TreeNode
	 * @Create: 2012-10-14 下午11:34:39
	 * @author lys
	 * @update logs
	 * @param right
	 * @return
	 * @throws Exception
	 */
	public static TreeNode toTreeNode(Right right){
		if(right==null){
			return null;
		}
		TreeNode treeNode = new TreeNode();
		treeNode.setId(right.getRightId());
		treeNode.setText(right.getRightName());
		treeNode.setChecked(right.getState());
		if(!right.getIsLeaf()&&!"系统权限".equals(right.getRightName())){
			treeNode.setState(StateType.closed);
		}
		treeNode.getAttributes().put("rightUrl", right.getRightUrl());
		return treeNode;
	}
	/**
	 * @Description: 将商品类型转化成树节点
	 * @Create: 2012-12-23 下午3:06:29
	 * @author lys
	 * @update logs
	 * @param right
	 * @return
	 */
	public static TreeNode toTreeNode(ProductType productType){
		if(productType==null){
			return null;
		}
		TreeNode treeNode = new TreeNode();
		treeNode.setId(productType.getProductTypeId());
		treeNode.setText(productType.getProductTypeName());
		if(!productType.getIsLeaf()){
			treeNode.setState(StateType.closed);
		}
		return treeNode;
	}
	/**
	 * @Description: 将List<Right>转化成List<TreeNode>
	 * @Create: 2012-10-14 下午11:43:15
	 * @author lys
	 * @update logs
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public static List<TreeNode> toTreeNodeList(List<Right> list){
		if(list==null){
			return null;
		}
		List<TreeNode> treeNodeList = new ArrayList<TreeNode>();
		for (Right right : list) {
			treeNodeList.add(toTreeNode(right));
		}
		return treeNodeList;
	}
	/**
	 * @Description: 将List<Right>生成JSON字符串
	 * @Create: 2012-10-14 下午11:46:04
	 * @author lys
	 * @update logs
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public static String toJSONRightList(List<Right> list){
		List<TreeNode> treeNodeList = toTreeNodeList(list);
		return toJSON(treeNodeList);
	}
	
	
	public static void main(String[] args) {
		TreeNode treeNode = new TreeNode();
		treeNode.setId(1);
		
		TreeNode treeNode2 = new TreeNode();
		treeNode2.setId(2);
		
		TreeNode treeNode3 = new TreeNode();
		treeNode3.setId(3);
		
		TreeNode treeNode4 = new TreeNode();
		treeNode4.setId(4);
		
		treeNode.getChildren().add(treeNode4);
		
		treeNode3.getChildren().add(treeNode);
		treeNode3.getChildren().add(treeNode2);
		treeNode3.getAttributes().put("url", "myUrl");
		
		TreeNode treeNode5 = new TreeNode();
		treeNode5.setText("系统权限");
		
		List<TreeNode> list = new ArrayList<TreeNode>();
		list.add(treeNode);
		list.add(treeNode2);
		
		List<TreeNode> list2 = new ArrayList<TreeNode>();
		
		String result1 = TreeUtil.toJSONObject(treeNode).toString();
		System.out.println("toJSONObject: "+result1);
		
		String result2 = TreeUtil.toJSONArray(list).toString();
		System.out.println("toJSONArray: "+result2);
		
		String result3 = TreeUtil.toJSON(treeNode);
		System.out.println("toJSON(TreeNode): "+result3);
		
		String result4 = TreeUtil.toJSON(list);
		System.out.println("toJSON(List<TreeNode>) : "+result4);
		
		String result5 = TreeUtil.toJSON(treeNode3);
		System.out.println("toJSON(TreeNode) have children : "+result5);
		
		treeNode5.setChildren(list);
		String result6 = TreeUtil.toJSON(treeNode5);
		System.out.println("空节点 : "+result6);
		
		
		String result7 = TreeUtil.toJSON(list2);
		System.out.println("空List : "+result7);
	}
	/**
	 * @Description: 角色权限List转化成树节点List
	 * @Create: 2012-10-27 下午9:17:45
	 * @author lys
	 * @update logs
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public static List<TreeNode> toTreeNodeListRoleRight(List<RoleRight> list) {
		if(list==null){
			return null;
		}
		List<TreeNode> treeNodeList = new ArrayList<TreeNode>();
		for (RoleRight roleRight : list) {
			treeNodeList.add(toTreeNode(roleRight));
		}
		return treeNodeList;
	}
	/**
	 * @Description: 将角色权限RoleRight转化成树节点TreeNode
	 * @Create: 2012-10-27 下午9:20:45
	 * @author lys
	 * @update logs
	 * @param roleRight
	 * @return
	 * @throws Exception
	 */
	private static TreeNode toTreeNode(RoleRight roleRight) {
		if(roleRight==null){
			return null;
		}
		TreeNode treeNode = new TreeNode();
		treeNode.setId(roleRight.getId().getRoleId()+"_"+roleRight.getId().getRightId());
		treeNode.setText(roleRight.getRight().getRightName());
		treeNode.setChecked(roleRight.getState());
		if(!roleRight.getRight().getIsLeaf()){
			treeNode.setState(StateType.closed);
		}
		return treeNode;
	}
	
	
	

}
