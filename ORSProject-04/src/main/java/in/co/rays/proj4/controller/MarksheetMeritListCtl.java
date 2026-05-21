package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.MarksheetBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.MarksheetModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * Marksheet Merit List functionality Controller. Performs operations for 
 * retrieving and displaying the top merit positions of student marksheet records.
 * 
 * @author vinay
 * @version 1.0
 */
@WebServlet(name = "MarksheetMeritListCtl", urlPatterns = { "/ctl/MarksheetMeritListCtl" })
public class MarksheetMeritListCtl extends BaseCtl {

	/**
	 * Handles HTTP GET requests. Fetches and displays the merit list compilation 
	 * based on aggregated scores from the database layers.
	 * 
	 * @param request the {@link HttpServletRequest} object
	 * @param response the {@link HttpServletResponse} object
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O network exception occurs
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

		MarksheetModel model = new MarksheetModel();

		try {
			List<MarksheetBean> list = model.getMeritList(pageNo, pageSize);

			if (list == null || list.isEmpty()) {
				ServletUtility.setErrorMessage("No record found", request);
			}

			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);

			ServletUtility.forward(getView(), request, response);

		} catch (ApplicationException e) {
			e.printStackTrace();
			ServletUtility.handleException(e, request, response);
			return;
		}
	}

	/**
	 * Handles HTTP POST requests. Processes control directives sent from presentation 
	 * layers, such as managing the 'Back' operation action to route users back to 
	 * the welcome index control view.
	 * 
	 * @param request the {@link HttpServletRequest} object
	 * @param response the {@link HttpServletResponse} object
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O network exception occurs
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String op = DataUtility.getString(request.getParameter("operation"));

		if (OP_BACK.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.WELCOME_CTL, request, response);
			return;
		}
	}

	/**
	 * Returns the mapped target presentation view configured for presenting marksheet merit lists.
	 * 
	 * @return String representing the view path constant mapping
	 */
	@Override
	protected String getView() {
		return ORSView.MARKSHEET_MERIT_LIST_VIEW;
	}
}