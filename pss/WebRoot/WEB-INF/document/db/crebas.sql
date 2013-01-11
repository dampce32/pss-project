/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2008                    */
/* Created on:     2013-01-11 21:34:46                          */
/*==============================================================*/


if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('T_Employee') and o.name = 'FK_T_EMPLOY_REFERENCE_T_PAY')
alter table T_Employee
   drop constraint FK_T_EMPLOY_REFERENCE_T_PAY
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('T_Pay') and o.name = 'FK_T_PAY_REFERENCE_T_RECEIV')
alter table T_Pay
   drop constraint FK_T_PAY_REFERENCE_T_RECEIV
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('T_Pay') and o.name = 'FK_T_PAY_REFERENCE_T_BANK')
alter table T_Pay
   drop constraint FK_T_PAY_REFERENCE_T_BANK
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('T_Product') and o.name = 'FK_T_PRODUC_REFERENCE_T_Size')
alter table T_Product
   drop constraint FK_T_PRODUC_REFERENCE_T_Size
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('T_Product') and o.name = 'FK_T_PRODUC_REFERENCE_PRODUCTT')
alter table T_Product
   drop constraint FK_T_PRODUC_REFERENCE_PRODUCTT
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('T_Product') and o.name = 'FK_T_PRODUC_REFERENCE_T_Unit')
alter table T_Product
   drop constraint FK_T_PRODUC_REFERENCE_T_Unit
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('T_Product') and o.name = 'FK_T_PRODUC_REFERENCE_T_Color')
alter table T_Product
   drop constraint FK_T_PRODUC_REFERENCE_T_Color
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('T_ProductType') and o.name = 'FK_PRODUCTT_REFERENCE_PRODUCTT')
alter table T_ProductType
   drop constraint FK_PRODUCTT_REFERENCE_PRODUCTT
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('T_Receive') and o.name = 'FK_T_RECEIV_REFERENCE_T_SUPPLI')
alter table T_Receive
   drop constraint FK_T_RECEIV_REFERENCE_T_SUPPLI
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('T_Receive') and o.name = 'FK_T_RECEIV_REFERENCE_T_WAREHO')
alter table T_Receive
   drop constraint FK_T_RECEIV_REFERENCE_T_WAREHO
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('T_Receive') and o.name = 'FK_T_RECEIV_REFERENCE_T_EMPLOY')
alter table T_Receive
   drop constraint FK_T_RECEIV_REFERENCE_T_EMPLOY
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('T_Receive') and o.name = 'FK_T_RECEIV_REFERENCE_T_BANK')
alter table T_Receive
   drop constraint FK_T_RECEIV_REFERENCE_T_BANK
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('T_Receive') and o.name = 'FK_T_RECEIV_REFERENCE_T_INVOIC')
alter table T_Receive
   drop constraint FK_T_RECEIV_REFERENCE_T_INVOIC
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('T_ReceiveDetail') and o.name = 'FK_T_RECEIV_REFERENCE_T_RECEIV')
alter table T_ReceiveDetail
   drop constraint FK_T_RECEIV_REFERENCE_T_RECEIV
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('T_ReceiveDetail') and o.name = 'FK_T_RECEIV_REFERENCE_T_PRODUC')
alter table T_ReceiveDetail
   drop constraint FK_T_RECEIV_REFERENCE_T_PRODUC
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('T_ReceiveDetail') and o.name = 'FK_T_RECEIV_REFERENCE_T_DATADI')
alter table T_ReceiveDetail
   drop constraint FK_T_RECEIV_REFERENCE_T_DATADI
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('T_Reject') and o.name = 'FK_T_REJECT_REFERENCE_T_WAREHO')
alter table T_Reject
   drop constraint FK_T_REJECT_REFERENCE_T_WAREHO
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('T_Reject') and o.name = 'FK_T_REJECT_REFERENCE_T_EMPLOY')
alter table T_Reject
   drop constraint FK_T_REJECT_REFERENCE_T_EMPLOY
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('T_Reject') and o.name = 'FK_T_REJECT_REFERENCE_T_INVOIC')
alter table T_Reject
   drop constraint FK_T_REJECT_REFERENCE_T_INVOIC
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('T_Reject') and o.name = 'FK_T_REJECT_REFERENCE_T_SUPPLI')
alter table T_Reject
   drop constraint FK_T_REJECT_REFERENCE_T_SUPPLI
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('T_RejectDetail') and o.name = 'FK_T_REJECT_REFERENCE_T_PRODUC')
alter table T_RejectDetail
   drop constraint FK_T_REJECT_REFERENCE_T_PRODUC
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('T_RejectDetail') and o.name = 'FK_T_REJECT_REFERENCE_T_DATADI')
alter table T_RejectDetail
   drop constraint FK_T_REJECT_REFERENCE_T_DATADI
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('T_RejectDetail') and o.name = 'FK_T_REJECT_REFERENCE_T_REJECT')
alter table T_RejectDetail
   drop constraint FK_T_REJECT_REFERENCE_T_REJECT
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('T_Right') and o.name = 'FK_T_RIGHT_REFERENCE_T_RIGHT')
alter table T_Right
   drop constraint FK_T_RIGHT_REFERENCE_T_RIGHT
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('T_RoleRight') and o.name = 'FK_T_ROLERI_REFERENCE_T_RIGHT')
alter table T_RoleRight
   drop constraint FK_T_ROLERI_REFERENCE_T_RIGHT
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('T_RoleRight') and o.name = 'FK_T_ROLERI_REFERENCE_T_ROLE')
alter table T_RoleRight
   drop constraint FK_T_ROLERI_REFERENCE_T_ROLE
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('T_Store') and o.name = 'FK_T_STORE_REFERENCE_T_WAREHO')
alter table T_Store
   drop constraint FK_T_STORE_REFERENCE_T_WAREHO
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('T_Store') and o.name = 'FK_T_STORE_REFERENCE_T_PRODUC')
alter table T_Store
   drop constraint FK_T_STORE_REFERENCE_T_PRODUC
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('T_UserRole') and o.name = 'FK_T_USERRO_REFERENCE_T_USER')
alter table T_UserRole
   drop constraint FK_T_USERRO_REFERENCE_T_USER
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('T_UserRole') and o.name = 'FK_T_USERRO_REFERENCE_T_ROLE')
alter table T_UserRole
   drop constraint FK_T_USERRO_REFERENCE_T_ROLE
