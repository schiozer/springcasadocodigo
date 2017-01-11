package br.com.casadocodigo.loja.conf;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

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

		return new Class[]{SecurityConfiguration.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		
		return new Class[]{ AppWebConfiguration.class, JPAConfiguration.class};
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] {"/"};
	}
	
	@Override
	protected void customizeRegistration(Dynamic registration) {
		
		registration.setMultipartConfig( new MultipartConfigElement("") );
	}

}
