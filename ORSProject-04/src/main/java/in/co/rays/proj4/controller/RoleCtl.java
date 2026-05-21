package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.RoleBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.RoleModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * Role functionality Controller. Handles operations for creating, 
 * updating, loading, and structural process management of security roles.
 * 
 * @author vinay
 * @version 1.0
 */
@WebServlet(name = "RoleCtl", urlPatterns = { "/ctl/RoleCtl" })
public class RoleCtl extends BaseCtl {

	/**
	 * Validates input fields submitted from the user presentation workspace view. 
	 * Validates mandatory presence and text format specifications for names and descriptions.
	 * 
	 * @param request the {@link HttpServletRequest} object containing user text parameters
	 * @return true if data elements perfectly match system rule frameworks, false otherwise
	 */
	@Override
	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.require", "name"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("name"))) {
			request.setAttribute("name", "invalid name");
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("description"))) {
			request.setAttribute("description", PropertyReader.getValue("error.require", "description"));
			pass = false;
		}

		return pass;
	}

	/**
	 * Populates the {@link RoleBean} data entity object container using structural request parameters.
	 * 
	 * @param request the {@link HttpServletRequest} object containing raw parameter mapping keys
	 * @return BaseBean containing the updated role context variables
	 */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		RoleBean bean = new RoleBean();
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setName(DataUtility.getString(request.getParameter("name")));
		bean.setDescription(DataUtility.getString(request.getParameter("description")));
		return bean;
	}

	/**
	 * Handles HTTP GET requests. Pulls a unique Role data instance using its target Primary Key 
	 * parameter allocation to populate inputs for modification layout screens.
	 * 
	 * @param request the {@link HttpServletRequest} object
	 * @param response the {@link HttpServletResponse} object
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O network connection error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		long id = DataUtility.getLong(request.getParameter("id"));
		RoleModel model = new RoleModel();
		if (id > 0) {
			try {
				RoleBean bean = model.findByPk(id);
				ServletUtility.setBean(bean, request);
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		}
		ServletUtility.forward(getView(), request, response);
	}

	/**
	 * Handles HTTP POST requests. Manages diverse operation routing commands including 
	 * inserting new roles, adjusting details of old roles, resetting current text areas, 
	 * or canceling workflow redirects out to role tracking index tables.
	 * 
	 * @param request the {@link HttpServletRequest} object
	 * @param response the {@link HttpServletResponse} object
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O network connection error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String op = DataUtility.getString(request.getParameter("operation"));
		RoleModel model = new RoleModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		
		if (OP_SAVE.equalsIgnoreCase(op)) {
			RoleBean bean = (RoleBean) populateBean(request);
			
			try {
				long pk = model.add(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("data is successfully saved", request);
			} catch (Exception e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("role already exists", request);
				e.printStackTrace();
				return;
			}
		} else if (OP_UPDATE.equalsIgnoreCase(op)) {
			RoleBean bean = (RoleBean) populateBean(request);
			try {
				if (id > 0) {
					model.update(bean);
				}
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("data updated successfully", request);

			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("role already exists", request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.ROLE_LIST_CTL, request, response);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.ROLE_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);
	}

	/**
	 * Returns the mapped target presentation view configured for updating security roles.
	 * 
	 * @return String representing the view path constant mapping
	 */
	@Override
	protected String getView() {
		return ORSView.ROLE_VIEW;
	}
}