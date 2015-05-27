import database.*;
import provider.*;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class EditProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();

		StringBuilder htmlStringBuilder = new StringBuilder(HtmlProvider.getInstance().getLoggedInHead("edit-profile.css"));
		htmlStringBuilder.append("<div id=\"body-text\">");
		htmlStringBuilder.append(HtmlProvider.getInstance().getNavHead());
		htmlStringBuilder.append("<p class=\"body-text-text\">Editing profile for <strong>" + 
				DatabaseController.getInstance().getCandidateName((String) session.getAttribute("currentUser")) +
				"</strong></p>");
		htmlStringBuilder.append(
				"\r\n<form class=\"form\" action=\"EditProfileConfirmationServlet\" method=\"post\">"
					+ "\r\n\t<label class=\"label-text\" for=\"email\">Email </label>"
					+ "\r\n    <input id=\"email\" class=\"textbox\" type=\"text\" name=\"email\" size=\"30\"><br><br>"
					+ "\r\n\t\r\n\t<label class=\"label-text\" for=\"address\">Address </label>"
					+ "\r\n    <input id=\"address\" class=\"textbox\" type=\"text\" name=\"address\" size=\"30\"><br><br>"
					+ "\r\n\t\r\n\t<label class=\"label-text\" for=\"city\">City </label>"
					+ "\r\n    <input id=\"city\" class=\"textbox\" type=\"text\" name=\"city\" size=\"30\"><br><br>"
					+ "<label class=\"label-text\" for=\"state\">State </label>"
				    + "<select id=\"state\" name=\"state\">"
				    	+ "<option value=\"AL\">Alabama</option>\r\n<option value=\"AK\">Alaska</option>\r\n"
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
				        + "<option value=\"WV\">West Virginia</option>\r\n<option value=\"WI\">Wisconsin</option>\r\n<option value=\"WY\">Wyoming</option>"
			        + "</select><br><br>"
			        + "\r\n\t\r\n\t<label class=\"label-text\" for=\"zip\">Zip </label>"
					+ "\r\n    <input id=\"zip\" class=\"textbox\" type=\"text\" name=\"zip\" size=\"30\"><br><br>"
					+ "\r\n\t\r\n\t<label class=\"label-text\" for=\"school\">School </label>"
					+ "\r\n    <input id=\"school\" class=\"textbox\" type=\"text\" name=\"school\" size=\"30\"><br><br>");
		htmlStringBuilder.append("</select>");
	    // Industry
	    htmlStringBuilder.append("<label class=\"label-text\" for=\"industry\">Industry </label>");
	    htmlStringBuilder.append("<select id=\"industry\" name=\"industry\">");
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
	    htmlStringBuilder.append("<input id=\"form-button\" type=\"submit\" value=\"Submit\">");
		htmlStringBuilder.append("</div>");
		htmlStringBuilder.append("</div>");
		
		htmlStringBuilder.append(HtmlProvider.getInstance().getHtmlTail());
		out.println(htmlStringBuilder.toString());
		out.close();
	}
}