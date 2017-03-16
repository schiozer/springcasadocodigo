package br.com.casadocodigo.loja.conf;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.context.request.RequestContextListener;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import br.com.casadocodigo.loja.daos.UserDAO;

public class ServletSpringMVC extends AbstractAnnotationConfigDispatcherServletInitializer {
	
	
	/*
	 * Antes tínhamos usado ométodo getServletConfigClasses e agora fomos obrigados a usar ométodo getRootConfigClasses. 
	 * O problema é que o filtro do Spring Security é carregado antes do Servlet do Spring MVC, logo, precisamos que os objetos 
	 * de configuração relativos a ele estejamdisponíveis antes. É justamente para isso que serve o getRootConfigClasses, ele faz com que 
	 * as classes sejam lidas e carregadas dentro de um Listener que é lido quando o servidor sobe. No caso do Spring MVC, essa classe é
	 * a ContextLoaderListener.
	 */
	@Override
	protected Class<?>[] getRootConfigClasses() {

		return new Class[]{SecurityConfiguration.class, AppWebConfiguration.class, JPAConfiguration.class, JPAProductionConfiguration.class, UserDAO.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		
		return new Class[]{};
	}

	/*Specify the servlet mapping(s) for the DispatcherServlet — for example "/", "/app", etc.*/
	@Override
	protected String[] getServletMappings() {
		return new String[] {"/"};
	}
	
	@Override
	protected void customizeRegistration(Dynamic registration) {
		
		registration.setMultipartConfig( new MultipartConfigElement("") );
	}

	/* Como não definimos nenhum profile nela, o Datasource não consegue ser encontrado. Para consertamos isso, podemos configurar o profile
	 * através de um parâmetro que deve ser lido no inicio do servidor. Podemos fazê-lo na classe ServletSpringMVC.
	 * Código inserido para podermos descobrir qual datasource deve ser utilizado em aplicações web
	 */
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
	
		super.onStartup(servletContext);
		servletContext.addListener(RequestContextListener.class);
		servletContext.setInitParameter("spring.profiles.active", "prod");
	}
	/*
	 * Nas consultas early load para evitar o lazy usamos fetch join. Se você nao usar, vai receber a seguinte exception:
	 * 		org.hibernate.LazyInitializationException: failed to lazily initialize a collection of role:
	 * 		br.com.casadocodigo.loja.models.Product.prices
	 * O exemplo do prices, 
	 * 
	 * Quando você carrega seus objetos, ele substitui algumas propriedades para permitir que você programe demaneira orientada a objetos. 
	 * Por exemplo basta invocar o método getPrices() que ele vai tentar disparar uma query no banco. O ponto ruim é que você precisa 
	 * entender um pouco mais dele, para conseguir tirar proveito dessa funcionalidade. Basicamente, 
	 * o EntityManager tem que ficar aberto durante todo o ciclo da requisição, inclusive nomomento em que você está montando a JSP.
	 * 
	 * Para facilitar nossa vida, o módulo do Spring de integração com a JPA e Hibernate já fornece um filtro, da API de Servlets
	 * */
	@Override
	protected Filter[] getServletFilters() {
	
		return new Filter[]{ new OpenEntityManagerInViewFilter() };
	}
}
