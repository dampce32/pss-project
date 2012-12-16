package org.linys.service.impl;


import java.io.Serializable;

import org.linys.dao.BaseDAO;
import org.linys.service.BaseService;

public class BaseServiceImpl<T,PK extends Serializable> implements BaseService<T,PK>{

	private BaseDAO<T,PK> baseDAO;
	
	@SuppressWarnings("rawtypes")
	public BaseDAO getBaseDAO() {
		return baseDAO;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

}
