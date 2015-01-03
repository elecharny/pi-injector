var index_plan = 0;


$(function() {
	if($('#form_test').length > 0) {
		$('#form_test_tab_request a').click(function(e) {
			e.preventDefault();
			$(this).tab('show');
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
			formDisplay(this);
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
			tr += 'Add : ' + entry_dn
				+ '<input type="hidden" id="form_test_plan_action-' + index_plan + '"  name="form_test_plan_action-' + index_plan + '" value="' + action + '">'
				+ '<input type="hidden" id="form_test_plan_entry-dn-' + index_plan + '" name="form_test_plan_entry-dn-' + index_plan + '" value="' + entry_dn + '">';
			break;
		
		case 'bind':
			tr += 'Bind'
				+ '<input type="hidden" id="form_test_plan_action-' + index_plan + '" name="form_test_plan_action-' + index_plan + '" value="' + action + '">';
			break;
		
		case 'bind-unbind':
			tr += 'Bind-Unbind'
				+ '<input type="hidden" id="form_test_plan_action-' + index_plan + '" name="form_test_plan_action-' + index_plan + '" value="' + action + '">';
			break;
		
		case 'compare':
			var entry_dn = $('#form_test_compare_entry-dn').val();
			var filter = $('#form_test_compare_filter').val();
			tr += 'Compare : ' + entry_dn + ' | ' + filter
				+ '<input type="hidden" id="form_test_plan_action-' + index_plan + '" name="form_test_plan_action-' + index_plan + '" value="' + action + '">'
				+ '<input type="hidden" id="form_test_plan_entry-dn-' + index_plan + '" name="form_test_plan_entry-dn-' + index_plan + '" value="' + entry_dn + '">'
				+ '<input type="hidden" id="form_test_plan_filter-' + index_plan + '" name="form_test_plan_filter-' + index_plan + '" value="' + filter + '">';
			break;
		
		case 'delete':
			var entry_dn = $('#form_test_delete_entry-dn').val();
			tr += 'Delete : ' + entry_dn
				+ '<input type="hidden" id="form_test_plan_action-' + index_plan + '" name="form_test_plan_action-' + index_plan + '" value="' + action + '">'
				+ '<input type="hidden" id="form_test_plan_entry-dn-' + index_plan + '" name="form_test_plan_entry-dn-' + index_plan + '" value="' + entry_dn + '">';
			break;
		
		case 'modify':
			var entry_dn = $('#form_test_modify_entry-dn').val();
			var attribute = $('#form_test_modify_attribute').val();
			var value = $('#form_test_modify_value').val();
			var opcode = $('#form_test_modify_opcode').val();
			tr += 'Modify : ' + entry_dn + ' | ' + attribute + ' | ' + value + ' | ' + opcode
				+ '<input type="hidden" id="form_test_plan_action-' + index_plan + '" name="form_test_plan_action-' + index_plan + '" value="' + action + '">'
				+ '<input type="hidden" id="form_test_plan_entry-dn-' + index_plan + '" name="form_test_plan_entry-dn-' + index_plan + '" value="' + entry_dn + '">'
				+ '<input type="hidden" id="form_test_plan_attribute-' + index_plan + '" name="form_test_plan_attribute-' + index_plan + '" value="' + attribute + '">'
				+ '<input type="hidden" id="form_test_plan_value-' + index_plan + '" name="form_test_plan_value-' + index_plan + '" value="' + value + '">'
				+ '<input type="hidden" id="form_test_plan_opcode-' + index_plan + '" name="form_test_plan_opcode-' + index_plan + '" value="' + opcode + '">';
			break;
		
		case 'rename':
			var old_entry_dn = $('#form_test_rename_old-entry-dn').val();
			var new_entry_dn = $('#form_test_rename_new-entry-dn').val();
			tr += 'Rename : ' + old_entry_dn + ' | ' + new_entry_dn
				+ '<input type="hidden" id="form_test_plan_action-' + index_plan + '" name="form_test_plan_action-' + index_plan + '" value="' + action + '">'
				+ '<input type="hidden" id="form_test_plan_old-entry-dn-' + index_plan + '" name="form_test_plan_old-entry-dn-' + index_plan + '" value="' + old_entry_dn + '">'
				+ '<input type="hidden" id="form_test_plan_new-entry-dn-' + index_plan + '" name="form_test_plan_new-entry-dn-' + index_plan + '" value="' + new_entry_dn + '">';
			break;
		
		case 'search':
			var base = $('#form_test_search_base').val();
			var filter = $('#form_test_search_filter').val();
			var scope = $('#form_test_search_scope').val();
			tr += 'Search : ' + base + ' | ' + filter + '|' + scope
				+ '<input type="hidden" id="form_test_plan_action-' + index_plan + '" name="form_test_plan_action-' + index_plan + '" value="' + action + '">'
				+ '<input type="hidden" id="form_test_plan_base-' + index_plan + '" name="form_test_plan_base-' + index_plan + '" value="' + base + '">'
				+ '<input type="hidden" id="form_test_plan_filter' + index_plan + '" name="form_test_plan_filter' + index_plan + '" value="' + filter + '">'
				+ '<input type="hidden" id="form_test_plan_scope' + index_plan + '" name="form_test_plan_scope' + index_plan + '" value="' + scope + '">';
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
}


/* ############################################################# TEST-DISPLAY */
function formDisplay(a) {
	
}























