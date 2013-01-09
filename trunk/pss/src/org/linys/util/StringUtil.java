package org.linys.util;

import org.apache.commons.lang.StringUtils;

public class StringUtil {
	/**
	 * @Description: 
	 * @Create: 2013-1-8 下午2:28:10
	 * @author lys
	 * @update logs
	 * @param source
	 * @param separator
	 * @return
	 */
	public static String[] split(String source,String separator){
		int i = 0;
		String[] distArray = new String[StringUtils.countMatches(source, separator)+1];
		while(source.length()>0){
			distArray[i++] = StringUtils.substringBefore(source,separator);
			source = StringUtils.substringAfter(source,separator);
		}
		if(distArray[distArray.length-1]==null){//排除最后一个分隔符后放空
			distArray[distArray.length-1] = "";
		}
		return distArray;
	}

}
