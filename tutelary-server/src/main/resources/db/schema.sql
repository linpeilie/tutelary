-- 应用表
drop table if exists t_app;

create table t_app
(
    id            int auto_increment
        primary key,
    app_name      varchar(64)   not null comment '应用名称',
    register_date datetime      not null comment '注册时间',
    instance_num  int default 0 not null comment '实例数',
    create_time   datetime      not null comment '创建时间',
    update_time   datetime      not null comment '更新时间',
    constraint t_app_app_name_uindex
        unique (app_name)
);

-- 应用实例表

drop table if exists t_instance;

create table t_instance
(
    id                int auto_increment
        primary key,
    instance_id       varchar(32) not null comment '实例ID',
    app_name          varchar(64) not null comment '应用名称',
    ip                varchar(15) not null comment '实例IP',
    register_date     datetime    not null comment '注册时间',
    state             tinyint     not null comment '状态「1-有效；0-无效」',
    input_arguments   text        not null comment 'JVM输入参数',
    system_properties text        not null comment '系统属性',
    class_path        text        not null comment '类路径',
    library_path      text        not null comment '依赖路径',
    vm_vendor         varchar(64) not null comment 'Java 虚拟机实现供应商',
    vm_name           varchar(64) not null comment 'Java 虚拟机实现名称',
    vm_version        varchar(32) not null comment 'Java 虚拟机实现版本',
    jdk_version       varchar(32) not null comment 'JDK 版本',
    start_time        datetime    not null comment '启动时间',
    create_time       datetime    not null comment '创建时间',
    update_time       datetime    not null comment '更新时间',
    constraint t_instance_instance_id_uindex
        unique (instance_id)
);

-- 应用实例主机信息

drop table if exists t_instance_host;

create table t_instance_host
(
    id                         int auto_increment
        primary key,
    instance_id                varchar(32) not null comment '实例ID',
    host_name                  varchar(32) not null comment '实例host名称',
    host_address               varchar(15) not null comment 'host IP',
    os_name                    varchar(32) not null comment '系统名称',
    arch                       varchar(16) not null comment '系统架构',
    available_processors       int         not null comment 'CPU可用核心数',
    committed_virtual_memory   bigint      not null comment '可用于正在运行的进程的虚拟机内存量「单位：kb」',
    total_physical_memory_size bigint      not null comment '总物理内存量「单位：kb」',
    free_physical_memory_size  bigint      not null comment '空闲物理内存量「单位：kb」',
    total_swap_space_size      bigint      not null comment '总交换分区内存量「单位：kb」',
    free_swap_space_size       bigint      not null comment '空闲交换分区内存量「单位：kb」',
    disk_free_space            bigint      not null comment '空闲硬盘空间「单位：kb」',
    disk_usable_space          bigint      not null comment '可用硬盘空间「单位：kb」',
    disk_total_space           bigint      not null comment '总硬盘空间「单位：kb」',
    report_time                datetime    not null comment '上报时间',
    create_time                datetime    not null comment '创建时间',
    update_time                datetime    not null comment '更新时间'
);

create unique index t_instance_host_instance_id
    on t_instance_host (instance_id);

-- 应用实例线程统计表

drop table if exists t_instance_thread_statistic;

create table t_instance_thread_statistic
(
    id                         int auto_increment
        primary key,
    instance_id                varchar(32) not null comment '实例ID',
    thread_count               int         not null comment '当前线程数量',
    peak_thread_count          int         not null comment '峰值线程数量',
    daemon_thread_count        int         not null comment '非守护线程数量',
    total_started_thread_count int         not null comment '虚拟机启动依赖创建和启动的线程总数',
    report_time                datetime    not null comment '上报时间',
    create_time                datetime    not null comment '创建时间',
    update_time                datetime    not null comment '更新时间'
);

create index t_instance_thread_statistic_instance_id
    on t_instance_thread_statistic (instance_id, report_time);

-- 应用实例 JVM 内存信息表

drop table if exists t_instance_jvm_memory;

create table t_instance_jvm_memory
(
    id          int auto_increment
        primary key,
    instance_id varchar(32) not null comment '实例ID',
    type        varchar(8)  not null comment '内存类型「HEAP、NON_HEAP」',
    name        varchar(64) not null comment '内存区域名称',
    used        int         not null comment '已使用内存「单位：kb」',
    committed   int         not null comment '已提交内存「单位：kb」',
    max         int         not null comment '最大内存「单位：kb」',
    report_time datetime    not null comment '上报时间',
    create_time datetime    not null comment '创建时间',
    update_time datetime    not null comment '更新时间'
);

create index t_instance_jvm_memory_instance_id
    on t_instance_jvm_memory (instance_id, report_time);

-- 应用实例内存回收信息表

drop table if exists t_instance_garbage_collectors;

create table t_instance_garbage_collectors
(
    id                int auto_increment
        primary key,
    instance_id       varchar(32)  not null comment '实例ID',
    name              varchar(64)  not null comment '垃圾收集器',
    collection_count  int          not null comment '垃圾手机次数',
    collection_time   int          not null comment '垃圾手机耗时',
    memory_pool_names varchar(512) not null comment '收集内存区域',
    report_time       datetime     not null comment '上报时间',
    create_time       datetime     not null comment '创建时间',
    update_time       datetime     not null comment '更新时间'
);

create index t_instance_garbage_collectors_instance_id
    on t_instance_garbage_collectors (instance_id, report_time);