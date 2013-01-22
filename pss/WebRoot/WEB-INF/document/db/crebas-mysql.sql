/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2013-01-20 11:04:06                          */
/*==============================================================*/


drop table if exists T_Bank;

drop table if exists T_Buy;

drop table if exists T_BuyDetail;

drop table if exists T_Customer;

drop table if exists T_DataDictionary;

drop table if exists T_Deliver;

drop table if exists T_DeliverDetail;

drop table if exists T_DeliverReject;

drop table if exists T_DeliverRejectDetail;

drop table if exists T_Employee;

drop table if exists T_Express;

drop table if exists T_InvoiceType;

drop table if exists T_Pay;

drop table if exists T_Product;

drop table if exists T_ProductType;

drop table if exists T_Receive;

drop table if exists T_ReceiveDetail;

drop table if exists T_Reject;

drop table if exists T_RejectDetail;

drop table if exists T_Right;

drop table if exists T_Role;

drop table if exists T_RoleRight;

drop table if exists T_Sale;

drop table if exists T_SaleDetail;

drop table if exists T_Store;

drop table if exists T_Supplier;

drop table if exists T_User;

drop table if exists T_UserRole;

drop table if exists T_Warehouse;

/*==============================================================*/
/* Table: T_Bank                                                */
/*==============================================================*/
create table T_Bank
(
   bankId               varchar(32) not null,
   bankName             varchar(100),
   bankShortName        varchar(50),
   amount               float,
   primary key (bankId)
);

/*==============================================================*/
/* Table: T_Buy                                                 */
/*==============================================================*/
create table T_Buy
(
   buyId                varchar(32) not null,
   invoiceTypeId        varchar(32),
   supplierId           varchar(32),
   employeeId           varchar(32),
   bankId               varchar(32),
   buyCode              varchar(50) not null,
   sourceCode           varchar(50),
   buyDate              date not null,
   receiveDate          date,
   otherAmount          float,
   amount               float,
   payAmount            float,
   statue               int,
   note                 varchar(100),
   primary key (buyId)
);

/*==============================================================*/
/* Table: T_BuyDetail                                           */
/*==============================================================*/
create table T_BuyDetail
(
   buyDetailId          varchar(32) not null,
   buyId                varchar(32),
   productId            varchar(32),
   colorId              varchar(32),
   qty                  float,
   price                float,
   note1                varchar(50),
   note2                varchar(50),
   note3                varchar(50),
   receiveQty           float,
   primary key (buyDetailId)
);

/*==============================================================*/
/* Table: T_Customer                                            */
/*==============================================================*/
create table T_Customer
(
   customerId           varchar(32) not null,
   customerCode         varchar(50) not null,
   customerName         varchar(100) not null,
   primary key (customerId)
);

/*==============================================================*/
/* Table: T_DataDictionary                                      */
/*==============================================================*/
create table T_DataDictionary
(
   dataDictionaryId     varchar(32) not null,
   dataDictionaryKind   varchar(20),
   dataDictionaryName   varchar(50),
   primary key (dataDictionaryId)
);

alter table T_DataDictionary comment '系统中用到的一些基础的数据：用kind 区分开
1.商品颜色 color
2.商品规格 size';

/*==============================================================*/
/* Table: T_Deliver                                             */
/*==============================================================*/
create table T_Deliver
(
   deliverId            varchar(32) not null,
   customerId           varchar(32),
   expressId            varchar(32),
   warehouseId          varchar(32),
   employeeId           varchar(32),
   bankId               varchar(32),
   invoiceTypeId        varchar(32),
   deliverCode          varchar(50),
   sourceCode           varchar(50),
   deliverDate          date,
   amount               float,
   discountAmount       float,
   receiptedAmount      float,
   status               int,
   expressCode          varchar(50),
   isReceipt            int,
   note                 varchar(50),
   primary key (deliverId)
);

/*==============================================================*/
/* Table: T_DeliverDetail                                       */
/*==============================================================*/
create table T_DeliverDetail
(
   deliverDetailId      varchar(32) not null,
   deliverId            varchar(32),
   productId            varchar(32),
   colorId              varchar(32),
   saleDetailId         varchar(32),
   qty                  float,
   price                float,
   discount             float,
   note1                varchar(50),
   note2                varchar(50),
   note3                varchar(50),
   primary key (deliverDetailId)
);

