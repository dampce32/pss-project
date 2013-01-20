package org.linys.dao;

import java.util.List;

import org.linys.model.PayDetail;
/**
 * @Description:付款明细DAO
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-1-20
 * @author lys
 * @vesion 1.0
 */
public interface PayDetailDAO extends BaseDAO<PayDetail,String>{
	/**
	 * @Description: 查找付款单payId下的付款明细
	 * @Create: 2013-1-20 下午4:23:06
	 * @author lys
	 * @update logs
	 * @param payId
	 * @return
	 */
	List<PayDetail> queryByPayId(String payId);

}
