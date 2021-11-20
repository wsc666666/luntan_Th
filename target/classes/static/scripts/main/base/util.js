String.prototype.endWith = function (s) {
    var d = this.length - s.length;
    return (d >= 0 && this.lastIndexOf(s) == d)
}
function b(){
    if ( $("#input-js-1").val().endWith('?')||$("#input-js-1").val().endWith('？')){
        document.getElementById('js-display-label').style.display='none';
    }
    else{document.getElementById('js-display-label').style.display='inline';}
}
function showlist(){
        document.getElementById('sort-ul').style.display='block';
        document.getElementById('sort-ul').focus();
}
function closelist(){
    timer = setTimeout(function() {
        //onblur事件要执行的代码
        document.getElementById('sort-ul').style.display='none';
    }, 240);

}
function sortmoren(){
    document.getElementById('sort-show').innerHTML='默认排序&nbsp;&nbsp;<i class="icon-sort"></i>';
}
function sorttime(){
    document.getElementById('sort-show').innerHTML='时间排序&nbsp;&nbsp;<i class="icon-sort"></i>';
}

function input1(e){

   // if (idObject != null)
      //  idObject.parentNode.removeChild(idObject);
    e.parentNode.innerHTML='<div id="huati-div"><input id="input-btn"/><button class="btn btn-link" onclick="tianjia(this)">添加</button></div>';
//    e.parentNode.innerHTML='<div id="huati-div"><input id="input-btn"/><button class="btn btn-link" onclick="tianjia(this)">添加</button></div>';
}
function tianjia(e) {

   var input=document.getElementById('input-btn').value;
   if(!input.trim())
   {
       return false;
   }
    var htmls= e.parentNode.innerHTML;
    e.parentNode.innerHTML='<div class="border-radius" ><span class="js-type-content">'+input+'</span><a class="radius-a" href="javascript:void(0);" onclick="delete_li(this)" >&nbsp;<i  class="icon-minus"></i></a></div>'+htmls;

}
function delete_li(e) {
    e.parentNode.remove();

}
function toUser() {
    $.ajax({
        url: '/user/'+8080,
        method: 'post',
        data: {},
        dataType: 'json'
    }).done(function (oResult) {
        window.location.reload(true);
    }).fail(function (oResult) {
        alert('出现错误，请重试');
    }).always(function () {
        that.requesting = false;
    });

}
function js_edit (){
   document.getElementById("edit-div").style.display="block";
    document.getElementById('js-btn-commit').onclick=function(){
        var content=editor.txt.html();
        //alert(news1Id);
        $.ajax({
            url: '/user/addAnswer/',
            method: 'post',
            data: {content: content,newsId: news1Id},
            dataType: 'json'
        }).done(function (oResult) {
            window.location.reload(true);
        }).fail(function (oResult) {
            alert('出现错误，请重试');
        }).always(function () {
            that.requesting = false;
        });
    }
if (document.getElementsByClassName("no-anwser")[0]!=null)
   document.getElementsByClassName("no-anwser")[0].style.display="none";

}
function star(t,id) {

    $.ajax({
        url: '/star',
        method: 'post',
        data: {newsId: id,starType:0},
        dataType: 'json'
    }).done(function (oResult) {
        alert('已收藏');
    }).fail(function (oResult) {
        alert('出现错误，请重试');
    }).always(function () {
        this.requesting = false;
    });

}
function star2(t,id) {

    $.ajax({
        url: '/star',
        method: 'post',
        data: {newsId: id,starType:1},
        dataType: 'json'
    }).done(function (oResult) {
        alert('已喜欢');
    }).fail(function (oResult) {
        alert('出现错误，请重试');
    }).always(function () {
        this.requesting = false;
    });

}
function focus_question() {
    $.ajax({
        url: '/star',
        method: 'post',
        data: {newsId: news1Id,starType:2},
        dataType: 'json'
    }).done(function (oResult) {
        alert('已关注');
    }).fail(function (oResult) {
        alert('出现错误，请重试');
    }).always(function () {
        this.requesting = false;
    });

}


(function (window, undefined) {
    var Util = Base.createClass('main.base.Util');
    $.extend(Util, {
        isEmail: fIsEmail
    });

    function fIsEmail(sEmail) {
        sEmail = $.trim(sEmail);
        return sEmail && /^([a-zA-Z0-9_\.\-\+])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/.test(sEmail);
    }

})(window);