/*==============================================================*/
/* Table: T_DeliverReject                                       */
/*==============================================================*/
create table T_DeliverReject
(
   deliverRejectId      varchar(32) not null,
   bankId               varchar(32),
   employeeId           varchar(32),
   warehouseId          varchar(32),
   invoiceTypeId        varchar(32),
   customerId           varchar(32),
   deliverRejectCode    varchar(50),
   sourceCode           varchar(50),
   deliverRejectDate    date,
   amount               float,
   payedAmount          float,
   note                 varchar(100),
   primary key (deliverRejectId)
);

/*==============================================================*/
/* Table: T_DeliverRejectDetail                                 */
/*==============================================================*/
create table T_DeliverRejectDetail
(
   deliverRejectDetailId varchar(32) not null,
   deliverRejectId      varchar(32),
   productId            varchar(32),
   colorId              varchar(32),
   qty                  float,
   price                float,
   note1                varchar(50),
   note2                varchar(50),
   note3                varchar(50),
   primary key (deliverRejectDetailId)
);

/*==============================================================*/
/* Table: T_Employee                                            */
/*==============================================================*/
create table T_Employee
(
   employeeId           varchar(32) not null,
   employeeName         varchar(50),
   primary key (employeeId)
);

/*==============================================================*/
/* Table: T_Express                                             */
/*==============================================================*/
create table T_Express
(
   expressId            varchar(32) not null,
   expressName          varchar(100) not null,
   primary key (expressId)
);

/*==============================================================*/
/* Table: T_InvoiceType                                         */
/*==============================================================*/
create table T_InvoiceType
(
   invoiceTypeId        varchar(32) not null,
   invoiceTypeName      varchar(50),
   primary key (invoiceTypeId)
);

/*==============================================================*/
/* Table: T_Pay                                                 */
/*==============================================================*/
create table T_Pay
(
   payId                varchar(3) not null,
   supplierId           varchar(32),
   bankId               varchar(32),
   employeeId           varchar(32),
   receiveId            varchar(32),
   payCode              varchar(50),
   payedAmount          float,
   discountAmount       float,
   payway               varchar(50),
   payDate              date,
   note                 varchar(50),
   primary key (payId)
);

/*==============================================================*/
/* Table: T_Product                                             */
/*==============================================================*/
create table T_Product
(
   productId            varchar(32) not null,
   productTypeID        varchar(32),
   unitId               varchar(32),
   colorId              varchar(32),
   sizeId               varchar(32),
   productCode          varchar(50),
   productName          varchar(100),
   qtyStore             float,
   amountStore          float,
   buyingPrice          float,
   salePrice            float,
   note                 varchar(50),
   primary key (productId)
);

/*==============================================================*/
/* Table: T_ProductType                                         */
/*==============================================================*/
create table T_ProductType
(
   productTypeId        varchar(32) not null,
   parentProductTypeId  varchar(32),
   productTypeName      varchar(50),
   productTypeCode      varchar(50),
   isLeaf               int,
   primary key (productTypeId)
);

/*==============================================================*/
/* Table: T_Receive                                             */
/*==============================================================*/
create table T_Receive
(
   receiveId            varchar(32) not null,
   supplierId           varchar(32),
   warehouseId          varchar(32),
   invoiceTypeId        varchar(32),
   employeeId           varchar(32),
   bankId               varchar(32),
   receiveCode          varchar(50),
   deliverCode          varchar(50),
   receiveDate          date,
   amount               float,
   discountAmount       float,
   payAmount            float,
   status               int,
   isPay                int,
   note                 varchar(50),
   primary key (receiveId)
);

/*==============================================================*/
/* Table: T_ReceiveDetail                                       */
/*==============================================================*/
create table T_ReceiveDetail
(
   receiveDetailId      varchar(32) not null,
   receiveId            varchar(32),
   productId            varchar(32),
   buyDetailId          varchar(32),
   colorId              varchar(32),
   qty                  float,
   price                float,
   note1                varchar(50),
   note2                varchar(50),
   note3                varchar(50),
   primary key (receiveDetailId)
);

/*==============================================================*/
/* Table: T_Reject                                              */
/*==============================================================*/
create table T_Reject
(
   rejectId             varchar(32) not null,
   warehouseId          varchar(32),
   employeeId           varchar(32),
   supplierId           varchar(32),
   invoiceTypeId        varchar(32),
   bankId               varchar(32),
   rejectCode           varchar(50),
   buyCode              varchar(50),
   rejectDate           date,
   amount               float,
   payAmount            float,
   shzt                 int,
   note                 varchar(50),
   primary key (rejectId)
);

