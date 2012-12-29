package org.linys.service;

import org.linys.model.Receive;
import org.linys.vo.ServiceResult;
/**
 * @Description:采购入库Service
 * @Copyright: 福州骏华信息有限公司 (c)2012
 * @Created Date : 2012-12-29
 * @author lys
 * @vesion 1.0
 */
public interface ReceiveService extends BaseService<Receive,String>{
	/**
	 * @Description: 分页查询收货
	 * @Create: 2012-12-29 下午9:55:19
	 * @author lys
	 * @update logs
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	ServiceResult query(Receive model, Integer page, Integer rows);
	/**
	 * @Description: 统计收货
	 * @Create: 2012-12-29 下午9:55:30
	 * @author lys
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult getTotalCount(Receive model);

}
