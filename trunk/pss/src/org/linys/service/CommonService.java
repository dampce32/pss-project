package org.linys.service;

import org.linys.vo.ServiceResult;

/**
 * @Description:公共Service
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-1-5
 * @author lys
 * @vesion 1.0
 */
public interface CommonService {
	/**
	 * @Description: 取得编号
	 * @Create: 2013-1-5 下午9:31:44
	 * @author lys
	 * @update logs
	 * @param type
	 * @param code
	 * @return
	 */
	ServiceResult getCode(String type, String code);

}
