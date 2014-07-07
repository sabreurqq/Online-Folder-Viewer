<%@ page language="java" contentType="text/html; charset=BIG5"  pageEncoding="BIG5"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
	<title>jQuery File Upload Example</title>
	<link rel=stylesheet type="text/css" href="css/bootstrap.css">
	<link rel=stylesheet type="text/css" href="css/bootstrap-theme.css">
	<link rel=stylesheet type="text/css" href="css/common.css">
	<link rel=stylesheet type="text/css" href="css/style.css">
	<script src="thirdpartyjs/jquery.js"></script>
	<script src="thirdpartyjs/bootstrap.min.js" ></script>
	<script src="thirdpartyjs/jquery.ui.widget.js"></script>
	<script src="thirdpartyjs/jquery.iframe-transport.js"></script>
	<script src="thirdpartyjs/jquery.fileupload.js"></script>
</head>
<body>
<div class="container">
	<span class="btn btn-success fileinput-button">
        <i class="glyphicon glyphicon-plus"></i>
        <span>Add files...</span>
        <!-- The file input field used as target for the file upload widget -->
        <input id="fileupload" type="file" name="files[]" multiple data-url="UploadServlet">
    </span>
 </div>
<script>

$(function () {
    $('#fileupload').fileupload({
        dataType: 'json',
        done: function (e, data) {
            $.each(data.result.files, function (index, file) {
                $('<p/>').text(file.name).appendTo(document.body);
            });
        },
        progressall: function (e, data) {
            var progress = parseInt(data.loaded / data.total * 100, 10);
            $('#progress .bar').css(
                'width',
                progress + '%'
            );
        }
    });
});
</script>
</body> 
</html>