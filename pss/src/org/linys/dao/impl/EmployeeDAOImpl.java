package org.linys.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.linys.dao.EmployeeDAO;
import org.linys.model.Employee;
import org.linys.vo.GobelConstants;
import org.springframework.stereotype.Repository;
@Repository
public class EmployeeDAOImpl extends BaseDAOImpl<Employee, String> implements
		EmployeeDAO {
	/*
	 * (non-Javadoc)   
	 * @see org.linys.dao.EmployeeDAO#query(org.linys.model.Employee, java.lang.Integer, java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Employee> query(Employee model, Integer page, Integer rows) {
		Criteria criteria  = getCurrentSession().createCriteria(Employee.class);
		
		
		if(model!=null&&StringUtils.isNotEmpty(model.getEmployeeName())){
			criteria.add(Restrictions.like("employeeName", model.getEmployeeName(),MatchMode.ANYWHERE));
		}
		
		if(page==null||page<1){
			page = 1;
		}
		if(rows==null||rows<0){
			rows = GobelConstants.DEFAULTPAGESIZE;
		}
		
		Integer begin = (page-1)*rows;
		
		criteria.setFirstResult(begin);
		criteria.setMaxResults(rows);
		
		return criteria.list();
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.dao.EmployeeDAO#getTotalCount(org.linys.model.Employee)
	 */
	@Override
	public Long getTotalCount(Employee model) {
		Criteria criteria  = getCurrentSession().createCriteria(Employee.class);
		
		
		if(model!=null&&StringUtils.isNotEmpty(model.getEmployeeName())){
			criteria.add(Restrictions.like("employeeName", model.getEmployeeName(),MatchMode.ANYWHERE));
		}
		criteria.setProjection(Projections.rowCount());
		return new Long(criteria.uniqueResult().toString());
	}
	
	

}
