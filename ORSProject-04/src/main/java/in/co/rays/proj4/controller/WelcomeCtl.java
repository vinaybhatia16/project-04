package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.util.ServletUtility;

/**
 * Welcome functionality Controller. Serves as the introductory entry landing 
 * point for authenticated or baseline traffic routing out to the system's main dashboard view.
 * 
 * @author vinay
 * @version 1.0
 */
@WebServlet("/WelcomeCtl")
public class WelcomeCtl extends BaseCtl {

	/**
	 * Handles HTTP GET requests. Forwards control directly to render and display 
	 * the application's central welcome landing layout panel view.
	 * 
	 * @param request the {@link HttpServletRequest} object containing client context data
	 * @param response the {@link HttpServletResponse} object containing presentation target pipelines
	 * @throws ServletException if a servlet-specific execution exception happens
	 * @throws IOException if an alternate network structural I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ServletUtility.forward(getView(), request, response);

	}

	/**
	 * Returns the mapped target view constant associated with showing the dashboard welcome layout.
	 * 
	 * @return String representing the component template route destination
	 */
	@Override
	protected String getView() {
		return ORSView.WELCOME_VIEW;
	}

}   