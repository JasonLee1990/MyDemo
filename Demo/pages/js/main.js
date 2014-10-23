/**
 * 阻止表单提交的默认动作
 */
$(document).ready(function() {
	$('form').one('submit', function(event) {
		event.preventDefault();
	});	
});