/*==============================================================*/
/* Table: T_RejectDetail                                        */
/*==============================================================*/
create table T_RejectDetail
(
   rejectDetailId       varchar(32) not null,
   rejectId             varchar(32),
   productId            varchar(32),
   colorId              varchar(32),
   qty                  float,
   price                float,
   note1                varchar(50),
   note2                varchar(50),
   note3                varchar(50),
   primary key (rejectDetailId)
);

/*==============================================================*/
/* Table: T_Right                                               */
/*==============================================================*/
create table T_Right
(
   rightId              varchar(32) not null,
   rightName            varchar(50),
   rightUrl             varchar(100),
   parentRightId        varchar(32),
   isLeaf               int,
   primary key (rightId)
);

/*==============================================================*/
/* Table: T_Role                                                */
/*==============================================================*/
create table T_Role
(
   roleId               varchar(32) not null,
   roleName             varchar(50),
   primary key (roleId)
);

/*==============================================================*/
/* Table: T_RoleRight                                           */
/*==============================================================*/
create table T_RoleRight
(
   rightId              varchar(32) not null,
   roleId               varchar(32) not null,
   state                int,
   primary key (rightId, roleId)
);

/*==============================================================*/
/* Table: T_Sale                                                */
/*==============================================================*/
create table T_Sale
(
   saleId               varchar(32) not null,
   customerId           varchar(32),
   bankId               varchar(32),
   employeeId           varchar(32),
   saleCode             varchar(50) not null,
   sourceCode           varchar(50),
   saleDate             date,
   deliverDate          date,
   otherAmount          float,
   amount               float,
   payedAmount          float,
   note                 varchar(500),
   status               int,
   primary key (saleId)
);

/*==============================================================*/
/* Table: T_SaleDetail                                          */
/*==============================================================*/
create table T_SaleDetail
(
   saleDetailId         varchar(32) not null,
   productId            varchar(32),
   colorId              varchar(32),
   saleId               varchar(32),
   qty                  float,
   price                float,
   discount             float,
   note1                varchar(50),
   note2                varchar(50),
   note3                varchar(50),
   hadSaleQty           float,
   primary key (saleDetailId)
);

/*==============================================================*/
/* Table: T_Store                                               */
/*==============================================================*/
create table T_Store
(
   storeId              varchar(32) not null,
   warehouseId          varchar(32),
   productId            varchar(32),
   qty                  float,
   amount               float,
   primary key (storeId)
);

/*==============================================================*/
/* Table: T_Supplier                                            */
/*==============================================================*/
create table T_Supplier
(
   supplierId           varchar(32) not null,
   supplierName         varchar(100),
   supplierCode         varchar(50),
   primary key (supplierId)
);

/*==============================================================*/
/* Table: T_User                                                */
/*==============================================================*/
create table T_User
(
   userId               varchar(32) not null,
   userCode             varchar(50),
   userName             varchar(50),
   userPwd              varchar(50),
   primary key (userId),
   key AK_UQ_USERCODE_T_USER (userCode)
);

/*==============================================================*/
/* Table: T_UserRole                                            */
/*==============================================================*/
create table T_UserRole
(
   userId               varchar(32) not null,
   roleId               varchar(32) not null,
   primary key (userId, roleId)
);

/*==============================================================*/
/* Table: T_Warehouse                                           */
/*==============================================================*/
create table T_Warehouse
(
   warehouseId          varchar(32) not null,
   warehouseName        varchar(100),
   warehouseCode        varchar(50),
   warehouseContactor   varchar(50),
   warehouseTel         varchar(50),
   warehouseAddr        varchar(200),
   warehouseNode        varchar(500),
   primary key (warehouseId)
);

alter table T_Buy add constraint FK_Reference_35 foreign key (supplierId)
      references T_Supplier (supplierId) on delete restrict on update restrict;

alter table T_Buy add constraint FK_Reference_36 foreign key (employeeId)
      references T_Employee (employeeId) on delete restrict on update restrict;

alter table T_Buy add constraint FK_T_BUY_REFERENCE_T_INVOIC foreign key (invoiceTypeId)
      references T_InvoiceType (invoiceTypeId);

alter table T_Buy add constraint FK_Reference_38 foreign key (bankId)
      references T_Bank (bankId) on delete restrict on update restrict;

