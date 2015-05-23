import database.*;
import provider.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 * CandidateSignup2Servlet.
 * 
 * @author Adrian Baran
 */
public class CandidateSignup2Servlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("text/html");
    HttpSession session = request.getSession();
    PrintWriter out = response.getWriter();
    
    boolean isValid = true;
    String errorMessage = "";
    
    String inputPassword1 = request.getParameter("password1");
    String inputPassword2 = request.getParameter("password2");
    
    if (!inputPassword1.equals(inputPassword2)) {
      isValid = false;
      errorMessage = "Your two passwords do not match. Please try again.";
    }
    
    String inputEmail = request.getParameter("email");
    
    if (DatabaseController.getInstance().emailRegistered(inputEmail, "CANDIDATE")) {
      isValid = false;
      errorMessage = "The email that you have provided appears to have already been registered.<br><br>"
          + "If you think this is a mistake, please submit a ticket to us.";
    }

    StringBuilder htmlStringBuilder = new StringBuilder();

    if (!isValid) {
      htmlStringBuilder.append(HtmlProvider.getInstance().getHtmlHead("new-signup.css"));

      htmlStringBuilder.append("<div id=\"body-signup\">");
      htmlStringBuilder.append("<p class=\"body-signup-header\">New Candidate Registration<br><br></em></p>");
      htmlStringBuilder.append("<p class=\"body-signup-text\">* denotes a mandatory field.</p><br><br>");
      htmlStringBuilder.append("<p class=\"body-signup-error\">" + errorMessage + "</p><br><br>");
      htmlStringBuilder.append("<form action=\"CandidateSignup2Servlet\" method=\"post\">");
      htmlStringBuilder.append("<div id=\"login-credentials\">");
      // First name
      htmlStringBuilder.append("<label class=\"label-text\" for=\"firstname\">First name *</label>");
      htmlStringBuilder.append("<input id=\"firstname\" class=\"textbox\" type=\"text\" name=\"firstname\" size=\"30\" value=\"" + request.getParameter("firstname") + "\" required><br><br>");
      // Last name
      htmlStringBuilder.append("<label class=\"label-text\" for=\"lastname\">Last name *</label>");
      htmlStringBuilder.append("<input id=\"lastname\" class=\"textbox\" type=\"text\" name=\"lastname\" size=\"30\" value=\"" + request.getParameter("lastname") + "\" required><br><br>");
      // Email
      htmlStringBuilder.append("<label class=\"label-text\" for=\"email\">Email *</label>");
      htmlStringBuilder.append("<input id=\"email\" class=\"textbox\" type=\"email\" name=\"email\" size=\"30\" value=\"" + request.getParameter("email") + "\" required><br><br>");
      // Password
      htmlStringBuilder.append("<label class=\"label-text\" for=\"password1\">Password *</label>");
      htmlStringBuilder.append("<input id=\"password1\" class=\"textbox\" type=\"password\" name=\"password1\" size=\"30\" value=\"" + request.getParameter("password1") + "\" required><br><br>");
      // Password verification
      htmlStringBuilder.append("<label class=\"label-text\" for=\"password2\">Verify Password *</label>");
      htmlStringBuilder.append("<input id=\"password2\" class=\"textbox\" type=\"password\" name=\"password2\" size=\"30\" value=\"" + request.getParameter("password2") + "\" required><br><br>");
      // Phone
      htmlStringBuilder.append("<label class=\"label-text\" for=\"phone\">Phone *</label>");
      htmlStringBuilder.append("<input id=\"phone\" class=\"textbox\" type=\"text\" name=\"phone\" size=\"30\" required><br><br>");
      // Street address
      htmlStringBuilder.append("<label class=\"label-text\" for=\"streetaddress\">Street Address *</label>");
      htmlStringBuilder.append("<input id=\"streetaddress\" class=\"textbox\" type=\"text\" name=\"streetaddress\" size=\"30\" value=\"" + request.getParameter("streetaddress") + "\" required><br><br>");
      // City
      htmlStringBuilder.append("<label class=\"label-text\" for=\"city\">City *</label>");
      htmlStringBuilder.append("<input id=\"city\" class=\"textbox\" type=\"text\" name=\"city\" size=\"30\" value=\"" + request.getParameter("city") + "\" required><br><br>");
      // State
      htmlStringBuilder.append("<label class=\"label-text\" for=\"state\">State *</label>");
      htmlStringBuilder.append("<select id=\"state\" name=\"state\" required>");
      htmlStringBuilder.append("<option value=\"AL\">Alabama</option>\r\n<option value=\"AK\">Alaska</option>\r\n"
          + "<option value=\"AZ\">Arizona</option>\r\n<option value=\"AR\">Arkansas</option>\r\n<option value=\"CA\">California</option>\r\n"
          + "<option value=\"CO\">Colorado</option>\r\n<option value=\"CT\">Connecticut</option>\r\n<option value=\"DE\">Delaware</option>\r\n"
          + "<option value=\"FL\">Florida</option>\r\n<option value=\"GA\">Georgia</option>\r\n<option value=\"HI\">Hawaii</option>\r\n"
          + "<option value=\"ID\">Idaho</option>\r\n<option value=\"IL\">Illinois</option>\r\n<option value=\"IN\">Indiana</option>\r\n"
          + "<option value=\"IA\">Iowa</option>\r\n<option value=\"KS\">Kansas</option>\r\n<option value=\"KY\">Kentucky</option>\r\n"
          + "<option value=\"LA\">Louisiana</option>\r\n<option value=\"ME\">Maine</option>\r\n<option value=\"MD\">Maryland</option>\r\n"
          + "<option value=\"MA\">Massachusetts</option>\r\n<option value=\"MI\">Michigan</option>\r\n<option value=\"MN\">Minnesota</option>\r\n"
          + "<option value=\"MS\">Mississippi</option>\r\n<option value=\"MO\">Missouri</option>\r\n<option value=\"MT\">Montana</option>\r\n"
          + "<option value=\"NE\">Nebraska</option>\r\n<option value=\"NV\">Nevada</option>\r\n<option value=\"NH\">New Hampshire</option>\r\n"
          + "<option value=\"NJ\">New Jersey</option>\r\n<option value=\"NM\">New Mexico</option>\r\n<option value=\"NY\">New York</option>\r\n"
          + "<option value=\"NC\">North Carolina</option>\r\n<option value=\"ND\">North Dakota</option>\r\n<option value=\"OH\">Ohio</option>\r\n"
          + "<option value=\"OK\">Oklahoma</option>\r\n<option value=\"OR\">Oregon</option>\r\n<option value=\"PA\">Pennsylvania</option>\r\n"
          + "<option value=\"RI\">Rhode Island</option>\r\n<option value=\"SC\">South Carolina</option>\r\n<option value=\"SD\">South Dakota</option>\r\n"
          + "<option value=\"TN\">Tennessee</option>\r\n<option value=\"TX\">Texas</option>\r\n<option value=\"UT\">Utah</option>\r\n"
          + "<option value=\"VT\">Vermont</option>\r\n<option value=\"VA\">Virginia</option>\r\n<option value=\"WA\">Washington</option>\r\n"
          + "<option value=\"WV\">West Virginia</option>\r\n<option value=\"WI\">Wisconsin</option>\r\n<option value=\"WY\">Wyoming</option>");
      htmlStringBuilder.append("</select><br><br>");
      // Zip code
      htmlStringBuilder.append("<label class=\"label-text\" for=\"zip\">Zip Code *</label>");
      htmlStringBuilder.append("<input id=\"zip\" class=\"textbox\" type=\"text\" name=\"zip\" size=\"30\" value=\"" + request.getParameter("zip") + "\" required><br><br>");
      // School
      htmlStringBuilder.append("<label class=\"label-text\" for=\"school\">School *</label>");
      htmlStringBuilder.append("<input id=\"school\" class=\"textbox\" type=\"text\" name=\"school\" size=\"30\" required><br><br>");
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
      
      htmlStringBuilder.append("</select><br><br><br>");
      htmlStringBuilder.append("<input id=\"submit-button\" type=\"submit\" value=\"Submit\">");
      htmlStringBuilder.append("</div></form></div></div>");
    } else {
      String firstName = request.getParameter("firstname");
      String lastName = request.getParameter("lastname");
      String email = request.getParameter("email");
      String password = PasswordHasher.getInstance().generateHash(request.getParameter("password"));
      String phone = request.getParameter("phone");
      String address = request.getParameter("streetaddress");
      String city = request.getParameter("city");
      String state = request.getParameter("state");
      String zip = request.getParameter("zip");
      String school = request.getParameter("school");
      String industry = request.getParameter("industry");

      DatabaseController.getInstance().addNewCandidate(firstName, lastName, email, password, phone, address,
          city, state, zip, school, industry);

      session.setAttribute("loggedIn", "true");
      session.setAttribute("candidateLoggedIn", "true");
      session.setAttribute("currentUserId", email);

      htmlStringBuilder.append(HtmlProvider.getInstance().getHtmlCandidateSkillSortHead("signup-skills.css"));

      session.setAttribute("currentNewCandidate", DatabaseController.getInstance().getCandidateId(email));

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
      htmlStringBuilder.append("<form action=\"CandidateWapServlet\" method=\"post\">\r\n\t\t\t\t");
      // Skills
      htmlStringBuilder.append("<input id=\"skills\" type=\"hidden\" name=\"skills\"></input>");
      htmlStringBuilder.append("<input id=\"submit-button\" type=\"submit\" value=\"Submit\" onclick=\"redirect();\">\r\n\t\t\t</form>\r\n\t\t</div>\r\n\t</div>");
    }
    
    htmlStringBuilder.append(HtmlProvider.getInstance().getHtmlTail());
    out.println(htmlStringBuilder.toString());
    out.close();
  }
}
