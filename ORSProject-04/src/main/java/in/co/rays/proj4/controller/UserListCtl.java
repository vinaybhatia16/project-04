 package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.UserBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.RoleModel;
import in.co.rays.proj4.model.UserModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * User List functionality Controller. Performs structural operations for listing, 
 * filtering, searching, and managing batch profile deletions for user entities.
 * 
 * @author vinay
 * @version 1.0
 */
@WebServlet("/UserListCtl")
public class UserListCtl extends BaseCtl {
	private static final Logger log = Logger.getLogger(UserListCtl.class);

	/**
	 * Preloads contextual dependencies, retrieving the complete security role master 
	 * list to populate dynamic dropdown filters within the user tracking view.
	 * 
	 * @param request the {@link HttpServletRequest} object holding user request data
	 */
	@Override
	protected void preload(HttpServletRequest request) {
		log.debug("UserListCtl preload() called");
		RoleModel roleModel = new RoleModel();
		try {
			List roleList = roleModel.list();
			System.out.println(roleList.size());
			request.setAttribute("roleList", roleList);
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Populates the {@link UserBean} object with specific criteria properties 
	 * extracted from the request context for targeted query filtering workflows.
	 * 
	 * @param request the {@link HttpServletRequest} object containing raw parameter text elements
	 * @return BaseBean containing the partial search filters for User records
	 */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		log.debug("UserListCtl populateBean() called");
		UserBean bean = new UserBean();

		bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));
		bean.setLogin(DataUtility.getString(request.getParameter("login")));
		bean.setRoleId(DataUtility.getLong(request.getParameter("roleId")));

		return bean;
	}

	/**
	 * Handles HTTP GET requests. Fetches and initiates the foundational introductory 
	 * index layout view for the active user master tracking table.
	 * 
	 * @param request the {@link HttpServletRequest} object
	 * @param response the {@link HttpServletResponse} object
	 * @throws ServletException if a servlet-specific execution logic error occurs
	 * @throws IOException if an alternate network I/O execution error happens
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.info("UserListCtl doGet() started");
		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

		UserBean bean = (UserBean) populateBean(request);
		UserModel model = new UserModel();

		try {
			List<UserBean> list = model.search(bean, pageNo, pageSize);
			List<UserBean> next = model.search(bean, pageNo + 1, pageSize);

			if (list == null || list.isEmpty()) {
				ServletUtility.setErrorMessage("No record found", request);
			}

			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.setBean(bean, request);
			request.setAttribute("nextListSize", next.size());

			ServletUtility.forward(getView(), request, response);

		} catch (ApplicationException e) {
			e.printStackTrace();
			return;
		}
	}

	/**
	 * Handles HTTP POST requests. Oversees complex presentation state behaviors, covering execution workflows 
	 * for list queries, table pagination (Next/Previous shifts), batch delete processes, 
	 * workspace resets, or navigation path overrides.
	 * 
	 * @param request the {@link HttpServletRequest} object
	 * @param response the {@link HttpServletResponse} object
	 * @throws ServletException if a servlet-specific execution logic error occurs
	 * @throws IOException if an alternate network I/O execution error happens
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.info("UserListCtl doPost() started");

		List list = null;
		List next = null;

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

		UserBean bean = (UserBean) populateBean(request);
		UserModel model = new UserModel();

		String op = DataUtility.getString(request.getParameter("operation"));
		String[] ids = request.getParameterValues("ids");

		try {

			if (OP_SEARCH.equalsIgnoreCase(op) || "Next".equalsIgnoreCase(op) || "Previous".equalsIgnoreCase(op)) {

				if (OP_SEARCH.equalsIgnoreCase(op)) {
					pageNo = 1;
				} else if (OP_NEXT.equalsIgnoreCase(op)) {
					pageNo++;
				} else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
					pageNo--;
				}

			} else if (OP_NEW.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.USER_CTL, request, response);
				return;

			} else if (OP_DELETE.equalsIgnoreCase(op)) {
				pageNo = 1;
				if (ids != null && ids.length > 0) {
					UserBean deletebean = new UserBean();
					for (String id : ids) {
						deletebean.setId(DataUtility.getInt(id));
						model.delete(deletebean);
						ServletUtility.setSuccessMessage("User deleted successfully", request);
					}
				} else {
					ServletUtility.setErrorMessage("Select at least one record", request);
				}

			} else if (OP_RESET.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.USER_LIST_CTL, request, response);
				return;

			} else if (OP_BACK.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.USER_LIST_CTL, request, response);
				return;
			}

			list = model.search(bean, pageNo, pageSize);
			next = model.search(bean, pageNo + 1, pageSize);

			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No record found ", request);
			}

			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.setBean(bean, request);
			request.setAttribute("nextListSize", next.size());

			ServletUtility.forward(getView(), request, response);

		} catch (ApplicationException e) {
			e.printStackTrace();
			return;
		}
	}

	/**
	 * Returns the mapped target presentation view configured for displaying lists of user profiles.
	 * 
	 * @return String representing the view path constant mapping
	 */
	@Override
	protected String getView() {
		log.debug("Returning UserList view page");
		return ORSView.USER_LIST_VIEW;
	}
}