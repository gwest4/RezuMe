package provider;

import javax.servlet.http.HttpServletRequest;

/**
 * The UrlProvider class allows for obtaining the URL of the web application.
 * 
 * @author Adrian Baran
 */
public class UrlProvider {
  private static UrlProvider instance;

  private UrlProvider() {}

  public static UrlProvider getInstance() {
    if (instance == null) {
      instance = new UrlProvider();
    }

    return instance;
  }

  public String getBaseUrl(HttpServletRequest request) {
    String baseUrlString =
        request.getScheme()
            + "://"
            + request.getServerName()
            + ("http".equals(request.getScheme()) && request.getServerPort() == 80
                || "https".equals(request.getScheme()) && request.getServerPort() == 443 ? "" : ":"
                + request.getServerPort());

    return baseUrlString;
  }
}
