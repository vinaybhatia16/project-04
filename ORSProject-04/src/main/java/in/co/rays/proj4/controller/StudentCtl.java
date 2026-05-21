package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.StudentBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.CollegeModel;
import in.co.rays.proj4.model.StudentModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * Student functionality Controller. Handles infrastructure workflow operations 
 * for preloading institutional datasets, creating new profiles, loading, and 
 * updating individual Student enrollment records.
 * 
 * @author vinay
 * @version 1.0
 */
@WebServlet(name = "StudentCtl", urlPatterns = { "/ctl/StudentCtl" })
public class StudentCtl extends BaseCtl {

	/**
	 * Preloads contextual dependencies needed by presentation layers, such as 
	 * retrieving the master college directory list to populate input select options.
	 * 
	 * @param request the {@link HttpServletRequest} object containing transaction data
	 */
	@Override
	protected void preload(HttpServletRequest request) {
		CollegeModel collegeModel = new CollegeModel();
		try {
			List collegeList = collegeModel.list();
			request.setAttribute("collegeList", collegeList);
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Validates input fields submitted from the student profile workspace. Verifies 
	 * explicit value mandates, naming structure, 10-digit telephone rules, 
	 * standard email formatting, and calendar birth date integrity.
	 * 
	 * @param request the {@link HttpServletRequest} object containing raw parameter strings
	 * @return true if data structures match application validation expectations, false otherwise
	 */
	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("firstName"))) {
			request.setAttribute("firstName", PropertyReader.getValue("error.require", "First Name"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("firstName"))) {
			request.setAttribute("firstName", "Invalid First Name");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("lastName"))) {
			request.setAttribute("lastName", PropertyReader.getValue("error.require", "Last Name"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("lastName"))) {
			request.setAttribute("lastName", "Invalid Last Name");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", PropertyReader.getValue("error.require", "Mobile No"));
			pass = false;
		} else if (!DataValidator.isPhoneLength(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", "Mobile No must have 10 digits");
			pass = false;
		} else if (!DataValidator.isPhoneNo(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", "Invalid Mobile No");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("gender"))) {
			request.setAttribute("gender", PropertyReader.getValue("error.require", "Gender"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("email"))) {
			request.setAttribute("email", PropertyReader.getValue("error.require", "Email "));
			pass = false;
		} else if (!DataValidator.isEmail(request.getParameter("email"))) {
			request.setAttribute("email", PropertyReader.getValue("error.email", "Email "));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("collegeId"))) {
			request.setAttribute("collegeId", PropertyReader.getValue("error.require", "College Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("dob"))) {
			request.setAttribute("dob", PropertyReader.getValue("error.require", "Date of Birth"));
			pass = false;
		} else if (!DataValidator.isDate(request.getParameter("dob"))) {
			request.setAttribute("dob", PropertyReader.getValue("error.date", "Date of Birth"));
			pass = false;
		}

		return pass;
	}

	/**
	 * Populates the {@link StudentBean} context data transfer object instance 
	 * using transformed values harvested from user request parameters.
	 * 
	 * @param request the {@link HttpServletRequest} object containing raw form properties
	 * @return BaseBean containing the updated student context variables
	 */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		StudentBean bean = new StudentBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));
		bean.setLastName(DataUtility.getString(request.getParameter("lastName")));
		bean.setDob(DataUtility.getDate(request.getParameter("dob")));
		bean.setGender(DataUtility.getString(request.getParameter("gender")));
		bean.setMobileNo(DataUtility.getString(request.getParameter("mobileNo")));
		bean.setEmail(DataUtility.getString(request.getParameter("email")));
		bean.setCollegeId(DataUtility.getLong(request.getParameter("collegeId")));

		populateDTO(bean, request);

		return bean;
	}

	/**
	 * Handles HTTP GET requests. Searches for a specific Student entry using its 
	 * assigned Primary Key identifier to populate profile inputs for display or edit steps.
	 * 
	 * @param request the {@link HttpServletRequest} object
	 * @param response the {@link HttpServletResponse} object
	 * @throws ServletException if a servlet-specific logic error occurs
	 * @throws IOException if an I/O network execution exception occurs
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		long id = DataUtility.getLong(request.getParameter("id"));

		StudentModel model = new StudentModel();

		if (id > 0) {
			try {
				StudentBean bean = model.findByPk(id);
				ServletUtility.setBean(bean, request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}
		}
		ServletUtility.forward(getView(), request, response);
	}

	/**
	 * Handles HTTP POST requests. Manages distinct transactional workflow operations 
	 * like committing a new student profile, modifying details of an existing profile, 
	 * resetting standard fields, or navigating away to collection tracking lists.
	 * 
	 * @param request the {@link HttpServletRequest} object
	 * @param response the {@link HttpServletResponse} object
	 * @throws ServletException if a servlet-specific logic error occurs
	 * @throws IOException if an I/O network execution exception occurs
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String op = DataUtility.getString(request.getParameter("operation"));

		StudentModel model = new StudentModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {
			StudentBean bean = (StudentBean) populateBean(request);
			try {
				long pk = model.add(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Student added successfully", request);
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Email already exists", request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}
		} else if (OP_UPDATE.equalsIgnoreCase(op)) {
			StudentBean bean = (StudentBean) populateBean(request);
			try {
				if (id > 0) {
					model.update(bean);
				}
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Student updated successfully", request);
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Email already exists", request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.STUDENT_LIST_CTL, request, response);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.STUDENT_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);
	}

	/**
	 * Returns the mapped target presentation view configured for student profile handling.
	 * 
	 * @return String representing the view path constant mapping
	 */
	@Override
	protected String getView() {
		return ORSView.STUDENT_VIEW;
	}
}