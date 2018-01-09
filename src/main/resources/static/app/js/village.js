$(function() {
	'use stricts'

	// ----------------------------------------------
	// def
	// ----------------------------------------------
	// var contextPath = $('#context-path').text();
	var villageId = $("[data-village-id]").data('village-id');
	var day = $("[data-day]").data('day');
	var GET_MESSAGE_URL = contextPath + 'village/getMessageList';
	var messageTemplate = Handlebars.compile($("#message-template").html());
	var $sayTextarea = $('[data-say-textarea]');
	var $sayTypeArea = $('[data-say-type]');

	// メッセージ取得
	$.ajax({
		type : 'GET',
		url : GET_MESSAGE_URL,
		data : {
			'villageId' : villageId,
			'day' : day
		}
	}).then(function(response) {
		console.log(response);
		$("[data-message-area]").html(messageTemplate(response));
	});

	// 発言種別に応じて発言エリアの色を分ける
	function changeSayTextAreaBackgroundColor() {
		if ($sayTypeArea == null) {
			return;
		}
		$sayTypeArea.find('label').each(function() {
			if ($(this).hasClass('active')) {
				var sayType = $(this).find('input').val();
				$sayTextarea.removeClass().addClass('form-control');
				switch (sayType) {
				case 'WEREWOLF_SAY':
					$sayTextarea.addClass('message-werewolf');
					break;
				case 'MASON_SAY':
					$sayTextarea.addClass('message-mason');
					break;
				case 'MONOLOGUE_SAY':
					$sayTextarea.addClass('message-monologue');
					break;
				case 'GRAVE_SAY':
					$sayTextarea.addClass('message-grave');
					break;
				default:
					break;
				}
				return;
			}
		});
	}
	
	$sayTypeArea.on('change', 'input[name=messageType]', function(){
		changeSayTextAreaBackgroundColor();
	});
	changeSayTextAreaBackgroundColor(); // 画面表示時にも切り替える

});