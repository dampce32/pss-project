if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('T_BuyDetail') and o.name = 'FK_T_BUYDET_REFERENCE_T_PRODUC')
alter table T_BuyDetail
   drop constraint FK_T_BUYDET_REFERENCE_T_PRODUC
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('T_BuyDetail') and o.name = 'FK_T_BUYDET_REFERENCE_T_DATADI')
alter table T_BuyDetail
   drop constraint FK_T_BUYDET_REFERENCE_T_DATADI
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('T_BuyDetail') and o.name = 'FK_T_BUYDET_REFERENCE_T_BUY')
alter table T_BuyDetail
   drop constraint FK_T_BUYDET_REFERENCE_T_BUY
go

if exists (select 1
            from  sysobjects
           where  id = object_id('T_BuyDetail')
            and   type = 'U')
   drop table T_BuyDetail
go

/*==============================================================*/
/* Table: T_BuyDetail                                           */
/*==============================================================*/
create table T_BuyDetail (
   buyDetailId          varchar(32)          not null,
   buyId                varchar(32)          null,
   productId            varchar(32)          null,
   colorId              varchar(32)          null,
   qty                  float                null,
   price                float                null,
   note1                varchar(50)          null,
   note2                varchar(50)          null,
   note3                varchar(50)          null,
   constraint PK_T_BUYDETAIL primary key (buyDetailId)
)
go

alter table T_BuyDetail
   add constraint FK_T_BUYDET_REFERENCE_T_PRODUC foreign key (productId)
      references T_Product (productId)
go

alter table T_BuyDetail
   add constraint FK_T_BUYDET_REFERENCE_T_DATADI foreign key (colorId)
      references T_DataDictionary (dataDictionaryId)
go

alter table T_BuyDetail
   add constraint FK_T_BUYDET_REFERENCE_T_BUY foreign key (buyId)
      references T_Buy (buyId)
go
