<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.controller.UserRegistrationCtl"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<%@ include file="Header.jsp"%>
	<form action="<%=ORSView.USER_REGISTRATION_CTL%>" method="post">

		<%-- <%UserBean bean = (UserBean) request.getAttribute("bean"); %> --%>

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.UserBean"
			scope="request"></jsp:useBean>

		<div align="center">
			<h1>User Registration</h1>

			<div style="height: 15px; margin-bottom: 12px">
				<H3 align="center">
					<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
					</font>
				</H3>
				<H3 align="center">
					<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
					</font>
				</H3>
			</div>

			<table>
				<tr>
					<th>First Name:</th>
					<td><input type="text" name="firstName"
						placeholder="enter first name"
						value="<%=DataUtility.getStringData(bean.getFirstName())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("firstName", request)%></font></td>

				</tr>
				<tr>
					<th>Last Name:</th>
					<td><input type="text" name="lastName"
						placeholder="enter last name"
						value="<%=DataUtility.getStringData(bean.getLastName())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("lastName", request)%></font></td>

				</tr>
				<tr>
					<th>Login ID:</th>
					<td><input type="text" name="login"
						placeholder="enter loginId"
						value="<%=DataUtility.getStringData(bean.getLogin())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("login", request)%></font></td>

				</tr>
				<tr>
					<th>Password:</th>
					<td><input type="text" name="password"
						placeholder="enter password"
						value="<%=DataUtility.getStringData(bean.getPassword())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("password", request)%></font></td>

				</tr>
				<tr>
					<th>Confirm:</th>
					<td><input type="text" name="confirmPassword"
						placeholder="confirm password"
						value="<%=DataUtility.getStringData(bean.getConfirmPassword())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("confirmPassword", request)%></font></td>

				</tr>
				<tr>
					<th align="left">Date of Birth<span style="width: 98%"
						style="color: red">*</span></th>
					<td><input type="udate" name="dob" style="width: 98%"
						value="<%=DataUtility.getDateString(bean.getDob())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("dob", request)%></font></td>

				</tr>
				<tr>
					<th>Gender:</th>
					<td><select style="width: 169px; text-align-last: center;"
						class='form-control' name='gender'>
							<option selected value=''>-------------Select-------------</option>
							<option value='male'>male</option>
							<option value='female'>female</option>
					</select></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("gender", request)%></font></td>

				</tr>
				<tr>
					<th>Mobile:</th>
					<td><input type="text" name="mobileNo"
						placeholder="enter mobile number"
						value="<%=DataUtility.getStringData(bean.getMobileNo())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("mobileNo", request)%></font></td>


				</tr>
				<tr>
					<td></td>
					<td><input type="submit" name="operation"
						value="<%=UserRegistrationCtl.OP_SIGN_UP%>"> <input
						type="submit" name="operation"
						value="<%=UserRegistrationCtl.OP_RESET%>"></td>

				</tr>
			</table>
		</div>
	</form>
	<%@ include file="Footer.jsp"%>
</body>
</html>