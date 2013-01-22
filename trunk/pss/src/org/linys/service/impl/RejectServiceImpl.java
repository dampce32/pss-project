package org.linys.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.linys.dao.BankDAO;
import org.linys.dao.CommonDAO;
import org.linys.dao.PrefixDAO;
import org.linys.dao.RejectDAO;
import org.linys.dao.RejectDetailDAO;
import org.linys.dao.StoreDAO;
import org.linys.model.Bank;
import org.linys.model.DataDictionary;
import org.linys.model.Product;
import org.linys.model.Reject;
import org.linys.model.RejectDetail;
import org.linys.model.Store;
import org.linys.service.RejectService;
import org.linys.util.JSONUtil;
import org.linys.util.StringUtil;
import org.linys.vo.GobelConstants;
import org.linys.vo.ServiceResult;
import org.springframework.stereotype.Service;
@Service
public class RejectServiceImpl extends BaseServiceImpl<Reject, String>
		implements RejectService {
	@Resource
	private RejectDAO rejectDAO;
	@Resource
	private RejectDetailDAO rejectDetailDAO;
	@Resource
	private StoreDAO storeDAO;
	@Resource
	private BankDAO bankDAO;
	@Resource
	private PrefixDAO prefixDAO;
	@Resource
	private CommonDAO commonDAO;
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.RejectService#query(org.linys.model.Reject, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public ServiceResult query(Reject model, Integer page, Integer rows) {
		ServiceResult result = new ServiceResult(false);
		
		List<Reject> list = rejectDAO.query(model,page,rows);
		
		String[] properties = {"rejectId","rejectCode","rejectDate","buyCode",
				"warehouse.warehouseName","supplier.supplierName","amount","payAmount",
				"employee.employeeName","note","status"};
		String data = JSONUtil.toJson(list,properties);
		result.addData("datagridData", data);
		
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.RejectService#getTotalCount(org.linys.model.Reject)
	 */
	@Override
	public ServiceResult getTotalCount(Reject model) {
		ServiceResult result = new ServiceResult(false);
		Long data = rejectDAO.getTotalCount(model);
		result.addData("total", data);
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.RejectService#save(org.linys.model.Reject, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public ServiceResult save(Reject model, String rejectDetailIds,
			String delRejectDetailIds, String productIds, String colorIds,
			String qtys, String prices, String note1s, String note2s,
			String note3s) {
		ServiceResult result = new ServiceResult(false);
		if(model==null){
			result.setMessage("请填写退货单信息");
			return result;
		}
		
		if(model.getSupplier()==null||StringUtils.isEmpty(model.getSupplier().getSupplierId())){
			result.setMessage("请选择供应商");
			return result;
		}
		
		if(model.getWarehouse()==null||StringUtils.isEmpty(model.getWarehouse().getWarehouseId())){
			result.setMessage("请选择仓库");
			return result;
		}
		if(model.getRejectDate()==null){
			result.setMessage("请选择退货日期");
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
		String[] delRejectDetailIdArray = StringUtil.split(delRejectDetailIds, GobelConstants.SPLIT_SEPARATOR);
		String[] rejectDetailIdArray = StringUtil.split(rejectDetailIds, GobelConstants.SPLIT_SEPARATOR);
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
		if(StringUtils.isEmpty(model.getRejectId())){//新增
			//取得入库单号
			
			model.setStatus(0);
			model.setRejectCode(commonDAO.getCode("Reject", "rejectCode",prefixDAO.getPrefix("reject")));
			rejectDAO.save(model);
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
				
				RejectDetail rejectDetail = new RejectDetail();
				rejectDetail.setReject(model);
				rejectDetail.setProduct(product);
				rejectDetail.setColor(color);
				rejectDetail.setQty(new Double(qty));
				rejectDetail.setPrice(new Double(price));
				rejectDetail.setNote1(note1);
				rejectDetail.setNote2(note2);
				rejectDetail.setNote3(note3);
				rejectDetailDAO.save(rejectDetail);
			}
		}else{
			//更新退货单
			Reject oldReject = rejectDAO.load(model.getRejectId());
			if(oldReject==null){
				result.setMessage("要更新的退货单已不存在");
				return result;
			}else{
				oldReject.setBuyCode(model.getBuyCode());
				oldReject.setSupplier(model.getSupplier());
				oldReject.setWarehouse(model.getWarehouse());
				oldReject.setRejectDate(model.getRejectDate());
				oldReject.setPayAmount(model.getPayAmount());
				oldReject.setAmount(model.getAmount());
				oldReject.setEmployee(model.getEmployee());
				oldReject.setNote(model.getNote());
				rejectDAO.update(oldReject);
			}
			
			//删除已删的退货单明细
			if(!"".equals(delRejectDetailIds)){
				for (String delRejectDetailId : delRejectDetailIdArray) {
					RejectDetail oldModel = rejectDetailDAO.load(delRejectDetailId);
					if(oldModel!=null){
						rejectDetailDAO.delete(oldModel);
					}
				}
			}
			//根据退货单明细Id更新或新增
			for (int i = 0 ;i<rejectDetailIdArray.length;i++) {
				String rejectDetailId = rejectDetailIdArray[i];
				String productId = productIdArray[i];
				String colorId = colorIdArray[i];
				String qty = qtyArray[i];
				String price = priceArray[i];
				String note1 = note1Array[i];
				String note2 = note2Array[i];
				String note3 = note3Array[i];
				if(StringUtils.isEmpty(rejectDetailId)){//新增
						Product product = new Product();
						product.setProductId(productId);
						DataDictionary color = new DataDictionary();
						color.setDataDictionaryId(colorId);
						
						RejectDetail rejectDetail = new RejectDetail();
						rejectDetail.setReject(model);
						rejectDetail.setProduct(product);
						rejectDetail.setColor(color);
						rejectDetail.setQty(new Double(qty));
						rejectDetail.setPrice(new Double(price));
						rejectDetail.setNote1(note1);
						rejectDetail.setNote2(note2);
						rejectDetail.setNote3(note3);
						rejectDetailDAO.save(rejectDetail);
				}else{
					RejectDetail oldModel = rejectDetailDAO.load(rejectDetailId);
					
					DataDictionary color = new DataDictionary();
					color.setDataDictionaryId(colorId);
					
					oldModel.setColor(color);
					oldModel.setQty(new Double(qty));
					oldModel.setPrice(new Double(price));
					oldModel.setNote1(note1);
					oldModel.setNote2(note2);
					oldModel.setNote3(note3);
					rejectDetailDAO.update(oldModel);
				}
			}
		}
		//返回值
		result.addData("rejectId", model.getRejectId());
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.RejectService#init(java.lang.String)
	 */
	@Override
	public ServiceResult init(String rejectId) {
		ServiceResult result = new ServiceResult(false);
		Reject reject = rejectDAO.load(rejectId);
		String[] propertiesReject = {"rejectId","rejectCode","rejectDate","buyCode",
				"supplier.supplierId","warehouse.warehouseId","amount","payAmount",
				"bank.bankId","employee.employeeId","note","status"};
		String rejectData = JSONUtil.toJson(reject,propertiesReject);
		result.addData("rejectData",rejectData);
		
		List<RejectDetail> rejectDetailList = rejectDetailDAO.queryByRejectId(rejectId);
		String[] propertiesDetail = {"rejectDetailId","product.productId","product.productCode","product.productName",
				"product.size.dataDictionaryName:sizeName","product.unit.dataDictionaryName:unitName","color.dataDictionaryName:colorName","color.dataDictionaryId:colorId",
				"qty","price","amount","note1","note2","note3"};
		String detailData = JSONUtil.toJson(rejectDetailList,propertiesDetail);
		result.addData("detailData", detailData);
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.RejectService#mulDel(java.lang.String)
	 */
	@Override
	public ServiceResult mulDelete(String ids) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(ids)){
			result.setMessage("请选择要删除的退货单");
			return result;
		}
		String[] idArray =StringUtil.split(ids,GobelConstants.SPLIT_SEPARATOR);
		if(idArray.length==0){
			result.setMessage("请选择要删除的退货单");
			return result;
		}
		boolean haveDel = false;
		for (String id : idArray) {
			Reject oldReject = rejectDAO.load(id);
			if(oldReject!=null&&oldReject.getStatus()==0){
				rejectDAO.delete(oldReject);
				haveDel = true;
			}
		}
		if(!haveDel){
			result.setMessage("没有可删除的退货单");
			return result;
		}
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.RejectService#mulUpdateStatus(java.lang.String, org.linys.model.Reject)
	 */
	@Override
	public ServiceResult mulUpdateStatus(String ids, Reject model) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(ids)){
			result.setMessage("请选择要修改审核状态的退货单");
			return result;
		}
		String[] idArray =StringUtil.split(ids,GobelConstants.SPLIT_SEPARATOR);
		if(idArray.length==0){
			result.setMessage("请选择要修改审核状态的退货单");
			return result;
		}
		if(model==null||model.getStatus()==null){
			result.setMessage("请选择要修改成的审核状态");
			return result;
		}
		boolean haveUpdateStatus = false;
		for (String id : idArray) {
			Reject oldReject = rejectDAO.load(id);
			if(oldReject!=null&&oldReject.getStatus().intValue()!=model.getStatus().intValue()){
				if(model.getStatus()==1){//如果是由未审改为已审
					//将该退货单下的商品入库
					List<RejectDetail> rejectDetailList = rejectDetailDAO.queryByRejectId(id);
					//更新对应商品的库存数量
					for (RejectDetail rejectDetail : rejectDetailList) {
						String[] propertyNames = {"warehouse.warehouseId","product.productId"};
						Object[] values = {oldReject.getWarehouse().getWarehouseId(),rejectDetail.getProduct().getProductId()};
						Store store = storeDAO.load(propertyNames, values);
						store.setQty(store.getQty()-rejectDetail.getQty());
						store.setAmount(store.getAmount()-rejectDetail.getAmount());
						storeDAO.update(store);
					}
				}else if(model.getStatus()==0){//如果是由已审改为未审
					//将该退货单下的商品入库
					List<RejectDetail> rejectDetailList = rejectDetailDAO.queryByRejectId(id);
					//更新对应商品的库存数量
					for (RejectDetail rejectDetail : rejectDetailList) {
						String[] propertyNames = {"warehouse.warehouseId","product.productId"};
						Object[] values = {oldReject.getWarehouse().getWarehouseId(),rejectDetail.getProduct().getProductId()};
						Store store = storeDAO.load(propertyNames, values);
						store.setQty(store.getQty()+rejectDetail.getQty());
						store.setAmount(store.getAmount()+rejectDetail.getAmount());
						storeDAO.update(store);
					}
				}
				
				Bank oldBank = bankDAO.load(oldReject.getBank().getBankId());
				//更新对应银行的账户金额
				if(model.getStatus()==1){//如果是由未审改为已审
					oldBank.setAmount(oldBank.getAmount()+oldReject.getPayAmount());
				}else if(model.getStatus()==0){//如果是由已审改为未审
					oldBank.setAmount(oldBank.getAmount()-oldReject.getPayAmount());
				}
				bankDAO.update(oldBank);
				oldReject.setStatus(model.getStatus());
				rejectDAO.update(oldReject);
				haveUpdateStatus = true;
			}
		}
		if(!haveUpdateStatus){
			result.setMessage("没有可修改审核状态的退货单");
			return result;
		}
		result.setIsSuccess(true);
		return result;
	}
	
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.RejectService#delete(org.linys.model.Reject)
	 */
	@Override
	public ServiceResult delete(Reject model) {
		ServiceResult result = new ServiceResult(false);
		if(model==null||StringUtils.isEmpty(model.getRejectId())){
			result.setMessage("请选择要删除的退货单");
			return result;
		}
		Reject oldReject = rejectDAO.load(model.getRejectId());
		
		if(oldReject==null){
			result.setMessage("要删除的退货单已不存在");
			return result;
		}
		rejectDAO.delete(oldReject);
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.RejectService#updateStatus(org.linys.model.Reject)
	 */
	@Override
	public ServiceResult updateStatus(Reject model) {
		
		ServiceResult result = new ServiceResult(false);
		if(model==null||StringUtils.isEmpty(model.getRejectId())){
			result.setMessage("请选择要修改状态的退货单");
			return result;
		}
		Reject oldReject = rejectDAO.load(model.getRejectId());
		
		if(oldReject==null){
			result.setMessage("要修改状态的退货单已不存在");
			return result;
		}
		if(oldReject.getStatus().intValue()==model.getStatus().intValue()){
			result.setMessage("要修改状态的退货单已是要修改的状态，请刷新界面");
			return result;
		}
		if(model.getStatus()==1){//如果是由未审改为已审
			//将该退货单下的商品入库
			List<RejectDetail> rejectDetailList = rejectDetailDAO.queryByRejectId(model.getRejectId());
			//更新对应商品的库存数量
			for (RejectDetail rejectDetail : rejectDetailList) {
				String[] propertyNames = {"warehouse.warehouseId","product.productId"};
				Object[] values = {oldReject.getWarehouse().getWarehouseId(),rejectDetail.getProduct().getProductId()};
				Store store = storeDAO.load(propertyNames, values);
				store.setQty(store.getQty()-rejectDetail.getQty());
				store.setAmount(store.getAmount()-rejectDetail.getAmount());
				storeDAO.update(store);
			}
		}else if(model.getStatus()==0){//如果是由已审改为未审
			//将该退货单下的商品入库
			List<RejectDetail> rejectDetailList = rejectDetailDAO.queryByRejectId(model.getRejectId());
			//更新对应商品的库存数量
			for (RejectDetail rejectDetail : rejectDetailList) {
				String[] propertyNames = {"warehouse.warehouseId","product.productId"};
				Object[] values = {oldReject.getWarehouse().getWarehouseId(),rejectDetail.getProduct().getProductId()};
				Store store = storeDAO.load(propertyNames, values);
				store.setQty(store.getQty()+rejectDetail.getQty());
				store.setAmount(store.getAmount()+rejectDetail.getAmount());
				storeDAO.update(store);
			}
		}
		
		Bank oldBank = bankDAO.load(oldReject.getBank().getBankId());
		//更新对应银行的账户金额
		if(model.getStatus()==1){//如果是由未审改为已审
			oldBank.setAmount(oldBank.getAmount()+oldReject.getPayAmount());
		}else if(model.getStatus()==0){//如果是由已审改为未审
			oldBank.setAmount(oldBank.getAmount()-oldReject.getPayAmount());
		}
		bankDAO.update(oldBank);
		oldReject.setStatus(model.getStatus());
		rejectDAO.update(oldReject);
		
		oldReject.setStatus(model.getStatus());
		rejectDAO.update(oldReject);
		result.setIsSuccess(true);
		return result;
	}

}
