package org.linys.dao;

import java.util.List;

import org.linys.model.ReportConfig;

public interface ReportConfigDAO extends BaseDAO<ReportConfig,String>{
	/**
	 * @Description: 分页查询报表配置
	 * @Create: 2013-1-23 下午2:26:26
	 * @author lys
	 * @update logs
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	List<ReportConfig> query(ReportConfig model, Integer page, Integer rows);
	/**
	 * @Description:  统计报表配置
	 * @Create: 2013-1-23 下午2:27:18
	 * @author lys
	 * @update logs
	 * @param model
	 * @return
	 */
	Long getTotalCount(ReportConfig model);

}
