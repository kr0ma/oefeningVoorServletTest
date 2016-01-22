<%@page contentType='text/html' pageEncoding='UTF-8' session='true'%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html>
<head>
<title>Gastenboek</title>
<link rel="stylesheet" href=<c:url value='/styles/default.css'/>>
</head>
<body>
	<h1>Gastenboek</h1>
	<form method="post">	
		<div>	
			<label>Naam:
				<input name="naam" value="${param.naam}" autofocus>
			</label>
			<label>Bericht:
				<input name="bericht" value="${param.bericht}" size="50">
			</label>		
			${fouten.entry}
		</div>
		<div>
		<label> ${vraag}:
			<input name="antwoord" value="${param.antwoord}"size="50">
		</label>
		${fouten.security}
		</div>
		<input type="hidden" name="vraagID" value="${vraagID}">
		<input type="submit" value="Toevoegen" id="toevoegknop">
		<c:if test="${not empty fouten}">
			${fout}
		</c:if>
	</form>	
	<c:if test="${not empty gastenboek}">
		<div>
		<c:forEach var="entry" items="${gastenboek}" varStatus="status">
			<section class="gastenboekentry">
				<h4>${status.count}. ${entry.naam}</h4>
				${entry.bericht}	
		    </section>	    
		</c:forEach>
		</div>
	</c:if>
	
</body>
</html>