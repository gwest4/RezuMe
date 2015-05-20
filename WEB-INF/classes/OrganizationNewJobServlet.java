import database.DatabaseController;

import provider.*;

import java.io.*;
import java.util.ArrayList;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 * OrganizationNewJobServlet.
 * 
 * @author Adrian Baran
 */
public class OrganizationNewJobServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("text/html");
    HttpSession session = request.getSession();
    PrintWriter out = response.getWriter();
    StringBuilder htmlStringBuilder = new StringBuilder(HtmlProvider.getInstance().getHtmlHead("new-job.css"));

    htmlStringBuilder.append("<script>");
    htmlStringBuilder.append("function getTextArea() {");
    htmlStringBuilder.append("alert(document.getElementById(\"description\").value);");
    htmlStringBuilder.append("}");
    htmlStringBuilder.append("</script>");
    
    htmlStringBuilder.append("<div id=\"body-signup\">");
    htmlStringBuilder.append("<p class=\"body-signup-header\">New Job Listing<br><br></em></p>");
    htmlStringBuilder.append("<p class=\"body-signup-text\">* denotes a mandatory field.</p><br><br>");
    htmlStringBuilder.append("<form method=\"get\">");
    htmlStringBuilder.append("<div id=\"login-credentials\">");
    // Industry
    htmlStringBuilder.append("<label class=\"label-text\" for=\"industry\">Industry *</label>");
    htmlStringBuilder.append("<select id=\"industry\" name=\"industry\" required>");
    ArrayList<String> industries = DatabaseController.getInstance().getIndustries();
    if (!(industries == null)) {
	    for (String industry : industries) {
	      htmlStringBuilder.append("<option value=\"" + industry + "\">" + industry + "</option>\r\n");
	    }
    } else {
    	htmlStringBuilder.append("<option value=\"error\">No Industry Found</option>\r\n");
    	log("WARNING: getIndustries() yielded null");
    }
    // Title
    htmlStringBuilder.append("</select><br><br><label class=\"label-text\" for=\"title\">Title *</label>");
    htmlStringBuilder.append("<input id=\"title\" class=\"textbox\" type=\"text\" name=\"title\" size=\"30\" required><br><br><br>");
    // Description
    htmlStringBuilder.append("<textarea id=\"description\" rows=\"15\" cols=\"50\" required>Insert the job description here.</textarea>");
    htmlStringBuilder.append("<br><br><br>");
    htmlStringBuilder.append("<input id=\"submit-button\" type=\"submit\" value=\"Submit\" onclick=\"getTextArea();\">");
    htmlStringBuilder.append("</div></form></div></div>");

    htmlStringBuilder.append(HtmlProvider.getInstance().getHtmlTail());
    out.println(htmlStringBuilder.toString());
    out.close();
  }
}
