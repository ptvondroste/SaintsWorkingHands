<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	Home Page! 
</h1>

<P>
<form name="Login" action="Login" method="post">
<input type="submit" value="Login">
</form>
<br>
<form name="ClientCaseEntryForm" action="ClientCaseEntryForm" method="post">
<input type="submit" value="Client Case Entry Form">
<br>
<form name="ClientProfileForm" action="ClientProfileForm" method="post">
<input type="submit" value="Client Profile Form">
<br>
<form name="ClientCaseFiles" action="ClientCaseFiles" method="post">
<input type="submit" value="Client Case Files">
<br>
<form name="ClientCaseFilesEditbyUser" action="ClientCaseFilesEditbyUser" method="post">
<input type="submit" value="Client Case Files Edit by User">
</P>
</body>
</html>
