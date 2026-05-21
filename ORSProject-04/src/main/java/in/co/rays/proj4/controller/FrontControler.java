package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import in.co.rays.proj4.util.ServletUtility;

/**
 * Front Controller Filter implementation that acts as a gatekeeper for the application.
 * It intercepts all requests targeting protected resources under matching URL patterns 
 * (e.g., "/ctl/*", "/doc/*") and verifies whether a valid user session exists.
 * If the session is missing or expired, it redirects the user back to the login page.
 * 
 * @author vinay
 * @version 1.0
 */
@WebFilter(filterName = "FrontCtl", urlPatterns = { "/ctl/*", "/doc/*" })
public class FrontControler implements Filter {

	/**
	 * Initializes the Front Controller filter.
	 * 
	 * @param filterConfig a {@link FilterConfig} object containing initialization parameters
	 * @throws ServletException if an error occurs during filter initialization
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}  

	/**
	 * Intercepts incoming client servlet requests to validate user session authenticity. 
	 * If the user attribute is absent from the session, an error message is set and the 
	 * request is forwarded to the login page. Otherwise, the request is permitted to 
	 * proceed down the filter chain.
	 * 
	 * @param req the {@link ServletRequest} object containing client request details
	 * @param resp the {@link ServletResponse} object for rendering responses
	 * @param chain the {@link FilterChain} invoked to pass the request to the next entity
	 * @throws IOException if an input/output exception occurs during processing
	 * @throws ServletException if a servlet processing error occurs
	 */
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("Fctl Do filter");
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;

		HttpSession session = request.getSession();
		if (session.getAttribute("user") == null) {

			ServletUtility.setErrorMessage("your session has been expired... please login again", request);
			
			ServletUtility.forward(ORSView.LOGIN_VIEW, request, response);
			
			return;

		}
		else {
			chain.doFilter(req, resp);
		}
	}

	/**
	 * Called by the web container to indicate that the filter is being taken out of service.
	 * Cleans up any resources held by the filter instance.
	 */
	@Override
	public void destroy() {

	}

}