package org.linys.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.linys.dao.ExpressDao;
import org.linys.model.Express;
import org.linys.service.ExpressService;
import org.linys.util.JSONUtil;
import org.linys.vo.ServiceResult;
import org.springframework.stereotype.Service;

/**
 * 
 * @Description:  快递service的实现类
 * @Copyright: 福州骏华科技信息有限公司 (c)2013
 * @Created Date : 2013-1-25
 * @author longweier
 * @vesion 1.0
 */
@Service
public class ExpressServiceImpl extends BaseServiceImpl<Express, String> implements ExpressService {

	@Resource
	private ExpressDao expressDao;
	
	public String queryExpress() {
		List<Express> list = expressDao.queryAll();
		String[] properties = {"expressId","expressName"};
		String jsonArray = JSONUtil.toJson(list, properties);
		return jsonArray;
	}

	public ServiceResult saveExpress(Express express) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(express.getExpressName())){
			result.setMessage("快递名称不能为空");
			return result;
		}
		//新增
		if(StringUtils.isEmpty(express.getExpressId())){
			expressDao.save(express);
		}else{
			expressDao.update(express);
		}
		result.setIsSuccess(true);
		return result;
	}

	public String queryComboboxExpress() {
		List<Express> list = expressDao.queryAll();
		String[] properties = {"expressId","expressName"};
		String jsonArray = JSONUtil.toJsonWithoutRows(list, properties);
		return jsonArray;
	}

}
