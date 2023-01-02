package cn.com.cgh.gateway.logs.pojo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * create table log_cgh
 * (
 *     ID          bigint auto_increment primary key,
 *     IP   varchar(32) not null ,
 *     TARGET_SERVER varchar(256) not null ,
 *     PATH varchar(256) not null ,
 *     METHOD varchar(32) not null ,
 *     MODE varchar(32) ,
 *     REQUEST_CONTENT_TYPE varchar(64),
 *     QUERY_PARAMS text ,
 *     REQUEST_BODY text ,
 *     CODE int,
 *     REQUEST_TIME     timestamp  ,
 *     EXECUTE_TIME long
 * );
 */
@Getter
@Setter
@ToString
@Builder
public class LogCgh {
private Long id;
private String ip;
private String targetServer;
private String path;
private String method;
private String mode;
private String requestContentType;
private String queryParams;
private String requestBody;
private Integer code;
private Date requestTime;
private Long executeTime;
}
