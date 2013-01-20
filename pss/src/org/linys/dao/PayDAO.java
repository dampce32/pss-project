package org.linys.dao;

import java.util.List;

import org.linys.model.Pay;

/**
 * @Description:付款单DAO
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-1-20
 * @author lys
 * @vesion 1.0
 */
public interface PayDAO extends BaseDAO<Pay,String>{
	/**
	 * @Description: 分页查询付款单
	 * @Create: 2013-1-20 下午3:11:27
	 * @author lys
	 * @update logs
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	List<Pay> query(Pay model, Integer page, Integer rows);
	/**
	 * @Description: 统计付款单
	 * @Create: 2013-1-20 下午3:12:35
	 * @author lys
	 * @update logs
	 * @param model
	 * @return
	 */
	Long getTotalCount(Pay model);

}
