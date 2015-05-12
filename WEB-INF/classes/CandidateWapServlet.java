import database.*;

import provider.*;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 * CandidateWapServlet.
 * 
 * @author Adrian Baran
 */
public class CandidateWapServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("text/html");
    HttpSession session = request.getSession();
    PrintWriter out = response.getWriter();

    System.out.println(request.getParameterValues("selectedskillsinput"));
    
    StringBuilder htmlStringBuilder = new StringBuilder(HtmlProvider.getInstance().getHtmlHead("CSS HERE"));

    
    htmlStringBuilder.append(HtmlProvider.getInstance().getHtmlTail());
    out.println(htmlStringBuilder.toString());
    out.close();
  }
}
