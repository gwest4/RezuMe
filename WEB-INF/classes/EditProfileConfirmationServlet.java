import database.*;
import provider.*;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;

public class EditProfileConfirmationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		String currentUserType;
		if (session.getAttribute("candidateLoggedIn").equals("true")) {
			currentUserType = "candidate";
		} else if (session.getAttribute("organizationLoggedIn").equals("true")) {
			currentUserType = "organization";
		} else {
			currentUserType = null;
		}
		
		boolean isValid = true;
		String errorMessage = "";

		String inputPassword1 = request.getParameter("password1");
		String inputPassword2 = request.getParameter("password2");
		//System.out.println("inputPassword1: "+inputPassword1+"\ninputPassword2: "+inputPassword2);
		
		if (	(inputPassword1 == null || inputPassword2 == null) ||
				(inputPassword1.equals("null") ||inputPassword2.equals("null"))) {
			isValid = false;
			errorMessage = "The password field cannot be empty.";
		}

		if (!inputPassword1.equals(inputPassword2)) {
			isValid = false;
			errorMessage = "Your two passwords do not match. Please try again.";
		}

		String inputEmail = request.getParameter("email");

		if (DatabaseController.getInstance().emailRegistered(inputEmail, "CANDIDATE")) {
			isValid = false;
			errorMessage = "The email that you have provided appears to have already been registered.<br><br>"
					+ "If you think this is a mistake, please submit a ticket to us.";
		}
		
		StringBuilder htmlStringBuilder = new StringBuilder(HtmlProvider.getInstance().getLoggedInHead("edit-profile.css"));
		
		if (!isValid) {
			EditProfileServlet servletObject = new EditProfileServlet();
			servletObject.doPost(request, response);
			response.sendRedirect(UrlProvider.getInstance().getBaseUrl(request)
					+ "/RezuMe/EditProfileServlet");
		} else {
			String email = request.getParameter("email");
			String password="";
			if (inputPassword1 != null && !inputPassword1.equals(""))
				password = PasswordHasher.getInstance().generateHash(inputPassword1);
			String phone = request.getParameter("phone");
			String address = request.getParameter("address");
			String city = request.getParameter("city");
			String state = request.getParameter("state");
			String zip = request.getParameter("zip");
			String school = request.getParameter("school");
			String industry = request.getParameter("industry");
			if (currentUserType.equals("candidate")) {
				DatabaseController.getInstance().updateCandidateProfile((String)session.getAttribute("currentUser"),
						email, password, phone, address, city, state, zip, school, industry);
			} else {
				DatabaseController.getInstance().updateOrganizationProfile((String)session.getAttribute("currentUser"),
						email, password, phone, address, city, state, zip);
			}
			
			if (email != null && !email.equals("")) session.setAttribute("currentUser", email);
			
			htmlStringBuilder.append("<div id=\"body-text\">");
			if (currentUserType.equals("candidate")) {
				htmlStringBuilder.append(HtmlProvider.getInstance().getCandidateNavBarHead());
			} else {
				htmlStringBuilder.append(HtmlProvider.getInstance().getOrganizationNavBarHead());
			}
			
			htmlStringBuilder.append("<p class=\"body-text-header\">"
					+ "\r\n\tYour profile has been updated.\r\n</p>"
					+ "\r\n<p class=\"body-text-text\">"
					+ "\r\n\tClick the button below to return to your home page.\r\n</p>"
					+ "\r\n<form class=\"form\" action=\"");
			if (currentUserType.equals("candidate")) {
				htmlStringBuilder.append("CandidateHomeServlet");
			} else {
				htmlStringBuilder.append("OrganizationHomeServlet");
			}
			htmlStringBuilder.append("\" method=\"post\">"
					+ "\r\n\t<input id=\"submit-button\" type=\"submit\" value=\"Home\">"
					+ "\r\n</form>");
			htmlStringBuilder.append("</div>");
			htmlStringBuilder.append("</div>");
			
			htmlStringBuilder.append(HtmlProvider.getInstance().getHtmlTail());
			out.println(htmlStringBuilder.toString());
			out.close();
		}
	}
}
