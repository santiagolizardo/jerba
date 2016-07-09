<%@page import="com.santiagolizardo.jerba.model.PMF"%>
<%@page import="javax.jdo.PersistenceManager"%>
<%@page import="com.santiagolizardo.jerba.managers.ConfigManager"%>
<%@page import="com.santiagolizardo.jerba.model.ConfigValue"%>
<%@page import="java.util.List"%>
<%
	PersistenceManager pm = null;
	try {
		pm = PMF.get().getPersistenceManager();
		List<ConfigValue> configValues = new ConfigManager(pm).findAll();
		pageContext.setAttribute("configValues", configValues);
	} finally {
		if (pm != null)
			pm.close();
	}
%>

<%@ include file="../includes/header.jsp"%>

<div>
	<a class="btn btn-primary" href="add.jsp">Add new config value</a>
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
		<c:forEach var="cv" items="${configValues}">
			<tr>
				<td>${cv.name}</td>
				<td>${cv.type}</td>
				<td>${cv.value}</td>
				<td>
					<div class="btn-group">
						<a class="btn btn-default" href="edit.jsp?name=${cv.name}">Edit</a>
						<a class="btn btn-danger" href="/AddConfig?name=${cv.name}">Delete</a>
					</div>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>

<%@ include file="../includes/footer.jsp"%>
