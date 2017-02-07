<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Meu Login</title>
    </head>
    <body>
         
         <!--  Quando utilizamos o atributo servletRelativeAction, o contexto da aplicação já é adicionado antes da URL  -->
         <form:form servletRelativeAction="/login" method="post">

            <div>
                <label for="username">Usuário</label>
                <input type="text" name="username"/> 
            </div>
            <div>
                <label for="password">Senha</label>
                <input type="password" name="password"/>
            </div>
            <div>
                <input name="submit" type="submit" value="Login">
            </div>          
        </form:form>        
    </body>
</html>