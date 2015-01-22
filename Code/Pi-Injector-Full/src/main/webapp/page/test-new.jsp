
<div class="container">
	<h3>Test configuration</h3>
	
	<form class="form-horizontal" role="form" id="form_test" method="post" action="test-new-form">
		<div class="form-group">
			<label class="control-label col-sm-4" for="form_test_name">Test name</label>
			<div class="col-sm-8">
				<input class="form-control" id="form_test_name" name="form_test_name" type="text" required>
			</div>
	 	</div>
		<div class="form-group">
			<label class="control-label col-sm-4" for="form_test_nb-injectors">Number of injectors</label>
			<div class="col-sm-8">
				<select class="form-control" id="form_test_nb-injectors" name="form_test_nb-injectors">
					<% for(int i = 1; i <= 5; i++) { %>
						<option value="<%= i %>"><%= i %></option>
					<% } %>
				</select>
			</div>
	 	</div>
		<div class="form-group">
			<label class="control-label col-sm-4" for="form_test_nb-threads">Number of threads by injector</label>
			<div class="col-sm-8">
				<select class="form-control" id="form_test_nb-threads" name="form_test_nb-threads">
					<% for(int i = 1; i <= 2; i++) { %>
						<option value="<%= i %>"><%= i %></option>
					<% } %>
				</select>
			</div>
	 	</div>
		<div class="form-group">
			<label class="control-label col-sm-4" for="form_test_duration-value">Test duration</label>
			<div class="col-sm-4">
				<input class="form-control" id="form_test_duration-value" name="form_test_duration-value" type="text" required>
			</div>
			<div class="col-sm-2">
				<label class="radio-inline">
					<input id="form_test_duration-unit-1" name="form_test_duration-unit" type="radio" value="seconds" required> seconds
				</label>
			</div>
			<div class="col-sm-2">
				<label class="radio-inline">
					<input id="form_test_duration-unit-2" name="form_test_duration-unit" type="radio" value="iteration" required> iterations
				</label>
			</div>
	 	</div>
		<div class="form-group">
			<label class="control-label col-sm-4" for="form_test_protocol">Protocol</label>
			<div class="col-sm-8">
				<select class="form-control" id="form_test_protocol" name="form_test_protocol">
					<option value="LDAP">LDAP</option>
				</select>
			</div>
	 	</div>
	 	
	 	<%@ include file="protocol/ldap.jsp" %>
		
	 	<div class="form-group">
	 		<div class="col-sm-offset-4 col-sm-8">
	 			<button class="btn btn-primary" type="submit">Run</button>
	 			<button class="btn btn-default" type="reset">Reset</button>
	 		</div>
	 	</div>
	</form>
</div>