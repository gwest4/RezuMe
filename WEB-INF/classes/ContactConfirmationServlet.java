import database.*;
import provider.*;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;

public class ContactConfirmationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();

		StringBuilder htmlStringBuilder = new StringBuilder(HtmlProvider.getInstance().getLoggedInHead("contact-confirm.css"));
		htmlStringBuilder.append("<div id=\"body-text\">");
		htmlStringBuilder.append(HtmlProvider.getInstance().getCandidateNavBarHead());
		htmlStringBuilder.append("<p class=\"body-text-header\">"
				+ "\r\n\t<br>You have pinged your requested contact.\r\n</p>"
				+ "\r\n<p class=\"body-text-text\">"
				+ "\r\n\tPlease allow the contact to receieve, view, and respond to your ping.<br>"
				+ "<br>Also, please note that all communication from this point forward will be handled outside of RezuMe.<br>"
				+ "\r\n\t<br>Click the button below to return to your home page.\r\n</p>"
				+ "\r\n<form class=\"form\" action=\"CandidateHomeServlet\" method=\"post\">"
				+ "\r\n\t<input id=\"submit-button\" type=\"submit\" value=\"Home\">"
				+ "\r\n</form>");
		htmlStringBuilder.append("</div>");
		htmlStringBuilder.append("</div>");
		
		htmlStringBuilder.append(HtmlProvider.getInstance().getHtmlTail());
		out.println(htmlStringBuilder.toString());
		out.close();
	}
}
