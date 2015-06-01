import database.*;
import provider.*;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;

public class AddReferencesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();

		StringBuilder htmlStringBuilder = new StringBuilder(HtmlProvider.getInstance().getLoggedInHead("add-references.css"));
		htmlStringBuilder.append("<div id=\"body-text\">");
		htmlStringBuilder.append(HtmlProvider.getInstance().getCandidateNavBarHead());
		htmlStringBuilder.append("<p class=\"body-text-text\">"
				+ "\r\n\tAdd a reference to give your profile added credibility:\r\n</p>"
				+ "\r\n<form class=\"form\" action=\"AddReferencesConfirmationServlet\" method=\"post\">"
					+ "\r\n\t<label class=\"label-text\" for=\"firstname\">First name *</label>"
					+ "\r\n    <input id=\"firstname\" class=\"textbox\" type=\"text\" name=\"firstname\" size=\"30\" required><br><br>"
					+ "\r\n\t\r\n\t<label class=\"label-text\" for=\"lastname\">Last name *</label>"
					+ "\r\n    <input id=\"lastname\" class=\"textbox\" type=\"text\" name=\"lastname\" size=\"30\" required><br><br>"
					+ "\r\n\t\r\n\t<label class=\"label-text\" for=\"email\">Email *</label>"
					+ "\r\n    <input id=\"email\" class=\"textbox\" type=\"text\" name=\"email\" size=\"30\" required><br><br>"
					+ "\r\n\t\r\n\t<label class=\"label-text\" for=\"email\">Notes  </label><br>"
					+ "\r\n    <textarea name=\"notes\" id=\"notes\" rows=\"15\" cols=\"50\"></textarea><br>"
					+ "\r\n\r\n\t<input class=\"submit-button\" type=\"submit\" value=\"Submit\">"
				+ "\r\n</form>");
		htmlStringBuilder.append("</div>");
		
		htmlStringBuilder.append("</div>");
		htmlStringBuilder.append(HtmlProvider.getInstance().getHtmlTail());
		out.println(htmlStringBuilder.toString());
		out.close();
	}
}
