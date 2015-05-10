import database.DatabaseController;

import provider.*;

import java.io.*;
import java.util.ArrayList;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 * UploadFileServlet.java
 *
 * @author Matthew Wylder
 */

public class UploadFileServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;
  //TODO: Create CSS file for this servlet
  private static final String CSS_FILE_NAME = "";


   protected void doPost(HttpServletRequest request, HttpServletResponse response)
 	throws ServletException, IOException {
      response.setContentType("text/html");
      HttpSession session = request.getSession();
      PrintWriter out = response.getWriter();
      StringBuilder htmlStringBuilder = new StringBuilder(HtmlProvider.getInstance().getHtmlHead( CSS_FILE_NAME));
      if(session.getAttribute("loggedIn") != null && (boolean) session.getAttribute("loggedIn")) {
	     String currentUserId = (String) session.getAttribute("currentUserId");
        /*
         * TODO: Check if the user has a resume uploaded already
         *       if they do, validate whether or not they want to replace it
         *       else go straight to allowing upload
         */


      } else {
         htmlStringBuilder.append("hello world");

         htmlStringBuilder.append(HtmlProvider.getInstance().getHtmlTail());
         out.println(htmlStringBuilder.toString());
         out.close();
      }
    }
} 
