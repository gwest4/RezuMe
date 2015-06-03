import database.*;
import provider.*; 

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.HashMap;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * CandidateSignup2Servlet.
 * 
 * @author Adrian Baran, Matt Wylder
 */
public class CandidateSignup2Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String UPLOAD_DIRECTORY = "resume-data";
	private static final int THRESHOLD_SIZE     = 1024 * 1024 * 3;  // 3MB
	private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
	private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();

    // checks if the request actually contains upload file
    if (!ServletFileUpload.isMultipartContent(request)) {
    	CandidateSignupResumeServlet servletObject = new CandidateSignupResumeServlet();
    	servletObject.doPost(request, response);
   	}
    
    // configures upload settings
    DiskFileItemFactory factory = new DiskFileItemFactory();
    factory.setSizeThreshold(THRESHOLD_SIZE);
    factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
    	 
    ServletFileUpload upload = new ServletFileUpload(factory);
    upload.setFileSizeMax(MAX_FILE_SIZE);
    upload.setSizeMax(MAX_REQUEST_SIZE);
    	
    // constructs the directory path to store upload file
    String uploadPath = getServletContext().getRealPath("")
    		+ File.separator + UPLOAD_DIRECTORY;
    // creates the directory if it does not exist
    File uploadDir = new File(uploadPath);
    if (!uploadDir.exists()) {
    	uploadDir.mkdir();
    }

    try {
      // parses the request's content to extract file data
      List formItems = upload.parseRequest(request);
      Iterator iter = formItems.iterator();
               
      // iterates over form's fields
      while (iter.hasNext()) {
	      FileItem item = (FileItem) iter.next();
	      // processes only fields that are not form fields
	      if (!item.isFormField()) {
	        String fileName = new File(item.getName()).getName();
	        String filePath = uploadPath + File.separator + fileName;
	        File storeFile = new File(filePath);
	                       
	        // saves the file on disk
	        item.write(storeFile);
	      }
    	}
   		System.out.println("Upload successful!");
	  	log("Upload successful!");
    } catch (Exception ex) {
      System.out.println("Upload not successful");
	    log("Upload not successful");
    }

    DatabaseController.getInstance().updateResume((String) session.getAttribute("currentUser"), uploadPath);

		StringBuilder htmlStringBuilder = new StringBuilder();

		htmlStringBuilder.append(HtmlProvider.getInstance().getHtmlCandidateSkillSortHead("signup-skills.css"));

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

		HashMap<String, String> skills = DatabaseController.getInstance().getIndustrySkills((String) session.getAttribute("newIndustry"), "ID");

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

		htmlStringBuilder.append(HtmlProvider.getInstance().getHtmlTail());
		out.println(htmlStringBuilder.toString());
		out.close();
	}
}
