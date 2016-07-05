<%@page import="java.util.List"%>
<%@page import="com.santiagolizardo.jerba.model.Article"%>
<%@page import="com.santiagolizardo.jerba.managers.ArticleManager"%>
<%
List<Article> articles = ArticleManager.getInstance().findAll();
%>

<%@ include file="../includes/header.jsp" %>

<div>
<a class="btn btn-primary" href="add.jsp">Add new post</a>
</div>

<table id="table" class="table table-striped">
<thead>
<tr>
	<th>Type</th>
	<th>Publication date</th>
	<th>Title</th>
	<th>Description</th>
	<th>Keywords</th>
	<th>Options</th>
</tr>
</thead>
<tbody>
<% for(Article a : articles) { %>
	<tr>
	<td><%= a.getType() %></td>
	<td><%= a.getPublicationDate() %></td>
	<td><%= a.getTitle() %></td>
	<td><%= a.getDescription() %></td>
	<td><%= a.getKeywords() %></td>
	<td>
		<div class="btn-group">
		<a class="btn btn-default" href="edit.jsp?articleId=<%= a.getKey().getId() %>">Edit</a>
		<a class="btn btn-danger" href="/DeleteArticle?id=<%= a.getKey().getId() %>">Delete</a>
		</div>
	</td>
	</tr>
<% } %>
</tbody>
</table>

<%@ include file="../includes/footer.jsp" %>
