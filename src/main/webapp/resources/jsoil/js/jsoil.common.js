;(function () {
	$('[data-rel="tooltip"]').tooltip();//针对含有data-rel="tooltip"属性的元素进行tip提示，提示内容为title的值
	
	try{
		$(".chosen-select").chosen(); //下拉框的渲染，需要加载：chosen.jquery.min.js与chosen.css文件
	}catch(e){}
	
	try{
		$('textarea[class*=autosize]').autosize({append: "\n"});
	}catch(e){}
	
	
	$buttonRow = $(".row-action-buttons");
	
	$buttonRow.on("showMiniButton",function(event){
	    var innerHTML = "";
	    if($(this).hasClass("hidden-sm")){
		    	$(this).find("a").each(function() {
            		innerHTML = innerHTML + "<li>" + $(this).prop("outerHTML"); + "</li>";
		    	});
	    }
	    var miniButtonHTML = '<div class="visible-xs visible-sm hidden-md hidden-lg">'
			+ '		<div class="inline position-relative">'
			+ '			<button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown">'
			+ '				<i class="icon-caret-down icon-only bigger-120"></i>'
			+ '			</button>'
			+ '			<ul class="dropdown-menu dropdown-only-icon dropdown-yellow pull-right dropdown-caret dropdown-close">'
			+ 				innerHTML
			+ '			</ul>'
			+ '		</div>'
			+ '	</div>';
	    $(this).after(miniButtonHTML) ;
	});
	
	try{
		$(".row-action-buttons").trigger("showMiniButton"); 
	}catch(e){
		alert(e);
	}
	
	//不支持placeholder浏览器下对placeholder进行处理
    if (document.createElement('input').placeholder !== '') {
        $('[placeholder]').focus(function () {
            var input = $(this);
            if (input.val() == input.attr('placeholder')) {
                input.val('');
                input.removeClass('placeholder');
            }
        }).blur(function () {
            var input = $(this);
            if (input.val() == '' || input.val() == input.attr('placeholder')) {
                input.addClass('placeholder');
                input.val(input.attr('placeholder'));
            }
        }).blur().parents('form').submit(function () {
            $(this).find('[placeholder]').each(function () {
                var input = $(this);
                if (input.val() == input.attr('placeholder')) {
                    input.val('');
                }
            });
        });
    }

    	/*简单的进度显示环*/
    if($('.easy-pie-chart.percentage').length){
	    $('.easy-pie-chart.percentage').each(function(){
			var $box = $(this).closest('.infobox');
			var barColor = $(this).data('color') || (!$box.hasClass('infobox-dark') ? $box.css('color') : 'rgba(255,255,255,0.95)');
			var trackColor = barColor == 'rgba(255,255,255,0.95)' ? 'rgba(255,255,255,0.25)' : '#E2E2E2';
			var size = parseInt($(this).data('size')) || 50;
			$(this).easyPieChart({
				barColor: barColor,
				trackColor: trackColor,
				scaleColor: false,
				lineCap: 'butt',
				lineWidth: parseInt(size/10),
				animate: /msie\s*(8|7|6)/.test(navigator.userAgent.toLowerCase()) ? false : 1000,
				size: size
			});
		});
    }
    
    //删除确认
    //如果data-noReload=true,则不刷新页面，但删除其父元素结点
    $(document).on('click','.row_del_button', function (e) {
    		e.preventDefault();
    		var $_this = this,
            $this = $($_this),
            noReload = $this.attr('data-noReload')=='true'?true:false,
            href = $this.prop('href');
    		$.teninedialog({

    	        title:'系统提示',
    	       
    	        content:'确认删除本条记录?',//
    	        url:'',//不支持跨域，如果url不为空，就会加载url的内容，content的内容忽略

    	        showCloseButton:false,

    	        otherButtons:["确定","取消"],

    	        otherButtonStyles:['btn-primary','btn-primary'],

    	        bootstrapModalOption:{keyboard: true},

    	        clickButton:function(sender,modal,index){
	    	        	if(index == 0){
	    	        		//如果选择确定
	    	        		//alert(noReload);
	    	        		$.ajax({
    						url: href, 
    						context: document.body, 
    						type : "GET", 
    					    dataType:"text",
    					    contentType:'application/json;charset=UTF-8',
    					    data : {
    					    		"ids": getAllCheckedRowId()
    					    },
    						success: function(resultData){
    							if(noReload){
    								$this.parent().remove();
    							}else{
    								window.location.reload();
    							}
    			      		},
    					    error:function(xhr,status,error){
    					    	  	alert("删除失败，请检查！error="+error);
    					    	  	return false;
    					    }
    					});
	    	        	}
    	            $(this).closeDialog(modal);
    	        }
    	    });
    });
    
    
    //批量删除按钮
    $('.batch_operate_button').on('click', function (e) {
		var $_this = this,
        $this = $($_this),
        href = $this.attr("data-action"),
        tip = $this.attr("title")==null?"确定要删除这些数据么？":$this.attr("title"),
		ids = getAllCheckedRowId();
		if(ids == null || ids == ""){
			$.teninedialog({
				title:'系统提示',
	    	        content:'没有选中的行，请先选择数据！',//
	    	        showCloseButton:false,
	    	        bootstrapModalOption:{keyboard: true}
	    	    });
		}else{
			$.teninedialog({
				title:'系统提示',
	    	        content:tip,//
	    	        showCloseButton:false,
	    	        otherButtons:["确定","取消"],
	    	        otherButtonStyles:['btn-primary','btn-primary'],
	    	        bootstrapModalOption:{keyboard: true},
	    	        clickButton:function(sender,modal,index){
		    	        	if(index == 0){
		    	        		/*if(href.indexOf("?") > -1){
		    	        			href = href + "&ids="+ids;
		    	        		}else{
		    	        			href = href + "?ids="+ids;
		    	        		}*/
		    	        		//alert(href);
		    	        		//确定后，执行ajax删除
		    	        		$.ajax({
	    						url: href, 
	    						context: document.body, 
	    						type : "GET", 
	    					    dataType:"text",
	    					    contentType:'application/json;charset=UTF-8',
	    					    data : {
	    					    		"ids": getAllCheckedRowId()
	    					    },
	    						success: function(resultData){
	    							window.location.reload();
	    			      		},
	    					    error:function(xhr,status,error){
	    					    	  	alert("删除失败，请检查！error="+error);
	    					    	  	return false;
	    					    }
	    					});
		    	        	}
	    	            $(this).closeDialog(modal);
	    	        }
    	    		});
		}
    });
    
    
    
    //点击打开由该超连接对象的href指定内容的弹出窗口
    $(document).on('click','.open-a', function (e) {
    		e.preventDefault();
    		var $_this = this,
            $this = $($_this),
            showIframeStr = $this.attr("data-showIframe") == null ?'false':$this.attr("data-showIframe"),
            	title = $this.attr('data-original-title'),
            	height = $this.attr('data-height'),
            	width = $this.attr('data-width'),
            href = $this.prop('href');
    		if(title == null || title == ""){
    			title = $this.attr('title');
    		}
    		if(title == ""){
    			title = "系统提示";
    		}
    		
    		if(height == null || height == ""){
    			height = 350;
    		}
    		if(width == null || width == ""){
    			width = 800;
    		}
    		var showIframe = false;
    		if(showIframeStr == "true"){
    			showIframe = true;
    		}
    		$.teninedialog({

    	        title : title,
    	        css: 'width:'+width+'px;height:'+height+'px;',
    	        content: '',//
    	        url: href == null ?'':href,//不支持跨域，如果url不为空，就会加载url的内容，content的内容忽略

    	        	showIframe : showIframe,//是否是以iframe的模式打开
    	        		 
    	        showCloseButton:true,

    	        bootstrapModalOption:{keyboard: true},
    	    });
    });
    
    
  //点击打开由该按钮对象的data-action指定内容的弹出窗口
    
    $('.open-button').on('click', function (e) {
    		e.preventDefault();
    		var $_this = this,
            $this = $($_this),
            showIframe = $this.attr("data-showIframe") == null ?false:$this.attr("data-showIframe"),
            tip = $this.attr("title")==null?"系统提示？":$this.attr("title"),
            href = $this.attr('data-action');
    		$.teninedialog({

    	        title : tip,
    	        css: 'width:800px;height:350px;',
    	        content: '',//
    	        url: href == null ?'':href,//不支持跨域，如果url不为空，就会加载url的内容，content的内容忽略

    	        	showIframe : showIframe,//是否是以iframe的模式打开
    	        		 
    	        showCloseButton:false,

    	        bootstrapModalOption:{keyboard: true},
    	    });
    });
    
    $('.show-a').on('click', function (e) {
   		e.preventDefault();
    		var $_this = this,
        $this = $($_this),
        title = $this.attr('data-original-title'),
        href = $this.attr('href');
    		if(title == null || title == ""){
    			title = $this.attr('title');
    		}
    		
    		$.ajax({ 
				url: href, 
				context: document.body, 
				type : "POST", 
			    dataType:"text",
			    contentType:'application/json;charset=UTF-8',
			    data : "",
				success: function(resultData){
					//alert(resultData);
					//设置tab页
					$("#top-tabbable").find("ul li").removeClass("active");
					if($("#tab-view") == null || $("#tab-view").length < 1){
						var tabLi = '<li id="tab-view">'
							  + '	<a data-toggle="tab"  href="#view-content">' + title + '</a>'
							  + '</li>';
			    			$("#top-tabbable").find(".message-tab").before(tabLi);
					}
					$("#tab-view").addClass("active");
		    			//设置内容
					$("#top-tabbable").find(".tab-content").find(".tab-pane").removeClass("active");
					if($("#view-content") == null || $("#view-content").length < 1){
						var tabContent = '<div id="view-content" class="tab-pane in active">' 
							+ 
							+ '</div>';
						$("#top-tabbable").find(".tab-content").append(tabContent);
					}
					$("#view-content").addClass("active");
					$("#view-content").html(resultData);
	      		},
			    error:function(xhr,status,error){
			    	  	alert("读取消息失败，请检查！error="+error);
			    	  	return false;
			    }
			});
    });
    
    
    
    $('.collect-action').on('click', function (e) {
   		e.preventDefault();
    		var $_this = this,
	        $this = $($_this),
	        href = $this.attr('href'),
	        name = $this.attr('data-name'),
	        collectModule = $this.attr('data-module'),
		    	collectBeanId = $this.attr('data-id'),
		    	redirectPath = $this.attr('data-path');
    		if(href.indexOf("?") > -1){
    			href = href + "&collectBeanId="+collectBeanId;
    		}else{
    			href = href + "?collectBeanId="+collectBeanId;
    		}
    		href = href + "&collectModule="+collectModule;
    		href = href + "&title="+name;
    		href = href + "&redirectPath="+redirectPath;
    		$.ajax({ 
				url: href, 
				context: document.body, 
				type : "POST", 
			    dataType:"text",
			    contentType:'application/json;charset=UTF-8',
			    data : {
			    		title : name,
			    		collectBeanId: collectBeanId ,
			    		collectModule : collectModule,
			    		redirectPath : redirectPath
			    },
				success: function(resultData){
					//设置tab页
					if($this.hasClass("btn-info")){
						$this.removeClass("btn-info").addClass("btn-success");
						$this.find("span").text("已收藏");
						$this.attr("title","取消收藏");
						$this.attr("data-original-title","取消收藏");
					}else {
						$this.removeClass("btn-success").addClass("btn-info");
						$this.find("span").text("收藏");
						$this.attr("title","收藏");
						$this.attr("data-original-title","收藏");
					}
					
	      		},
			    error:function(xhr,status,error){
			    	  	alert("收藏失败，请检查！error="+error);
			    	  	return false;
			    }
			});
    });
    
})();

function getAllCheckedRowId(){
	var ids = "";
	$("[name='rowId']").each(function() {
        if ($(this).is(':checked') ==true) {
        	ids = ids + "," + $(this).val();
        }
	});
	return ids;
}
