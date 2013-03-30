<%@page import="com.santiagolizardo.jerba.managers.ConfigManager"%>
<%@page import="com.santiagolizardo.jerba.model.ConfigValue"%>
<%@page import="java.util.List"%>
<%
List<ConfigValue> articles = ConfigManager.getInstance().findAll();
%>

<%@ include file="../includes/header.jsp" %>

<div>
<a href="add.jsp">Add config value</a>
</div>

<table id="table" class="table table-striped">
<thead>
<tr>
	<th>Name</th>
	<th>Type</th>
	<th>Value</th>
	<th>Options</th>
</tr>
</thead>
<tbody>
<% for(ConfigValue a : articles) { %>
	<tr>
	<td><%= a.getName() %></td>
	<td><%= a.getType() %></td>
	<td><%= a.getValue() %></td>
	<td>
		<div class="btn-group">
			<a class="btn btn-danger" href="/Config?name=<%= a.getName() %>">Delete</a>
		</div>
	</td>
	</tr>
<% } %>
</tbody>
</table>

<%@ include file="../includes/footer.jsp" %>
