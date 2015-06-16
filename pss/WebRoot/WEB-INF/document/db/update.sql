drop table if exists T_ProductRange;

/*==============================================================*/
/* Table: T_ProductRange                                        */
/*==============================================================*/
create table T_ProductRange
(
   productRangeId       varchar(32),
   productId            varchar(32) not null,
   priceLevel           varchar(50) not null,
   priceBegin           double not null,
   priceEnd             double not null,
   primary key (productRangeId)
);

alter table T_ProductRange add constraint FK_Reference_120 foreign key (productId)
      references T_Product (productId) on delete restrict on update restrict;
