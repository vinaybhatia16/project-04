package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.RoleBean;
import in.co.rays.proj4.bean.UserBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.RoleModel;
import in.co.rays.proj4.model.UserModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * Login functionality Controller. Handles operations for user authentication, 
 * session creation, session invalidation (Logout), and routing to registration fields.
 * 
 * @author vinay
 * @version 1.0
 */
@WebServlet("/LoginCtl")
public class LoginCtl extends BaseCtl {

	/** Constant identifier for Sign In operation. */
	public static final String OP_SIGN_IN = "Sign In";
	
	/** Constant identifier for Sign Up operation. */
	public static final String OP_SIGN_UP = "Sign Up";
	
	/** Constant identifier for Logout operation. */
	public static final String OP_LOG_OUT = "Logout";

	/**
	 * Validates input parameters submitted on the login page view.
	 * Bypasses validation during Sign Up or Logout commands. Checks empty constraints 
	 * and formatting for Email/Login ID and Password fields.
	 * 
	 * @param request the {@link HttpServletRequest} object containing user input data
	 * @return true if incoming data matches structural expectations, false otherwise
	 */
	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		String op = request.getParameter("operation");

		if (OP_SIGN_UP.equals(op) || OP_LOG_OUT.equals(op)) {
			return pass;
		}

		if (DataValidator.isNull(request.getParameter("login"))) {
			request.setAttribute("login", PropertyReader.getValue("error.require", "Login Id"));
			pass = false;
		} else if (!DataValidator.isEmail(request.getParameter("login"))) {
			request.setAttribute("login", PropertyReader.getValue("error.email", "Login "));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("password"))) {
			request.setAttribute("password", PropertyReader.getValue("error.require", "Password"));
			pass = false;
		}
		return pass;
	}

	/**
	 * Populates the {@link UserBean} data container object using raw input details 
	 * taken from request elements.
	 * 
	 * @param request the {@link HttpServletRequest} object
	 * @return BaseBean containing the formatted login credentials
	 */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		UserBean bean = new UserBean();
		bean.setLogin(DataUtility.getString(request.getParameter("login")));
		bean.setPassword(DataUtility.getString(request.getParameter("password")));
		return bean;
	}

	/**
	 * Handles HTTP GET requests. Processes user request tracking parameters, performs explicit 
	 * session invalidation if a Logout action is declared, and serves the presentation view.
	 * 
	 * @param request the {@link HttpServletRequest} object
	 * @param response the {@link HttpServletResponse} object
	 * @throws ServletException if a servlet-specific exception occurs
	 * @throws IOException if an I/O network exception occurs
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String op = DataUtility.getString(request.getParameter("operation"));

		if (OP_LOG_OUT.equals(op)) {
			HttpSession session = request.getSession();
			session.invalidate();
			ServletUtility.setSuccessMessage("Logout Successful!", request);
		}
		ServletUtility.forward(getView(), request, response);
	}

	/**
	 * Handles HTTP POST requests. Manages user profile validation processes across data tiers. 
	 * Binds user and role identity instances to the context session if authentication matches, 
	 * or provides routing options to navigate toward the signup registration view.
	 * 
	 * @param request the {@link HttpServletRequest} object
	 * @param response the {@link HttpServletResponse} object
	 * @throws ServletException if a servlet-specific exception occurs
	 * @throws IOException if an I/O network exception occurs
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String op = DataUtility.getString(request.getParameter("operation"));

		System.out.println("op in do post === " + op);

		UserModel model = new UserModel();
		RoleModel role = new RoleModel();
		HttpSession session = request.getSession();

		if (OP_SIGN_IN.equalsIgnoreCase(op)) {

			UserBean bean = (UserBean) populateBean(request);

			try {
				bean = model.authenticate(bean.getLogin(), bean.getPassword());

				if (bean != null) {
					session.setAttribute("user", bean);
					RoleBean rolebean = role.findByPk(bean.getRoleId());
					if (rolebean != null) {
						session.setAttribute("role", rolebean.getName());
					}

					ServletUtility.redirect(ORSView.WELCOME_CTL, request, response);
					return;

				} else {
					ServletUtility.setBean(bean, request);
					ServletUtility.setErrorMessage("Invalid LoginId And Password", request);
				}
			} catch (ApplicationException e) {
				e.printStackTrace();
				return;
			}
		} else if (OP_SIGN_UP.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.USER_REGISTRATION_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	/**
	 * Returns the mapped source target presentation view configured for login tracking.
	 * 
	 * @return String representing the view path constant mapping
	 */
	@Override
	protected String getView() {
		return ORSView.LOGIN_VIEW;
	}
}