<%@page import="com.santiagolizardo.jerba.managers.ConfigManager"%>
<%@page import="com.santiagolizardo.jerba.model.Article"%>
<%@page import="java.util.List"%>
<%
String[] configTypes = {
	"string",
	"int",
	"date",
	"boolean",
};
%>
<%@ include file="../includes/header.jsp" %>

<script type="text/javascript">
$(document).ready(function() {
	$('#names').change(function() {
		$('#name').val($(this).val());
	});
});
</script>

<form action="/Config" method="post">
<input type="hidden" name="action" value="Add" />

<legend>Add configuration value</legend>

<div class="form-group">
    <label for="name">Name</label>
    <input type="text" name="name" id="name" />
    <select id="names">
    <option class="form-control" value="">(reset)</option>
    <% for(String name : ConfigManager.NAMES) { %>
	    <option value="<%= name %>"><%= name %></option>
    <% } %>
    </select>
</div>

<div class="form-group">
    <label for="type">Type</label>
    <select class="form-control" name="type" id="type">
    <% for(String type : configTypes) { %>
	    <option value="<%= type %>"><%= type %></option>
    <% } %>
    </select>
</div>

<div class="form-group">
    <label for="value">Value</label>
    <input type="text" class="form-control" name="value" id="value" />
</div>

<div class="form-actions">
	<input class="btn" type="button" value="Cancel" onclick="window.history.go(-1);" />
	<div class="pull-right"><input class="btn btn-primary" type="submit" value="Add" /></div>
</div>

</form>

<%@ include file="../includes/footer.jsp" %>
