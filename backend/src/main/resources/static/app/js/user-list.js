$(function () {
    'use strict'

    $('body').on('click', '[data-user-page]', function () {
        const userName = $(this).text();
        window.open(contextPath + 'user/' + userName);
    });

    $('body').on('click', '[data-prev-page]', function () {
        const currentPage = parseInt($(this).data('prev-page'));
        loadUserList(currentPage - 1);
    });
    $('body').on('click', '[data-next-page]', function () {
        const currentPage = parseInt($(this).data('next-page'));
        loadUserList(currentPage + 1);
    });
    $('body').on('click', '[data-pagenum]', function () {
        loadUserList(parseInt($(this).data('pagenum')));
    });

    function loadUserList(page) {
        const param = {
            pageNum: page
        }
        location.href = contextPath + 'user-list?' + $.param(param)
    }
});