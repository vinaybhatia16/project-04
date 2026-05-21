package in.co.rays.proj4.util;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.controller.BaseCtl;
import in.co.rays.proj4.controller.ORSView;

/**
 * Utility class providing helper operations to handle common Servlet and JSP architecture 
 * responsibilities. This includes forwarding/redirecting requests, managing error or success 
 * messages within request scopes, mapping Data Beans, and handling pagination values.
 * 
 * @author vinay
 * @version 1.0
 */
public class ServletUtility {

	/**
	 * Forwards a request from a servlet to a target page or view resource inside the web container.
	 * 
	 * @param page     the relative path layout destination view string
	 * @param request  the tracking request scope instance handler
	 * @param response the execution response channel context
	 * @throws IOException      thrown if an input or output exception is caught
	 * @throws ServletException thrown if the target request cannot be routed successfully
	 */
	public static void forward(String page, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		RequestDispatcher rd = request.getRequestDispatcher(page);
		rd.forward(request, response);
	}

	/**
	 * Redirects the client response to an entirely new URL location.
	 * 
	 * @param page     the target destination view or controller string path
	 * @param request  the tracking request scope instance handler
	 * @param response the execution response channel context
	 * @throws IOException      thrown if an input or output exception is caught
	 * @throws ServletException thrown if the target redirect route fails
	 */
	public static void redirect(String page, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		response.sendRedirect(page);
	}

	/**
	 * Extracts an error message stored under a specific field attribute property key inside the request context.
	 * 
	 * @param property the key mapping the target attribute error expression
	 * @param request  the running servlet request context
	 * @return the message string mapped to the property, or an empty string if null
	 */
	public static String getErrorMessage(String property, HttpServletRequest request) {
		String val = (String) request.getAttribute(property);
		if (val == null) {
			return "";
		} else {
			return val;
		}
	}

	/**
	 * Extracts a generic notification message stored under a specific attribute property key inside the request context.
	 * 
	 * @param property the key mapping the target attribute notice expression
	 * @param request  the running servlet request context
	 * @return the message string mapped to the property, or an empty string if null
	 */
	public static String getMessage(String property, HttpServletRequest request) {
		String val = (String) request.getAttribute(property);
		if (val == null) {
			return "";
		} else {
			return val;
		}
	}

	/**
	 * Binds a global error message string to the shared request context scope.
	 * 
	 * @param msg     the content text detailing the application error alert
	 * @param request the running servlet request context
	 */
	public static void setErrorMessage(String msg, HttpServletRequest request) {
		request.setAttribute(BaseCtl.MSG_ERROR, msg);
	}

	/**
	 * Extracts the global error message string currently bound to the shared request context scope.
	 * 
	 * @param request the running servlet request context
	 * @return the global error message string, or an empty string if null
	 */
	public static String getErrorMessage(HttpServletRequest request) {
		String val = (String) request.getAttribute(BaseCtl.MSG_ERROR);
		if (val == null) {
			return "";
		} else {
			return val;
		}
	}

	/**
	 * Binds a global success confirmation message string to the shared request context scope.
	 * 
	 * @param msg     the content text detailing successful transaction operations
	 * @param request the running servlet request context
	 */
	public static void setSuccessMessage(String msg, HttpServletRequest request) {
		request.setAttribute(BaseCtl.MSG_SUCCESS, msg);
	}

	/**
	 * Extracts the global success confirmation message string currently bound to the shared request context scope.
	 * 
	 * @param request the running servlet request context
	 * @return the global success message string, or an empty string if null
	 */
	public static String getSuccessMessage(HttpServletRequest request) {
		String val = (String) request.getAttribute(BaseCtl.MSG_SUCCESS);
		if (val == null) {
			return "";
		} else {
			return val;
		}
	}

	/**
	 * Binds a target business entity bean onto the running request context.
	 * 
	 * @param bean    the model data wrapper object descending from {@link BaseBean}
	 * @param request the running servlet request context
	 */
	public static void setBean(BaseBean bean, HttpServletRequest request) {
		request.setAttribute("bean", bean);
	}

	/**
	 * Extracts the primary model business entity bean currently bound to the running request context.
	 * 
	 * @param request the running servlet request context
	 * @return the mapped model bean object container
	 */
	public static BaseBean getBean(HttpServletRequest request) {
		return (BaseBean) request.getAttribute("bean");
	}

	/**
	 * Safe extraction utility pulling string values out of raw query parameters or form submissions.
	 * Returns an empty string instead of null if the key parameter data is missing.
	 * 
	 * @param property the form field or query parameter variable identifier name
	 * @param request  the running servlet request context
	 * @return the incoming raw parameter value expression, or empty string if null
	 */
	public static String getParameter(String property, HttpServletRequest request) {
		String val = (String) request.getParameter(property);
		if (val == null) {
			return "";
		} else {
			return val;
		}
	}

	/**
	 * Binds a collection list of records onto the tracking request scope (typically used for population on grid lists).
	 * 
	 * @param list    the collection framework data list holding dynamic search elements
	 * @param request the running servlet request context
	 */
	public static void setList(List list, HttpServletRequest request) {
		request.setAttribute("list", list);
	}

	/**
	 * Extracts the primary data collection list currently bound to the running request context.
	 * 
	 * @param request the running servlet request context
	 * @return the bound list data array structure
	 */
	public static List getList(HttpServletRequest request) {
		return (List) request.getAttribute("list");
	}

	/**
	 * Configures the current pagination table page tracking index marker integer value onto the request.
	 * 
	 * @param pageNo  the numeric layout sequence identity pointer value
	 * @param request the running servlet request context
	 */
	public static void setPageNo(int pageNo, HttpServletRequest request) {
		request.setAttribute("pageNo", pageNo);
	}

	/**
	 * Extracts the current pagination table page tracking index marker integer value.
	 * 
	 * @param request the running servlet request context
	 * @return the registered active page list sequence number index
	 */
	public static int getPageNo(HttpServletRequest request) {
		return (Integer) request.getAttribute("pageNo");
	}

	/**
	 * Configures the total size of element sets processed per single display page context.
	 * 
	 * @param pageSize total dynamic rows assigned per view page block
	 * @param request  the running servlet request context
	 */
	public static void setPageSize(int pageSize, HttpServletRequest request) {
		request.setAttribute("pageSize", pageSize);
	}

	/**
	 * Extracts the total size of element sets processed per single display page context.
	 * 
	 * @param request the running servlet request context
	 * @return total dynamic rows assigned per view page block
	 */
	public static int getPageSize(HttpServletRequest request) {
		return (Integer) request.getAttribute("pageSize");
	}

	/**
	 * Handles runtime unexpected system failures by recording exception variables inside 
	 * request state attributes and redirecting the application boundary directly onto the designated system Error View.
	 * 
	 * @param e        the exception instance thrown by the system application context
	 * @param request  the running servlet request context
	 * @param response the execution response channel context
	 * @throws IOException      thrown if redirection path operations break
	 * @throws ServletException thrown if system context redirection traps unexpected processing errors
	 */
	public static void handleException(Exception e, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		request.setAttribute("exception", e);
		response.sendRedirect(ORSView.ERROR_CTL);
	}
}