DROP PROCEDURE IF EXISTS `P_SaleParams`;
DELIMITER ;;
CREATE  PROCEDURE `P_SaleParams`(in saleId varchar(32))
BEGIN
	select 
			a.saleCode,
			a.saleDate,
			a.sourceCode,
			a.deliverDate,
			a.note,
			b.customerName,
			c.companyName
	from t_sale a
	left join t_customer b on a.customerId = b.customerId
	left join (select a.companyName
			from t_systemConfig a) c on 1 = 1
	where a.saleId = saleId;
END
;;
DELIMITER ;

/*

select *
from  t_sale

call P_SaleParams ('402880bb3ca7f528013ca831a6a80009')

*/

