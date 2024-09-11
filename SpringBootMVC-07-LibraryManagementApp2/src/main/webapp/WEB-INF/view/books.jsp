<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Book Library</title>
</head>
<body>
	<div>
		<h2>LIBRARY</h2>
	</div>
	<div>
		<div>
			BOOK LIST
		</div>
		<div>
			<table>
				<tr>
					<th>ID</th>
					<th>AUTHOR</th>
					<th>NAME</th>
				</tr>
				<c:forEach var="book" items="${books }">
					<tr>
						<td>${book.id }</td>
						<td>${book.author }</td>
						<td>${book.name }</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
</body>
</html>