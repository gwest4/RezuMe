import database.*;
import provider.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.*;
import javax.servlet.http.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

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

    session.setAttribute("userType", "ORGANIZATION");

    HashMap<String,String> profileData = DatabaseController.getInstance().getOrganizationProfileData((String) session.getAttribute("currentUser"));
    StringBuilder htmlStringBuilder = new StringBuilder(HtmlProvider.getInstance().getLoggedInHead("organization-home.css"));

    htmlStringBuilder.append("<div id=\"body-text\">");
    htmlStringBuilder.append(HtmlProvider.getInstance().getOrganizationNavBarHead());
    htmlStringBuilder.append("<p class=\"body-text-text\">Signed in as <strong>" + profileData.get("name") +
    		"</strong><br><br>Here are your matches:</p>");
    HashMap<HashMap<String,String>, ArrayList<HashMap<String,String>>> matches =
		MatchProvider.getMatchesForOrganization((String) session.getAttribute("currentUser"));
	htmlStringBuilder.append("\r\n<div id=\"match-box\">\r\n\t"+/*"Sorry, no matches at this time."+*/"\r\n");
	//System.out.println("matches: "+matches);
	for (Entry<HashMap<String,String>, ArrayList<HashMap<String,String>>> entry : matches.entrySet()) {
		htmlStringBuilder.append("<div id=\"match\">"
			+ "<p>"+entry.getKey().get("title")+"</p>");
		htmlStringBuilder.append("<table id=\"match-table\">"
			+ "<tr class=\"table-header\">"
			+ "<th>Name</th>"
			+ "<th>Location</th>"
			+ "<th>Phone</th>"
			+ "<th></th>"
			+ "<th></th>"
			+ "</tr>");
		for (HashMap<String,String> candidate: entry.getValue()) {
			htmlStringBuilder.append("<tr>"
				+ "<td>"+candidate.get("firstname")+" "+candidate.get("lastname")+"</td>"
				+ "<td>"+candidate.get("city")+", "+candidate.get("state")+"</td>"
				+ "<td class=\"long\">"+candidate.get("phone")+"</td>"
				+ "<td id=\"match-percentage-text\">"+candidate.get("percentage")+"% match</td>");
			htmlStringBuilder.append("<td><form class=\"contact-form\" action=\"ContactServlet\" method=\"post\">"
				+ "<input type=\"hidden\" name=\"job-listing-id\" value=\""+candidate.get("candidate_id")
					+ "\"></input>" 
				+ "<input class=\"contact-button\" type=\"submit\""
					+ "value=\"Contact\"></input></form></td>");
			htmlStringBuilder.append("</tr>");
		}
		htmlStringBuilder.append("</table>");
		htmlStringBuilder.append("</div>");
	}
	htmlStringBuilder.append("</div>");
    
    htmlStringBuilder.append("</div></div>");
    htmlStringBuilder.append(HtmlProvider.getInstance().getHtmlTail());
    out.println(htmlStringBuilder.toString());
    out.close();
  }
}