alter table T_BuyDetail add constraint FK_Reference_39 foreign key (productId)
      references T_Product (productId) on delete restrict on update restrict;

alter table T_BuyDetail add constraint FK_Reference_40 foreign key (colorId)
      references T_DataDictionary (dataDictionaryId) on delete restrict on update restrict;

alter table T_BuyDetail add constraint FK_T_BUYDET_REFERENCE_T_BUY foreign key (buyId)
      references T_Buy (buyId);

alter table T_Deliver add constraint FK_Reference_67 foreign key (customerId)
      references T_Customer (customerId) on delete restrict on update restrict;

alter table T_Deliver add constraint FK_Reference_68 foreign key (expressId)
      references T_Express (expressId) on delete restrict on update restrict;

alter table T_Deliver add constraint FK_Reference_69 foreign key (warehouseId)
      references T_Warehouse (warehouseId) on delete restrict on update restrict;

alter table T_Deliver add constraint FK_Reference_70 foreign key (employeeId)
      references T_Employee (employeeId) on delete restrict on update restrict;

alter table T_Deliver add constraint FK_Reference_71 foreign key (bankId)
      references T_Bank (bankId) on delete restrict on update restrict;

alter table T_Deliver add constraint FK_Reference_72 foreign key (invoiceTypeId)
      references T_InvoiceType (invoiceTypeId) on delete restrict on update restrict;

alter table T_DeliverDetail add constraint FK_Reference_73 foreign key (deliverId)
      references T_Deliver (deliverId) on delete restrict on update restrict;

alter table T_DeliverDetail add constraint FK_Reference_74 foreign key (productId)
      references T_Product (productId) on delete restrict on update restrict;

alter table T_DeliverDetail add constraint FK_Reference_75 foreign key (colorId)
      references T_DataDictionary (dataDictionaryId) on delete restrict on update restrict;

alter table T_DeliverDetail add constraint FK_Reference_76 foreign key (saleDetailId)
      references T_SaleDetail (saleDetailId) on delete restrict on update restrict;

alter table T_DeliverReject add constraint FK_Reference_77 foreign key (bankId)
      references T_Bank (bankId) on delete restrict on update restrict;

alter table T_DeliverReject add constraint FK_Reference_78 foreign key (employeeId)
      references T_Employee (employeeId) on delete restrict on update restrict;

alter table T_DeliverReject add constraint FK_Reference_79 foreign key (warehouseId)
      references T_Warehouse (warehouseId) on delete restrict on update restrict;

alter table T_DeliverReject add constraint FK_Reference_80 foreign key (invoiceTypeId)
      references T_InvoiceType (invoiceTypeId) on delete restrict on update restrict;

alter table T_DeliverReject add constraint FK_Reference_81 foreign key (customerId)
      references T_Customer (customerId) on delete restrict on update restrict;

alter table T_DeliverRejectDetail add constraint FK_Reference_82 foreign key (deliverRejectId)
      references T_DeliverReject (deliverRejectId) on delete restrict on update restrict;

alter table T_DeliverRejectDetail add constraint FK_Reference_83 foreign key (productId)
      references T_Product (productId) on delete restrict on update restrict;

alter table T_DeliverRejectDetail add constraint FK_Reference_84 foreign key (colorId)
      references T_DataDictionary (dataDictionaryId) on delete restrict on update restrict;

alter table T_Pay add constraint FK_Reference_34 foreign key (supplierId)
      references T_Supplier (supplierId) on delete restrict on update restrict;

alter table T_Pay add constraint FK_Reference_58 foreign key (bankId)
      references T_Bank (bankId) on delete restrict on update restrict;

alter table T_Pay add constraint FK_Reference_59 foreign key (employeeId)
      references T_Employee (employeeId) on delete restrict on update restrict;

alter table T_Pay add constraint FK_Reference_60 foreign key (receiveId)
      references T_Receive (receiveId) on delete restrict on update restrict;

alter table T_Product add constraint FK_T_PRODUC_REFERENCE_T_Size foreign key (sizeId)
      references T_DataDictionary (dataDictionaryId);

alter table T_Product add constraint FK_T_PRODUC_REFERENCE_PRODUCTT foreign key (productTypeID)
      references T_ProductType (productTypeId);

alter table T_Product add constraint FK_T_PRODUC_REFERENCE_T_Unit foreign key (unitId)
      references T_DataDictionary (dataDictionaryId);

