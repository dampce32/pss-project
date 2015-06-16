/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2015-06-17 00:15:03                          */
/*==============================================================*/


drop table if exists T_Bank;

drop table if exists T_BankStatements;

drop table if exists T_Buy;

drop table if exists T_BuyDetail;

drop table if exists T_Customer;

drop table if exists T_CustomerType;

drop table if exists T_DataDictionary;

drop table if exists T_DefaultPackaging;

drop table if exists T_Deliver;

drop table if exists T_DeliverDetail;

drop table if exists T_DeliverReject;

drop table if exists T_DeliverRejectDetail;

drop table if exists T_Employee;

drop table if exists T_Expense;

drop table if exists T_Express;

drop table if exists T_Income;

drop table if exists T_InvoiceType;

drop table if exists T_PackageDetail;

drop table if exists T_Packaging;

drop table if exists T_Pay;

drop table if exists T_PayDetail;

drop table if exists T_Prefix;

drop table if exists T_Prepay;

drop table if exists T_Product;

drop table if exists T_ProductPriceRange;

drop table if exists T_ProductType;

drop table if exists T_Receive;

drop table if exists T_ReceiveDetail;

drop table if exists T_Reject;

drop table if exists T_RejectDetail;

drop table if exists T_Reminder;

drop table if exists T_ReminderDetail;

drop table if exists T_ReminderItem;

drop table if exists T_ReportConfig;

drop table if exists T_ReportParam;

drop table if exists T_ReportParamConfig;

drop table if exists T_Right;

drop table if exists T_Role;

drop table if exists T_RoleRight;

drop table if exists T_Sale;

drop table if exists T_SaleDetail;

drop table if exists T_Split;

drop table if exists T_SplitDetail;

drop table if exists T_Store;

drop table if exists T_Supplier;

drop table if exists T_SystemConfig;

drop table if exists T_User;

drop table if exists T_UserRole;

drop table if exists T_Warehouse;

/*==============================================================*/
/* User: pss                                                    */
/*==============================================================*/
create user pss;

/*==============================================================*/
/* Table: T_Bank                                                */
/*==============================================================*/
create table T_Bank
(
   bankId               varchar(32) not null,
   bankName             varchar(100),
   bankShortName        varchar(50),
   amount               double,
   primary key (bankId)
);

/*==============================================================*/
/* Table: T_BankStatements                                      */
/*==============================================================*/
create table T_BankStatements
(
   bankStatementsId     varchar(32) not null,
   bankId               varchar(32) not null,
   employeeId           varchar(32),
   amount               double not null,
   bankStatementsDate   date not null,
   bankStatementsKind   varchar(20) not null,
   status               int not null,
   primary key (bankStatementsId)
);

/*==============================================================*/
/* Table: T_Buy                                                 */
/*==============================================================*/
create table T_Buy
(
   buyId                varchar(32) not null,
   supplierId           varchar(32),
   employeeId           varchar(32),
   bankId               varchar(32),
   invoiceTypeId        varchar(32),
   buyCode              varchar(50) not null,
   sourceCode           varchar(50),
   buyDate              date not null,
   receiveDate          date,
   otherAmount          double,
   amount               double,
   payAmount            double,
   checkAmount          double,
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
   qty                  double,
   price                double,
   note1                varchar(50),
   note2                varchar(50),
   note3                varchar(50),
   receiveQty           double,
   primary key (buyDetailId)
);

/*==============================================================*/
/* Table: T_Customer                                            */
/*==============================================================*/
create table T_Customer
(
   customerId           varchar(32) not null,
   customerTypeID       varchar(32),
   customerCode         varchar(50) not null,
   customerName         varchar(100),
   contacter            varchar(100),
   phone                varchar(50),
   fax                  varchar(50),
   status               int not null,
   priceLevel           varchar(50) not null,
   note                 varchar(1000),
   primary key (customerId)
);

alter table T_Customer comment '价格等级priceLevel
批发价格wholesalePrice
VIP价格vipPrice
';

