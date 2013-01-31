DROP PROCEDURE IF EXISTS `P_PayCheckDetail`;
DELIMITER ;;
CREATE  PROCEDURE `P_PayCheckDetail`(
	IN txtBeginDate varchar(20),
	IN txtEndDate varchar(20),
	IN supplierId varchar(32),
	IN warehouseId varchar(32)
)
BEGIN
	select 
		b.receiveDate,
		b.receiveCode,
		c.productCode,
		c.productName,
		d.dataDictionaryName unitName,
		e.dataDictionaryName sizeName,
		sum(a.qty) qty,
    sum(a.qty*a.price) amount
	from t_receivedetail a
  left join t_receive b on a.receiveId = b.receiveId
  left join t_product c on a.productId = c.productId
  left join t_datadictionary d on c.unitId = d.dataDictionaryId
  left join t_datadictionary e on c.sizeId = e.dataDictionaryId
  where b.receiveDate between txtBeginDate and txtEndDate
		and b.supplierId = supplierId and b.warehouseId = warehouseId
  group by
		b.receiveDate,
		b.receiveCode,
		c.productCode,
		c.productName,
		d.dataDictionaryName,
		e.dataDictionaryName;
END
;;
DELIMITER ;

/*
CALL P_PayCheck('2012-10-1','2013-10-1','402880bb3c85143a013c8522deb90012','402880bb3c85143a013c852342d80015')
*/


