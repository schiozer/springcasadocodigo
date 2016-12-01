package br.com.casadocodigo.loja.service;

import java.math.BigDecimal;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.async.DeferredResult;
import br.com.casadocodigo.loja.models.PaymentData;

public class IntegrandoComPagamento implements Runnable {

	private DeferredResult<String> result;
	private BigDecimal value;
	private RestTemplate restTemplate;
	
	public IntegrandoComPagamento(DeferredResult<String> result, BigDecimal value, RestTemplate restTemplate) {
		super();
		this.result = result;
		this.value = value;
		this.restTemplate = restTemplate;
	}

	/* O método setResult é parte importante desse trecho de código. Quando você o invoca, o objeto 
	 * do tipo DeferredResult notifica o SpringMVC de que o contexto assíncrono acabou e que ele 
	 * pode gerar o retorno para o usuário da aplicação. O uso do DeferredResult ainda permite um 
	 * controle mais fino da execução assíncrona, disponibilizando alguns outros métodos.
	 * • onTimeout(Runnable callback): registre um objeto que deve ter o método invocado caso expire um determinado tempo.
	 * • onCompletion(Runnable callback): registre um objeto que deve ter o método invocado quando a execução acabar.
	 * */
	@Override
	public void run() {
	
		//String uriToPay = "http://localhost:9000/payment";
		String uriToPay = "http://book-payment.herokuapp.com/payment";
		try {
			
			String response = restTemplate.postForObject(uriToPay, new PaymentData(value), String.class);
			
			System.out.println("Resultado do post: " + response);
			//linha de notificação
			result.setResult("redirect:/payment/success");
			
		} catch (HttpClientErrorException exception) {
			
			result.setResult("redirect:/payment/error");
		}
	}
}
