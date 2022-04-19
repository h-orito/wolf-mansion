$(function() {
	$('#back').on('click', function(){
		const $form = $('#new-village-confirm-form');
		$form.attr('action', contextPath + 'new-village');
		$form.submit();
	});

	$(document).on('change', ':file', function() {
        const input = $(this)
        const label = input.val().replace(/\\/g, '/').replace(/.*\//, '');
        input.parent().parent().next(':text').val(label);
        const files = !!this.files ? this.files : [];
        if (!files.length || !window.FileReader) return;
        if (/^image/.test(files[0].type)){
            var reader = new FileReader();
            reader.readAsDataURL(files[0]);
            reader.onloadend = function(){
                $('#dummy-chara-img').attr('src', this.result);
            }
        }
    });
});