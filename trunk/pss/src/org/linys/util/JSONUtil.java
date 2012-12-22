package org.linys.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.linys.model.Right;

/**
 * @Description: 处理json数据的工具类
 * @Copyright: 福州骏华信息有限公司 (c)2012
 * @Created Date : 2012-10-27
 * @author lys
 * @vesion 1.0
 */
public class JSONUtil{
	public static final String EMPTYJSON = "{}";
	/**
	 * @Description: 将List型数据转化成Json数据
	 * @Create: 2012-10-27 上午10:26:14
	 * @author lys
	 * @update logs
	 * @param list
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static String toJson(List list){
		JSONArray jsonArray = JSONArray.fromObject(list);
		return jsonArray.toString();
	}
	/**
	 * @Description: 将List型数据转化成Json数据,并指定要选取的属性 
	 * @Create: 2012-10-27 上午10:48:13
	 * @author lys
	 * @update logs
	 * @param list
	 * @param filterList
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static String toJson(List list,List<String> propertyList){
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setJsonPropertyFilter(new JSONPropertyFilter(propertyList));
		JSONArray jsonArray = JSONArray.fromObject(list, jsonConfig);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", jsonArray);
		String result = JSONObject.fromObject(map).toString();
		return result;
	}
	/**
	 * @Description:  将List型数据转化成Json数据,并指定要选取的属性 ( easyui datagrid中使用)
	 * @Create: 2012-10-27 上午10:50:45
	 * @author lys
	 * @update logs
	 * @param list
	 * @param propertyList
	 * @param total
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static String toJson(List list,List<String> propertyList,Long total){
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setJsonPropertyFilter(new JSONPropertyFilter(propertyList));
		JSONArray jsonArray = JSONArray.fromObject(list, jsonConfig);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", total);
		map.put("rows", jsonArray);
		String result = JSONObject.fromObject(map).toString();
		return result;
	}
	/**
	 * @Description: 将List型数据转化成Json数据,并指定要选取的属性 ( easyui datagrid中使用)
	 * @Create: 2012-10-28 上午9:25:48
	 * @author lys
	 * @update logs
	 * @param list
	 * @param total
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static String toJson(List list,Long total){
		JSONArray jsonArray = JSONArray.fromObject(list);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", total);
		map.put("rows", jsonArray);
		String result = JSONObject.fromObject(map).toString();
		return result;
	}
	
	public static void main(String[] args) {
		List<Right> list = new ArrayList<Right>();
		Right right = new Right();
		right.setRightName("name");
		list.add(right);
		System.out.println(JSONUtil.toJson(list));
		
		List<String> filterList = new ArrayList<String>();
		filterList.add("rightName");
		System.out.println(JSONUtil.toJson(list,filterList));
		
		System.out.println(JSONUtil.toJson(list,filterList,1L));
	}
}
