package br.com.casadocodigo.loja.controllers;

import javax.transaction.Transactional;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Transactional
@RequestMapping("/login")
@Scope(value=WebApplicationContext.SCOPE_REQUEST)
public class LoginController {
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView list() {
		
		System.out.println("Entrando na página de Login");
		
		ModelAndView modelAndView = new ModelAndView("login");
		
		return modelAndView;
	}
}
