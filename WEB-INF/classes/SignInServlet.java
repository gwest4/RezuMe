import provider.*;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * SignInServlet.
 * 
 * @author Adrian Baran
 */
public class SignInServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("text/html");
    HttpSession session = request.getSession();
    PrintWriter out = response.getWriter();
    StringBuilder htmlStringBuilder = new StringBuilder(HtmlProvider.getInstance().getHtmlHead("login.css"));

    if (session.getAttribute("loggedIn") != null && Boolean.parseBoolean((String) session.getAttribute("loggedIn"))) {
      String currentUser = (String) session.getAttribute("currentUser");

      if (Boolean.parseBoolean((String) session.getAttribute("candidateLoggedIn"))) {
        CandidateHomeServlet cHSObject = new CandidateHomeServlet();
        cHSObject.doPost(request, response);
      } else if (Boolean.parseBoolean((String) session.getAttribute("organizationLoggedIn"))) {
        OrganizationHomeServlet oHSObject = new OrganizationHomeServlet();
        oHSObject.doPost(request, response);
      }
    } else {
      htmlStringBuilder.append("<div id=\"body-login\">");
      htmlStringBuilder.append("<p class=\"body-login-header\">Please Sign In</p>");
      htmlStringBuilder.append("<br><br>");
      htmlStringBuilder.append("<form action=\"SignInValidationServlet\" method=\"post\">");
      htmlStringBuilder.append("<input id=\"candidate\" type=\"radio\" name=\"loginType\" "
          + "value=\"candidate\" checked>");
      htmlStringBuilder.append("<label class=\"label-text\" for=\"candidate\">"
          + "Sign in as Candidate</label><br>");
      htmlStringBuilder.append("<input id=\"organization\" type=\"radio\" name=\"loginType\" "
          + "value=\"organization\">");
      htmlStringBuilder.append("<label class=\"label-text\" for=\"organization\">"
          + "Sign in as Organization</label>");
      htmlStringBuilder.append("<br><br><br>");
      htmlStringBuilder.append("<div id=\"login-credentials\">");
      htmlStringBuilder.append("<input id=\"email\" class=\"textbox\" type=\"text\" "
          + "name=\"email\" placeholder=\"Email\" size=\"30\" required><br><br>");
      htmlStringBuilder.append("<input id=\"password\" class=\"textbox\" type=\"password\" "
          + "name=\"password\" placeholder=\"Password\" size=\"30\" required>");
      htmlStringBuilder.append("</div><br><br>");
      htmlStringBuilder.append("<input id=\"signin-button\" type=\"submit\" value=\"Sign In\">");
      htmlStringBuilder.append("</form><br><br>");
      htmlStringBuilder
          .append("<p id=\"body-login-help\"><a id=\"help-link\" href=\"loginhelp.html\">"
              + "Having trouble signing in? Click here.</a></p><br>");
      htmlStringBuilder.append("<p id=\"signup-link-text2\"><a id=\"signup-link2\" "
          + "href=\"signup.html\">Not registered? Sign up now.</a></p>");
      htmlStringBuilder.append("</div></div>");
    }

    htmlStringBuilder.append(HtmlProvider.getInstance().getHtmlTail());
    out.println(htmlStringBuilder.toString());
    out.close();
  }
}
