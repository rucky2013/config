package com.lynn.config.api.entity;

import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Created by fancongchun on 2016/1/22.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name="t_system_server_ip_down_log")
public class SystemServerIpDownLog extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private String systemName;
    private String ip;
}
