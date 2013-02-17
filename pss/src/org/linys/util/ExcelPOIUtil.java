package org.linys.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.linys.vo.ServiceResult;
/**
 * @Description:利用POI的Excel工具类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-2-16
 * @author lys
 * @vesion 1.0
 */
public class ExcelPOIUtil {
	/**
	 * @Description: 读取单元格中的值
	 * @Create: 2013-2-16 下午3:08:12
	 * @author lys
	 * @update logs
	 * @param cell
	 * @return
	 */
	public static String getCellValue(HSSFCell cell) {
		String value = null;
		if (cell != null) {
			switch (cell.getCellType()) {
			case HSSFCell.CELL_TYPE_NUMERIC: // 数值型
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					// 如果是date类型则 ，获取该cell的date值
					value = HSSFDateUtil
							.getJavaDate(cell.getNumericCellValue()).toString();
				} else {// 纯数字
					value = String.valueOf(cell.getNumericCellValue());
				}
				break;
			/* 此行表示单元格的内容为string类型 */
			case HSSFCell.CELL_TYPE_STRING: // 字符串型
				value = cell.getRichStringCellValue().toString();
				break;
			case HSSFCell.CELL_TYPE_FORMULA:// 公式型
				// 读公式计算值
				value = String.valueOf(cell.getNumericCellValue());
				if (value.equals("NaN")) {// 如果获取的数据值为非法值,则转换为获取字符串
					value = cell.getRichStringCellValue().toString();
				}
				break;
			case HSSFCell.CELL_TYPE_BOOLEAN:// 布尔
				value = " " + cell.getBooleanCellValue();
				break;
			/* 此行表示该单元格值为空 */
			case HSSFCell.CELL_TYPE_BLANK: // 空值
				value = "";
				break;
			case HSSFCell.CELL_TYPE_ERROR: // 故障
				value = "";
				break;
			default:
				value = cell.getRichStringCellValue().toString();
			}
		}
		return value;
	}
	/**
	 * @Description: Excel文件上传验证
	 * @Create: 2013-2-17 上午10:33:24
	 * @author lys
	 * @update logs
	 * @param file
	 * @param templatePath
	 * @param templateFileName
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static ServiceResult uploadValidate(File file,String templatePath,String templateFileName,String fileName) throws Exception {
		ServiceResult result = new ServiceResult(false);
		InputStream is = null;
		HSSFWorkbook workBook = null;
		InputStream isTemplate = null;
		HSSFWorkbook workBookTemplate = null;
		if(file==null){
			result.setMessage("请上传文件");
			return result;
		}
		if(!"xls".equals(FilenameUtils.getExtension(fileName))){
			result.setMessage("请上传1997~2003Excel");
			return result;
		}
		is = new FileInputStream(file);
		workBook = new HSSFWorkbook(is);
		//检查Excel
		int noSheet = workBook.getNumberOfSheets();//工作薄个数
		if(noSheet<=0){
			result.setMessage("请检查上传的Excel文件，没有工作薄");
			is.close();
			return result;
		}
		// 创建工作表
		HSSFSheet sheet = workBook.getSheetAt(0);
		int rows = sheet.getPhysicalNumberOfRows(); // 获得行数
		if(rows<=1){
			result.setMessage("请上传有数据的Excel文件");
			is.close();
			return result;
		}
		//检查表格头
		isTemplate = new FileInputStream(templatePath+File.separator+templateFileName+".xls");
		workBookTemplate = new HSSFWorkbook(isTemplate);
		//取得报表头
		HSSFSheet sheetTemplate = workBookTemplate.getSheetAt(0);
		int rowsTemplate = sheetTemplate.getPhysicalNumberOfRows(); // 获得行数
		if(rowsTemplate<=0){
			isTemplate.close();
			is.close();
			result.setMessage(templateFileName+"模板不存在表格头");
			return result;
		}
		HSSFRow rowTemplate = sheetTemplate.getRow(0);
		HSSFRow row = sheet.getRow(0);
		if(row.getLastCellNum()!=rowTemplate.getLastCellNum()){
			result.setMessage(templateFileName+"模板文件的列数是"+rowTemplate.getLastCellNum()+",而上传文件的列数是"+row.getLastCellNum());
			isTemplate.close();
			is.close();
			return result;
		}
		for (int i = 0; i < rowTemplate.getLastCellNum(); i++) {
			if(!ExcelPOIUtil.getCellValue(rowTemplate.getCell(i)).equals(ExcelPOIUtil.getCellValue(row.getCell(i)))){
				result.setMessage(templateFileName+"模板文件第"+(i+1)+"列是"+ExcelPOIUtil.getCellValue(rowTemplate.getCell(i))+",而上传文件第"+(i+1)+"列是"+ExcelPOIUtil.getCellValue(row.getCell(i)));
				isTemplate.close();
				is.close();
				return result;
			}
		}
		result.setIsSuccess(true);
		return result;
	}
}
