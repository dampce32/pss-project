package org.linys.service;

import org.linys.model.Express;
import org.linys.vo.ServiceResult;

/**
 * 
 * @Description:  快递service
 * @Copyright: 福州骏华科技信息有限公司 (c)2013
 * @Created Date : 2013-1-25
 * @author longweier
 * @vesion 1.0
 */
public interface ExpressService extends BaseService<Express, String> {
	
	/**
	 * 
	 * @Description: 新增/修改快递
	 * @Create: 2013-1-25 上午12:57:37
	 * @author longweier
	 * @update logs
	 * @param express
	 * @return
	 * @throws Exception
	 */
	public ServiceResult saveExpress(Express express);
	
	/**
	 * 
	 * @Description: 查询快递列表
	 * @Create: 2013-1-25 上午12:58:26
	 * @author longweier
	 * @update logs
	 * @return
	 * @throws Exception
	 */
	public String queryExpress();
	
	/**
	 * 
	 * @Description: 查询快递combobox
	 * @Create: 2013-1-25 上午01:01:49
	 * @author longweier
	 * @update logs
	 * @return
	 * @throws Exception
	 */
	public String queryComboboxExpress();
}
