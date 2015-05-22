import database.*;

import provider.*;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * CandidateHomeServlet (Modified from OrganizationHomeServlet)
 * 
 * @author Adrian Baran, George West
 */
public class CandidateHomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		
	    response.setContentType("text/html");
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		
		StringBuilder htmlStringBuilder = new StringBuilder(HtmlProvider.getInstance().getHtmlUserHomeHead("candidate-home.css"));
		
		htmlStringBuilder.append("<div id=\"body-text\">");
		htmlStringBuilder.append("<div id=\"nav-header\">");
		htmlStringBuilder.append("<form class=\"nav-form\" action=\"CandidateHomeServlet\" method=\"post\"><input class=\"nav-button\" type=\"submit\" value=\"Home\"></form>");
		htmlStringBuilder.append("<form class=\"nav-form\" action=\"EditProfileServlet\" method=\"post\"><input class=\"nav-button\" type=\"submit\" value=\"Edit Profile\"></form>");
		htmlStringBuilder.append("<form class=\"nav-form\" action=\"SignOutServlet\" method=\"post\"><input class=\"nav-button\" type=\"submit\" value=\"Sign Out\"></form></div>");
		htmlStringBuilder.append("<p class=\"body-text-text\">Signed in as <strong>" + DatabaseController.getInstance().getCandidateName((String) session.getAttribute("currentUser")) +
				"</strong><br><br>Here are your matches:</p>");
		htmlStringBuilder.append("\r\n<div id=\"match-box\">\r\n\tSorry, no matches at this time.\r\n</div>");
		htmlStringBuilder.append("</div>");
		htmlStringBuilder.append("</div>");
		
	    htmlStringBuilder.append(HtmlProvider.getInstance().getHtmlTail());
	    out.println(htmlStringBuilder.toString());
	    out.close();
	}
}
