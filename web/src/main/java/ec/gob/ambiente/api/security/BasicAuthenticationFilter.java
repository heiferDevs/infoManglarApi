package ec.gob.ambiente.api.security;

import com.itextpdf.xmp.impl.Base64;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.StringTokenizer;

public abstract class BasicAuthenticationFilter implements Filter {

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
      throws IOException, ServletException {

    HttpServletRequest request = (HttpServletRequest) servletRequest;
    HttpServletResponse response = (HttpServletResponse) servletResponse;

    if (request.getMethod().equals("OPTIONS")) {
    	// PASS
        filterChain.doFilter(servletRequest, servletResponse);
        return;
    }

    String authHeader = request.getHeader("Authorization");

    if (authHeader != null) {
      StringTokenizer st = new StringTokenizer(authHeader);
      if (st.hasMoreTokens()) {
        String basic = st.nextToken();
        if (basic.equalsIgnoreCase("Basic")) {
          try {
        	  String credentials = Base64.decode(st.nextToken()); 
            int p = credentials.indexOf(":");
            if (p != -1) {
              String _username = credentials.substring(0, p).trim();
              String _password = credentials.substring(p + 1).trim();
              if (!isValidUser(_username, _password)) {
                unauthorized(response, "Bad credentials");
                return;
              }
              // PASS
              filterChain.doFilter(servletRequest, servletResponse);
            } else {
              unauthorized(response, "Invalid authentication token");
            }
          } catch (UnsupportedEncodingException e) {
            throw new Error("Couldn't retrieve authentication", e);
          }
        }
      }
    } else {
      unauthorized(response, "Unauthorized");
    }
  }

  public abstract boolean isValidUser(String username, String password);

  @Override
  public void destroy() {
  }

  private void unauthorized(HttpServletResponse response, String message) throws IOException {
	 response.setHeader("WWW-Authenticate", "Basic realm=\"Protected\"");
	 String json = "{\"state\": \"ERROR USUARIO NO AUTORIZADO\"}";
	 response.setContentType("application/json");
	 response.setCharacterEncoding("UTF-8");
	 response.getWriter().write(json);
  }

}