import database.*;
import provider.*;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;

public class ContactServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();

		StringBuilder htmlStringBuilder = new StringBuilder(HtmlProvider.getInstance().getLoggedInHead("contact.css"));
		htmlStringBuilder.append("<div id=\"body-text\">");
		htmlStringBuilder.append(HtmlProvider.getInstance().getCandidateNavBarHead());
		htmlStringBuilder.append("<p class=\"body-text-text\">"
				+ "\r\n\tTime to set up the connection.\r\n</p>"
				+ "\r\n<form class=\"form\" action=\"ContactConfirmationServlet\" method=\"post\">"
					+ "<label class=\"label-text\" for=\"email\">Add your message below.</label><br><br>"
					+ "\r\n    <textarea name=\"notes\" id=\"notes\" rows=\"15\" cols=\"50\"></textarea><br>"
					+ "\r\n\r\n\t<input id=\"submit-button\" type=\"submit\" value=\"Submit\">"
				+ "\r\n</form>");
		htmlStringBuilder.append("</div>");
		htmlStringBuilder.append("</div>");
		
		htmlStringBuilder.append(HtmlProvider.getInstance().getHtmlTail());
		out.println(htmlStringBuilder.toString());
		out.close();
	}
}
