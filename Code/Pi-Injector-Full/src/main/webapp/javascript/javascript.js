var index_plan = 0;


$(function() {
	if($('#form_test').length > 0) {
		$('#form_test_tab_request a').click(function(e) {
			e.preventDefault();
			$(this).tab('show');
			window.scrollTo(0, document.body.scrollHeight);
		});
		
		$('.form_test_add-to-plan').click(function(e) {
			e.preventDefault();
			var action = $(this).attr('id').split('_');
			addToPlan(action[action.length - 1]);
		});
		
		removeToPlanBind();
	}
	
	if($('#table_test-display-all').length > 0) {
		$('.form_display').click(function(e) {
			e.preventDefault();
			formDisplay($(this));
		});
	}
});


/* ################################################################# TEST-NEW */
function removeToPlanBind() {
	$('.form_test_remove-from-plan').click(function(e) {
		e.preventDefault();
		$(this).parents('tr').remove();
	});
}


function addToPlan(action) {
	index_plan++;
	var tr = '<tr><td><span class="glyphicon glyphicon-remove form_test_remove-from-plan" aria-hidden="true"></span></td><td>';
	
	switch(action) {
		case 'add':
			var entry_dn = $('#form_test_add_entry-dn').val();
			var nb_add = $('#form_test_nb-add').val();
			tr += 'Add : ' + entry_dn
				+ '<input type="hidden" id="form_test_plan_action-' + index_plan + '"  name="form_test_plan_action-' + index_plan + '" value="' + action + '">'
				+ '<input type="hidden" id="form_test_plan_entry-dn-' + index_plan + '" name="form_test_plan_entry-dn-' + index_plan + '" value="' + entry_dn + '">'
				+ '<input type="hidden" id="form_test_plan_add-nb-' + index_plan + '"  name="form_test_plan_add-nb-' + index_plan + '" value="' + nb_add + '">';
			for(var i = 1; i <= nb_add; i++) {
				var attribute = $('#form_test_add_attribute-' + i).val();
				var values = $('#form_test_add_values-' + i).val();
				tr += '<input type="hidden" id="form_test_plan_attribute-' + index_plan + '-' + i + '" name="form_test_plan_attribute-' + index_plan + '-' + i + '" value="' + attribute + '">'
					+ '<input type="hidden" id="form_test_plan_values-' + index_plan + '-' + i + '" name="form_test_plan_values-' + index_plan + '-' + i + '" value="' + values + '">';
			}
			break;
		
		case 'bind':
			var username = $('#form_test_bind_username').val();
			var password = $('#form_test_bind_password').val();
			tr += 'Bind : ' + username
				+ '<input type="hidden" id="form_test_plan_action-' + index_plan + '" name="form_test_plan_action-' + index_plan + '" value="' + action + '">';
				+ '<input type="hidden" id="form_test_plan_username-' + index_plan + '" name="form_test_username-' + index_plan + '" value="' + username + '">';
				+ '<input type="hidden" id="form_test_plan_password-' + index_plan + '" name="form_test_password-' + index_plan + '" value="' + password + '">';
			break;
		
		case 'delete':
			var entry_dn = $('#form_test_delete_entry-dn').val();
			tr += 'Delete : ' + entry_dn
				+ '<input type="hidden" id="form_test_plan_action-' + index_plan + '" name="form_test_plan_action-' + index_plan + '" value="' + action + '">'
				+ '<input type="hidden" id="form_test_plan_entry-dn-' + index_plan + '" name="form_test_plan_entry-dn-' + index_plan + '" value="' + entry_dn + '">';
			break;
		
		case 'search':
			var base = $('#form_test_search_base').val();
			var filter = $('#form_test_search_filter').val();
			var scope = $('#form_test_search_scope').val();
			tr += 'Search : ' + base + ' | ' + filter + '|' + scope
				+ '<input type="hidden" id="form_test_plan_action-' + index_plan + '" name="form_test_plan_action-' + index_plan + '" value="' + action + '">'
				+ '<input type="hidden" id="form_test_plan_base-' + index_plan + '" name="form_test_plan_base-' + index_plan + '" value="' + base + '">'
				+ '<input type="hidden" id="form_test_plan_filter-' + index_plan + '" name="form_test_plan_filter-' + index_plan + '" value="' + filter + '">'
				+ '<input type="hidden" id="form_test_plan_scope-' + index_plan + '" name="form_test_plan_scope-' + index_plan + '" value="' + scope + '">';
			break;
		
		case 'unbind':
			tr += 'Unbind'
				+ '<input type="hidden" id="form_test_plan_action-' + index_plan + '" name="form_test_plan_action-' + index_plan + '" value="' + action + '">';
			break;
	}
	
	tr += '</td></tr>';
	$('#form_test_table_plan > tbody').append(tr);
	$('#form_test_nb-plan').val(index_plan);
	removeToPlanBind();
	window.scrollTo(0, document.body.scrollHeight);
}


/* ############################################################# TEST-DISPLAY */
function formDisplay(a) {
	var file = a.parents('tr').attr('data-file');
	
	switch(a.attr('data-action')) {
		case 'delete' :
			break;
		
		case 'display' :
			$.ajax({
				url: 'test-display-form',
				async: true,
				data: {
					file: file
				},
				dataType: "json",
				type: "get",
				error: function(xhr, ajaxOptions, thrownError) {
					if(xhr.status == 599) {
						// file not found
						//$("#form_login_help").html("<strong class=\"text-danger\">Informations de connexion incorrectes.</strong>");
					}
					else if(xhr.status == 598) {
						// file empty
						//$("#form_login_help").html("<strong class=\"text-danger\">Informations de connexion incorrectes.</strong>");
					}
					else {
						console.error("Erreur formDisplay\n" + xhr.status + "\n" + thrownError);
					}
				},
				success: function(data) {
					//console.log(data);
					$('.modal-title').html(file);
					$('.modal-body').html('<canvas id="canvas_test-display-all" width="800" height="400"></canvas>');
					
					var ctx = $("#canvas_test-display-all").get(0).getContext("2d");
					var myLineChart = new Chart(ctx).Line(data, { scaleShowGridLines: true, pointDot: false, animation: false, scaleShowLabels: true });
					
					$('#modal_test-display-all').modal();
					$('#modal_test-display-all').on('hidden.bs.modal', function() {
						$('.modal-body').html('');
					});
				}
			});
			break;
	}
}
