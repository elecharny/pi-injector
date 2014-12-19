
<div class="container">
	<h3>Test configuration</h3>
	
	<form class="form-horizontal" role="form" id="form_test" method="post" action="">
		<div class="form-group">
			<label class="control-label col-sm-4" for="form_test_name">Test name</label>
			<div class="col-sm-8">
				<input class="form-control" id="form_test_name" type="text" required>
			</div>
	 	</div>
		<div class="form-group">
			<label class="control-label col-sm-4" for="form_test_nb_injectors">Number of injectors</label>
			<div class="col-sm-8">
				<select class="form-control" id="form_test_nb_injectors">
					<% for(int i = 1; i <= 5; i++) { %>
						<option value="<%= i %>"><%= i %></option>
					<% } %>
				</select>
			</div>
	 	</div>
		<div class="form-group">
			<label class="control-label col-sm-4" for="form_test_nb_threads">Number of threads by injector</label>
			<div class="col-sm-8">
				<select class="form-control" id="form_test_nb_threads">
					<% for(int i = 1; i <= 2; i++) { %>
						<option value="<%= i %>"><%= i %></option>
					<% } %>
				</select>
			</div>
	 	</div>
		<div class="form-group">
			<label class="control-label col-sm-4" for="form_test_duration">Test duration (in seconds)</label>
			<div class="col-sm-8">
				<input class="form-control" id="form_test_duration" type="text">
			</div>
	 	</div>
		<div class="form-group">
			<label class="control-label col-sm-4" for="form_test_">Protocol</label>
			<div class="col-sm-8">
				<select class="form-control" id="form_test_protocol">
					<option value="LDAP">LDAP</option>
				</select>
			</div>
	 	</div>
	 	<fieldset>
	 		<legend>LDAP</legend>
			<div class="form-group">
				<label class="control-label col-sm-4" for="form_test_servername">Servername / port</label>
				<div class="col-sm-6">
					<input class="form-control" id="form_test_servername" type="text" placeholder="Servername">
				</div>
				<div class="col-sm-2">
					<input class="form-control" id="form_test_port" type="text" placeholder="Port">
				</div>
		 	</div>
			<div class="form-group">
				<label class="control-label col-sm-4" for="form_test_dn">DN</label>
				<div class="col-sm-8">
					<input class="form-control" id="form_test_dn" type="text">
				</div>
		 	</div>
			<div class="form-group">
				<label class="control-label col-sm-4" for="form_test_username">Username / password</label>
				<div class="col-sm-4">
					<input class="form-control" id="form_test_username" type="text" placeholder="Username">
				</div>
				<div class="col-sm-4">
					<input class="form-control" id="form_test_password" type="password" placeholder="Password">
				</div>
		 	</div>
			<div class="form-group">
				<label class="control-label col-sm-4">Test plan</label>
				<div class="col-sm-8">
					<input id="form_test_nb-plan" type="hidden" value="0">
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
				 			<li role="presentation" class="active"><a href="#add" aria-controls="add" role="tab" data-toggle="tab">Add</a></li>
				 			<li role="presentation"><a href="#bind" aria-controls="bind" role="tab" data-toggle="tab">Bind</a></li>
				 			<li role="presentation"><a href="#bind-unbind" aria-controls="bind-unbind" role="tab" data-toggle="tab">Bind-unbind</a></li>
				 			<li role="presentation"><a href="#compare" aria-controls="compare" role="tab" data-toggle="tab">Compare</a></li>
				 			<li role="presentation"><a href="#delete" aria-controls="delete" role="tab" data-toggle="tab">Delete</a></li>
				 			<li role="presentation"><a href="#modify" aria-controls="modify" role="tab" data-toggle="tab">Modify</a></li>
				 			<li role="presentation"><a href="#rename" aria-controls="rename" role="tab" data-toggle="tab">Rename</a></li>
				 			<li role="presentation"><a href="#search" aria-controls="search" role="tab" data-toggle="tab">Search</a></li>
				 			<li role="presentation"><a href="#unbind" aria-controls="unbind" role="tab" data-toggle="tab">Unbind</a></li>
				 		</ul>
				 		<div class="tab-content">
						 	<div role="tabpanel" class="tab-pane active" id="add">
								<div class="form-group">
									<label class="control-label col-sm-4" for="form_test_add_entry-dn">Entry DN</label>
									<div class="col-sm-8">
										<input class="form-control" id="form_test_add_entry-dn" type="text" placeholder="">
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
							 		<div class="col-sm-offset-4 col-sm-8">
							 			<button class="btn btn-primary form_test_add-to-plan" type="button" id="form_test_btn_bind">Add to test plan</button>
							 		</div>
							 	</div>
				 			</div>
							
				 			<div role="tabpanel" class="tab-pane" id="bind-unbind">
							 	<div class="form-group">
							 		<div class="col-sm-offset-4 col-sm-8">
							 			<button class="btn btn-primary form_test_add-to-plan" type="button" id="form_test_btn_bind-unbind">Add to test plan</button>
							 		</div>
							 	</div>
				 			</div>
				 			
						 	<div role="tabpanel" class="tab-pane" id="compare">
								<div class="form-group">
									<label class="control-label col-sm-4" for="form_test_compare_entry-dn">Entry DN</label>
									<div class="col-sm-8">
										<input class="form-control" id="form_test_compare_entry-dn" type="text" placeholder="">
									</div>
							 	</div>
								<div class="form-group">
									<label class="control-label col-sm-4" for="form_test_compare_filter">Compare filter</label>
									<div class="col-sm-8">
										<input class="form-control" id="form_test_compare_filter" type="text" placeholder="">
									</div>
							 	</div>
							 	<div class="form-group">
							 		<div class="col-sm-offset-4 col-sm-8">
							 			<button class="btn btn-primary form_test_add-to-plan" type="button" id="form_test_btn_compare">Add to test plan</button>
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
							
						 	<div role="tabpanel" class="tab-pane" id="modify">
								<div class="form-group">
									<label class="control-label col-sm-4" for="form_test_modify_entry-dn">Entry DN</label>
									<div class="col-sm-8">
										<input class="form-control" id="form_test_modify_entry-dn" type="text" placeholder="">
									</div>
							 	</div>
								<div class="form-group">
									<label class="control-label col-sm-4" for="form_test_modify_attribute">Attribute</label>
									<div class="col-sm-8">
										<input class="form-control" id="form_test_modify_attribute" type="text" placeholder="">
									</div>
							 	</div>
								<div class="form-group">
									<label class="control-label col-sm-4" for="form_test_modify_value">Value</label>
									<div class="col-sm-8">
										<input class="form-control" id="form_test_modify_value" type="text" placeholder="">
									</div>
							 	</div>
								<div class="form-group">
									<label class="control-label col-sm-4" for="form_test_modify_opcode">opcode</label>
									<div class="col-sm-8">
										<select class="form-control" id="form_test_modify_opcode">
											<option value="Add">Add</option>
											<option value="Delete">Delete</option>
											<option value="Replace">Replace</option>
										</select>
									</div>
							 	</div>
							 	<div class="form-group">
							 		<div class="col-sm-offset-4 col-sm-8">
							 			<button class="btn btn-primary form_test_add-to-plan" type="button" id="form_test_btn_modify">Add to test plan</button>
							 		</div>
							 	</div>
							</div>
							
						 	<div role="tabpanel" class="tab-pane" id="rename">
								<div class="form-group">
									<label class="control-label col-sm-4" for="form_test_rename_old-entry-dn">Old entry DN</label>
									<div class="col-sm-8">
										<input class="form-control" id="form_test_rename_old-entry-dn" type="text" placeholder="">
									</div>
							 	</div>
								<div class="form-group">
									<label class="control-label col-sm-4" for="form_test_rename_new-entry-dn">New entry DN</label>
									<div class="col-sm-8">
										<input class="form-control" id="form_test_rename_new-entry-dn" type="text" placeholder="">
									</div>
							 	</div>
							 	<div class="form-group">
							 		<div class="col-sm-offset-4 col-sm-8">
							 			<button class="btn btn-primary form_test_add-to-plan" type="button" id="form_test_btn_rename">Add to test plan</button>
							 		</div>
							 	</div>
							</div>
							
							<div role="tabpanel" class="tab-pane" id="search">
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
	 	<div class="form-group">
	 		<div class="col-sm-offset-4 col-sm-8">
	 			<button class="btn btn-primary" type="submit">Run</button>
	 			<button class="btn btn-default" type="reset">Reset</button>
	 		</div>
	 	</div>
	</form>
</div>