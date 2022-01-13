/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2022/1/13 11:47:11                           */
/*==============================================================*/


drop table if exists answer_paper;

drop table if exists answer_paper_choice;

drop table if exists answer_question;

drop table if exists sys_paper;

drop table if exists sys_paper_choice;

drop table if exists sys_question;

drop table if exists sys_user;

/*==============================================================*/
/* Table: answer_paper                                          */
/*==============================================================*/
create table answer_paper
(
   answer_paper_id      int not null auto_increment,
   paper_id             int,
   openid               varchar(164),
   paper_type           varchar(2),
   paper_value          text,
   primary key (answer_paper_id)
);

alter table answer_paper comment '小程序开始答题所使用的表';

/*==============================================================*/
/* Table: answer_paper_choice                                   */
/*==============================================================*/
create table answer_paper_choice
(
   answer_choice_id     int not null auto_increment,
   choice_id            int,
   paper_id             int,
   openid               varchar(164),
   primary key (answer_choice_id)
);

alter table answer_paper_choice comment '小程序答题答案多选情况下的选项存储';

/*==============================================================*/
/* Table: answer_question                                       */
/*==============================================================*/
create table answer_question
(
   answer_id            int not null auto_increment,
   question_id          int,
   openid               varchar(164),
   create_time          datetime,
   primary key (answer_id)
);

alter table answer_question comment '小程序用，查询数据时，回填数据';

/*==============================================================*/
/* Table: sys_paper                                             */
/*==============================================================*/
create table sys_paper
(
   paper_id             int not null auto_increment,
   question_id          int,
   paper_title          varchar(164),
   paper_type           varchar(2),
   paper_order          int,
   primary key (paper_id)
);

/*==============================================================*/
/* Table: sys_paper_choice                                      */
/*==============================================================*/
create table sys_paper_choice
(
   choice_id            int not null auto_increment,
   paper_id             int,
   choice_text          varchar(164),
   choice_order         int,
   primary key (choice_id)
);

/*==============================================================*/
/* Table: sys_question                                          */
/*==============================================================*/
create table sys_question
(
   question_id          int not null auto_increment,
   question_title       text,
   question_desc        text,
   question_img         varchar(164),
   question_order       int,
   question_status      varchar(2),
   primary key (question_id)
);

/*==============================================================*/
/* Table: sys_user                                              */
/*==============================================================*/
create table sys_user
(
   user_id              int not null auto_increment,
   username             varchar(64),
   password             varchar(64),
   phone                varchar(14),
   email                varchar(64),
   primary key (user_id)
);

alter table answer_paper add constraint FK_Reference_4 foreign key (paper_id)
      references sys_paper (paper_id) on delete restrict on update restrict;

alter table answer_paper_choice add constraint FK_Reference_5 foreign key (choice_id)
      references sys_paper_choice (choice_id) on delete restrict on update restrict;

alter table answer_paper_choice add constraint FK_Reference_6 foreign key (paper_id)
      references sys_paper (paper_id) on delete restrict on update restrict;

alter table answer_question add constraint FK_Reference_3 foreign key (question_id)
      references sys_question (question_id) on delete restrict on update restrict;

alter table sys_paper add constraint FK_Reference_1 foreign key (question_id)
      references sys_question (question_id) on delete restrict on update restrict;

alter table sys_paper_choice add constraint FK_Reference_2 foreign key (paper_id)
      references sys_paper (paper_id) on delete restrict on update restrict;

