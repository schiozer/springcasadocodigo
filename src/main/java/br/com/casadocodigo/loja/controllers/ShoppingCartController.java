package br.com.casadocodigo.loja.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.casadocodigo.loja.daos.ProductDAO;
import br.com.casadocodigo.loja.models.BookType;
import br.com.casadocodigo.loja.models.Product;
import br.com.casadocodigo.loja.models.ShoppingCart;
import br.com.casadocodigo.loja.models.ShoppingItem;
import org.springframework.web.context.WebApplicationContext;

/*
 * A regra geral é: injete sempre objetos de escopos maiores em objetos de escopos menores, nunca ao contrário.
 * - Objetos no escopo Application podem ser injetados em qualquer lugar.
 * - Objetos no escopo de Session podem ser injetados em outros do mesmo escopo ou em escopo de Request.
 * */
@Controller
@RequestMapping("/shopping")
@Scope(value=WebApplicationContext.SCOPE_REQUEST)
public class ShoppingCartController {
	
	@Autowired
	private ProductDAO productDAO;
	@Autowired
	private ShoppingCart shoppingCart;

	@RequestMapping(method=RequestMethod.POST)
	public ModelAndView add(Integer productId, BookType bookType) {
	
		ShoppingItem item = createItem(productId, bookType);
		shoppingCart.add(item);
		
		System.out.println("Adicionando ao carrinho productId[" + productId + "] bookType[" + bookType + "]");
		
		return new ModelAndView("redirect:/products");
	}
	
	private ShoppingItem createItem(Integer productId, BookType bookType) {
			
		Product product = productDAO.find(productId);
		ShoppingItem item = new ShoppingItem(product,bookType);
		return item;
	}

	@RequestMapping(method=RequestMethod.GET)
	public String items(){
		return "shoppingCart/items";
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/{productId}")
	public String remove(@PathVariable("productId") Integer productId,BookType bookType){
		shoppingCart.remove(createItem(productId, bookType));
		return "redirect:/shopping";
	}
}
