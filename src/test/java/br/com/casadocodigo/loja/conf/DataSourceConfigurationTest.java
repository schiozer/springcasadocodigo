package br.com.casadocodigo.loja.conf;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/*
 * O que queremos é configurar que o Datasource de testes deva ser carregado em um contexto de testes enquanto o outro deve ser carregado 
 * em ambiente de desenvolvimento. Para fazer isso, podemos usar o recurso de Profiles do Spring
 * */
public class DataSourceConfigurationTest {

	@Bean
	@Profile("test") 
	public DataSource dataSource(){
	
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/casadocodigo_test");
		dataSource.setUsername("root");
		dataSource.setPassword("");
		
		return dataSource;
	}
}
