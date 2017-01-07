package br.com.casadocodigo.loja.viewresolver;

import java.util.Locale;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

public class JsonViewResolver implements ViewResolver {

	@Override
	public View resolveViewName(String viewName, Locale locale) throws Exception {
		
		/* O importante é que devemos retornar um objeto do tipo View, que efetivamente será responsável por escrever 
		 * a resposta no cliente. No nosso caso, estamos aproveitando a classe MappingJackson2JsonView, que já está disponível 
		 * no projeto, desde que adicionamos as dependências do Jackson, no capítulo anterior.
		 * 
		 * Existe um post no blog do Spring sobre esse assunto;
		 * http://spring.io/blog/2013/06/03/content-negotiation-using-views/
		 * */
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		
		view.setPrettyPrint(true);
		//define a chave
		view.setModelKey("products");
		return view;
	}

}
