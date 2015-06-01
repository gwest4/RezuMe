import database.*;
import provider.*;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class OrganizationProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();

		StringBuilder htmlStringBuilder = new StringBuilder(HtmlProvider.getInstance().getLoggedInHead("candidate-profile.css"));
		htmlStringBuilder.append("<div id=\"body-text\">");
		htmlStringBuilder.append(HtmlProvider.getInstance().getOrganizationNavBarHead());
		
		HashMap<String,String> profileData = DatabaseController.getInstance().getOrganizationProfileData((String) session.getAttribute("currentUser"));
		htmlStringBuilder.append("<p class=\"body-text-header\"><strong>" + 
				profileData.get("name") + "</strong>'s profile</p>");
		htmlStringBuilder.append("<p class=\"body-text-text\">Organization info:");
		htmlStringBuilder.append("\r\n<table id=\"profile-table\">\r\n\t"
				+ "<tr>\r\n\t\t"
				+ "<th>Email</th><td>"+ profileData.get("email") +"</td>\r\n\t</tr>\r\n\t"
				+ "<tr>\r\n\t\t"
				+ "<th>Address</th><td>"+ profileData.get("address") +"</td>\r\n\t</tr>\r\n\t"
				+ "<tr>\r\n\t\t"
				+ "<th>City</th><td>"+ profileData.get("city") +"</td>\r\n\t</tr>\r\n\t"
				+ "<tr>\r\n\t\t"
				+ "<th>State</th><td>"+ profileData.get("state") +"</td>\r\n\t</tr>\r\n\t"
				+ "<tr>\r\n\t\t"
				+ "<th>Zip</th><td>"+ profileData.get("zip") +"</td>\r\n\t</tr>\r\n\t"
				+ "<tr>\r\n\t\t"
				+ "<th>Phone</th><td>"+ profileData.get("phone") +"</td>\r\n\t</tr>\r\n\t"
				+ "</table>\r\n");
		htmlStringBuilder.append("<br><br>");
		htmlStringBuilder.append("<form class=\"form\" action=\"EditProfileServlet\" method=\"post\">");
		htmlStringBuilder.append("<input id=\"submit-button\" type=\"submit\" value=\"Edit Profile\"></form>");
		htmlStringBuilder.append("</p>");
		htmlStringBuilder.append("</div>");
		htmlStringBuilder.append("</div>");
		
		htmlStringBuilder.append(HtmlProvider.getInstance().getHtmlTail());
		out.println(htmlStringBuilder.toString());
		out.close();
	}
}