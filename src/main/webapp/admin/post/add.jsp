<%@page import="com.santiagolizardo.jerba.model.Article"%>
<%@page import="java.util.List"%>
<%@page import="com.santiagolizardo.jerba.managers.ArticleManager"%>
<%
List<Article> articles = ArticleManager.getInstance().findAll();
%>
<%@ include file="../includes/header.jsp" %>

<script type="text/javascript">
$(document).ready(function() {
	CKEDITOR.replace('content');
});
</script>

<form action="/AddArticle" method="post">
<input type="hidden" name="action" value="Add" />

<legend>Add article</legend>

<label for="parentId">Parent</label>
<select name="parentId" id="parentId">
<option value="-1">No parent</option>
<% for(Article a : articles) { %>
	<option value="<%= a.getKey().getId() %>"><%= a.getTitle() %></option>
<% } %>
</select>

<label>Type</label>
<label for="type_P"><input type="radio" name="type" value="P" id="type_P" /> Permanent</label>
<label for="type_E"><input type="radio" name="type" value="E" checked="checked" id="type_E" /> Ephemeral</label>

<label for="pubDate">Publication date</label>
<input type="date" name="pubDate" id="pubDate" />

<label for="title">Title</label>
<input type="text" name="title" id="title" />

<label for="keywords">Keywords</label>
<input type="text" name="keywords" id="keywords" />

<label for="description">Description</label>
<input class="input-xxlarge" type="text" name="description" id="description" />

<label for="content">Content</label>
<textarea name="content" id="content"></textarea>

<label for="order">Order</label>
<input type="text" name="order" id="order" value="0" />

<label for="visible" class="checkbox"><input type="checkbox" name="visible" id="visible" value="true" /> Visible -published-</label>

<div class="form-actions">
	<input class="btn" type="button" value="Cancel" onclick="window.history.go(-1);" />
	<div class="pull-right"><input class="btn btn-primary" type="submit" value="Add" /></div>
</div>

</form>

<%@ include file="../includes/footer.jsp" %>
