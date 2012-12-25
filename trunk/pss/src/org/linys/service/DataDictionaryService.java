package org.linys.service;

import org.linys.model.DataDictionary;
import org.linys.vo.ServiceResult;
/**
 * @Description:数据字典Service
 * @Copyright: 福州骏华信息有限公司 (c)2012
 * @Created Date : 2012-12-18
 * @author lys
 * @vesion 1.0
 */
public interface DataDictionaryService extends BaseService<DataDictionary,String>{
	/**
	 * @Description: 添加数据字典
	 * @Create: 2012-12-18 下午10:50:56
	 * @author lys
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult add(DataDictionary model);
	/**
	 * @Description: 
	 * @Create: 2012-12-18 下午10:56:12
	 * @author lys
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult update(DataDictionary model);
	/**
	 * @Description: 删除数据字典
	 * @Create: 2012-12-18 下午10:58:49
	 * @author lys
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult delete(DataDictionary model);
	/**
	 * @Description: 查询数据字典 
	 * @Create: 2012-12-18 下午10:59:07
	 * @author lys
	 * @update logs
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	ServiceResult query(DataDictionary model, Integer page, Integer rows);
	/**
	 * @Description: 统计 数据字典
	 * @Create: 2012-12-18 下午10:59:52
	 * @author lys
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult getTotalCount(DataDictionary model);
	/**
	 * @Description: 取得字典类型为kind的数据
	 * @Create: 2012-12-23 下午10:52:24
	 * @author lys
	 * @update logs
	 * @param kind
	 * @return
	 */
	String queryByDictionaryKind(String kind);

}
