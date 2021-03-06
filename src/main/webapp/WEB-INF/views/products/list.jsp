<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>

<%@taglib tagdir="/WEB-INF/tags" prefix="customTags"%>

<fmt:message key="shoppingCart.title" var="title"/>

<customTags:page bodyClass="cart" title="${title}">

<jsp:attribute name="extraScripts">
</jsp:attribute>

		<jsp:body>
		    <security:authorize access="isAuthenticated()">
		       <security:authentication property="principal" var="user"/>
		       <div>
		           Ol� ${user.name}
		           <br>
		           <!-- Usando o spring para mostrar a mensagem -->
		           <spring:message code="users.welcome" arguments="${user.name}"/>
		           <br>
                   <!-- Usando o JSTL para mostrar a mensagem -->
                   <fmt:message key="users.welcome">
                       <fmt:param value="${user.name}"/>
                   </fmt:message>
                   <br>
		       </div>
		    </security:authorize>
		    
		    <!--
		    O atributo access recebe algumas express�es que s�o suportadas pelo Spring Security. Foi usada a isAuthenticated(), mas
	        podiamser v�rias outras. Outramuito comum � hasRole.
	        Consulte http://docs.spring.io/spring-security/site/docs/4.0.0.M2/reference/htmlsingle/#el-access
	        <sec:authorize access="hasRole('ROLE_ADMIN')">
	               <li><a href="${spring:mvcUrl('PC#form').build()}">
	                    Cadastrar novo produto</a>
	                </li>
	        </sec:authorize>	    
		     
		     -->
			<h3>${sucesso}</h3>
			<table>
				<tr>
					<td>Titulo</td>
					<!-- 
					<td>Data de Lan�amento</td>
					 -->
					<td>Valores</td>
					<td>Summary</td>
				</tr>
				<c:forEach items="${products}" var="product">
					<tr>
						<td>
							${product.title}
						</td>
						<!-- 
						<td>${product.releaseDate}</td>
						 -->
						<td>
							<c:forEach items="${product.prices}" var="price">
								[${price.value} - ${price.bookType}]
							</c:forEach>
						</td>
						<td>
							<img src="${product.summaryPath}"/>
						</td>
						<td>
							<a href="${spring:mvcUrl('productShow').arg(0, product.id).build()}">Detalhes</a>
						</td>
					</tr>
				</c:forEach>
			</table>
		</jsp:body>

</customTags:page>