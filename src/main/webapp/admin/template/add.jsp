<%@page import="java.util.List"%>
<%@page import="com.santiagolizardo.jerba.model.Template"%>
<%@page import="com.santiagolizardo.jerba.managers.TemplateManager"%>

<%@include file="../includes/header.jsp"%>

<form action="/AddTemplate" method="post">

	<h2>Add template</h2>

	<div class="form-group">
		<label for="identifier">Identifier</label> <input type="text"
			class="form-control" name="identifier" id="identifier"
			required="required" />
	</div>

	<div class="form-group">
		<label for="contentTypeRich" class="checkbox"><input
			type="radio" name="contentType" id="contentTypeRich" value="rich" />
			Rich</label> <label for="contentTypePlain" class="checkbox"><input
			type="radio" name="contentType" id="contentTypePlain" value="plain"
			checked="checked" /> Plain</label>
	</div>

	<div class="form-group">
		<label for="content">Content</label>
		<textarea class="form-control" name="content" id="content"
			required="required" rows="10"></textarea>
	</div>

	<div class="form-actions">
		<input class="btn" type="button" value="Cancel"
			onclick="window.history.go(-1);" />
		<div class="pull-right">
			<input class="btn btn-primary" type="submit" value="Add" />
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
