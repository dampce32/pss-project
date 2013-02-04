package org.linys.dao;

import java.util.List;

import org.linys.model.Split;
import org.linys.model.SplitDetail;

/**
 * 
 * @Description: 拆分明细dao实现类
 * @Copyright: 福州骏华科技信息有限公司 (c)2013
 * @Created Date : 2013-2-4
 * @author longweier
 * @vesion 1.0
 */
public interface SplitDetailDao extends BaseDAO<SplitDetail, String> {
	
	/**
	 * 
	 * @Description: 查询某个拆分的拆分明细
	 * @Create: 2013-2-4 下午05:16:48
	 * @author longweier
	 * @update logs
	 * @param split
	 * @return
	 * @throws Exception
	 */
	public List<SplitDetail> querySplitDetail(Split split);
}
