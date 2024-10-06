$(function () {
    'use stricts'

    // ----------------------------------------------
    // def
    // ----------------------------------------------
    const villageId = $("[data-village-id]").data('village-id');
    const day = $("[data-day]").data('day');
    const GET_MESSAGE_URL = contextPath + 'village/getMessageList';
    const VILLAGE_UPDATE_URL = contextPath + 'village/' + villageId + '/update';
    const GET_LATEST_MESSAGE_DATETIME_URL = contextPath + 'village/getLatestMessageDatetime';
    const GET_ANCHOR_MESSAGE_URL = contextPath + 'village/getAnchorMessage';
    const GET_ATTACKTARGET_URL = contextPath + 'village/getAttackTargetList';
    const GET_FOOTSTEP_URL = contextPath + 'village/getFootstepList';
    const GET_SELECTABLE_CHARA_URL = contextPath + 'getSelectableCharaList/' + villageId;
    const GET_FACEIMG_URL = contextPath + 'getFaceImgUrl/' + villageId;
    const SAY_CONFIRM_URL = contextPath + 'village/' + villageId + '/confirm';
    const ACTION_CONFIRM_URL = contextPath + 'village/' + villageId + '/action-confirm';
    const PARTICIPANTS_URL = contextPath + 'village/' + villageId + '/getParticipants';
    const messageTemplate = Handlebars.compile($("#message-template").html());
    const messagePartialTemplate = Handlebars.compile($("#message-partial-template").html());
    Handlebars.registerPartial('messagePartial', messagePartialTemplate);
    const participantsTemplate = Handlebars.compile($("#participants-template").html());
    const $sayTextarea = $('#sayform [data-say-textarea]');
    const $sayTypeArea = $('[data-say-type]');
    const $abilityArea = $('[data-ability]');
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
    let canAutoRefresh = true; // 発言確認中はfalseになる
    let filterToParticipantIds = [];
    let filterParticipantIds = [];
    let filterTypes = [];
    let filterKeywords = [];
    let filterSpoiled = false;

    init();

    function init() {
        restoreFilter();
        restoreDisplaySetting();
        // 日付クリックで遷移した場合は1ページ目を表示、そうでなければ最新を表示
        if (location.href.indexOf('/day') !== -1) {
            loadAndDisplayMessage(1);
        } else {
            loadAndDisplayMessage(null, true);
        }
        changeSayTextAreaBackgroundColor(); // 画面表示時にも切り替える
        if ($('[data-attacker-select]').length > 0) {
            // 選択していた襲撃対象をプルダウンから選択する
            const nowSelectedTarget = $('[data-selected-attack-target]').data('selected-attack-target');
            // 選択していた足音をプルダウンから選択する
            const nowSelectedFootstep = $('[data-selected-footstep]').data('selected-footstep');
            replaceAttackTargetList(nowSelectedTarget, nowSelectedFootstep); // 画面表示時にも取得して切り替える
        } else if ($('[data-footstep-select]').length > 0) {
            // 選択していた足音をプルダウンから選択する
            const nowSelectedFootstep = $('[data-selected-footstep]').data('selected-footstep');
            replaceFootstepList(nowSelectedFootstep);
        }
        if ($('#participate-charachip-select').length > 0) {
            // 参戦可能なキャラを読み込んで選択状態にする
            loadSelectableCharaList($('[data-selected-chara]').data('selected-chara'));
        }

        selectDefaultFootsteps(); // 狐と狂人だったら選択していた足音の部屋を選択状態にする
        $('[data-toggle="tooltip"]').tooltip();
    }

    // ----------------------------------------------
    // ページング
    // ----------------------------------------------
    $('body').on('click', '[data-prev-page]', function () {
        const currentPage = parseInt($(this).data('prev-page'));
        loadAndDisplayMessage(currentPage - 1).then(function () {
            gotoHead();
        });
    });
    $('body').on('click', '[data-next-page]', function () {
        const currentPage = parseInt($(this).data('next-page'));
        loadAndDisplayMessage(currentPage + 1).then(function () {
            gotoHead();
        });
    });
    $('body').on('click', '[data-pagenum]', function () {
        loadAndDisplayMessage(parseInt($(this).data('pagenum'))).then(function () {
            gotoHead();
        });
    });
    $('body').on('click', '[data-latest-message]', function () {
        loadAndDisplayMessage(null, true).then(function () {
            gotoHead();
        });
    });

    // ----------------------------------------------
    // メッセージ取得
    // ----------------------------------------------
    function loadAndDisplayMessageWithCurrentSetting() {
        const $latest = $('[data-latest-message]')
        if ($latest.length === 0) {
            return loadAndDisplayMessage();
        }
        const isDispLatest = $latest.closest('li').hasClass('active');
        return loadAndDisplayMessage(null, isDispLatest);
    }

    function loadAndDisplayMessage(pageNum, isDispLatest) {
        canAutoRefresh = true; // 発言確認されたままだと自動更新されないのでここで解除
        $("[data-message-area]").addClass('loading');
        const isNoPaging = getDisplaySetting('is_no_paging');
        const pageSize = getDisplaySetting('page_size');
        return $.ajax({
            type: 'GET', url: GET_MESSAGE_URL, data: {
                'villageId': villageId,
                'day': day,
                'pageSize': pageSize != null ? pageSize : 30,
                'pageNum': isNoPaging ? null : pageNum,
                'toParticipantIds': filterToParticipantIds.join(','),
                'types': filterTypes.join(','),
                'participantIds': filterParticipantIds.join(','),
                'keywords': filterKeywords.join(' '),
                'isPaging': isNoPaging == null ? true : !isNoPaging,
                'isDispLatest': isDispLatest == null ? false : isDispLatest
            }
        }).then(function (response) {
            // htmlエスケープと、アンカーの変換を行う
            $.each(response.messageList, function () {
                this.messageContent = escapeAndSetAnchor(this.messageContent, this.isConvertDisable);
                this.messageContent = replaceIdLink(this);
            });
            $("[data-message-area]").html(messageTemplate(response));
            // 画像を大きく表示
            if (getDisplaySetting('is_disp_image_large')) {
                $('[data-message-area] .message-face img').each(function () {
                    $(this).attr('width', parseInt($(this).attr('width')) * 2);
                    $(this).attr('height', parseInt($(this).attr('height')) * 2)
                });
            }
            $("[data-message-area]").removeClass('loading');

            // 必要ならネタバレ防止
            filterSpoiledContent();

            // 更新通知のために最新メッセージ日時を埋め込む
            storeLatestMessageDatetime(response, day);

            // 参加者一覧があったら追加でメッセージを読み込んで埋め込む
            loadParticipantsIfNeeded();
        });
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

    function replaceIdLink(message) {
        if (message.messageType === 'PUBLIC_SYSTEM' && message.messageContent.indexOf('(master)、死亡') != -1) {
            return message.messageContent.replace(/ \(([^\(]*)\)、/g, '(<a href="javascript:void(0);" data-user-page="$1">$1</a>)、');
        }
        return message.messageContent;
    }

	function updateVillage() {
        return $.ajax({
            type: 'POST',
            url: VILLAGE_UPDATE_URL
        }).then(function (response) {
            // 最新の日付が変わったら通知
            if (latestDay != null && latestDay < response.latestDay) {
                $('.daychange-alert').css('display', 'block');
            }
            latestDay = response.latestDay;

            // セッションが切れていたら通知
            const $userAlert = $('#user-alert');
            if (!response.login && $userAlert.length > 0) {
            	$userAlert.removeClass('alert-info')
            	$userAlert.addClass('alert-danger')
            	$userAlert.text('要再ログイン')
            }
        });
    }

    function loadParticipantsIfNeeded() {
        if ($('#participants').length === 0) return;
        $.ajax({
            type: 'GET', url: PARTICIPANTS_URL
        }).then(function (response) {
            $('#participants').find('.message-public-system').html(participantsTemplate(response));
        });
    }

    function storeLatestMessageDatetime(response, day) {
        if (!isInLatestPage(day)) {
            return;
        }
        const current = $('#latest-message-datetime').text();
        const resTime = response.latestMessageDatetime;
        if (current == null || current === '' || parseInt(current) < resTime) {
            $('#latest-message-datetime').text(response.latestMessageDatetime);
        }
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

    // ----------------------------------------------
    // 発言
    // ----------------------------------------------
    // 発言種別に応じて発言エリアの色を分ける
    function changeSayTextAreaBackgroundColor() {
        if ($sayTypeArea == null) {
            return;
        }
        $sayTypeArea.find('label').each(function () {
            if ($(this).hasClass('active')) {
                var sayType = $(this).find('input').val();
                $sayTextarea.removeClass().addClass('form-control');
                $('[data-secret-say-target]').addClass('hidden');
                switch (sayType) {
                    case 'NORMAL_SAY':
                        changeFaceTypeIfNeeded('NORMAL');
                        break;
                    case 'WEREWOLF_SAY':
                        $sayTextarea.addClass('message-werewolf');
                        changeFaceTypeIfNeeded('WEREWOLF');
                        break;
                    case 'MASON_SAY':
                        $sayTextarea.addClass('message-mason');
                        changeFaceTypeIfNeeded('MASON');
                        break;
                    case 'LOVERS_SAY':
                        $sayTextarea.addClass('message-lover');
                        changeFaceTypeIfNeeded('LOVER');
                        break;
                    case 'TELEPATHY':
                        $sayTextarea.addClass('message-telepathy');
                        changeFaceTypeIfNeeded('SECRET');
                        break;
                    case 'MONOLOGUE_SAY':
                        $sayTextarea.addClass('message-monologue');
                        changeFaceTypeIfNeeded('MONOLOGUE');
                        break;
                    case 'SECRET_SAY':
                        $sayTextarea.addClass('message-secret');
                        $('[data-secret-say-target]').removeClass('hidden');
                        changeFaceTypeIfNeeded('SECRET');
                        break;
                    case 'GRAVE_SAY':
                        $sayTextarea.addClass('message-grave');
                        changeFaceTypeIfNeeded('GRAVE');
                        break;
                    case 'SPECTATE_SAY':
                        $sayTextarea.addClass('message-spectate');
                        changeFaceTypeIfNeeded('NORMAL');
                        break;
                    default:
                        break;
                }
                return;
            }
        });
        updateSayCount($('[data-say-textarea]'));
    }

    $sayTypeArea.on('change', 'input[name=messageType]', function () {
        changeSayTextAreaBackgroundColor();
    });

    // 可能なら表情を入れ替え
    function changeFaceTypeIfNeeded(faceTypeCode) {
        $('#faceType').find('option').each(function () {
            if ($(this).val() === faceTypeCode) {
                $('#faceType').val(faceTypeCode);
                changeFace(faceTypeCode);
                return false;
            }
        });
    }

    // 表情を入れ替え
    function changeFace(faceTypeCode) {
        $.ajax({
            type: 'GET', url: GET_FACEIMG_URL + '/' + faceTypeCode
        }).then(function (response) {
            if (response == null || response == '') {
                return;
            }
            $('#say-face-img').attr('src', response);
        });
    }

	$('#faceType').on('change', function () {
		changeFace($(this).val());
	});

    $('[data-select-charaimage-facetype]').on('click', function () {
        const faceTypeCode = $(this).data('select-charaimage-facetype');
        const url = $(this).data('select-charaimage-url');
        $('#faceType').val(faceTypeCode);
        changeFace(faceTypeCode);
        $('#modal-select-chara-image').modal('hide');
    });

    // 秘話モーダルでキャラ選択
    $('[data-select-secretsay-chara]').on('click', function () {
        const charaId = $(this).data('select-secretsay-chara');
        $('#secretSayTargetCharaId').val(charaId);
        $('#modal-select-secretsay-target-chara').modal('hide');
        disabledSubmitButtonIfNeeded();
    });

    // 秘話相手選択
    $('[data-secret-say-target-select]').on('change', function () {
        disabledSubmitButtonIfNeeded();
    });

    // 発言確認を表示
    let sayFormParam = null;
    $('#sayform').on('submit', function () {
        sayFormParam = $(this).serializeArray();
        $.ajax({
            type: 'POST', url: SAY_CONFIRM_URL, data: sayFormParam
        }).then(function (response) {
            if (response == null || response === '') {
                return false;
            }
            canAutoRefresh = false; // 自動でログ更新させない
            // htmlエスケープと、アンカーの変換を行う
            response.message.messageContent = escapeAndSetAnchor(response.message.messageContent, response.message.isConvertDisable);
            const $confirmMessage = $(messagePartialTemplate(response.message));
            $('#message-confirm-area').empty();
            $('#message-confirm-area').append($('<div></div>', {
                text: '以下の内容で発言してよろしいですか？（まだ発言されていません）', 'style': 'margin-bottom: 10px;'
            }));
            $('#message-confirm-area').append($confirmMessage);
            $('#message-confirm-area').append($('<button></button>', {
                text: detectSayLabel(response.message.messageType),
                'class': 'btn btn-success btn-sm pull-right',
                'data-say-determine': ''
            }));
            const $cancelButton = $('<button></button>', {
                text: 'キャンセル', 'class': 'btn btn-default btn-sm'
            });
            $cancelButton.attr('data-say-cancel', 'sayform');
            $cancelButton.data('say-cancel', 'sayform');
            $('#message-confirm-area').append($cancelButton);
            $('#message-confirm-area').show();
            // 発言確認エリアに遷移
            $('html, body').animate({
                scrollTop: $('#message-confirm-area-bottom').offset().top
            }, 200);
        });
        return false; // submitしない
    });

    // アクション確認を表示
    let actionFormParam = null;
    $('#actionform').on('submit', function () {
        actionFormParam = $(this).serializeArray();
        $.ajax({
            type: 'POST', url: ACTION_CONFIRM_URL, data: actionFormParam
        }).then(function (response) {
            if (response == null || response === '') {
                return false;
            }
            canAutoRefresh = false; // 自動でログ更新させない
            // htmlエスケープと、アンカーの変換を行う
            response.message.messageContent = escapeAndSetAnchor(response.message.messageContent, response.message.isConvertDisable);
            const $confirmMessage = $(messagePartialTemplate(response.message));
            $('#message-confirm-area').empty();
            $('#message-confirm-area').append($('<div></div>', {
                text: '以下の内容で発言してよろしいですか？（まだ発言されていません）', 'style': 'margin-bottom: 10px;'
            }));
            $('#message-confirm-area').append($confirmMessage);
            $('#message-confirm-area').append($('<button></button>', {
                text: detectSayLabel(response.message.messageType),
                'class': 'btn btn-success btn-sm pull-right',
                'data-action-determine': ''
            }));
            const $cancelButton = $('<button></button>', {
                text: 'キャンセル', 'class': 'btn btn-default btn-sm'
            });
            $cancelButton.attr('data-action-cancel', 'actionform');
            $cancelButton.data('action-cancel', 'actionform');
            $('#message-confirm-area').append($cancelButton);
            $('#message-confirm-area').show();
            // 発言確認エリアに遷移
            $('html, body').animate({
                scrollTop: $('#message-confirm-area-bottom').offset().top
            }, 200);
        });
        return false; // submitしない
    });

    function detectSayLabel(type) {
        if (type === 'NORMAL_SAY') {
            return '発言する';
        } else if (type === 'WEREWOLF_SAY') {
            return '発言する（囁き）';
        } else if (type === 'MASON_SAY') {
            return '発言する（共鳴）';
        } else if (type === 'LOVERS_SAY') {
            return '発言する（恋人）';
        } else if (type === 'TELEPATHY') {
            return '発言する（念話）';
        } else if (type === 'MONOLOGUE_SAY') {
            return '発言する（独り言）';
        } else if (type === 'SECRET_SAY') {
            return '発言する（秘話）';
        } else if (type === 'GRAVE_SAY') {
            return '呻く';
        } else if (type === 'SPECTATE_SAY') {
            return '発言する';
        } else if (type === 'ACTION') {
            return 'アクション';
        }
        return '発言する';
    }

    $('body').on('click', '[data-say-cancel]', function () {
        canAutoRefresh = true;
        $('#message-confirm-area').empty();
        $('#message-confirm-area').hide();
        const sayAreaId = $(this).data('say-cancel');
        if (sayAreaId === 'sayform' && getDisplaySetting('bottom_fix_tab') === 'sayform-panel') {
            return;
        }
        $('html, body').animate({
            scrollTop: $('#' + sayAreaId).offset().top
        }, 200);
    });

    $('body').on('click', '[data-say-determine]', function () {
        const $confirmForm = $('#say-confirm-form');
        $.each(sayFormParam, function () {
            if (this.name === '_csrf') {
                return true;
            }
            $confirmForm.append($('<input></input>', {
                type: 'hidden', name: this.name, value: this.value
            }));
        });
        $confirmForm.submit();
    });

    $('body').on('click', '[data-action-cancel]', function () {
        canAutoRefresh = true;
        $('#message-confirm-area').empty();
        $('#message-confirm-area').hide();
        const actionAreaId = $(this).data('action-cancel');
        if (actionAreaId === 'actionform' && getDisplaySetting('bottom_fix_tab') === 'actionform-panel') {
            return;
        }
        $('html, body').animate({
            scrollTop: $('#' + actionAreaId).offset().top
        }, 200);
    });

    $('body').on('click', '[data-action-determine]', function () {
        const $confirmForm = $('#action-confirm-form');
        $.each(actionFormParam, function () {
            if (this.name === '_csrf') {
                return true;
            }
            $confirmForm.append($('<input></input>', {
                type: 'hidden', name: this.name, value: this.value
            }));
        });
        $confirmForm.submit();
    });

    // ----------------------------------------------
    // 足音
    // ----------------------------------------------
    // 襲撃候補を入れ替える
    function replaceAttackTargetList(attackTarget, footstep) {
        const $attackerSelect = $('[data-attacker-select]');
        if ($attackerSelect.length === 0) {
            return;
        }
        const charaId = $attackerSelect.val();
        const $targetSelect = $('[data-ability-target-select]');
        return $.ajax({
            type: 'GET', url: GET_ATTACKTARGET_URL, data: {
                'villageId': villageId, 'charaId': charaId
            }
        }).then(function (response) {
            if (response == '') {
                return;
            }
            $targetSelect.empty();
            $.each(response.attackTargetList, function (idx, val) {
                $targetSelect.append($('<option></option>', {
                    'value': val.value, text: val.name
                }));
            });
            if (attackTarget != null) {
                $targetSelect.val(attackTarget)
            }
            replaceFootstepList(footstep);
        });
    }

    // 足音候補を入れ替える
    function replaceFootstepList(footstepTarget) {
        const $footstepSelect = $('[data-footstep-select]');
        if ($footstepSelect.length === 0) {
            return;
        }
        $('[data-skill-submit-button]').prop('disabled', true);
        const $attackerSelect = $('[data-attacker-select]');
        const charaId = $attackerSelect == null ? null : $attackerSelect.val();
        const $targetSelect = $('[data-ability-target-select]');
        const targetCharaId = $targetSelect == null ? null : $targetSelect.val();
        return $.ajax({
            type: 'GET', url: GET_FOOTSTEP_URL, data: {
                'villageId': villageId, 'charaId': charaId, 'targetCharaId': targetCharaId
            }
        }).then(function (response) {
            if (response == '') {
                $('[data-skill-submit-button]').prop('disabled', false);
                return;
            }
            $footstepSelect.empty();
            $.each(response.footstepList, function (idx, val) {
                $footstepSelect.append($('<option></option>', {
                    'value': val, text: val
                }));
            });
            if (footstepTarget != null) {
                $footstepSelect.val(footstepTarget)
            }
            $('[data-skill-submit-button]').prop('disabled', false);
        });
    }

    // 足音候補を選択できる場合、襲撃者や対象を選択時に足音候補を取得して入れ替える
    $abilityArea.on('change', '[data-attacker-select]', function () {
        replaceAttackTargetList();
    });
    $abilityArea.on('change', '[data-ability-target-select]', function () {
        replaceFootstepList();
    });

    // ----------------------------------------------
    // 文字数カウント
    // ----------------------------------------------
    $('body').on('keyup', '[data-say-textarea]', function () {
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

        disabledSubmitButtonIfNeeded();
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
        } else if (messageType === 'LOVERS_SAY') {
            length = $countspan.data('message-restrict-lovers-max-length');
            count = $countspan.data('message-restrict-lovers-max-count');
            leftCount = $countspan.data('message-restrict-lovers-left-count');
        } else if (messageType === 'TELEPATHY') {
            length = $countspan.data('message-restrict-telepathy-max-length');
            count = $countspan.data('message-restrict-telepathy-max-count');
            leftCount = $countspan.data('message-restrict-telepathy-left-count');
        }
        return {
            length: length != null ? length : 400,
            count: count,
            leftCount: leftCount
        };
    }

    function disabledSubmitButtonIfNeeded() {
        const messageType = $('[data-say-type] .active input').val();
        const $textarea = $('[data-say-textarea]');
        const $countspan = $textarea.closest('form').find('[data-message-count]');
        const restrict = getRestriction(messageType, $countspan);
        const $submitbtn = $textarea.closest('form').find('[data-message-submit-btn]');
        const len = $textarea.val().length;
        const line = $textarea.val().split('\n').length;

        if (len > restrict.length || line > 20 || (restrict.leftCount != null && restrict.leftCount <= 0)) {
            $countspan.addClass('text-danger');
            $submitbtn.prop('disabled', true);
        } else {
            $countspan.removeClass('text-danger');
            $submitbtn.prop('disabled', false);
        }
        if ($textarea.val().trim().length == 0) {
            $submitbtn.prop('disabled', true);
        }
        if (messageType === 'SECRET_SAY' && $('[data-secret-say-target] select').val() === '') {
            $submitbtn.prop('disabled', true);
        }
    }

    $('body').on('keyup', '[data-creator-say-textarea]', function () {
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
        if ($(this).val().trim().length == 0) {
            $submitbtn.prop('disabled', true);
        }
    });

    // アクション文字数カウント
    $('body').on('keyup', '[data-action-text]', function () {
        updateActionCount($(this));
    });
    $('body').on('change', '[data-action-select]', function () {
        updateActionCount($(this));
    });

    function updateActionCount($input) {
        if ($input.length === 0) {
            return;
        }
        // 制限
        const $countspan = $input.closest('form').find('[data-action-count]');
        const restrict = getActionRestriction($countspan);
        let countStr;
        if (restrict.count == null || restrict.count === '') {
            countStr = '';
        } else {
            countStr = '残り ' + restrict.leftCount + '/' + restrict.count + '回, ';
        }
        const myself = $input.closest('form').find('input[name=myself]').val();
        const target = $input.closest('form').find('select[name=target]').val();
        const len = myself.length + target.length + $input.val().length;
        const lenStr = '文字数: ' + len + '/' + restrict.length;
        $countspan.text(countStr + lenStr);
        const $submitbtn = $input.closest('form').find('[data-action-submit-btn]');
        if (len > restrict.length || (restrict.leftCount != null && restrict.leftCount <= 0)) {
            $countspan.addClass('text-danger');
            $submitbtn.prop('disabled', true);
        } else {
            $countspan.removeClass('text-danger');
            $submitbtn.prop('disabled', false);
        }
        if ($input.val().trim().length == 0) {
            $submitbtn.prop('disabled', true);
        }
    }

    function getActionRestriction($countspan) {
        let length = $countspan.data('message-restrict-action-max-length');
        return {
            length: length != null ? length : 400,
            count: $countspan.data('message-restrict-action-max-count'),
            leftCount: $countspan.data('message-restrict-action-left-count')
        };
    }

    $('body').on('keyup', '#name,#shortName', function () {
		updateNameCount($(this));
	});

	function updateNameCount() {
		const $nameInput = $('#name');
		const $shortNameInput = $('#shortName');
		const $countspan = $nameInput.closest('form').find('[data-name-count]');
		const nameLen = $nameInput.val().length;
		const shortNameLen = $shortNameInput.val().length;
		$countspan.text('略称: ' + shortNameLen + '/1,  名前: ' + nameLen + '/40');
		const $submitbtn = $nameInput.closest('form').find('[data-name-submit]');
		if (nameLen > 40 || shortNameLen > 1) {
			$countspan.addClass('text-danger');
			$submitbtn.prop('disabled', true);
		} else {
			$countspan.removeClass('text-danger');
			$submitbtn.prop('disabled', false);
		}
	}

    $('body').on('keyup', '#memo', function () {
		updateMemoCount($(this));
	});

	function updateMemoCount($input) {
		if ($input.length === 0) {
			return;
		}
		const $countspan = $input.closest('form').find('[data-memo-count]');
		const len = $input.val().length;
		$countspan.text('文字数: ' + len + '/20');
		const $submitbtn = $input.closest('form').find('[data-memo-submit]');
		if (len > 20) {
			$countspan.addClass('text-danger');
			$submitbtn.prop('disabled', true);
		} else {
			$countspan.removeClass('text-danger');
			$submitbtn.prop('disabled', false);
		}
	}

    // 画面上部遷移
    $('body').on('click', '[data-goto-top]', function () {
        gotoHead();
        return false;
    });

    function gotoHead() {
        $('html, body').animate({
            scrollTop: 0
        }, 200);
    }

    // 画面下部遷移
    $('body').on('click', '[data-goto-bottom]', function () {
        $('html, body').animate({
            scrollTop: $('#bottom').offset().top
        }, 200);
        return false;
    });

    // 画面下部遷移
    $('body').on('click', '[data-goto-vote]', function () {
        $('html, body').animate({
            scrollTop: $('#voteform-panel').offset().top
        }, 200);
        return false;
    });

    // 更新
    $('body').on('click', '[data-refresh]', function () {
        loadAndDisplayMessageWithCurrentSetting();
    });

    // 足音選択
    $('[data-footstep-select-table] td').on('click', function () {
        const $table = $('[data-footstep-select-table]');
        const $footstepHdInput = $('[data-footstep-hd-input]');
        const $footstepInput = $('[data-footstep-input]');

        $(this).toggleClass('footstep-selected-room');

        let footsteps = [];
        $table.find('.footstep-selected-room').each(function () {
            footsteps.push($(this).data('footstep-room-number'));
        });

        if (footsteps.length < 1) {
            $footstepHdInput.val('なし');
            $footstepInput.text('なし');
        } else {
            footsteps.sort(function (f1, f2) {
                return parseInt(f1) - parseInt(f2);
            });
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
        $table.find('[data-footstep-room-number]').each(function () {
            if ($.inArray(String($(this).data('footstep-room-number')), footsteps) != -1) {
                $(this).addClass('footstep-selected-room');
            }
        });
    }

    // ----------------------------------------------
    // 抽出
    // ----------------------------------------------
    // キャラクター
    $('[data-filter-participant-allon]').on('click', function () {
        doFilterParticipantAllOn();
    });

    function doFilterParticipantAllOn() {
        $('#filter-character').find('input').each((idx, elm) => {
            $(elm).prop('checked', true);
        });
    }

    $('[data-filter-participant-alloff]').on('click', function () {
        doFilterParticipantAllOff();
    });

    function doFilterParticipantAllOff() {
        $('#filter-character').find('input').each((idx, elm) => {
            $(elm).prop('checked', false);
        });
    }

    $('[data-filter-participant-reverse]').on('click', function () {
        $('#filter-character').find('input').each((idx, elm) => {
            const checked = $(elm).prop('checked');
            $(elm).prop('checked', !checked);
        });
    });

    // 宛先
    $('[data-filter-to-participant-allon]').on('click', function () {
        doFilterToParticipantAllOn();
    });

    function doFilterToParticipantAllOn() {
        $('#filter-to-character').find('input').each((idx, elm) => {
            $(elm).prop('checked', true);
        });
    }

    $('[data-filter-to-participant-alloff]').on('click', function () {
        doFilterToParticipantAllOff();
    });

    function doFilterToParticipantAllOff() {
        $('#filter-to-character').find('input').each((idx, elm) => {
            $(elm).prop('checked', false);
        });
    }

    $('[data-filter-to-participant-reverse]').on('click', function () {
        $('#filter-to-character').find('input').each((idx, elm) => {
            const checked = $(elm).prop('checked');
            $(elm).prop('checked', !checked);
        });
    });

    // 自分宛
    $('[data-filter-to-me]').on('click', function () {
        const myself = $(this).data('filter-to-me');
        $('#filter-to-character').find('input').each((idx, elm) => {
            const value = $(elm).val();
            $(elm).prop('checked', value == myself);
        });
    });

    // 発言種別
    $('[data-filter-type-allon]').on('click', function () {
        doFilterTypeAllOn();
    });

    function doFilterTypeAllOn() {
        $('#filter-type').find('label').each(function () {
            $(this).addClass('active');
        });
    }

    $('[data-filter-type-alloff]').on('click', function () {
        $('#filter-type').find('label').each(function () {
            $(this).removeClass('active');
        });
    });

    $('[data-filter-type-reverse]').on('click', function () {
        $('#filter-type').find('label').each(function () {
            if ($(this).hasClass('active')) {
                $(this).removeClass('active');
            } else {
                $(this).addClass('active');
            }
        });
    });

    // キーワード
    $('[data-filter-message-clear]').on('click', function () {
        resetKeyword();
    });

    function resetKeyword() {
        $('#modal-filter [data-filter-message-keyword]').val('');
    }

    $('[data-filter-submit]').on('click', function () {
        doFilter();
    });

	$('[data-filter-newtab-submit]').on('click', function () {
		doFilterWithNewTab();
	});

	$('[data-filter-reset]').on('click', function () {
		if (window.confirm('抽出条件をリセットしてよろしいですか？')) {
			resetFilter();
			doFilter();
		}
	});

	$('[data-filter-shortcut-werewolf-say]').on('click', function () {
		sayShortcutFilter('WEREWOLF_SAY');
	});

	$('[data-filter-shortcut-mason-say]').on('click', function () {
		sayShortcutFilter('MASON_SAY');
	});

	$('[data-filter-shortcut-lovers-say]').on('click', function () {
		sayShortcutFilter('LOVERS_SAY');
	});

	$('[data-filter-shortcut-telepathy]').on('click', function () {
		sayShortcutFilter('TELEPATHY');
	});

	$('[data-filter-shortcut-tome]').on('click', function () {
		resetFilter();
		const myself = $('#filter-to-character [data-filter-to-me]').data('filter-to-me');
		$('#filter-to-character').find('input').each((idx, elm) => {
			const value = $(elm).val();
			$(elm).prop('checked', value == myself);
		});
		doFilter();
	});

	$('[data-filter-shortcut-keyword]').on('click', function () {
		resetFilter();
		const keyword = $('#keyword').val();
		$('#modal-filter [data-filter-message-keyword]').val(keyword);
		doFilter();
	});

	function sayShortcutFilter(type) {
		resetFilter();
		$('#filter-type').find('label').each(function () {
			if ($(this).find('input').val() === type) {
				$(this).addClass('active');
			} else {
				$(this).removeClass('active');
			}
		});
		doFilter();
	}

    function doFilter() {
        filterTypes = $('#filter-type').find('label.active input').map(function () {
            return $(this).val();
        }).get();
        if (filterTypes.length == $('#filter-type label').length) {
			filterTypes = []; // 全部表示する場合は空にする
        }
        filterParticipantIds = $('#filter-character').find('input').filter((idx, elm) => {
            return $(elm).prop('checked');
        }).map((idx, elm) => $(elm).val()).get().sort();
        if (filterParticipantIds.length == $('#filter-character input').length) {
        	filterParticipantIds = []; // 全部表示する場合は空にする
        }
        filterToParticipantIds = $('#filter-to-character').find('input').filter((idx, elm) => {
            return $(elm).prop('checked');
        }).map((idx, elm) => $(elm).val()).get().sort();
        if (filterToParticipantIds.length == $('#filter-to-character input').length) {
			filterToParticipantIds = []; // 全部表示する場合は空にする
		}
        filterKeywords = $('#modal-filter [data-filter-message-keyword]').val().replace(/　/g, ' ').split(' ');
        filterSpoiled = $('[data-dsetting-unspoiled]').prop('checked');

		// URLパラメータ設定
		const url = new URL(location);
        if (filterParticipantIds.length > 0) {
			url.searchParams.set('fpid', filterParticipantIds.join(','));
        } else {
        	url.searchParams.delete('fpid');
        }
        if (filterTypes.length > 0) {
        	url.searchParams.set('typ', filterTypes.join(','));
        } else {
        	url.searchParams.delete('typ');
        }
        if (filterKeywords.length > 0 && filterKeywords[0] !== '') {
        	url.searchParams.set('kwd', filterKeywords.join(' '));
        } else {
        	url.searchParams.delete('kwd');
        }
        if (filterSpoiled) {
        	url.searchParams.set('spl', 'true');
        } else {
        	url.searchParams.delete('spl');
        }
        if (filterToParticipantIds.length > 0) {
        	url.searchParams.set('tpid', filterToParticipantIds.join(','));
        } else {
        	url.searchParams.delete('tpid');
        }

		// 発言読み込み
		loadAndDisplayMessageWithCurrentSetting();
		window.history.pushState({}, "", url);
		addFilterParameterToDayLink();

        // 抽出中ならfooterボタンをactiveに
        if (filterTypes.length > 0 && filterTypes.length != $('#filter-type label').length
            || filterParticipantIds.length > 0 && filterParticipantIds[0] !== ''
            || filterKeywords.length > 0 && filterKeywords[0] !== ''
            || filterSpoiled
            || filterToParticipantIds.length > 0 && filterToParticipantIds[0] !== ''
        ) {
            $('#filter-button').addClass('active');
            $('#filter-buttom-text').text('抽出中');
        } else {
            $('#filter-button').removeClass('active');
            $('#filter-buttom-text').text('抽出');
        }

        $('#modal-filter').modal('hide');
    }

    function doFilterWithNewTab() {
		let types = $('#filter-type').find('label.active input').map(function () {
			return $(this).val();
		}).get();
		if (types.length == $('#filter-type label').length) {
			types = []; // 全部表示する場合は空にする
		}
		let participantIds = $('#filter-character').find('input').filter((idx, elm) => {
			return $(elm).prop('checked');
		}).map((idx, elm) => $(elm).val()).get().sort();
		if (participantIds.length == $('#filter-character input').length) {
			participantIds = []; // 全部表示する場合は空にする
		}
		let toParticipantIds = $('#filter-to-character').find('input').filter((idx, elm) => {
			return $(elm).prop('checked');
		}).map((idx, elm) => $(elm).val()).get().sort();
		if (toParticipantIds.length == $('#filter-to-character input').length) {
			toParticipantIds = []; // 全部表示する場合は空にする
		}
		keywords = $('#modal-filter [data-filter-message-keyword]').val().replace(/　/g, ' ').split(' ');
		spoiled = $('[data-dsetting-unspoiled]').prop('checked');

		// URLパラメータ設定
		const url = new URL(location);
		if (participantIds.length > 0) {
			url.searchParams.set('fpid', participantIds.join(','));
		} else {
			url.searchParams.delete('fpid');
		}
		if (types.length > 0) {
			url.searchParams.set('typ', types.join(','));
		} else {
			url.searchParams.delete('typ');
		}
		if (keywords.length > 0 && keywords[0] !== '') {
			url.searchParams.set('kwd', keywords.join(' '));
		} else {
			url.searchParams.delete('kwd');
		}
		if (spoiled) {
			url.searchParams.set('spl', 'true');
		} else {
			url.searchParams.delete('spl');
		}
		if (toParticipantIds.length > 0) {
			url.searchParams.set('tpid', toParticipantIds.join(','));
		} else {
			url.searchParams.delete('tpid');
		}

		window.open(url);
	}

    function addFilterParameterToDayLink() {
    	const searchParams = $(location).attr('search');
    	$('[data-day-link]').each((idx, elm) => {
			$(elm).attr('href', function (idx, attr) {
				const href = attr.split('?')[0];
				return href + searchParams;
			});
		});
    }

    function resetFilter() {
        doFilterParticipantAllOff();
        doFilterToParticipantAllOff();
        doFilterTypeAllOn();
        resetKeyword();
        $('[data-dsetting-unspoiled]').prop('checked', false);
    }

    function filterSpoiledContent() {
        if (!filterSpoiled) {
            $('[data-spoiled-content]').each(function (idx, elm) {
                $(elm).removeClass('hidden');
            });
            $('[data-spoiled-alternative-content]').addClass('hidden');
            return;
        } else {
            // ネタバレ防止する
            // 発言と部屋割り
            $('[data-spoiled-content]').each(function (idx, elm) {
                $(elm).addClass('hidden');
            });
            // 足音
            $('[data-spoiled-alternative-content]').removeClass('hidden');
            return;
        }
    }

    // フィルタを復元
    function restoreFilter() {
    	filterParticipantIds = getParam('fpid');
    	filterTypes = getParam('typ');
    	filterKeywords = getParam('kwd');
    	const spl = getParam('spl');
    	filterSpoiled = spl.length === 1 && spl[0] === 'true';
    	filterToParticipantIds = getParam('tpid');

        // 復元
        $('#filter-character').find('input').each(function (idx, elm) {
            const participantId = $(elm).val();
            $(elm).prop('checked', true)
            if ($.inArray(participantId, filterParticipantIds) === -1) {
                $(elm).prop('checked', false);
            }
        });
        $('#filter-to-character').find('input').each(function (idx, elm) {
            const participantId = $(elm).val();
            $(elm).prop('checked', true)
            if ($.inArray(participantId, filterToParticipantIds) === -1) {
                $(elm).prop('checked', false);
            }
        });
        $('#filter-type').find('label').each((idx, elm) => {
            $(elm).addClass('active');
            if (filterTypes.length > 0 && $.inArray($(elm).find('input').val(), filterTypes) === -1) {
                $(elm).removeClass('active');
            }
        });
        $('#modal-filter [data-filter-message-keyword]').val(filterKeywords.join(' '));
        if (filterSpoiled && $('[data-dsetting-unspoiled]').length != 0) {
            $('[data-dsetting-unspoiled]').prop('checked', true);
        }

        // 抽出中ならfooterボタンをactiveに
        if (filterTypes.length != 0 && filterTypes.length != $('#filter-type label').length
            || filterParticipantIds.length > 0 && filterParticipantIds[0] !== ''
            || filterKeywords.length > 0 && filterKeywords[0] !== ''
            || filterSpoiled
            || filterToParticipantIds.length > 0 && filterToParticipantIds[0] !== ''
        ) {
            $('#filter-button').addClass('active');
            $('#filter-buttom-text').text('抽出中');
        } else {
            $('#filter-button').removeClass('active');
            $('#filter-buttom-text').text('抽出');
        }

        // 日付リンクもフィルタを引き継ぐ
        addFilterParameterToDayLink();
    }

    function getParam(name) {
    	const searchArr = $(location).attr('search').split('?');
    	if (searchArr.length < 2) return [];
    	let values = [];
    	searchArr[1].split('&').forEach(function (param) {
    		const temp = param.split('=');
			if (temp[0] === name) {
				const value = decodeURIComponent(temp[1]);
				if (value == null || value === '') return [];
				if (name === 'kwd') {
					values = value.split('+');
				} else {
					values = value.split(',');
				}
			}
    	});
    	return values;
    }

	// ---------------------------------------------------------------

    // ハッシュタグ
    $('body').on('click', '[data-message-hashtag]', function () {
        const keyword = $(this).data('message-hashtag')
        doFilterParticipantAllOn();
        doFilterToParticipantAllOn();
        doFilterTypeAllOn();
        $('#modal-filter [data-filter-message-keyword]').val(keyword);
        doFilter();
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

    // 退村時は確認フォーム表示
    $('#leave-form').on('submit', function () {
        return confirm('本当に退村してよろしいですか？');
    });
    $('#kick-form').on('submit', function () {
        return confirm('本当に退村させてよろしいですか？');
    });
    $('#cancel-form').on('submit', function () {
        return confirm('本当に廃村にしてよろしいですか？');
    });

    // 投票欄
    $('[data-vote-day]').on('click', function () {
        const sortIndex = $(this).data('vote-day');
        const $table = $('#tab-votelist table');
        let voteList = makeVoteList();
        voteList.sort(function (a, b) {
            // クリックした日の昇順 → 投票した回数の降順 → 部屋番号順
            let a_vote_target = a.votes[sortIndex] === '' ? 99 : a.votes.count < sortIndex + 1 ? 99 : parseInt(a.votes[sortIndex].substr(0, 2));
            let b_vote_target = b.votes[sortIndex] === '' ? 99 : b.votes.count < sortIndex + 1 ? 99 : parseInt(b.votes[sortIndex].substr(0, 2));
            if (a_vote_target < b_vote_target) {
                return -1;
            } else if (a_vote_target > b_vote_target) {
                return 1;
            }
            return parseInt(a.votes[0].substr(0, 2)) - parseInt(b.votes[0].substr(0, 2));
        });
        const $tbody = $table.find('tbody');
        $tbody.empty();
        $.each(voteList, function () {
            let $tr = $('<tr></tr>');
            $.each(this.votes, function () {
                $tr.append($('<td></td>', {
                    text: this
                }));
            });
            $tbody.append($tr);
        });
    });

    $('body').on('click', '#tab-votelist table tbody td', function () {
        const target = $(this).text();
        if (target === '') return;
        // すでに色がついているところだったら消して終了
        if ($(this).hasClass('bg-info')) {
            $('#tab-votelist table td').each(function () {
                $(this).removeClass('bg-info');
            });
            return;
        }
        $('#tab-votelist table td').each(function () {
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
        $table.find('tbody').find('tr').each(function () {
            const $tr = $(this);
            let vote = {
                votes: [], count: 0
            };
            $tr.find('td').each(function (idx, elm) {
                vote.votes.push($(elm).text());
                vote.count++;
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
        if (!getDisplaySetting('is_no_paging')) {
            $('[data-dsetting-paging]').prop('checked', true);
        }
        if (getDisplaySetting('auto_refresh')) {
            $('[data-dsetting-autorefresh]').prop('checked', true);
        }
        if (getDisplaySetting('page_size')) {
            $('[data-dsetting-pagesize]').val(getDisplaySetting('page_size'));
        }
        if (getDisplaySetting('is_disp_random_tag_area')) {
            $('[data-dsetting-disp-random-tag-area]').prop('checked', true);
            $('[data-random-tag-area]').removeClass('hidden');
        }
        if (getDisplaySetting('is_disp_image_large')) {
            $('[data-dsetting-disp-image-large]').prop('checked', true);
        }
        if (getDisplaySetting('is_disp_message_large')) {
            $('[data-dsetting-disp-message-large]').prop('checked', true);
            $('.village-wrapper').addClass('large')
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
        if (!getDisplaySetting('is_open_switchparticipateform_tab')) {
            $('[data-switchparticipateform-tab-open]').click();
        }
        if (!getDisplaySetting('is_open_changeskillform_tab')) {
            $('[data-changeskillform-tab-open]').click();
        }
        if (!getDisplaySetting('is_open_leaveform_tab')) {
            $('[data-leaveform-tab-open]').click();
        }
        if (!getDisplaySetting('is_open_actionform_tab')) {
            $('[data-actionform-tab-open]').click();
        }
        if (!getDisplaySetting('is_open_changenameform_tab')) {
            $('[data-changenameform-tab-open]').click();
        }
        if (!getDisplaySetting('is_open_facetypeform_tab')) {
            $('[data-facetypeform-tab-open]').click();
        }
        if (!getDisplaySetting('is_open_adminform_tab')) {
            $('[data-adminform-tab-open]').click();
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
        // 年齢制限確認
        const agelimit = $('[data-agelimit]').data('agelimit');
        const isLimited = agelimit === 'R15' || agelimit === 'R18';
        const agelimitConfirm = String(getDisplaySetting('already_agelimit_confirm'));
        const ageLimitConfirmVillages = agelimitConfirm == null || agelimitConfirm == '' ? [] : agelimitConfirm.split(',');
        if (isLimited && $.inArray(String(villageId), ageLimitConfirmVillages) == -1) {
            $('#modal-initial-agelimit-confirm').modal('show');
            return;
        }
        // 役職確認
        const confirms = String(getDisplaySetting('already_skill_confirm'));
        const confirmVillages = confirms == null || confirms == '' ? [] : confirms.split(',');
        if ($.inArray(String(villageId), confirmVillages) == -1) {
            $('#modal-initial-skill-description').modal('show');
        }
    }

    $('[data-dsetting-reset]').on('click', function () {
        if (!confirm('本当に表示設定をリセットしてよろしいですか？')) {
            return;
        }
        $.cookie('village_display_setting', makeDisplaySettingObject(), {
            expires: 365, path: '/'
        });
    });

    $('#initial-skill-confirm').on('click', function () {
        const confirms = String(getDisplaySetting('already_skill_confirm'));
        let confirmVillages = confirms == null || confirms == '' ? [] : confirms.split(',');
        confirmVillages.push(villageId);
        saveDisplaySetting('already_skill_confirm', confirmVillages);
    });

    $('#initial-agelimit-confirm').on('click', function () {
        const confirms = String(getDisplaySetting('already_agelimit_confirm'));
        let confirmVillages = confirms == null || confirms == '' ? [] : confirms.split(',');
        confirmVillages.push(villageId);
        saveDisplaySetting('already_agelimit_confirm', confirmVillages);
    });

    $('[data-dsetting-paging]').on('change', function () {
        const isCheck = $(this).prop('checked');
        saveDisplaySetting('is_no_paging', !isCheck);
        loadAndDisplayMessage(null, true);
    });

    $('[data-dsetting-autorefresh]').on('change', function () {
        const isCheck = $(this).prop('checked');
        saveDisplaySetting('auto_refresh', isCheck);
    });

    $('[data-dsetting-pagesize]').on('change', function () {
        const pageSize = $(this).val();
        saveDisplaySetting('page_size', pageSize);
        loadAndDisplayMessageWithCurrentSetting();
    });

    $('[data-dsetting-disp-random-tag-area]').on('change', function () {
        const isCheck = $(this).prop('checked');
        saveDisplaySetting('is_disp_random_tag_area', isCheck);
        if (isCheck) {
            $('[data-random-tag-area]').removeClass('hidden');
        } else {
            $('[data-random-tag-area]').addClass('hidden');
        }
    });

    $('[data-dsetting-disp-image-large]').on('change', function () {
        const isCheck = $(this).prop('checked');
        saveDisplaySetting('is_disp_image_large', isCheck);
    });

    $('[data-dsetting-disp-message-large]').on('change', function () {
        const isCheck = $(this).prop('checked');
        saveDisplaySetting('is_disp_message_large', isCheck);
        if (isCheck) {
            $('.village-wrapper').addClass('large');
        } else {
            $('.village-wrapper').removeClass('large');
        }
    });

    $('[data-situation-tab-open]').on('click', function () {
        const isOpen = $($(this).attr('href')).hasClass('in');
        saveDisplaySetting('is_open_situation_tab', !isOpen); // クリック後は逆になるので、逆を保存
    });

    $('[data-sayform-tab-open]').on('click', function () {
        const isOpen = $($(this).attr('href')).hasClass('in');
        saveDisplaySetting('is_open_sayform_tab', !isOpen); // クリック後は逆になるので、逆を保存
    });

    $('[data-skillform-tab-open]').on('click', function () {
        const isOpen = $($(this).attr('href')).hasClass('in');
        saveDisplaySetting('is_open_skillform_tab', !isOpen); // クリック後は逆になるので、逆を保存
    });

    $('[data-voteform-tab-open]').on('click', function () {
        const isOpen = $($(this).attr('href')).hasClass('in');
        saveDisplaySetting('is_open_voteform_tab', !isOpen); // クリック後は逆になるので、逆を保存
    });

    $('[data-creatorform-tab-open]').on('click', function () {
        const isOpen = $($(this).attr('href')).hasClass('in');
        saveDisplaySetting('is_open_creatorform_tab', !isOpen); // クリック後は逆になるので、逆を保存
    });

    $('[data-participateform-tab-open]').on('click', function () {
        const isOpen = $($(this).attr('href')).hasClass('in');
        saveDisplaySetting('is_open_participateform_tab', !isOpen); // クリック後は逆になるので、逆を保存
    });

    $('[data-switchparticipateform-tab-open]').on('click', function () {
        const isOpen = $($(this).attr('href')).hasClass('in');
        saveDisplaySetting('is_open_switchparticipateform_tab', !isOpen); // クリック後は逆になるので、逆を保存
    });

    $('[data-changeskillform-tab-open]').on('click', function () {
        const isOpen = $($(this).attr('href')).hasClass('in');
        saveDisplaySetting('is_open_changeskillform_tab', !isOpen); // クリック後は逆になるので、逆を保存
    });

    $('[data-leaveform-tab-open]').on('click', function () {
        const isOpen = $($(this).attr('href')).hasClass('in');
        saveDisplaySetting('is_open_leaveform_tab', !isOpen); // クリック後は逆になるので、逆を保存
    });

    $('[data-actionform-tab-open]').on('click', function () {
        const isOpen = $($(this).attr('href')).hasClass('in');
        saveDisplaySetting('is_open_actionform_tab', !isOpen); // クリック後は逆になるので、逆を保存
    });

    $('[data-changenameform-tab-open]').on('click', function () {
        const isOpen = $($(this).attr('href')).hasClass('in');
        saveDisplaySetting('is_open_changenameform_tab', !isOpen); // クリック後は逆になるので、逆を保存
    });

    $('[data-facetypeform-tab-open]').on('click', function () {
        const isOpen = $($(this).attr('href')).hasClass('in');
        saveDisplaySetting('is_open_facetypeform_tab', !isOpen); // クリック後は逆になるので、逆を保存
    });

    $('[data-adminform-tab-open]').on('click', function () {
        const isOpen = $($(this).attr('href')).hasClass('in');
        saveDisplaySetting('is_open_adminform_tab', !isOpen); // クリック後は逆になるので、逆を保存
    });

    $('[data-bottom-fix]').on('click', function () {
        const $this = $(this);
        const $panelGroup = $this.closest('.panel-group');
        if ($panelGroup.hasClass('popupform')) {
            $panelGroup.removeClass('popupform');
            $this.text('固定');
            saveDisplaySetting('bottom_fix_tab', '');
            $('.container').css('padding-bottom', 40);
        } else {
            $('[data-bottom-fix]').each(function () {
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
    $('body').on('change', '#participate-charachip-select', function () {
        loadSelectableCharaList();
    });

    function loadSelectableCharaList(selectedCharaId) {
        const $charaSelect = $('#participate-chara-select');
        const $modalSelectableCharaArea = $('#modal-selectable-chara-area');
        $.ajax({
            type: 'GET',
            url: GET_SELECTABLE_CHARA_URL,
            data: {
                charachipId: $('#participate-charachip-select').val()
            }
        }).then(function (response) {
            if (response == '') {
                return;
            }
            $charaSelect.empty();
            $.each(response, function (idx, val) {
                $charaSelect.append($('<option></option>', {
                    'value': val.id,
                    text: val.name
                }));
            });
            $modalSelectableCharaArea.empty();
            $.each(response, function (idx, val) {
                const $div = $('<div></div>', {
                    class: 'col-xs-6 col-sm-4',
                    style: 'border: 1px solid #464545;'
                });
                const $span1 = $('<span></span>', {
                    style: 'display: block; text-align: center;'
                });
                const $img = $('<img>', {
                    src: val.url,
                    width: val.width,
                    height: val.height,
                    loading: 'lazy'
                });
                $span1.append($img);
                $div.append($span1);
                const $span2 = $('<span></span>', {
                    style: 'display: block; text-align: center;',
                    text: val.name
                });
                $div.append($span2);
                const $selectAnchor = $('<a></a>', {
                    href: 'javascript:void(0)',
                    'data-select-participate-chara': val.id,
                    class: 'btn btn-xs btn-success',
                    style: 'display: block; text-align: center;',
                    text: '選択'
                });
                $div.append($selectAnchor);
                $modalSelectableCharaArea.append($div);
            });

            if (selectedCharaId != null && selectedCharaId !== '') {
                $charaSelect.val(selectedCharaId)
            }
        });
    }

    // 参戦でキャラを画像選択
    $('body').on('click', '[data-select-participate-chara]', function () {
        const charaId = $(this).data('select-participate-chara');
        $('#participate-chara-select').val(charaId);
        $('#modal-select-participate-chara').modal('hide');
    });

    // ----------------------------------------------
    // 残り時間
    // ----------------------------------------------
    let timeIntervalMilliSecond = 500;
    setInterval(function () {
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
        } else if (1000 * 60 * 60 * 100 <= diff) {
            $('#left-time').text('99:59:59');
            return;
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
    setInterval(function () {
        if ($('#daychange-datetime').length) {
            $.ajax({
                type: 'GET', url: GET_LATEST_MESSAGE_DATETIME_URL, data: {
                    'villageId': villageId, 'day': day
                }
            }).then(function (response) {
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
                    if (isInLatestPage(day) && getDisplaySetting('auto_refresh') && canAutoRefresh) {
                        loadAndDisplayMessageWithCurrentSetting().then(function () {
                            $('#autorefresh-alert').show();
                            $('#autorefresh-alert').fadeIn(1000).delay(2000).fadeOut(2000);
                        });
                    }
                }
            });
            updateVillage();
        } else {
            timeIntervalMilliSecond = 24 * 60 * 60 * 1000;
        }
    }, timeIntervalMilliSecondToRefresh);

	// 10秒後に1回だけ表示
    setTimeout(function () {
		if ($('#sayform-content').length > 0 && $('#log-ad-area').length > 0 && $('#log-ad-area').height() <= 10) {
 			alert('当サイトは広告料でサーバー代を賄っています。広告表示にご協力ください。');
		}
    }, 10000);

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
    $('body').on('click', '[data-random-tag-area] label', function () {
        const type = $(this).data('tag-type');
        const tagName = $(this).data('tag-name');
        addTag(tagName, type);
    });

    $('body').on('click', '[data-random-tag-btn]', function () {
        const tagName = $('#random-tag-select').val();
        addTag(tagName, 'single');
    });

    function addTag(tagName, type) {
        const $textarea = $('[data-say-textarea]');
        const currentText = $textarea.val();
        const selectionStart = $textarea.get(0).selectionStart;
        const selectionEnd = $textarea.get(0).selectionEnd;
        if (type === 'single') {
            const value = currentText.substr(0, selectionStart) + '[[' + tagName + ']]' + currentText.substr(selectionStart);
            $textarea.val(value);
        } else {
            let end = tagName.startsWith('#') ? '[[/#]]' : '[[/' + tagName + ']]';
            let value = '';
            if (tagName.startsWith('#')) end = '[[/#]]';
            const prefix = currentText.substr(0, selectionStart);
            const selected = currentText.substr(selectionStart, selectionEnd - selectionStart);
            const suffix = currentText.substr(selectionEnd);
            if (tagName === 'ruby') {
                value = prefix + '[[ruby]]' + selected + '[[rt]][[/rt]][[/ruby]]' + suffix;
            } else {
                value = prefix + '[[' + tagName + ']]' + selected + end + suffix;
            }
            $textarea.val(value);
        }
    }

    $('body').on('click', '[data-reply-to]', function () {
        const $textarea = $('[data-say-textarea]');
        if ($textarea.length <= 0) return;
        const anchorStr = $(this).data('reply-to');
        const currentText = $textarea.val();
        const selectionStart = $textarea.get(0).selectionStart;
        const selectionEnd = $textarea.get(0).selectionEnd;
        const value = currentText.substr(0, selectionStart) + anchorStr + currentText.substr(selectionStart);
        $textarea.val(value);
        if (!$('#sayform-panel').hasClass('popupform')) {
            $('html, body').animate({
                scrollTop: $('#sayform-content').offset().top
            }, 200);
        }
        const $message = $(this).closest('[data-message').clone();
        const $replyToContentArea = $('#reply-content')
        $replyToContentArea.empty();
        $replyToContentArea.removeClass('hidden')
        $replyToContentArea.append($message);
    });

    $('body').on('click', '[data-secret-to]', function () {
        const $saytypes = $('[data-say-type]');
        if ($saytypes.length <= 0) return;
        let $secretLabel = null;
        $saytypes.find('label').each(function () {
            const $label = $(this);
            if ($(this).text().trim() === '秘話') $secretLabel = $label;
        });
        if ($secretLabel == null) return;
        const targetCharaId = $(this).closest('[data-chara-id').data('chara-id');
        const currentVal = $('#secretSayTargetCharaId').val();
        $('#secretSayTargetCharaId').val(targetCharaId);
        if ($('#secretSayTargetCharaId').val() == null) {
            $('#secretSayTargetCharaId').val(currentVal);
            alert('秘話できない対象です')
            return;
        }
        $secretLabel.click();
        if (!$('#sayform-panel').hasClass('popupform')) {
            $('html, body').animate({
                scrollTop: $('#sayform-content').offset().top
            }, 200);
        }
        const $message = $(this).closest('[data-message').clone();
        const $replyToContentArea = $('#reply-content')
        $replyToContentArea.empty();
        $replyToContentArea.removeClass('hidden')
        $replyToContentArea.append($message);
    });

    $(document).on('change', ':file', function () {
        const input = $(this)
        const label = input.val().replace(/\\/g, '/').replace(/.*\//, '');
        input.parent().parent().next(':text').val(label);
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
});