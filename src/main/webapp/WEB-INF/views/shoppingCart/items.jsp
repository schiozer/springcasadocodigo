<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="customTags"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<fmt:message key="shoppingCart.title" var="title"/>
<customTags:page bodyClass="cart" title="${title}">

<jsp:attribute name="extraScripts">
	<script>
		$(function() {
			$('#checkout').click(function() {
				_gaq.push([ '_trackPageview', '/checkout/finalizaCompra' ]);
			});
			$('.book-suggest').click(function() {
				var book = $(this).data('book');
				_gaq.push([ '_trackEvent', 'Recomendação', 'Livro', book ]);
			});
		});
	</script>
	
	<script>
		$(function() {
			$('a[href^="http"]').not('.dont-track').filter(function(index) {
				var ccb = $(this).attr('href').indexOf("casadocodigo.com.br");
				if (ccb == -1)
					ccb = $(this).attr('href').indexOf("localhost");
				return ccb != 7 && ccb != 11;
			}).click(function(event) {
				var domain = this.href;
				domain = domain.substring(7);
				domain = domain.substring(0, domain.indexOf('/'));
				if (domain.substring(0, 4) == 'www.')
					domain = domain.substring(4);
				_gaq.push([ '_trackPageview', '/LinkExterno/' + this.href ]);
			});
		});
	</script>

</jsp:attribute>

<jsp:body>
	<section class="container middle">
		<h2 id="cart-title">Seu carrinho de compras</h2>
		<table id="cart-table">
			<colgroup>
				<col class="item-col">
				<col class="item-price-col">
				<col class="item-quantity-col">
				<col class="line-price-col">
				<col class="delete-col">
			</colgroup>
			<thead>
				<tr>
					<th class="cart-img-col"></th>
					<th width="65%">Item</th>
					<th width="10%">Preço</th>
					<th width="10%">Quantidade</th>
					<th width="10%">Total</th>
					<th width="5%"></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${shoppingCart.list}" var="item">
					<tr>
						<td class="cart-img-col"><img src="" alt="${item.product.title}" /></td>
						<td class="item-title">${item.product.title}-${item.bookType}</td>
						<td class="numeric-cell">${item.price}</td>
						<td class="quantity-input-cell"><input type="number" min="0" readonly="readonly" value="${shoppingCart.getQuantity(item)}"></td>
						<td class="numeric-cell">${shoppingCart.getTotal(item)}</td>
						<td class="remove-item">
						<!-- 
							<form:form method="post" action="${spring:mvcUrl('SCC#remove').arg(0,item.product.id).arg(1,item.bookType).build()}">
								<input type="image" src="//cdn.shopify.com/s/files/1/0155/7645/t/177/assets/excluir.png?58522" alt="Excluir" title="Excluir" />
							</form:form>
						-->
						</td>
					</tr>
				</c:forEach>

			</tbody>
			<tfoot>
				<tr>
					<td colspan="2">
						<form:form action="${spring:mvcUrl('PC#checkout').build()}" method="post">
		                    <!-- Asigla CSRF significa Cross-Site Request Forgery e éumtipo de ataque que pode ser feito contra sua aplicação. 
		                         A ideia basicamente é que dados possam ser enviados para a nossa aplicação sendo provenientes de uma outra página
		                         qualquer, aberta no seu navegador A variável de nome _csrf contém a referência para um objeto do tipo
		                         DefaultCsrfToken. 
		                         
		                         O livro mostra algumas maneiras de como usar e uma delas é utilizar um annotation que está deprecated
		                         Como mostrado no SecurityCOnfiguration.java 
		                         
		                         se não quiser utilizar as taglibs JSP, podemos utilizar
		                         <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		                    -->
						    <security:csrfInput/>
							<input type="submit" class="checkout" name="checkout" value="Finalizar compra" id="checkout" />
						</form:form>
					</td>
					<td class="numeric-cell">${shoppingCart.total}</td>
				</tr>
			</tfoot>
		</table>
	</section>
</jsp:body>	
</customTags:page>