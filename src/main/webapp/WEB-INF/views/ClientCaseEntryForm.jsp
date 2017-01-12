<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Client Case Entry Form</title>
</head>
<body>
<h1>Client Case Entry Form</h1>
	<p>
	<form name="ClientCaseEntryForm" onsubmit="return validation()" action=""
		method="post">

		<br>First Name: <input type="text" name="firstName"> 
		<br>Last Name: <input type="text" name="lastName"> 
		<br>Birthdate: <input type="text" name="birthDate">
		<br>Address: <input type="text" name="address"> 
		<br>Phone Number: <input type="text" name="phoneNumber"> 
		<br> <br> <input type="submit" value="Login">
	</form>
	</p>
	<p>
	<form name="Home" action="" method="post">
	<input type="submit" value="Home">
	</p>
	<script>
		function validation() {

			var firstName = document.forms["ClientCaseEntryForm"]["firstName"].value;
			var lastName = document.forms["ClientCaseEntryForm"]["lastName"].value;
			var birthDate = document.forms["ClientCaseEntryForm"]["birthDate"].value;
			var address = document.forms["ClientCaseEntryForm"]["address"].value;
			//Need to add validation for address
			var phoneNumber = document.forms["ClientCaseEntryForm"]["phoneNumber"].value;

			var letters = /^[A-Za-z\s]+$/;
			var phoneVal = /^\+?([0-9]{2})\)?[-. ]?([0-9]{4})[-. ]?([0-9]{4})$/;
			var birthDateVal = /^([0-9]{2})\/([0-9]{2})\/([0-9]{4})$/

			if (firstName.length < 2) {
				alert("First name is too short!");
				return false;
			}

			if (letters.test(firstName) == false) {
				alert("First name can only have letters!");
				return false;
			}

			if (lastName.length < 2) {
				alert("Last name is too short!");
				return false;
			}

			if (letters.test(lastName) == false) {
				alert("Last name can only have letters!");
				return false;
			}
			
			if (birthDateVal.test(birthDate) == false) {
				alert("Not a valid date! Please enter Birthdate!")
			}
			
			if (phoneNumber.value.match(phoneVal) == false) {
				alert("Phone Number is not valid!")
			}

		}
	</script>
</body>
</html>