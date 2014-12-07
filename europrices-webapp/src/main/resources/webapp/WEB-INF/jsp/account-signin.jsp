<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:url value="/j_spring_security_check" var="loginUrl" />
<!doctype html >
<html lang="en">
	<head>
	</head>
	<body>
		<%= request.getParameter("param.error") %>
		SIGN IN		
		<!--  
		<form action="/j_spring_security_check" method="POST">
		-->
		<!--  
		<form action="${loginUrl}" method="POST">
		-->
		<form action="${request.contextPath}/j_spring_security_check" method="POST">
			email <input id="user-email" name="username" type="text" />
			pwd <input id="user-password" name="password" type="password" />
			<input type="submit" value="Submit"/>
		</form>
		
		<div>
			<ul> o ${request.contextPath}</ul>
			<ul> o ${loginUrl}</ul>
			<ul> o j_spring_security_check</ul>
		</div>
		
		<p><a href="/auth/twitter">sign in with twitter</a></p>
		<p><a href="/auth/facebook">sign in with facebook</a></p>		
		
	</body>
</html>