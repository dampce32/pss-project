package org.linys.action;

import javax.annotation.Resource;

import org.linys.model.DataDictionary;
import org.linys.service.DataDictionaryService;
import org.linys.vo.ServiceResult;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

@Controller
@Scope("prototype")
public class DataDictionaryAction extends BaseAction implements ModelDriven<DataDictionary> {
	
	private static final long serialVersionUID = 7057554927263572080L;
	DataDictionary model = new DataDictionary();
	@Resource
	private DataDictionaryService dataDictionaryService;
	
	public DataDictionary getModel() {
		return model;
	}
	/**
	 * @Description: 添加数据字典
	 * @Create: 2012-12-18 下午10:48:29
	 * @author lys
	 * @update logs
	 */
	public void add(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = dataDictionaryService.add(model);
		} catch (Exception e) {
			result.setMessage("添加数据字典失败");
			result.setIsSuccess(false);
			e.printStackTrace();
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description: 修改数据字典
	 * @Create: 2012-12-18 下午10:55:46
	 * @author lys
	 * @update logs
	 */
	public void update(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = dataDictionaryService.update(model);
		} catch (Exception e) {
			result.setMessage("添加数据字典失败");
			result.setIsSuccess(false);
			e.printStackTrace();
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description: 删除数据字典
	 * @Create: 2012-12-18 下午10:56:44
	 * @author lys
	 * @update logs
	 */
	public void delete(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = dataDictionaryService.delete(model);
		} catch (Exception e) {
			result.setMessage("删除数据字典失败");
			result.setIsSuccess(false);
			e.printStackTrace();
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description:分组查询 数据字典 
	 * @Create: 2012-12-18 下午10:58:02
	 * @author lys
	 * @update logs
	 */
	public void query(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = dataDictionaryService.query(model,page,rows);
		} catch (Exception e) {
			result.setMessage("查询数据字典失败");
			result.setIsSuccess(false);
			e.printStackTrace();
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description: 统计 数据字典
	 * @Create: 2012-12-18 下午10:59:26
	 * @author lys
	 * @update logs
	 */
	public void getTotalCount(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = dataDictionaryService.getTotalCount(model);
		} catch (Exception e) {
			result.setMessage("查询数据字典失败");
			result.setIsSuccess(false);
			e.printStackTrace();
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}

}
