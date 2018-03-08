$(function() {
	'use stricts'

	// ----------------------------------------------
	// def
	// ----------------------------------------------
	const villageId = $("[data-village-id]").data('village-id');
	const day = $("[data-day]").data('day');
	const GET_MESSAGE_URL = contextPath + 'village/getMessageList';
	const GET_ANCHOR_MESSAGE_URL = contextPath + 'village/getAnchorMessage';
	const GET_FOOTSTEP_URL = contextPath + 'village/getFootstepList';
	const messageTemplate = Handlebars.compile($("#message-template").html());
	const messagePartialTemplate = Handlebars.compile($("#message-partial-template").html());
	Handlebars.registerPartial('messagePartial', messagePartialTemplate);
	const $sayTextarea = $('[data-say-textarea]');
	const $sayTypeArea = $('[data-say-type]');
	const $abilityArea = $('[data-ability]');

	init();
	function init() {
		loadAndDisplayMessage();
		changeSayTextAreaBackgroundColor(); // 画面表示時にも切り替える
		replaceFootstepList(); // 画面表示時にも取得して切り替える

	}

	// メッセージ取得
	function loadAndDisplayMessage() {
		$("[data-message-area]").addClass('loading');
		return $.ajax({
			type : 'GET',
			url : GET_MESSAGE_URL,
			data : {
				'villageId' : villageId,
				'day' : day
			}
		}).then(function(response) {
			// htmlエスケープと、アンカーの変換を行う
			$.each(response.messageList, function() {
				this.messageContent = this.messageContent.replace(/(\r\n|\n|\r)/gm, '<br>').split('<br>').map(function(item) { // 先に改行を分割
					item = item.replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;').replace(/"/g, '&quot;').replace(/'/g, '&#39;'); // htmlエスケープ
					item = item.replace(/&gt;&gt;(\d)/g, '<a href=\"javascript:void(0);\" data-message-anchor=\"$1\">&gt;&gt;$1<\/a>'); // 次にアンカーをaタグにする
					item = item.replace(/&gt;&gt;\+(\d)/g, '<a href=\"javascript:void(0);\" data-message-grave-anchor=\"$1\">&gt;&gt;\+$1<\/a>');
					item = item.replace(/&gt;&gt;=(\d)/g, '<a href=\"javascript:void(0);\" data-message-mason-anchor=\"$1\">&gt;&gt;=$1<\/a>');
					return item.replace(/&gt;&gt;\*(\d)/g, '<a href=\"javascript:void(0);\" data-message-whisper-anchor=\"$1\">&gt;&gt;\*$1<\/a>');
				}).join('<br>');
			});
			$("[data-message-area]").html(messageTemplate(response));
			$("[data-message-area]").removeClass('loading');
		});
	}

	// アンカー
	$('body').on('click', '[data-message-anchor]', function() {
		const messageNumber = $(this).data('message-anchor');
		return $.ajax({
			type : 'GET',
			url : GET_ANCHOR_MESSAGE_URL,
			data : {
				'villageId' : villageId,
				'messageNumber' : messageNumber,
				'messageType' : 'NORMAL_SAY'
			}
		}).then(function(response) {
			if (response == '') {
				return;
			}
			// htmlエスケープと、アンカーの変換を行う
			response.message.messageContent = response.message.messageContent.replace(/(\r\n|\n|\r)/gm, '<br>').split('<br>').map(function(item) { // 先に改行を分割
				item = item.replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;').replace(/"/g, '&quot;').replace(/'/g, '&#39;'); // htmlエスケープ
				item = item.replace(/(&gt;&gt;\d)/g, '<a href=\"javascript:void(0);\" data-message-anchor>$1<\/a>'); // 次にアンカーをaタグにする
				item = item.replace(/(&gt;&gt;\+\d)/g, '<a href=\"javascript:void(0);\" data-message-grave-anchor>$1<\/a>');
				item = item.replace(/(&gt;&gt;=\d)/g, '<a href=\"javascript:void(0);\" data-message-mason-anchor>$1<\/a>');
				return item.replace(/(&gt;&gt;\*\d)/g, '<a href=\"javascript:void(0);\" data-message-whisper-anchor>$1<\/a>');
			}).join('<br>');

			$(this).closest('[data-message]').after(messagePartialTemplate(response.message));
		});
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

	$sayTypeArea.on('change', 'input[name=messageType]', function() {
		changeSayTextAreaBackgroundColor();
	});

	// 足音候補を取得して入れ替える
	function replaceFootstepList() {
		const $footstepSelect = $('[data-footstep-select]');
		if ($footstepSelect == null) {
			return;
		}
		const $attackerSelect = $('[data-attacker-select]');
		const charaId = $attackerSelect == null ? null : $attackerSelect.val();
		const $targetSelect = $('[data-ability-target-select]');
		const targetCharaId = $targetSelect == null ? null : $targetSelect.val();
		$.ajax({
			type : 'GET',
			url : GET_FOOTSTEP_URL,
			data : {
				'villageId' : villageId,
				'charaId' : charaId,
				'targetCharaId' : targetCharaId
			}
		}).then(function(response) {
			if (response == '') {
				return;
			}
			$footstepSelect.empty();
			$.each(response.footstepList, function(idx, val) {
				$footstepSelect.append($('<option></option>', {
					'value' : val,
					text : val
				}));
			});
		});
	}

	// 足音候補を選択できる場合、襲撃者や対象を選択時に足音候補を取得して入れ替える
	$abilityArea.on('change', '[data-attacker-select], [data-ability-target-select]', function() {
		replaceFootstepList();
	});

	// 文字数カウント
	$('body').on('keyup', '[data-say-textarea]', function() {
		var len = $(this).val().length;
		var line = $(this).val().split('\n').length;
		const $countspan = $(this).closest('form').find('[data-message-count]');
		const $submitbtn = $(this).closest('form').find('[data-message-submit-btn]');
		$countspan.text('文字数: ' + len + '/200, 行数: ' + line + '/10');
		if (len > 200 || line > 10) {
			$countspan.addClass('text-danger');
			$submitbtn.prop('disabled', true);
		} else {
			$countspan.removeClass('text-danger');
			$submitbtn.prop('disabled', false);
		}
	});
});