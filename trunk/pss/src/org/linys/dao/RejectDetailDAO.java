package org.linys.dao;

import java.util.List;

import org.linys.model.RejectDetail;
/**
 * @Description:退货明细DAO
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-1-11
 * @author lys
 * @vesion 1.0
 */
public interface RejectDetailDAO extends BaseDAO<RejectDetail,String>{
	/**
	 * @Description: 根据退货单Id，查询退货单的退货明细
	 * @Create: 2013-1-11 上午11:19:04
	 * @author lys
	 * @update logs
	 * @param rejectId
	 * @return
	 */
	List<RejectDetail> queryByRejectId(String rejectId);

}
