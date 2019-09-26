package com.ant.sso.Config.data;

import com.ant.sso.Utils.StringUtils;
import lombok.extern.log4j.Log4j2;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class MySQLUpperCaseStrategy extends PhysicalNamingStrategyStandardImpl {
    private static final long serialVersionUID = 1383021413247872469L;
    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
        // 将表名首字母转换成大写
        String tableName = StringUtils.UpFirst(name.getText());
        return name.toIdentifier(tableName);
    }
}
