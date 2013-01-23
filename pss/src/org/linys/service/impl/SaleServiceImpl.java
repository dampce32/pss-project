package org.linys.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.linys.bean.Pager;
import org.linys.dao.CommonDAO;
import org.linys.dao.PrefixDAO;
import org.linys.dao.SaleDao;
import org.linys.dao.SaleDetailDao;
import org.linys.model.Bank;
import org.linys.model.DataDictionary;
import org.linys.model.Product;
import org.linys.model.Sale;
import org.linys.model.SaleDetail;
import org.linys.service.SaleService;
import org.linys.util.JSONUtil;
import org.linys.util.StringUtil;
import org.linys.vo.GobelConstants;
import org.linys.vo.ServiceResult;
import org.springframework.stereotype.Service;

/**
 * 
 * 
 * @Description: 订单service
 * @Copyright: 福州骏华科技信息有限公司 (c)2013
 * @Created Date : 2013-1-22
 * @author longweier
 * @vesion 1.0
 */
@Service
public class SaleServiceImpl extends BaseServiceImpl<Sale, String> implements SaleService {

	@Resource
	private SaleDao saleDao;
	@Resource
	private SaleDetailDao saleDetailDao;
	@Resource
	private CommonDAO commonDAO;
	@Resource
	private PrefixDAO prefixDAO; 
	
	public ServiceResult addSale(Sale sale, String productIds, String colorIds,String qtys, 
								String prices, String discounts, String note1s,String note2s, String note3s) {
		ServiceResult result = new ServiceResult(false);
		if(sale.getCustomer()==null || StringUtils.isEmpty(sale.getCustomer().getCustomerId())){
			result.setMessage("客户不能为空");
			return result;
		}
		if(sale.getSaleDate()==null){
			result.setMessage("订单日期不能为空");
			return result;
		}
		if(sale.getDeliverDate()==null){
			result.setMessage("订单交期不能为空");
			return result;
		}
		if(sale.getEmployee()==null||StringUtils.isEmpty(sale.getEmployee().getEmployeeId())){
			result.setMessage("请选择经办人");
			return result;
		}
		if(StringUtils.isEmpty(productIds)){
			result.setMessage("请选择商品");
			return result;
		}
		String[] productIdArray = StringUtil.split(productIds, GobelConstants.SPLIT_SEPARATOR);
		String[] colorIdArray = StringUtil.split(colorIds, GobelConstants.SPLIT_SEPARATOR);
		String[] qtyArray = StringUtil.split(qtys, GobelConstants.SPLIT_SEPARATOR);
		String[] priceArray = StringUtil.split(prices, GobelConstants.SPLIT_SEPARATOR);
		String[] discountArray = StringUtil.split(discounts, GobelConstants.SPLIT_SEPARATOR);
		String[] note1Array = StringUtil.split(note1s, GobelConstants.SPLIT_SEPARATOR);
		String[] note2Array = StringUtil.split(note2s, GobelConstants.SPLIT_SEPARATOR);
		String[] note3Array = StringUtil.split(note3s, GobelConstants.SPLIT_SEPARATOR);
		
		for (int i = 0; i < qtyArray.length; i++) {
			String qty = qtyArray[i];
			if("0".equals(qty) ||"".equals(qty)){
				result.setMessage("第"+(i+1)+"行商品数量不能为0");
				return result;
			}
			
		}
		//取得订单编号
		sale.setSaleCode(commonDAO.getCode(Sale.class.getName(), "saleCode", prefixDAO.getPrefix("sale")));
		
		sale.setStatus(0);
		saleDao.save(sale);
		
		for (int i=0;i<productIdArray.length;i++) {
			String productId = productIdArray[i];
			String colorId = colorIdArray[i];
			String qty = qtyArray[i];
			String price = priceArray[i];
			String discount = discountArray[i];
			String note1 = note1Array[i];
			String note2 = note2Array[i];
			String note3 = note3Array[i];
			
			if(StringUtils.isEmpty(price)){
				price ="0";
			}
			if(StringUtils.isEmpty(discount)){
				discount="1";
			}
			SaleDetail saleDetail = new SaleDetail();
			
			Product product = new Product();
			product.setProductId(productId);
			
			if(StringUtils.isNotEmpty(colorId)){
				DataDictionary color = new DataDictionary();
				color.setDataDictionaryId(colorId);
				saleDetail.setColor(color);
			}
			saleDetail.setProduct(product);
			saleDetail.setSale(sale);
			saleDetail.setQty(Double.parseDouble(qty));
			saleDetail.setPrice(Double.parseDouble(price));
			saleDetail.setDiscount(Double.parseDouble(discount));
			saleDetail.setNote1(note1);
			saleDetail.setNote2(note2);
			saleDetail.setNote3(note3);
			saleDetail.setHadSaleQty(0d);
			
			saleDetailDao.save(saleDetail);
		}
		result.setIsSuccess(true);
		result.addData("saleId", sale.getSaleId());
		return result;
	}
	
