package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.RoleBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.RoleModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * Role List functionality Controller. Performs operations for listing, 
 * searching, and batch deletion actions for Security Role records.
 * 
 * @author vinay
 * @version 1.0
 */
@WebServlet(name = "RoleListCtl", urlPatterns = { "/ctl/RoleListCtl" })
public class RoleListCtl extends BaseCtl {

	/**
	 * Populates the {@link RoleBean} data entity container with structural criteria parameters 
	 * passed by the client for targeted data searching workflows.
	 * 
	 * @param request the {@link HttpServletRequest} object containing raw input mapping elements
	 * @return BaseBean containing filtered role entity conditions
	 */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		RoleBean bean = new RoleBean();

		bean.setName(DataUtility.getString(request.getParameter("name")));
		bean.setId(DataUtility.getLong(request.getParameter("roleId")));
		return bean;
	}

	/**
	 * Handles HTTP GET requests. Fetches and initializes the introductory page of the 
	 * security roles master ledger layout view.
	 * 
	 * @param request the {@link HttpServletRequest} object
	 * @param response the {@link HttpServletResponse} object
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O network execution exception occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("paze.size"));

		RoleBean bean = (RoleBean) populateBean(request);
		RoleModel model = new RoleModel();
		try {

			List<RoleBean> list = model.search(bean, pageNo, pageSize);
			List<RoleBean> next = model.search(bean, pageNo + 1, pageSize);
			if (list == null || list.isEmpty()) {

				ServletUtility.setErrorMessage("no record found ", request);
			}

			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.setBean(bean, request);
			request.setAttribute("nextListSize", next.size());

			ServletUtility.forward(getView(), request, response);

		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

	/**
	 * Handles HTTP POST requests. Oversees complex presentation state behaviors, covering execution workflows 
	 * for list queries, table pagination (Next/Previous step updates), batch delete executions, 
	 * or resetting standard criteria conditions back to baseline.
	 * 
	 * @param request the {@link HttpServletRequest} object
	 * @param response the {@link HttpServletResponse} object
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O network execution exception occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List list = null;
		List next = null;

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;
		RoleBean bean = (RoleBean) populateBean(request);
		RoleModel model = new RoleModel();
		String op = DataUtility.getString(request.getParameter("operation"));
		String[] ids = request.getParameterValues("ids");
		try {

			if (OP_SEARCH.equalsIgnoreCase(op) || "Next".equalsIgnoreCase(op) || "Previous".equalsIgnoreCase(op)) {
				if (OP_SEARCH.equals(op)) {
					pageNo = 1;
				} else if (OP_NEXT.equals(op)) {
					pageNo++;
				} else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
					pageNo--;
				}

			} else if (OP_NEW.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.ROLE_CTL, request, response);
				return;
			} else if (OP_DELETE.equalsIgnoreCase(op)) {
				pageNo = 1;
				if (ids != null && ids.length > 0) {
					RoleBean deletebean = new RoleBean();
					for (String id : ids) {
						deletebean.setId(DataUtility.getInt(id));
						model.delete(deletebean);
						ServletUtility.setSuccessMessage("data is deleted successfully", request);

					}
				} else {
					ServletUtility.setErrorMessage("select at least one record", request);
				}

			} else if (OP_RESET.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.ROLE_LIST_CTL, request, response);
				return;
			}

			list = model.search(bean, pageNo, pageSize);
			next = model.search(bean, pageNo + 1, pageSize);

			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("no record found ", request);
			}
			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.setBean(bean, request);
			request.setAttribute("nextListSize", next.size());

			ServletUtility.forward(getView(), request, response);
		}

		catch (ApplicationException e) {
			e.printStackTrace();
			return;
		}
	}

	/**
	 * Returns the mapped source target presentation view configured for displaying lists of security roles.
	 * 
	 * @return String representing the view path constant mapping
	 */
	@Override
	protected String getView() {

		return ORSView.ROLE_LIST_VIEW;
	}

}