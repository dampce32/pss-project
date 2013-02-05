DROP PROCEDURE IF EXISTS `P_PackagingDetail`;
DELIMITER ;;
CREATE  PROCEDURE `P_PackagingDetail`(
	IN packagingId varchar(32)
)
BEGIN
	select 
		a.qty,
		g.qty totalQty,
		a.price,
		a.price*a.qty*g.qty totalAmount,
		b.productCode,
		b.productName,
		c.warehouseName,
		d.dataDictionaryName unitName,
		e.dataDictionaryName colorName,
		f.dataDictionaryName sizeName
	from(select *
		from t_packagingdetail a
		where a.packagingId = packagingId)a
	left join t_product b on a.productId = b.productId
	left join t_warehouse c on a.warehouseId = c.warehouseId
	left join t_datadictionary d on b.unitId = d.dataDictionaryId
  left join t_datadictionary e on b.sizeId = e.dataDictionaryId
  left join t_datadictionary f on b.colorId = f.dataDictionaryId
	left join t_packaging g on a.packagingId = g.packagingId;
END
;;
DELIMITER ;

/*
select *
from t_packaging

CALL P_PackagingDetail('402880bb3ca7f528013ca9324fae001a')

*/


