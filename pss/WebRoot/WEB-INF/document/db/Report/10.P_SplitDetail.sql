DROP PROCEDURE IF EXISTS `P_SplitDetail`;
DELIMITER ;;
CREATE  PROCEDURE `P_SplitDetail`(
	IN splitId varchar(32)
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
		from t_splitdetail a
		where a.SplitId = SplitId)a
	left join t_product b on a.productId = b.productId
	left join t_warehouse c on a.warehouseId = c.warehouseId
	left join t_datadictionary d on b.unitId = d.dataDictionaryId
  left join t_datadictionary e on b.sizeId = e.dataDictionaryId
  left join t_datadictionary f on b.colorId = f.dataDictionaryId
	left join t_split g on a.splitId = g.splitId;
END
;;
DELIMITER ;

/*
select *
from t_Split

CALL P_SplitDetail('402880bb3ca975a4013ca97664d10001')

*/


