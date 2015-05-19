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
public class CandidateHomeServlet {
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
	    response.setContentType("text/html");
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		
		StringBuilder htmlStringBuilder = new StringBuilder(HtmlProvider.getInstance().getHtmlHead("organization-home.css"));
		
		htmlStringBuilder.append("<div id=\"body-text\">");
		htmlStringBuilder.append("<div id=\"nav-header\">");
		htmlStringBuilder.append("<form class=\"nav-form\" action=\"CandidateHomeServlet\" method=\"post\"><input class=\"home-button\" type=\"submit\" value=\"Home\"></form>");
		htmlStringBuilder.append("<form class=\"nav-form\" action=\"SignOutServlet\" method=\"post\"><input class=\"home-button\" type=\"submit\" value=\"Sign Out\"></form></div>");
		htmlStringBuilder.append("<p class=\"body-text-text\">Signed in as <strong>" + DatabaseController.getInstance().getCandidateName((String) session.getAttribute("currentUser")) + "</strong></p>");
		htmlStringBuilder.append("</div></div>");
		
	    htmlStringBuilder.append(HtmlProvider.getInstance().getHtmlTail());
	    out.println(htmlStringBuilder.toString());
	    out.close();
	}
}
