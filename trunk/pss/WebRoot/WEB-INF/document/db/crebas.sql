/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2008                    */
/* Created on:     2012/9/17 22:50:05                           */
/*==============================================================*/


if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.t_operator')
            and   type = 'U')
   drop table dbo.t_operator
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.t_user')
            and   type = 'U')
   drop table dbo.t_user
go

execute sp_revokedbaccess dbo
go

/*==============================================================*/
/* User: dbo                                                    */
/*==============================================================*/
execute sp_grantdbaccess dbo
go

/*==============================================================*/
/* Table: t_operator                                            */
/*==============================================================*/
create table dbo.t_operator (
   id                   varchar(32)          collate Chinese_PRC_CI_AS not null,
   code                 varchar(50)          collate Chinese_PRC_CI_AS null,
   name                 varchar(50)          collate Chinese_PRC_CI_AS null,
   password             varchar(50)          collate Chinese_PRC_CI_AS null,
   constraint PK__t_operat__3213E83F1273C1CD primary key (id)
         on "PRIMARY"
)
on "PRIMARY"
go

/*==============================================================*/
/* Table: t_user                                                */
/*==============================================================*/
create table dbo.t_user (
   id                   varchar(32)          collate Chinese_PRC_CI_AS not null,
   name                 varchar(255)         collate Chinese_PRC_CI_AS null,
   constraint PK__t_user__3213E83F164452B1 primary key (id)
         on "PRIMARY"
)
on "PRIMARY"
go

