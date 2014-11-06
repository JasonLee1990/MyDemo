/**
 * create by Jason Lee
 */
$.extend({
	alert : function(msg, title) {
		if (title) {
			alert('title : ' + title);
		}
		alert('message : ' + msg);
	}
});

$(document).ready(function() {

	//界面样式的预调
	$('.roleTable').find('tbody > tr:odd').addClass('success');
	$('.mgr').each(function() {
		if ($(this).width() > 0) {
			$('.main').css({
				'height' : ($(this).height() + 40) + 'px',
				'overflow' : 'hidden'
			});
		}
	});

	//表单初始化及事件绑定
	$('#ruleForm').on('init.form', function() {
		//对视图中的按钮的事件绑定
		$('#ruleSearchBtn').on('click', $.proxy(function() {
			$(this).trigger('search.form');
		}, this));
		$('#ruleCreateBtn').on('click', $.proxy(function() {
			$(this).trigger('add.form');
		}, this));
		$('#ruleEditBtn').on('click', $.proxy(function() {
			$(this).trigger('edit.form');
		}, this));
		$('#ruleRemoveBtn').on('click', $.proxy(function() {
			var currentId = 001;
			$(this).trigger('remove.form', currentId);
		}, this));
	}).on('add.form edit.form', function(e, obj) {
		$(this)[0].reset();
		$(this).find('[name=org]').val('');
		if (e.type == 'edit') {
			if (obj) {
				$(this).autofill(obj);
			}
		}
		$('#ruleModal').modal();
	}).on('remove.form', function(e, obj) {
		$.get('deleteOperation?id=' + obj, function(returnVal) {
			alert('deleteComplete!');
		});
	}).on('submit', function(e) {
		
	}).trigger('init.form');

	//功能按钮对视图的切换
	$('.menubar').find('li').addClass('pull-left').on('click', function() {
		var $this = $(this);
		var viewType = $this.attr('name');
		$this.parent().children().removeClass('active');
		$this.addClass('active');
		$('.mgr').each(function() {
			if (!$(this).hasClass(viewType)) {
				/*
				 $(this).animate({width:0,opacity:0},500, function(){
				 $(this).hide();
				 });
				 $('.mgr').not(this).animate({width:'100%',opacity:1},500, function(){
				 $(this).show();
				 });*/

				$(this).animate({
					width : 0,
					opacity : 0
				}, 500);
				$('.mgr').not(this).animate({
					width : '100%',
					opacity : 1
				}, 500, $.proxy(function() {
					var height = $('.mgr').not(this).height();
					$('.main').css({
						'height' : '' + (height + 40) + 'px',
						'overflow' : 'hidden'
					});
				}, this));
			}
		});
	});

	//表单初始化及事件绑定
	$('#roleForm').on('init.form', function() {
		$(this).bootstrapValidator({
        message: '值无效',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            name: {
                message: '用户名无效',
                validators: {
                    notEmpty: {
                        message: '需要用户名且不为空'
                    },
                    stringLength: {
                        min: 6,
                        max: 30,
                        message: '用户名长度在6到30之间'
                    },
                    regexp: {
                        regexp: /^[a-zA-Z0-9_]+$/,
                        message: '用户名格式错误'
                    }
                }
            },
            type: {
                validators: {
                    notEmpty: {
                        message: '类型不能为空'
                    }
                }
            }
        }
    });
		//对视图中的按钮的事件绑定
		$('#roleSearchBtn').on('click', $.proxy(function() {
			$(this).trigger('search.form');
		}, this));
		$('#roleCreateBtn').on('click', $.proxy(function() {
			$(this).trigger('add.form');
		}, this));
		$('#roleEditBtn').on('click', $.proxy(function() {
			$(this).trigger('edit.form');
		}, this));
		$('#roleRemoveBtn').on('click', $.proxy(function() {
			var currentId = 001;
			$(this).trigger('remove.form', currentId);
		}, this));
	}).on('add.form edit.form', function(e, obj) {
		$(this)[0].reset();
		$(this).find('[name=org]').val('');
		if (e.type == 'edit') {
			if (obj) {
				$(this).autofill(obj);
			}
		}
		$('#roleModal').modal();
	}).on('remove.form', function(e, obj) {
		$.get('deleteOperation?id=' + obj, function(returnVal) {
			alert('deleteComplete!');
		});
	}).on('submit', function(e) {
		e.preventDefault();
	}).trigger('init.form');
});

