$(function() {
	'use strict'

	$('body').on('click', '[data-goto-village]', function() {
		const href = $(this).find('[data-village-url]').data('village-url');
		location.href = href;
	});

	$('[data-submit-logout]').on('click', function() {
		$('#logout-form').submit();
		return false;
	});

});