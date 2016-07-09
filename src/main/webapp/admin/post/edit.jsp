
<%@page import="com.santiagolizardo.jerba.model.PMF"%>
<%@page import="javax.jdo.PersistenceManager"%>
<%
	Long id = Long.valueOf(request.getParameter("articleId"));
	PersistenceManager pm = PMF.get().getPersistenceManager();
	Article article = new ArticleManager(pm).findByPrimaryKey(id);
%>
<%@page import="com.santiagolizardo.jerba.model.Article"%>
<%@page import="java.util.List"%>
<%@page import="com.santiagolizardo.jerba.managers.ArticleManager"%>
<%@page import="com.santiagolizardo.jerba.model.ArticleType"%>
<%@include file="../includes/header.jsp"%>
<script type="text/javascript" src="/scripts/ckeditor/ckeditor.js"></script>
<script type="text/javascript">
	window.onload = function() {
		CKEDITOR.replace('content');
	};
</script>

<form action="/UpdateArticle" method="post">
	<input type="hidden" name="articleId"
		value="<%=article.getKey().getId()%>" />

	<h3>Edit post</h3>

	<div class="form-group">
		<label>Type</label> <label for="type_P"><input type="radio"
			name="type" value="P" id="type_P"
			<%=article.getType().equals(ArticleType.Permanent) ? "checked=\"checked\"" : ""%> />
			Permanent</label> <label for="type_E"><input type="radio" name="type"
			value="E" id="type_E"
			<%=article.getType().equals(ArticleType.Ephemeral) ? "checked=\"checked\"" : ""%> />
			Ephemeral</label> <label for="title">Title</label> <input type="text"
			name="title" id="title" value="<%=article.getTitle()%>" />
	</div>
	<div class="form-group">
		<label for="sanitizedTitle">Sanitized title</label> <input type="text"
			class="form-control" name="sanitizedTitle" id="sanitizedTitle"
			value="<%=article.getSanitizedTitle()%>" />
	</div>
	<div class="form-group">
		<label for="keywords">Keywords</label> <input type="text"
			class="form-control" name="keywords" id="keywords"
			value="<%=article.getKeywords()%>" />
	</div>
	<div class="form-group">

		<label for="description">Description</label> <input
			class="input-xxlarge form-control" type="text" name="description"
			id="description" value="<%=article.getDescription()%>" />
	</div>
	<div class="form-group">
		<label for="content">Content</label>
		<textarea name="content" class="form-control" id="content"><%=article.getContent().getValue()%></textarea>
	</div>

	<div class="form-group">
		<label for="order">Order</label> <input type="text"
			class="form-control" name="order" id="order"
			value="<%=article.getPosition()%>" />
	</div>
	<div class="form-group">
		<label for="visible"><input type="checkbox" name="visible"
			id="visible" value="true"
			<%=article.isVisible() ? "checked=\"checked\"" : ""%> /> Visible
			-published-</label>
	</div>

	<div class="form-actions">
		<input class="btn" type="button" value="Cancel"
			onclick="window.history.go(-1);" />
		<div class="pull-right">
			<input class="btn btn-primary" type="submit" value="Save" />
		</div>
	</div>

</form>

<%@ include file="../includes/footer.jsp"%>
