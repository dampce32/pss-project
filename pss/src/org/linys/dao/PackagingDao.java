package org.linys.dao;

import java.util.Date;

import org.linys.bean.Pager;
import org.linys.model.Packaging;

/**
 * 
 * @Description: 组装Dao
 * @Copyright: 福州骏华科技信息有限公司 (c)2013
 * @Created Date : 2013-2-3
 * @author longweier
 * @vesion 1.0
 */
public interface PackagingDao extends BaseDAO<Packaging, String> {
	
	/**
	 * 
	 * @Description: 查询
	 * @Create: 2013-2-3 下午09:05:11
	 * @author longweier
	 * @update logs
	 * @param pager
	 * @param packaging
	 * @param beginDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	public Pager queryPackaging(Pager pager,Packaging packaging,Date beginDate,Date endDate);
	
}
