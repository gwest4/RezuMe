import database.*;
import provider.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 * OrganizationConfirmationServlet.
 * 
 * @author Adrian Baran
 */
public class OrganizationConfirmationServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("text/html");
    HttpSession session = request.getSession();
    PrintWriter out = response.getWriter();

    String wap = request.getParameter("wap");

    DatabaseController.getInstance().updateJobListingWap((String) session.getAttribute("currentNewJob"), wap);

    StringBuilder htmlStringBuilder = new StringBuilder();

    htmlStringBuilder.append(HtmlProvider.getInstance().getHtmlHead("signup-select.css"));

    htmlStringBuilder.append("<div id=\"body-signup\">");
    htmlStringBuilder.append("<p class=\"body-signup-header\">Your job listing has been posted.<br><br><br></em></p>");
    htmlStringBuilder.append("<p class=\"body-signup-text\">Be sure to frequently check your listings to see if there are any valuable<br>"
        + "Candidates would be interested in your available position(s).<br><br><br>"
        + "We hope you are enjoying our seamless process in finding<br>the best Candidates for your Organization.<br><br>");
    htmlStringBuilder.append("<form action=\"OrganizationHomeServlet\" method=\"post\">\r\n\t\t\t\t");
    htmlStringBuilder.append("<input id=\"submit-button\" type=\"submit\" value=\"Home\">\r\n\t\t\t");
    htmlStringBuilder.append("</form>\r\n\t\t</div>\r\n\t</div>");

    htmlStringBuilder.append(HtmlProvider.getInstance().getHtmlTail());
    out.println(htmlStringBuilder.toString());
    out.close();
  }
}
