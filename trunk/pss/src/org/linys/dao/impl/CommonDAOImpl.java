package org.linys.dao.impl;

import java.util.Date;

import org.hibernate.Query;
import org.linys.dao.CommonDAO;
import org.linys.model.BaseModel;
import org.linys.util.DateUtil;
import org.springframework.stereotype.Repository;

@Repository
public class CommonDAOImpl extends BaseDAOImpl<BaseModel,String> implements CommonDAO {
	/*
	 * (non-Javadoc)   
	 * @see org.linys.dao.CommonDAO#getMaxCode(java.lang.String, java.lang.String)
	 */
	@Override
	public String getCode(String table, String field,String filedPrefix) {
		
		filedPrefix = filedPrefix+DateUtil.dateToString(new Date(),"yyyyMMdd");
		StringBuilder hql = new StringBuilder();
		hql.append("select max("+field+") " );
		hql.append("from " +table+" ");
		hql.append("where  " +field+" like :prefex");
		Query query = getCurrentSession().createQuery(hql.toString());
		query.setString("prefex", filedPrefix+"%");
		String maxCode = query.uniqueResult()==null?null:query.uniqueResult().toString();
		int index = 0;
		if(maxCode!=null){
			index = Integer.parseInt(maxCode.substring(filedPrefix.length(), maxCode.length()));	
		}
		maxCode = filedPrefix+String.format("%04d", index+1);
		return maxCode;
	}

}
