$(function() {
	'use strict'

	$('body').on('click', '[data-all-view]', function() {
		$(this).closest('td').find('.hidden').each(function(){
			$(this).removeClass('hidden');
		});
		$(this).remove();
	});
});