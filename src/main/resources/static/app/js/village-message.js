$(function () {
    'use stricts'

    // ----------------------------------------------
    // def
    // ----------------------------------------------
    const villageId = $("[data-village-id]").data('village-id');
    const GET_MESSAGE_URL = contextPath + 'village/' + villageId + '/getAnchorMessages';
    const GET_ANCHOR_MESSAGE_URL = contextPath + 'village/getAnchorMessage';
    const messageTemplate = Handlebars.compile($("#message-template").html());
    const messagePartialTemplate = Handlebars.compile($("#message-partial-template").html());
    Handlebars.registerPartial('messagePartial', messagePartialTemplate);
    // メッセージ変換機能
    const diceRegex = /(\[\[\d{1}d\d{1,5}\]\]?)/g;
    const fortuneRegex = /(\[\[fortune\]\])/g;
    const orRegex = /(?!\[\[fortune\]\])(\[\[[^\]]*or.*?\]\])/g; // [[fortune]]でなく、さらに]を含まない[[(.*)or(.*?)]]
    const whoRegex = /(?!\[\[allwho\]\])(\[\[who\]\])/g;
    const allWhoRegex = /(\[\[allwho\]\])/g;
    const gwhoRegex = /(\[\[gwho\]\])/g;
    // 文字装飾
    const colorRegex = /\[\[(#[0-9a-fA-F]{6})\]\](.*?)\[\[\/#\]\]/g;
    const boldRegex = /\[\[b\]\](.*?)\[\[\/b\]\]/g;
    const strikeRegex = /\[\[s\]\](.*?)\[\[\/s\]\]/g;
    const largeRegex = /\[\[large\]\](.*?)\[\[\/large\]\]/g;
    const smallRegex = /\[\[small\]\](.*?)\[\[\/small\]\]/g;
    const rubyRegex = /\[\[ruby\]\](.*?)\[\[rt\]\](.*?)\[\[\/rt\]\]\[\[\/ruby\]\]/g;
    const netabareRegex = /\[\[netabare\]\](.*?)\[\[\/netabare\]\]/g;
    const cwRegex = /\[\[cw\]\](.*?)\[\[\/cw\]\]/g;
    const transparencyRegex = /\[\[tp\]\](.*?)\[\[\/tp\]\]/g;
    let latestDay;

    init();

    function init() {
        restoreDisplaySetting();
		loadAndDisplayMessage();
    }

    // ----------------------------------------------
    // メッセージ取得
    // ----------------------------------------------
    function loadAndDisplayMessage() {
    	if (getParam('anchors') == '') {
    		$("[data-message-area]").empty();
    		return;
    	}
        $("[data-message-area]").addClass('loading');
        return $.ajax({
            type: 'GET',
            url: GET_MESSAGE_URL,
            data: {
            	'anchors': getParam('anchors'),
            }
        }).then(function (response) {
            // htmlエスケープと、アンカーの変換を行う
            $.each(response.messageList, function () {
                this.messageContent = escapeAndSetAnchor(this.messageContent, this.isConvertDisable);
            });
            response.allPageCount = 1;
            $("[data-message-area]").html(messageTemplate(response));
            // 画像を大きく表示
            if (getDisplaySetting('is_disp_image_large')) {
                $('[data-message-area] .message-face img').each(function () {
                    $(this).attr('width', parseInt($(this).attr('width')) * 2);
                    $(this).attr('height', parseInt($(this).attr('height')) * 2)
                });
            }
            $("[data-message-area]").removeClass('loading');
        });
    }

    function getParam(name) {
    	const searchArr = $(location).attr('search').split('?');
    	if (searchArr.length < 2) return '';
    	let result = '';
    	searchArr[1].split('&').forEach(function (param) {
    		const temp = param.split('=');
			if (temp[0] === name) {
				result = decodeURIComponent(temp[1]);
				return false;
			}
    	});
		return result;
    }

    function escapeAndSetAnchor(message, isConvertDisable) {
        let mes = message.replace(/(\r\n|\n|\r)/gm, '<br>').split('<br>').map(function (item) { // 先に改行を分割
            item = item.replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;').replace(/"/g, '&quot;') // htmlエスケープ
            // 変換機能
            if (!isConvertDisable) {
                item = item.replace(diceRegex, '<span class="extra-small">$1</span>');
                item = item.replace(fortuneRegex, '<span class="extra-small">$1</span>');
                item = item.replace(orRegex, '<span class="extra-small">$1</span>');
                item = item.replace(whoRegex, '<span class="extra-small">$1</span>');
                item = item.replace(allWhoRegex, '<span class="extra-small">$1</span>');
                item = item.replace(gwhoRegex, '<span class="extra-small">$1</span>');
                const userRandomKeywords = $('#random-keywords').text();
                if (userRandomKeywords != null) {
                    $.each(userRandomKeywords.split(','), function (idx, elm) {
                        const regex = new RegExp('(\\[\\[' + elm + '\\]\\])', 'g');
                        item = item.replace(regex, '<span class="extra-small">$1</span>');
                    });
                }
            }
            // ハッシュタグ
            // 否定後読みが一部ブラウザで対応していないため、代替手段
            // item = item.replace(/(?<!&gt;&gt;|\[\[|\[\[\/)[#＃]([Ａ-Ｚａ-ｚA-Za-z一-鿆0-9０-９ぁ-ヶｦ-ﾟー._-]+)/g, '<a href=\"javascript:void(0);\" data-message-hashtag=\"#$1\">#$1<\/a>');
            const hashArray = item.split('#')
            item = hashArray.map((str, index) => {
                if (index === 0) return str;
                const prev = hashArray[index - 1];
                if (prev.endsWith('&gt;&gt;') || prev.endsWith('[[') || prev.endsWith('[[/')) return '#' + str;
                return str.replace(
                    /^([Ａ-Ｚａ-ｚA-Za-z一-鿆0-9０-９ぁ-ヶｦ-ﾟー._-]+)(.*)/g,
                    '<a href=\"javascript:void(0);\" data-message-hashtag=\"#$1\">#$1<\/a>$2'
                );
            }).join('')
            // シングルクォートはハッシュタグより後で変換する
            item = item.replace(/'/g, '&#39;');
            // アンカー
            item = item.replace(/&gt;&gt;(\d{1,5})/g, '<a href=\"javascript:void(0);\" data-message-anchor=\"$1\">&gt;&gt;$1<\/a>'); // 次にアンカーをaタグにする
            item = item.replace(/&gt;&gt;\+(\d{1,5})/g, '<a href=\"javascript:void(0);\" data-message-grave-anchor=\"$1\">&gt;&gt;\+$1<\/a>');
            item = item.replace(/&gt;&gt;=(\d{1,5})/g, '<a href=\"javascript:void(0);\" data-message-mason-anchor=\"$1\">&gt;&gt;=$1<\/a>');
            item = item.replace(/&gt;&gt;\?(\d{1,5})/g, '<a href=\"javascript:void(0);\" data-message-lover-anchor=\"$1\">&gt;&gt;\?$1<\/a>');
            item = item.replace(/&gt;&gt;_(\d{1,5})/g, '<a href=\"javascript:void(0);\" data-message-telepathy-anchor=\"$1\">&gt;&gt;\_$1<\/a>');
            item = item.replace(/&gt;&gt;@(\d{1,5})/g, '<a href=\"javascript:void(0);\" data-message-spectate-anchor=\"$1\">&gt;&gt;@$1<\/a>');
            item = item.replace(/&gt;&gt;-(\d{1,5})/g, '<a href=\"javascript:void(0);\" data-message-monologue-anchor=\"$1\">&gt;&gt;\-$1<\/a>');
            item = item.replace(/&gt;&gt;\*(\d{1,5})/g, '<a href=\"javascript:void(0);\" data-message-whisper-anchor=\"$1\">&gt;&gt;\*$1<\/a>');
            item = item.replace(/&gt;&gt;#(\d{1,5})/g, '<a href=\"javascript:void(0);\" data-message-creator-anchor=\"$1\">&gt;&gt;\#$1<\/a>');
            item = item.replace(/&gt;&gt;a(\d{1,5})/g, '<a href=\"javascript:void(0);\" data-message-action-anchor=\"$1\">&gt;&gt;a$1<\/a>');
            item = item.replace(/&gt;&gt;s(\d{1,5})/g, '<a href=\"javascript:void(0);\" data-message-secret-anchor=\"$1\">&gt;&gt;s$1<\/a>');
            return item;
        }).join('<br>');
        // 文字装飾
        if (!isConvertDisable) {
            mes = mes.replace(colorRegex, '<span style="color: $1">$2</span>');
            mes = mes.replace(boldRegex, '<strong>$1</strong>');
            mes = mes.replace(strikeRegex, '<span style="text-decoration: line-through;">$1</span>');
            mes = mes.replace(largeRegex, '<span style="font-size: 150%;">$1</span>');
            mes = mes.replace(smallRegex, '<span style="font-size: 80%;">$1</span>');
            mes = mes.replace(rubyRegex, '<ruby>$1<rt>$2</rt></ruby>');
            mes = mes.replace(netabareRegex, '<span class="netabare">$1</span>');
            mes = mes.replace(cwRegex, '<span class="netabare">$1</span>');
            mes = mes.replace(transparencyRegex, '<span class="transparency">$1</span>');
        }
        return mes;
    }

    // ----------------------------------------------
    // アンカー
    // ----------------------------------------------
    $('body').on('click', '[data-message-anchor]', function () {
        const messageNumber = $(this).data('message-anchor');
        handlingNumberAnchor($(this), 'NORMAL_SAY', messageNumber);
    });
    $('body').on('click', '[data-message-grave-anchor]', function () {
        const messageNumber = $(this).data('message-grave-anchor');
        handlingNumberAnchor($(this), 'GRAVE_SAY', messageNumber);
    });
    $('body').on('click', '[data-message-mason-anchor]', function () {
        const messageNumber = $(this).data('message-mason-anchor');
        handlingNumberAnchor($(this), 'MASON_SAY', messageNumber);
    });
    $('body').on('click', '[data-message-lover-anchor]', function () {
        const messageNumber = $(this).data('message-lover-anchor');
        handlingNumberAnchor($(this), 'LOVERS_SAY', messageNumber);
    });
    $('body').on('click', '[data-message-telepathy-anchor]', function () {
        const messageNumber = $(this).data('message-telepathy-anchor');
        handlingNumberAnchor($(this), 'TELEPATHY', messageNumber);
    });
    $('body').on('click', '[data-message-spectate-anchor]', function () {
        const messageNumber = $(this).data('message-spectate-anchor');
        handlingNumberAnchor($(this), 'SPECTATE_SAY', messageNumber);
    });
    $('body').on('click', '[data-message-monologue-anchor]', function () {
        const messageNumber = $(this).data('message-monologue-anchor');
        handlingNumberAnchor($(this), 'MONOLOGUE_SAY', messageNumber);
    });
    $('body').on('click', '[data-message-whisper-anchor]', function () {
        const messageNumber = $(this).data('message-whisper-anchor');
        handlingNumberAnchor($(this), 'WEREWOLF_SAY', messageNumber);
    });
    $('body').on('click', '[data-message-creator-anchor]', function () {
        const messageNumber = $(this).data('message-creator-anchor');
        handlingNumberAnchor($(this), 'CREATOR_SAY', messageNumber);
    });
    $('body').on('click', '[data-message-action-anchor]', function () {
        const messageNumber = $(this).data('message-action-anchor');
        handlingNumberAnchor($(this), 'ACTION', messageNumber);
    });
    $('body').on('click', '[data-message-secret-anchor]', function () {
        const messageNumber = $(this).data('message-secret-anchor');
        handlingNumberAnchor($(this), 'SECRET_SAY', messageNumber);
    });

    function handlingNumberAnchor($anchor, messageType, messageNumber) {
        const $thisMessage = $anchor.closest('[data-message]');
        const anchorClassName = getClassName($anchor) + '_' + messageType.substring(0, 1) + messageNumber;

        if ($('body').find('.' + anchorClassName).length != 0) { // 既に読み込み済みの場合
            $('body').find('.' + anchorClassName).collapse('toggle');
            return false;
        }

        return $.ajax({
            type: 'GET', url: GET_ANCHOR_MESSAGE_URL, data: {
                'villageId': villageId, 'messageNumber': messageNumber, 'messageType': messageType
            }
        }).then(function (response) {
            if (response == '') {
                return;
            }
            // htmlエスケープと、アンカーの変換を行う
            response.message.messageContent = escapeAndSetAnchor(response.message.messageContent, response.message.isConvertDisable);
            let $anchorMessage = makeAnchorMessage($(messagePartialTemplate(response.message)), anchorClassName);
            $thisMessage.after($anchorMessage);
            // 画像を大きく表示
            if (getDisplaySetting('is_disp_image_large')) {
                $anchorMessage.find('.message-face img').each(function () {
                    $(this).attr('width', parseInt($(this).attr('width')) * 2);
                    $(this).attr('height', parseInt($(this).attr('height')) * 2)
                });
            }
            $('.' + anchorClassName).collapse('toggle');
            return false;
        });
    }

    function getClassName($anchor) {
        return $.grep($anchor.closest('[data-message]').attr('class').split(' '), function (elm, idx) {
            return String(elm).indexOf('say') != -1;
        })[0];
    }

    function makeAnchorMessage($message, anchorClassName) {
        var beforeClassName = $.grep($message.attr('class').split(' '), function (elm, idx) {
            return String(elm).indexOf('say') != -1;
        })[0];
        $message.removeClass(beforeClassName);
        $message.addClass(anchorClassName);
        $message.addClass('collapse');
        $message.addClass('well');
        // $message.find('div.message').addClass('bg-white');
        $message.closest('[data-message]').prepend('<span class="btn btn-default btn-sm pull-right close-anchor" style="margin-left:5px; margin-right: -15px;">×</span>');
        return $message;
    }

    // アンカー閉じるボタン
    $('body').on('click', '.close-anchor', function () {
        $(this).closest('[data-message]').collapse('hide');
    });

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

    $('body').on('click', '[data-copy-anchor]', function () {
        const text = $(this).text();
        clipboardCopy(text, 'コピーしました： ' + text);
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
            expires: 365, path: '/'
        });
    }

    function makeDisplaySettingObject() {
        return {
            'is_open_situation_tab': true,
            'is_open_sayform_tab': true,
            'is_open_skillform_tab': true,
            'is_open_voteform_tab': true,
            'is_open_creatorform_tab': true,
            'is_open_participateform_tab': true,
            'is_open_switchparticipateform_tab': true,
            'bottom_fix_tab': 'no-fix',
            'is_no_paging': false,
            'page_size': 30,
            'auto_refresh': false,
            'is_disp_random_tag_area': false,
            'already_skill_confirm': ''
        };
    }

    function getDisplaySetting(key) {
        let displaySetting = $.cookie('village_display_setting');
        // なかったら作成
        if (displaySetting == null || Object.keys(displaySetting).length === 0) {
            displaySetting = makeDisplaySettingObject();
            $.cookie('village_display_setting', displaySetting, {
                expires: 365, path: '/'
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
        const bottomFixTab = getDisplaySetting('bottom_fix_tab');
		$('.container').css('padding-bottom', 40);
        // 年齢制限確認
        const agelimit = $('[data-agelimit]').data('agelimit');
        const isLimited = agelimit === 'R15' || agelimit === 'R18';
        const agelimitConfirm = String(getDisplaySetting('already_agelimit_confirm'));
        const ageLimitConfirmVillages = agelimitConfirm == null || agelimitConfirm == '' ? [] : agelimitConfirm.split(',');
        if (isLimited && $.inArray(String(villageId), ageLimitConfirmVillages) == -1) {
            $('#modal-initial-agelimit-confirm').modal('show');
            return;
        }
    }

    $('#initial-agelimit-confirm').on('click', function () {
        const confirms = String(getDisplaySetting('already_agelimit_confirm'));
        let confirmVillages = confirms == null || confirms == '' ? [] : confirms.split(',');
        confirmVillages.push(villageId);
        saveDisplaySetting('already_agelimit_confirm', confirmVillages);
    });

    // ----------------------------------------------
    // ユーザページリンク
    // ----------------------------------------------
    $('body').on('click', '[data-user-page]', function () {
        const userName = $(this).text();
        window.open(contextPath + 'user/' + userName);
    });

	// ----------------------------------------------
	// 文字装飾
	// ----------------------------------------------
	$('body').on('click', '.netabare', function () {
		$(this).removeClass('netabare');
	});
	$('body').on('click', '.transparency', function () {
		$(this).removeClass('transparency');
	});

	// ----------------------------------------------
	// メッセージ追加
	// ----------------------------------------------
	$('[data-anchor-btn]').on('click', function () {
		loadAnchor();
	});

	function loadAnchor() {
		const anchor = $('[data-anchor]').val();
		let str = '';
		if (anchor.startsWith('>>*')) {
			str = 'w' + anchor.substring(3);
		} else if (anchor.startsWith('>>#')) {
			str = 'c' + anchor.substring(3);
		} else if (anchor.startsWith('>>s')) {
			str = 'S' + anchor.substring(3);
		} else if (anchor.startsWith('>>=')) {
			str = 'm' + anchor.substring(3);
		} else if (anchor.startsWith('>>?')) {
			str = 'l' + anchor.substring(3);
		} else if (anchor.startsWith('>>_')) {
			str = 'f' + anchor.substring(3);
		} else if (anchor.startsWith('>>@')) {
			str = 's' + anchor.substring(3);
		} else if (anchor.startsWith('>>-')) {
			str = 'M' + anchor.substring(3);
		} else if (anchor.startsWith('>>+')) {
			str = 'g' + anchor.substring(3);
		} else if (anchor.startsWith('>>a')) {
			str = 'a' + anchor.substring(3);
		} else if (anchor.startsWith('>>')) {
			str = 'n' + anchor.substring(2);
		} else {
			return;
		}

		if (location.href.indexOf('?') == -1) {
			window.location.replace(location.href + '?anchors=' + str);
		} else {
			window.location.replace(location.href + '_' + str);
		}
		loadAndDisplayMessage();
	}

	$('#scrap-form').submit(function (e) {
		loadAnchor();
		return false;
	});

	$('[data-scrap-remove-btn]').on('click', function () {
		if (location.href.indexOf('?') == -1) {
			return;
		}
		const url = location.href.split('?')[0];
		window.location.replace(url);
		loadAndDisplayMessage();
	});
});