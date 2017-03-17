function showHotelBDMap(){
	var mapObj=$("#bdmap-main-div");
	if(mapObj.html()==undefined){
		showLoadDiv();
	var mapMainHtml="<div id=\"bdmap-main-div\" style=\"position:absolute;height:100%;width:100%;z-index:999;display:none\">"+
						"<iframe name=\"bdmapIframe\" src=\""+dbmapUrl+"\" frameborder=\"0\" width=\"100%\" height=\"100%\"></iframe>"+
					"</div>"
		$("body").prepend(mapMainHtml); 
		$("#bdmap-main-div").show();	
		//bdmapIframe.onload=hideLoadDiv();
	}else{
		mapObj.show();
		bdmapIframe.showMarker();
	}
}

var iframeUrl;
function showListHotelBDMap(hotelId){
	var mapObj=$("#bdmap-main-div");
	if(mapObj.html()==undefined){
		showLoadDiv();
		iframeUrl=dbmapUrl+"?hotelId="+hotelId
	var mapMainHtml="<div id=\"bdmap-main-div\" style=\"position:absolute;height:100%;width:100%;z-index:999;display:none\">"+
						"<iframe name=\"bdmapIframe\" src=\""+iframeUrl+"\" frameborder=\"0\" width=\"100%\" height=\"100%\"></iframe>"+
					"</div>"
		$("body").prepend(mapMainHtml); 
		$("#bdmap-main-div").show();	
	}else{
		if(iframeUrl.indexOf(hotelId)>-1){
			mapObj.show();
			bdmapIframe.showMarker();
		}else{
			showLoadDiv();
			iframeUrl=dbmapUrl+"?hotelId="+hotelId;
			bdmapIframe.location.href=iframeUrl;
			bdmapIframe.onload=showHotelBDMapSub();
			//alert(bdmapIframe.document.readyState );
			//mapObj.show();
		}
		
	}
}

function showLoadDiv(){
	$("#map-loading-div").show();
}

function hideLoadDiv(){
	$("#map-loading-div").hide();
}

function showHotelBDMapSub(){
	//alert("show");
	$("#bdmap-main-div").show();
}

function hideHotelBDMap(){
	$("#bdmap-main-div").fadeOut();
}