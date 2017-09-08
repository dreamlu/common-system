/**
 *  lucheng~~
 */
//获取编辑器数据
function getEditData() {
	
    var editor_data = "";
    //捕获没有textarea时产生的异常
    try {
    	editor_data = CKEDITOR.instances['content_id'].getData();//或者CKEDITOR.instances.content_id.getData()
	} catch (e) {
	}
    if(editor_data==null || editor_data==""){  
        //alert("请填写内容！");  
        return false;  
    }
    return editor_data;
} 
//修改
function upd(opte){
	//alert();
	var editor_data = getEditData();
	if(editor_data != false){
		$("#content_id").val(getEditData());//给textarea赋值，一起序列化数据
	}
	var data = $("#upd").serialize();
	//data += getEditData();
	//alert("$"+data);
	$.post("operateDeal?operate="+opte,data,function(){
		
	});
}
//获取id
function getId(event){
	//通过事件的目标节点找到上级节点id(值与数据表id一一对应)
	var id = $(event.target).parent().parent().attr("id");
	return id;
}
//添加数据
function addData(tbname){
	//alert();
	$("#main").empty();
	$("#main").load("operate?operate=add",{"tbname":tbname},function(response,status,xhr){
		if(status == "success"){
			//mainLoad(tbname,0);
		    CKEDITOR.replace('content_id');  
		}
	});
}
//删除
function deleteData(id,tbname){
	$("#main").empty();
	$("#main").load("operate?operate=delete",{"id":id,"tbname":tbname},function(response,status,xhr){
		alert(status);
		if(status == "success"){
				mainLoad(tbname,0);
		}
	});
}
//修改
function alterData(id, tbname){
	//alert();
	$("#main").empty();
	$("#main").load("operate?operate=alter",{"id":id,"tbname":tbname},function(response,status,xhr){
		if(status == "success"){
			//mainLoad(tbname,0);
			CKEDITOR.replace('content_id');
		}
	});
}
//页面展示
function mainLoad(tbname,pageNum){
	//ajax参数请求
	$("#main").load("list",{"tbname":tbname,"pageNum":pageNum},function(response,status,xhr){
		//alert(status);
		//alert(response);
		if(status == "success"){
			//$("#main").append(response);
			$(".update_data").bind("click",function(event){
				//alert(getId(event));
				alterData(getId(event),tbname);
			});
			$(".delete_data").bind("click",function(event){
				deleteData(getId(event),tbname);
			});
			$(".add_data").bind("click",function(event){
				addData(tbname);
			});
		}
	});
}
//添加表
function addTable(){
		$("#main").empty();
		$("#main").append("<form method=post onSubmit='submitTable()' id='frmAddTable'><table id='tb_adding' class='table table-striped table-hover'></table></form>");
		$("#tb_adding").append("<tr><td>表名<input type=text id=tb_name name=tb_name value='tb_'></td><td>中文<input type=text name=tb_title value='信息'></td></tr>");
		$("#tb_adding").append("<tr><td>列名</td><td>类型</td><td>中文</td><td><a href=# onclick='addColumn();'>添加列</a></td></tr>");
		$("#frmAddTable").append("<input type=button onclick='submitTable()' value='确定'><input type=reset value='重置'>");
		addColumn();
	}
//添加列
function addColumn(){
	var row="<tr>";
	row+="<td><input name=fd_name type=text></td>";
	row+="<td><input name=fd_type type=text value='varchar(32)'></td>";
	row+="<td><input name=fd_title type=text></td>";
	row+="</tr>";
	$("#tb_adding").append(row);
}
//提交表
function submitTable(){
	var data=$("#frmAddTable").serialize();
	//alert("$"+data);
	$.post("addTable",data,function(){
		//这里有错误
		//window.setTimeout("mainLoad('"+tbname+"',0,'"+searchKeyword+"')",1000);
	});
}
//退出
function drop(){
	$.post("drop");
}