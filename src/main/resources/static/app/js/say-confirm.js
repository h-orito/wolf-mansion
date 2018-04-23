$(function() {
	// ---------------------
	// definition
	// ---------------------
	const diceRegex = /(\[\[\d{1}d\d{1,5}\]\])/g;
	const fortuneRegex = /(\[\[fortune\]\])/g;
	const orRegex = /(?!\[\[fortune\]\])(\[\[.*or.*\]\])/g;
	const whoRegex = /(?!\[\[allwho\]\])(\[\[who\]\])/g;
	const allWhoRegex = /(\[\[allwho\]\])/g;
	
	// ---------------------
	// execute
	// ---------------------
	
	$('[data-back]').on('click', function(){
		const villageId = $(this).data('back');
		location.href = contextPath + '/village/' + villageId;
	});
	
	replaceMessageFunctionString();
	
	function replaceMessageFunctionString() {
		const $messageArea = $('#message-area');
		const message = $messageArea.text();
		const replacedMessage = replaceMessage(message);
		$messageArea.html(replacedMessage);
	}
	
	function replaceMessage(message) {
		return message.replace(/(\r\n|\n|\r)/gm, '<br>').split('<br>').map(function(item) { // 先に改行を分割
			item = item.replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;').replace(/"/g, '&quot;').replace(/'/g, '&#39;'); // htmlエスケープ
			item = item.replace(diceRegex, '<strong>$1</strong>'); // 次に関数を太字にする
			item = item.replace(fortuneRegex, '<strong>$1</strong>');
			item = item.replace(orRegex, '<strong>$1</strong>');
			item = item.replace(whoRegex, '<strong>$1</strong>');
			item = item.replace(allWhoRegex, '<strong>$1</strong>');
			return item;
		}).join('<br>');
	}
});