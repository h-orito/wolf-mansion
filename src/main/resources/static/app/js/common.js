// api
var werewolfmansion = werewolfmansion || {};
werewolfmansion.api = {
};

var contextPath;
$(function() {
	contextPath = $('#context-path').text();
	
	$('body').on('click', '[data-goto]', function(){
		location.href = contextPath + $(this).data('goto');
	});
});

// ---------------------------------------------------
// Handlebar helper
// ---------------------------------------------------
Handlebars.registerHelper('empty', function(param) {
	if (param == null || param === '' || param === []) {
		return true;
	} else {
		return false;
	}
});
Handlebars.registerHelper('eq', function(param1, param2) {
	return param1 == param2;
});
Handlebars.registerHelper('neq', function(param1, param2) {
	return param1 != param2;
});
Handlebars.registerHelper('or', function() {
	//argumentsに渡された引数が入っている
	var existsTrue = false;
    for (var i = 0; i < arguments.length; i++) {
        if (arguments[i]) {
        		existsTrue = true;
        }
    }
	return existsTrue;
});
Handlebars.registerHelper('timeFormat', function(time) {
	var year = time.year;
	var month = String('0' + time.monthValue).slice(-2);
	var day = String('0' + time.dayOfMonth).slice(-2);
	var hour = String('0' + time.hour).slice(-2);
	var minute = String('0' + time.minute).slice(-2);
	var second = String('0' + time.second).slice(-2);
	return year + '/' + month + '/' + day + ' ' + hour + ':' + minute + ':' + second;
});

Handlebars.registerHelper('escapeHtmlWithoutBr', function(text) {
	return text.replace(/(\r\n|\n|\r)/gm, '<br>').split('<br>').map(
			function(item) {
				return item.replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;').replace(/"/g, '&quot;')
						.replace(/'/g, '&#39;');
			}).join('<br>');
});