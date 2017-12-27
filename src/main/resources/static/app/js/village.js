$(function() {
	'use stricts'

	// ----------------------------------------------
	// def
	// ----------------------------------------------
	//var contextPath = $('#context-path').text();
	var villageId = $("[data-village-id]").data('village-id');
	var day = $("[data-day]").data('day');
	var GET_MESSAGE_URL = contextPath + 'village/getMessageList';
	var messageTemplate = Handlebars.compile($("#message-template").html());
	
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

});