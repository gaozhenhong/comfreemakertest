	var $currentImgDiv ;
	function openMeiTu(currentButton){
		if(currentButton != null){
			$currentImgDiv = $(currentButton).closest(".imgDiv");
		}
		var imgId,imgUrl,cropPresets ,folder,contentPath;
		if($currentImgDiv != null){
			imgId = $currentImgDiv.find("#thumbId").val();
			imgUrl = $currentImgDiv.find("#thumbIdPath").val();
			cropPresets = $currentImgDiv.find("#cropPresets").val();
			folder = $currentImgDiv.find("#folder").val();
			contentPath = $currentImgDiv.find("#contentPath").val();
		}else{
			imgId = $("#thumbId").val();
			imgUrl = $("#thumbIdPath").val();
			cropPresets = $("#cropPresets").val();
			folder = $("#folder").val();
			contentPath = $("#contentPath").val();
		}
		
		var openFileUploadUrl = contentPath + "/sys/file/imgUpload.do?imgId="+imgId + "&imgUrl="+imgUrl+"&cropPresets="+cropPresets+ "&folder="+folder;
		//alert("openFileUploadUrl="+openFileUploadUrl);
		openWindow = openPopupWindow(openFileUploadUrl, '图片上传', 'exportLayoutDialog1', {width:730,height:500});
	}
	
	function closeWindow(){
		var index = layer.index;
		layer.close(index);
	}
	
	//layer弹出层模式
	var closeReload = 0;	//判断是否关闭刷新 0 不刷新，1刷新
	function openPopupWindow(url, title, id, dialogParams) {
		var width = "600px";
		var height = "400px";
		if(dialogParams != null){
			width = dialogParams.width == null ? width : dialogParams.width;
			height = dialogParams.height == null ? height : dialogParams.height;
		}
		$.layer({
		    type: 2,
		    shadeClose: true,
		    title: title,
		    shift:"top",
		    area: [width,height],
		    iframe: {src: url},
		    end:function(){
			    	if(closeReload == 1){
			    		closeReload = 0;
			    		parent.location.reload();
			    	}
		    }
		});
	}
	
	function setImgUrl(baseViewUrl,data,addMore){
		//alert("meiTu_init.jsp::::"+data);
		var dataObj = JSON.parse(data);
		var fileUrl,fileId;
		if(dataObj != undefined && dataObj.length > 0){
			fileUrl = dataObj[0].resPath;
			fileId = dataObj[0].id;
		}
		if(!fileUrl.match(/^http/i)){
			fileUrl = baseViewUrl + fileUrl;
		}
		//alert("fileUrl == " + fileUrl);
		if(addMore){
			addImgDiv(fileId,fileUrl);
		}else{
			if($currentImgDiv != null){
				$currentImgDiv.find('#thumbId').val(fileId);
				$currentImgDiv.find('#imgSrcThumb').attr('src', fileUrl);
				$currentImgDiv.find('#imgSrcThumb').css('display', "block");
			}else{
				$('#thumbId').val(fileId);
				$('#imgSrcThumb').attr('src', fileUrl);
				$('#imgSrcThumb').css('display', "block");
			}
			closeWindow();
		}
	}
	
	function addImgDiv(fileId,fileUrl,imgName){
		var newThumbId = $('#thumbId').clone();
		newThumbId.attr('name',imgName);
		newThumbId.val(fileId);
		$('#imgDiv').append(newThumbId);
		
		var newThumbIdPath = $('#thumbIdPath').clone();
		newThumbIdPath.attr('name',imgName+'Path');
		newThumbIdPath.val(fileUrl);
		$('#imgDiv').append(newThumbIdPath);
		
		var newImgSrcThumb = $('#imgSrcThumb').clone();
		newImgSrcThumb.attr('src', fileUrl);
		newImgSrcThumb.css('display', "block");
		$('#imgDiv').append(newImgSrcThumb);
	}