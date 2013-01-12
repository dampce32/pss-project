package org.linys.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.linys.dao.BankDAO;
import org.linys.dao.ProductDAO;
import org.linys.dao.ReceiveDAO;
import org.linys.dao.ReceiveDetailDAO;
import org.linys.dao.StoreDAO;
import org.linys.model.Bank;
import org.linys.model.DataDictionary;
import org.linys.model.Product;
import org.linys.model.Receive;
import org.linys.model.ReceiveDetail;
import org.linys.model.Store;
import org.linys.service.ReceiveService;
import org.linys.util.DateUtils;
import org.linys.util.JSONUtil;
import org.linys.util.StringUtil;
import org.linys.vo.GobelConstants;
import org.linys.vo.ServiceResult;
import org.springframework.stereotype.Service;

@Service
public class ReceiveServiceImpl extends BaseServiceImpl<Receive, String>
		implements ReceiveService {
	@Resource
	private ReceiveDAO receiveDAO;
	@Resource
	private ReceiveDetailDAO receiveDetailDAO;
	@Resource
	private StoreDAO storeDAO;
	@Resource
	private ProductDAO productDAO;
	@Resource
	private BankDAO bankDAO;
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.ReceiveService#query(org.linys.model.Receive, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public ServiceResult query(String kind,Receive model, Integer page, Integer rows) {
		ServiceResult result = new ServiceResult(false);
		
		List<Receive> list = receiveDAO.query(kind,model,page,rows);
		
		String[] properties = {"receiveId","receiveCode","receiveDate","deliverCode",
				"warehouse.warehouseName","supplier.supplierName","amount","payAmount","discountAmount",
				"employee.employeeName","note","shzt","invoiceType.invoiceTypeName"};
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
	public ServiceResult getTotalCount(String kind,Receive model) {
		ServiceResult result = new ServiceResult(false);
		Long data = receiveDAO.getTotalCount(kind,model);
		result.addData("total", data);
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.ReceiveService#save(org.linys.model.Receive, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public ServiceResult save(String kind,Receive model, String receiveDetailIds,
			String delReceiveDetailIds, String productIds, String colorIds,
			String qtys, String prices, String note1s, String note2s,
			String note3s) {
		ServiceResult result = new ServiceResult(false);
		if(model==null){
			result.setMessage("请填写收货单信息");
			return result;
		}
		
		if(!"other".equals(kind)){
			if(model.getSupplier()==null||StringUtils.isEmpty(model.getSupplier().getSupplierId())){
				result.setMessage("请选择供应商");
				return result;
			}
			if(model.getBank()==null||StringUtils.isEmpty(model.getBank().getBankId())){
				result.setMessage("请选择银行");
				return result;
			}
			if(model.getInvoiceType()==null||StringUtils.isEmpty(model.getInvoiceType().getInvoiceTypeId())){
				result.setMessage("请选择发票");
				return result;
			}
		}
		
		if(model.getWarehouse()==null||StringUtils.isEmpty(model.getWarehouse().getWarehouseId())){
			result.setMessage("请选择仓库");
			return result;
		}
		if(model.getReceiveDate()==null){
			result.setMessage("请选择收货日期");
			return result;
		}
		if(model.getEmployee()==null||StringUtils.isEmpty(model.getEmployee().getEmployeeId())){
			result.setMessage("请选择经办人");
			return result;
		}
		if(StringUtils.isEmpty(productIds)){
			result.setMessage("请选择商品");
			return result;
		}
		String[] productIdArray = StringUtil.split(productIds, GobelConstants.SPLIT_SEPARATOR);
		String[] delReceiveDetailIdArray = StringUtil.split(delReceiveDetailIds, GobelConstants.SPLIT_SEPARATOR);
		String[] receiveDetailIdArray = StringUtil.split(receiveDetailIds, GobelConstants.SPLIT_SEPARATOR);
		String[] colorIdArray = StringUtil.split(colorIds, GobelConstants.SPLIT_SEPARATOR);
		String[] qtyArray = StringUtil.split(qtys, GobelConstants.SPLIT_SEPARATOR);
		String[] priceArray = StringUtil.split(prices, GobelConstants.SPLIT_SEPARATOR);
		String[] note1Array = StringUtil.split(note1s, GobelConstants.SPLIT_SEPARATOR);
		String[] note2Array = StringUtil.split(note2s, GobelConstants.SPLIT_SEPARATOR);
		String[] note3Array = StringUtil.split(note3s, GobelConstants.SPLIT_SEPARATOR);
		if(productIdArray==null||productIdArray.length==0){
			result.setMessage("请选择商品");
			return result;
		}
		for (int i = 0; i < qtyArray.length; i++) {
			String qty = qtyArray[i];
			if("0".equals(qty)){
				result.setMessage("第"+(i+1)+"行商品数量不能为0");
				return result;
			}
			
		}
		if(StringUtils.isEmpty(model.getReceiveId())){//新增
			//取得入库单号
			String receiveCode = null;
			String prefix = null;
			if(!"other".equals(kind)){
				prefix = "CJ";
			}else{
				prefix = "QR";
			}
			receiveCode = prefix + DateUtils.dateToString(new Date(),"yyyyMMdd");
			receiveCode = receiveDAO.getMaxCode(receiveCode);
			
			receiveCode = newReceiveCode(prefix,receiveCode);
			model.setShzt(0);
			model.setReceiveCode(receiveCode);
			receiveDAO.save(model);
			for (int i = 0; i < productIdArray.length; i++) {
				String productId = productIdArray[i];
				String colorId = colorIdArray[i];
				String qty = qtyArray[i];
				String price = priceArray[i];
				String note1 = note1Array[i];
				String note2 = note2Array[i];
				String note3 = note3Array[i];
				
				Product product = new Product();
				product.setProductId(productId);
				DataDictionary color = new DataDictionary();
				color.setDataDictionaryId(colorId);
				
				ReceiveDetail receiveDetail = new ReceiveDetail();
				receiveDetail.setReceive(model);
				receiveDetail.setProduct(product);
				receiveDetail.setColor(color);
				receiveDetail.setQty(new Double(qty));
				receiveDetail.setPrice(new Double(price));
				receiveDetail.setNote1(note1);
				receiveDetail.setNote2(note2);
				receiveDetail.setNote3(note3);
				receiveDetailDAO.save(receiveDetail);
			}
		}else{
			//更新收货单
			Receive oldReceive = receiveDAO.load(model.getReceiveId());
			if(oldReceive==null){
				result.setMessage("要更新的收货单已不存在");
				return result;
			}else{
				oldReceive.setDeliverCode(model.getDeliverCode());
				oldReceive.setSupplier(model.getSupplier());
				oldReceive.setWarehouse(model.getWarehouse());
				oldReceive.setReceiveDate(model.getReceiveDate());
				oldReceive.setDiscountAmount(model.getDiscountAmount());
				oldReceive.setPayAmount(model.getPayAmount());
				oldReceive.setAmount(model.getAmount());
				oldReceive.setBank(model.getBank());
				oldReceive.setInvoiceType(model.getInvoiceType());
				oldReceive.setEmployee(model.getEmployee());
				oldReceive.setNote(model.getNote());
				receiveDAO.update(oldReceive);
			}
			
			//删除已删的收货单明细
			if(!"".equals(delReceiveDetailIds)){
				for (String delReceiveDetailId : delReceiveDetailIdArray) {
					ReceiveDetail oldModel = receiveDetailDAO.load(delReceiveDetailId);
					if(oldModel!=null){
						receiveDetailDAO.delete(oldModel);
					}
				}
			}
			//根据收货单明细Id更新或新增
			for (int i = 0 ;i<receiveDetailIdArray.length;i++) {
				String receiveDetailId = receiveDetailIdArray[i];
				String productId = productIdArray[i];
				String colorId = colorIdArray[i];
				String qty = qtyArray[i];
				String price = priceArray[i];
				String note1 = note1Array[i];
				String note2 = note2Array[i];
				String note3 = note3Array[i];
				if(StringUtils.isEmpty(receiveDetailId)){//新增
						Product product = new Product();
						product.setProductId(productId);
						DataDictionary color = new DataDictionary();
						color.setDataDictionaryId(colorId);
						
						ReceiveDetail receiveDetail = new ReceiveDetail();
						receiveDetail.setReceive(model);
						receiveDetail.setProduct(product);
						receiveDetail.setColor(color);
						receiveDetail.setQty(new Double(qty));
						receiveDetail.setPrice(new Double(price));
						receiveDetail.setNote1(note1);
						receiveDetail.setNote2(note2);
						receiveDetail.setNote3(note3);
						receiveDetailDAO.save(receiveDetail);
				}else{
					ReceiveDetail oldModel = receiveDetailDAO.load(receiveDetailId);
					
					DataDictionary color = new DataDictionary();
					color.setDataDictionaryId(colorId);
					
					oldModel.setColor(color);
					oldModel.setQty(new Double(qty));
					oldModel.setPrice(new Double(price));
					oldModel.setNote1(note1);
					oldModel.setNote2(note2);
					oldModel.setNote3(note3);
					receiveDetailDAO.update(oldModel);
				}
			}
		}
		//返回值
		result.addData("receiveId", model.getReceiveId());
		result.setIsSuccess(true);
		return result;
	}
	/**
	 * @Description: 生成新的收货单编号
	 * @Create: 2013-1-7 下午10:24:00
	 * @author lys
	 * @update logs
	 * @param receiveCode
	 * @return
	 */
	private String newReceiveCode(String prefix,String receiveCode) {
		int index = 0;
		if(receiveCode!=null){
			index = Integer.parseInt(receiveCode.substring(10, receiveCode.length()));	
		}
		return prefix+DateUtils.dateToString(new Date(),"yyyyMMdd")+String.format("%04d", index+1);
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.ReceiveService#init(java.lang.String)
	 */
	@Override
	public ServiceResult init(String receiveId) {
		ServiceResult result = new ServiceResult(false);
		Receive receive = receiveDAO.load(receiveId);
		String[] propertiesReceive = {"receiveId","receiveCode","receiveDate","deliverCode",
				"supplier.supplierId","warehouse.warehouseId","amount","discountAmount","payAmount",
				"bank.bankId","invoiceType.invoiceTypeId","employee.employeeId","note"};
		String receiveData = JSONUtil.toJson(receive,propertiesReceive);
		result.addData("receiveData",receiveData);
		
		List<ReceiveDetail> receiveDetailList = receiveDetailDAO.queryByReceiveId(receiveId);
		String[] propertiesDetail = {"receiveDetailId","product.productId","product.productCode","product.productName",
				"product.size.dataDictionaryName:sizeName","product.unit.dataDictionaryName:unitName","color.dataDictionaryName:colorName","color.dataDictionaryId:colorId",
				"qty","price","amount","note1","note2","note3"};
		String detailData = JSONUtil.toJson(receiveDetailList,propertiesDetail);
		result.addData("detailData", detailData);
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.ReceiveService#mulDel(java.lang.String)
	 */
	@Override
	public ServiceResult mulDel(String ids) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(ids)){
			result.setMessage("请选择要删除的收货单");
			return result;
		}
		String[] idArray =StringUtil.split(ids,GobelConstants.SPLIT_SEPARATOR);
		if(idArray.length==0){
			result.setMessage("请选择要删除的收货单");
			return result;
		}
		boolean haveDel = false;
		for (String id : idArray) {
			Receive oldReceive = receiveDAO.load(id);
			if(oldReceive!=null&&oldReceive.getShzt()==0){
				receiveDAO.delete(oldReceive);
				haveDel = true;
			}
		}
		if(!haveDel){
			result.setMessage("没有可删除的收货单");
			return result;
		}
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.ReceiveService#mulUpdateShzt(java.lang.String, org.linys.model.Receive)
	 */
	@Override
	public ServiceResult mulUpdateShzt(String kind,String ids, Receive model) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(ids)){
			result.setMessage("请选择要修改审核状态的收货单");
			return result;
		}
		String[] idArray =StringUtil.split(ids,GobelConstants.SPLIT_SEPARATOR);
		if(idArray.length==0){
			result.setMessage("请选择要修改审核状态的收货单");
			return result;
		}
		if(model==null||model.getShzt()==null){
			result.setMessage("请选择要修改成的审核状态");
			return result;
		}
		boolean haveUpdateShzt = false;
		for (String id : idArray) {
			Receive oldReceive = receiveDAO.load(id);
			if(oldReceive!=null&&oldReceive.getShzt()!=model.getShzt()){
				if(model.getShzt()==1){//如果是由未审改为已审
					//将该收货单下的商品入库
					List<ReceiveDetail> receiveDetailList = receiveDetailDAO.queryByReceiveId(id);
					//将对应商品入库
					for (ReceiveDetail receiveDetail : receiveDetailList) {
						/*
						 * 查找商品在仓库中是否已有记录，如果有则修改库存数量和库存金额，	
						 * 如果没有，则修改新增库存记录
						*/
						String[] propertyNames = {"warehouse.warehouseId","product.productId"};
						Object[] values = {oldReceive.getWarehouse().getWarehouseId(),receiveDetail.getProduct().getProductId()};
						Store store = storeDAO.load(propertyNames, values);
						if(store==null){
							store = new Store();
							store.setWarehouse(oldReceive.getWarehouse());
							store.setProduct(receiveDetail.getProduct());
							store.setQty(0.0);
							store.setAmount(0.0);
							storeDAO.save(store);
						}
						store.setQty(store.getQty()+receiveDetail.getQty());
						storeDAO.update(store);
						//更新商品总的库存数量和金额
						Product oldProduct = productDAO.load(receiveDetail.getProduct().getProductId());
						oldProduct.setQtyStore(oldProduct.getQtyStore()+receiveDetail.getQty());
						oldProduct.setAmountStore(oldProduct.getAmountStore()+receiveDetail.getAmount());
						productDAO.update(oldProduct);
					}
				}else if(model.getShzt()==0){//如果是由已审改为未审
					//将该收货单下的商品入库
					List<ReceiveDetail> receiveDetailList = receiveDetailDAO.queryByReceiveId(id);
					//更新对应商品的库存数量
					for (ReceiveDetail receiveDetail : receiveDetailList) {
						String[] propertyNames = {"warehouse.warehouseId","product.productId"};
						Object[] values = {oldReceive.getWarehouse().getWarehouseId(),receiveDetail.getProduct().getProductId()};
						Store store = storeDAO.load(propertyNames, values);
						store.setQty(store.getQty()-receiveDetail.getQty());
						storeDAO.update(store);
						//更新商品总的库存数量和金额
						Product oldProduct = productDAO.load(receiveDetail.getProduct().getProductId());
						oldProduct.setQtyStore(oldProduct.getQtyStore()-receiveDetail.getQty());
						oldProduct.setAmountStore(oldProduct.getAmountStore()-receiveDetail.getAmount());
						productDAO.update(oldProduct);
					}
				}
				if(!"other".equals(kind)){
					Bank oldBank = bankDAO.load(oldReceive.getBank().getBankId());
					//更新对应银行的账户金额
					if(model.getShzt()==1){//如果是由未审改为已审
						oldBank.setAmount(oldBank.getAmount()-oldReceive.getPayAmount());
					}else if(model.getShzt()==0){//如果是由已审改为未审
						oldBank.setAmount(oldBank.getAmount()+oldReceive.getPayAmount());
					}
					bankDAO.update(oldBank);
				}
				oldReceive.setShzt(model.getShzt());
				receiveDAO.update(oldReceive);
				haveUpdateShzt = true;
			}
		}
		if(!haveUpdateShzt){
			result.setMessage("没有可修改审核状态的收货单");
			return result;
		}
		result.setIsSuccess(true);
		return result;
	}
}
