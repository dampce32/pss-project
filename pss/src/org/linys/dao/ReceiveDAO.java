package org.linys.dao;

import java.util.List;

import org.linys.model.Receive;
/**
 * @Description:收货DAO
 * @Copyright: 福州骏华信息有限公司 (c)2012
 * @Created Date : 2012-12-29
 * @author lys
 * @vesion 1.0
 */
public interface ReceiveDAO extends BaseDAO<Receive,String>{
	/**
	 * @Description: 分页查询收货
	 * @Create: 2012-12-29 下午9:58:06
	 * @author lys
	 * @update logs
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	List<Receive> query(String kind,Receive model, Integer page, Integer rows);
	/**
	 * @Description: 统计收货
	 * @Create: 2012-12-29 下午9:58:32
	 * @author lys
	 * @update logs
	 * @param model
	 * @return
	 */
	Long getTotalCount(String kind,Receive model);
	/**
	 * @Description: 取得当天最大的收货单编号
	 * @Create: 2013-1-7 下午10:16:20
	 * @author lys
	 * @update logs
	 * @param receiveCode
	 * @return
	 */
	String getMaxCode(String receiveCode);

}
