package org.linys.service;

import org.linys.model.Prepay;
import org.linys.vo.ServiceResult;
/**
 * @Description:预付单Service
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-1-23
 * @author lys
 * @vesion 1.0
 */
public interface PrepayService extends BaseService<Prepay,String>{
	/**
	 * @Description: 分页查询预付单
	 * @Create: 2013-1-23 下午9:54:31
	 * @author lys
	 * @update logs
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	ServiceResult query(Prepay model, Integer page, Integer rows);
	/**
	 * @Description: 统计预付单
	 * @Create: 2013-1-23 下午9:54:46
	 * @author lys
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult getTotalCount(Prepay model);
	/**
	 * @Description: 保存预付单
	 * @Create: 2013-1-23 下午9:54:59
	 * @author lys
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult save(Prepay model);
	/**
	 * @Description: 打开初始化预付单
	 * @Create: 2013-1-23 下午9:55:36
	 * @author lys
	 * @update logs
	 * @param prepayId
	 * @return
	 */
	ServiceResult init(String prepayId);
	/**
	 * @Description: 删除预付单
	 * @Create: 2013-1-23 下午9:55:51
	 * @author lys
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult delete(Prepay model);
	/**
	 * @Description:  批量删除预付单
	 * @Create: 2013-1-23 下午9:56:08
	 * @author lys
	 * @update logs
	 * @param ids
	 * @return
	 */
	ServiceResult mulDelele(String ids);
	/**
	 * @Description: 更新预付单状态
	 * @Create: 2013-1-23 下午9:56:21
	 * @author lys
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult updateStatus(Prepay model);
	/**
	 * @Description: 批量更新预付单状态
	 * @Create: 2013-1-23 下午9:56:33
	 * @author lys
	 * @update logs
	 * @param ids
	 * @param model
	 * @return
	 */
	ServiceResult mulUpdateStatus(String ids, Prepay model);

}
