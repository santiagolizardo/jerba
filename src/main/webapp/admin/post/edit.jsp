<%
Long id = Long.valueOf(request.getParameter("articleId"));
Article article = ArticleManager.getInstance().findByPrimaryKey(id);
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
<input type="hidden" name="action" value="Save" />
<input type="hidden" name="articleId" value="<%= article.getKey().getId() %>" />

<legend>Edit post</legend>

<label>Type</label>
<label for="type_P"><input type="radio" name="type" value="P" id="type_P" <%= article.getType().equals(ArticleType.Permanent) ? "checked=\"checked\"" : "" %> /> Permanent</label>
<label for="type_E"><input type="radio" name="type" value="E" id="type_E" <%= article.getType().equals(ArticleType.Ephemeral) ? "checked=\"checked\"" : "" %> /> Ephemeral</label>

<label for="title">Title</label>
<input type="text" name="title" id="title" value="<%= article.getTitle() %>" />

<label for="keywords">Keywords</label>
<input type="text" name="keywords" id="keywords" value="<%= article.getKeywords() %>" />

<label for="description">Description</label>
<input class="input-xxlarge" type="text" name="description" id="description" value="<%= article.getDescription() %>" />

<label for="content">Content</label>
<textarea name="content" id="content"><%= article.getContent().getValue() %></textarea>

<label for="order">Order</label>
<input type="text" name="order" id="order" value="<%= article.getPosition() %>" />

<label for="visible"><input type="checkbox" name="visible" id="visible" value="true" <%= article.isVisible() ? "checked=\"checked\"" : "" %>  /> Visible -published-</label>

<div class="form-actions">
	<input class="btn" type="button" value="Cancel" onclick="window.history.go(-1);" />
	<div class="pull-right"><input class="btn btn-primary" type="submit" value="Save" /></div>
</div>

</form>

<%@ include file="../includes/footer.jsp" %>