alter table T_Product add constraint FK_T_PRODUC_REFERENCE_T_Color foreign key (colorId)
      references T_DataDictionary (dataDictionaryId);

alter table T_Receive add constraint FK_Reference_43 foreign key (supplierId)
      references T_Supplier (supplierId) on delete restrict on update restrict;

alter table T_Receive add constraint FK_Reference_44 foreign key (warehouseId)
      references T_Warehouse (warehouseId) on delete restrict on update restrict;

alter table T_Receive add constraint FK_Reference_45 foreign key (invoiceTypeId)
      references T_InvoiceType (invoiceTypeId) on delete restrict on update restrict;

alter table T_Receive add constraint FK_Reference_46 foreign key (employeeId)
      references T_Employee (employeeId) on delete restrict on update restrict;

alter table T_Receive add constraint FK_Reference_47 foreign key (bankId)
      references T_Bank (bankId) on delete restrict on update restrict;

alter table T_ReceiveDetail add constraint FK_T_RECEIV_REFERENCE_T_RECEIV foreign key (receiveId)
      references T_Receive (receiveId);

alter table T_ReceiveDetail add constraint FK_Reference_42 foreign key (buyDetailId)
      references T_BuyDetail (buyDetailId) on delete restrict on update restrict;

alter table T_ReceiveDetail add constraint FK_Reference_48 foreign key (productId)
      references T_Product (productId) on delete restrict on update restrict;

alter table T_ReceiveDetail add constraint FK_Reference_49 foreign key (colorId)
      references T_DataDictionary (dataDictionaryId) on delete restrict on update restrict;

alter table T_Reject add constraint FK_Reference_50 foreign key (warehouseId)
      references T_Warehouse (warehouseId) on delete restrict on update restrict;

alter table T_Reject add constraint FK_Reference_51 foreign key (employeeId)
      references T_Employee (employeeId) on delete restrict on update restrict;

alter table T_Reject add constraint FK_Reference_52 foreign key (invoiceTypeId)
      references T_InvoiceType (invoiceTypeId) on delete restrict on update restrict;

alter table T_Reject add constraint FK_Reference_53 foreign key (bankId)
      references T_Bank (bankId) on delete restrict on update restrict;

alter table T_RejectDetail add constraint FK_T_REJECT_REFERENCE_T_REJECT foreign key (rejectId)
      references T_Reject (rejectId);

alter table T_RejectDetail add constraint FK_Reference_54 foreign key (productId)
      references T_Product (productId) on delete restrict on update restrict;

alter table T_RejectDetail add constraint FK_Reference_55 foreign key (colorId)
      references T_DataDictionary (dataDictionaryId) on delete restrict on update restrict;

alter table T_RoleRight add constraint FK_T_ROLERI_REFERENCE_T_RIGHT foreign key (rightId)
      references T_Right (rightId);

alter table T_RoleRight add constraint FK_T_ROLERI_REFERENCE_T_ROLE foreign key (roleId)
      references T_Role (roleId);

alter table T_Sale add constraint FK_Reference_61 foreign key (customerId)
      references T_Customer (customerId) on delete restrict on update restrict;

alter table T_Sale add constraint FK_Reference_62 foreign key (bankId)
      references T_Bank (bankId) on delete restrict on update restrict;

alter table T_Sale add constraint FK_Reference_63 foreign key (employeeId)
      references T_Employee (employeeId) on delete restrict on update restrict;

alter table T_SaleDetail add constraint FK_Reference_64 foreign key (productId)
      references T_Product (productId) on delete restrict on update restrict;

alter table T_SaleDetail add constraint FK_Reference_65 foreign key (colorId)
      references T_DataDictionary (dataDictionaryId) on delete restrict on update restrict;

alter table T_SaleDetail add constraint FK_Reference_66 foreign key (saleId)
      references T_Sale (saleId) on delete restrict on update restrict;

alter table T_Store add constraint FK_Reference_56 foreign key (warehouseId)
      references T_Warehouse (warehouseId) on delete restrict on update restrict;

alter table T_Store add constraint FK_Reference_57 foreign key (productId)
      references T_Product (productId) on delete restrict on update restrict;

alter table T_UserRole add constraint FK_T_USERRO_REFERENCE_T_USER foreign key (userId)
      references T_User (userId);

alter table T_UserRole add constraint FK_T_USERRO_REFERENCE_T_ROLE foreign key (roleId)
      references T_Role (roleId);

