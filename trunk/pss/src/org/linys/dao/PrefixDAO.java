package org.linys.dao;

import java.util.List;

import org.linys.model.Prefix;
/**
 * @Description:编号前缀DAO
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-1-21
 * @author lys
 * @vesion 1.0
 */
public interface PrefixDAO extends BaseDAO<Prefix,String>{
	/**
	 * @Description: 查询编号前缀
	 * @Create: 2013-1-21 下午9:20:31
	 * @author lys
	 * @update logs
	 * @param model
	 * @return
	 */
	List<Prefix> query(Prefix model);
	/**
	 * @Description: 取得编号前缀
	 * @Create: 2013-1-21 下午10:35:05
	 * @author lys
	 * @update logs
	 * @param string
	 * @return
	 */
	String getPrefix(String prefixCode);

}
