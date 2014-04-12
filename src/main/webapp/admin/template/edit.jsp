<%@page import="com.santiagolizardo.jerba.model.PMF"%>
<%@page import="javax.jdo.PersistenceManager"%>
<%
String id = request.getParameter("identifier");

PersistenceManager pm = PMF.get().getPersistenceManager();
Template tpl = new TemplateManager(pm).findById(id);
pm.close();
%>
<%@page import="java.util.List"%>
<%@page import="com.santiagolizardo.jerba.model.Template"%>
<%@page import="com.santiagolizardo.jerba.utilities.StringUtils"%>
<%@page import="com.santiagolizardo.jerba.managers.TemplateManager"%>
<%@include file="../includes/header.jsp"%>

<form action="/UpdateTemplate" method="post">

<legend>Edit template</legend>

<label for="identifier">Identifier</label>
<input type="text" name="identifier" id="identifier" value="<%= tpl.getIdentifier() %>" />

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
