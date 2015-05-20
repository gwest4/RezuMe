import database.*;
import provider.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 * SignOutServlet.
 * 
 * @author Adrian Baran
 */
public class SignOutServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("text/html");
    HttpSession session = request.getSession();
    PrintWriter out = response.getWriter();

    StringBuilder htmlStringBuilder = new StringBuilder(HtmlProvider.getInstance().getHtmlHead("signout.css"));

    session.setAttribute("loggedIn", "false");
    session.setAttribute("organizationLoggedIn", "false");
    session.setAttribute("candidateLoggedIn", "false");
    session.setAttribute("currentUser", null);

    htmlStringBuilder.append("<div id=\"body-signup\">");
    htmlStringBuilder.append("<p class=\"body-signup-header\">You have signed out.<br><br><br></em></p>");
    htmlStringBuilder.append("<p class=\"body-signup-text\">Thank you for using RezuMe.<br><br>"
        + "Hope to see you again soon!</p><br><br><br>"
        + "<p id=\"return-text\"><a id=\"return-link\" href=\"index.html\">Click here to return to the home page.</a></p></div></div>");
    htmlStringBuilder.append(HtmlProvider.getInstance().getHtmlTail());
    out.println(htmlStringBuilder.toString());
    out.close();
  }
}
