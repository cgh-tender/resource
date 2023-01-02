create table log_cgh
(
    ID          bigint auto_increment primary key,
    IP   varchar(32) not null ,
    TARGET_SERVER varchar(256) not null ,
    PATH varchar(256) not null ,
    METHOD varchar(32) not null ,
    MODE varchar(32) ,
    REQUEST_CONTENT_TYPE varchar(64),
    QUERY_PARAMS text ,
    REQUEST_BODY text ,
    CODE int,
    REQUEST_TIME     timestamp  ,
    EXECUTE_TIME long
);