package org.linys.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
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
	public static String toJsonWithoutRows(List list,List<String> propertyList){
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setJsonPropertyFilter(new JSONPropertyFilter(propertyList));
		JSONArray jsonArray = JSONArray.fromObject(list, jsonConfig);
		String result = jsonArray.toString();
		return result;
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
	
	
	@SuppressWarnings("rawtypes")
	public static String toJson(List list,String[] propertiesName){
		JSONObject object = new JSONObject();
		JSONArray array = new JSONArray();
		try {
			for (Object t : list) {
				JSONObject item = new JSONObject();
				for(String property : propertiesName){
					/*
					 * 处理property 
					 * property的规则是:
					 * 如果包含有：这说明指定了key的别名
					 * 
					 * 如果没有则key的名称，要最后一个.后的名称为key
					 */
					String key = null;
					if(property.contains(":")){
						 key = StringUtils.substringAfter(property, ":");
						 property =  StringUtils.substringBefore(property, ":");
					}else{
						if(property.contains(".")){
							 key =  StringUtils.substringAfterLast(property, ".");
						}else{
							key = property;
						}
					}
					if(property.contains(".")){
						String propertyPrefix = "";
						String propertySuffix = property;
						
						boolean canGetProperty = true;
						/*
						 * 判读.前的属性是否为null，如果为null，则不能读取该属性
						 * 如果不为null，则需截取.后的属性，继续相同判断，直到最后截取的属性中不含有.
						 */
						while(propertySuffix.contains(".")){
							propertyPrefix = StringUtils.substringBefore(propertySuffix, ".");
							
							if(BeanUtils.getProperty(t, propertyPrefix)==null){
								canGetProperty = false;
								break;
							}
							propertySuffix = StringUtils.substringAfter(propertySuffix, ".");
						}
						if(canGetProperty){
							item.put(key, BeanUtils.getProperty(t, property));
						}
					}else{
						item.put(key, BeanUtils.getProperty(t, property));
					}
				}
				array.add(item);
			}
			object.put("rows", array);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object.toString();
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
