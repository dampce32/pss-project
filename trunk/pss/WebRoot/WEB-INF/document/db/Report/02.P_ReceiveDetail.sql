DROP PROCEDURE IF EXISTS `P_ReceiveDetail`;
DELIMITER ;;
CREATE  PROCEDURE `P_ReceiveDetail`(in receiveId varchar(32))
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
	from t_Receivedetail a
  left join t_product b on a.productId = b.productId
  left join t_datadictionary c on a.colorId = c.dataDictionaryId
  left join t_datadictionary d on b.sizeId = d.dataDictionaryId
  left join t_datadictionary e on b.unitId = e.dataDictionaryId
  where a.receiveId = receiveId;
END
;;
DELIMITER ;




/*
call P_ReceiveDetail('402880bb3c8542f0013c857b66580007')
*/