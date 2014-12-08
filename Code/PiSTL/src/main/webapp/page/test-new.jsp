
<div class="container">
	<h3>Test configuration</h3>
	
	<form class="form-horizontal" role="form" id="form_test" method="post" action="">
		<div class="form-group">
			<label class="control-label col-sm-4" for="form_test_">Test name</label>
			<div class="col-sm-8">
				<input class="form-control" id="form_test_" type="text" placeholder="">
			</div>
	 	</div>
		<div class="form-group">
			<label class="control-label col-sm-4" for="form_test_">Number of injectors</label>
			<div class="col-sm-8">
				<select class="form-control" id="form_test_">
					<% for(int i = 1; i <= 5; i++) { %>
						<option value="<%= i %>"><%= i %></option>
					<% } %>
				</select>
			</div>
	 	</div>
		<div class="form-group">
			<label class="control-label col-sm-4" for="form_test_">Number of threads by injector</label>
			<div class="col-sm-8">
				<select class="form-control" id="form_test_">
					<% for(int i = 1; i <= 2; i++) { %>
						<option value="<%= i %>"><%= i %></option>
					<% } %>
				</select>
			</div>
	 	</div>
		<div class="form-group">
			<label class="control-label col-sm-4" for="form_test_">Test duration (in seconds)</label>
			<div class="col-sm-8">
				<input class="form-control" id="form_test_" type="text" placeholder="">
			</div>
	 	</div>
		<div class="form-group">
			<label class="control-label col-sm-4" for="form_test_">Protocol</label>
			<div class="col-sm-8">
				<select class="form-control" id="form_test_">
					<option value="LDAP">LDAP</option>
					<option value="HTML">HTML</option>
				</select>
			</div>
	 	</div>
	 	<fieldset>
	 		<legend>LDAP</legend>
			<div class="form-group">
				<label class="control-label col-sm-4" for="form_test_">Servername / port</label>
				<div class="col-sm-6">
					<input class="form-control" id="form_test_" type="text" placeholder="Servername">
				</div>
				<div class="col-sm-2">
					<input class="form-control" id="form_test_" type="text" placeholder="Port">
				</div>
		 	</div>
			<div class="form-group">
				<label class="control-label col-sm-4" for="form_test_">DN</label>
				<div class="col-sm-8">
					<input class="form-control" id="form_test_" type="text" placeholder="DN">
				</div>
		 	</div>
			<div class="form-group">
				<label class="control-label col-sm-4" for="form_test_">Username / password</label>
				<div class="col-sm-4">
					<input class="form-control" id="form_test_" type="text" placeholder="Username">
				</div>
				<div class="col-sm-4">
					<input class="form-control" id="form_test_" type="password" placeholder="Password">
				</div>
		 	</div>
			<div class="form-group">
				<label class="control-label col-sm-4" for="form_test_">Test plan</label>
				<div class="col-sm-8">
					<table class="table table-hover table-striped" id="form_test_table_">
						<thead>
							<tr>
								<th>Remove</th>
								<th>Action</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></td>
								<td>Bind</td>
							</tr>
							<tr>
								<td><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></td>
								<td>Add</td>
							</tr>
						</tbody>
					</table>
				</div>
		 	</div>
			<div class="form-group">
				<label class="control-label col-sm-4" for="form_test_">Add request</label>
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
									<label class="control-label col-sm-4" for="form_test_">Entry DN</label>
									<div class="col-sm-8">
										<input class="form-control" id="form_test_" type="text" placeholder="">
									</div>
							 	</div>
							 	<div class="form-group">
							 		<div class="col-sm-offset-4 col-sm-8">
							 			<button class="btn btn-primary" type="button">Add to test plan</button>
							 		</div>
							 	</div>
							</div>
							
				 			<div role="tabpanel" class="tab-pane" id="bind">
							 	<div class="form-group">
							 		<div class="col-sm-offset-4 col-sm-8">
							 			<button class="btn btn-primary" type="button">Add to test plan</button>
							 		</div>
							 	</div>
				 			</div>
							
				 			<div role="tabpanel" class="tab-pane" id="bind-unbind">
							 	<div class="form-group">
							 		<div class="col-sm-offset-4 col-sm-8">
							 			<button class="btn btn-primary" type="button">Add to test plan</button>
							 		</div>
							 	</div>
				 			</div>
				 			
						 	<div role="tabpanel" class="tab-pane" id="compare">
								<div class="form-group">
									<label class="control-label col-sm-4" for="form_test_">Entry DN</label>
									<div class="col-sm-8">
										<input class="form-control" id="form_test_" type="text" placeholder="">
									</div>
							 	</div>
								<div class="form-group">
									<label class="control-label col-sm-4" for="form_test_">Compare filter</label>
									<div class="col-sm-8">
										<input class="form-control" id="form_test_" type="text" placeholder="">
									</div>
							 	</div>
							 	<div class="form-group">
							 		<div class="col-sm-offset-4 col-sm-8">
							 			<button class="btn btn-primary" type="button">Add to test plan</button>
							 		</div>
							 	</div>
							</div>
							
						 	<div role="tabpanel" class="tab-pane" id="delete">
								<div class="form-group">
									<label class="control-label col-sm-4" for="form_test_">Entry DN</label>
									<div class="col-sm-8">
										<input class="form-control" id="form_test_" type="text" placeholder="">
									</div>
							 	</div>
							 	<div class="form-group">
							 		<div class="col-sm-offset-4 col-sm-8">
							 			<button class="btn btn-primary" type="button">Add to test plan</button>
							 		</div>
							 	</div>
							</div>
							
						 	<div role="tabpanel" class="tab-pane" id="modify">
								<div class="form-group">
									<label class="control-label col-sm-4" for="form_test_">Entry DN</label>
									<div class="col-sm-8">
										<input class="form-control" id="form_test_" type="text" placeholder="">
									</div>
							 	</div>
								<div class="form-group">
									<label class="control-label col-sm-4" for="form_test_">Attribute</label>
									<div class="col-sm-8">
										<input class="form-control" id="form_test_" type="text" placeholder="">
									</div>
							 	</div>
								<div class="form-group">
									<label class="control-label col-sm-4" for="form_test_">Value</label>
									<div class="col-sm-8">
										<input class="form-control" id="form_test_" type="text" placeholder="">
									</div>
							 	</div>
								<div class="form-group">
									<label class="control-label col-sm-4" for="form_test_">opcode</label>
									<div class="col-sm-8">
										<select class="form-control" id="form_test_">
											<option value="Add">Add</option>
											<option value="Delete">Delete</option>
											<option value="Replace">Replace</option>
										</select>
									</div>
							 	</div>
							 	<div class="form-group">
							 		<div class="col-sm-offset-4 col-sm-8">
							 			<button class="btn btn-primary" type="button">Add to test plan</button>
							 		</div>
							 	</div>
							</div>
							
						 	<div role="tabpanel" class="tab-pane" id="rename">
								<div class="form-group">
									<label class="control-label col-sm-4" for="form_test_">Old entry DN</label>
									<div class="col-sm-8">
										<input class="form-control" id="form_test_" type="text" placeholder="">
									</div>
							 	</div>
								<div class="form-group">
									<label class="control-label col-sm-4" for="form_test_">New entry DN</label>
									<div class="col-sm-8">
										<input class="form-control" id="form_test_" type="text" placeholder="">
									</div>
							 	</div>
							 	<div class="form-group">
							 		<div class="col-sm-offset-4 col-sm-8">
							 			<button class="btn btn-primary" type="button">Add to test plan</button>
							 		</div>
							 	</div>
							</div>
							
							<div role="tabpanel" class="tab-pane" id="search">
								<div class="form-group">
									<label class="control-label col-sm-4" for="form_test_">Search base</label>
									<div class="col-sm-8">
										<input class="form-control" id="form_test_" type="text" placeholder="">
									</div>
							 	</div>
								<div class="form-group">
									<label class="control-label col-sm-4" for="form_test_">Search filter</label>
									<div class="col-sm-8">
										<input class="form-control" id="form_test_" type="text" placeholder="">
									</div>
							 	</div>
								<div class="form-group">
									<label class="control-label col-sm-4" for="form_test_">Search scope</label>
									<div class="col-sm-8">
										<select class="form-control" id="form_test_">
											<option value="">Base object</option>
											<option value="Add">One level</option>
											<option value="Bind">Subtree</option>
										</select>
									</div>
							 	</div>
							 	<div class="form-group">
							 		<div class="col-sm-offset-4 col-sm-8">
							 			<button class="btn btn-primary" type="button">Add to test plan</button>
							 		</div>
							 	</div>
							</div>
							
						 	<div role="tabpanel" class="tab-pane" id="unbind">
							 	<div class="form-group">
							 		<div class="col-sm-offset-4 col-sm-8">
							 			<button class="btn btn-primary" type="button">Add to test plan</button>
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