	function goFolder(folderPath)
	{
		showCurrPath(folderPath);
		var downloadURL = $("#DownloadURL").val();
		var data = {
			ActionType: "Show",
			targetPath : folderPath
		};
		var dataArea = $('#content-tbody-2');
		$.ajax({
			url: $('#ShowdataURL').val(),
			type: 'post',
			data: data,
			success : function(retData) {
				if (retData.RESULT == "SUCCESS") {
					
					dataArea.html('');
					var tableHtml = "";
					
					var pfArea = $('#parentFolder');
					var lastSeparatorIndex = folderPath.lastIndexOf("/");
					var parentFolder = folderPath.substr(0, lastSeparatorIndex);
					if (endsWith(parentFolder, ':')) {
						parentFolder = parentFolder + '/';
					}
					parentFolder = parentFolder.replace(/\\/g,'\\\\');
					var pf = "<td style='vertical-align:middle' align='center' data-value='-1'>";
					pf += '<span class="glyphicon glyphicon-folder-open"></span>';
					pf += "</td>";
					
					pf += "<td style='vertical-align:middle' data-value='-1'>";
					pf += '<a href="#" onClick="goFolder(\'' + parentFolder + '\');" >..</a>';
					pf += "</td>";
					pf += "<td data-value='-1'></td>";
					pf += "<td data-dateformat='YYYY/MM/DD HH:mm:ss' data-value='1911/10/10 10:10:10'></td>";
					pfArea.html(pf);
					
					$.each(retData.DATA, function(key, val) {
						tableHtml += createRow(downloadURL, val);
					});
					dataArea.html(tableHtml);
				} else if(retData.RESULT == "FAIL") {
					alert("無法顯示," + retData.REASON);
				} else {
					alert("無法處理的retData" + retData);
				}
				
			},
			error : function(retData) {
				alert("ERROR");
			}
		});
	}
	
	function showCurrPath(path)
	{
		$("#Path").val(path);
		var currPathLi = $("#CurrPath");
		var pathSplitArray = path.replace(/\\/g, '/').split('/');
		var htmlVar = "";
		var subPath = ""; 
		$.each(pathSplitArray, function(index, val) {
			if (val) {
				subPath += val + "/";
				htmlVar += '<a href="#" onClick="goFolder(\'' + subPath + '\');" >' + val + '</a>';
				htmlVar += "/";
			}
		});
		currPathLi.html(htmlVar);
	}
	
	function createRow(downloadURL, val)
	{
		if(val.isDir) {
			var htmlVar = "<tr>";
			
				htmlVar += "<td style='vertical-align:middle' align='center' data-value='0'>";
				htmlVar += '<span class="glyphicon glyphicon-folder-open"></span>';
				htmlVar += "</td>";
				
				htmlVar += "<td style='vertical-align:middle' data-value='" + val.name + "'>";
				htmlVar += '<a href="#" onClick="goFolder(\'' + val.path.replace(/\\/g,'/') + '\');" >'+val.name+'</a>';
				htmlVar += "</td>";
				
				htmlVar += "<td data-value='0'></td>";
				htmlVar += "<td style='vertical-align:middle' align='right' data-dateformat='YYYY/MM/DD HH:mm:ss' data-value='" + val.lastModified + "'>";
				htmlVar += "<label>" + val.lastModified + "</label>";
				htmlVar += "</td>";
				
				htmlVar += "</tr>";
				return htmlVar;
		} else {
			var htmlVar = "<tr>";
			
				htmlVar += "<td style='vertical-align:middle' align='center' data-value='1'>";
			    htmlVar += '<span class="glyphicon glyphicon-file"></span>';
			    htmlVar += "</td>";
			    
			    htmlVar += "<td style='vertical-align:middle' data-value='" + val.name + "'>";
			    htmlVar += '<a href="' + downloadURL + '?TargetPath='+encodeURIComponent(encodeURIComponent(val.path))+'">'+val.name+'</a>';
			    htmlVar += "</td>";
			    
			    htmlVar += "<td style='vertical-align:middle' align='right' data-value='" + val.realSize + "'>";
				htmlVar += "<label>" + val.size + "</label>";
				htmlVar += "</td>";
				
			    htmlVar += "<td style='vertical-align:middle' align='right' data-dateformat='YYYY/MM/DD HH:mm:ss' data-value='" + val.lastModified + "'>";
				htmlVar += "<label>" + val.lastModified + "</label>";
				htmlVar += "</td>";
				
				htmlVar += "</tr>";
				return htmlVar;
		}
	}
	
	function endsWith(str, suffix) 
	{
	    return str.indexOf(suffix, str.length - suffix.length) !== -1;
	}
	
	function search(str)
	{
		if (str == '') {
			$('#content-tbody-2').find('tr').show();
		} else {
			var tolowcaseStr = str.toLowerCase();
			$('#content-tbody-2').find('tr').hide();
			$.each($('#content-tbody-2').find('tr'), function(index, tr) {
				var jTr = $(tr);
				var showed = false;
				$.each(jTr.find('span'), function(key, span) {
					if ($(span).attr('class').toLowerCase().indexOf(tolowcaseStr) != -1) {
						jTr.show();
						showed = true;
						return false;
					} else {
						return true;
					}
				});
				
				if (showed == false) {
					$.each(jTr.find('a'), function(key, a) {
						if ($(a).text().toLowerCase().indexOf(tolowcaseStr) != -1) {
							jTr.show();
							showed = true;
							return false;
						} else {
							return true;
						}
					});
				}
				
				if (showed == false) {
					$.each(jTr.find('label'), function(key, label) {
						if ($(label).text().toLowerCase().indexOf(tolowcaseStr) != -1) {
							jTr.show();
							showed = true;
							return false;
						} else {
							return true;
						}
					});
				} 
			});
		}
	}
	
	function initFileUpload()
	{
		$('#fileupload').fileupload({
	        dataType: 'json',
	        add : function (e, data) {
	        	data.url = "UploadServlet?Path=" + encodeURIComponent(encodeURIComponent($("#Path").val()));
	        	data.submit();
	        },
	        done: function (e, data) {
	        	var downloadURL = $("#DownloadURL").val();
	        	$.each(data.result, function (index, file) {
	        		var dataArea = $('#content-tbody-2');
	        		dataArea.html(dataArea.html() + createRow(downloadURL, file));
	        	});
	        }
	    });
	}
	
	