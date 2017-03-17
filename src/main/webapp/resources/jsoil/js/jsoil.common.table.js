;(function () {
    
    if($('.table-order-column').length ){
	    	$('.table-order-column').append("<i/>");
	    	$(".table-order-column").each(function () {
	        if($(this).attr('id') == $('#orderByProperty').val()){
	        	$(this).addClass("table-order-"+$('#orderByKind').val()+" :after");
	        }
		});
    }
    
    
    //表头排序
    $('.table-order-column').on('click', function (e) {
		e.preventDefault();
		var $_this = this,
        $this = $($_this),
        orderByProperty = $this.attr("id");
		
		$('#orderByProperty').val(orderByProperty);
		$('#orderByKind').val($('#orderByKind').val()=="asc"?"desc":"asc");
		$('#queryForm').submit();
    });
    
    //列表分页器
    $('.pagination-index').on('click', function (e) {
		e.preventDefault();
		
		var $_this = this,
        $this = $($_this);
		if($this.closest("li").hasClass("disabled") ){
			return ;
		}
        var pageIndex = $this.attr('data-pageIndex');
		if(isNaN(pageIndex)){
			pageIndex = 1;
		}
		$('#currentPage').val(pageIndex);
		$('#queryForm').submit();
    });
    
    //列表分页器
    $('.current-page').on('change', function (e) {
	    	e.preventDefault();
	    	var $_this = this,
	    	$this = $($_this);
	    	var pageIndex = $this.val();
	    	if(isNaN(pageIndex) || pageIndex < 1){
	    		pageIndex = 1;
	    	}
	    	var maxPage = $this.attr("data-maxValue");
	    	if(parseInt(pageIndex) > parseInt(maxPage)){
	    		alert("请输入有效页面范围[1~"+maxPage+"]!");
	    		return false;
	    	}
	    	$('#currentPage').val(pageIndex);
	    	$('#queryForm').submit();
    });
    
    //全选
    $('#checkAll').on('click', function (e) {
		var $_this = this,
        $this = $($_this)
        checkValue = $this.is(':checked');
		
		if($("[name='rowId']").length){
			if(checkValue){
				//LCY_TODO 这里反复 操作会出问题
				$("[name='rowId']").each(function () {
			        $(this).attr('checked',true);
				});
				
			}else{
				$("[name='rowId']").each(function () {
			        $(this).attr('checked',false);
				});
			}
		}
		
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
