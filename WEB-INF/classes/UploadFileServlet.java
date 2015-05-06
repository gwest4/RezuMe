import database.DatabaseController;

import provider.*;

import java.io.*;
import java.util.ArrayList;

import javax.servlet.*;
import javax.servley.http.*;

/**
 * UploadFileServlet.java
 *
 * @author Matthew Wylder
 */

public class UploadFileServlet.java extends HttpServley {

  private static final long serialVersionUID = 1L;
  //TODO: Create CSS file for this servlet
  private static final String CSS_FILE_NAME = "";


   protected void doPost(HttpServletRequest request, HttpServletResponse response)
 	throws ServletException, IOException {
      response.setContentType("text/html");
      HttpSession session = reqest.getSession();
      PrintWriter out = response.getWriter();
      StringBuilder htmlStringBuilder = new Stringbuilder(HtmlProvider.getInstance().getHtmlhead( CSS_FILE_NAME));
      if(session.getAttribute("loggedIn") != null && (boolean) session.getAttribute("loggedIn")) {
         
      }

  }
} 
