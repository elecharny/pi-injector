var index_plan = 0;


$(function() {
	if($('#form_test').length > 0) {
		$('#form_test_tab_request a').on('click', function(e) {
			e.preventDefault();
			$(this).tab('show');
			window.scrollTo(0, document.body.scrollHeight);
		});
		
		addToPlanBind();
		
		removeToPlanBind();
		
		addAddAttributeValueBind();
		
		removeAddAttributeValueBind();
		
		addSearchAttributeValueBind();
		
		removeSearchAttributeValueBind();
		
		$('#form_test').on('submit', function(e) {
			e.preventDefault();
			if($('#form_test').attr('data-nb-injectors') == 0 || $(':submit').is(':disabled'))
				alert('There are no more injector available, the test can\'t be runned.');
			else if($('#form_test_nb-plan').val() <= 0)
				alert('You can\'t run a test without any request on the test plan.');
			else
				$('#form_test').submit();
		});
	}
	
	if($('#table_test-display-all').length > 0) {
		$('.form_display').on('click', function(e) {
			e.preventDefault();
			formDisplay($(this));
		});
	}
	
	if($('#table_test_running').length > 0) {
		displayTestProgress();
	}
});


/* ################################################################# TEST-NEW */
function addAddAttributeValueBind() {
	$('.form_test_add_add-attribute-value').unbind('click');
	$('.form_test_add_add-attribute-value').on('click', function(e) {
		e.preventDefault();
		addAddAttributeValue();
	});
}


function removeAddAttributeValueBind() {
	$('.form_test_remove_add-attribute-value').unbind('click');
	$('.form_test_remove_add-attribute-value').on('click', function(e) {
		e.preventDefault();
		var index = $(this).attr('data-index');
		$('#form_test_add_attribute-' + index).val('');
		$('#form_test_add_value-' + index).val('');
		$(this).closest('.form-group').hide();
	});
}


function addSearchAttributeValueBind() {
	$('.form_test_add_search-attribute-value').unbind('click');
	$('.form_test_add_search-attribute-value').on('click', function(e) {
		e.preventDefault();
		addSearchAttributeValue();
	});
}


function removeSearchAttributeValueBind() {
	$('.form_test_remove_search-attribute-value').unbind('click');
	$('.form_test_remove_search-attribute-value').on('click', function(e) {
		e.preventDefault();
		var index = $(this).attr('data-index');
		$('#form_test_search_attribute-' + index).val('');
		$('#form_test_search_value-' + index).val('');
		$(this).closest('.form-group').hide();
	});
}


function addToPlanBind() {
	$('.form_test_add-to-plan').unbind('click');
	$('.form_test_add-to-plan').on('click', function(e) {
		e.preventDefault();
		var action = $(this).attr('id').split('_');
		addToPlan(action[action.length - 1]);
	});
}


function removeToPlanBind() {
	$('.form_test_remove-from-plan').unbind('click');
	$('.form_test_remove-from-plan').on('click', function(e) {
		e.preventDefault();
		$(this).closest('tr').remove();
	});
}


