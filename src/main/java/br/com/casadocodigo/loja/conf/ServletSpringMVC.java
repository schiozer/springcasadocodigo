package br.com.casadocodigo.loja.conf;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.context.request.RequestContextListener;
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

		return new Class[]{SecurityConfiguration.class, AppWebConfiguration.class, JPAConfiguration.class, UserDAO.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		
		return new Class[]{};
	}

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
		servletContext.setInitParameter("spring.profiles.active", "dev");
	}
}
