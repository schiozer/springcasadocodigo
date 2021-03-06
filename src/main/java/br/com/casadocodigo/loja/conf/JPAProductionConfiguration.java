package br.com.casadocodigo.loja.conf;

import java.net.URI;
import java.net.URISyntaxException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class JPAProductionConfiguration {
	
	/*Podemos acessar qualquer variável de ambiente através da classe Environment, já disponibilizada pelo próprio Spring.*/
	@Autowired
	private Environment environment;
	
	@Bean
	@Profile("prod")
	public DataSource dataSource() throws URISyntaxException{
		
		System.out.println("Pegando Data Source de PROD");
		
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		
		dataSource.setDriverClassName("org.postgresql.Driver");
		
		URI dbUrl = new URI(environment.getProperty("DATABASE_URL"));

		System.out.println("Segue URL" + dbUrl.toString());
		
		dataSource.setUrl("jdbc:postgresql://" + dbUrl.getHost() + ":" + dbUrl.getPort() + dbUrl.getPath());
		
		dataSource.setUsername(dbUrl.getUserInfo().split(":")[0]);
		dataSource.setPassword(dbUrl.getUserInfo().split(":")[1]);
		
		System.out.println("Pegando Data Source de PROD");

		return dataSource;
	}

}
