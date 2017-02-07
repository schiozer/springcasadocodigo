<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>

<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Insert title here</title>
	</head>
	<body>
	    <security:authorize access="isAuthenticated()">
	       <security:authentication property="principal" var="user"/>
	       <div>
	           Olá ${user.name}
	       </div>
	    </security:authorize>
	    
	    <!--
	    O atributo access recebe algumas expressões que são suportadas pelo Spring Security. Foi usada a isAuthenticated(), mas
        podiamser várias outras. Outramuito comum é hasRole.
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
				<td>Data de Lançamento</td>
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
	</body>
</html>