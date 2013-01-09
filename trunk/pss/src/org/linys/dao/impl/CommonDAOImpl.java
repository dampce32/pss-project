package org.linys.dao.impl;

import java.util.List;

import org.hibernate.SQLQuery;
import org.linys.dao.CommonDAO;
import org.linys.model.BaseModel;
import org.springframework.stereotype.Repository;

@Repository
public class CommonDAOImpl extends BaseDAOImpl<BaseModel,String> implements CommonDAO {
	/*
	 * (non-Javadoc)   
	 * @see org.linys.dao.CommonDAO#getCode(java.lang.String, java.lang.String)
	 */
	@Override
	public String getCode(String type, String code) {
		StringBuilder sqlsb = new StringBuilder();
		sqlsb.append("{Call P_GetCode('a','a')}");
		SQLQuery  query = getCurrentSession().createSQLQuery(sqlsb.toString());
//		 query.setString("type", type);
//		 query.setString("code", code);
		List list = query.list();
		return (String) query.uniqueResult();
	}

}
