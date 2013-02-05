DROP PROCEDURE IF EXISTS `P_DeliverDetail`;
DELIMITER ;;
CREATE  PROCEDURE `P_DeliverDetail`(in deliverId varchar(32))
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
	from t_deliverdetail a
  left join t_product b on a.productId = b.productId
  left join t_datadictionary c on a.colorId = c.dataDictionaryId
  left join t_datadictionary d on b.sizeId = d.dataDictionaryId
  left join t_datadictionary e on b.unitId = e.dataDictionaryId
  where a.deliverId = deliverId;
END
;;
DELIMITER ;

/*
select *
from t_deliver


call P_DeliverDetail('402880bb3ca7f528013ca8472a81000c')
*/