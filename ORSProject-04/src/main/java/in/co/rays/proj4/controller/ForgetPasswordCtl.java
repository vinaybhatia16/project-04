package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.UserBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.RecordNotFoundException;
import in.co.rays.proj4.model.UserModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * Forget Password functionality Controller. Processes the request for resetting 
 * or recovering a forgotten password by verifying the registered email ID and 
 * sending recovery details.
 * 
 * @author vinay
 * @version 1.0
 */
@WebServlet(name = "ForgetPasswordCtl", urlPatterns = { "/ForgetPasswordCtl" })
public class ForgetPasswordCtl extends BaseCtl {

	/**
	 * Validates the user input data submitted from the forget password view.
	 * Checks if the login email is empty or structurally invalid.
	 * 
	 * @param request the {@link HttpServletRequest} object containing the request data
	 * @return true if validation passes, false otherwise
	 */
	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("login"))) {
			request.setAttribute("login", PropertyReader.getValue("error.require", "Email Id"));
			pass = false;
		} else if (!DataValidator.isEmail(request.getParameter("login"))) {
			request.setAttribute("login", PropertyReader.getValue("error.email", "Login "));
			pass = false;
		}

		return pass;
	}

	/**
	 * Populates the {@link UserBean} object with the login credentials 
	 * submitted by the user.
	 * 
	 * @param request the {@link HttpServletRequest} object containing form fields
	 * @return BaseBean containing the email/login identifier data
	 */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		UserBean bean = new UserBean();

		bean.setLogin(DataUtility.getString(request.getParameter("login")));

		return bean;
	}

	/**
	 * Handles HTTP GET requests. Forwards the request directly to the forget password view page.
	 * 
	 * @param request the {@link HttpServletRequest} object
	 * @param response the {@link HttpServletResponse} object
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletUtility.forward(getView(), request, response);
	}

	/**
	 * Handles HTTP POST requests. Executes the "Go" operation to trigger password recovery 
	 * via the application model layers and updates the UI status accordingly.
	 * 
	 * @param request the {@link HttpServletRequest} object
	 * @param response the {@link HttpServletResponse} object
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String op = DataUtility.getString(request.getParameter("operation"));

		UserBean bean = (UserBean) populateBean(request);

		UserModel model = new UserModel();

		if (OP_GO.equalsIgnoreCase(op)) {
			try {
				boolean flag = model.forgetPassword(bean.getLogin());
				if (flag) {
					ServletUtility.setSuccessMessage("Password has been sent to your email id", request);
				}
			} catch (RecordNotFoundException e) {
				ServletUtility.setErrorMessage(e.getMessage(), request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.setErrorMessage("Please check your internet connection..!!", request);
			}
			ServletUtility.forward(getView(), request, response);
		}
	}

	/**
	 * Returns the mapped target presentation view for processing forget password requests.
	 * 
	 * @return String representing the view path
	 */
	@Override
	protected String getView() {
		return ORSView.FORGET_PASSWORD_VIEW;
	}
}