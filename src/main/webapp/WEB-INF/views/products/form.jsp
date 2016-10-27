<!DOCTYPE html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Cadastro de produtos</title>
	</head>
	<body>
		 <form:form action="${spring:mvcUrl('saveProduct').build()}" method="post" commandName="product" enctype="multipart/form-data">>

			<div>
			<!-- O atributo name dos campos de entrada devem bater com o nome dos atributos
				que o Spring recebe nos Objetos. 
			 -->
			 
				<label for="title">Titulo</label>
				<form:input path="title"/>
				<form:errors path="title"/>	
				<!-- 
				<spring:hasBindErrors name="product">
					<c:forEach var="error" items="${errors.getFieldErrors('title')}">
						<span>
							<spring:message code="${error.code}" text="${error.defaultMessage}"/>
						</span>
					</c:forEach>
				</spring:hasBindErrors>
				-->
			</div>
			<div>
				<label for="description">Descrição</label>
				<form:textarea path="description" rows="10" cols="20"/>
				<form:errors path="description"/>
			</div>
			<div>
				<label for="pages">Número de paginas e não podem ser muitas</label>
				<form:input path="pages"/>
				<form:errors path="pages"/>
			</div>
			<div>
				<label for="releaseDate">Data de lançamento</label>
				<form:input type="date" path="releaseDate"/>
				<form:errors path="releaseDate"/>
			</div>
			<c:forEach items="${types}" var="bookType" varStatus="status">
				<div>
					<label for="price_${bookType}">${bookType}</label>
					<input type="text" name="prices[${status.index}].value" id="price_${bookType}"/>
					<input type="hidden" name="prices[${status.index}].bookType" value="${bookType}"/>
				</div>
			</c:forEach>
			<div>
				<label for="summary">Sumário do livro</label>
				<input type="file" name="summary"/>
				<form:errors path="summaryPath"/>
			</div>
			<div>
				<input type="submit" value="Enviar">
			</div>			
			<!-- 			
			<c:forEach items="${requestScope['org.springframework.validation.BindingResult.product'].allErrors}" var="error">
				${error.code}<br/>
			</c:forEach>
			 -->
		</form:form>		
	</body>
</html>