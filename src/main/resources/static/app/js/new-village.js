$(function () {

    const GET_DUMMY_CHARA_INFO_URL = contextPath + 'getCharacterList';
    let characterList = null;

    $('#characterSetId').on('change', function () {
        const charaGroupIds = $(this).val();
        replaceCharaSet(charaGroupIds, true);
    });

    if ($('#characterSetId').length != 0) {
        replaceCharaSet($('#characterSetId').val(), false);
    }

    function replaceCharaSet(charaGroupIds, manualChanged) {
        $.ajax({
            type: 'GET',
            url: GET_DUMMY_CHARA_INFO_URL + '?' + $.param({
                charaGroupId: charaGroupIds
            }, true)
        }).then(function (response) {
            characterList = response;
            // 発言欄
            replaceDummyChara(response[0].id, manualChanged);
            // プルダウン
            const $dummyCharaSelect = $('#dummyCharaId');
            $dummyCharaSelect.empty();
            $.each(response, function (idx, elm) {
                $dummyCharaSelect.append($('<option></option>', {
                    text: elm.name,
                    value: elm.id,
                }));
            });
            const firstDummyCharaId = $('[data-dummy-chara-id]').data('dummy-chara-id');
            if (response.some(chara => chara.id === firstDummyCharaId)) {
                $dummyCharaSelect.val(firstDummyCharaId);
                replaceDummyChara(firstDummyCharaId, manualChanged);
            }
        });
    }

    $('#dummyCharaId').on('change', function () {
        replaceDummyChara($(this).val(), true);
    });

    function replaceDummyChara(charaId, manualChanged) {
        const dummyChara = characterList.filter(c => c.id === parseInt(charaId))[0];
        $('#dummy-chara-img').html($('<img />', {
            src: dummyChara.images.list[0].url,
            width: dummyChara.size.width,
            height: dummyChara.size.height
        }));
		$('#dummy-chara-img2').html($('<img />', {
			src: dummyChara.images.list[0].url,
			width: dummyChara.size.width,
			height: dummyChara.size.height
		}));
        $('#dummyCharaName').val(dummyChara.name);
        $('#dummyCharaShortName').val(dummyChara.shortName);
        if (dummyChara.defaultJoinMessage != null && $('#characterSetId').val().length < 2 && dummyChara.defaultJoinMessage !== '') {
            if ($('#dummy-chara-join-message').val() === '') {
                $('#dummy-chara-join-message').val(dummyChara.defaultJoinMessage);
            } else if (manualChanged && window.confirm('ダミーキャラの入村発言を上書きしてもよろしいですか？')) {
                $('#dummy-chara-join-message').val(dummyChara.defaultJoinMessage);
            }
        }
        if (dummyChara.defaultFirstdayMessage != null && $('#characterSetId').val().length < 2 && dummyChara.defaultFirstdayMessage !== '') {
            if ($('#dummy-chara-day1-message').val() === '') {
                $('#dummy-chara-day1-message').val(dummyChara.defaultFirstdayMessage);
            } else if (manualChanged && window.confirm('ダミーキャラの1日目発言を上書きしてもよろしいですか？')) {
                $('#dummy-chara-day1-message').val(dummyChara.defaultFirstdayMessage);
            }
        }
    }

    // 構成
    addPersonNum();

    function addPersonNum() {
        const orgs = $('#organization').val();
        const organization = orgs.split('\n').map(function (elm, idx) {
            return elm.length + '人：' + elm;
        }).join('\n');
        $('#organization').val(organization);
    }

    $('#new-village-form').on('submit', function () {
        const orgs = $('#organization').val();
        const organization = orgs.split('\n').map(function (elm, idx) {
            return elm.split('：')[1]
        }).join('\n');
        $('#organization').val(organization);
    });

    // 発言制限
    $('[data-restrict-check]').on('change', function () {
        handleRestriction($(this));
    });

    initRestrict();

    function initRestrict() {
        $('[data-restrict-check]').each(function () {
            handleRestriction($(this));
        });
    }

    function handleRestriction($checkbox) {
        const checked = $checkbox.prop('checked');
        $checkbox.closest('tr').find('[data-restrict-length]').each(function (idx, elm) {
            if (checked) {
                $(elm).prop('disabled', false);
                $(elm).css('background-color', '');
            } else {
                $(elm).prop('disabled', true);
                $(elm).css('background-color', '#aaaaaa');
            }
        });
        $checkbox.closest('tr').find('[data-restrict-count]').each(function (idx, elm) {
            if (checked) {
                $(elm).prop('disabled', false);
                $(elm).css('background-color', '');
            } else {
                $(elm).prop('disabled', true);
                $(elm).css('background-color', '#aaaaaa');
            }
        });
    }

    $('[data-restrict-copy]').on('click', function () {
        const checked = $('[data-restrict-check]:first').prop('checked');
        const length = $('[data-restrict-length]:first').val();
        const count = $('[data-restrict-count]:first').val();

        $('#say-restriction [data-restrict-check]').each(function () {
            $(this).prop('checked', checked);
        });
        $('#say-restriction [data-restrict-length]').each(function () {
            $(this).val(length);
        });
        $('#say-restriction [data-restrict-count]').each(function () {
            $(this).val(count);
        });
        initRestrict();
    });

    $('#divert-btn').on('click', function () {
        const villageId = $('#divert-village').val();
        const $form = $('#divert-form');
        $form.attr('action', contextPath + 'new-village/divert/' + villageId);
        $form.submit();
    });

    $('#org-check input').on('change', function () {
        showOrganizationForm();
    });
    showOrganizationForm();

    function showOrganizationForm() {
        const isRandom = $('#org-check').find('label').eq(0).hasClass('active');
        $('#random-org').addClass('hidden');
        $('#fix-org').addClass('hidden');
        if (isRandom) {
            $('#random-org').removeClass('hidden');
        } else {
            $('#fix-org').removeClass('hidden');
        }
    }

    $('#charachip-check input').on('change', function () {
        showCharachipForm();
    });
    showCharachipForm();

    function showCharachipForm() {
        const shouldOriginalImage = $('#charachip-check').find('label').eq(1).hasClass('active');
        $('#use-charachip').addClass('hidden');
        $('#use-original-chara').addClass('hidden');
        if (shouldOriginalImage) {
            $('#use-original-chara').removeClass('hidden');
            $('#dummy-chara-img').html($('<img />', {
                src: contextPath + 'app/images/placeholder.png',
                width: 60,
                height: 60
            }));
			$('#dummy-chara-img2').html($('<img />', {
				src: contextPath + 'app/images/placeholder.png',
				width: 60,
				height: 60
			}));
        } else {
            $('#use-charachip').removeClass('hidden');
            replaceCharaSet($('#characterSetId').val(), false);
        }
    }
});