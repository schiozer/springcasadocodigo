package br.com.casadocodigo.loja.controllers;

import java.math.BigDecimal;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.async.DeferredResult;

import br.com.casadocodigo.loja.models.ShoppingCart;
import br.com.casadocodigo.loja.service.IntegrandoComPagamento;

/*
import java.util.concurrent.Callable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.client.HttpClientErrorException;
import br.com.casadocodigo.loja.models.BookType;
import br.com.casadocodigo.loja.models.PaymentData;
import br.com.casadocodigo.loja.models.Product;
*/

@Controller
@RequestMapping("/payment")
@Scope(value=WebApplicationContext.SCOPE_REQUEST)
public class PaymentController {

	@Autowired
	private ShoppingCart shoppingCart;
	@Autowired
	private RestTemplate restTemplate;

	/* Basta que retornemos um objeto do tipo Callable, que existe desde o Java 5 e é um análogo ao 
	 * objeto tipo Runnable, muito comum para quem usaThreads. A única diferença do Callable é que ele 
	 * nos permite dar um retorno, algo necessário para nós, já que precisamos informar para qual endereço
	 * vamos depois da integração. Só de você retornar um Callable, o Spring MVC já vai criar iniciar 
	 * um contexto assíncrono em sua Servlet e liberar para que o Tomcat possa usar a Thread dele 
	 * para atender novas requisições.
	 */
	/*
	@RequestMapping(value="checkout",method=RequestMethod.POST)
	public Callable<String> checkout() {
		
		return () -> {
		
			BigDecimal total = shoppingCart.getTotal();
			System.out.println("Total do checkout: " + total);
			
			String uriToPay = "http://book-payment.herokuapp.com/payment";
			
			try {
				
				String response = restTemplate.postForObject(uriToPay, new PaymentData(total), String.class);
				
				return "redirect:/payment/success";
			
			} catch (HttpClientErrorException exception) {
				
				return "redirect:/payment/error";
			}

		};
	}
	*/

	/* O código acima serve para executarmos coisas assíncronamente e deixar que o Spring controle 
	 * todo o fluxo da Thread. Mas e se quisermos controlar a thread ? ou até mesmo roda o código
	 * em uma fila de processamento, algo bastante comum.
	 * Nesse caso utilizamos o código abaixo usando DefferedResult que, em uma tradução livre, 
	 * seria algo parecido como retorno postergado*/
	
	@RequestMapping(value = "checkout", method = RequestMethod.POST)
	public DeferredResult<String> checkout() {
		
		BigDecimal total = shoppingCart.getTotal();
		DeferredResult<String> result = new DeferredResult<String>();
		
		IntegrandoComPagamento integrandoComPagamento = new IntegrandoComPagamento(result, total, restTemplate);
		
		Thread thread = new Thread(integrandoComPagamento);
		thread.start();
		
		return result;
	}
	
	@RequestMapping("/success")
	public String success() {

		return "payment/success";
	}

	
}