<%@page import="java.util.List"%>
<%@page import="com.santiagolizardo.jerba.model.Template"%>
<%@page import="com.santiagolizardo.jerba.managers.TemplateManager"%>

<%@include file="../includes/header.jsp"%>

<form action="/AddTemplate" method="post">
	<input type="hidden" name="action" value="Add" />

	<legend>Add template</legend>

		<label for="identifier">Identifier</label>
		<input type="text" name="identifier" id="identifier" required="required" />
	
		<label for="contentTypeRich" class="checkbox"><input type="radio" name="contentType" id="contentTypeRich" value="rich" /> Rich</label>
		<label for="contentTypePlain" class="checkbox"><input type="radio" name="contentType" id="contentTypePlain" value="plain" checked="checked" /> Plain</label>

		<label for="content">Content</label>
		<textarea name="content" id="content" required="required" style="width: 90%; height: 300px;"></textarea>

	<div class="form-actions">
		<input class="btn" type="button" value="Cancel" onclick="window.history.go(-1);" />
		<div class="pull-right"><input class="btn btn-primary" type="submit" value="Add" /></div>
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

<%@ include file="../includes/footer.jsp"%>
