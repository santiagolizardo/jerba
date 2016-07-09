<%@page import="com.santiagolizardo.jerba.model.PMF"%>
<%@page import="javax.jdo.PersistenceManager"%>
<%
	final String id = request.getParameter("identifier");

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

	<h2>Edit template</h2>

	<div class="form-group">
		<label for="identifier">Identifier</label> <input type="text"
			class="form-control" name="identifier" id="identifier"
			value="<%=tpl.getIdentifier()%>" />
	</div>

	<div class="form-group">
		<label for="contentTypeRich"><input type="radio"
			name="contentType" id="contentTypeRich" value="rich" /> Rich</label> <label
			for="contentTypePlain"><input type="radio" name="contentType"
			id="contentTypePlain" value="plain" checked="checked" /> Plain</label>
	</div>

	<div class="form-group">
		<label for="content">Content</label>
		<textarea name="content" class="form-control" id="content"
			required="required" rows="10"><%=StringUtils.decodeHtmlEntities(tpl.getContent().getValue())%></textarea>
	</div>

	<div class="form-actions">
		<input class="btn" type="button" value="Cancel"
			onclick="window.history.go(-1);" />
		<div class="pull-right">
			<input class="btn btn-primary" type="submit" value="Save" />
		</div>
	</div>

</form>

<script type="text/javascript">
	$('input[name=contentType]').click(function() {
		var editor = CKEDITOR.instances['content'];
		if ($('#contentTypeRich').is(':checked') && !editor) {
			CKEDITOR.replace('content');
		} else if ($('#contentTypePlain').is(':checked') && editor) {
			editor.destroy();
		}
	});
</script>

<%@ include file="../includes/footer.jsp"%>
