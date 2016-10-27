<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Insert title here</title>
	</head>
	<body>
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