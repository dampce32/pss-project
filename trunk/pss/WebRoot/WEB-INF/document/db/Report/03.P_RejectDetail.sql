DROP PROCEDURE IF EXISTS `P_RejectDetail`;
DELIMITER ;;
CREATE  PROCEDURE `P_RejectDetail`(in rejectId varchar(32))
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
	from t_rejectdetail a
  left join t_product b on a.productId = b.productId
  left join t_datadictionary c on a.colorId = c.dataDictionaryId
  left join t_datadictionary d on b.sizeId = d.dataDictionaryId
  left join t_datadictionary e on b.unitId = e.dataDictionaryId
  where a.rejectId = rejectId;
END
;;
DELIMITER ;




/*
call P_RejectDetail('402880bb3c8a3478013c8a39fa9e000b')
*/