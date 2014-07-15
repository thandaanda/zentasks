# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table project (
  id                        bigint not null,
  name                      varchar(255),
  folder                    varchar(255),
  constraint pk_project primary key (id))
;

create table task (
  id                        bigint not null,
  title                     varchar(255),
  done                      boolean,
  due_date                  timestamp,
  assigned_to_email         varchar(255),
  folder                    varchar(255),
  project_id                bigint,
  constraint pk_task primary key (id))
;

create table user_details (
  email                     varchar(255) not null,
  name                      varchar(255),
  password                  varchar(255),
  constraint pk_user_details primary key (email))
;


create table project_user_details (
  project_id                     bigint not null,
  user_details_email             varchar(255) not null,
  constraint pk_project_user_details primary key (project_id, user_details_email))
;
create sequence project_seq;

create sequence task_seq;

create sequence user_details_seq;

alter table task add constraint fk_task_assignedTo_1 foreign key (assigned_to_email) references user_details (email);
create index ix_task_assignedTo_1 on task (assigned_to_email);
alter table task add constraint fk_task_project_2 foreign key (project_id) references project (id);
create index ix_task_project_2 on task (project_id);



alter table project_user_details add constraint fk_project_user_details_proje_01 foreign key (project_id) references project (id);

alter table project_user_details add constraint fk_project_user_details_user__02 foreign key (user_details_email) references user_details (email);

# --- !Downs

drop table if exists project cascade;

drop table if exists project_user_details cascade;

drop table if exists task cascade;

drop table if exists user_details cascade;

drop sequence if exists project_seq;

drop sequence if exists task_seq;

drop sequence if exists user_details_seq;

