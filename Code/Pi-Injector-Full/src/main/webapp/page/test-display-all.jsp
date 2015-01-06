<%@page import="java.io.File"%>

<div class="modal fade" id="modal_test-display-all" role="dialog" tabindex="-1" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title"></h4>
			</div>
			<div class="modal-body"></div>
		</div>
	</div>
</div>

<div class="container">
	<h3>Test results</h3>
	
	<table class="table table-hover table-striped" id="table_test-display-all">
		<thead>
			<tr>
				<th>Remove</th>
				<th>File name</th>
			</tr>
		</thead>
		<tbody>
			<% File directory = new File(".." + File.separator + "tests-results");
			if(directory.exists() && directory.isDirectory()) {
				File[] files = directory.listFiles();
				if(files.length > 0) {
					for(int i = 0; i < files.length; i++) { %>
			<tr data-file="<%= files[i].getName() %>">
				<td><a class="form_display" data-action="delete" href=""><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></a></td>
				<td><a class="form_display" data-action="display" href=""><%= files[i].getName() %></a></td>
			</tr>
					<% }
				}
				else { %>
			<tr>
				<td class="info" colspan="2">There is no result to display.</td>
			</tr>
				<% }
			}
			else { %>
			<tr>
				<td class="info" colspan="2">There is no result to display.</td>
			</tr>
			<% } %>
		</tbody>
	</table>
</div>