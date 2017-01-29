package br.com.casadocodigo.loja.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import br.com.casadocodigo.loja.models.User;

public class UserDAO implements UserDetailsService {
	
	@PersistenceContext
	private EntityManager em;
	
	/* realizamos uma query e devemos retornar um usuário encontrado, ou uma exception indicando o contrário. Perceba que o nome do 
	 * método é loadUserByUsername, justamente o que a interface nos obriga implementar. Uma outra questão interessante é que ele não 
	 * recebe a senha como argumento, por que será? Oprincipal motivo é para você não ter que lidar com o processo de aplicar o hash 
	 * na senha antes de fazer a consulta. Você simplesmente busca pelo login e o Spring Security vai verificar se a senha 
	 * cadastrada bate com a senha com que foi enviada pelo formulário.
	 * */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	
		String jpql = "select u from User u where u.login = :login";
	
		List<User> users = em.createQuery(jpql, User.class).setParameter("login", username).getResultList();
		
		if(users.isEmpty()) {
			
			throw new UsernameNotFoundException ("O usuario "+username+" não existe");
		}
		
		return users.get(0);
	}
}
