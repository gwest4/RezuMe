import database.*;

import provider.*;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

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
    
    StringBuilder htmlStringBuilder = new StringBuilder(HtmlProvider.getInstance().getHtmlHead("organization-home.css"));

    htmlStringBuilder.append("<div id=\"body-text\">");
    htmlStringBuilder.append("<div id=\"nav-header\">");
    htmlStringBuilder.append("<form class=\"nav-form\" action=\"OrganizationHomeServlet\" method=\"post\"><input class=\"home-button\" type=\"submit\" value=\"Home\"></form>");
    htmlStringBuilder.append("<form class=\"nav-form\" action=\"OrganizationNewJobServlet\" method=\"post\"><input class=\"home-button\" type=\"submit\" value=\"New Job Listing\"></form>");
    htmlStringBuilder.append("<form class=\"nav-form\" action=\"SignOutServlet\" method=\"post\"><input class=\"home-button\" type=\"submit\" value=\"Sign Out\"></form></div>");
    htmlStringBuilder.append("<p class=\"body-text-text\">Signed in as <strong>" + DatabaseController.getInstance().getOrganizationName((String) session.getAttribute("currentUser")) + "</strong></p>");
    htmlStringBuilder.append("</div></div>");

    htmlStringBuilder.append(HtmlProvider.getInstance().getHtmlTail());
    out.println(htmlStringBuilder.toString());
    out.close();
  }
}
