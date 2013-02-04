DROP PROCEDURE IF EXISTS `P_BuyDetail`;
DELIMITER ;;
CREATE  PROCEDURE `P_BuyDetail`(in buyId_in varchar(32))
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
	from t_buydetail a
  left join t_product b on a.productId = b.productId
  left join t_datadictionary c on a.colorId = c.dataDictionaryId
  left join t_datadictionary d on b.sizeId = d.dataDictionaryId
  left join t_datadictionary e on b.unitId = e.dataDictionaryId
  where buyId = buyId_in;
END
;;
DELIMITER ;