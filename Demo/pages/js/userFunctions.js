/**
 * create by Jason Lee
 */
$(function(){
	$('.userTable').find('tbody > tr:even').addClass('success');
	$('#userForm').on('init.form', function(e){
		$('#userSearchBtn').on('click', $.proxy(function(){
			$(this).trigger('search.form');			
		},this));
		$('#userCreateBtn').on('click', $.proxy(function(){
			$(this).trigger('create.form');			
		},this));
		$('#userEditBtn').on('click', $.proxy(function(){
			$(this).trigger('edit.form');			
		},this));
		$('#userRemoveBtn').on('click', $.proxy(function(){
			$(this).trigger('remove.form');			
		},this));
	}).on('create.form edit.form', function(e){
		$('#userModal').modal();
	}).trigger('init.form');
});

