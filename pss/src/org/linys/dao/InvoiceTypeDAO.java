package org.linys.dao;

import java.util.List;

import org.linys.model.InvoiceType;

/**
 * @Description:发票类型DAO
 * @Copyright: 福州骏华信息有限公司 (c)2012
 * @Created Date : 2012-12-29
 * @author lys
 * @vesion 1.0
 */
public interface InvoiceTypeDAO extends BaseDAO<InvoiceType, String> {
	/**
	 * @Description: 分页查询发票类型
	 * @Create: 2012-12-29 下午4:32:43
	 * @author lys
	 * @update logs
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	List<InvoiceType> invoiceTypeDAO(InvoiceType model, Integer page,
			Integer rows);
	/**
	 * @Description: 统计发票类型
	 * @Create: 2012-12-29 下午4:33:14
	 * @author lys
	 * @update logs
	 * @param model
	 * @return
	 */
	Long getTotalCount(InvoiceType model);

}
