import provider.*; 
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
 
import javax.servlet.ServletException;
import javax.servlet.http.*;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
/**
 * Servlet implementation class UploadServlet
 */
public class UploadFileServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
	
	private static final String UPLOAD_DIRECTORY = "resume-data";
	private static final int THRESHOLD_SIZE     = 1024 * 1024 * 3;  // 3MB
	private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
	private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    public void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
    	
//*/
    	response.setContentType("text/html");
        HttpSession session = request.getSession();
        PrintWriter out = response.getWriter();
    	StringBuilder htmlStringBuilder = new StringBuilder();
    	
    	htmlStringBuilder.append(HtmlProvider.getInstance().getHtmlCandidateSkillSortHead(""));
    	
    	htmlStringBuilder.append("<html>\r\n\t<form action=\"UploadFileServlet\" method=\"post\" enctype=\"multipart/form-data\">\t\r\n\t\tSelect file to upload: <input id=\"choose-file-button\" type=\"file\" value=\"Choose file\">\r\n\t\t<br/><br/>\r\n\t\t<input id=\"confirm-file-button\" type=\"submit\" value=\"submit\" >\r\n\t</form>\r\n</html>");
//*/
    	// checks if the request actually contains upload file
    	if (!ServletFileUpload.isMultipartContent(request)) {
    	    PrintWriter writer = response.getWriter();
    	    writer.println("Request does not contain upload data");
    	    writer.flush();
    	    return;
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
         // getServletContext().getRequestDispatcher("/message.jsp").forward(request, response);
         
	SignInServlet servletObject = new SignInServlet();
	servletObject.doPost(request, response);
	response.sendRedirect(UrlProvider.getInstance().getBaseUrl(request)+ "/RezuMe/SignInServlet");
    }
   public void doGet(HttpServletRequest request,
       HttpServletResponse response) throws ServletException, IOException {
       response.setContentType("text/html");
       PrintWriter out = response.getWriter();
       String docType = "<DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">\n";
       out.println(docType + "<HTML>Hello World</HTML>\n");
   }
}

