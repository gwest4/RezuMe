import database.*;
import provider.*;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;

public class EditProfileConfirmationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();

		StringBuilder htmlStringBuilder = new StringBuilder(HtmlProvider.getInstance().getLoggedInHead("edit-profile.css"));
		htmlStringBuilder.append("<div id=\"body-text\">");
		htmlStringBuilder.append(HtmlProvider.getInstance().getNavHead());
		htmlStringBuilder.append("<p class=\"body-text-header\">"
				+ "\r\n\tYour profile has been updated.\r\n</p>"
				+ "\r\n<p class=\"body-text-text\">"
				+ "\r\n\tClick the button below to return to your home page.\r\n</p>"
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
