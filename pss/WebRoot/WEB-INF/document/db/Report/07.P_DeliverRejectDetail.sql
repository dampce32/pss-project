DROP PROCEDURE IF EXISTS `P_DeliverRejectDetail`;
DELIMITER ;;
CREATE  PROCEDURE `P_DeliverRejectDetail`(in deliverRejectId varchar(32))
BEGIN
	select 
		b.productCode,
		b.productName,
		e.dataDictionaryName unitName,
		c.dataDictionaryName colorName,
		d.dataDictionaryName sizeName,
    a.qty,
		a.price,
		a.qty*a.price amount,
		a.note1 
	from t_DeliverRejectdetail a
  left join t_product b on a.productId = b.productId
  left join t_datadictionary c on a.colorId = c.dataDictionaryId
  left join t_datadictionary d on b.sizeId = d.dataDictionaryId
  left join t_datadictionary e on b.unitId = e.dataDictionaryId
  where a.deliverRejectId = deliverRejectId;
END
;;
DELIMITER ;

/*
call P_DeliverRejectDetail('402880bb3ca7f528013ca91f5fa30017')
*/