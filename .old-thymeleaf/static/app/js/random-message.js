$(function() {
	'use strict'

	$('body').on('click', '[data-all-view]', function() {
		$(this).closest('td').find('.hidden').each(function(){
			$(this).removeClass('hidden');
		});
		$(this).remove();
	});

	$('body').on('click', '[data-search]', function(){
		const keyword = $('#search-keyword').val();
		if (keyword.length <= 0) {
			$('#keyword-table').find('tbody tr').each(function(){
				$(this).removeClass('hidden');
			});
			return;
		}

		$('#keyword-table').find('tbody tr').each(function(){
			const word = $(this).find('td').eq(0).text();
			const contents = $(this).find('td').eq(1).text().split('\n').map(content => {
				return content.trim();
			}).filter(content => {
				return content.length > 0 && content !== '全て表示';
			});
			if (word.indexOf(keyword) != -1 || contents.some(content => content.indexOf(keyword) != -1)){
				$(this).removeClass('hidden');
			} else {
				$(this).addClass('hidden');
			}
		});
	});

	$('body').on('click', '[data-copy]', function(){
		const text = '[[' + $(this).data('copy') + ']]';
		clipboardCopy(text, 'コピーしました： ' + text);
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
});