go

if exists (select 1
            from  sysobjects
           where  id = object_id('T_Bank')
            and   type = 'U')
   drop table T_Bank
go

if exists (select 1
            from  sysobjects
           where  id = object_id('T_DataDictionary')
            and   type = 'U')
   drop table T_DataDictionary
go

if exists (select 1
            from  sysobjects
           where  id = object_id('T_Employee')
            and   type = 'U')
   drop table T_Employee
go

if exists (select 1
            from  sysobjects
           where  id = object_id('T_InvoiceType')
            and   type = 'U')
   drop table T_InvoiceType
go

if exists (select 1
            from  sysobjects
           where  id = object_id('T_Pay')
            and   type = 'U')
   drop table T_Pay
go

if exists (select 1
            from  sysobjects
           where  id = object_id('T_Product')
            and   type = 'U')
   drop table T_Product
go

if exists (select 1
            from  sysobjects
           where  id = object_id('T_ProductType')
            and   type = 'U')
   drop table T_ProductType
go

if exists (select 1
            from  sysobjects
           where  id = object_id('T_Receive')
            and   type = 'U')
   drop table T_Receive
go

if exists (select 1
            from  sysobjects
           where  id = object_id('T_ReceiveDetail')
            and   type = 'U')
   drop table T_ReceiveDetail
go

if exists (select 1
            from  sysobjects
           where  id = object_id('T_Reject')
            and   type = 'U')
   drop table T_Reject
go

if exists (select 1
            from  sysobjects
           where  id = object_id('T_RejectDetail')
            and   type = 'U')
   drop table T_RejectDetail
go

if exists (select 1
            from  sysobjects
           where  id = object_id('T_Right')
            and   type = 'U')
   drop table T_Right
go

if exists (select 1
            from  sysobjects
           where  id = object_id('T_Role')
            and   type = 'U')
   drop table T_Role
go

if exists (select 1
            from  sysobjects
           where  id = object_id('T_RoleRight')
            and   type = 'U')
   drop table T_RoleRight
go

if exists (select 1
            from  sysobjects
           where  id = object_id('T_Store')
            and   type = 'U')
   drop table T_Store
go

if exists (select 1
            from  sysobjects
           where  id = object_id('T_Supplier')
            and   type = 'U')
   drop table T_Supplier
go

if exists (select 1
            from  sysobjects
           where  id = object_id('T_User')
            and   type = 'U')
   drop table T_User
go

