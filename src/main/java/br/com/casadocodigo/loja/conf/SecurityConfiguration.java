package br.com.casadocodigo.loja.conf;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


/*
 * Aqui herdamos da classe WebSecurityConfigurerAdapter, que já fornece toda a infraestrutura pronta para começarmos a fazer nossas
 * configurações de segurança. Além disso, utilizamos a annotation @EnableWebSecurity, que deve ser colocada em cima das classes 
 * de configuração do Spring Security responsáveis por efetivamente controlar as regras de acesso. Essa annotation faz com que 
 * outros componentes sejam carregados. Seguem alguns exemplos.
 * */
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

}
