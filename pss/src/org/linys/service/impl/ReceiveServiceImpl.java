package org.linys.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.linys.dao.ReceiveDAO;
import org.linys.model.Receive;
import org.linys.service.ReceiveService;
import org.linys.util.JSONUtil;
import org.linys.vo.ServiceResult;
import org.springframework.stereotype.Service;
@Service
public class ReceiveServiceImpl extends BaseServiceImpl<Receive, String>
		implements ReceiveService {
	@Resource
	private ReceiveDAO receiveDAO;
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.ReceiveService#query(org.linys.model.Receive, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public ServiceResult query(Receive model, Integer page, Integer rows) {
		ServiceResult result = new ServiceResult(false);
		
		List<Receive> list = receiveDAO.query(model,page,rows);
		
		String[] properties = {"receiveId","receiveCode"};
		String data = JSONUtil.toJson(list,properties);
		result.addData("datagridData", data);
		
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.ReceiveService#getTotalCount(org.linys.model.Receive)
	 */
	@Override
	public ServiceResult getTotalCount(Receive model) {
		ServiceResult result = new ServiceResult(false);
		Long data = receiveDAO.getTotalCount(model);
		result.addData("total", data);
		result.setIsSuccess(true);
		return result;
	}

}
