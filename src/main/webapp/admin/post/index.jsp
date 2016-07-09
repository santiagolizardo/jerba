<%@page import="com.santiagolizardo.jerba.model.PMF"%>
<%@page import="javax.jdo.PersistenceManager"%>
<%@page import="java.util.List"%>
<%@page import="com.santiagolizardo.jerba.model.Article"%>
<%@page import="com.santiagolizardo.jerba.managers.ArticleManager"%>
<%
	PersistenceManager pm = null;
	try {
		pm = PMF.get().getPersistenceManager();
		List<Article> articles = new ArticleManager(pm).findAll();
		pageContext.setAttribute("articles", articles);
	} finally {
		if (pm != null)
			pm.close();
	}
%>

<%@ include file="../includes/header.jsp"%>

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
		<c:forEach var="a" items="#{articles}">
			<tr>
				<td>${a.type}</td>
				<td>${a.publicationDate}</td>
				<td>${a.title}</td>
				<td>${a.description}</td>
				<td>${a.keywords}</td>
				<td>
					<div class="btn-group">
						<a class="btn btn-default" href="edit.jsp?articleId=${a.key.id}">Edit</a>
						<a class="btn btn-danger" href="/DeleteArticle?id=${a.key.id}">Delete</a>
					</div>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>

<%@ include file="../includes/footer.jsp"%>
