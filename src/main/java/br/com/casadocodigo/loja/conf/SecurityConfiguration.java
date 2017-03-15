package br.com.casadocodigo.loja.conf;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/*
 * Aqui herdamos da classe WebSecurityConfigurerAdapter, que já fornece toda a infraestrutura pronta para começarmos a fazer nossas
 * configurações de segurança. Além disso, utilizamos a annotation @EnableWebSecurity, que deve ser colocada em cima das classes 
 * de configuração do Spring Security responsáveis por efetivamente controlar as regras de acesso. Essa annotation faz com que 
 * outros componentes sejam carregados. Seguem alguns exemplos.
 * */

/* Asigla CSRF significa Cross-Site Request Forgery e éumtipo de ataque que pode ser feito contra sua aplicação. 
 * A ideia basicamente é que dados possam ser enviados para a nossa aplicação sendo provenientes de uma outra página qualquer, aberta no seu navegador.
 * o Spring Security já provê a implementação para a proteção contra o CSRF.
 * A variável de nome _csrf contém a referência para um objeto do tipo DefaultCsrfToken. Isso nos form HTML
 * 
 * Adicionando a annotation @EnableWebMvcSecurity, iremos prover a conf necessário para lidar com csrf 
 * Porem essa anottation está deprecated 
 * */
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsService users;
	
	/* Além disso, ainda forçamos que a senha sejamantida utilizandoumalgoritmo de hash; escolhemos o BCrypt. 
	 * Poderiamexistir vários capítulos somente dedicados a algoritmos de hash, já que estes formam uma vasta área de estudo.
	 * Aqui no livro, vamos nos restringir somente a usar o BCrypt, que a própria documentação do Spring Security estimula que seja usado.
	 * */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	
		auth.userDetailsService(users);
		//.passwordEncoder(new BCryptPasswordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests()
			.antMatchers("/products/form").hasRole("ADMIN")
			.antMatchers("/shopping/**").permitAll()
			.antMatchers(HttpMethod.POST,"/products").hasRole("ADMIN")
			.antMatchers("/products/**").permitAll()
			.anyRequest().authenticated()
			.and().formLogin().loginPage("/login").permitAll()
			.and()
			.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
			/*Devemos invocar o método permitAll para informar que esse endereço está liberado para todos os usuários*/
	}
	
	/*
	 * Se tivermos um js na pasta resources e eles precisar ser acessado, passará pelo security da mesma forma, solicitando
	 * autenticação do usuário, isso não faz muito sentido. Sendo assim, é preciso ignorar os itens estáticos de nossas página
	 * como conteúdo de código js
	 * 
	 * Ao contrário do primeiro configure, que recebe como argumento um objeto do tipo HttpSecurity, esse recebeum WebSecurity. Oprimeiro é utilizado para fazer a 
	 * configuração geral. Como vimos, ele fazmuitomais coisas do que apenas permitir o acesso a algumas URLs. Este que estamos vendo agora é específico para a 
	 * aplicação web. Por exemplo, estamos pedindo para o Spring Security ignorar qualquer acesso a URL que comece com resources 
	 * 
	 * Agora, quando você tentar acessa amesma URL, o servidor vai retornar o status 404. Oproblema é que a Servlet do SpringMVC está achando que você
	 * está acessando um endereço mapeado para um controller, o que claramente não é o caso. Para resolver isso, vamos sobrescrever mais um método, agora
	 * na nossa classe AppWebConfiguration.
	 * 	 * */
	@Override
	public void configure(WebSecurity web) throws Exception {
	
		web.ignoring().antMatchers("/resources/**");
		
	}

}
