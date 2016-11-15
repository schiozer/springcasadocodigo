package br.com.casadocodigo.loja.models;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;


@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)

/* Esse proxy mode deve ser usado quando queremos colocar um objeto de escopo menor em um objeto de escopo maior.
 * por exemplo o controller sendo escopo aplicação e esse aqui sessão. Dessa forma, o Spring acaba criando um Proxy usando a lib cglib.
 * gera um proxy de acesso ao ShoppingCart. Eu não vou fazer isso, irei deixar o controller com escopo de requisição que é o que uma aplicação web deve fazer
 * import org.springframework.context.annotation.ScopedProxyMode;
 * , proxyMode = ScopedProxyMode.TARGET_CLASS) */
public class ShoppingCart {

	private Map<ShoppingItem, Integer> items = new LinkedHashMap<ShoppingItem, Integer>();

	public void add(ShoppingItem item) {
		
		System.out.println("Quantidade no carrinho: " + getQuantity());
		
		items.put(item, getQuantity(item) + 1);
	}

	public Integer getQuantity(ShoppingItem item) {
		
		if (!items.containsKey(item))
			items.put(item, 0);
		
		return items.get(item);
	}

	public Integer getQuantity() {
		
		return items.values().stream().reduce(0, (next, accumulator) -> next + accumulator);
	}

	public Collection<ShoppingItem> getList() {
		
		return items.keySet();
	}

	public BigDecimal getTotal(ShoppingItem item) {
		
		return item.getTotal(getQuantity(item));
	}
	
	public BigDecimal getTotal(){
		
		BigDecimal total = BigDecimal.ZERO;
		
		//TODO change to reduce?
		for(ShoppingItem item : items.keySet()) {
			total = total.add(getTotal(item));
		}
		return total;
	}

	public void remove(ShoppingItem shoppingItem) {
		items.remove(shoppingItem);
	}

	public boolean isEmpty() {
		return items.isEmpty();
	}
}