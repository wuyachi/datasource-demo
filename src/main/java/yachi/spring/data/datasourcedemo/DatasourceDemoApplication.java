package yachi.spring.data.datasourcedemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootApplication
@Slf4j
public class DatasourceDemoApplication implements CommandLineRunner, WebServerFactoryCustomizer<ConfigurableWebServerFactory> {
	@Resource
	private DataSource			dataSource;

	@Resource
	private JdbcTemplate		jdbcTemplate;

	public static void main(String[] args) {
		SpringApplication.run(DatasourceDemoApplication.class, args);
	}

//	@Bean
//	public WebServerFactoryCustomizer<ConfigurableWebServerFactory> webServerFactoryCustomizer(){
//		return new WebServerFactoryCustomizer<ConfigurableWebServerFactory>() {
//			public void customize(ConfigurableWebServerFactory factory) {
//				factory.setPort(8082);
//			}
//		};
//	}

	@Override
	public void customize(ConfigurableWebServerFactory factory) {
		factory.setPort(8082);
	}

	@Override
	public void run(String... args) throws Exception{
		showConnection();
		showData();
	}

	private void showConnection() throws SQLException {
		log.info("dataSource:{}", dataSource.toString());
		Connection conn = dataSource.getConnection();
		log.info("conn:{}", conn.toString());
		conn.close();
	}

	private void showData() {
		jdbcTemplate.queryForList("select * from FOO")
				.forEach(row -> log.info(row.toString()));
	}
}
