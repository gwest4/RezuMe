import html.HtmlProvider;

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
    StringBuilder htmlStringBuilder = new StringBuilder(HtmlProvider.getInstance().getHtmlHead());

    String username = request.getParameter("username");
    String password = request.getParameter("password");

    if (username.equals("cheese")) {
      htmlStringBuilder.append("<div id=\"body-login\">");
      htmlStringBuilder.append("<p class=\"body-login-header\">Please sign in.</p>");
      htmlStringBuilder.append("<br>");
      htmlStringBuilder.append("<p id=\"body-login-error\">Incorrect username/password.</p>");
      htmlStringBuilder.append("<br>");
      htmlStringBuilder.append("<form action=\"LoginValidationServlet\" method=\"post\">");
      htmlStringBuilder.append("<input id=\"candidate\" type=\"radio\" name=\"loginType\" "
          + "value=\"candidate\" checked>");
      htmlStringBuilder.append("<label class=\"label-text\" for=\"candidate\">"
          + "Sign in as Candidate.</label><br>");
      htmlStringBuilder.append("<input id=\"company\" type=\"radio\" name=\"loginType\" "
          + "value=\"company\">");
      htmlStringBuilder.append("<label class=\"label-text\" for=\"company\">"
          + "Sign in as Company.</label>");
      htmlStringBuilder.append("<br><br><br>");
      htmlStringBuilder.append("<div id=\"login-credentials\">");
      htmlStringBuilder.append("<label class=\"label-text\" for=\"username\">Email </label>");
      htmlStringBuilder.append("<input id=\"username\" class=\"textbox\" type=\"text\" "
          + "name=\"username\" size=\"30\"><br>");
      htmlStringBuilder.append("<label class=\"label-text\" for=\"password\">Password </label>");
      htmlStringBuilder.append("<input id=\"password\" class=\"textbox\" type=\"password\" "
          + "name=\"password\" size=\"30\">");
      htmlStringBuilder.append("</div><br><br>");
      htmlStringBuilder.append("<input id=\"signin-button\" type=\"submit\" value=\"Sign In\">");
      htmlStringBuilder.append("</form><br><br>");
      htmlStringBuilder.append("<p id=\"signup-link-text2\"><a id=\"signup-link2\" "
          + "href=\"signup.html\">Not registered? Sign up now.</a></p>");
      htmlStringBuilder.append("</div></div>");
    } else {
      String baseUrl =
          request.getScheme()
              + "://"
              + request.getServerName()
              + ("http".equals(request.getScheme()) && request.getServerPort() == 80
                  || "https".equals(request.getScheme()) && request.getServerPort() == 443 ? ""
                  : ":" + request.getServerPort());
      
      if (request.getParameter("loginType").equals("candidate")) {
        response.sendRedirect(baseUrl + "/capstone/CandidateHomeServlet");
      } else {
        response.sendRedirect(baseUrl + "/capstone/CompanyHomeServlet");
      }
    }

    htmlStringBuilder.append(HtmlProvider.getInstance().getHtmlTail());
    out.println(htmlStringBuilder.toString());
    out.close();
  }
}
