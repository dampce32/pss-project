if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('T_Product') and o.name = 'FK_T_PRODUC_REFERENCE_T_PRODUC')
alter table T_Product
   drop constraint FK_T_PRODUC_REFERENCE_T_PRODUC
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('T_ProductType') and o.name = 'FK_T_PRODUC_REFERENCE_T_PRODUC')
alter table T_ProductType
   drop constraint FK_T_PRODUC_REFERENCE_T_PRODUC
go

if exists (select 1
            from  sysobjects
           where  id = object_id('T_ProductType')
            and   type = 'U')
   drop table T_ProductType
go

/*==============================================================*/
/* Table: T_ProductType                                         */
/*==============================================================*/
create table T_ProductType (
   productTypeID        varchar(32)          not null,
   parentProductTypeID  varchar(32)          null,
   productTypeName      varchar(50)          null,
   productTypeCode      varchar(50)          null,
   constraint PK_T_PRODUCTTYPE primary key (productTypeID)
)
go

alter table T_ProductType
   add constraint FK_T_PRODUC_REFERENCE_T_PRODUC foreign key (parentProductTypeID)
      references T_ProductType (productTypeID)
go
