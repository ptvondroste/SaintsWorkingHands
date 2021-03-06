<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User Profile Form</title>
</head>
<body>
<p>
	<form name="Home" action="" method="post">
	<input type="submit" value="Home">
	</p>
	<form name="userForm" action="" onsubmit="return validation()" method="post">
	<br>First Name: <input type="text" name="userFirstName"> 
		<br>Last Name: <input type="text" name="userLastName"> 
		<br>Sign In Name: <input type="text" name="signInName">
		<br>Password: <input type="password" name="password"> 
		<br>Address: <input type="text" name="address">
		<br>State: <select name="State">
    <option value="AL">Alabama</option>
	<option value="AK">Alaska</option>
	<option value="AZ">Arizona</option>
	<option value="AR">Arkansas</option>
	<option value="CA">California</option>
	<option value="CO">Colorado</option>
	<option value="CT">Connecticut</option>
	<option value="DE">Delaware</option>
	<option value="DC">District Of Columbia</option>
	<option value="FL">Florida</option>
	<option value="GA">Georgia</option>
	<option value="HI">Hawaii</option>
	<option value="ID">Idaho</option>
	<option value="IL">Illinois</option>
	<option value="IN">Indiana</option>
	<option value="IA">Iowa</option>
	<option value="KS">Kansas</option>
	<option value="KY">Kentucky</option>
	<option value="LA">Louisiana</option>
	<option value="ME">Maine</option>
	<option value="MD">Maryland</option>
	<option value="MA">Massachusetts</option>
	<option value="MI">Michigan</option>
	<option value="MN">Minnesota</option>
	<option value="MS">Mississippi</option>
	<option value="MO">Missouri</option>
	<option value="MT">Montana</option>
	<option value="NE">Nebraska</option>
	<option value="NV">Nevada</option>
	<option value="NH">New Hampshire</option>
	<option value="NJ">New Jersey</option>
	<option value="NM">New Mexico</option>
	<option value="NY">New York</option>
	<option value="NC">North Carolina</option>
	<option value="ND">North Dakota</option>
	<option value="OH">Ohio</option>
	<option value="OK">Oklahoma</option>
	<option value="OR">Oregon</option>
	<option value="PA">Pennsylvania</option>
	<option value="RI">Rhode Island</option>
	<option value="SC">South Carolina</option>
	<option value="SD">South Dakota</option>
	<option value="TN">Tennessee</option>
	<option value="TX">Texas</option>
	<option value="UT">Utah</option>
	<option value="VT">Vermont</option>
	<option value="VA">Virginia</option>
	<option value="WA">Washington</option>
	<option value="WV">West Virginia</option>
	<option value="WI">Wisconsin</option>
	<option value="WY">Wyoming</option>
  </select>
  Zip Code: <input type="text" name="zipCode">
		<br>Phone Number: <input type="text" name="phoneNumber">
		<br>Email: <input type="email" name="email">
		<br> <br> <input type="submit" value="Create User"> 
	</form>
	<script>
		function validation() {

			var firstName = document.forms["userForm"]["userFirstName"].value;
			var lastName = document.forms["userForm"]["userLastName"].value;
			var signInName = document.forms["userForm"]["signInName"].value;
			var password = document.forms["userForm"]["password"].value;
			var phoneNumber = document.forms["userForm"]["phoneNumber"].value;
			var zipCode = document.forms["userForm"]["zipCode"].value

			var letters = /^[A-Za-z\s]+$/;
			var phoneVal = /^\+?([0-9]{2})\)?[-. ]?([0-9]{4})[-. ]?([0-9]{4})$/;
			var zipVal = /^\d{5}(-\d{4})?$/;

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
			
			if (signInName.length < 2) {
				alert("Sign In Name is too short!");
			}
			
			if (phoneNumber.value.match(phoneVal) == false) {
				alert("Phone Number is not valid!");
			}
			
			if (zipVal.test(zipCode) === false) {
				alert("Zip Code is not valid!");
			}

		}
	</script>
</body>
</html>