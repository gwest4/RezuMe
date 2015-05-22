
import java.io.*;
import java.util.Scanner;

import javax.servlet.*;
import javax.servlet.http.*;

import database.*;

import provider.*;
/**
 * CandidateWapServlet.
 * 
 * @author Aditya Narravula
 */
public class OrganizationWapServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("text/html");
    HttpSession session = request.getSession();
    PrintWriter out = response.getWriter();
	StringBuilder htmlStringBuilder = new StringBuilder();
	htmlStringBuilder.append(HtmlProvider.getInstance().getHtmlCandidateSkillSortHead("signup-skills.css"));

      htmlStringBuilder.append("<div id=\"body-skills\">\r\n\t\t\t<p class=\"body-skills-header\">Job WAP Order<br><br><br></p>\r\n\t\t\t");
      htmlStringBuilder.append("<p class=\"body-skills-text\">Now it's time for you to rank your WAP traits based on your selected job listing.</p>\r\n\t\t\t<br>");
      htmlStringBuilder.append("\r\n\t\t\t<p class=\"body-skills-text\">Use the left pane to drag and drop your desired traits to the right pane in your order of strength.</p>");
      htmlStringBuilder.append("\r\n\t\t\t<br><br><br><br>\r\n\t\t\t");
      htmlStringBuilder.append("<div id=\"available-skills\">\r\n\t\t\t\t");
      htmlStringBuilder.append("<p class=\"box-header-text\">Available WAP Traits</p>\r\n\t\t\t");
      htmlStringBuilder.append("<ul id=\"availableskills\" class=\"connected sortable list\">");
	  htmlStringBuilder.append("<li id=\"leadership\">Leadership</li>");
	  htmlStringBuilder.append("<li id=\"creativity\">Creativity</li>");
	  htmlStringBuilder.append("<li id=\"communication\">Communication</li>");
	  htmlStringBuilder.append("<li id=\"adaptability\">Adaptability</li>");
	  htmlStringBuilder.append("<li id=\"collaboration\">Collaboration</li>");
	  htmlStringBuilder.append("<li id=\"action\">Action Orientation</li>");
	  
	  htmlStringBuilder.append("</ul></div>\r\n\t\t\t");
      htmlStringBuilder.append("<div id=\"selected-skills\">\r\n\t\t\t\t");
      htmlStringBuilder.append("<p class=\"box-header-text\">Selected Traits</p>\r\n\t\t\t");
      htmlStringBuilder.append("<ul id=\"selectedskills\" class=\"connected sortable list\">");
      htmlStringBuilder.append("<li class=\"disabled\">Place your traits below.</li>");
      htmlStringBuilder.append("</ul></div>\r\n\t\t\t");
      htmlStringBuilder.append("<form action=\"CandidateWapServlet\" method=\"post\">\r\n\t\t\t\t");
      htmlStringBuilder.append("<input id=\"submit-button\" type=\"submit\" value=\"Submit\">\r\n\t\t\t</form>\r\n\t\t</div>\r\n\t</div>");
	  
	  htmlStringBuilder.append(HtmlProvider.getInstance().getHtmlTail());
    out.println(htmlStringBuilder.toString());
	out.println("hello");
    out.close();
	}
}
