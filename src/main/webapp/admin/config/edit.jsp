<%@page import="com.santiagolizardo.jerba.model.ConfigValue"%>
<%@page import="com.santiagolizardo.jerba.managers.ConfigManager"%>
<%@page import="com.santiagolizardo.jerba.model.Article"%>
<%@page import="java.util.List"%>
<%
final String[] configTypes = {
	"string",
	"int",
	"date",
	"boolean",
};
pageContext.setAttribute("configTypes", configTypes);
pageContext.setAttribute("configNames", ConfigManager.NAMES);
final String name = request.getParameter("name");
final ConfigValue configValue = new ConfigManager().findByName(name);
pageContext.setAttribute("configValue", configValue);
%>
<%@ include file="../includes/header.jsp" %>

<script type="text/javascript">
$(document).ready(function() {
	$('#names').change(function() {
		$('#name').val($(this).val());
	});
});
</script>

<form action="/EditConfig" method="post">

<legend>Edit configuration value</legend>

<div class="form-group">
    <label for="name">Name</label>
    <input class="form-control" type="text" name="name" id="name" required="required" value="${configValue.name}" />
    <select id="names">
    <option class="form-control" value="">(reset)</option>
    <c:forEach var="name" items="#{configNames}">
    	<c:if test="${configValue.name == name}">
	    	<option value="${name}" selected="selected">${name}</option>
    	</c:if>
    	<c:if test="${configValue.name != name}">
	    	<option value="${name}">${name}</option>
    	</c:if>
    </c:forEach>
    </select>
</div>

<div class="form-group">
    <label for="type">Type</label>
    <select class="form-control" name="type" id="type">
    <c:forEach var="type" items="#{configTypes}">
    	<c:if test="${configValue.type == type}">
	    	<option value="${type}" selected="selected">${type}</option>
    	</c:if>
    	<c:if test="${configValue.type != type}">
	    	<option value="${type}">${type}</option>
    	</c:if>
    </c:forEach>
    </select>
</div>

<div class="form-group">
    <label for="value">Value</label>
    <input type="text" class="form-control" name="value" id="value" value="${configValue.value}" />
</div>

<div class="form-actions">
	<input class="btn" type="button" value="Cancel" onclick="window.history.go(-1);" />
	<div class="pull-right"><input class="btn btn-primary" type="submit" value="Edit" /></div>
</div>

</form>

<%@ include file="../includes/footer.jsp" %>
