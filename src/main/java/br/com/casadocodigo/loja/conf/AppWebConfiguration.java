package br.com.casadocodigo.loja.conf;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.datetime.DateFormatterRegistrar;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import br.com.casadocodigo.loja.controllers.FileSaver;
import br.com.casadocodigo.loja.controllers.HomeController;
import br.com.casadocodigo.loja.daos.ProductDAO;
import br.com.casadocodigo.loja.models.ShoppingCart;

@EnableWebMvc
@ComponentScan( basePackageClasses = { HomeController.class, ProductDAO.class, FileSaver.class, ShoppingCart.class} )
public class AppWebConfiguration extends WebMvcConfigurerAdapter{

	@Bean
	public InternalResourceViewResolver internalResourceViewResolver() {
		
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		return resolver;
	}
	
	/*Um detalhe muito importante, mas não muito claro, é que o nome do método deve ser messageSource. 
	 * O Spring MVC vai procurar por um Bean registrado com esse nome. Uma alternativa, para não ter que 
	 * se preocupar com o nome do método, é utilizar o atributo name da annotation @Bean*/
	@Bean(name="messageSource")
	public MessageSource messageSource() {

		ReloadableResourceBundleMessageSource bundle = new ReloadableResourceBundleMessageSource();
		
		bundle.setBasename("/WEB-INF/message");
		bundle.setDefaultEncoding("UTF-8");
		bundle.setCacheSeconds(1);
		
		return bundle;
	}
	
	/*nome do método tem que ser mvcConversionService, pois esse é o nome usado internamente pelo Spring MVC 
	 * para registrar o objeto responsável por agrupar os conversores.
	 * */
	@Bean
	public FormattingConversionService mvcConversionService() {
		
		DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService(true);
		
		DateFormatterRegistrar registrar = new DateFormatterRegistrar();
		
		registrar.setFormatter(new DateFormatter("yyyy-MM-dd"));
		registrar.registerFormatters(conversionService);
		
		return conversionService;
	}
	
	@Bean
	public MultipartResolver multipartResolver(){
		
		return new StandardServletMultipartResolver();
	}

}
