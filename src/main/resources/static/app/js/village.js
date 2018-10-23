$(function() {
	'use stricts'

	// ----------------------------------------------
	// def
	// ----------------------------------------------
	const villageId = $("[data-village-id]").data('village-id');
	const day = $("[data-day]").data('day');
	const GET_MESSAGE_URL = contextPath + 'village/getMessageList';
	const GET_LATEST_MESSAGE_DATETIME_URL = contextPath + 'village/getLatestMessageDatetime';
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
	const strikeRegex = /\[\[s\]\](.*?)\[\[\/s\]\]/g;
	const largeRegex = /\[\[large\]\](.*?)\[\[\/large\]\]/g;
	const smallRegex = /\[\[small\]\](.*?)\[\[\/small\]\]/g;
	let latestDay;

	init();
	function init() {
		loadAndDisplayMessage().then(function() {
			restoreDisplaySetting();
		});
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
		loadAndDisplayMessage(currentPage + 1).then(function() {
			gotoHead();
		});
	});
	$('body').on('click', '[data-pagenum]', function() {
		loadAndDisplayMessage(parseInt($(this).data('pagenum'))).then(function() {
			gotoHead();
		});
	});

	// メッセージ取得
	function loadAndDisplayMessage(pageNum) {
		$("[data-message-area]").addClass('loading');
		const isNoPaging = getDisplaySetting('is_no_paging');
		const pageSize = getDisplaySetting('page_size');
		return $.ajax({
			type : 'GET',
			url : GET_MESSAGE_URL,
			data : {
				'villageId' : villageId,
				'day' : day,
				'pageSize' : isNoPaging ? null : pageSize != null ? pageSize : 30,
				'pageNum' : isNoPaging ? null : pageNum
			}
		}).then(function(response) {
			// htmlエスケープと、アンカーの変換を行う
			$.each(response.messageList, function() {
				this.messageContent = escapeAndSetAnchor(this.messageContent, this.isConvertDisable);
				this.messageContent = replaceIdLink(this);
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

			// 更新通知のために最新メッセージ日時を埋め込む
			storeLatestMessageDatetime(response, day);
		});
	}

	function escapeAndSetAnchor(message, isConvertDisable) {
		let mes = message.replace(/(\r\n|\n|\r)/gm, '<br>').split('<br>').map(function(item) { // 先に改行を分割
			item = item.replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;').replace(/"/g, '&quot;').replace(/'/g, '&#39;'); // htmlエスケープ
			// 変換機能
			if (!isConvertDisable) {
				item = item.replace(diceRegex, '<span class="extra-small">$1</span>');
				item = item.replace(fortuneRegex, '<span class="extra-small">$1</span>');
				item = item.replace(orRegex, '<span class="extra-small">$1</span>');
				item = item.replace(whoRegex, '<span class="extra-small">$1</span>');
				item = item.replace(allWhoRegex, '<span class="extra-small">$1</span>');
				const userRandomKeywords = $('#random-keywords').text();
				if (userRandomKeywords != null) {
					$.each(userRandomKeywords.split(','), function(idx, elm) {
						const regex = new RegExp('(\\[\\[' + elm + '\\]\\])', 'g');
						item = item.replace(regex, '<span class="extra-small">$1</span>');
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
		}
		return mes;
	}

	function replaceIdLink(message) {
		if (message.messageType === 'PUBLIC_SYSTEM' && message.messageContent.indexOf('(master)、死亡。') != -1) {
			return message.messageContent.replace(/ \(([^\(]*)\)、/g, '(<a href="javascript:void(0);" data-user-page="$1">$1</a>)、');
		}
		return message.messageContent;
	}

	function storeLatestMessageDatetime(response, day) {
		if (!isInLatestPage(day)) {
			return;
		}
		$('#latest-message-datetime').text(response.latestMessageDatetime);
		$('.glyphicon-refresh').removeClass('flash');
	}

	function isInLatestPage(day) {
		if (latestDay != day) {
			return false;
		}
		if ($('[data-next-page]').length == 0) { // ページングなし
			return true;
		}
		if ($('[data-next-page]').closest('li').hasClass('disabled')) {
			return true; // ページングがあり、次のページに遷移不可能
		}
		return false;
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
	$('body').on('click', '[data-message-creator-anchor]', function() {
		const messageNumber = $(this).data('message-creator-anchor');
		handlingNumberAnchor($(this), 'CREATOR_SAY', messageNumber);
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
				$('[data-secret-say-target]').addClass('hidden');
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
				case 'SECRET_SAY':
					$sayTextarea.addClass('message-monologue');
					$('[data-secret-say-target]').removeClass('hidden');
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
		updateSayCount($('[data-say-textarea]'));
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
		updateSayCount($(this));
	});

	function updateSayCount($textarea) {
		if ($textarea.length === 0) {
			return;
		}
		// 制限
		const messageType = $('[data-say-type] .active input').val();
		const $countspan = $textarea.closest('form').find('[data-message-count]');
		const restrict = getRestriction(messageType, $countspan);
		let countStr;
		if (restrict.count == null || restrict.count === '') {
			countStr = '';
		} else {
			countStr = '残り ' + restrict.leftCount + '/' + restrict.count + '回, ';
		}
		const len = $textarea.val().length;
		const lenStr = '文字数: ' + len + '/' + restrict.length + ', ';
		const line = $textarea.val().split('\n').length;
		const lineStr = '行数: ' + line + '/20';
		$countspan.text(countStr + lenStr + lineStr);
		const $submitbtn = $textarea.closest('form').find('[data-message-submit-btn]');
		if (len > restrict.length || line > 20 || (restrict.leftCount != null && restrict.leftCount <= 0)) {
			$countspan.addClass('text-danger');
			$submitbtn.prop('disabled', true);
		} else {
			$countspan.removeClass('text-danger');
			$submitbtn.prop('disabled', false);
		}
	}

	function getRestriction(messageType, $countspan) {
		let length = null;
		let count = null;
		let leftCount = null;
		if (messageType === 'NORMAL_SAY') {
			length = $countspan.data('message-restrict-normal-max-length');
			count = $countspan.data('message-restrict-normal-max-count');
			leftCount = $countspan.data('message-restrict-normal-left-count');
		} else if (messageType === 'WEREWOLF_SAY') {
			length = $countspan.data('message-restrict-whisper-max-length');
			count = $countspan.data('message-restrict-whisper-max-count');
			leftCount = $countspan.data('message-restrict-whisper-left-count');
		} else if (messageType === 'MASON_SAY') {
			length = $countspan.data('message-restrict-mason-max-length');
			count = $countspan.data('message-restrict-mason-max-count');
			leftCount = $countspan.data('message-restrict-mason-left-count');
		}
		return {
			length : length != null ? length : 400,
			count : count,
			leftCount : leftCount
		};
	}

	$('body').on('keyup', '[data-creator-say-textarea]', function() {
		const len = $(this).val().length;
		const line = $(this).val().split('\n').length;
		const $countspan = $(this).closest('form').find('[data-message-count]');
		const $submitbtn = $(this).closest('form').find('[data-message-submit-btn]');
		$countspan.text('文字数: ' + len + '/1000, 行数: ' + line + '/40');
		if (len > 1000 || line > 40) {
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
		// 発言抽出
		filterMessage();
		// 日付を跨いでも維持できるように一時的にcookieに入れておく
		const charaFilterArr = $('#modal-filter').find('.bg-info[data-filter-chara-id]').map(function() {
			return String($(this).data('filter-chara-id'));
		});
		const typeFilterArr = $('#modal-filter').find('.bg-info[data-filter-message-type]').map(function() {
			return String($(this).data('filter-message-type'));
		});
		const keywordFilterArr = $('#modal-filter [data-filter-message-keyword]').val().replace(/　/g, ' ').split(' ');
		saveDisplaySetting('filter_village_id', villageId);
		saveDisplaySetting('filter_chara', charaFilterArr.length == 0 ? [] : $(charaFilterArr).get().join(','));
		saveDisplaySetting('filter_type', typeFilterArr.length == 0 ? [] : $(typeFilterArr).get().join(','));
		saveDisplaySetting('filter_keyword', keywordFilterArr.length == 0 ? [] : $(keywordFilterArr).get().join(' '));
		saveDisplaySetting('filter_spoiled_content', $('[data-dsetting-unspoiled]').length != 0 && $('[data-dsetting-unspoiled]').prop('checked'));
		$('#modal-filter').modal('hide');
	});

	function filterMessage() {
		const wholeCharaArr = $('#modal-filter').find('[data-filter-chara-id]').map(function() {
			return String($(this).data('filter-chara-id'));
		});
		const charaFilterArr = $('#modal-filter').find('.bg-info[data-filter-chara-id]').map(function() {
			return String($(this).data('filter-chara-id'));
		});
		const typeFilterArr = $('#modal-filter').find('.bg-info[data-filter-message-type]').map(function() {
			return String($(this).data('filter-message-type'));
		});
		const keywordFilterArr = $('#modal-filter [data-filter-message-keyword]').val().replace(/　/g, ' ').split(' ');

		const doFilterSpiledContent = filterSpoiledContent();
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
			// 全員表示の場合は退村した人も表示する
			if (wholeCharaArr.length != charaFilterArr.length && $.inArray(charaId, charaFilterArr) == -1) {
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
			if (doFilterSpiledContent && $(elm).attr('data-spoiled-content') != undefined) {
				disp = false;
			}

			if (disp) {
				$(elm).removeClass('hidden');
			} else {
				$(elm).addClass('hidden');
			}
		});
	}

	function filterSpoiledContent() {
		const $spoilCheck = $('[data-dsetting-unspoiled]');
		if ($spoilCheck.length == 0 || !$spoilCheck.prop('checked')) {
			$('[data-spoiled-content]').each(function(idx, elm) {
				$(elm).removeClass('hidden');
			});
			$('[data-spoiled-alternative-content]').addClass('hidden');
			return false;
		} else {
			// ネタバレ防止する
			// 発言と部屋割り
			$('[data-spoiled-content]').each(function(idx, elm) {
				$(elm).addClass('hidden');
			});
			// 足音
			$('[data-spoiled-alternative-content]').removeClass('hidden');
			return true;
		}
	}

	// フィルタを引き継ぐ
	function restoreFilter() {
		const filterVillageId = getDisplaySetting('filter_village_id');
		if (filterVillageId == null || filterVillageId != villageId) {
			saveDisplaySetting('filter_village_id', 0);
			return;
		}
		const filterChara = getDisplaySetting('filter_chara');
		const charaFilterArr = filterChara == null || filterChara.length == 0 ? [] : filterChara.split(',');
		const filterType = getDisplaySetting('filter_type');
		const typeFilterArr = filterType == null || filterType.length == 0 ? [] : filterType.split(',');
		// 復元
		$('#modal-filter').find('[data-filter-chara-id]').each(function(idx, elm) {
			const charaId = String($(elm).data('filter-chara-id'));
			if ($.inArray(charaId, charaFilterArr) == -1) {
				$(elm).removeClass('bg-info');
			}
		});
		$('#modal-filter').find('.bg-info[data-filter-message-type]').each(function(idx, elm) {
			const type = String($(elm).data('filter-message-type'));
			if ($.inArray(type, typeFilterArr) == -1) {
				$(elm).removeClass('bg-info');
			}
		});
		$('#modal-filter [data-filter-message-keyword]').val(getDisplaySetting('filter_keyword'));
		if (getDisplaySetting('filter_spoiled_content') && $('[data-dsetting-unspoiled]').length != 0) {
			$('[data-dsetting-unspoiled]').prop('checked', true);
		}
		filterMessage();
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
	$('#cancel-form').on('submit', function() {
		return confirm('本当に廃村にしてよろしいですか？');
	});

	// 投票欄
	$('[data-vote-day]').on('click', function() {
		const sortIndex = $(this).data('vote-day');
		const $table = $('#tab-votelist table');
		let voteList = makeVoteList();
		voteList.sort(function(a, b) {
			// クリックした日の昇順→投票した回数の降順→部屋番号順
			let a_vote_target = a.votes.length < sortIndex + 1 ? 99 : parseInt(a.votes[sortIndex].substr(0, 2));
			let b_vote_target = b.votes.length < sortIndex + 1 ? 99 : parseInt(b.votes[sortIndex].substr(0, 2));
			if (a_vote_target < b_vote_target) {
				return -1;
			} else if (a_vote_target > b_vote_target) {
				return 1;
			}
			return parseInt(a.votes[0].substr(0, 2)) - parseInt(b.votes[0].substr(0, 2));
		});
		const $tbody = $table.find('tbody');
		$tbody.empty();
		$.each(voteList, function() {
			let $tr = $('<tr></tr>');
			$.each(this.votes, function() {
				$tr.append($('<td></td>', {
					text : this
				}));
			});
			$tbody.append($tr);
		});
	});

	$('body').on('click', '#tab-votelist table tbody td', function() {
		const target = $(this).text();
		// すでに色がついているところだったら消して終了
		if ($(this).hasClass('bg-info')) {
			$('#tab-votelist table td').each(function() {
				$(this).removeClass('bg-info');
			});
			return;
		}
		$('#tab-votelist table td').each(function() {
			if ($(this).text() === target) {
				$(this).addClass('bg-info');
			} else {
				$(this).removeClass('bg-info');
			}
		});
	});

	function makeVoteList() {
		let voteList = [];
		const $table = $('#tab-votelist table');
		$table.find('tbody').find('tr').each(function() {
			const $tr = $(this);
			let vote = {
				votes : []
			};
			$tr.find('td').each(function(idx, elm) {
				vote.votes.push($(elm).text());
			});
			voteList.push(vote);
		});
		return voteList;
	}

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
			expires : 365,
			path : '/'
		});
	}

	function makeDisplaySettingObject() {
		return {
			'is_open_situation_tab' : true,
			'is_open_sayform_tab' : true,
			'is_open_skillform_tab' : true,
			'is_open_voteform_tab' : true,
			'is_open_creatorform_tab' : true,
			'bottom_fix_tab' : 'no-fix',
			'is_no_paging' : false,
			'page_size' : 30,
			'auto_refresh' : false
		};
	}

	function getDisplaySetting(key) {
		let displaySetting = $.cookie('village_display_setting');
		// なかったら作成
		if (displaySetting == null || Object.keys(displaySetting).length === 0) {
			displaySetting = makeDisplaySettingObject();
			$.cookie('village_display_setting', displaySetting, {
				expires : 365,
				path : '/'
			});
		}
		// 各項目なかったら作成
		if (displaySetting[key] == null) {
			const tmplDisplaySetting = makeDisplaySettingObject();
			saveDisplaySetting(key, tmplDisplaySetting[key]);
		}
		return displaySetting[key];
	}

	function restoreDisplaySetting() {
		if (getDisplaySetting('is_no_paging')) {
			$('[data-dsetting-nopaging]').prop('checked', true);
		}
		if (getDisplaySetting('auto_refresh')) {
			$('[data-dsetting-autorefresh]').prop('checked', true);
		}
		if (getDisplaySetting('page_size')) {
			$('[data-dsetting-pagesize]').val(getDisplaySetting('page_size'));
		}
		if (!getDisplaySetting('is_open_situation_tab')) {
			$('[data-situation-tab-open]').click();
		}
		if (!getDisplaySetting('is_open_sayform_tab')) {
			$('[data-sayform-tab-open]').click();
		}
		if (!getDisplaySetting('is_open_skillform_tab')) {
			$('[data-skillform-tab-open]').click();
		}
		if (!getDisplaySetting('is_open_voteform_tab')) {
			$('[data-voteform-tab-open]').click();
		}
		if (!getDisplaySetting('is_open_creatorform_tab')) {
			$('[data-creatorform-tab-open]').click();
		}
		if (!getDisplaySetting('is_open_participateform_tab')) {
			$('[data-participateform-tab-open]').click();
		}
		if (!getDisplaySetting('is_open_changeskillform_tab')) {
			$('[data-changeskillform-tab-open]').click();
		}
		if (!getDisplaySetting('is_open_leaveform_tab')) {
			$('[data-leaveform-tab-open]').click();
		}
		const bottomFixTab = getDisplaySetting('bottom_fix_tab');
		if (bottomFixTab != null && bottomFixTab != '' && $('#' + bottomFixTab).length != 0) {
			$bottomFixTab = $('#' + bottomFixTab);
			$bottomFixTab.addClass('popupform');
			$bottomFixTab.find('[data-bottom-fix]').text('固定解除');
			$('.container').css('padding-bottom', $bottomFixTab.height() + 40);
		} else {
			$('.container').css('padding-bottom', 40);
		}
		restoreFilter(); // フィルタを引き継ぐ
	}

	$('[data-dsetting-nopaging]').on('change', function() {
		const isCheck = $(this).prop('checked');
		saveDisplaySetting('is_no_paging', isCheck ? true : false);
		loadAndDisplayMessage();
	});

	$('[data-dsetting-autorefresh]').on('change', function() {
		const isCheck = $(this).prop('checked');
		saveDisplaySetting('auto_refresh', isCheck ? true : false);
	});

	$('[data-dsetting-pagesize]').on('change', function() {
		const pageSize = $(this).val();
		saveDisplaySetting('page_size', pageSize);
		loadAndDisplayMessage();
	});

	$('[data-situation-tab-open]').on('click', function() {
		const isOpen = $($(this).attr('href')).hasClass('in');
		saveDisplaySetting('is_open_situation_tab', !isOpen); // クリック後は逆になるので、逆を保存
	});

	$('[data-sayform-tab-open]').on('click', function() {
		const isOpen = $($(this).attr('href')).hasClass('in');
		saveDisplaySetting('is_open_sayform_tab', !isOpen); // クリック後は逆になるので、逆を保存
	});

	$('[data-skillform-tab-open]').on('click', function() {
		const isOpen = $($(this).attr('href')).hasClass('in');
		saveDisplaySetting('is_open_skillform_tab', !isOpen); // クリック後は逆になるので、逆を保存
	});

	$('[data-voteform-tab-open]').on('click', function() {
		const isOpen = $($(this).attr('href')).hasClass('in');
		saveDisplaySetting('is_open_voteform_tab', !isOpen); // クリック後は逆になるので、逆を保存
	});

	$('[data-creatorform-tab-open]').on('click', function() {
		const isOpen = $($(this).attr('href')).hasClass('in');
		saveDisplaySetting('is_open_creatorform_tab', !isOpen); // クリック後は逆になるので、逆を保存
	});

	$('[data-participateform-tab-open]').on('click', function() {
		const isOpen = $($(this).attr('href')).hasClass('in');
		saveDisplaySetting('is_open_participateform_tab', !isOpen); // クリック後は逆になるので、逆を保存
	});

	$('[data-changeskillform-tab-open]').on('click', function() {
		const isOpen = $($(this).attr('href')).hasClass('in');
		saveDisplaySetting('is_open_changeskillform_tab', !isOpen); // クリック後は逆になるので、逆を保存
	});

	$('[data-leaveform-tab-open]').on('click', function() {
		const isOpen = $($(this).attr('href')).hasClass('in');
		saveDisplaySetting('is_open_leaveform_tab', !isOpen); // クリック後は逆になるので、逆を保存
	});

	$('[data-bottom-fix]').on('click', function() {
		const $this = $(this);
		const $panelGroup = $this.closest('.panel-group');
		if ($panelGroup.hasClass('popupform')) {
			$panelGroup.removeClass('popupform');
			$this.text('固定');
			saveDisplaySetting('bottom_fix_tab', '');
			$('.container').css('padding-bottom', 40);
		} else {
			$('[data-bottom-fix]').each(function() {
				$(this).closest('.panel-group').removeClass('popupform');
				$(this).text('固定');
			});
			$panelGroup.addClass('popupform');
			$this.text('固定解除');
			saveDisplaySetting('bottom_fix_tab', $panelGroup.attr('id'));
			$('.container').css('padding-bottom', $panelGroup.height() + 40);
		}
	});

	// ----------------------------------------------
	// 参戦
	// ----------------------------------------------
	// 参戦でキャラを画像選択
	$('[data-select-participate-chara]').on('click', function() {
		const charaId = $(this).data('select-participate-chara');
		$('#participate-chara-select').val(charaId);
		$('#modal-select-participate-chara').modal('hide');
	});

	// ----------------------------------------------
	// 残り時間
	// ----------------------------------------------
	let timeIntervalMilliSecond = 500;
	setInterval(function() {
		if ($('#daychange-datetime').length) {
			displayLeftTime();
			$('#time-alert').removeClass('hidden');
		} else {
			timeIntervalMilliSecond = 24 * 60 * 60;
		}
	}, timeIntervalMilliSecond);

	function displayLeftTime() {
		const dayChangeDatetime = new Date($('#daychange-datetime').text());
		const nowDatetime = new Date();
		let diff = dayChangeDatetime.getTime() - nowDatetime.getTime();
		if (diff < 0) {
			diff = 0; // 過ぎてたら00:00:00表示
		}
		let diffHours = Math.floor(diff / (1000 * 60 * 60));
		diffHours = ('0' + diffHours).slice(-2);
		let diffMinutes = Math.floor((diff % (1000 * 60 * 60)) / (1000 * 60));
		diffMinutes = ('0' + diffMinutes).slice(-2);
		let diffSeconds = Math.floor((diff % (1000 * 60)) / 1000);
		diffSeconds = ('0' + diffSeconds).slice(-2);
		$('#left-time').text(diffHours + ':' + diffMinutes + ':' + diffSeconds);
	}

	// ----------------------------------------------
	// 更新通知
	// ----------------------------------------------
	let timeIntervalMilliSecondToRefresh = 30000;
	setInterval(function() {
		if ($('#daychange-datetime').length) {
			$.ajax({
				type : 'GET',
				url : GET_LATEST_MESSAGE_DATETIME_URL,
				data : {
					'villageId' : villageId,
					'day' : day
				}
			}).then(function(response) {
				const $latestMessageDatetime = $('#latest-message-datetime');
				const beforeLatestMessageDatetime = $latestMessageDatetime.text();
				if (beforeLatestMessageDatetime == null || !$.isNumeric(beforeLatestMessageDatetime) || beforeLatestMessageDatetime == '0') {
					$latestMessageDatetime.text(response.latestMessageDatetime);
					return;
				}
				const beforeLatestMessageDatetimeNum = Number(beforeLatestMessageDatetime);
				const afterLatestMessageDatetimeNum = Number(response.latestMessageDatetime);
				if (beforeLatestMessageDatetimeNum < afterLatestMessageDatetimeNum) {
					$('.glyphicon-refresh').addClass('flash');
					if (isInLatestPage(day) && getDisplaySetting('auto_refresh')) {
						loadAndDisplayMessage().then(function() {
							$('#autorefresh-alert').show();
							$('#autorefresh-alert').fadeIn(1000).delay(2000).fadeOut(2000);
						});
					}
				}
			});
		} else {
			timeIntervalMilliSecond = 24 * 60 * 60 * 1000;
		}
	}, timeIntervalMilliSecondToRefresh);

	// ----------------------------------------------
	// ユーザページリンク
	// ----------------------------------------------
	$('body').on('click', '[data-user-page]', function() {
		const userName = $(this).text();
		window.open(contextPath + 'user/' + userName);
	});
});