package org.linys.dao;

import java.util.List;
import java.util.Map;

import org.linys.model.Prepay;
/**
 * @Description:预付单DAO
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-1-23
 * @author lys
 * @vesion 1.0
 */
public interface PrepayDAO extends BaseDAO<Prepay,String>{
	/**
	 * @Description: 分页查询预付单
	 * @Create: 2013-1-23 下午10:04:31
	 * @author lys
	 * @update logs
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	List<Prepay> query(Prepay model, Integer page, Integer rows);
	/**
	 * @Description: 统计预付单
	 * @Create: 2013-1-23 下午10:04:57
	 * @author lys
	 * @update logs
	 * @param model
	 * @return
	 */
	Long getTotalCount(Prepay model);
	/**
	 * @Description: 判断该预付单是否已经进入了 付款单 
	 * @Create: 2013-1-30 下午2:43:25
	 * @author lys
	 * @update logs
	 * @param prepayId
	 * @return
	 */
	List<Map<String,Object>> countPrepay(String prepayId);

}
