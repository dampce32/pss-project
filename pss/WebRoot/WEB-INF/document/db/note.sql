--查询可用于"从采购单添加商品"功能的采购单
select b.buyId,b.buyCode,b.buyDate
from(select distinct b.buyId
	from(select *
		from T_Buy a
		where a.buyDate between '2013-01-13' and '2013-01-14' and a.status = 1 and a.supplierId = '402881e53c29e068013c2a0f8e2a000e')a
	left join T_BuyDetail b on a.buyId = b.buyId
	where b.qty - b.receiveQty > 0 and b.buyDetailId not in('')) a
left join T_Buy b on a.buyId = b.buyId
order by b.buyDate,b.buyCode

--将选择的采购单中的采购明细，添加到收货单明细中
select a.buyId,b.buyCode,a.buyDetailId,a.productId,c.productCode,c.productName,
d.dataDictionaryName unitName,e.dataDictionaryName sizeName,a.colorId,f.dataDictionaryName colorName,
a.qty-a.receiveQty qty,a.price
from T_BuyDetail a
left join T_Buy b on a.buyId = b.buyId
left join T_Product c on a.productId = c.productId
left join T_DataDictionary d on c.unitId  = d.dataDictionaryId
left join T_DataDictionary e on c.sizeId = e.dataDictionaryId
left join T_DataDictionary f on c.colorId = f.dataDictionaryId
where a.buyId in ('402881e53c319ed6013c31adc3020001','402881e53c395e97013c397d3cf30001')
and a.buyDetailId not in('') and a.qty - a.receiveQty >  0 
