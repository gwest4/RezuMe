

import java.io.*;
import java.util.Scanner;

import javax.servlet.*;
import javax.servlet.http.*;

import database.*;

import provider.*;
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
    
    StringBuilder htmlStringBuilder = new StringBuilder(HtmlProvider.getInstance().getHtmlHead("candidate-wap.css"));
	
    htmlStringBuilder.append("<div id=\"body-WAPQuiz\">");
	htmlStringBuilder.append("<p class=\"body-WAPQuiz-header\">New Candidate WAP Quiz<br><br></em></p>");
	htmlStringBuilder.append("<form action=\"CandidateWAP2Servlet\" method=\"post\">");
	htmlStringBuilder.append("<div id=\"WAP-questions\">");
	
	//CheckBox Questions
	
	Scanner sc = new Scanner(new File("Technology.txt"));
      while (sc.hasNextLine()) {
          htmlStringBuilder.append("<p>"+sc.nextLine()+"</p>");
      }
	
	htmlStringBuilder.append("</div></form></div></div>");
	
    htmlStringBuilder.append(HtmlProvider.getInstance().getHtmlTail());
    out.println(htmlStringBuilder.toString());
    out.close();
  }
}
