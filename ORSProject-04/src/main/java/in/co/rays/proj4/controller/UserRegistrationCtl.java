package in.co.rays.proj4.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.RoleBean;
import in.co.rays.proj4.bean.UserBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.UserModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.ServletUtility;

/**
 * User Registration functionality Controller. Manages public self-service sign-up 
 * workflows, processes incoming validation rules, maps student-level security roles, 
 * and handles persistent new user registrations.
 * 
 * @author vinay
 * @version 1.0
 */
@WebServlet("/UserRegistrationCtl")
public class UserRegistrationCtl extends BaseCtl {

	/**
	 * Operation constant representing the action to execute user registration submission.
	 */
	public static final String OP_SIGN_UP = "Sign Up";

	/**
	 * Validates public criteria received from the sign-up view. Enforces non-empty 
	 * parameters for standard user inputs, tests formatting rules for names, logins, 
	 * phone data, password complexity metrics, and cross-checks matching confirmation elements.
	 * 
	 * @param request the {@link HttpServletRequest} object containing raw parameter strings
	 * @return true if parameters satisfy application input boundaries, false otherwise
	 */
	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("firstName"))) {
			request.setAttribute("firstName", "firstName is required");
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("firstName"))) {
			request.setAttribute("firstName", "firstName is invalid");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("lastName"))) {
			request.setAttribute("lastName", "lastName is required");
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("lastName"))) {
			request.setAttribute("lastName", "Invalid Last Name");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("login"))) {
			request.setAttribute("login", "login is required");
			pass = false;
		} else if (!DataValidator.isEmail(request.getParameter("login"))) {
			request.setAttribute("login", "Invalid login id");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("password"))) {
			request.setAttribute("password", "password is required");
			pass = false;
		} else if (!DataValidator.isPasswordLength(request.getParameter("password"))) {
			request.setAttribute("password", "Password should be 8 to 12 characters");
			pass = false;
		} else if (!DataValidator.isPassword(request.getParameter("password"))) {
			request.setAttribute("password", "Must contain uppercase, lowercase, digit & special character");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("confirmPassword"))) {
			request.setAttribute("confirmPassword", "confirmPassword is required");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("gender"))) {
			request.setAttribute("gender", "gender is required");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("dob"))) {
			request.setAttribute("dob", "dob is required");
			pass = false;
		} else if (!DataValidator.isDate(request.getParameter("dob"))) {
			request.setAttribute("dob", "Invalid date of birth");
			pass = false;
		}

		if (!request.getParameter("password").equals(request.getParameter("confirmPassword"))) {
			request.setAttribute("confirmPassword", "Password and Confirm Password must be Same!");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", "mobileNo is required");
			pass = false;
		} else if (!DataValidator.isPhoneLength(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", "Mobile No must have 10 digits");
			pass = false;
		} else if (!DataValidator.isPhoneNo(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", "Invalid Mobile No");
			pass = false;
		}

		return pass;
	}

	/**
	 * Extracts structural request parameters to assemble a transaction-ready 
	 * {@link UserBean} object instance, forcing default Student authorization privileges.
	 * 
	 * @param request the {@link HttpServletRequest} object holding user text fields
	 * @return BaseBean containing data mapped for self-registration rows
	 */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		UserBean bean = new UserBean();

		bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));
		bean.setLastName(DataUtility.getString(request.getParameter("lastName")));
		bean.setLogin(DataUtility.getString(request.getParameter("login")));
		bean.setPassword(DataUtility.getString(request.getParameter("password")));
		bean.setConfirmPassword(DataUtility.getString(request.getParameter("confirmPassword")));
		bean.setGender(DataUtility.getString(request.getParameter("gender")));
		bean.setDob(DataUtility.getDate(request.getParameter("dob")));
		bean.setMobileNo(DataUtility.getString(request.getParameter("mobileNo")));

		// imp line - registers public users under the student security tier role.
		bean.setRoleId(RoleBean.STUDENT);

		return bean;
	}

	/**
	 * Handles HTTP GET requests. Forwards control to load and present the initial 
	 * public sign-up registration user interface form.
	 * 
	 * @param request the {@link HttpServletRequest} object
	 * @param response the {@link HttpServletResponse} object
	 * @throws ServletException if a servlet-specific execution exception happens
	 * @throws IOException if an alternate network structural I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ServletUtility.forward(getView(), request, response);

	}

	/**
	 * Handles HTTP POST requests. Evaluates interaction flows, matching commands 
	 * to process new profile registrations into the datastore database, manage duplication errors, 
	 * or reset active entry fields.
	 * 
	 * @param request the {@link HttpServletRequest} object
	 * @param response the {@link HttpServletResponse} object
	 * @throws ServletException if a servlet-specific execution exception happens
	 * @throws IOException if an alternate network structural I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String op = DataUtility.getString(request.getParameter("operation"));

		UserModel model = new UserModel();

		if (OP_SIGN_UP.equalsIgnoreCase(op)) {
			UserBean bean = (UserBean) populateBean(request); 

			try {
				long pk = model.add(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Registration successful!", request);
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Login id already exists", request);
			} catch (ApplicationException e) {
				e.printStackTrace();
			}
			ServletUtility.forward(getView(), request, response);
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.USER_REGISTRATION_CTL, request, response);
			return;
		}

	}

	/**
	 * Returns the mapped target view constant associated with managing user registration views.
	 * 
	 * @return String representing the component layout template route destination
	 */
	@Override
	protected String getView() {
		return ORSView.USER_REGISTRATION_VIEW;
	}

}