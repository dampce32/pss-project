package org.linys.service;

import org.linys.model.InvoiceType;
import org.linys.vo.ServiceResult;
/**
 * @Description:发票类型Service
 * @Copyright: 福州骏华信息有限公司 (c)2012
 * @Created Date : 2012-12-29
 * @author lys
 * @vesion 1.0
 */
public interface InvoiceTypeService extends BaseService<InvoiceType,String>{
	/**
	 * @Description: 保存发票类型
	 * @Create: 2012-12-29 下午4:29:20
	 * @author lys
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult save(InvoiceType model);
	/**
	 * @Description: 删除发票类型
	 * @Create: 2012-12-29 下午4:29:37
	 * @author lys
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult delete(InvoiceType model);
	/**
	 * @Description: 分页查询发票类型
	 * @Create: 2012-12-29 下午4:29:48
	 * @author lys
	 * @update logs
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	ServiceResult query(InvoiceType model, Integer page, Integer rows);
	/**
	 * @Description: 统计发票类型
	 * @Create: 2012-12-29 下午4:30:01
	 * @author lys
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult getTotalCount(InvoiceType model);

}
