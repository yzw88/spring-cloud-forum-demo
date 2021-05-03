package pers.walyex.mybatis.configuration;

import lombok.Data;

/**
 * mapper xml中的sql配置信息加载到这个类中
 *
 * @author Waldron Ye
 * @date 2021/3/14 11:54
 */
@Data
public class MyMapperStatement {

    private String namespace;
    private String id;
    private String resultType;
    private String sql;
}
