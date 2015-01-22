<%@ page import="jppf.GridClient" %>

<%-- TODO int nbInjectors = GridClient.getInstance().refreshNodesCount(); --%>
<% int nbInjectors = 3; %>

<div class="container">
	<h3>Test configuration</h3>
	
	<form class="form-horizontal" role="form" id="form_test" method="post" action="test-new-form" data-nb-injectors="<%= nbInjectors %>">
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
					<% for(int i = 1; i <= nbInjectors; i++) { %>
					<option value="<%= i %>"><%= i %></option>
					<% } %>
				</select>
			</div>
	 	</div>
		<div class="form-group">
			<label class="control-label col-sm-4" for="form_test_iterations">Test iterations</label>
			<div class="col-sm-8">
				<input class="form-control" id="form_test_iterations" name="form_test_iterations" type="text" required>
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
	 			<button class="btn btn-primary" type="submit" <%= nbInjectors <= 0 ? "disabled" : "" %>>Run</button>
	 			<button class="btn btn-default" type="reset">Reset</button>
	 		</div>
	 	</div>
	</form>
</div>