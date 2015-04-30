import database.*;

import provider.*;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 * LoginValidationServlet.
 * 
 * @author Adrian Baran
 */
public class LoginValidationServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("text/html");
    HttpSession session = request.getSession();
    PrintWriter out = response.getWriter();
    StringBuilder htmlStringBuilder = new StringBuilder(HtmlProvider.getInstance().getHtmlHead("login.css"));

    String inputEmail = request.getParameter("email");
    String inputPassword = request.getParameter("password");

    boolean isAuthenticated = false;

    if (request.getParameter("loginType").equals("candidate")) {
      isAuthenticated =
          PasswordHasher.getInstance().isAuthenticated(inputEmail, inputPassword, "CANDIDATE");
    } else {
      isAuthenticated =
          PasswordHasher.getInstance().isAuthenticated(inputEmail, inputPassword, "ORGANIZATION");
    }

    if (!isAuthenticated) {
      htmlStringBuilder.append("<div id=\"body-login\">");
      htmlStringBuilder.append("<p class=\"body-login-header\">Please sign in.</p>");
      htmlStringBuilder.append("<br><br>");
      htmlStringBuilder.append("<p id=\"body-login-error\">Oops! Invalid email/password...</p>");
      htmlStringBuilder.append("<br><br>");
      htmlStringBuilder.append("<form action=\"LoginValidationServlet\" method=\"post\">");
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
      htmlStringBuilder.append("<label class=\"label-text\" for=\"email\">Email </label>");
      htmlStringBuilder.append("<input id=\"email\" class=\"textbox\" type=\"text\" "
          + "name=\"email\" size=\"30\"><br><br>");
      htmlStringBuilder.append("<label class=\"label-text\" for=\"password\">Password </label>");
      htmlStringBuilder.append("<input id=\"password\" class=\"textbox\" type=\"password\" "
          + "name=\"password\" size=\"30\">");
      htmlStringBuilder.append("</div><br><br>");
      htmlStringBuilder.append("<input id=\"signin-button\" type=\"submit\" value=\"Sign In\">");
      htmlStringBuilder.append("</form><br><br>");
      htmlStringBuilder
          .append("<p id=\"body-login-help\"><a id=\"help-link\" href=\"loginhelp.html\">"
              + "Having trouble signing in? Click here.</a></p><br>");
      htmlStringBuilder.append("<p id=\"signup-link-text2\"><a id=\"signup-link2\" "
          + "href=\"signup.html\">Not registered? Sign up now.</a></p>");
      htmlStringBuilder.append("</div></div>");
    } else {
      if (request.getParameter("loginType").equals("candidate")) {
        response.sendRedirect(UrlProvider.getInstance().getBaseUrl(request)
            + "/capstone/CandidateHomeServlet");
      } else {
        response.sendRedirect(UrlProvider.getInstance().getBaseUrl(request)
            + "/capstone/OrganizationHomeServlet");
      }
    }

    htmlStringBuilder.append(HtmlProvider.getInstance().getHtmlTail());
    out.println(htmlStringBuilder.toString());
    out.close();
  }
}
