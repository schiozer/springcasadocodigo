package br.com.casadocodigo.loja.daos;

import java.math.BigDecimal;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.ServletTestExecutionListener;

import br.com.casadocodigo.loja.builders.ProductBuilder;
import br.com.casadocodigo.loja.conf.DataSourceConfigurationTest;
import br.com.casadocodigo.loja.conf.JPAConfiguration;
import br.com.casadocodigo.loja.conf.SecurityConfiguration;
import br.com.casadocodigo.loja.models.BookType;
import br.com.casadocodigo.loja.models.Product;
/* Ocorrerá um erro de deprecated se usar o import abaixo
 * import junit.framework.Assert; 
 * O correro é o que está sendo mencionado.
 * */
import org.junit.Assert; 

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DataSourceConfigurationTest.class, ProductDAO.class, JPAConfiguration.class})
@ActiveProfiles("test")
public class ProductDAOTest {

	@Autowired
	private ProductDAO productDAO;
	
	@Transactional
	@Test
	public void shouldSumAllPricesOfEachBookPerType() {
	
		List<Product> printedBooks = ProductBuilder.newProduct(BookType.PRINTED, BigDecimal.TEN).more(4).buildAll();
		printedBooks.stream().forEach(productDAO::save);
	
		List<Product> ebooks = ProductBuilder.newProduct(BookType.EBOOK, BigDecimal.TEN).more(4).buildAll();
		ebooks.stream().forEach(productDAO::save);
		
		BigDecimal value = productDAO.sumPricesPerType(BookType.PRINTED);
		
		Assert.assertEquals(new BigDecimal(50).setScale(2), value);
	}
	
	/*
	@Test
	public void shouldSumAllPricesOfEachBookPerType() {
	
		/* Não fique muito assustado com esse código, ele só demonstra um pouco do que acontece por trás dos panos quando você está utilizando o Spring
			MVC. Tivemos que criar o contexto do Spring na mão e também usamos os objetos dele para recuperar os objetos e controlar a transação. 
			O ponto aqui é: já temos muitas preocupações com nossos testes para ainda ter que ficar aprendendo a lidar com os detalhes internos do framework. 
			Para não ficar preso nesse tipo de situação, o Spring criou um módulo focado apenas em ajudar nesses tipos de testes
		 * 
		 * 
		ProductDAO dao;
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(JPAConfiguration.class, ProductDAO.class);
		dao = ctx.getBean(ProductDAO.class);
		
		PlatformTransactionManager txManager = 	ctx.getBean(PlatformTransactionManager.class);
		TransactionStatus transaction = txManager.getTransaction(new DefaultTransactionAttribute());
		//até aqui foi configuração do spring
		
		//salva uma lista de livros impressos
		List<Product> printedBooks = ProductBuilder.newProduct(BookType.PRINTED, BigDecimal.TEN).more(4).buildAll();
		
		//foreach do Java8, fique à vontade para usar um for normal
		printedBooks.stream().forEach(dao::save);
		
		//salva uma lista de ebooks
		List<Product> ebooks = ProductBuilder.newProduct(BookType.EBOOK, BigDecimal.TEN).more(4).buildAll();
		
		//foreach do Java8, fique à vontade para usar um for normal
		ebooks.stream().forEach(dao::save);
		
		BigDecimal value = dao.sumPricesPerType(BookType.PRINTED);
		
		Assert.assertEquals(new BigDecimal(50).setScale(2), value);
		
		txManager.commit(transaction);
		ctx.close();
	}
	*/
}
