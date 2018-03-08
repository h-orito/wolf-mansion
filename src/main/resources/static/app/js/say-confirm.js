$(function() {
	$('[data-back]').on('click', function(){
		const villageId = $(this).data('back');
		location.href = contextPath + '/village/' + villageId;
	});
});