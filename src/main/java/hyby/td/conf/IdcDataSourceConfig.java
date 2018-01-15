package hyby.td.conf;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * provider 数据源配置
 * Created by 11019 on 17.10.26.
 */
@Configuration
// 扫描 Mapper 接口并容器管理
@MapperScan(basePackages = IdcDataSourceConfig.PACKAGE,sqlSessionFactoryRef = "idcSqlSessionFactory")
public class IdcDataSourceConfig {

    // 精确到 mapper.idcDataSource 目录，以便跟其他数据源隔离
    static final String PACKAGE = "hyby.td.dao.idcDataSource";
    static final String MAPPER_LOCATION = "classpath:mapper/idcDataSource/*.xml";

    @Value("${mysqlIdc.driver}")
    private String driverClass;

    @Value("${mysqlIdc.url}")
    private String url;

    @Value("${mysqlIdc.name}")
    private String username;

    @Value("${mysqlIdc.password}")
    private String password;

    @SuppressWarnings("all")
    @Bean(name = "mapper/idcDataSource")
    @Primary
    public DataSource idcDataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClass);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setTestWhileIdle(false);
        return dataSource;
    }

    @Bean(name = "idcTransactionManager")
    @Primary
    public DataSourceTransactionManager idcTransactionManager(){
        return new DataSourceTransactionManager(idcDataSource());
    }

    @Bean(name = "idcSqlSessionFactory")
    @Primary
    public SqlSessionFactory idcSqlSessionFactory(@Qualifier("mapper/idcDataSource") DataSource idcDataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(idcDataSource);
        sessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(IdcDataSourceConfig.MAPPER_LOCATION));
        return sessionFactoryBean.getObject();
    }
}
