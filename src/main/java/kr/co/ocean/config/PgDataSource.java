package kr.co.ocean.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
@MapperScan(value="kr.co.ocean.board.dao, kr.co.ocean.member.dao, kr.co.ocean.jwt.dao")
public class PgDataSource {
	
	@Primary
    @Bean(name = "dataSourcePg")
    @ConfigurationProperties(prefix = "spring.datasource-pg")
    DataSource dataSourcePg() {
		return DataSourceBuilder.create().build();
	}
	
	@Primary
    @Bean(name = "sqlSessionFactory")
    SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setConfigLocation(new PathMatchingResourcePatternResolver().getResource("classpath:mybatis/config/mybatis-config.xml"));
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/sql/pg/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }
	
	@Primary
    @Bean(name = "sqlSession-pg")
    SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
