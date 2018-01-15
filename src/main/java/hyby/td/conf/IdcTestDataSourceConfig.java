package hyby.td.conf;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * provider 数据源配置
 * Created by 11019 on 17.10.26.
 */
@Configuration
// 扫描 Mapper 接口并容器管理
@MapperScan(basePackages = IdcTestDataSourceConfig.PACKAGE,sqlSessionFactoryRef = "idcTestSqlSessionFactory")
public class IdcTestDataSourceConfig {

    // 精确到 mapper.idcDataSource 目录，以便跟其他数据源隔离
    static final String PACKAGE = "hyby.td.dao.idcTestDataSource";
    static final String MAPPER_LOCATION = "classpath:mapper/idcTestDataSource/*.xml";

    @Value("${mysqlIdcTest.driver}")
    private String driverClass;

    @Value("${mysqlIdcTest.url}")
    private String url;

    @Value("${mysqlIdcTest.name}")
    private String username;

    @Value("${mysqlIdcTest.password}")
    private String password;

    @SuppressWarnings("all")
    @Bean(name = "mapper/idcTestDataSource")
    public DataSource idcTestDataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClass);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setTestWhileIdle(false);
        return dataSource;
    }

    @Bean(name = "idcTestTransactionManager")

    public DataSourceTransactionManager idcTestTransactionManager(){
        return new DataSourceTransactionManager(idcTestDataSource());
    }

    @Bean(name = "idcTestSqlSessionFactory")

    public SqlSessionFactory idcTestSqlSessionFactory(@Qualifier("mapper/idcTestDataSource") DataSource idcTestDataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(idcTestDataSource);
        sessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(IdcTestDataSourceConfig.MAPPER_LOCATION));
        return sessionFactoryBean.getObject();
    }

}
