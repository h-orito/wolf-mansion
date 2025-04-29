$(function() {
	'use strict'

	$('body').on('click', '[data-delete]', function() {
		if (confirm('本当に削除してよろしいですか？')) {
			$(this).closest('form').attr('action', contextPath + 'delete-random-keyword');
			$(this).closest('form').submit();
		}
	});
});