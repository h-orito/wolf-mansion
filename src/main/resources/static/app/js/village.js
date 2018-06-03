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
	const $sayTextarea = $('#sayform [data-say-textarea]');
	const $sayTypeArea = $('[data-say-type]');
	const $abilityArea = $('[data-ability]');
	// メッセージ変換機能
	const diceRegex = /(\[\[\d{1}d\d{1,5}\]\]?)/g;
	const fortuneRegex = /(\[\[fortune\]\])/g;
	const orRegex = /(?!\[\[fortune\]\])(\[\[[^\]]*or.*?\]\])/g; // [[fortune]]でなく、さらに]を含まない[[(.*)or(.*?)]]
	const whoRegex = /(?!\[\[allwho\]\])(\[\[who\]\])/g;
	const allWhoRegex = /(\[\[allwho\]\])/g;
	// 文字装飾
	const colorRegex = /\[\[(#[0-9a-fA-F]{6})\]\](.*?)\[\[\/#\]\]/g;
	const boldRegex = /\[\[b\]\](.*?)\[\[\/b\]\]/g;
	const largeRegex = /\[\[large\]\](.*?)\[\[\/large\]\]/g;
	const smallRegex = /\[\[small\]\](.*?)\[\[\/small\]\]/g;
	let latestDay;

	init();
	function init() {
		loadAndDisplayMessage();
		changeSayTextAreaBackgroundColor(); // 画面表示時にも切り替える
		let def = replaceFootstepList(); // 画面表示時にも取得して切り替える
		if ($('[data-footstep-select]') != null) {
			// 選択していた足音をプルダウンから選択する
			const nowSelectedFootstep = $('[data-selected-footstep]').data('selected-footstep');
			def.then(function() {
				$('[data-footstep-select]').val(nowSelectedFootstep);
			});
		}
		selectDefaultFootsteps(); // 狐と狂人だったら選択していた足音の部屋を選択状態にする
		restoreDisplaySetting();
	}

	// ページング
	$('body').on('click', '[data-prev-page]', function() {
		const currentPage = parseInt($(this).data('prev-page'));
		loadAndDisplayMessage(currentPage - 1).then(function() {
			gotoHead();
		});
	});
	$('body').on('click', '[data-next-page]', function() {
		const currentPage = parseInt($(this).data('next-page'));
		loadAndDisplayMessage(currentPage + 1).then(function(){
			gotoHead();
		});
	});
	$('body').on('click', '[data-pagenum]', function() {
		loadAndDisplayMessage(parseInt($(this).data('pagenum'))).then(function(){
			gotoHead();
		});
	});

	// メッセージ取得
	function loadAndDisplayMessage(pageNum) {
		$("[data-message-area]").addClass('loading');
		const isNoPaging = getDisplaySetting('is_no_paging');
		return $.ajax({
			type : 'GET',
			url : GET_MESSAGE_URL,
			data : {
				'villageId' : villageId,
				'day' : day,
				'pageSize' : isNoPaging ? null : 30,
				'pageNum' : isNoPaging ? null : pageNum
			}
		}).then(function(response) {
			// htmlエスケープと、アンカーの変換を行う
			$.each(response.messageList, function() {
				this.messageContent = escapeAndSetAnchor(this.messageContent);
			});
			$("[data-message-area]").html(messageTemplate(response));
			$("[data-message-area]").removeClass('loading');

			// 最新の日付が変わったら通知
			if (latestDay != null && latestDay < response.latestDay) {
				$('.daychange-alert').css('display', 'block');
			}
			latestDay = response.latestDay;

			// フィルタ適用
			filterMessage();
		});
	}

	function escapeAndSetAnchor(message) {
		let mes = message.replace(/(\r\n|\n|\r)/gm, '<br>').split('<br>').map(function(item) { // 先に改行を分割
			item = item.replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;').replace(/"/g, '&quot;').replace(/'/g, '&#39;'); // htmlエスケープ
			// 変換機能
			item = item.replace(diceRegex, '<span class="extra-small">$1</span>');
			item = item.replace(fortuneRegex, '<span class="extra-small">$1</span>');
			item = item.replace(orRegex, '<span class="extra-small">$1</span>');
			item = item.replace(whoRegex, '<span class="extra-small">$1</span>');
			item = item.replace(allWhoRegex, '<span class="extra-small">$1</span>');
			// アンカー
			item = item.replace(/&gt;&gt;(\d{1,5})/g, '<a href=\"javascript:void(0);\" data-message-anchor=\"$1\">&gt;&gt;$1<\/a>'); // 次にアンカーをaタグにする
			item = item.replace(/&gt;&gt;\+(\d{1,5})/g, '<a href=\"javascript:void(0);\" data-message-grave-anchor=\"$1\">&gt;&gt;\+$1<\/a>');
			item = item.replace(/&gt;&gt;=(\d{1,5})/g, '<a href=\"javascript:void(0);\" data-message-mason-anchor=\"$1\">&gt;&gt;=$1<\/a>');
			item = item.replace(/&gt;&gt;@(\d{1,5})/g, '<a href=\"javascript:void(0);\" data-message-spectate-anchor=\"$1\">&gt;&gt;@$1<\/a>');
			item = item.replace(/&gt;&gt;\-(\d{1,5})/g, '<a href=\"javascript:void(0);\" data-message-monologue-anchor=\"$1\">&gt;&gt;\-$1<\/a>');
			item = item.replace(/&gt;&gt;\*(\d{1,5})/g, '<a href=\"javascript:void(0);\" data-message-whisper-anchor=\"$1\">&gt;&gt;\*$1<\/a>');
			return item;
		}).join('<br>');
		// 文字装飾
		mes = mes.replace(colorRegex, '<span style="color: $1">$2</span>');
		mes = mes.replace(boldRegex, '<b>$1</b>');
		mes = mes.replace(largeRegex, '<span style="font-size: 16px;">$1</span>');
		return mes.replace(smallRegex, '<span style="font-size: 10px;">$1</span>');
	}

	// アンカー
	$('body').on('click', '[data-message-anchor]', function() {
		const messageNumber = $(this).data('message-anchor');
		handlingNumberAnchor($(this), 'NORMAL_SAY', messageNumber);
	});
	$('body').on('click', '[data-message-grave-anchor]', function() {
		const messageNumber = $(this).data('message-grave-anchor');
		handlingNumberAnchor($(this), 'GRAVE_SAY', messageNumber);
	});
	$('body').on('click', '[data-message-mason-anchor]', function() {
		const messageNumber = $(this).data('message-mason-anchor');
		handlingNumberAnchor($(this), 'MASON_SAY', messageNumber);
	});
	$('body').on('click', '[data-message-spectate-anchor]', function() {
		const messageNumber = $(this).data('message-spectate-anchor');
		handlingNumberAnchor($(this), 'SPECTATE_SAY', messageNumber);
	});
	$('body').on('click', '[data-message-monologue-anchor]', function() {
		const messageNumber = $(this).data('message-monologue-anchor');
		handlingNumberAnchor($(this), 'MONOLOGUE_SAY', messageNumber);
	});
	$('body').on('click', '[data-message-whisper-anchor]', function() {
		const messageNumber = $(this).data('message-whisper-anchor');
		handlingNumberAnchor($(this), 'WEREWOLF_SAY', messageNumber);
	});

	function handlingNumberAnchor($anchor, messageType, messageNumber) {
		const $thisMessage = $anchor.closest('[data-message]');
		const anchorClassName = getClassName($anchor) + '_' + messageType.substring(0, 1) + messageNumber;

		if ($('body').find('.' + anchorClassName).length != 0) { // 既に読み込み済みの場合
			$('body').find('.' + anchorClassName).collapse('toggle');
			return false;
		}

		return $.ajax({
			type : 'GET',
			url : GET_ANCHOR_MESSAGE_URL,
			data : {
				'villageId' : villageId,
				'messageNumber' : messageNumber,
				'messageType' : messageType
			}
		}).then(function(response) {
			if (response == '') {
				return;
			}
			// htmlエスケープと、アンカーの変換を行う
			response.message.messageContent = escapeAndSetAnchor(response.message.messageContent);
			let $anchorMessage = makeAnchorMessage($(messagePartialTemplate(response.message)), anchorClassName);
			$thisMessage.after($anchorMessage);
			$('.' + anchorClassName).collapse('toggle');
			return false;
		});

	}

	function getClassName($anchor) {
		return $.grep($anchor.closest('[data-message]').attr('class').split(' '), function(elm, idx) {
			return String(elm).indexOf('say') != -1;
		})[0];
	}

	function makeAnchorMessage($message, anchorClassName) {
		var beforeClassName = $.grep($message.attr('class').split(' '), function(elm, idx) {
			return String(elm).indexOf('say') != -1;
		})[0];
		$message.removeClass(beforeClassName);
		$message.addClass(anchorClassName);
		$message.addClass('collapse');
		$message.addClass('well');
		// $message.find('div.message').addClass('bg-white');
		$message.closest('[data-message]').prepend(
				'<span class="btn btn-default btn-sm pull-right close-anchor" style="margin-left:5px; margin-right: -15px;">×</span>');
		return $message;
	}

	// アンカー閉じるボタン
	$('body').on('click', '.close-anchor', function() {
		$(this).closest('[data-message]').collapse('hide');
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
				case 'SPECTATE_SAY':
					$sayTextarea.addClass('message-spectate');
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
		return $.ajax({
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

	// 画面上部遷移
	$('body').on('click', '[data-goto-top]', function() {
		gotoHead();
		return false;
	});
	function gotoHead() {
		$('html, body').animate({
			scrollTop : 0
		}, 200);
	}

	// 画面下部遷移
	$('body').on('click', '[data-goto-bottom]', function() {
		$('html, body').animate({
			scrollTop : $('#bottom').offset().top
		}, 200);
		return false;
	});

	// 更新
	$('body').on('click', '[data-refresh]', function() {
		loadAndDisplayMessage();
	});

	// 足音選択
	$('[data-footstep-select-table] td').on('click', function() {
		const $table = $('[data-footstep-select-table]');
		const $footstepHdInput = $('[data-footstep-hd-input]');
		const $footstepInput = $('[data-footstep-input]');

		$(this).toggleClass('footstep-selected-room');

		let footsteps = [];
		$table.find('.footstep-selected-room').each(function() {
			footsteps.push($(this).data('footstep-room-number'));
		});

		if (footsteps.length < 1) {
			$footstepHdInput.val('なし');
			$footstepInput.text('なし');
		} else {
			footsteps.sort();
			$footstepHdInput.val(footsteps.join(','));
			$footstepInput.text(footsteps.join(','));
		}
	});

	function selectDefaultFootsteps() {
		const $table = $('[data-footstep-select-table]');
		if ($table.length < 1) {
			return;
		}
		const footstepsStr = $('[data-footstep-hd-input]').val();
		if (footstepsStr === 'なし') {
			return;
		}
		const footsteps = footstepsStr.split(',');
		$table.find('[data-footstep-room-number]').each(function() {
			if ($.inArray(String($(this).data('footstep-room-number')), footsteps) != -1) {
				$(this).addClass('footstep-selected-room');
			}
		});
	}

	$('[data-filter-chara-id],[data-filter-message-type]').on('click', function() {
		$(this).toggleClass('bg-info');
	});
	$('[data-filter-chara-allon]').on('click', function() {
		$('#modal-filter').find('[data-filter-chara-id]').each(function() {
			$(this).addClass('bg-info');
		});
	});
	$('[data-filter-chara-alloff]').on('click', function() {
		$('#modal-filter').find('[data-filter-chara-id]').each(function() {
			$(this).removeClass('bg-info');
		});
	});
	$('[data-filter-type-allon]').on('click', function() {
		$('#modal-filter').find('[data-filter-message-type]').each(function() {
			$(this).addClass('bg-info');
		});
	});
	$('[data-filter-type-alloff]').on('click', function() {
		$('#modal-filter').find('[data-filter-message-type]').each(function() {
			$(this).removeClass('bg-info');
		});
	});
	$('[data-filter-message-clear]').on('click', function() {
		$('#modal-filter [data-filter-message-keyword]').val('');
	});
	$('[data-filter-submit]').on('click', function() {
		filterMessage();
		$('#modal-filter').modal('hide');
	});

	function filterMessage() {
		// data-message-area
		const charaFilterArr = $('#modal-filter').find('.bg-info[data-filter-chara-id]').map(function() {
			return String($(this).data('filter-chara-id'));
		});
		const typeFilterArr = $('#modal-filter').find('.bg-info[data-filter-message-type]').map(function() {
			return String($(this).data('filter-message-type'));
		});
		const keywordFilterArr = $('#modal-filter [data-filter-message-keyword]').val().replace(/　/g, ' ').split(' ');

		$('[data-message-area] [data-message]').each(function(idx, elm) {
			const type = String($(elm).data('message'));
			if (type == '') {
				$(elm).removeClass('hidden');
				return true;
			}
			let disp = true;
			if ($.inArray(type, typeFilterArr) == -1) {
				disp = false;
			}
			const charaId = String($(elm).data('chara-id'));
			if ($.inArray(charaId, charaFilterArr) == -1) {
				disp = false;
			}
			if (keywordFilterArr.length > 0) {
				const message = String($(elm).find('.message').text());
				let match = false;
				$.each(keywordFilterArr, function(idx, keyword) {
					if (message.indexOf(keyword) != -1) {
						match = true;
					}
				});
				if (!match) {
					disp = false;
				}
			}

			if (disp) {
				$(elm).removeClass('hidden');
			} else {
				$(elm).addClass('hidden');
			}
		});
	}

	// コピー
	function clipboardCopy(text, alertText) {
		var temp = document.createElement('div');
		temp.appendChild(document.createElement('pre')).textContent = text;
		var s = temp.style;
		s.position = 'fixed';
		s.left = '-100%';
		document.body.appendChild(temp);
		document.getSelection().selectAllChildren(temp);
		var result = document.execCommand('copy');
		document.body.removeChild(temp);
		alert(alertText);
	}

	$('body').on('click', '[data-copy-anchor]', function() {
		const text = $(this).text();
		clipboardCopy(text, 'コピーしました： ' + text);
	});

	// 退村時は確認フォーム表示
	$('#leave-form').on('submit', function() {
		return confirm('本当に退村してよろしいですか？');
	});
	$('#kick-form').on('submit', function() {
		return confirm('本当に退村させてよろしいですか？');
	});

	// ----------------------------------------------
	// 表示設定
	// ----------------------------------------------
	function saveDisplaySetting(key, value) {
		let displaySetting = $.cookie('village_display_setting');
		if (displaySetting == null) {
			displaySetting = {};
		}
		displaySetting[key] = value;
		$.cookie('village_display_setting', displaySetting, {
			expires : 365
		});
	}

	function getDisplaySetting(key) {
		let displaySetting = $.cookie('village_display_setting');
		if (displaySetting == null) {
			displaySetting = {
				'is_open_situation_tab' : true,
				'is_sayform_transparent' : false,
				'is_no_paging' : false
			};
			saveDisplaySetting(displaySetting);
			return;
		}
		return displaySetting[key];
	}

	function restoreDisplaySetting() {
		if (getDisplaySetting('is_sayform_transparent')) {
			$('[data-transparent]').prop('checked', true);
			$('[data-transparent]').closest('.well').addClass('transparent-well');
		}
		if (!getDisplaySetting('has_confirm_footer-tab-announce')) {
			$('#tab-announce').attr('data-toggle', 'popover');
			$('[data-toggle="popover"]').popover();
			$('#tab-announce').popover('show');
		}
	}

	$('[data-transparent]').on('change', function() {
		const isCheck = $(this).prop('checked');
		saveDisplaySetting('is_sayform_transparent', isCheck ? true : false);
		if (isCheck) {
			$(this).closest('.well').addClass('transparent-well');
		} else {
			$(this).closest('.well').removeClass('transparent-well');
		}
	});

	$('[data-dsetting-nopaging]').on('change', function() {
		const isCheck = $(this).prop('checked');
		saveDisplaySetting('is_no_paging', isCheck ? true : false);
		loadAndDisplayMessage();
	});

	$('[data-footer-tab-announce-delete]').on('click', function() {
		saveDisplaySetting('has_confirm_footer-tab-announce', true);
		$('#tab-announce').popover('hide');
	});

	// 参戦でキャラを画像選択
	$('[data-select-participate-chara]').on('click', function() {
		const charaId = $(this).data('select-participate-chara');
		$('#participate-chara-select').val(charaId);
		$('#modal-select-participate-chara').modal('hide');
	});
});