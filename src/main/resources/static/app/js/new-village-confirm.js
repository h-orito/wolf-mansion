$(function() {
	$('#back').on('click', function(){
		const $form = $('#new-village-confirm-form');
		$form.attr('action', contextPath + 'new-village');
		$form.submit();
	});
});