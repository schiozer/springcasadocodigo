package br.com.casadocodigo.loja.controllers;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.casadocodigo.loja.daos.ProductDAO;
import br.com.casadocodigo.loja.models.BookType;
import br.com.casadocodigo.loja.models.Product;

@Controller
@Transactional
@RequestMapping("/products")
@Scope(value=WebApplicationContext.SCOPE_REQUEST)
public class ProductsController {

	@Autowired
	private ProductDAO productDAO;
	@Autowired
	private FileSaver fileSaver;
	/*
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(new ProductValidator());
	}
	*/
	
	@RequestMapping(method=RequestMethod.POST, name="saveProduct")
	/*Esse annottation atualiza o cache de nome books. Na verdade ele invalida o cache, retirando os valores.
	 * allEntries para indicar que queremos que todos os valores sejam retirados.
	 * dessa forma, na próxima vez que o list for executado, não haverá cache e ele será construído novamente*/
	@CacheEvict(value="books", allEntries=true)
	public ModelAndView save(MultipartFile summary, @Valid Product product, BindingResult bindingResult, RedirectAttributes redirectAttributes){
		
		if(bindingResult.hasErrors()) {
			return form(product);
		}
		
		if (!summary.getOriginalFilename().isEmpty() && !summary.getOriginalFilename().equals(null)) {
			
			System.out.println("Salvando arquivo " + summary.getOriginalFilename());
			String webPath = fileSaver.write("uploaded-images",summary);
			product.setSummaryPath(webPath);
		} else {
			System.out.println("Não vou salvar nenhum arquivo");	
		}
		
		productDAO.save(product);
		System.out.println("Cadastrando o produto "+product);
		
		/*o correto era mostrar a listagem novamente, ou seja, retornar ao padrão do GET
		 * para isso eu poderia simplesmente chamar o return list(); e então retornar o list
		 * Só que eu estaria forçando sem o browser saber, tanto que o endereço na barra de título continua ref ao POST. 
		 * O correto, é deixar o browser realizar essa atividade, com isso fazemos um redirect
		 * O prefixo redirect: indica para o Spring MVC que, em vez de simplesmente fazer um forward, 
		 * é necessário que ele retorne o status 302 para navegador, 
		 * solicitando que omesmo faça um novo request para o novo endereço.
		 * */
		/* O problema do apprach abaixo é que essa informação vai aparecer na URL e com isso só pode ser string,
		 * fora o receio do usuário marretar a informaçaõ na marra. 
		 * Uma alternativa seria guardar na sessão, mas aí o dado duraria toda a sessão do usuário.
		 * Com isso existe um novo elemento no framework chamado Flash.
		 * */
		//ModelAndView modelAndView = new ModelAndView("redirect:products");
		//modelAndView.addObject("sucesso", "Produto cadastrado com sucesso");
		redirectAttributes.addFlashAttribute("sucesso", "Produto cadastrado com sucesso");
		return new ModelAndView("redirect:products");
	}
	
	/*
	@RequestMapping("/products/form")
		public String form(){
		return "products/form";
	}
	*/
	
	@RequestMapping("/form")
	public ModelAndView form(Product product) {

		ModelAndView modelAndView = new ModelAndView("products/form");
		modelAndView.addObject("types", BookType.values());
		return modelAndView;
	}
	
	@RequestMapping(method=RequestMethod.GET)
	@Cacheable(value="books")
	/*
	 * Caso você queira forçar o retorno em JSON pelo navegador, basta que acesse a URL com a extensão .json. 
	 * Por exemplo, http://localhost:8080/casadocodigo/products.json.
	 */
	public ModelAndView list() {
		
		System.out.println("Carregando os produtos");
		
		ModelAndView modelAndView = new ModelAndView("products/list");
		modelAndView.addObject("products", productDAO.list());
		
		return modelAndView;
	}

	/*
	 * http://localhost:8080/casadocodigo/products/json
	 * nesse cenário, eu utilizei a contrução de URL dinâminca para gerar o JSON
	 * 
	 * */
	@RequestMapping(method = RequestMethod.GET, value="json")
	@ResponseBody
	public List<Product> listJson() {
		
		System.out.println("Carregando os produtos JSON");

		return productDAO.list();
	}
	
	
	/*
	 * O endereço que aparece é o casadocodigo/produtos/show?id=2.
	 *	Na verdade, essa é a maneira padrão de passar parâmetros via get só que, se
	 *  olharmos para omesmo endereço no site da Casa do Código, veremos que ele
	 *  possui uma estrutura um pouco diferente, http://www.casadocodigo.com.br/products/livro-apis-java.
	 *  O trecho livro-apis-java, funciona basicamente como um id dentro do sistema da Casa do Código. Ao contrário da nossa implementação,
	 *  esse parâmetro vai como parte da própria URL, técnica conhecida como URI Template.
	 *  E qual o motivo de passarmos o parâmetro de uma maneira diferente? Os motores de busca, tipo o Google, preferem endereços que, se acessados,
	 *  sempre retornem o mesmo resultado. E esta é a diferença: quando usamos a ? estamos dizendo que basta uma mudança no valor do parâmetro que o
	 *  resultado da páginamuda e, quando usamos o parâmetro como parte da URI, isso passa a ideia de que aquele é um endereço fixo.
	 * Sendo assim, esse era o código e agora abaixo vamos atualizar*/
	/*
	@RequestMapping(method=RequestMethod.GET,value="/show", name="productShow")
	public ModelAndView show(Integer id) {
		
		ModelAndView modelAndView = new ModelAndView("products/show");
		Product product = productDAO.find(id);
		modelAndView.addObject("product", product);
		
		return modelAndView;
	}
	*/
	@RequestMapping(method=RequestMethod.GET, value="/{id}", name="productShow")
	public ModelAndView show(@PathVariable("id") Integer id) {
		
		ModelAndView modelAndView = new ModelAndView("products/show");
		Product product = productDAO.find(id);
		modelAndView.addObject("product", product);
		
		return modelAndView;
	}
}
