$(function() {

    var shopId = getQueryString('shopId');

    alert('yea'+shopId);
    var shopInfoUrl = '/myo2o/shop/getshopmanagementinfo?shopId='+shopId;
    //alert('yeah');
    $.getJSON(shopInfoUrl,function (data) {
        if(data.redirect) {
            window.location.href = data.url;

        }else{
            if(data.shopId!=undefined&&data.shopId!=null){
                shopId=data.shopId;
            }
            $('#shopInfo')
                .attr('href','/myo2o/shop/shopoperation?shopId='+shopId);
        }
    });

});