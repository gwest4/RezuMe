import database.*;
import provider.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 * OrganizationNewJobSkillsServlet.
 * 
 * @author Adrian Baran
 */
public class OrganizationNewJobSkillsServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("text/html");
    HttpSession session = request.getSession();
    PrintWriter out = response.getWriter();
    
    String industry = request.getParameter("industry");
    String title = request.getParameter("title");
    String description = request.getParameter("description");

    DatabaseController.getInstance().addNewJobListing((String) session.getAttribute("currentUser"), industry, title, description);

    session.setAttribute("currentNewJob", DatabaseController.getInstance().getJobListingId(description));

    StringBuilder htmlStringBuilder = new StringBuilder(HtmlProvider.getInstance().getHtmlCandidateSkillSortHead("signup-skills.css"));

    htmlStringBuilder.append("<script>");
    htmlStringBuilder.append("function redirect() {");
    htmlStringBuilder.append("var list = document.getElementById(\"selectedskills\").getElementsByTagName(\"li\");");
    htmlStringBuilder.append("var skillsString = \"\";");
    htmlStringBuilder.append("var i = 0;");
    htmlStringBuilder.append(" for (i = 0; i < list.length; i++) {");
    htmlStringBuilder.append("skillsString += list[i].id + \",\";");
    htmlStringBuilder.append("} document.getElementById(\"skills\").value = skillsString.substring(1, skillsString.length - 1);");
    htmlStringBuilder.append("}</script>");

    htmlStringBuilder.append("<div id=\"body-skills\">\r\n\t\t\t<p class=\"body-skills-header\">New Job Listing<br><br><br></p>\r\n\t\t\t");
    htmlStringBuilder.append("<p class=\"body-skills-text\">Now it's time for you to decide which skills are required for the job.</p>\r\n\t\t\t<br>");
    htmlStringBuilder.append("\r\n\t\t\t<p class=\"body-skills-text\">Use the left pane to drag and drop your desired skills to the right pane in your order of strength.</p>");
    htmlStringBuilder.append("\r\n\t\t\t<br><br><br><br>\r\n\t\t\t");
    htmlStringBuilder.append("<div id=\"available-skills\">\r\n\t\t\t\t");
    htmlStringBuilder.append("<p class=\"box-header-text\">Available Skills</p>\r\n\t\t\t");
    htmlStringBuilder.append("<ul id=\"availableskills\" class=\"connected sortable list\">");
      
    HashMap<String, String> skills = DatabaseController.getInstance().getIndustrySkills(request.getParameter("industry"), "NAME");
      
    if (!(skills == null)) {
      for (String skillsKey : skills.keySet()) {
        htmlStringBuilder.append("<li id=\"" + skillsKey + "\">" + skills.get(skillsKey) + "</li>");
      } 
    } else {
      htmlStringBuilder.append("<option value=\"error\">No Skill Found</option>\r\n");
      log("WARNING: getIndustrySkills() yielded null");
    }

    htmlStringBuilder.append("</ul></div>\r\n\t\t\t");
    htmlStringBuilder.append("<div id=\"selected-skills\">\r\n\t\t\t\t");
    htmlStringBuilder.append("<p class=\"box-header-text\">Selected Skills</p>\r\n\t\t\t");
    htmlStringBuilder.append("<ul id=\"selectedskills\" class=\"connected sortable list\">");
    htmlStringBuilder.append("<li class=\"disabled\">Place your skills below.</li>");
    htmlStringBuilder.append("</ul></div>\r\n\t\t\t");
    htmlStringBuilder.append("<form action=\"OrganizationWapServlet\" method=\"post\">\r\n\t\t\t\t");
    htmlStringBuilder.append("<input id=\"submit-button\" type=\"submit\" value=\"Submit\" onclick=\"redirect();\">\r\n\t\t\t");
    // Skills
    htmlStringBuilder.append("<input id=\"skills\" type=\"hidden\" name=\"skills\"></input>");
    htmlStringBuilder.append("</form>\r\n\t\t</div>\r\n\t</div>");
    htmlStringBuilder.append(HtmlProvider.getInstance().getHtmlTail());
    out.println(htmlStringBuilder.toString());
    out.close();
  }
}
