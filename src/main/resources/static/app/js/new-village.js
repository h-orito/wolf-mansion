$(function() {
	
	const GET_DUMMY_CHARA_INFO_URL = contextPath + '/getDummyCharaImgUrl/';

	$('#characterSetId').on('change', function(){
		const charaGroupId = $(this).val();
		replaceDummyCharaInfo(charaGroupId);
	});
	
	function replaceDummyCharaInfo(charaGroupId) {		
		$.ajax({
			type : 'GET',
			url : GET_DUMMY_CHARA_INFO_URL + charaGroupId
		}).then(function(response) {
			$('#dummy-chara-img').css('background', 'url(\'' + response.charaImgUrl + '\')');
			$('#dummy-chara-join-message').attr('placeholder', response.joinMessage);
		});
	}
	
	replaceDummyCharaInfo($('#characterSetId').val());
});