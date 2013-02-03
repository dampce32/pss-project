DROP PROCEDURE IF EXISTS `P_ReceiptCheckDetail`;
DELIMITER ;;
CREATE  PROCEDURE `P_ReceiptCheckDetail`(
	IN txtBeginDate varchar(20),
	IN txtEndDate varchar(20),
	IN customerId varchar(32),
	IN warehouseId varchar(32)
)
BEGIN
	select 
		b.deliverDate,
		b.deliverCode,
		c.productCode,
		c.productName,
		d.dataDictionaryName unitName,
		e.dataDictionaryName sizeName,
		sum(a.qty) qty,
    sum(a.qty*a.price) amount
	from t_deliverdetail a
  left join t_deliver b on a.deliverId = b.deliverId
  left join t_product c on a.productId = c.productId
  left join t_datadictionary d on c.unitId = d.dataDictionaryId
  left join t_datadictionary e on c.sizeId = e.dataDictionaryId
  where b.deliverDate between txtBeginDate and txtEndDate
		and b.customerId = customerId and b.warehouseId = warehouseId
    and b.`status` = 1
  group by
		b.deliverDate,
		b.deliverCode,
		c.productCode,
		c.productName,
		d.dataDictionaryName,
		e.dataDictionaryName;
END
;;
DELIMITER ;

/*
CALL P_ReceiptCheckDetail('2012-1-1','2014-1-1','402880bb3c85143a013c852448ea001e','402880bb3c85143a013c852342d80015')

*/
