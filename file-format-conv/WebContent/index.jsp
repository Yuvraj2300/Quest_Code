<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
</head>
<body>
	<form id="form_1" action="upload" method="post"
		enctype="multipart/form-data">
		<label id="lbl_1">Upload File</label> 
		<input type="file" name="file" accept=".xls,.xlsx,.json">
		<button id="upldBttn" style="margin-top: 0px" class="btn"
					type="submit" value="Upload File">Upload</button>
		</form>
		
		<form id="form_2"  action="download" method="get" enctype="multipart/form-data">
			<button id="downldBtn" style="margin-top: 0px" class="btn"
						type="submit" value="Upload File">Download</button>
		</form>
</body>
</html>