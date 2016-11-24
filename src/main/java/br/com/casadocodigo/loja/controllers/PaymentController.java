package br.com.casadocodigo.loja.controllers;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import br.com.casadocodigo.loja.models.BookType;
import br.com.casadocodigo.loja.models.PaymentData;
import br.com.casadocodigo.loja.models.Product;
import br.com.casadocodigo.loja.models.ShoppingCart;

@Controller
@RequestMapping("/payment")
@Scope(value=WebApplicationContext.SCOPE_REQUEST)
public class PaymentController {

	@Autowired
	private ShoppingCart shoppingCart;
	@Autowired
	private RestTemplate restTemplate;
	
	@RequestMapping(value="checkout",method=RequestMethod.POST)
	public String checkout() {
		BigDecimal total = shoppingCart.getTotal();
		System.out.println("Total do checkout: " + total);
		
		String uriToPay = "http://book-payment.herokuapp.com/payment";
		
		try {
			
			String response = restTemplate.postForObject(uriToPay, new PaymentData(total), String.class);
			
			return "redirect:/payment/success";
		
		} catch (HttpClientErrorException exception) {
			
			return "redirect:/payment/error";
		}
	}
	
	@RequestMapping("/success")
	public String success() {

		return "payment/success";
	}

	
}