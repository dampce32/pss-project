package org.linys.util;

import org.apache.commons.lang.StringUtils;
import org.linys.vo.GobelConstants;

public class StringUtil {
	/**
	 * @Description: 将字符串source根据separator分割成字符串数组
	 * @Create: 2013-1-8 下午2:28:10
	 * @author lys
	 * @update logs
	 * @param source
	 * @param separator
	 * @return
	 */
	public static String[] split(String source,String separator){
		String[] distArray ={};
		if(source==null){
			return distArray;
		}
		int i = 0;
		distArray = new String[StringUtils.countMatches(source, separator)+1];
		while(source.length()>0){
			String value = StringUtils.substringBefore(source,separator);
			distArray[i++] = StringUtils.isEmpty(value)?null:value;
			source = StringUtils.substringAfter(source,separator);
		}
		if(distArray[distArray.length-1]==null){//排除最后一个分隔符后放空
			distArray[distArray.length-1] = null;
		}
		return distArray;
	}
	/**
	 * @Description: 将字符串source根据全局变量GobelConstants.SPLIT_SEPARATOR分割成字符串数组
	 * @Create: 2013-1-15 上午12:06:52
	 * @author lys
	 * @update logs
	 * @param source
	 * @return
	 */
	public static String[] split(String source){
		return split(source,GobelConstants.SPLIT_SEPARATOR);
	}

}