if exists (select 1
            from  sysobjects
           where  id = object_id('T_UserRole')
            and   type = 'U')
   drop table T_UserRole
go

if exists (select 1
            from  sysobjects
           where  id = object_id('T_Warehouse')
            and   type = 'U')
   drop table T_Warehouse
go

/*==============================================================*/
/* Table: T_Bank                                                */
/*==============================================================*/
create table T_Bank (
   bankId               varchar(32)          not null,
   bankName             varchar(100)         null,
   bankShortName        varchar(50)          null,
   amount               float                null,
   constraint PK_T_BANK primary key (bankId)
)
go

/*==============================================================*/
/* Table: T_DataDictionary                                      */
/*==============================================================*/
create table T_DataDictionary (
   dataDictionaryId     varchar(32)          not null,
   dataDictionaryKind   varchar(20)          null,
   dataDictionaryName   varchar(50)          null,
   constraint PK_T_DATADICTIONARY primary key (dataDictionaryId)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '系统中用到的一些基础的数据：用kind 区分开
   1.商品颜色 color
   2.商品规格 size 
   3.商品单位 unit',
   'user', @CurrentUser, 'table', 'T_DataDictionary'
go

/*==============================================================*/
/* Table: T_Employee                                            */
/*==============================================================*/
create table T_Employee (
   employeeId           varchar(32)          not null,
   payId                varchar(3)           null,
   employeeName         varchar(50)          null,
   constraint PK_T_EMPLOYEE primary key (employeeId)
)
go

/*==============================================================*/
/* Table: T_InvoiceType                                         */
/*==============================================================*/
create table T_InvoiceType (
   invoiceTypeId        varchar(32)          not null,
   invoiceTypeName      varchar(50)          null,
   constraint PK_T_INVOICETYPE primary key (invoiceTypeId)
)
go

/*==============================================================*/
/* Table: T_Pay                                                 */
/*==============================================================*/
create table T_Pay (
   payId                varchar(3)           not null,
   receiveId            varchar(32)          null,
   bankId               varchar(32)          null,
   payCode              varchar(50)          null,
   payedAmount          float                null,
   discountAmount       float                null,
   payway               varchar(50)          null,
   payDate              date                 null,
   note                 varchar(50)          null,
   constraint PK_T_PAY primary key (payId)
)
go

/*==============================================================*/
/* Table: T_Product                                             */
/*==============================================================*/
create table T_Product (
   productId            varchar(32)          not null,
   productTypeID        varchar(32)          null,
   unitId               varchar(32)          null,
   colorId              varchar(32)          null,
   sizeId               varchar(32)          null,
   productCode          varchar(50)          null,
   productName          varchar(100)         null,
   qtyStore             float                null,
   amountStore          float                null,
   note                 varchar(50)          null,
   constraint PK_T_PRODUCT primary key (productId)
)
go

/*==============================================================*/
/* Table: T_ProductType                                         */
/*==============================================================*/
create table T_ProductType (
   productTypeID        varchar(32)          not null,
   parentProductTypeID  varchar(32)          null,
   productTypeName      varchar(50)          null,
   productTypeCode      varchar(50)          null,
   isLeaf               int                  null,
   constraint PK_PRODUCTTYPE primary key (productTypeID)
)
go

/*==============================================================*/
/* Table: T_Receive                                             */
/*==============================================================*/
create table T_Receive (
   receiveId            varchar(32)          not null,
   supplierId           varchar(32)          null,
   warehouseId          varchar(32)          null,
   employeeId           varchar(32)          null,
   bankId               varchar(32)          null,
   invoiceTypeId        varchar(32)          null,
   receiveCode          varchar(50)          null,
   deliverCode          varchar(50)          null,
   receiveDate          date                 null,
   amount               float                null,
   discountAmount       float                null,
   payAmount            float                null,
   shzt                 int                  null,
   note                 varchar(50)          null,
   constraint PK_T_RECEIVE primary key (receiveId)
)
go

/*==============================================================*/
/* Table: T_ReceiveDetail                                       */
/*==============================================================*/
create table T_ReceiveDetail (
   receiveDetailId      varchar(32)          not null,
   receiveId            varchar(32)          null,
   productId            varchar(32)          null,
   colorId              varchar(32)          null,
   qty                  float                null,
   price                float                null,
   note1                varchar(50)          null,
   note2                varchar(50)          null,
   note3                varchar(50)          null,
   constraint PK_T_RECEIVEDETAIL primary key (receiveDetailId)
)
go

/*==============================================================*/
/* Table: T_Reject                                              */
/*==============================================================*/
create table T_Reject (
   rejectId             varchar(32)          not null,
   supplierId           varchar(32)          null,
   warehouseId          varchar(32)          null,
   employeeId           varchar(32)          null,
   invoiceTypeId        varchar(32)          null,
   rejectCode           varchar(50)          null,
   buyCode              varchar(50)          null,
   rejectDate           date                 null,
   amount               float                null,
   payAmount            float                null,
   shzt                 int                  null,
   note                 varchar(50)          null,
   constraint PK_T_REJECT primary key (rejectId)
)
go

/*==============================================================*/
/* Table: T_RejectDetail                                        */
/*==============================================================*/
create table T_RejectDetail (
   rejectDetailId       varchar(32)          not null,
   rejectId             varchar(32)          null,
   productId            varchar(32)          null,
   colorId              varchar(32)          null,
   qty                  float                null,
   price                float                null,
   note1                varchar(50)          null,
   note2                varchar(50)          null,
   note3                varchar(50)          null,
   constraint PK_T_REJECTDETAIL primary key (rejectDetailId)
)
go

/*==============================================================*/
/* Table: T_Right                                               */
/*==============================================================*/
create table T_Right (
   rightId              varchar(32)          not null,
   rightName            varchar(50)          null,
   rightUrl             varchar(100)         null,
   parentRightId        varchar(32)          null,
   isLeaf               int                  null,
   constraint PK_T_RIGHT primary key (rightId)
)
go

/*==============================================================*/
/* Table: T_Role                                                */
/*==============================================================*/
create table T_Role (
   roleId               varchar(32)          not null,
   roleName             varchar(50)          null,
   constraint PK_T_ROLE primary key (roleId)
)
go

/*==============================================================*/
/* Table: T_RoleRight                                           */
/*==============================================================*/
create table T_RoleRight (
   rightId              varchar(32)          not null,
   roleId               varchar(32)          not null,
   state                bit                  null,
   constraint PK_T_ROLERIGHT primary key (rightId, roleId)
)
go

/*==============================================================*/
/* Table: T_Store                                               */
/*==============================================================*/
create table T_Store (
   storeId              varchar(32)          not null,
   warehouseId          varchar(32)          null,
   productId            varchar(32)          null,
   qty                  float                null,
   amount               float                null,
   constraint PK_T_STORE primary key (storeId)
)
go

/*==============================================================*/
/* Table: T_Supplier                                            */
/*==============================================================*/
create table T_Supplier (
   supplierId           varchar(32)          not null,
   supplierName         varchar(100)         null,
   supplierCode         varchar(50)          null,
   constraint PK_T_SUPPLIER primary key (supplierId)
)
go

/*==============================================================*/
/* Table: T_User                                                */
/*==============================================================*/
create table T_User (
   userId               varchar(32)          not null,
   userCode             varchar(50)          null,
   userName             varchar(50)          null,
   userPwd              varchar(50)          null,
   constraint PK_T_USER primary key (userId),
   constraint AK_UQ_USERCODE_T_USER unique (userCode)
)
go

/*==============================================================*/
/* Table: T_UserRole                                            */
/*==============================================================*/
create table T_UserRole (
   userId               varchar(32)          not null,
   roleId               varchar(32)          not null,
   constraint PK_T_USERROLE primary key (userId, roleId)
)
go

/*==============================================================*/
/* Table: T_Warehouse                                           */
/*==============================================================*/
create table T_Warehouse (
   warehouseId          varchar(32)          not null,
   warehouseName        varchar(100)         null,
   warehouseCode        varchar(50)          null,
   warehouseContactor   varchar(50)          null,
   warehouseTel         varchar(50)          null,
   warehouseAddr        varchar(200)         null,
   warehouseNode        varchar(500)         null,
   constraint PK_T_WAREHOUSE primary key (warehouseId)
)
go

alter table T_Employee
   add constraint FK_T_EMPLOY_REFERENCE_T_PAY foreign key (payId)
      references T_Pay (payId)
go

alter table T_Pay
   add constraint FK_T_PAY_REFERENCE_T_RECEIV foreign key (receiveId)
      references T_Receive (receiveId)
go

alter table T_Pay
   add constraint FK_T_PAY_REFERENCE_T_BANK foreign key (bankId)
      references T_Bank (bankId)
go

alter table T_Product
   add constraint FK_T_PRODUC_REFERENCE_T_Size foreign key (sizeId)
      references T_DataDictionary (dataDictionaryId)
go

alter table T_Product
   add constraint FK_T_PRODUC_REFERENCE_PRODUCTT foreign key (productTypeID)
      references T_ProductType (productTypeID)
go

alter table T_Product
   add constraint FK_T_PRODUC_REFERENCE_T_Unit foreign key (unitId)
      references T_DataDictionary (dataDictionaryId)
go

alter table T_Product
   add constraint FK_T_PRODUC_REFERENCE_T_Color foreign key (colorId)
      references T_DataDictionary (dataDictionaryId)
go

alter table T_ProductType
   add constraint FK_PRODUCTT_REFERENCE_PRODUCTT foreign key (parentProductTypeID)
      references T_ProductType (productTypeID)
go

alter table T_Receive
   add constraint FK_T_RECEIV_REFERENCE_T_SUPPLI foreign key (supplierId)
      references T_Supplier (supplierId)
go

alter table T_Receive
   add constraint FK_T_RECEIV_REFERENCE_T_WAREHO foreign key (warehouseId)
      references T_Warehouse (warehouseId)
go

alter table T_Receive
   add constraint FK_T_RECEIV_REFERENCE_T_EMPLOY foreign key (employeeId)
      references T_Employee (employeeId)
go

alter table T_Receive
   add constraint FK_T_RECEIV_REFERENCE_T_BANK foreign key (bankId)
      references T_Bank (bankId)
go

alter table T_Receive
   add constraint FK_T_RECEIV_REFERENCE_T_INVOIC foreign key (invoiceTypeId)
      references T_InvoiceType (invoiceTypeId)
go

alter table T_ReceiveDetail
   add constraint FK_T_RECEIV_REFERENCE_T_RECEIV foreign key (receiveId)
      references T_Receive (receiveId)
go

alter table T_ReceiveDetail
   add constraint FK_T_RECEIV_REFERENCE_T_PRODUC foreign key (productId)
      references T_Product (productId)
go

alter table T_ReceiveDetail
   add constraint FK_T_RECEIV_REFERENCE_T_DATADI foreign key (colorId)
      references T_DataDictionary (dataDictionaryId)
go

alter table T_Reject
   add constraint FK_T_REJECT_REFERENCE_T_WAREHO foreign key (warehouseId)
      references T_Warehouse (warehouseId)
go

alter table T_Reject
   add constraint FK_T_REJECT_REFERENCE_T_EMPLOY foreign key (employeeId)
      references T_Employee (employeeId)
go

alter table T_Reject
   add constraint FK_T_REJECT_REFERENCE_T_INVOIC foreign key (invoiceTypeId)
      references T_InvoiceType (invoiceTypeId)
go

alter table T_Reject
   add constraint FK_T_REJECT_REFERENCE_T_SUPPLI foreign key (supplierId)
      references T_Supplier (supplierId)
go

alter table T_RejectDetail
   add constraint FK_T_REJECT_REFERENCE_T_PRODUC foreign key (productId)
      references T_Product (productId)
go

alter table T_RejectDetail
   add constraint FK_T_REJECT_REFERENCE_T_DATADI foreign key (colorId)
      references T_DataDictionary (dataDictionaryId)
go

alter table T_RejectDetail
   add constraint FK_T_REJECT_REFERENCE_T_REJECT foreign key (rejectId)
      references T_Reject (rejectId)
go

alter table T_Right
   add constraint FK_T_RIGHT_REFERENCE_T_RIGHT foreign key (parentRightId)
      references T_Right (rightId)
go

alter table T_RoleRight
   add constraint FK_T_ROLERI_REFERENCE_T_RIGHT foreign key (rightId)
      references T_Right (rightId)
go

alter table T_RoleRight
   add constraint FK_T_ROLERI_REFERENCE_T_ROLE foreign key (roleId)
      references T_Role (roleId)
go

alter table T_Store
   add constraint FK_T_STORE_REFERENCE_T_WAREHO foreign key (warehouseId)
      references T_Warehouse (warehouseId)
go

alter table T_Store
   add constraint FK_T_STORE_REFERENCE_T_PRODUC foreign key (productId)
      references T_Product (productId)
go

alter table T_UserRole
   add constraint FK_T_USERRO_REFERENCE_T_USER foreign key (userId)
      references T_User (userId)
go

alter table T_UserRole
   add constraint FK_T_USERRO_REFERENCE_T_ROLE foreign key (roleId)
      references T_Role (roleId)
go

