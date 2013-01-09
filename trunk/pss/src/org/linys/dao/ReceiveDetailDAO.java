package org.linys.dao;

import java.util.List;

import org.linys.model.ReceiveDetail;
/**
 * @Description:收货明细DAO
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-1-1
 * @author lys
 * @vesion 1.0
 */
public interface ReceiveDetailDAO extends BaseDAO<ReceiveDetail,String>{
	/**
	 * @Description: 根据收货单Id查询收货明细
	 * @Create: 2013-1-3 上午11:08:24
	 * @author lys
	 * @update logs
	 * @param receiveId
	 * @return
	 */
	List<ReceiveDetail> queryByReceiveId(String receiveId);

}