function addToPlan(action) {
	index_plan++;
	var tr = '<tr><td><button type="button" class="btn btn-default btn-sm form_test_remove-from-plan"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></button></td><td>';
	
	switch(action) {
		case 'add':
			var entry_dn = $('#form_test_add_entry-dn').val();
			$('#form_test_add_entry-dn').val('');
			var nb_add = $('#form_test_nb-add').val();
			tr += 'Add : ' + entry_dn
				+ '<input type="hidden" id="form_test_plan_action-' + index_plan + '"  name="form_test_plan_action-' + index_plan + '" value="' + action + '">'
				+ '<input type="hidden" id="form_test_plan_entry-dn-' + index_plan + '" name="form_test_plan_entry-dn-' + index_plan + '" value="' + entry_dn + '">'
				+ '<input type="hidden" id="form_test_plan_add-nb-' + index_plan + '"  name="form_test_plan_add-nb-' + index_plan + '" value="' + nb_add + '">';
			for(var i = 1; i <= nb_add; i++) {
				var attribute = $('#form_test_add_attribute-' + i).val();
				$('#form_test_add_attribute-' + i).val('');
				var value = $('#form_test_add_value-' + i).val();
				$('#form_test_add_value-' + i).val('');
				if($('#form_test_add_attribute-' + i).is(':visible') && attribute != null && attribute != '') {
					tr += '<input type="hidden" id="form_test_plan_attribute-' + index_plan + '-' + i + '" name="form_test_plan_attribute-' + index_plan + '-' + i + '" value="' + attribute + '">'
						+ '<input type="hidden" id="form_test_plan_value-' + index_plan + '-' + i + '" name="form_test_plan_value-' + index_plan + '-' + i + '" value="' + value + '">';
				}
			}
			for(var i = 2; i <= nb_add; i++)
				$('#form_test_add_attribute-' + i).closest('.form-group').hide();
			break;
		
		case 'bind':
			var username = $('#form_test_bind_username').val();
			$('#form_test_bind_username').val('');
			var password = $('#form_test_bind_password').val();
			$('#form_test_bind_password').val('');
			tr += 'Bind : ' + username
				+ '<input type="hidden" id="form_test_plan_action-' + index_plan + '" name="form_test_plan_action-' + index_plan + '" value="' + action + '">'
				+ '<input type="hidden" id="form_test_plan_username-' + index_plan + '" name="form_test_plan_username-' + index_plan + '" value="' + username + '">'
				+ '<input type="hidden" id="form_test_plan_password-' + index_plan + '" name="form_test_plan_password-' + index_plan + '" value="' + password + '">';
			break;
		
		case 'delete':
			var entry_dn = $('#form_test_delete_entry-dn').val();
			$('#form_test_delete_entry-dn').val('');
			tr += 'Delete : ' + entry_dn
				+ '<input type="hidden" id="form_test_plan_action-' + index_plan + '" name="form_test_plan_action-' + index_plan + '" value="' + action + '">'
				+ '<input type="hidden" id="form_test_plan_entry-dn-' + index_plan + '" name="form_test_plan_entry-dn-' + index_plan + '" value="' + entry_dn + '">';
			break;
		
		case 'search':
			var base = $('#form_test_search_base').val();
			$('#form_test_search_base').val('');
			var filter = $('#form_test_search_filter').val();
			$('#form_test_search_filter').val('');
			var scope = $('#form_test_search_scope').val();
			tr += 'Search : ' + base + ' | ' + filter + ' |' + scope
				+ '<input type="hidden" id="form_test_plan_action-' + index_plan + '" name="form_test_plan_action-' + index_plan + '" value="' + action + '">'
				+ '<input type="hidden" id="form_test_plan_base-' + index_plan + '" name="form_test_plan_base-' + index_plan + '" value="' + base + '">'
				+ '<input type="hidden" id="form_test_plan_filter-' + index_plan + '" name="form_test_plan_filter-' + index_plan + '" value="' + filter + '">'
				+ '<input type="hidden" id="form_test_plan_scope-' + index_plan + '" name="form_test_plan_scope-' + index_plan + '" value="' + scope + '">';
			var nb_search = $('#form_test_nb-search').val();
			for(var i = 1; i <= nb_search; i++) {
				var attribute = $('#form_test_search_attribute-' + i).val();
				$('#form_test_search_attribute-' + i).val('');
				var value = $('#form_test_search_value-' + i).val();
				$('#form_test_search_value-' + i).val('');
				if($('#form_test_search_attribute-' + i).is(':visible') && attribute != null && attribute != '') {
					tr += '<input type="hidden" id="form_test_plan_attribute-' + index_plan + '-' + i + '" name="form_test_plan_attribute-' + index_plan + '-' + i + '" value="' + attribute + '">'
						+ '<input type="hidden" id="form_test_plan_value-' + index_plan + '-' + i + '" name="form_test_plan_value-' + index_plan + '-' + i + '" value="' + value + '">';
				}
			}
			for(var i = 2; i <= nb_search; i++)
				$('#form_test_search_attribute-' + i).closest('.form-group').hide();
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


function addAddAttributeValue() {
	var nb_add = 1 + (+$('#form_test_nb-add').val());
	for(var i = 1; i <= nb_add; i++) {
		if($('#form_test_add_attribute-' + i).is(':hidden')) {
			$('#form_test_add_attribute-' + i).closest('.form-group').show();
			break;
		}
	}
	window.scrollTo(0, document.body.scrollHeight);
}


function addSearchAttributeValue() {
	var nb_search = 1 + (+$('#form_test_nb-search').val());
	for(var i = 1; i <= nb_search; i++) {
		if($('#form_test_search_attribute-' + i).is(':hidden')) {
			$('#form_test_search_attribute-' + i).closest('.form-group').show();
			break;
		}
	}
	window.scrollTo(0, document.body.scrollHeight);
}


/* ############################################################# TEST-DISPLAY */
function formDisplay(a) {
	var file = a.closest('tr').attr('data-file');
	
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
						console.error('Erreur formDisplay\n' + xhr.status + '\n' + thrownError);
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


/* ############################################################# TEST-RUNNING */
function displayTestProgress() {
	$.ajax({
		url: 'test-running-form',
		async: true,
		dataType: "json",
		type: "get",
		success: function(data) {
			var json = eval(data);
			var html = '';
			for(var name in json['tests']) {
				html += '<tr>'
					+ '<td>' + name + '</td>'
					+ '<td>'
					+ '<div class="progress">'
					+ '<div class="progress-bar" role="progressbar" aria-valuenow="' + json['tests'][name] + '" aria-valuemin="0" aria-valuemax="100" style="width:' + json['tests'][name] + '%">'
					+ json['tests'][name] + '%'
					+ '</div>'
					+ '</div>'
					+ '</tr>';
			}
			
			$('#table_test_running > tbody').html(html);
			setTimeout(displayTestProgress, 3000);
		},
		error: function(xhr, ajaxOptions, thrownError) {
			console.error('Erreur displayTestProgress\n' + xhr.status + '\n' + thrownError);
		}
	});
}