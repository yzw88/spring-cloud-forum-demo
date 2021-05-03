package pers.walyex.mybatis.configuration;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class MyConfiguration {

    private String jdbcDriver;
    private String jdbcUrl;
    private String jdbcUsername;
    private String jdbcPassword;

    private Map<String,MyMapperStatement> myMapperStatementMap = new HashMap<>();
}
