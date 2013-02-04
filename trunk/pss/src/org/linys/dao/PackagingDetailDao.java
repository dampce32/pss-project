package org.linys.dao;

import java.util.List;

import org.linys.model.Packaging;
import org.linys.model.PackagingDetail;

/**
 * 
 * @Description: 组装明细dao
 * @Copyright: 福州骏华科技信息有限公司 (c)2013
 * @Created Date : 2013-2-3
 * @author longweier
 * @vesion 1.0
 */
public interface PackagingDetailDao extends BaseDAO<PackagingDetail, String> {

	/**
	 * 
	 * @Description: 通过组装查询组装明细
	 * @Create: 2013-2-3 下午09:33:57
	 * @author longweier
	 * @update logs
	 * @param packaging
	 * @return
	 * @throws Exception
	 */
	public List<PackagingDetail> queryByPackaging(Packaging packaging);
}
