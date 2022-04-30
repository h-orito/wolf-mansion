$(function() {
	// ---------------------
	// definition
	// ---------------------
	const diceRegex = /(\[\[\d{1}d\d{1,5}\]\])/g;
	const fortuneRegex = /(\[\[fortune\]\])/g;
	const orRegex = /(?!\[\[fortune\]\])(\[\[.*or.*\]\])/g;
	const whoRegex = /(?!\[\[allwho\]\])(\[\[who\]\])/g;
	const allWhoRegex = /(\[\[allwho\]\])/g;
	// 文字装飾
	const colorRegex = /\[\[(#[0-9a-fA-F]{6})\]\](.*?)\[\[\/#\]\]/g;
	const boldRegex = /\[\[b\]\](.*?)\[\[\/b\]\]/g;
	const strikeRegex = /\[\[s\]\](.*?)\[\[\/s\]\]/g;
	const largeRegex = /\[\[large\]\](.*?)\[\[\/large\]\]/g;
	const smallRegex = /\[\[small\]\](.*?)\[\[\/small\]\]/g;
	const rubyRegex = /\[\[ruby\]\](.*?)\[\[rt\]\](.*?)\[\[\/rt\]\]\[\[\/ruby\]\]/g;

	// ---------------------
	// execute
	// ---------------------

	$('[data-back]').on('click', function() {
		const villageId = $(this).data('back');
		location.href = contextPath + 'village/' + villageId;
	});

	replaceMessageFunctionString();

	function replaceMessageFunctionString() {
		const $messageArea = $('#message-area');
		const message = $messageArea.text();
		const replacedMessage = replaceMessage(message, $('#isConvertDisable').val() === 'true');
		$messageArea.html(replacedMessage);
	}

	function replaceMessage(message, isConvertDisable) {
		let mes = message.replace(/(\r\n|\n|\r)/gm, '<br>').split('<br>').map(function(item) { // 先に改行を分割
			item = item.replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;').replace(/"/g, '&quot;').replace(/'/g, '&#39;'); // htmlエスケープ
			if (!isConvertDisable) {
				item = item.replace(diceRegex, '<strong>$1</strong>'); // 次に関数を太字にする
				item = item.replace(fortuneRegex, '<strong>$1</strong>');
				item = item.replace(orRegex, '<strong>$1</strong>');
				item = item.replace(whoRegex, '<strong>$1</strong>');
				item = item.replace(allWhoRegex, '<strong>$1</strong>');
				const userRandomKeywords = $('#random-keywords').text();
				if (userRandomKeywords != null) {
					$.each(userRandomKeywords.split(','), function(idx, elm) {
						const regex = new RegExp('(\\[\\[' + elm + '\\]\\])', 'g');
						item = item.replace(regex, '<strong>$1</strong>');
					});
				}
			}
			// アンカー
			item = item.replace(/&gt;&gt;(\d{1,5})/g, '<a href=\"javascript:void(0);\" data-message-anchor=\"$1\">&gt;&gt;$1<\/a>'); // 次にアンカーをaタグにする
			item = item.replace(/&gt;&gt;\+(\d{1,5})/g, '<a href=\"javascript:void(0);\" data-message-grave-anchor=\"$1\">&gt;&gt;\+$1<\/a>');
			item = item.replace(/&gt;&gt;=(\d{1,5})/g, '<a href=\"javascript:void(0);\" data-message-mason-anchor=\"$1\">&gt;&gt;=$1<\/a>');
			item = item.replace(/&gt;&gt;@(\d{1,5})/g, '<a href=\"javascript:void(0);\" data-message-spectate-anchor=\"$1\">&gt;&gt;@$1<\/a>');
			item = item.replace(/&gt;&gt;\-(\d{1,5})/g, '<a href=\"javascript:void(0);\" data-message-monologue-anchor=\"$1\">&gt;&gt;\-$1<\/a>');
			item = item.replace(/&gt;&gt;\*(\d{1,5})/g, '<a href=\"javascript:void(0);\" data-message-whisper-anchor=\"$1\">&gt;&gt;\*$1<\/a>');
			item = item.replace(/&gt;&gt;\#(\d{1,5})/g, '<a href=\"javascript:void(0);\" data-message-creator-anchor=\"$1\">&gt;&gt;\#$1<\/a>');

			return item;
		}).join('<br>');

		// 文字装飾
		if (!isConvertDisable) {
			mes = mes.replace(colorRegex, '<span style="color: $1">$2</span>');
			mes = mes.replace(boldRegex, '<strong>$1</strong>');
			mes = mes.replace(strikeRegex, '<span style="text-decoration: line-through;">$1</span>');
			mes = mes.replace(largeRegex, '<span style="font-size: 16px;">$1</span>');
			mes = mes.replace(smallRegex, '<span style="font-size: 10px;">$1</span>');
            mes = mes.replace(rubyRegex, '<ruby>$1<rt>$2</rt></ruby>');
		}
		return mes;
	}

	// 入村時
	$('#agree-rule, #agree-mind').on('change', function() {
		if ($('#agree-rule').prop('checked') && $('#agree-mind').prop('checked')) {
			$('#participate-submit').removeClass('disabled');
			$('#participate-submit').prop('disabled', false);
		} else {
			$('#participate-submit').addClass('disabled');
			$('#participate-submit').prop('disabled', true);
		}
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
                $('#chara-img').attr('src', this.result);
            }
        }
    });
});