import database.*;

import provider.*;

import java.io.*;
import java.util.HashMap;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 * SignInValidationServlet.
 * 
 * @author Adrian Baran
 */
public class SignInValidationServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("text/html");
    HttpSession session = request.getSession();
    PrintWriter out = response.getWriter();
    StringBuilder htmlStringBuilder = new StringBuilder();

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
      htmlStringBuilder.append(HtmlProvider.getInstance().getHtmlHead("login.css"));
      htmlStringBuilder.append("<div id=\"body-login\">");
      htmlStringBuilder.append("<p class=\"body-login-header\">Please Sign In</p>");
      htmlStringBuilder.append("<br><br>");
      htmlStringBuilder.append("<p id=\"body-login-error\">Oops! Invalid email/password...</p>");
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
    } else if (request.getParameter("loginType").equals("candidate") &&
        !DatabaseController.getInstance().candidateCompletedSkills(inputEmail)) {
      htmlStringBuilder.append(HtmlProvider.getInstance().getHtmlCandidateSkillSortHead("signup-skills.css"));

      session.setAttribute("currentNewCandidate", DatabaseController.getInstance().getCandidateId(inputEmail));

      htmlStringBuilder.append("<script>");
      htmlStringBuilder.append("function redirect() {");
      htmlStringBuilder.append("var list = document.getElementById(\"selectedskills\").getElementsByTagName(\"li\");");
      htmlStringBuilder.append("var skillsString = \"\";");
      htmlStringBuilder.append("var i = 0;");
      htmlStringBuilder.append(" for (i = 0; i < list.length; i++) {");
      htmlStringBuilder.append("skillsString += list[i].id + \",\";");
      htmlStringBuilder.append("} document.getElementById(\"skills\").value = skillsString.substring(1, skillsString.length - 1);");
      htmlStringBuilder.append("}</script>");

      htmlStringBuilder.append("<div id=\"body-skills\">\r\n\t\t\t<p class=\"body-skills-header\">New Candidate Registration<br><br><br></p>\r\n\t\t\t");
      htmlStringBuilder.append("<p class=\"body-skills-text\">Now it's time for you to rank your skills based on your selected industry.</p>\r\n\t\t\t<br>");
      htmlStringBuilder.append("\r\n\t\t\t<p class=\"body-skills-text\">Use the left pane to drag and drop your desired skills to the right pane in your order of strength.</p>");
      htmlStringBuilder.append("\r\n\t\t\t<br><br><br><br>\r\n\t\t\t");
      htmlStringBuilder.append("<div id=\"available-skills\">\r\n\t\t\t\t");
      htmlStringBuilder.append("<p class=\"box-header-text\">Available Skills</p>\r\n\t\t\t");
      htmlStringBuilder.append("<ul id=\"availableskills\" class=\"connected sortable list\">");

      HashMap<String, String> skills = DatabaseController.getInstance().getIndustrySkills(
          DatabaseController.getInstance().getCandidateIndustryId(inputEmail), "ID");
      
      for (String skillsKey : skills.keySet()) {
        htmlStringBuilder.append("<li id=\"" + skillsKey + "\">" + skills.get(skillsKey) + "</li>");
      }

      htmlStringBuilder.append("</ul></div>\r\n\t\t\t");
      htmlStringBuilder.append("<div id=\"selected-skills\">\r\n\t\t\t\t");
      htmlStringBuilder.append("<p class=\"box-header-text\">Selected Skills</p>\r\n\t\t\t");
      htmlStringBuilder.append("<ul id=\"selectedskills\" class=\"connected sortable list\">");
      htmlStringBuilder.append("<li class=\"disabled\">Place your skills below.</li>");
      htmlStringBuilder.append("</ul></div>\r\n\t\t\t");
      
      //Needed the current user for the WapServlet to know who to fill out the DB for
      session.setAttribute("currentUser", inputEmail);
      
      htmlStringBuilder.append("<form action=\"CandidateWapServlet\" method=\"post\">\r\n\t\t\t\t");
      // Skills
      htmlStringBuilder.append("<input id=\"skills\" type=\"hidden\" name=\"skills\"></input>");
      htmlStringBuilder.append("<input id=\"submit-button\" type=\"submit\" value=\"Submit\" onclick=\"redirect();\">\r\n\t\t\t</form>\r\n\t\t</div>\r\n\t</div>");
    } else {
      if (request.getParameter("loginType").equals("candidate")) {
        session.setAttribute("loggedIn", "true");
        session.setAttribute("candidateLoggedIn", "true");
        session.setAttribute("organizationLoggedIn", "false");
        session.setAttribute("currentUser", inputEmail);

        CandidateHomeServlet cHSObject = new CandidateHomeServlet();
        cHSObject.doPost(request, response);

        // response.sendRedirect(UrlProvider.getInstance().getBaseUrl(request)
        //     + "/RezuMe/CandidateHomeServlet");
      } else {
        session.setAttribute("loggedIn", "true");
        session.setAttribute("organizationLoggedIn", "true");
        session.setAttribute("candidateLoggedIn", "false");
        session.setAttribute("currentUser", inputEmail);

        OrganizationHomeServlet oHSObject = new OrganizationHomeServlet();
        oHSObject.doPost(request, response);

        // response.sendRedirect(UrlProvider.getInstance().getBaseUrl(request)
        //     + "/RezuMe/OrganizationHomeServlet");
      }
    }

    htmlStringBuilder.append(HtmlProvider.getInstance().getHtmlTail());
    out.println(htmlStringBuilder.toString());
    out.close();
  }
}
