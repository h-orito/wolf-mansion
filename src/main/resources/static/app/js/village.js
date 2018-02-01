$(function() {
	'use stricts'

	// ----------------------------------------------
	// def
	// ----------------------------------------------
	const villageId = $("[data-village-id]").data('village-id');
	const day = $("[data-day]").data('day');
	const GET_MESSAGE_URL = contextPath + 'village/getMessageList';
	const GET_FOOTSTEP_URL = contextPath + 'village/getFootstepList';
	const messageTemplate = Handlebars.compile($("#message-template").html());
	const $sayTextarea = $('[data-say-textarea]');
	const $sayTypeArea = $('[data-say-type]');
	const $abilityArea = $('[data-ability]');

	// メッセージ取得
	$.ajax({
		type : 'GET',
		url : GET_MESSAGE_URL,
		data : {
			'villageId' : villageId,
			'day' : day
		}
	}).then(function(response) {
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

	$sayTypeArea.on('change', 'input[name=messageType]', function() {
		changeSayTextAreaBackgroundColor();
	});
	changeSayTextAreaBackgroundColor(); // 画面表示時にも切り替える

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
	replaceFootstepList(); // 画面表示時にも取得して切り替える
});