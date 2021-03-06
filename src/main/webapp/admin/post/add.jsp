<%@page import="com.santiagolizardo.jerba.model.PMF"%>
<%@page import="javax.jdo.PersistenceManager"%>
<%@page import="com.santiagolizardo.jerba.model.Article"%>
<%@page import="java.util.List"%>
<%@page import="com.santiagolizardo.jerba.managers.ArticleManager"%>
<%
	PersistenceManager pm = PMF.get().getPersistenceManager();
	List<Article> articles = new ArticleManager(pm).findAll();
	pageContext.setAttribute("articles", articles);
	pm.close();
%>
<%@ include file="../includes/header.jsp"%>

<script type="text/javascript">
	$(document).ready(function() {
		CKEDITOR.replace('content');
	});
</script>

<form action="/AddArticle" method="post">

	<h3>Add article</h3>

	<div class="form-group">
		<label for="parentId">Parent</label> <select class="form-control"
			name="parentId" id="parentId">
			<option value="-1">No parent</option>
			<c:forEach var="a" items="${articles}">
				<option value="${a.key.id}">${a.title}</option>
			</c:forEach>
		</select>
	</div>

	<div class="form-group">
		<label>Type</label>
		<div class="radio">
			<label><input type="radio" name="type" value="P" />Permanent</label>
		</div>
		<div class="radio">
			<label><input type="radio" name="type" value="E"
				checked="checked" />Ephemeral</label>
		</div>
	</div>

	<div class="form-group">
		<label for="pubDate">Publication date</label> <input
			class="form-control" type="date" name="pubDate" id="pubDate" />
	</div>

	<div class="form-group">
		<label for="title">Title</label> <input class="form-control"
			type="text" name="title" id="title" />
	</div>

	<div class="form-group">
		<label for="keywords">Keywords</label> <input class="form-control"
			type="text" name="keywords" id="keywords" />
	</div>

	<div class="form-group">
		<label for="description">Description</label> <input
			class="form-control" class="input-xxlarge" type="text"
			name="description" id="description" />
	</div>

	<div class="form-group">
		<label for="content">Content</label>
		<textarea class="form-control" name="content" id="content"></textarea>
	</div>

	<div class="form-group">
		<label for="order">Order</label> <input class="form-control"
			type="text" name="order" id="order" value="0" />
	</div>

	<div class="checkbox">
		<label for="visible" class="checkbox"><input type="checkbox"
			name="visible" id="visible" value="true" />Visible (published)</label>
	</div>

	<div class="form-actions">
		<input class="btn" type="button" value="Cancel"
			onclick="window.history.go(-1);" />
		<div class="pull-right">
			<input class="btn btn-primary" type="submit" value="Add" />
		</div>
	</div>

</form>

<%@ include file="../includes/footer.jsp"%>
