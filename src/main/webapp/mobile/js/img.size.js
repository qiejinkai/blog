$(function(){
    $('.uc_height').each(function(){
        var w = $(this).width();
        var p =  $(this).attr('ucheight')/100;

        $(this).height(w*p);


    });
});
