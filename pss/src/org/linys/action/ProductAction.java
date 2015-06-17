package org.linys.action;

import java.io.File;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.linys.model.Product;
import org.linys.service.ProductService;
import org.linys.util.FileUtil;
import org.linys.vo.ServiceResult;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
/**
 * @Description:商品Action
 * @Copyright: 福州骏华信息有限公司 (c)2012
 * @Created Date : 2012-12-23
 * @author lys
 * @vesion 1.0
 */
@Controller
@Scope("prototype")
public class ProductAction extends BaseAction implements ModelDriven<Product> {
	
	private static final long serialVersionUID = -7463394380104260815L;
	private static final Logger logger = Logger.getLogger(ProductAction.class);
	@Resource
	private ProductService productService;
	Product model = new Product();
	public Product getModel() {
		return model;
	}
	/**
	 * @Description: 保存商品
	 * @Create: 2012-12-23 下午5:42:17
	 * @author lys
	 * @update logs
	 */
	public void save(){
		ServiceResult result = new ServiceResult(false);
		try {
			String defaultPackagingIds = getParameter("defaultPackagingIds");
			String deleleIds = getParameter("deleleIds");
			String productIds = getParameter("productIds");
			String qtys = getParameter("qtys");
			
			String productPriceRangeIdsWholesalePrice = getParameter("productPriceRangeIdsWholesalePrice");
			String deleleIdsWholesalePrice = getParameter("deleleIdsWholesalePrice");
			String pricesWholesalePrice = getParameter("pricesWholesalePrice");
			String qtyBeginsWholesalePrice = getParameter("qtyBeginsWholesalePrice");
			String qtyEndsWholesalePrice = getParameter("qtyEndsWholesalePrice");
			
			String productPriceRangeIdsVipPrice = getParameter("productPriceRangeIdsVipPrice");
			String deleleIdsVipPrice = getParameter("deleleIdsVipPrice");
			String pricesVipPrice = getParameter("pricesVipPrice");
			String qtyBeginsVipPrice = getParameter("qtyBeginsVipPrice");
			String qtyEndsVipPrice = getParameter("qtyEndsVipPrice");
			
			String productPriceRangeIdsMemberPrice = getParameter("productPriceRangeIdsMemberPrice");
			String deleleIdsMemberPrice = getParameter("deleleIdsMemberPrice");
			String pricesMemberPrice = getParameter("pricesMemberPrice");
			String qtyBeginsMemberPrice = getParameter("qtyBeginsMemberPrice");
			String qtyEndsMemberPrice = getParameter("qtyEndsMemberPrice");
			
			String productPriceRangeIdsSalePrice = getParameter("productPriceRangeIdsSalePrice");
			String deleleIdsSalePrice = getParameter("deleleIdsSalePrice");
			String pricesSalePrice = getParameter("pricesSalePrice");
			String qtyBeginsSalePrice = getParameter("qtyBeginsSalePrice");
			String qtyEndsSalePrice = getParameter("qtyEndsSalePrice");
			
			result = productService.save(model,defaultPackagingIds,deleleIds,productIds,qtys,
					productPriceRangeIdsWholesalePrice,deleleIdsWholesalePrice,pricesWholesalePrice,qtyBeginsWholesalePrice,qtyEndsWholesalePrice,
					productPriceRangeIdsVipPrice,deleleIdsVipPrice,pricesVipPrice,qtyBeginsVipPrice,qtyEndsVipPrice,
					productPriceRangeIdsMemberPrice,deleleIdsMemberPrice,pricesMemberPrice,qtyBeginsMemberPrice,qtyEndsMemberPrice,
					productPriceRangeIdsSalePrice,deleleIdsSalePrice,pricesSalePrice,qtyBeginsSalePrice,qtyEndsSalePrice
					);
		} catch (Exception e) {
			result.setMessage("保存商品失败");
			logger.error("保存商品失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description: 打开初始化
	 * @Create: 2013-2-3 下午12:00:55
	 * @author lys
	 * @update logs
	 */
	public void init(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = productService.init(model.getProductId());
		} catch (Exception e) {
			result.setMessage("打开初始化失败");
			result.setIsSuccess(false);
			logger.error("打开初始化失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	
	/**
	 * @Description: 删除商品
	 * @Create: 2012-12-18 下午10:56:44
	 * @author lys
	 * @update logs
	 */
	public void delete(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = productService.delete(model);
		} catch (Exception e) {
			if(e instanceof org.springframework.dao.DataIntegrityViolationException){
				result.setMessage("该商品已被使用，不能删除");
			}else{
				result.setMessage("删除商品失败");
				logger.error("删除商品失败", e);
			}
			result.setIsSuccess(false);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description:分组查询 商品 
	 * @Create: 2012-12-18 下午10:58:02
	 * @author lys
	 * @update logs
	 */
	public void query(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = productService.query(model,page,rows);
		} catch (Exception e) {
			result.setMessage("查询商品失败");
			result.setIsSuccess(false);
			logger.error("查询商品失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description: 统计 商品
	 * @Create: 2012-12-18 下午10:59:26
	 * @author lys
	 * @update logs
	 */
	public void getTotalCount(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = productService.getTotalCount(model);
		} catch (Exception e) {
			result.setMessage("统计商品失败");
			result.setIsSuccess(false);
			logger.error("统计商品失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description: 选择商品
	 * @Create: 2012-12-30 上午10:24:19
	 * @author lys
	 * @update logs
	 */
	public void select(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = productService.select(model,page,rows);
		} catch (Exception e) {
			result.setMessage("选择商品失败");
			result.setIsSuccess(false);
			logger.error("选择商品失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description: 退货选择商品
	 * @Create: 2013-1-11 下午12:48:15
	 * @author lys
	 * @update logs
	 */
	public void selectReject(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = productService.selectReject(model,page,rows);
		} catch (Exception e) {
			result.setMessage("退货选择商品失败");
			result.setIsSuccess(false);
			logger.error("退货选择商品失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description: 查询当前库存的商品
	 * @Create: 2013-1-12 下午1:44:38
	 * @author lys
	 * @update logs
	 */
	public void queryStore(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = productService.queryStore(model,page,rows);
		} catch (Exception e) {
			result.setMessage("查询当前库存的商品失败");
			result.setIsSuccess(false);
			logger.error("查询当前库存的商品失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	
	/**
	 * @Description: 默认商品组装，选择商品
	 * @Create: 2013-2-3 上午10:22:37
	 * @author lys
	 * @update logs
	 */
	public void selectDefaultPacking(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = productService.selectDefaultPacking(model,ids,page,rows);
		} catch (Exception e) {
			result.setMessage("默认商品组装，选择商品失败");
			logger.error("默认商品组装，选择商品失败", e);
			result.setIsSuccess(false);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	
	/**
	 * 
	 * @Description: 查询某个商品的默认保证
	 * @Create: 2013-2-4 下午02:19:41
	 * @author longweier
	 * @update logs
	 * @throws Exception
	 */
	public void queryDefaultPacking(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = productService.selectDefaultPacking(model);
		} catch (Exception e) {
			result.setMessage("默认商品组装，选择商品失败");
			logger.error("默认商品组装，选择商品失败", e);
			result.setIsSuccess(false);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description: 取得新编号
	 * @Created Time: 2013-2-20 上午11:28:46
	 * @Author lys
	 */
	public void newCode(){
		ServiceResult result = new ServiceResult(false);
		try {
			String productCode = request.getParameter("productCode");
			result = productService.newCode(productCode);
		} catch (Exception e) {
			result.setMessage("取得新编号失败");
			logger.error("取得新编号失败", e);
			result.setIsSuccess(false);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description: 上传图片文件
	 * @Created Time: 2013-2-20 下午4:29:47
	 * @Author lys
	 */
	public void uploadImg(){
		ServiceResult result = new ServiceResult(false);
		try {
			String productId = getParameter("productId");
			String fileName = getParameter("fileName");
			result = productService.uploadImg(file,getBasePath(),productId,fileName);
		} catch (Exception e) {
			result.setMessage("上传图片文件失败");
			logger.error("上传图片文件失败", e);
			result.setIsSuccess(false);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description: 下载图片
	 * @Created Time: 2013-2-21 上午9:44:42
	 * @Author lys
	 */
	public void showImg(){
		String productId = getParameter("productId");
		String rootPath = getBasePath()+"productImg";
		try {
			String fileName =File.separator+productId+".png"; 
			FileUtil.downloadFile(getResponse(), rootPath, fileName);
		} catch (Exception e) {
			logger.error("下载文件失败", e);
			e.printStackTrace();
		}
	}
	/**
	 * @Description: 删除商品图片
	 * @Created Time: 2013-2-21 下午2:06:50
	 * @Author lys
	 */
	public void deleteImg(){
		ServiceResult result = new ServiceResult(false);
		String productId = getParameter("productId");
		try {
			String filePath =getBasePath()+"productImg"+File.separator+productId+".png"; 
			FileUtil.deleteFile(filePath);
			result.setIsSuccess(true);
		} catch (Exception e) {
			logger.error("删除商品图片失败", e);
			result.setMessage("删除商品图片失败");
			result.setIsSuccess(false);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
}