/*==============================================================*/
/* Table: T_CustomerType                                        */
/*==============================================================*/
create table T_CustomerType
(
   customerTypeID       varchar(32) not null,
   customerTypeCode     varchar(100),
   customerTypeName     varchar(500),
   note                 varchar(1000),
   primary key (customerTypeID)
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
/* Table: T_DefaultPackaging                                    */
/*==============================================================*/
create table T_DefaultPackaging
(
   defaultPackagingId   varchar(32) not null,
   productId            varchar(32),
   parentProductId      varchar(32),
   qty                  int,
   primary key (defaultPackagingId)
);

/*==============================================================*/
/* Table: T_Deliver                                             */
/*==============================================================*/
create table T_Deliver
(
   deliverId            varchar(32) not null,
   expressId            varchar(32),
   warehouseId          varchar(32),
   employeeId           varchar(32),
   bankId               varchar(32),
   invoiceTypeId        varchar(32),
   customerId           varchar(32),
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
   qty                  double,
   price                double,
   discount             double,
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
   amount               double,
   payedAmount          double,
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
   qty                  double,
   price                double,
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
   employeeCode         varchar(50) not null,
   employeeName         varchar(50) not null,
   sex                  varchar(2),
   birthday             date,
   isMarry              varchar(2),
   education            varchar(50),
   nation               varchar(50),
   nativePlace          varchar(100),
   residence            varchar(500),
   major                varchar(200),
   startWorkDate        date not null,
   salary               double,
   bankNo               varchar(50),
   idNo                 varchar(50),
   phone                varchar(50),
   telPhone             varchar(50),
   qq                   varchar(50),
   email                varchar(100),
   note                 varchar(500),
   status               int not null,
   primary key (employeeId)
);

alter table T_Employee comment '性别:0--男 1--女
婚否: 0--否 1--是
';

/*==============================================================*/
/* Table: T_Expense                                             */
/*==============================================================*/
create table T_Expense
(
   expenseId            varchar(32) not null,
   expenseName          varchar(50),
   bankId               varchar(32) not null,
   employeeId           varchar(32),
   amount               double not null,
   expenseDate          date not null,
   note                 varchar(100),
   status               int,
   primary key (expenseId)
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
/* Table: T_Income                                              */
/*==============================================================*/
create table T_Income
(
   incomeId             varchar(32) not null,
   bankId               varchar(32) not null,
   employeeId           varchar(32),
   incomeName           varchar(50),
   incomeDate           date not null,
   note                 varchar(100),
   amount               double not null,
   status               int not null,
   primary key (incomeId)
);

/*==============================================================*/
/* Table: T_InvoiceType                                         */
/*==============================================================*/
create table T_InvoiceType
(
   invoiceTypeId        varchar(32) not null,
   invoiceTypeName      varchar(50) not null,
   primary key (invoiceTypeId)
);

/*==============================================================*/
/* Table: T_PackageDetail                                       */
/*==============================================================*/
create table T_PackageDetail
(
   packagingDetailId    varchar(32) not null,
   productId            varchar(32) not null,
   warehouseId          varchar(32) not null,
   packagingId          varchar(32) not null,
   qty                  int not null,
   price                double not null,
   note1                varchar(50),
   note2                varchar(50),
   note3                varchar(50),
   primary key (packagingDetailId)
);

/*==============================================================*/
/* Table: T_Packaging                                           */
/*==============================================================*/
create table T_Packaging
(
   packagingId          varchar(32) not null,
   packagingCode        varchar(50) not null,
   warehouseId          varchar(32) not null,
   productId            varchar(32) not null,
   employeeId           varchar(32),
   packagingDate        date not null,
   qty                  int not null,
   price                double not null,
   note                 varchar(100),
   status               int not null,
   primary key (packagingId),
   key AK_AK (packagingCode)
);

/*==============================================================*/
/* Table: T_Pay                                                 */
/*==============================================================*/
create table T_Pay
(
   payId                varchar(32) not null,
   supplierId           varchar(32),
   bankId               varchar(32),
   employeeId           varchar(32),
   payCode              varchar(50),
   payway               varchar(50),
   payDate              date,
   note                 varchar(50),
   status               int,
   primary key (payId)
);

/*==============================================================*/
/* Table: T_PayDetail                                           */
/*==============================================================*/
create table T_PayDetail
(
   payDetailId          varchar(32) not null,
   payId                varchar(32),
   receiveId            varchar(32),
   prepayId             varchar(32),
   buyId                varchar(32),
   rejectId             varchar(32),
   payKind              varchar(20),
   sourceCode           varchar(50),
   sourceDate           varchar(20),
   amount               double,
   payedAmount          double,
   discountAmount       double,
   payAmount            double,
   primary key (payDetailId)
);

/*==============================================================*/
/* Table: T_Prefix                                              */
/*==============================================================*/
create table T_Prefix
(
   prefixId             varchar(32) not null,
   prefixCode           varchar(50),
   prefix               varchar(50),
   prefixName           varchar(100),
   primary key (prefixId),
   key AK_AK (prefixCode)
);

/*==============================================================*/
/* Table: T_Prepay                                              */
/*==============================================================*/
create table T_Prepay
(
   prepayId             varchar(32) not null,
   bankId               varchar(32),
   supplierId           varchar(32),
   employeeId           varchar(32),
   prepayCode           varchar(50),
   prepayDate           date,
   amount               double,
   checkAmount          double,
   note                 varchar(100),
   status               int,
   primary key (prepayId)
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
   priceStore           double,
   qtyStore             double,
   amountStore          double,
   buyingPrice          double,
   wholesalePrice       double,
   vipPrice             double,
   memberPrice          double,
   salePrice            double,
   status               int,
   note                 varchar(50),
   primary key (productId)
);

alter table T_Product comment '1.库存数量：入库+，出库-
2.库存金额：入库时=原库存金额+入库单金额
          ';

/*==============================================================*/
/* Table: T_ProductPriceRange                                   */
/*==============================================================*/
create table T_ProductPriceRange
(
   productPriceRangeId  varchar(32) not null,
   productId            varchar(32) not null,
   priceLevel           varchar(50) not null,
   qtyBegin             double not null,
   qtyEnd               double not null,
   price                double not null,
   primary key (productPriceRangeId)
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
   employeeId           varchar(32),
   bankId               varchar(32),
   invoiceTypeId        varchar(32),
   receiveCode          varchar(50),
   deliverCode          varchar(50),
   receiveDate          date,
   amount               double,
   discountAmount       double,
   payAmount            double,
   checkAmount          double,
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
   qty                  double,
   price                double,
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
   bankId               varchar(32),
   invoiceTypeId        varchar(32),
   rejectCode           varchar(50),
   buyCode              varchar(50),
   rejectDate           date,
   amount               double,
   payAmount            double,
   checkAmount          double,
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
   qty                  double,
   price                double,
   note1                varchar(50),
   note2                varchar(50),
   note3                varchar(50),
   primary key (rejectDetailId)
);

/*==============================================================*/
/* Table: T_Reminder                                            */
/*==============================================================*/
create table T_Reminder
(
   reminderId           int not null auto_increment,
   title                varchar(50) not null,
   status               int not null,
   array                int not null,
   primary key (reminderId)
);

alter table T_Reminder comment '允许类型 1 -- 用户
                 0 -- 角色';

/*==============================================================*/
/* Table: T_ReminderDetail                                      */
/*==============================================================*/
create table T_ReminderDetail
(
   reminderDetailId     int not null auto_increment,
   reminderId           int,
   reminderItemId       int,
   primary key (reminderDetailId)
);

/*==============================================================*/
/* Table: T_ReminderItem                                        */
/*==============================================================*/
create table T_ReminderItem
(
   reminderItemId       int not null auto_increment,
   rightId              int,
   title                varchar(50) not null,
   message              varchar(200) not null,
   countSql             varchar(1000),
   primary key (reminderItemId)
);

/*==============================================================*/
/* Table: T_ReportConfig                                        */
/*==============================================================*/
create table T_ReportConfig
(
   reportConfigId       varchar(32) not null,
   reportCode           varchar(50),
   reportName           varchar(100),
   reportDetailSql      varchar(100),
   reportParamsSql      varchar(100),
   reportKind           int not null,
   primary key (reportConfigId),
   key AK_AK (reportCode)
);

alter table T_ReportConfig comment '其中：
报表类型：
0--模块报表
1--统计报表';

/*==============================================================*/
/* Table: T_ReportParam                                         */
/*==============================================================*/
create table T_ReportParam
(
   reportParamId        varchar(32) not null,
   paramName            varchar(50),
   paramCode            varchar(100) not null,
   primary key (reportParamId),
   key AK_Key_2 (paramCode)
);

/*==============================================================*/
/* Table: T_ReportParamConfig                                   */
/*==============================================================*/
create table T_ReportParamConfig
(
   reportParamConfigId  varchar(32) not null,
   reportConfigId       varchar(32),
   reportParamId        varchar(32),
   isNeedChoose         int not null,
   array                int not null,
   primary key (reportParamConfigId)
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
   array                int,
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
   bankId               varchar(32),
   employeeId           varchar(32),
   customerId           varchar(32),
   saleCode             varchar(50) not null,
   sourceCode           varchar(50),
   saleDate             date,
   deliverDate          date,
   otherAmount          double,
   amount               double,
   receiptedAmount      double,
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
   qty                  double,
   price                double,
   discount             double,
   note1                varchar(50),
   note2                varchar(50),
   note3                varchar(50),
   hadSaleQty           double,
   primary key (saleDetailId)
);

/*==============================================================*/
/* Table: T_Split                                               */
/*==============================================================*/
create table T_Split
(
   splitId              varchar(32) not null,
   splitCode            varchar(50) not null,
   warehouseId          varchar(32) not null,
   productId            varchar(32) not null,
   employeeId           varchar(32),
   splitDate            date not null,
   qty                  int not null,
   price                double not null,
   note                 varchar(100),
   status               int not null,
   primary key (splitId),
   key AK_AK (splitCode)
);

/*==============================================================*/
/* Table: T_SplitDetail                                         */
/*==============================================================*/
create table T_SplitDetail
(
   splitDetailId        varchar(32) not null,
   productId            varchar(32) not null,
   warehouseId          varchar(32) not null,
   splitId              varchar(32),
   qty                  int not null,
   price                double not null,
   note1                varchar(50),
   note2                varchar(50),
   note3                varchar(50),
   primary key (splitDetailId)
);

/*==============================================================*/
/* Table: T_Store                                               */
/*==============================================================*/
create table T_Store
(
   storeId              varchar(32) not null,
   warehouseId          varchar(32),
   productId            varchar(32),
   qty                  double,
   amount               double,
   primary key (storeId)
);

/*==============================================================*/
/* Table: T_Supplier                                            */
/*==============================================================*/
create table T_Supplier
(
   supplierId           varchar(32) not null,
   supplierName         varchar(100),
   supplierCode         varchar(50) not null,
   contact              varchar(50),
   addr                 varchar(100),
   phone                varchar(20),
   fax                  varchar(20),
   email                varchar(50),
   note1                varchar(100),
   note2                varchar(100),
   note3                varchar(100),
   primary key (supplierId)
);

/*==============================================================*/
/* Table: T_SystemConfig                                        */
/*==============================================================*/
create table T_SystemConfig
(
   systemConfigId       varchar(32) not null,
   companyName          varchar(50),
   companyPhone         varchar(50),
   companyFax           varchar(50),
   companyAddr          varchar(100),
   primary key (systemConfigId)
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

alter table T_BankStatements add constraint FK_Reference_97 foreign key (bankId)
      references T_Bank (bankId) on delete restrict on update restrict;

alter table T_BankStatements add constraint FK_Reference_98 foreign key (employeeId)
      references T_Employee (employeeId) on delete restrict on update restrict;

alter table T_Buy add constraint FK_Reference_100 foreign key (invoiceTypeId)
      references T_InvoiceType (invoiceTypeId) on delete restrict on update restrict;

alter table T_Buy add constraint FK_Reference_35 foreign key (supplierId)
      references T_Supplier (supplierId) on delete restrict on update restrict;

alter table T_Buy add constraint FK_Reference_36 foreign key (employeeId)
      references T_Employee (employeeId) on delete restrict on update restrict;

alter table T_Buy add constraint FK_Reference_38 foreign key (bankId)
      references T_Bank (bankId) on delete restrict on update restrict;

alter table T_BuyDetail add constraint FK_Reference_39 foreign key (productId)
      references T_Product (productId) on delete restrict on update restrict;

alter table T_BuyDetail add constraint FK_Reference_40 foreign key (colorId)
      references T_DataDictionary (dataDictionaryId) on delete restrict on update restrict;

alter table T_BuyDetail add constraint FK_T_BUYDET_REFERENCE_T_BUY foreign key (buyId)
      references T_Buy (buyId);

alter table T_Customer add constraint FK_Reference_81 foreign key (customerTypeID)
      references T_CustomerType (customerTypeID) on delete restrict on update restrict;

alter table T_DefaultPackaging add constraint FK_Reference_105 foreign key (productId)
      references T_Product (productId) on delete restrict on update restrict;

alter table T_DefaultPackaging add constraint FK_Reference_106 foreign key (parentProductId)
      references T_Product (productId) on delete restrict on update restrict;

alter table T_Deliver add constraint FK_Reference_101 foreign key (customerId)
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

alter table T_DeliverReject add constraint FK_Reference_102 foreign key (customerId)
      references T_Customer (customerId) on delete restrict on update restrict;

alter table T_DeliverReject add constraint FK_Reference_77 foreign key (bankId)
      references T_Bank (bankId) on delete restrict on update restrict;

alter table T_DeliverReject add constraint FK_Reference_78 foreign key (employeeId)
      references T_Employee (employeeId) on delete restrict on update restrict;

alter table T_DeliverReject add constraint FK_Reference_79 foreign key (warehouseId)
      references T_Warehouse (warehouseId) on delete restrict on update restrict;

alter table T_DeliverReject add constraint FK_Reference_80 foreign key (invoiceTypeId)
      references T_InvoiceType (invoiceTypeId) on delete restrict on update restrict;

alter table T_DeliverRejectDetail add constraint FK_Reference_82 foreign key (deliverRejectId)
      references T_DeliverReject (deliverRejectId) on delete restrict on update restrict;

alter table T_DeliverRejectDetail add constraint FK_Reference_83 foreign key (productId)
      references T_Product (productId) on delete restrict on update restrict;

alter table T_DeliverRejectDetail add constraint FK_Reference_84 foreign key (colorId)
      references T_DataDictionary (dataDictionaryId) on delete restrict on update restrict;

alter table T_Expense add constraint FK_Reference_95 foreign key (bankId)
      references T_Bank (bankId) on delete restrict on update restrict;

alter table T_Expense add constraint FK_Reference_96 foreign key (employeeId)
      references T_Employee (employeeId) on delete restrict on update restrict;

alter table T_Income add constraint FK_Reference_93 foreign key (bankId)
      references T_Bank (bankId) on delete restrict on update restrict;

alter table T_Income add constraint FK_Reference_94 foreign key (employeeId)
      references T_Employee (employeeId) on delete restrict on update restrict;

alter table T_PackageDetail add constraint FK_Reference_110 foreign key (productId)
      references T_Product (productId) on delete restrict on update restrict;

alter table T_PackageDetail add constraint FK_Reference_111 foreign key (warehouseId)
      references T_Warehouse (warehouseId) on delete restrict on update restrict;

alter table T_PackageDetail add constraint FK_Reference_112 foreign key (packagingId)
      references T_Packaging (packagingId) on delete restrict on update restrict;

alter table T_Packaging add constraint FK_Reference_107 foreign key (warehouseId)
      references T_Warehouse (warehouseId) on delete restrict on update restrict;

alter table T_Packaging add constraint FK_Reference_108 foreign key (productId)
      references T_Product (productId) on delete restrict on update restrict;

alter table T_Packaging add constraint FK_Reference_109 foreign key (employeeId)
      references T_Employee (employeeId) on delete restrict on update restrict;

alter table T_Pay add constraint FK_Reference_34 foreign key (supplierId)
      references T_Supplier (supplierId) on delete restrict on update restrict;

alter table T_Pay add constraint FK_Reference_58 foreign key (bankId)
      references T_Bank (bankId) on delete restrict on update restrict;

alter table T_Pay add constraint FK_Reference_59 foreign key (employeeId)
      references T_Employee (employeeId) on delete restrict on update restrict;

alter table T_PayDetail add constraint FK_Reference_85 foreign key (payId)
      references T_Pay (payId) on delete restrict on update restrict;

alter table T_PayDetail add constraint FK_Reference_86 foreign key (receiveId)
      references T_Receive (receiveId) on delete restrict on update restrict;

alter table T_PayDetail add constraint FK_Reference_90 foreign key (prepayId)
      references T_Prepay (prepayId) on delete restrict on update restrict;

alter table T_PayDetail add constraint FK_Reference_91 foreign key (buyId)
      references T_Buy (buyId) on delete restrict on update restrict;

alter table T_PayDetail add constraint FK_Reference_92 foreign key (rejectId)
      references T_Reject (rejectId) on delete restrict on update restrict;

alter table T_Prepay add constraint FK_Reference_87 foreign key (bankId)
      references T_Bank (bankId) on delete restrict on update restrict;

alter table T_Prepay add constraint FK_Reference_88 foreign key (supplierId)
      references T_Supplier (supplierId) on delete restrict on update restrict;

alter table T_Prepay add constraint FK_Reference_89 foreign key (employeeId)
      references T_Employee (employeeId) on delete restrict on update restrict;

alter table T_Product add constraint FK_T_PRODUC_REFERENCE_T_Size foreign key (sizeId)
      references T_DataDictionary (dataDictionaryId);

alter table T_Product add constraint FK_T_PRODUC_REFERENCE_PRODUCTT foreign key (productTypeID)
      references T_ProductType (productTypeId);

alter table T_Product add constraint FK_T_PRODUC_REFERENCE_T_Unit foreign key (unitId)
      references T_DataDictionary (dataDictionaryId);

alter table T_Product add constraint FK_T_PRODUC_REFERENCE_T_Color foreign key (colorId)
      references T_DataDictionary (dataDictionaryId);

alter table T_ProductPriceRange add constraint FK_Reference_120 foreign key (productId)
      references T_Product (productId) on delete restrict on update restrict;

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

alter table T_ReminderDetail add constraint FK_Reference_65 foreign key (reminderId)
      references T_Reminder (reminderId) on delete restrict on update restrict;

alter table T_ReminderDetail add constraint FK_Reference_66 foreign key (reminderItemId)
      references T_ReminderItem (reminderItemId) on delete restrict on update restrict;

alter table T_ReminderItem add constraint FK_Reference_119 foreign key (rightId)
      references T_Right (rightId) on delete restrict on update restrict;

alter table T_ReportParamConfig add constraint FK_Reference_103 foreign key (reportConfigId)
      references T_ReportConfig (reportConfigId) on delete restrict on update restrict;

alter table T_ReportParamConfig add constraint FK_Reference_104 foreign key (reportParamId)
      references T_ReportParam (reportParamId) on delete restrict on update restrict;

alter table T_RoleRight add constraint FK_T_ROLERI_REFERENCE_T_RIGHT foreign key (rightId)
      references T_Right (rightId);

alter table T_RoleRight add constraint FK_T_ROLERI_REFERENCE_T_ROLE foreign key (roleId)
      references T_Role (roleId);

alter table T_Sale add constraint FK_Reference_62 foreign key (bankId)
      references T_Bank (bankId) on delete restrict on update restrict;

alter table T_Sale add constraint FK_Reference_63 foreign key (employeeId)
      references T_Employee (employeeId) on delete restrict on update restrict;

alter table T_Sale add constraint FK_Reference_99 foreign key (customerId)
      references T_Customer (customerId) on delete restrict on update restrict;

alter table T_SaleDetail add constraint FK_Reference_64 foreign key (productId)
      references T_Product (productId) on delete restrict on update restrict;

alter table T_SaleDetail add constraint FK_Reference_65 foreign key (colorId)
      references T_DataDictionary (dataDictionaryId) on delete restrict on update restrict;

alter table T_SaleDetail add constraint FK_Reference_66 foreign key (saleId)
      references T_Sale (saleId) on delete restrict on update restrict;

alter table T_Split add constraint FK_Reference_113 foreign key (warehouseId)
      references T_Warehouse (warehouseId) on delete restrict on update restrict;

alter table T_Split add constraint FK_Reference_114 foreign key (productId)
      references T_Product (productId) on delete restrict on update restrict;

alter table T_Split add constraint FK_Reference_115 foreign key (employeeId)
      references T_Employee (employeeId) on delete restrict on update restrict;

alter table T_SplitDetail add constraint FK_Reference_116 foreign key (productId)
      references T_Product (productId) on delete restrict on update restrict;

alter table T_SplitDetail add constraint FK_Reference_117 foreign key (warehouseId)
      references T_Warehouse (warehouseId) on delete restrict on update restrict;

alter table T_SplitDetail add constraint FK_Reference_118 foreign key (splitId)
      references T_Split (splitId) on delete restrict on update restrict;

alter table T_Store add constraint FK_Reference_56 foreign key (warehouseId)
      references T_Warehouse (warehouseId) on delete restrict on update restrict;

alter table T_Store add constraint FK_Reference_57 foreign key (productId)
      references T_Product (productId) on delete restrict on update restrict;

alter table T_UserRole add constraint FK_T_USERRO_REFERENCE_T_USER foreign key (userId)
      references T_User (userId);

alter table T_UserRole add constraint FK_T_USERRO_REFERENCE_T_ROLE foreign key (roleId)
      references T_Role (roleId);

