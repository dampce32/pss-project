package org.linys.dao;

import java.util.Date;

import org.linys.bean.Pager;
import org.linys.model.Split;

/**
 * 
 * @Description: 拆分dao
 * @Copyright: 福州骏华科技信息有限公司 (c)2013
 * @Created Date : 2013-2-4
 * @author longweier
 * @vesion 1.0
 */
public interface SplitDao extends BaseDAO<Split, String> {
	
	/**
	 * 
	 * @Description: 查询
	 * @Create: 2013-2-4 下午05:12:00
	 * @author longweier
	 * @update logs
	 * @param pager
	 * @param split
	 * @param beginDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	public Pager querySplit(Pager pager,Split split,Date beginDate,Date endDate);
}
