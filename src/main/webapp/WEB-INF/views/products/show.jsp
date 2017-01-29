<%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!doctype html>
<!--[if lt IE 7]><html class="no-js lt-ie9 lt-ie8 lt-ie7" lang="pt"><![endif]-->
<!--[if IE 7]><html class="no-js lt-ie9 lt-ie8" lang="pt"><![endif]-->
<!--[if IE 8]><html class="no-js lt-ie9" lang="pt"><![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js" lang="pt">
<!--<![endif]-->

	<head>
	
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">		
		<title>${product.title}</title>
		
	</head>
	
	<body class="product">
	
		<header id="layout-header">
			<div class="clearfix container">
				<a href="/" id="logo"></a>
				<div id="header-content">
					
					<nav id="main-nav">
						<ul class="clearfix">
							<li>
							     <a href="${spring:mvcUrl('SCC#items').build()}" rel="nofollow">Seu carrinho (${shoppingCart.quantity}) </a>
							</li>
							<!--
							     <span>Seu carrinho (${sessionScope['scopedTarget.shoppingCart'].quantity})</span>
							     quando usamos um proxy scoped para possibilitar uso de componente de escopo menor dentro de escopo maior
							     that proxyMode = ScopedProxyMode.TARGET_CLASS thing
							     então precisamos utilizar a variável sessionScope do jsp, que deixa as varíveis disponíveis ... 
							     na verdade deve ser criado um proxy para a sessão que gerencia todos os componentes.
							 -->
						</ul>
					</nav>
				</div>
			</div>
		</header>
	
		<article id="${product.title}" itemscope itemtype="http://schema.org/Book">
		
			<header id="product-highlight" class="clearfix">
				<div id="product-overview" class="container">
					<h1 class="product-title" itemprop="name">${product.title}</h1>
					<p itemprop="description" class="book-description">${product.description}</p>
					<p>
					 Veja o <a href="<c:url value='/${product.summaryPath}'/>" target="_blank">sum&#225;rio</a> completo do livro!
					</p>
				</div>
			</header>	
			<section class="buy-options clearfix">
			
				<form action="<c:url value="/shopping"/>" method="post" class="container">
					<input type="hidden" value="${product.id}" name="productId"/>
					<ul id="variants" class="clearfix">
						
						<c:forEach items="${product.prices}" var="price">
						
							<li class="buy-option">
								
								<input type="radio" name="bookType" class="variant-radio" id="${product.id}-${price.bookType}"
								value="${price.bookType}" ${price.bookType.name() == 'COMBO' ? 'checked' : ''} >
								 
								<label  class="variant-label" for="${product.id}-${price.bookType}">${price.bookType}</label> 
								<p class="variant-price">${price.value}</p>
							</li>
							
						</c:forEach>
	
					</ul>
					
					<!-- Asigla CSRF significa Cross-Site Request Forgery e éumtipo de ataque que pode ser feito contra sua aplicação. 
					     A ideia basicamente é que dados possam ser enviados para a nossa aplicação sendo provenientes de uma outra página
                         qualquer, aberta no seu navegador A variável de nome _csrf contém a referência para um objeto do tipo
                         DefaultCsrfToken. 
                         
                         O livro mostra algumas maneiras de como usar e uma delas é utilizar um annotation que está deprecated
                         Como mostrado no SecurityCOnfiguration.java 
                         <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    -->
	                <security:csrfInput/>
	                
					<input type="submit" class="submit-image icon-basket-alt" alt="Compre agora" title="Compre agora '${product.title}'!" value="comprar"/>
	
				</form>
	
			</section>
	
			<div class="container">
	
				<section class="data product-detail">
					<h2 class="section-title">Dados do livro:</h2>
					<p>
						Número de paginas: <span itemprop="numberOfPages">${product.pages}</span>
					</p>
				</section>
			</div>	
		</article>
	
		<footer id="layout-footer">
			<div class="clearfix container">
			<p>footer</p>
			</div>
		</footer>
	</body>
</html>