package org.linys.service;

import org.linys.model.Prefix;
import org.linys.vo.ServiceResult;
/**
 * @Description:编号前缀Service
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-1-21
 * @author lys
 * @vesion 1.0
 */
public interface PrefixService extends BaseService<Prefix,String>{
	/**
	 * @Description: 保存编号前缀
	 * @Create: 2013-1-21 下午9:00:46
	 * @author lys
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult save(Prefix model);
	/**
	 * @Description: 删除编号前缀
	 * @Create: 2013-1-21 下午9:00:59
	 * @author lys
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult delete(Prefix model);
	/**
	 * @Description: :分页查询编号前缀
	 * @Create: 2013-1-21 下午9:01:14
	 * @author lys
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult query(Prefix model);

}
