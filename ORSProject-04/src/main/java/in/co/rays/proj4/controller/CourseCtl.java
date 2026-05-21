package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.CourseBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.CourseModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * CourseCtl handles course-related operations such as
 * adding, updating, validating, and displaying course data.
 * 
 * It processes user requests and interacts with CourseModel
 * for database operations.
 * 
 * This controller extends BaseCtl.
 * 
 * @author Vinay
 * @version 1.0
 */
@WebServlet(name = "CourseCtl", urlPatterns = { "/ctl/CourseCtl" })
public class CourseCtl extends BaseCtl {

	/**
	 * Validates user input fields.
	 * 
	 * @param request HttpServletRequest object
	 * @return true if validation succeeds otherwise false
	 */
	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("name"))) {

			request.setAttribute("name",
					PropertyReader.getValue(
							"error.require", "Name"));

			pass = false;

		} else if (!DataValidator.isName(
				request.getParameter("name"))) {

			request.setAttribute(
					"name",
					"Invalid Name");

			pass = false;
		}

		if (DataValidator.isNull(
				request.getParameter("duration"))) {

			request.setAttribute(
					"duration",
					PropertyReader.getValue(
							"error.require",
							"Duration"));

			pass = false;
		}

		if (DataValidator.isNull(
				request.getParameter("description"))) {

			request.setAttribute(
					"description",
					PropertyReader.getValue(
							"error.require",
							"Description"));

			pass = false;
		}

		return pass;
	}

	/**
	 * Populates request data into CourseBean.
	 * 
	 * @param request HttpServletRequest object
	 * @return populated CourseBean object
	 */
	@Override
	protected BaseBean populateBean(
			HttpServletRequest request) {

		CourseBean bean = new CourseBean();

		bean.setId(
				DataUtility.getLong(
						request.getParameter("id")));

		bean.setName(
				DataUtility.getString(
						request.getParameter("name")));

		bean.setDuration(
				DataUtility.getString(
						request.getParameter("duration")));

		bean.setDescription(
				DataUtility.getString(
						request.getParameter("description")));

		populateDTO(bean, request);

		return bean;
	}

	/**
	 * Handles GET request and loads course data
	 * for editing by id.
	 * 
	 * @param request HttpServletRequest object
	 * @param response HttpServletResponse object
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	protected void doGet(
			HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {

		long id = DataUtility.getLong(
				request.getParameter("id"));

		CourseModel model = new CourseModel();

		if (id > 0) {

			try {

				CourseBean bean =
						model.findByPk(id);

				ServletUtility.setBean(
						bean,
						request);

			} catch (ApplicationException e) {

				e.printStackTrace();

				ServletUtility.handleException(
						e,
						request,
						response);

				return;
			}
		}

		ServletUtility.forward(
				getView(),
				request,
				response);
	}

	/**
	 * Handles POST request for save, update,
	 * cancel and reset operations.
	 * 
	 * @param request HttpServletRequest object
	 * @param response HttpServletResponse object
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	protected void doPost(
			HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {

		String op = DataUtility.getString(
				request.getParameter("operation"));

		CourseModel model = new CourseModel();

		long id = DataUtility.getLong(
				request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {

			CourseBean bean =
					(CourseBean) populateBean(request);

			try {

				model.add(bean);

				ServletUtility.setBean(
						bean,
						request);

				ServletUtility.setSuccessMessage(
						"Course added successfully",
						request);

			} catch (DuplicateRecordException e) {

				ServletUtility.setBean(
						bean,
						request);

				ServletUtility.setErrorMessage(
						"Course already exists",
						request);

			} catch (ApplicationException e) {

				e.printStackTrace();

				ServletUtility.handleException(
						e,
						request,
						response);

				return;
			}

		} else if (OP_UPDATE.equalsIgnoreCase(op)) {

			CourseBean bean =
					(CourseBean) populateBean(request);

			try {

				if (id > 0) {
					model.update(bean);
				}

				ServletUtility.setBean(
						bean,
						request);

				ServletUtility.setSuccessMessage(
						"Course updated successfully",
						request);

			} catch (DuplicateRecordException e) {

				ServletUtility.setBean(
						bean,
						request);

				ServletUtility.setErrorMessage(
						"Course already exists",
						request);

			} catch (ApplicationException e) {

				e.printStackTrace();

				ServletUtility.handleException(
						e,
						request,
						response);

				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(
					ORSView.COURSE_LIST_CTL,
					request,
					response);

			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(
					ORSView.COURSE_CTL,
					request,
					response);

			return;
		}

		ServletUtility.forward(
				getView(),
				request,
				response);
	}

	/**
	 * Returns course view page path.
	 * 
	 * @return course view page path
	 */
	@Override
	protected String getView() {
		return ORSView.COURSE_VIEW;
	}
}