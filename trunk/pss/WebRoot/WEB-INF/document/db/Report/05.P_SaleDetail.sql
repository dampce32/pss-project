DROP PROCEDURE IF EXISTS `P_SaleDetail`;
DELIMITER ;;
CREATE  PROCEDURE `P_SaleDetail`(in saleId varchar(32))
BEGIN
	select 
		b.productCode,
		b.productName,
		e.dataDictionaryName unitName,
		c.dataDictionaryName colorName,
		d.dataDictionaryName sizeName,
    a.qty,
		a.price,
		a.discount,
		a.qty*a.price*a.discount amount,
		a.note1 
	from t_Saledetail a
  left join t_product b on a.productId = b.productId
  left join t_datadictionary c on a.colorId = c.dataDictionaryId
  left join t_datadictionary d on b.sizeId = d.dataDictionaryId
  left join t_datadictionary e on b.unitId = e.dataDictionaryId
  where a.saleId = saleId;
END
;;
DELIMITER ;

/*

select *
from  t_sale

call P_SaleDetail ('402880bb3ca7f528013ca831a6a80009')

*/

