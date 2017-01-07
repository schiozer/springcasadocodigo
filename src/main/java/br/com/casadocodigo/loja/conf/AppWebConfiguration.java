package br.com.casadocodigo.loja.conf;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.datetime.DateFormatterRegistrar;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import br.com.casadocodigo.loja.controllers.FileSaver;
import br.com.casadocodigo.loja.controllers.HomeController;
import br.com.casadocodigo.loja.daos.ProductDAO;
import br.com.casadocodigo.loja.models.ShoppingCart;
import br.com.casadocodigo.loja.viewresolver.JsonViewResolver;

@EnableWebMvc
@ComponentScan( basePackageClasses = { HomeController.class, ProductDAO.class, FileSaver.class, ShoppingCart.class} )
@EnableCaching
public class AppWebConfiguration extends WebMvcConfigurerAdapter{

	@Bean
	public InternalResourceViewResolver internalResourceViewResolver() {
		
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		/* Podemos ensinar ao SpringMVCque queremos que todos os nossos objetos
		* gerenciados fiquem disponíveis para uso, através da Expression Language. */
		//resolver.setExposeContextBeansAsAttributes(true);
		/*Ou então para não liberar todo mundo, damos acesso apenas aqueles que queremos.*/
		resolver.setExposedContextBeanNames("shoppingCart");
		return resolver;
	}
	
	/*
	 * A referência à interface CacheManager é importante, pois sempre que quisermos usar uma implementação que faça o papel do cache
	 * para a gente, teremos que buscar classes que implementem essa interface.
	 */
	/* E quando quisermos um cache mais encarnado !?!?!?!?!?! que podemos dizer tempo, limite de objetos, etc ...
	 * Podemos utilizar a Guava, uma biblioteca criada pelo Google com várias classes que podem ser úteis em qualquer
	 * projeto. Siga este link: https://code.google.com/p/guava-libraries/wiki/GuavaExplained para mais detalhes sobre o que é oferecido.
	 * 
	 * Além das opções citadas aqui no livro, o Spring também suporta outros provedores de cache, já prontos.
	 * 		• EhCache
	 * 		• GemFire
	 * 		• JSR-107, especificação do Java para padronizar as APIs de cache.
	 * */
	@Bean
	public CacheManager cacheManager(){

		/*
		CacheBuilder<Object, Object> builder = CacheBuilder.newBuilder().maximumSize(100).expireAfterAccess(5, TimeUnit.MINUTES);
		GuavaCacheManager cacheManager = new GuavaCacheManager();
		cacheManager.setCacheBuilder(builder);
		*/
		
		return new ConcurrentMapCacheManager();
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
	
	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}
	
	
	/*
	 * No primeiro exemplo, fizemos uma requisição indicando que desejamos o retorno no formato application/json. 
	 * Isso é feito através do cabeçalho Accept. Perceba que no segundo já indicamos que queremos HTML como formato. 
	 * Essa técnica é conhecida como Content Negotiation e é muito utilizada em integrações baseadas no HTTP, também conhecida como REST.
	 * A parte bem legal é que o Spring MVC já oferece esse suporte para nós. Precisamos apenas ensiná-lo que agora ele tem que 
	 * decidir qual formato retornar baseado no Accept.
	 * 
	 * testando interfaces:
	 * curl -H "Accept:application/json" -X GET "http://localhost:8080/casadocodigo/products"
	 * curl -H "Accept:text/html" -X GET "http://localhost:8080/casadocodigo/products"
	 * */
	@Bean
	public ViewResolver contentNegotiatingViewResolver(ContentNegotiationManager manager) {
			
		List<ViewResolver> resolvers = new ArrayList<ViewResolver>();
		
		resolvers.add(internalResourceViewResolver());
		resolvers.add(new JsonViewResolver());
		
		ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
		
		resolver.setViewResolvers(resolvers);
		resolver.setContentNegotiationManager(manager);
		
		return resolver;
	}

}
