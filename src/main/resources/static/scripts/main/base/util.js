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
    var htmls= e.parentNode.innerHTML;
    e.parentNode.innerHTML='<div class="border-radius" ><a>'+input+'&nbsp;</a><a class="radius-a" href="javascript:void(0);" onclick="delete_li(this)" ><i  class="icon-minus"></i></a></div>'+htmls;
}
function delete_li(e) {
    e.parentNode.remove();

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