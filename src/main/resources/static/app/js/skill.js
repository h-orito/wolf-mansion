$(function() {
	'use strict'

	$('body').on('click', '[data-tag]', function() {
		if ($(this).hasClass('label-default')) {
			$(this).removeClass('label-default');
			$(this).addClass('label-success');
		} else {
        	$(this).removeClass('label-success');
			$(this).addClass('label-default');
		}
	});

	$('[data-search]').on('click', function() {
		const url = contextPath + 'skill-list'
		const tags = $('body')
		.find('[data-tag].label-success')
		.map((idx, tag) => $(tag).text())
		.toArray()
		.join(',');
		$.ajax({
			type: 'GET',
			url: url,
			data: {
				'tags': tags,
				'name': $('#skill-name').val()
            }
		}).then(function (skillCodes) {
			const anchors = skillCodes.map(code => '#' + code);
			$('#menu').find('a').each((idx, elm) => {
				$(elm).closest('span').removeClass('hidden');
				const anchor = $(elm).attr('href');
				if (anchors.indexOf(anchor) === -1) {
					$(elm).closest('span').addClass('hidden');
				}
			});
			$('#skill li ul li').each((idx, elm) => {
				const id = $(elm).attr('id')
				if (id == null || id.length === 0) return;
				$(elm).removeClass('hidden');
				if (skillCodes.indexOf(id) === -1) {
					$(elm).addClass('hidden');
				}
			});
		});
	});
});