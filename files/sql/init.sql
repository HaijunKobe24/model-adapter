create table tab_book
(
    id       varchar(64)                           not null comment 'ID',
    ref_id   varchar(64)                           not null comment '引用的教材ID，即官方教材ID',
    creator  varchar(64) default ''                not null comment '创建者',
    modifier varchar(64) default ''                not null comment '最后修改人',
    created  datetime    default CURRENT_TIMESTAMP not null comment '创建时间',
    modified datetime    default CURRENT_TIMESTAMP not null comment '修改时间',
    primary key (id)
) comment '教材信息表';
create index idx_ref_id on tab_book (ref_id);



create table tab_book_unit
(
    id       varchar(64)                           not null comment 'ID',
    book_id  varchar(64)                           not null comment '教材ID',
    status   tinyint     default 0                 not null comment '状态：0、草稿，1、待发布，2、已发布',
    sort     smallint    default 0                 not null comment '序号',
    creator  varchar(64) default ''                not null comment '创建者',
    modifier varchar(64) default ''                not null comment '最后修改人',
    created  datetime    default CURRENT_TIMESTAMP not null comment '创建时间',
    modified datetime    default CURRENT_TIMESTAMP not null comment '修改时间',
    primary key (id)
) comment '教材单元表';
create index idx_book_id on tab_book_unit (book_id);