$(function() {

	const GET_DUMMY_CHARA_INFO_URL = contextPath + '/getDummyCharaImgUrl/';

	$('#characterSetId').on('change', function() {
		const charaGroupId = $(this).val();
		replaceDummyCharaInfo(charaGroupId);
	});

	function replaceDummyCharaInfo(charaGroupId) {
		$.ajax({
			type : 'GET',
			url : GET_DUMMY_CHARA_INFO_URL + charaGroupId
		}).then(function(response) {
			$('#dummy-chara-img').html($('<img />', {
				src : response.charaImgUrl,
				width : response.charaImgWidth,
				height : response.charaImgHeight
			}));
			$('#dummy-chara-join-message').attr('placeholder', response.joinMessage);
		});
	}

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

	replaceDummyCharaInfo($('#characterSetId').val());
});