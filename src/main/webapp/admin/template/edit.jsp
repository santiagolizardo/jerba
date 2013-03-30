<%
String id = request.getParameter("identifier");
Template tpl = TemplateManager.getInstance().findById(id);
%>
<%@page import="java.util.List"%>
<%@page import="com.santiagolizardo.jerba.model.Template"%>
<%@page import="com.santiagolizardo.jerba.utilities.StringUtils"%>
<%@page import="com.santiagolizardo.jerba.managers.TemplateManager"%>
<%@include file="../includes/header.jsp"%>

<form action="/UpdateTemplate" method="post">
<input type="hidden" name="id" value="<%= tpl.getIdentifier() %>" />

<legend>Edit template</legend>

<label for="newId">Identifier</label>
<input type="text" name="newId" id="newId" value="<%= tpl.getIdentifier() %>" />

<label for="contentTypeRich"><input type="radio" name="contentType" id="contentTypeRich" value="rich" /> Rich</label>
<label for="contentTypePlain"><input type="radio" name="contentType" id="contentTypePlain" value="plain" checked="checked" /> Plain</label>

<label for="content">Content</label>
<textarea name="content" id="content"><%= StringUtils.decodeHtmlEntities( tpl.getContent().getValue() ) %></textarea>

<div class="form-actions">
<input class="btn" type="button" value="Cancel" onclick="window.history.go(-1);" />
<div class="pull-right"><input class="btn btn-primary" type="submit" value="Save" /></div>
</div>

</form>

<script type="text/javascript">
$( 'input[name=contentType]' ).click( function()
{
	var editor = CKEDITOR.instances['content'];
	if ( $( '#contentTypeRich' ).is( ':checked' ) && !editor)
	{
		CKEDITOR.replace('content');
	}
	else if ( $( '#contentTypePlain' ).is( ':checked' ) && editor)
	{
		editor.destroy();
	}
} );
</script>

<%@ include file="../includes/footer.jsp" %>
