package org.linys.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.linys.dao.SupplierDAO;
import org.linys.model.Supplier;
import org.linys.service.SupplierService;
import org.linys.util.ExcelPOIUtil;
import org.linys.util.JSONUtil;
import org.linys.vo.ServiceResult;
import org.springframework.stereotype.Service;
/**
 * @Description:供应商Service实现类
 * @Copyright: 福州骏华信息有限公司 (c)2012
 * @Created Date : 2012-12-29
 * @author lys
 * @vesion 1.0
 */
@Service
public class SupplierServiceImpl extends BaseServiceImpl<Supplier, String>
		implements SupplierService {
	@Resource
	private SupplierDAO supplierDAO;
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.SupplierService#save(org.linys.model.Supplier)
	 */
	@Override
	public ServiceResult save(Supplier model) {
		ServiceResult result = new ServiceResult(false);
		if(model==null){
			result.setMessage("请填写供应商信息");
			return result;
		}
		if(StringUtils.isEmpty(model.getSupplierCode())){
			result.setMessage("请填写供应商编号");
			return result;
		}
		if(StringUtils.isEmpty(model.getSupplierName())){
			result.setMessage("请填写供应商名称");
			return result;
		}
		
		if(StringUtils.isEmpty(model.getSupplierId())){//新增
			Supplier oldModel = supplierDAO.load("supplierCode", model.getSupplierCode());
			if(oldModel!=null){
				result.setMessage("该供应商编号已存在");
				return result;
			}
			supplierDAO.save(model);
		}else{
			Supplier oldModel = supplierDAO.load(model.getSupplierId());
			if(oldModel==null){
				result.setMessage("该供应商已不存在");
				return result;
			}else if(!oldModel.getSupplierCode().equals(model.getSupplierCode())){
				Supplier oldSupplier = supplierDAO.load("supplierCode", model.getSupplierCode());
				if(oldSupplier!=null){
					result.setMessage("该供应商编号已存在");
					return result;
				}
			}
			oldModel.setSupplierCode(model.getSupplierCode());
			oldModel.setSupplierName(model.getSupplierName());
			oldModel.setContact(model.getContact());
			oldModel.setAddr(model.getAddr());
			oldModel.setPhone(model.getPhone());
			oldModel.setFax(model.getFax());
			oldModel.setEmail(model.getEmail());
			oldModel.setNote1(model.getNote1());
			oldModel.setNote2(model.getNote2());
			oldModel.setNote3(model.getNote3());
			
			supplierDAO.update(oldModel);
		}
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.SupplierService#delete(org.linys.model.Supplier)
	 */
	@Override
	public ServiceResult delete(Supplier model) {
		ServiceResult result = new ServiceResult(false);
		if(model==null||StringUtils.isEmpty(model.getSupplierId())){
			result.setMessage("请选择要删除的供应商");
			return result;
		}
		Supplier oldModel = supplierDAO.load(model.getSupplierId());
		if(oldModel==null){
			result.setMessage("该供应商已不存在");
			return result;
		}else{
			supplierDAO.delete(oldModel);
		}
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.SupplierService#query(org.linys.model.Supplier, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public ServiceResult query(Supplier model, Integer page, Integer rows) {
		ServiceResult result = new ServiceResult(false);
		
		List<Supplier> list = supplierDAO.query(model,page,rows);
		
		String[] properties = {"supplierId","supplierCode","supplierName","contact","addr",
					"phone","fax","email","note1","note2","note3"};
		String data = JSONUtil.toJson(list,properties);
		result.addData("datagridData", data);
		
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.SupplierService#getTotalCount(org.linys.model.Supplier)
	 */
	@Override
	public ServiceResult getTotalCount(Supplier model) {
		ServiceResult result = new ServiceResult(false);
		Long data = supplierDAO.getTotalCount(model);
		result.addData("total", data);
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.SupplierService#queryCombogrid(java.lang.Integer, java.lang.Integer, org.linys.model.Supplier)
	 */
	@Override
	public String queryCombogrid(Integer page, Integer rows, Supplier model) {
		List<Supplier> list = supplierDAO.query(model,page,rows);
		Long total = supplierDAO.getTotalCount(model);
		String[] properties = {"supplierId","supplierCode","supplierName"};
		String ajaxString = JSONUtil.toJson(list,properties,total);
		return ajaxString;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.SupplierService#upload(java.io.File, java.lang.String)
	 */
	@Override
	public ServiceResult upload(File file, String templatePath,String fileName) throws Exception {
		ServiceResult result = new ServiceResult(false);
		result = ExcelPOIUtil.uploadValidate(file,templatePath,"供应商",fileName);
		if(!result.getIsSuccess()){
			return result;
		}
		InputStream is = new FileInputStream(file);
		HSSFWorkbook workBook = new HSSFWorkbook(is);
		HSSFSheet sheet = workBook.getSheetAt(0);
		int rows = sheet.getPhysicalNumberOfRows(); // 获得行数
		for (int j = 1; j < rows; j++) { // 行循环
			HSSFRow row = sheet.getRow(j);
			if (row != null) {
				//取得对应的列数据
				String supplierCode  = ExcelPOIUtil.getCellValue(row.getCell(0));
				String supplierName  = ExcelPOIUtil.getCellValue(row.getCell(1));
				String contact  = ExcelPOIUtil.getCellValue(row.getCell(2));
				String email  = ExcelPOIUtil.getCellValue(row.getCell(3));
				String addr  = ExcelPOIUtil.getCellValue(row.getCell(4));
				String phone  = ExcelPOIUtil.getCellValue(row.getCell(5));
				String fax  = ExcelPOIUtil.getCellValue(row.getCell(6));
				String note1  = ExcelPOIUtil.getCellValue(row.getCell(7));
				String note2  = ExcelPOIUtil.getCellValue(row.getCell(8));
				String note3  = ExcelPOIUtil.getCellValue(row.getCell(9));
				//检查供应商编号是否已存在，存在则放弃本次的上传
				Supplier oldSupplier = supplierDAO.load("supplierCode", supplierCode);
				if(oldSupplier!=null){
					is.close();
					throw new RuntimeException("供应商编号"+supplierCode+"已存在");
				}else{
					Supplier supplier = new Supplier();
					supplier.setSupplierCode(supplierCode);
					supplier.setSupplierName(supplierName);
					supplier.setContact(contact);
					supplier.setEmail(email);
					supplier.setAddr(addr);
					supplier.setPhone(phone);
					supplier.setFax(fax);
					supplier.setNote1(note1);
					supplier.setNote2(note2);
					supplier.setNote3(note3);
					supplierDAO.save(supplier);
				}
			}
		}
		is.close();
		result.setIsSuccess(true);
		return result;
	}

}