	public ServiceResult deleteSale(String saleId) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(saleId)){
			result.setMessage("参数错误");
			return result;
		}
		Sale sale = saleDao.load(saleId);
		if(sale==null){
			result.setMessage("该订单已被删除");
			return result;
		}
		if(sale.getStatus().intValue()==1){
			result.setMessage("该订单已审核,不能删除");
			return result;
		}
		saleDao.delete(sale);
		result.setIsSuccess(true);
		return result;
	}

	public ServiceResult mulDeleteSale(String ids) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(ids)){
			result.setMessage("参数错误");
			return result;
		}
		String[] idArray = StringUtil.split(ids,GobelConstants.SPLIT_SEPARATOR);
		for(String saleId : idArray){
			Sale sale = saleDao.load(saleId);
			if(sale==null || sale.getStatus()==1) continue;
			saleDao.delete(sale);
		}
		result.setIsSuccess(true);
		return result;
	}

	public ServiceResult updateStatus(Sale sale) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(sale.getSaleId()) || sale.getStatus()==null){
			result.setMessage("参数错误");
			return result;
		}
		Sale model = saleDao.load(sale.getSaleId());
		if(model.getStatus().intValue()!=sale.getStatus().intValue()){
			Bank bank = model.getBank();
			if(bank!=null){
				if(sale.getStatus().intValue()==1){//如果是由未审改为已审
					bank.setAmount(bank.getAmount()+model.getReceiptedAmount());
				}else{//如果是由已审改为未审
					bank.setAmount(bank.getAmount()-model.getReceiptedAmount());
				}
			}
			model.setStatus(sale.getStatus());
		}
		result.setIsSuccess(true);
		return result;
	}
	public ServiceResult mulUpdateStatus(String ids, Integer status) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(ids) || status==null){
			result.setMessage("参数错误");
			return result;
		}
		String[] idArray = StringUtil.split(ids,GobelConstants.SPLIT_SEPARATOR);
		for(String saleId : idArray){
			Sale model = saleDao.load(saleId);
			if(model.getStatus().intValue()!=status.intValue()){
				Bank bank = model.getBank();
				if(bank!=null){
					if(status==1){//如果是由未审改为已审
						bank.setAmount(bank.getAmount()+model.getReceiptedAmount());
					}else{//如果是由已审改为未审
						bank.setAmount(bank.getAmount()-model.getReceiptedAmount());
					}
				}
				model.setStatus(status);
			}
		}
		result.setIsSuccess(true);
		return result;
	}

	public String querySale(Integer pageNumber,Integer pageSize,Sale sale,Date beginDate,Date endDate) {
		try {
			Pager pager = new Pager(pageNumber, pageSize);
			pager = saleDao.querySale(pager, sale, beginDate, endDate);
			
			String[] properties = {"saleId","saleCode","saleDate","sourceCode","deliverDate",
					"customer.customerName","employee.employeeName","amount","otherAmount",
					"receiptedAmount","note","status"}; 
			String jsonArray = JSONUtil.toJson(pager.getList(), properties, pager.getTotalCount());
			return jsonArray;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ServiceResult updateSale(Sale sale, String saleDetailIds,String delSaleDetailIds, String productIds, 
			String colorIds,String qtys, String prices, String discounts, String note1s,String note2s, String note3s) {
		ServiceResult result = new ServiceResult(false);
		if(sale.getCustomer()==null || StringUtils.isEmpty(sale.getCustomer().getCustomerId())){
			result.setMessage("客户不能为空");
			return result;
		}
		if(sale.getSaleDate()==null){
			result.setMessage("订单日期不能为空");
			return result;
		}
		if(sale.getDeliverDate()==null){
			result.setMessage("订单交期不能为空");
			return result;
		}
		if(sale.getEmployee()==null||StringUtils.isEmpty(sale.getEmployee().getEmployeeId())){
			result.setMessage("请选择经办人");
			return result;
		}
		if(StringUtils.isEmpty(productIds)){
			result.setMessage("请选择商品");
			return result;
		}
		String[] saleDetailIdArray =  StringUtil.split(saleDetailIds, GobelConstants.SPLIT_SEPARATOR);
		String[] delSaleDetailIdArray =  StringUtil.split(delSaleDetailIds, GobelConstants.SPLIT_SEPARATOR);
		String[] productIdArray = StringUtil.split(productIds, GobelConstants.SPLIT_SEPARATOR);
		String[] colorIdArray = StringUtil.split(colorIds, GobelConstants.SPLIT_SEPARATOR);
		String[] qtyArray = StringUtil.split(qtys, GobelConstants.SPLIT_SEPARATOR);
		String[] priceArray = StringUtil.split(prices, GobelConstants.SPLIT_SEPARATOR);
		String[] discountArray = StringUtil.split(discounts, GobelConstants.SPLIT_SEPARATOR);
		String[] note1Array = StringUtil.split(note1s, GobelConstants.SPLIT_SEPARATOR);
		String[] note2Array = StringUtil.split(note2s, GobelConstants.SPLIT_SEPARATOR);
		String[] note3Array = StringUtil.split(note3s, GobelConstants.SPLIT_SEPARATOR);
		
		for (int i = 0; i < qtyArray.length; i++) {
			String qty = qtyArray[i];
			if("0".equals(qty) ||"".equals(qty)){
				result.setMessage("第"+(i+1)+"行商品数量不能为0");
				return result;
			}
		}
		Sale model = saleDao.load(sale.getSaleId());
		sale.setStatus(model.getStatus());
		saleDao.evict(model);
		saleDao.update(sale);
		//删除
		for(String id : delSaleDetailIdArray){
			if(StringUtils.isEmpty(id)) continue;
			saleDetailDao.delete(id);
		}
		//新增、修改
		for (int i=0;i<productIdArray.length;i++) {
			String saleDetailId = saleDetailIdArray[i];
			String productId = productIdArray[i];
			String colorId = colorIdArray[i];
			String qty = qtyArray[i];
			String price = priceArray[i];
			String discount = discountArray[i];
			String note1 = note1Array[i];
			String note2 = note2Array[i];
			String note3 = note3Array[i];
			
			if(StringUtils.isEmpty(price)){
				price ="0";
			}
			if(StringUtils.isEmpty(discount)){
				discount="1";
			}
			Product product = new Product();
			product.setProductId(productId);
			DataDictionary color = new DataDictionary();
			color.setDataDictionaryId(colorId);
			
			if(StringUtils.isEmpty(saleDetailId)){//新增
				SaleDetail saleDetail = new SaleDetail();
				
				saleDetail.setProduct(product);
				saleDetail.setSale(sale);
				if(StringUtils.isNotEmpty(colorId)){
					saleDetail.setColor(color);
				}
				saleDetail.setQty(Double.parseDouble(qty));
				saleDetail.setPrice(Double.parseDouble(price));
				saleDetail.setDiscount(Double.parseDouble(discount));
				saleDetail.setNote1(note1);
				saleDetail.setNote2(note2);
				saleDetail.setNote3(note3);
				saleDetail.setHadSaleQty(0d);
				
				saleDetailDao.save(saleDetail);
				
			}else{//修改
				SaleDetail saleDetail = saleDetailDao.load(saleDetailId);
				
				if(saleDetail==null) continue;
				if(StringUtils.isNotEmpty(colorId)){
					saleDetail.setColor(color);
				}else{
					saleDetail.setColor(null);
				}
				saleDetail.setQty(Double.parseDouble(qty));
				saleDetail.setPrice(Double.parseDouble(price));
				saleDetail.setDiscount(Double.parseDouble(discount));
				saleDetail.setNote1(note1);
				saleDetail.setNote2(note2);
				saleDetail.setNote3(note3);
			}
		}
		result.setIsSuccess(true);
		result.addData("saleId", sale.getSaleId());
		return result;
	}

	public String initSale(String saleId) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(saleId)){
			return result.toJSON();
		}
		Sale sale = saleDao.getSale(saleId);
		String[] propertiesSale = {"saleId","saleCode","saleDate","sourceCode","deliverDate",
				"customer.customerId","customer.customerName","amount","receiptedAmount","otherAmount",
				"bank.bankId","employee.employeeId","note","status"};
		String buyData = JSONUtil.toJson(sale,propertiesSale);
		result.addData("saleData",buyData);
		
		List<SaleDetail> saleDetailList = saleDetailDao.querySaleDetail(sale);
		String[] propertiesDetail = {"saleDetailId","product.productId","product.productCode","product.productName",
				"product.size.dataDictionaryName:sizeName","product.unit.dataDictionaryName:unitName","color.dataDictionaryName:colorName","color.dataDictionaryId:colorId",
				"qty","price","discount","amount","note1","note2","note3","hadSaleQty"};
		String detailData = JSONUtil.toJson(saleDetailList,propertiesDetail);
		result.addData("detailData", detailData);
		result.setIsSuccess(true);
		return result.toJSON();
	}
}
