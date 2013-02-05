DROP PROCEDURE IF EXISTS `P_SplitParams`;
DELIMITER ;;
CREATE  PROCEDURE `P_SplitParams`(
	IN splitId varchar(32)
)
BEGIN
	select 
		a.splitCode,
		a.splitDate,
		a.price,
		a.qty,
		a.price*a.qty amount,
		b.productCode,
		b.productName,
		c.warehouseName,
		d.dataDictionaryName colorName,
		e.dataDictionaryName sizeName,
		f.dataDictionaryName unitName,
		g.companyName,
		a.note
	from(select *
		from t_split a
		where a.splitId = splitId)a
	left join t_product b on a.productId = b.productId 
  left join t_warehouse c on a.warehouseId = c.warehouseId
  left join t_datadictionary d on b.colorId = d.dataDictionaryId
	left join t_datadictionary e on b.sizeId = e.dataDictionaryId
  left join t_datadictionary f on b.unitId = f.dataDictionaryId
  left join(select *
		from t_systemconfig) g on 1 = 1;
 
END
;;
DELIMITER ;

/*
select *
from t_Split

CALL P_SplitParams('402880bb3ca975a4013ca97664d10001')

*/


