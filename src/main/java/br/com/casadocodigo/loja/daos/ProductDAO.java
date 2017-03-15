package br.com.casadocodigo.loja.daos;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import br.com.casadocodigo.loja.models.BookType;
import br.com.casadocodigo.loja.models.Product;

@Repository
public class ProductDAO {

	@PersistenceContext
	private EntityManager manager;
	
	public void save(Product product){
		manager.persist(product);
	}
	
	/*
	 * Explicação do joing fetch
	 * 
	 * http://www.objectdb.com/java/jpa/query/jpql/from
	 * 
	 * O joing fetch faz o early load
	 * */
	public List<Product> list() {
		return manager.createQuery("select distinct(p) from Product p join fetch p.prices", Product.class).getResultList();
	}

	/*
	 * Para testar o lazy load que o Spring tem suporte. Ver getServletFilters em ServlerSpringMVC
	 * */
	public List<Product> list2() {
		return manager.createQuery("select distinct(p) from Product p", Product.class).getResultList();
	}

	public Product find(Integer id){
		return manager.find(Product.class, id);
	}
	
	/*
	 * Testes de integração no DAO
	 * 
	 * Geralmente, os códigos que necessitam de alguma infraestrutura, por exemplo do acesso ao banco de dados, exigem que você 
	 * faça bastante configuração e simule cenários, dos quais o seu framework já cuida para você. Para esses tipos de testes, 
	 * o Spring pode lhe ajudar muito.
	 * */
	
	public BigDecimal sumPricesPerType(BookType bookType) {
		
		TypedQuery<BigDecimal> query = manager.createQuery ("select sum(price.value) from Product p join p.prices price "
															+ "where price.bookType =:bookType", BigDecimal.class);
		
		query.setParameter("bookType", bookType);
		
		return query.getSingleResult();
	}
}
