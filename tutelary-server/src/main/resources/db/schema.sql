drop table if exists t_app;

create table t_app
(
    id            int auto_increment
        primary key,
    app_name      varchar(64) not null,
    register_date datetime    not null,
    instance_num  int         not null default 0,
    constraint t_app_app_name_uindex
        unique (app_name)
);

drop table if exists t_instance;

create table t_instance
(
    id            int auto_increment
        primary key,
    instance_id   varchar(32) not null,
    app_name      varchar(64) not null,
    ip            varchar(15) not null,
    register_date datetime    not null,
    constraint t_instance_instance_id_uindex
        unique (instance_id)
);

