package org.linys.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.linys.dao.ReportParamConfigDAO;
import org.linys.model.ReportParamConfig;
import org.springframework.stereotype.Repository;
/**
 * @Description:报表参数配置DAO实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-1-31
 * @author lys
 * @vesion 1.0
 */
@Repository
public class ReportParamConfigDAOImpl extends
		BaseDAOImpl<ReportParamConfig, String> implements ReportParamConfigDAO {
	/*
	 * (non-Javadoc)   
	 * @see org.linys.dao.ReportParamConfigDAO#queryByReportConfigId(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ReportParamConfig> queryByReportConfigId(String reportConfigId) {
		Criteria criteria  = getCurrentSession().createCriteria(ReportParamConfig.class);
		criteria.add(Restrictions.eq("reportConfig.reportConfigId", reportConfigId));
		return criteria.list();
	}

}
