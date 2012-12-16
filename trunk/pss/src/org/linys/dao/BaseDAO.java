package org.linys.dao;

import java.io.Serializable;

/**
 * 基本DAO
 * 包含了对实体T的基本操作：
 * 增 save 删 delete 改 update 分页查询 get和count
 * @author LYS
 *
 * @param <T>
 */
public interface BaseDAO<T,PK extends Serializable> {

	/**
	 * @Description: 保存实体
	 * @Created: 2012-10-8 下午1:49:13
	 * @Author lys
	 * @param model
	 */
	public void save(T model);
	/**
	 * @Description:  删除相应实体
	 * @Created: 2012-10-8 下午1:48:36
	 * @Author lys
	 * @param model 要删除的实体
	 */
	public void delete(T model);
	/**
	 * @Description: 根据主键删除实体
	 * @Create: 2012-10-27 下午2:21:26
	 * @author lys
	 * @update logs
	 * @param pk
	 * @throws Exception
	 */
	public void delete(PK pk);
	/**
	 * 更新相应实体
	 * @param model
	 */
	public void update(T model);
	/**
	 * @Description: 根据id取得model
	 * @Create: 2012-9-17 下午11:27:16
	 * @author lys
	 * @param id
	 * @return
	 */
	public T load(PK id);
	/**
	 * @Description: 根据某种属性取得model
	 * @Create: 2012-9-17 下午11:33:29
	 * @author lys
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public T load(String propertyName, Object value);
	/**
	 * @Description: 根据多个属性取得Model
	 * @Create: 2012-10-14 下午10:18:00
	 * @author lys
	 * @update logs
	 * @param propertyNames
	 * @param values
	 * @return
	 * @throws Exception
	 */
	public T load(String[] propertyNames, Object[] values);
}
