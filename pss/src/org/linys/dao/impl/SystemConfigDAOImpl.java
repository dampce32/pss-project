package org.linys.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.linys.dao.SystemConfigDAO;
import org.linys.model.SystemConfig;
import org.springframework.stereotype.Repository;
/**
 * @Description:系统配置DAO实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-2-4
 * @author lys
 * @vesion 1.0
 */
@Repository
public class SystemConfigDAOImpl extends BaseDAOImpl<SystemConfig, String>
		implements SystemConfigDAO {
	/*
	 * (non-Javadoc)   
	 * @see org.linys.dao.SystemConfigDAO#init()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public SystemConfig init() {
		Criteria criteria  = getCurrentSession().createCriteria(SystemConfig.class);
		
		criteria.setFirstResult(0);
		criteria.setMaxResults(1);
		List<SystemConfig> list = criteria.list();
		if(list==null||list.size()==0){
			return null;
		}else{
			return list.get(0);
		}
	}

}
