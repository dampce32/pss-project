package org.linys.dao;

import java.util.List;

import org.linys.model.DataDictionary;

public interface DataDictionaryDAO extends BaseDAO<DataDictionary,String> {
	/**
	 * @Description: 分组查询 数据字典
	 * @Create: 2012-12-18 下午11:09:35
	 * @author lys
	 * @update logs
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	List<DataDictionary> query(DataDictionary model, Integer page, Integer rows);
	/**
	 * @Description: 统计 数据字典
	 * @Create: 2012-12-18 下午11:10:37
	 * @author lys
	 * @update logs
	 * @param model
	 * @return
	 */
	Long getTotalCount(DataDictionary model);

}
