<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Online Folder Viewer by CHLin</title>
	<link rel=stylesheet type="text/css" href="css/bootstrap.css">
	<link rel=stylesheet type="text/css" href="css/bootstrap-theme.css">
	<link rel=stylesheet type="text/css" href="css/bootstrap-sortable.css">
	<link rel=stylesheet type="text/css" href="css/common.css">
	<link rel=stylesheet type="text/css" href="css/jquery.fileupload.css">
	<link rel=stylesheet type="text/css" href="css/jquery.fileupload-ui.css">
	<script src="thirdpartyjs/jquery.js"></script>
	<script src="thirdpartyjs/bootstrap.min.js"></script>
	<script src="thirdpartyjs/bootstrap-sortable.js"></script>
	<script src="thirdpartyjs/moment.min.js"></script>
	<script src="thirdpartyjs/jquery.ui.widget.js"></script>
	<script src="thirdpartyjs/jquery.fileupload.js"></script>
	<script src="thirdpartyjs/jquery.iframe-transport.js"></script>
	<script src="js/FolderViewer.js"></script>
</head>
<body>
	<input id="Path" type="hidden" value="<%=pers.chlin.ofv.Res.getDefaultPath()%>" />
	<input id="DownloadURL" type="hidden" value="<%=pers.chlin.ofv.Res.getDownloadURL()%>" />
	<input id="ShowdataURL" type="hidden" value="<%=pers.chlin.ofv.Res.getShowdataURL()%>" />
	<script type="text/javascript">
				$(document).ready( function(){
					initFileUpload();
					goFolder( $('#Path').val() );
				});
	</script>
	<div class="narbar  navbar-inverse navbar-fixed-top " role="navigation">
		<div class="container">
			<ul class="nav navbar-nav navbar-left">
				<li>
					<span class="btn btn-success fileinput-button">
				        <i class="glyphicon glyphicon-plus"></i>
				        <span>上傳</span>
				        <input id="fileupload" type="file" name="files[]" multiple />
				    </span>
				</li>
			</ul>
			<ul class="nav navbar-nav navbar-right">
			</ul>
		</div>
	</div>
	<div class="container">
		<div id="dataArea" style=" width:100% ">
		<table id="content-table-2" class="table table-striped table-hover table-condensed sortable">
		<thead>
			<tr>
				<td id="CurrPath" align="center" colspan="4">
				<td>
			<tr>
			<tr>
				<th width="8%" style="text-align: center;" >類型　</th>
				<th width="50%" style="text-align: left;" >檔案名稱</th>
				<th width="17%" style="text-align: right;">檔案大小　</th>
				<th width="20%" style="text-align: right;">最後修改日期　</th>
			</tr>
			<tr id="parentFolder">
			</tr>
		</thead>
		<tbody id="content-tbody-2">
		</tbody>
	</table>
		</div>
	</div>
	<div class="navbar navbar-inverse navbar-fixed-bottom" role="navigation">
		<div class="container">
			<div class="navbar-form navbar-left">
				<input type="text" class="form-control" placeholder="按下Enter搜尋..." name="srch-term"  onchange="search($(this).val())"/>		
			</div>
		</div>
	</div>
</body>
</html>