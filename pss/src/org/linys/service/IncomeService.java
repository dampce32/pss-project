package org.linys.service;

import org.linys.model.Income;
import org.linys.vo.ServiceResult;
/**
 * @Description:收入Service
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-1-25
 * @author lys
 * @vesion 1.0
 */
public interface IncomeService extends BaseService<Income,String>{
	/**
	 * @Description: 保存收入
	 * @Create: 2013-1-25 下午8:06:30
	 * @author lys
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult save(Income model);
	/**
	 * @Description: 删除收入
	 * @Create: 2013-1-25 下午8:06:46
	 * @author lys
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult delete(Income model);
	/**
	 * @Description: 批量删除收入
	 * @Create: 2013-1-25 下午8:10:28
	 * @author lys
	 * @update logs
	 * @param ids
	 * @return
	 */
	ServiceResult mulDelete(String ids);
	/**
	 * @Description: 分页查询收入
	 * @Create: 2013-1-25 下午8:07:00
	 * @author lys
	 * @update logs
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	ServiceResult query(Income model, Integer page, Integer rows);
	/**
	 * @Description: 统计收入
	 * @Create: 2013-1-25 下午8:07:15
	 * @author lys
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult getTotalCount(Income model);
	/**
	 * @Description: 修改收入状态
	 * @Create: 2013-1-25 下午8:09:40
	 * @author lys
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult updateStatus(Income model);
	/**
	 * @Description: 批量修改收入状态
	 * @Create: 2013-1-25 下午8:09:51
	 * @author lys
	 * @update logs
	 * @param ids
	 * @param model
	 * @return
	 */
	ServiceResult mulUpdateStatus(String ids, Income model);
	/**
	 * @Description: 打开初始化
	 * @Create: 2013-1-25 下午10:45:05
	 * @author lys
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult init(Income model);

}
