package org.linys.dao;

import org.linys.model.BaseModel;

/**
 * @Description:公共DAO
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-1-5
 * @author lys
 * @vesion 1.0
 */
public interface CommonDAO extends BaseDAO<BaseModel,String>{
	/**
	 * @Description: 取得编号
	 * @Create: 2013-1-5 下午9:35:26
	 * @author lys
	 * @update logs
	 * @param type
	 * @param code
	 * @return
	 */
	String getCode(String type, String code);

}
