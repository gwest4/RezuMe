import database.*;
import provider.*;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * OrganizationHomeServlet.
 * 
 * @author Adrian Baran
 */
public class OrganizationHomeServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("text/html");
    HttpSession session = request.getSession();
    PrintWriter out = response.getWriter();
    HashMap<String,String> profileData = DatabaseController.getInstance().getOrganizationProfileData((String) session.getAttribute("currentUser"));
    StringBuilder htmlStringBuilder = new StringBuilder(HtmlProvider.getInstance().getLoggedInHead("organization-home.css"));

    htmlStringBuilder.append("<div id=\"body-text\">");
    htmlStringBuilder.append(HtmlProvider.getInstance().getOrganizationNavBarHead());
    htmlStringBuilder.append("<p class=\"body-text-text\">Signed in as <strong>" + profileData.get("name") +
    		"</strong><br><br>Here are your matches:</p>");
    ArrayList<HashMap<String,String>> matches = MatchProvider.getMatchesForOrganization((String) session.getAttribute("currentUser"));
	htmlStringBuilder.append("\r\n<div id=\"match-box\">\r\n\t"+/*"Sorry, no matches at this time."+*/"\r\n");
	for (HashMap<String,String> match : matches) {
		htmlStringBuilder.append("<div id=\"match\">");
		htmlStringBuilder.append("<table id=\"match-table\">"
				+ "<tr class=\"table-header\">"
				+ "<th>Organization</td>"
				+ "<th>Job Title</td>"
				+ "<th class=\"long\">Job Description</td>"
				+ "<th>Location</td>"
				+ "<th>Phone</td>"
				+ "</tr><tr>"
				+ "<td>"+match.get("organization_name")+"</td>"
				+ "<td>"+match.get("title")+"</td>"
				+ "<td class=\"long\">"+match.get("description")+"</td>"
				+ "<td>"+match.get("city")+", "+match.get("state")+"</td>"
				+ "<td class=\"long\">"+match.get("phone")+"</td>"
				+ "</tr></table>");
		htmlStringBuilder.append("<form class=\"contact-form\" action=\"ContactServlet\" method=\"post\">"
				+ "<input type=\"hidden\" name=\"job-listing-id\" value=\""+match.get("joblisting_id")+"\"></input>" 
				+ "<input class=\"contact-button\" type=\"submit\""
					+ "value=\"Contact\"></input></form>");
		htmlStringBuilder.append("<p id=\"match-percentage-text\">"+match.get("percentage")+"% match</p>");
		htmlStringBuilder.append("</div>");
	}
	htmlStringBuilder.append("</div>");
    
    htmlStringBuilder.append("</div></div>");
    htmlStringBuilder.append(HtmlProvider.getInstance().getHtmlTail());
    out.println(htmlStringBuilder.toString());
    out.close();
  }
}
