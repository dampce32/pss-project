use pss
--≤È—Øµ±«∞ø‚¥Ê
select b.productId,b.productCode,b.productName,f.productTypeName,
c.dataDictionaryId colorId,c.dataDictionaryName colorName,d.dataDictionaryId sizeId,d.dataDictionaryName sizeName,e.dataDictionaryId unitId,e.dataDictionaryName unitName,
SUM(a.qty) qty,SUM(a.amount) amount
from T_Store a
left join T_Product b on a.productId = b.productId
left join T_DataDictionary c on b.colorId = c.dataDictionaryId
left join T_DataDictionary d on b.sizeId = d.dataDictionaryId
left join T_DataDictionary e on b.unitId = e.dataDictionaryId
left join T_ProductType f on b.productTypeID = f.productTypeID
group by 
b.productId,b.productCode,b.productName,f.productTypeName,
c.dataDictionaryId,c.dataDictionaryName,d.dataDictionaryId,d.dataDictionaryName,e.dataDictionaryId,e.dataDictionaryName

