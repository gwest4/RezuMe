package provider;

/**
 * The HtmlProvider class allows for obtaining static HTML mark-up.
 * 
 * @author Adrian Baran
 */
public class HtmlProvider {
  private static HtmlProvider instance;
  private static String htmlHeadString1 = "<!doctype html>\r\n<html>\r\n<head>\r\n\t"
      + "<meta charset=\"utf-8\">\r\n\t<title>RezuMe - Sign In</title>\r\n\t"
      + "<link rel=\"stylesheet\" href=\"css/html5reset.css\">\r\n\t";
  private static String htmlHeadString2 = "<link href='http://fonts.googleapis.com/css?family=Lato:300,400' rel='stylesheet' "
      + "type='text/css'>\r\n</head>\r\n<body>\r\n\t<div id=\"container\">\r\n\t\t"
      + "<div id=\"header\">\r\n\t\t\t<img src=\"images/chicago-header.jpg\" alt=\"\">\r\n\t\t\t"
      + "<div id=\"header-logo\">\r\n\t\t\t\t<a href=\"index.html\"><h1 id=\"header-logo-text\">"
      + "RezuMe</h1></a>\r\n\t\t\t\t<h2 id=\"header-logo-subtext\">Internships, simplified.</h2>"
      + "\r\n\t\t\t</div>\r\n\t\t\t<form action=\"LoginServlet\" method=\"post\">\r\n\t\t\t\t"
      + "<input id=\"login-button\" type=\"submit\" value=\"My Account\">\r\n\t\t\t\t"
      + "<p id=\"signup-link-text\"><a id=\"signup-link\" href=\"signup.html\">"
      + "Not registered? Sign up now.</a></p>\r\n\t\t\t</form>\r\n\t\t</div>";
  private static String htmlTailString = "\n\t<footer><p id=\"footer-text\">"
      + "DePaul University - Spring 2015 - Software Projects &copy; 2015 Team 2</p>\r\n"
      + "<center><img src=\"images/dpu-logo.png\" alt=\"\"></center>\r\n"
      + "</footer></body>\r\n</html>";

  private HtmlProvider() {}

  public static HtmlProvider getInstance() {
    if (instance == null) {
      instance = new HtmlProvider();
    }

    return instance;
  }

  public String getHtmlHead(String cssFile) {
    String htmlHeadString = htmlHeadString1 + "<link rel=\"stylesheet\" href=\"css/" + cssFile + "\">\r\n\t"
        + htmlHeadString2;
    return htmlHeadString;
  }

  public String getHtmlTail() {
    return htmlTailString;
  }

  public String getHtmlCandidateSkillSortHead(String cssFile) {
    String htmlHeadString = htmlHeadString1 + "<link rel=\"stylesheet\" href=\"css/" + cssFile + "\">\r\n\t"
      +"<link href='http://fonts.googleapis.com/css?family=Lato:300,400' rel='stylesheet' "
      + "type='text/css'>\r\n"
      +"<script src=\"http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js\"></script>"
      + "<script src=\"jquery/jquery.sortable.js\"></script>"
      + "<script>$(function() {$('.sortable').sortable();$('.handles').sortable({handle: 'span'});"
      + "$('.connected').sortable({connectWith: '.connected', items: ':not(.disabled)'});$('.exclude').sortable({items: ':not(.disabled)'});});"
      + "</script></head>\r\n<body>\r\n\t<div id=\"container\">\r\n\t\t"
      + "<div id=\"header\">\r\n\t\t\t<img src=\"images/chicago-header.jpg\" alt=\"\">\r\n\t\t\t"
      + "<div id=\"header-logo\">\r\n\t\t\t\t<a href=\"index.html\"><h1 id=\"header-logo-text\">"
      + "RezuMe</h1></a>\r\n\t\t\t\t<h2 id=\"header-logo-subtext\">Internships, simplified.</h2>"
      + "\r\n\t\t\t</div>\r\n\t\t\t<form action=\"LoginServlet\" method=\"post\">\r\n\t\t\t\t"
      + "<input id=\"login-button\" type=\"submit\" value=\"My Account\">\r\n\t\t\t\t"
      + "<p id=\"signup-link-text\"><a id=\"signup-link\" href=\"signup.html\">"
      + "Not registered? Sign up now.</a></p>\r\n\t\t\t</form>\r\n\t\t</div>";
    return htmlHeadString;
  }
}
