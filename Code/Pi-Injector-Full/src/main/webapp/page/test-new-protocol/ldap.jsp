<% int nbAdd = 20, nbSearch = 20; %>

<fieldset id="fieldset_ldap">
	<legend>LDAP</legend>
	<div class="form-group">
		<label class="control-label col-sm-4" for="form_test_servername">Servername / port</label>
		<div class="col-sm-6">
			<input class="form-control" id="form_test_servername" name="form_test_servername" type="text" placeholder="Servername" required>
		</div>
		<div class="col-sm-2">
			<input class="form-control" id="form_test_port" name="form_test_port" type="text" placeholder="Port">
		</div>
 	</div>
	<div class="form-group">
		<label class="control-label col-sm-4">Test plan</label>
		<div class="col-sm-8">
			<input id="form_test_nb-plan" name="form_test_nb-plan" type="hidden" value="0">
			<table class="table table-hover table-striped" id="form_test_table_plan">
				<thead>
					<tr>
						<th>Remove</th>
						<th>Action</th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
		</div>
 	</div>
	<div class="form-group">
		<label class="control-label col-sm-4">Add request</label>
		<div class="col-sm-8">
			<div role="tabpanel" id="form_test_tab_request">
		 		<ul class="nav nav-tabs" role="tablist">
		 			<li role="presentation"><a href="#add" aria-controls="add" role="tab" data-toggle="tab">Add</a></li>
		 			<li role="presentation"><a href="#bind" aria-controls="bind" role="tab" data-toggle="tab">Bind</a></li>
		 			<li role="presentation"><a href="#delete" aria-controls="delete" role="tab" data-toggle="tab">Delete</a></li>
		 			<li role="presentation"><a href="#search" aria-controls="search" role="tab" data-toggle="tab">Search</a></li>
		 			<li role="presentation"><a href="#unbind" aria-controls="unbind" role="tab" data-toggle="tab">Unbind</a></li>
		 		</ul>
		 		<div class="tab-content">
				 	<div role="tabpanel" class="tab-pane" id="add">
						<input id="form_test_nb-add" name="form_test_nb-add" type="hidden" value="<%= nbAdd %>">
						<div class="form-group">
							<label class="control-label col-sm-4" for="form_test_add_entry-dn">Entry DN</label>
							<div class="col-sm-8">
								<input class="form-control" id="form_test_add_entry-dn" type="text" placeholder="">
							</div>
					 	</div>
					 	<% for(int i = 1; i <= nbAdd; i++) { %>
						<div class="form-group" <%= i != 1 ? "style=\"display : none\"" : "" %>>
							<label class="control-label col-sm-4" for="form_test_add_attribute-<%= i %>">
								<% if(i != 1) { %>
								<button type="button" class="btn btn-default btn-xs form_test_remove_add-attribute-value" data-index="<%= i %>">
									<span class="glyphicon glyphicon-minus" aria-hidden="true"></span>
								</button>
								<% } %>
								Attribute / value
							</label>
							<div class="col-sm-4">
								<input class="form-control" id="form_test_add_attribute-<%= i %>" type="text" placeholder="">
							</div>
							<div class="col-sm-4">
								<input class="form-control" id="form_test_add_value-<%= i %>" type="text" placeholder="">
							</div>
					 	</div>
					 	<% } %>
					 	<div class="form-group">
					 		<div class="col-sm-offset-4 col-sm-8">
					 			<button type="button" class="btn btn-default btn-xs form_test_add_add-attribute-value">
					 				<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
					 			</button>
					 		</div>
					 	</div>
					 	<div class="form-group">
					 		<div class="col-sm-offset-4 col-sm-8">
					 			<button class="btn btn-primary form_test_add-to-plan" type="button" id="form_test_btn_add">Add to test plan</button>
					 		</div>
					 	</div>
					</div>
					
		 			<div role="tabpanel" class="tab-pane" id="bind">
						<div class="form-group">
							<label class="control-label col-sm-4" for="form_test_bind_username">Username</label>
							<div class="col-sm-8">
								<input class="form-control" id="form_test_bind_username" type="text" placeholder="">
							</div>
					 	</div>
						<div class="form-group">
							<label class="control-label col-sm-4" for="form_test_bind_password">Password</label>
							<div class="col-sm-8">
								<input class="form-control" id="form_test_bind_password" type="password" placeholder="">
							</div>
					 	</div>
					 	<div class="form-group">
					 		<div class="col-sm-offset-4 col-sm-8">
					 			<button class="btn btn-primary form_test_add-to-plan" type="button" id="form_test_btn_bind">Add to test plan</button>
					 		</div>
					 	</div>
		 			</div>
					
				 	<div role="tabpanel" class="tab-pane" id="delete">
						<div class="form-group">
							<label class="control-label col-sm-4" for="form_test_delete_entry-dn">Entry DN</label>
							<div class="col-sm-8">
								<input class="form-control" id="form_test_delete_entry-dn" type="text" placeholder="">
							</div>
					 	</div>
					 	<div class="form-group">
					 		<div class="col-sm-offset-4 col-sm-8">
					 			<button class="btn btn-primary form_test_add-to-plan" type="button" id="form_test_btn_delete">Add to test plan</button>
					 		</div>
					 	</div>
					</div>
					
					<div role="tabpanel" class="tab-pane" id="search">
						<input id="form_test_nb-search" name="form_test_nb-search" type="hidden" value="<%= nbSearch %>">
						<div class="form-group">
							<label class="control-label col-sm-4" for="form_test_search_base">Search base</label>
							<div class="col-sm-8">
								<input class="form-control" id="form_test_search_base" type="text" placeholder="">
							</div>
					 	</div>
						<div class="form-group">
							<label class="control-label col-sm-4" for="form_test_search_filter">Search filter</label>
							<div class="col-sm-8">
								<input class="form-control" id="form_test_search_filter" type="text" placeholder="">
							</div>
					 	</div>
						<div class="form-group">
							<label class="control-label col-sm-4" for="form_test_search_scope">Search scope</label>
							<div class="col-sm-8">
								<select class="form-control" id="form_test_search_scope">
									<option value="base-object">Base object</option>
									<option value="one-level">One level</option>
									<option value="subtree">Subtree</option>
								</select>
							</div>
					 	</div>
					 	<% for(int i = 1; i <= nbSearch; i++) { %>
						<div class="form-group" <%= i != 1 ? "style=\"display : none\"" : "" %>>
							<label class="control-label col-sm-4" for="form_test_search_attribute-<%= i %>">
								<% if(i != 1) { %>
								<button type="button" class="btn btn-default btn-xs form_test_remove_search-attribute-value" data-index="<%= i %>">
									<span class="glyphicon glyphicon-minus" aria-hidden="true"></span>
								</button>
								<% } %>
								Attribute / value
							</label>
							<div class="col-sm-4">
								<input class="form-control" id="form_test_search_attribute-<%= i %>" type="text" placeholder="">
							</div>
							<div class="col-sm-4">
								<input class="form-control" id="form_test_search_value-<%= i %>" type="text" placeholder="">
							</div>
					 	</div>
					 	<% } %>
					 	<div class="form-group">
					 		<div class="col-sm-offset-4 col-sm-8">
					 			<button type="button" class="btn btn-default btn-xs form_test_add_search-attribute-value">
					 				<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
					 			</button>
					 		</div>
					 	</div>
					 	<div class="form-group">
					 		<div class="col-sm-offset-4 col-sm-8">
					 			<button class="btn btn-primary form_test_add-to-plan" type="button" id="form_test_btn_search">Add to test plan</button>
					 		</div>
					 	</div>
					</div>
					
				 	<div role="tabpanel" class="tab-pane" id="unbind">
					 	<div class="form-group">
					 		<div class="col-sm-offset-4 col-sm-8">
					 			<button class="btn btn-primary form_test_add-to-plan" type="button" id="form_test_btn_unbind">Add to test plan</button>
					 		</div>
					 	</div>
		 			</div>
				</div>
		 	</div>
		</div>
 	</div>
</fieldset>