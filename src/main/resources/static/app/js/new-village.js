$(function() {

	const GET_DUMMY_CHARA_INFO_URL = contextPath + '/getCharacterList/';
	let characterList = null;

	$('#characterSetId').on('change', function() {
		const charaGroupId = $(this).val();
		replaceCharaSet(charaGroupId);
	});

	replaceCharaSet($('#characterSetId').val());
	function replaceCharaSet(charaGroupId) {
		$.ajax({
			type : 'GET',
			url : GET_DUMMY_CHARA_INFO_URL + charaGroupId
		}).then(function(response) {
			characterList = response;
			// 発言欄
			replaceDummyChara(response[0].charaId);
			// プルダウン
			const $dummyCharaSelect = $('#dummyCharaId');
			$dummyCharaSelect.empty();
			$.each(response, function(idx, elm) {
				$dummyCharaSelect.append($('<option></option>', {
					text : elm.charaName,
					value : elm.charaId,
				}));
			});
		});
	}
	
	$('#dummyCharaId').on('change', function(){
		replaceDummyChara($(this).val());
	});
	
	function replaceDummyChara(charaId) {
		const dummyChara = characterList.filter(c => c.charaId === parseInt(charaId))[0];
		$('#dummy-chara-img').html($('<img />', {
			src : dummyChara.charaImgUrl,
			width : dummyChara.charaImgWidth,
			height : dummyChara.charaImgHeight
		}));
		$('#dummy-chara-join-message').attr('placeholder', dummyChara.joinMessage);
	}

	// 構成
	addPersonNum();
	function addPersonNum() {
		const orgs = $('#organization').val();
		const organization = orgs.split('\n').map(function(elm, idx) {
			return (idx + 8) + '人：' + elm;
		}).join('\n');
		$('#organization').val(organization);
	}

	$('#new-village-form').on('submit', function() {
		const orgs = $('#organization').val();
		const organization = orgs.split('\n').map(function(elm, idx) {
			return elm.split('：')[1]
		}).join('\n');
		$('#organization').val(organization);
	});

	// 発言制限
	$('[data-restrict-check]').on('change', function() {
		handleRestriction($(this));
	});

	initRestrict();
	function initRestrict() {
		$('[data-restrict-check]').each(function() {
			handleRestriction($(this));
		});
	}

	function handleRestriction($checkbox) {
		const checked = $checkbox.prop('checked');
		$checkbox.closest('tr').find('[data-restrict-length]').each(function(idx, elm) {
			if (checked) {
				$(elm).prop('disabled', false);
				$(elm).css('background-color', '');
			} else {
				$(elm).prop('disabled', true);
				$(elm).css('background-color', '#aaaaaa');
			}
		});
		$checkbox.closest('tr').find('[data-restrict-count]').each(function(idx, elm) {
			if (checked) {
				$(elm).prop('disabled', false);
				$(elm).css('background-color', '');
			} else {
				$(elm).prop('disabled', true);
				$(elm).css('background-color', '#aaaaaa');
			}
		});
	}

	$('[data-restrict-copy]').on('click', function() {
		const checked = $('[data-restrict-check]:first').prop('checked');
		const length = $('[data-restrict-length]:first').val();
		const count = $('[data-restrict-count]:first').val();

		$('[data-restrict-check]').each(function() {
			$(this).prop('checked', checked);
		});
		$('[data-restrict-length]').each(function() {
			$(this).val(length);
		});
		$('[data-restrict-count]').each(function() {
			$(this).val(count);
		});
		initRestrict();
	});
});