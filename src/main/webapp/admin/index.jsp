<%@include file="includes/header.jsp"%>

<div class="page-header">
	<h2>Jerba <small>control panel</small></h2>
</div>

<div class="row">

	<div class="span6">
		<ul class="nav nav-list">
			<li class="nav-header">Content</li>
			<li><a href="config/">General config</a></li>
			<li><a href="config/add.jsp">Add config value</a></li>
			<li><a href="post/">Posts</a></li>
			<li><a href="post/add.jsp">Add post</a></li>
			<li><a href="template/">Templates</a></li>
			<li><a href="template/add.jsp">Add templates</a></li>
			<li><a href="resource/">Resources</a></li>
			<li><a href="resource/add.jsp">Add resources</a></li>

		</ul>
	</div>

	<div class="span6">
		<ul class="nav nav-list">
			<li class="nav-header">Utilities</li>
			<li><a href="actions/clearCache.jsp">Clean cache</a></li>
			<li><a href="actions/cacheStats.jsp">View cache stats</a></li>
		</ul>
	</div>

</div>

<%@include file="includes/footer.jsp"%>
