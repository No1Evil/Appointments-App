package dev.tsumakov.appointments;

import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.EachBean;
import io.micronaut.context.annotation.Factory;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Factory
public class JdbcTemplateFactory {

  @EachBean(DataSource.class)
  @Bean
  public JdbcTemplate createJdbcTemplate(DataSource dataSource) {
    return new JdbcTemplate(dataSource);
  }

  @EachBean(JdbcTemplate.class)
  @Bean
  public DataSourceTransactionManager createDatasourceTransactionManager(JdbcTemplate jdbcTemplate) {
    return new DataSourceTransactionManager(jdbcTemplate.getDataSource());
  }
}
