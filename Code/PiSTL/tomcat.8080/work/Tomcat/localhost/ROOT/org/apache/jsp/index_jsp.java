/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/8.0.15
 * Generated at: 2014-12-15 18:02:39 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class index_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(4);
    _jspx_dependants.put("/page/test-display-one.jsp", Long.valueOf(1417601969568L));
    _jspx_dependants.put("/page/test-new.jsp", Long.valueOf(1418666555614L));
    _jspx_dependants.put("/page/test-display-all.jsp", Long.valueOf(1417601969568L));
    _jspx_dependants.put("/component/menu-top.jsp", Long.valueOf(1418069666050L));
  }

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
        throws java.io.IOException, javax.servlet.ServletException {

final java.lang.String _jspx_method = request.getMethod();
if (!"GET".equals(_jspx_method) && !"POST".equals(_jspx_method) && !"HEAD".equals(_jspx_method) && !javax.servlet.DispatcherType.ERROR.equals(request.getDispatcherType())) {
response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "JSPs only permit GET POST or HEAD");
return;
}

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("\t<head>\n");
      out.write("\t\t<meta charset=\"UTF-8\">\n");
      out.write("\t\t<title>Pi-Injector</title>\n");
      out.write("\t\t\n");
      out.write("\t\t<!-- jQuery -->\n");
      out.write("\t\t<script src=\"lib/jquery.js\"></script>\n");
      out.write("\t\t\n");
      out.write("\t\t<!-- Bootstrap -->\n");
      out.write("\t\t<link rel=\"stylesheet\" href=\"lib/bootstrap/css/bootstrap.min.css\">\n");
      out.write("\t\t<link rel=\"stylesheet\" href=\"lib/bootstrap/css/bootstrap-theme.min.css\">\n");
      out.write("\t\t<script src=\"lib/bootstrap/js/bootstrap.min.js\"></script>\n");
      out.write("\t\t<!-- pour les mobiles -->\n");
      out.write("\t\t<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n");
      out.write("\t\t\n");
      out.write("\t\t<!-- Chart.js -->\n");
      out.write("\t\t<script src=\"lib/chart.js/Chart.min.js\"></script>\n");
      out.write("\t\t\n");
      out.write("\t\t<!-- JavaScript -->\n");
      out.write("\t\t<script type=\"text/javascript\" src=\"javascript/javascript.js\" charset=\"UTF-8\"></script>\n");
      out.write("\t\t\n");
      out.write("\t\t<!-- CSS -->\n");
      out.write("\t\t<link rel=\"stylesheet\" href=\"css/style.css\">\n");
      out.write("\t</head>\n");
      out.write("\t<body>\n");
      out.write("\t\t");
      out.write("\r\n");
      out.write("<div class=\"navbar navbar-default navbar-fixed-top\" role=\"navigation\" id=\"navbar_menuTop\">\r\n");
      out.write("\t<div class=\"container\">\r\n");
      out.write("\t\t<div class=\"navbar-header\">\r\n");
      out.write("\t\t\t<!-- button type=\"button\" class=\"navbar-toggle collapsed\" data-toggle=\"collapse\"-->\r\n");
      out.write("\t\t\t<ul class=\"nav navbar-nav\">\r\n");
      out.write("\t\t\t\t<li><a href=\"index.jsp\" id=\"a_test-new\"><span class=\"glyphicon glyphicon-home\"></span> Nouveau test</a></li>\r\n");
      out.write("\t\t\t</ul>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t\t<div class=\"navbar-collapse\">\r\n");
      out.write("\t\t\t<ul class=\"nav navbar-nav\">\r\n");
      out.write("\t\t\t\t<li><a href=\"index.jsp?page=test-display-all\" id=\"a_test-display-all\">Tous les tests</a></li>\r\n");
      out.write("\t\t\t\t<li><a href=\"index.jsp?page=test-display-one\" id=\"a_test-display-one\">Un seul test</a></li>\r\n");
      out.write("\t\t\t</ul>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</div>\r\n");
      out.write("</div>");
      out.write("\n");
      out.write("\t\t\n");
      out.write("\t\t<div class=\"container\">\n");
      out.write("\t\t\t<div class=\"row\">\n");
      out.write("\t\t\t\t<div class=\"container\">\n");
      out.write("\t\t\t\t\t<img src=\"img/logo.png\" height=\"100px\">\n");
      out.write("\t\t\t\t</div>\n");
      out.write("\t\t\t\t\n");
      out.write("\t\t\t\t<div class=\"col-sm-10\" role=\"main\">\n");
      out.write("\t\t\t\t\t");
 if("test-display-all".equals(request.getParameter("page"))) { 
      out.write("\n");
      out.write("\t\t\t\t\t\t");
      out.write("\r\n");
      out.write("<div class=\"container\">\r\n");
      out.write("\t<h3>Test results</h3>\r\n");
      out.write("\t\r\n");
      out.write("\t<table class=\"table table-hover table-striped\" id=\"form_test_table_\">\r\n");
      out.write("\t\t<thead>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<th>Remove</th>\r\n");
      out.write("\t\t\t\t<th>Date</th>\r\n");
      out.write("\t\t\t\t<th>Test name</th>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t</thead>\r\n");
      out.write("\t\t<tbody>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td><span class=\"glyphicon glyphicon-remove\" aria-hidden=\"true\"></span></td>\r\n");
      out.write("\t\t\t\t<td>2014/11/27 08:02</td>\r\n");
      out.write("\t\t\t\t<td>Test LDAP 3</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td><span class=\"glyphicon glyphicon-remove\" aria-hidden=\"true\"></span></td>\r\n");
      out.write("\t\t\t\t<td>2014/11/26 21:57</td>\r\n");
      out.write("\t\t\t\t<td>Test LDAP 2</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td><span class=\"glyphicon glyphicon-remove\" aria-hidden=\"true\"></span></td>\r\n");
      out.write("\t\t\t\t<td>2014/11/26 20:34</td>\r\n");
      out.write("\t\t\t\t<td>Test LDAP 1</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t</tbody>\r\n");
      out.write("\t</table>\r\n");
      out.write("</div>");
      out.write("\n");
      out.write("\t\t\t\t\t");
 }
					else if("test-display-one".equals(request.getParameter("page"))) { 
      out.write("\n");
      out.write("\t\t\t\t\t\t");
      out.write("\r\n");
      out.write("<div class=\"container\">\r\n");
      out.write("\t<h3>Test LDAP 3</h3>\r\n");
      out.write("\t\r\n");
      out.write("\t<a href=\"\">Download CSV</a>\r\n");
      out.write("\t\r\n");
      out.write("\t<img src=\"tmp/test_diagramm.png\">\r\n");
      out.write("</div>");
      out.write("\n");
      out.write("\t\t\t\t\t");
 }
					else { 
      out.write("\n");
      out.write("\t\t\t\t\t\t");
      out.write("\r\n");
      out.write("<div class=\"container\">\r\n");
      out.write("\t<h3>Test configuration</h3>\r\n");
      out.write("\t\r\n");
      out.write("\t<form class=\"form-horizontal\" role=\"form\" id=\"form_test\" method=\"post\" action=\"\">\r\n");
      out.write("\t\t<div class=\"form-group\">\r\n");
      out.write("\t\t\t<label class=\"control-label col-sm-4\" for=\"form_test_name\">Test name</label>\r\n");
      out.write("\t\t\t<div class=\"col-sm-8\">\r\n");
      out.write("\t\t\t\t<input class=\"form-control\" id=\"form_test_name\" type=\"text\" placeholder=\"\" required>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t \t</div>\r\n");
      out.write("\t\t<div class=\"form-group\">\r\n");
      out.write("\t\t\t<label class=\"control-label col-sm-4\" for=\"form_test_nb_injectors\">Number of injectors</label>\r\n");
      out.write("\t\t\t<div class=\"col-sm-8\">\r\n");
      out.write("\t\t\t\t<select class=\"form-control\" id=\"form_test_nb_injectors\">\r\n");
      out.write("\t\t\t\t\t");
 for(int i = 1; i <= 5; i++) { 
      out.write("\r\n");
      out.write("\t\t\t\t\t\t<option value=\"");
      out.print( i );
      out.write('"');
      out.write('>');
      out.print( i );
      out.write("</option>\r\n");
      out.write("\t\t\t\t\t");
 } 
      out.write("\r\n");
      out.write("\t\t\t\t</select>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t \t</div>\r\n");
      out.write("\t\t<div class=\"form-group\">\r\n");
      out.write("\t\t\t<label class=\"control-label col-sm-4\" for=\"form_test_nb_threads\">Number of threads by injector</label>\r\n");
      out.write("\t\t\t<div class=\"col-sm-8\">\r\n");
      out.write("\t\t\t\t<select class=\"form-control\" id=\"form_test_nb_threads\">\r\n");
      out.write("\t\t\t\t\t");
 for(int i = 1; i <= 2; i++) { 
      out.write("\r\n");
      out.write("\t\t\t\t\t\t<option value=\"");
      out.print( i );
      out.write('"');
      out.write('>');
      out.print( i );
      out.write("</option>\r\n");
      out.write("\t\t\t\t\t");
 } 
      out.write("\r\n");
      out.write("\t\t\t\t</select>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t \t</div>\r\n");
      out.write("\t\t<div class=\"form-group\">\r\n");
      out.write("\t\t\t<label class=\"control-label col-sm-4\" for=\"form_test_duration\">Test duration (in seconds)</label>\r\n");
      out.write("\t\t\t<div class=\"col-sm-8\">\r\n");
      out.write("\t\t\t\t<input class=\"form-control\" id=\"form_test_duration\" type=\"text\" placeholder=\"\">\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t \t</div>\r\n");
      out.write("\t\t<div class=\"form-group\">\r\n");
      out.write("\t\t\t<label class=\"control-label col-sm-4\" for=\"form_test_\">Protocol</label>\r\n");
      out.write("\t\t\t<div class=\"col-sm-8\">\r\n");
      out.write("\t\t\t\t<select class=\"form-control\" id=\"form_test_protocol\">\r\n");
      out.write("\t\t\t\t\t<option value=\"LDAP\">LDAP</option>\r\n");
      out.write("\t\t\t\t</select>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t \t</div>\r\n");
      out.write("\t \t<fieldset>\r\n");
      out.write("\t \t\t<legend>LDAP</legend>\r\n");
      out.write("\t\t\t<div class=\"form-group\">\r\n");
      out.write("\t\t\t\t<label class=\"control-label col-sm-4\" for=\"form_test_servername\">Servername / port</label>\r\n");
      out.write("\t\t\t\t<div class=\"col-sm-6\">\r\n");
      out.write("\t\t\t\t\t<input class=\"form-control\" id=\"form_test_servername\" type=\"text\" placeholder=\"Servername\">\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t<div class=\"col-sm-2\">\r\n");
      out.write("\t\t\t\t\t<input class=\"form-control\" id=\"form_test_port\" type=\"text\" placeholder=\"Port\">\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t \t</div>\r\n");
      out.write("\t\t\t<div class=\"form-group\">\r\n");
      out.write("\t\t\t\t<label class=\"control-label col-sm-4\" for=\"form_test_dn\">DN</label>\r\n");
      out.write("\t\t\t\t<div class=\"col-sm-8\">\r\n");
      out.write("\t\t\t\t\t<input class=\"form-control\" id=\"form_test_dn\" type=\"text\" placeholder=\"DN\">\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t \t</div>\r\n");
      out.write("\t\t\t<div class=\"form-group\">\r\n");
      out.write("\t\t\t\t<label class=\"control-label col-sm-4\" for=\"form_test_username\">Username / password</label>\r\n");
      out.write("\t\t\t\t<div class=\"col-sm-4\">\r\n");
      out.write("\t\t\t\t\t<input class=\"form-control\" id=\"form_test_username\" type=\"text\" placeholder=\"Username\">\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t<div class=\"col-sm-4\">\r\n");
      out.write("\t\t\t\t\t<input class=\"form-control\" id=\"form_test_password\" type=\"password\" placeholder=\"Password\">\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t \t</div>\r\n");
      out.write("\t\t\t<div class=\"form-group\">\r\n");
      out.write("\t\t\t\t<label class=\"control-label col-sm-4\">Test plan</label>\r\n");
      out.write("\t\t\t\t<div class=\"col-sm-8\">\r\n");
      out.write("\t\t\t\t\t<input id=\"form_test_nb-plan\" type=\"hidden\" value=\"0\">\r\n");
      out.write("\t\t\t\t\t<table class=\"table table-hover table-striped\" id=\"form_test_table_plan\">\r\n");
      out.write("\t\t\t\t\t\t<thead>\r\n");
      out.write("\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t<th>Remove</th>\r\n");
      out.write("\t\t\t\t\t\t\t\t<th>Action</th>\r\n");
      out.write("\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t</thead>\r\n");
      out.write("\t\t\t\t\t\t<tbody>\r\n");
      out.write("\t\t\t\t\t\t</tbody>\r\n");
      out.write("\t\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t \t</div>\r\n");
      out.write("\t\t\t<div class=\"form-group\">\r\n");
      out.write("\t\t\t\t<label class=\"control-label col-sm-4\">Add request</label>\r\n");
      out.write("\t\t\t\t<div class=\"col-sm-8\">\r\n");
      out.write("\t\t\t\t\t<div role=\"tabpanel\" id=\"form_test_tab_request\">\r\n");
      out.write("\t\t\t\t \t\t<ul class=\"nav nav-tabs\" role=\"tablist\">\r\n");
      out.write("\t\t\t\t \t\t\t<li role=\"presentation\" class=\"active\"><a href=\"#add\" aria-controls=\"add\" role=\"tab\" data-toggle=\"tab\">Add</a></li>\r\n");
      out.write("\t\t\t\t \t\t\t<li role=\"presentation\"><a href=\"#bind\" aria-controls=\"bind\" role=\"tab\" data-toggle=\"tab\">Bind</a></li>\r\n");
      out.write("\t\t\t\t \t\t\t<li role=\"presentation\"><a href=\"#bind-unbind\" aria-controls=\"bind-unbind\" role=\"tab\" data-toggle=\"tab\">Bind-unbind</a></li>\r\n");
      out.write("\t\t\t\t \t\t\t<li role=\"presentation\"><a href=\"#compare\" aria-controls=\"compare\" role=\"tab\" data-toggle=\"tab\">Compare</a></li>\r\n");
      out.write("\t\t\t\t \t\t\t<li role=\"presentation\"><a href=\"#delete\" aria-controls=\"delete\" role=\"tab\" data-toggle=\"tab\">Delete</a></li>\r\n");
      out.write("\t\t\t\t \t\t\t<li role=\"presentation\"><a href=\"#modify\" aria-controls=\"modify\" role=\"tab\" data-toggle=\"tab\">Modify</a></li>\r\n");
      out.write("\t\t\t\t \t\t\t<li role=\"presentation\"><a href=\"#rename\" aria-controls=\"rename\" role=\"tab\" data-toggle=\"tab\">Rename</a></li>\r\n");
      out.write("\t\t\t\t \t\t\t<li role=\"presentation\"><a href=\"#search\" aria-controls=\"search\" role=\"tab\" data-toggle=\"tab\">Search</a></li>\r\n");
      out.write("\t\t\t\t \t\t\t<li role=\"presentation\"><a href=\"#unbind\" aria-controls=\"unbind\" role=\"tab\" data-toggle=\"tab\">Unbind</a></li>\r\n");
      out.write("\t\t\t\t \t\t</ul>\r\n");
      out.write("\t\t\t\t \t\t<div class=\"tab-content\">\r\n");
      out.write("\t\t\t\t\t\t \t<div role=\"tabpanel\" class=\"tab-pane active\" id=\"add\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<div class=\"form-group\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<label class=\"control-label col-sm-4\" for=\"form_test_add_entry-dn\">Entry DN</label>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<div class=\"col-sm-8\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<input class=\"form-control\" id=\"form_test_add_entry-dn\" type=\"text\" placeholder=\"\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t \t</div>\r\n");
      out.write("\t\t\t\t\t\t\t \t<div class=\"form-group\">\r\n");
      out.write("\t\t\t\t\t\t\t \t\t<div class=\"col-sm-offset-4 col-sm-8\">\r\n");
      out.write("\t\t\t\t\t\t\t \t\t\t<button class=\"btn btn-primary form_test_add-to-plan\" type=\"button\" id=\"form_test_btn_add\">Add to test plan</button>\r\n");
      out.write("\t\t\t\t\t\t\t \t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t \t</div>\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t \t\t\t<div role=\"tabpanel\" class=\"tab-pane\" id=\"bind\">\r\n");
      out.write("\t\t\t\t\t\t\t \t<div class=\"form-group\">\r\n");
      out.write("\t\t\t\t\t\t\t \t\t<div class=\"col-sm-offset-4 col-sm-8\">\r\n");
      out.write("\t\t\t\t\t\t\t \t\t\t<button class=\"btn btn-primary form_test_add-to-plan\" type=\"button\" id=\"form_test_btn_bind\">Add to test plan</button>\r\n");
      out.write("\t\t\t\t\t\t\t \t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t \t</div>\r\n");
      out.write("\t\t\t\t \t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t \t\t\t<div role=\"tabpanel\" class=\"tab-pane\" id=\"bind-unbind\">\r\n");
      out.write("\t\t\t\t\t\t\t \t<div class=\"form-group\">\r\n");
      out.write("\t\t\t\t\t\t\t \t\t<div class=\"col-sm-offset-4 col-sm-8\">\r\n");
      out.write("\t\t\t\t\t\t\t \t\t\t<button class=\"btn btn-primary form_test_add-to-plan\" type=\"button\" id=\"form_test_btn_bind-unbind\">Add to test plan</button>\r\n");
      out.write("\t\t\t\t\t\t\t \t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t \t</div>\r\n");
      out.write("\t\t\t\t \t\t\t</div>\r\n");
      out.write("\t\t\t\t \t\t\t\r\n");
      out.write("\t\t\t\t\t\t \t<div role=\"tabpanel\" class=\"tab-pane\" id=\"compare\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<div class=\"form-group\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<label class=\"control-label col-sm-4\" for=\"form_test_compare_entry-dn\">Entry DN</label>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<div class=\"col-sm-8\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<input class=\"form-control\" id=\"form_test_compare_entry-dn\" type=\"text\" placeholder=\"\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t \t</div>\r\n");
      out.write("\t\t\t\t\t\t\t\t<div class=\"form-group\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<label class=\"control-label col-sm-4\" for=\"form_test_compare_filter\">Compare filter</label>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<div class=\"col-sm-8\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<input class=\"form-control\" id=\"form_test_compare_filter\" type=\"text\" placeholder=\"\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t \t</div>\r\n");
      out.write("\t\t\t\t\t\t\t \t<div class=\"form-group\">\r\n");
      out.write("\t\t\t\t\t\t\t \t\t<div class=\"col-sm-offset-4 col-sm-8\">\r\n");
      out.write("\t\t\t\t\t\t\t \t\t\t<button class=\"btn btn-primary form_test_add-to-plan\" type=\"button\" id=\"form_test_btn_compare\">Add to test plan</button>\r\n");
      out.write("\t\t\t\t\t\t\t \t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t \t</div>\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t \t<div role=\"tabpanel\" class=\"tab-pane\" id=\"delete\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<div class=\"form-group\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<label class=\"control-label col-sm-4\" for=\"form_test_delete_entry-dn\">Entry DN</label>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<div class=\"col-sm-8\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<input class=\"form-control\" id=\"form_test_delete_entry-dn\" type=\"text\" placeholder=\"\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t \t</div>\r\n");
      out.write("\t\t\t\t\t\t\t \t<div class=\"form-group\">\r\n");
      out.write("\t\t\t\t\t\t\t \t\t<div class=\"col-sm-offset-4 col-sm-8\">\r\n");
      out.write("\t\t\t\t\t\t\t \t\t\t<button class=\"btn btn-primary form_test_add-to-plan\" type=\"button\" id=\"form_test_btn_delete\">Add to test plan</button>\r\n");
      out.write("\t\t\t\t\t\t\t \t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t \t</div>\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t \t<div role=\"tabpanel\" class=\"tab-pane\" id=\"modify\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<div class=\"form-group\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<label class=\"control-label col-sm-4\" for=\"form_test_modify_entry-dn\">Entry DN</label>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<div class=\"col-sm-8\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<input class=\"form-control\" id=\"form_test_modify_entry-dn\" type=\"text\" placeholder=\"\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t \t</div>\r\n");
      out.write("\t\t\t\t\t\t\t\t<div class=\"form-group\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<label class=\"control-label col-sm-4\" for=\"form_test_modify_attribute\">Attribute</label>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<div class=\"col-sm-8\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<input class=\"form-control\" id=\"form_test_modify_attribute\" type=\"text\" placeholder=\"\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t \t</div>\r\n");
      out.write("\t\t\t\t\t\t\t\t<div class=\"form-group\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<label class=\"control-label col-sm-4\" for=\"form_test_modify_value\">Value</label>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<div class=\"col-sm-8\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<input class=\"form-control\" id=\"form_test_modify_value\" type=\"text\" placeholder=\"\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t \t</div>\r\n");
      out.write("\t\t\t\t\t\t\t\t<div class=\"form-group\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<label class=\"control-label col-sm-4\" for=\"form_test_modify_opcode\">opcode</label>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<div class=\"col-sm-8\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<select class=\"form-control\" id=\"form_test_modify_opcode\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t<option value=\"Add\">Add</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t<option value=\"Delete\">Delete</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t<option value=\"Replace\">Replace</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t</select>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t \t</div>\r\n");
      out.write("\t\t\t\t\t\t\t \t<div class=\"form-group\">\r\n");
      out.write("\t\t\t\t\t\t\t \t\t<div class=\"col-sm-offset-4 col-sm-8\">\r\n");
      out.write("\t\t\t\t\t\t\t \t\t\t<button class=\"btn btn-primary form_test_add-to-plan\" type=\"button\" id=\"form_test_btn_modify\">Add to test plan</button>\r\n");
      out.write("\t\t\t\t\t\t\t \t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t \t</div>\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t \t<div role=\"tabpanel\" class=\"tab-pane\" id=\"rename\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<div class=\"form-group\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<label class=\"control-label col-sm-4\" for=\"form_test_rename_old-entry-dn\">Old entry DN</label>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<div class=\"col-sm-8\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<input class=\"form-control\" id=\"form_test_rename_old-entry-dn\" type=\"text\" placeholder=\"\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t \t</div>\r\n");
      out.write("\t\t\t\t\t\t\t\t<div class=\"form-group\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<label class=\"control-label col-sm-4\" for=\"form_test_rename_new-entry-dn\">New entry DN</label>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<div class=\"col-sm-8\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<input class=\"form-control\" id=\"form_test_rename_new-entry-dn\" type=\"text\" placeholder=\"\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t \t</div>\r\n");
      out.write("\t\t\t\t\t\t\t \t<div class=\"form-group\">\r\n");
      out.write("\t\t\t\t\t\t\t \t\t<div class=\"col-sm-offset-4 col-sm-8\">\r\n");
      out.write("\t\t\t\t\t\t\t \t\t\t<button class=\"btn btn-primary form_test_add-to-plan\" type=\"button\" id=\"form_test_btn_rename\">Add to test plan</button>\r\n");
      out.write("\t\t\t\t\t\t\t \t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t \t</div>\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\t<div role=\"tabpanel\" class=\"tab-pane\" id=\"search\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<div class=\"form-group\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<label class=\"control-label col-sm-4\" for=\"form_test_search_base\">Search base</label>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<div class=\"col-sm-8\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<input class=\"form-control\" id=\"form_test_search_base\" type=\"text\" placeholder=\"\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t \t</div>\r\n");
      out.write("\t\t\t\t\t\t\t\t<div class=\"form-group\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<label class=\"control-label col-sm-4\" for=\"form_test_search_filter\">Search filter</label>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<div class=\"col-sm-8\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<input class=\"form-control\" id=\"form_test_search_filter\" type=\"text\" placeholder=\"\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t \t</div>\r\n");
      out.write("\t\t\t\t\t\t\t\t<div class=\"form-group\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<label class=\"control-label col-sm-4\" for=\"form_test_search_scope\">Search scope</label>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<div class=\"col-sm-8\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<select class=\"form-control\" id=\"form_test_search_scope\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t<option value=\"base-object\">Base object</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t<option value=\"one-level\">One level</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t<option value=\"subtree\">Subtree</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t</select>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t \t</div>\r\n");
      out.write("\t\t\t\t\t\t\t \t<div class=\"form-group\">\r\n");
      out.write("\t\t\t\t\t\t\t \t\t<div class=\"col-sm-offset-4 col-sm-8\">\r\n");
      out.write("\t\t\t\t\t\t\t \t\t\t<button class=\"btn btn-primary form_test_add-to-plan\" type=\"button\" id=\"form_test_btn_search\">Add to test plan</button>\r\n");
      out.write("\t\t\t\t\t\t\t \t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t \t</div>\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t \t<div role=\"tabpanel\" class=\"tab-pane\" id=\"unbind\">\r\n");
      out.write("\t\t\t\t\t\t\t \t<div class=\"form-group\">\r\n");
      out.write("\t\t\t\t\t\t\t \t\t<div class=\"col-sm-offset-4 col-sm-8\">\r\n");
      out.write("\t\t\t\t\t\t\t \t\t\t<button class=\"btn btn-primary form_test_add-to-plan\" type=\"button\" id=\"form_test_btn_unbind\">Add to test plan</button>\r\n");
      out.write("\t\t\t\t\t\t\t \t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t \t</div>\r\n");
      out.write("\t\t\t\t \t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t \t</div>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t \t</div>\r\n");
      out.write("\t\t</fieldset>\r\n");
      out.write("\t \t<div class=\"form-group\">\r\n");
      out.write("\t \t\t<div class=\"col-sm-offset-4 col-sm-8\">\r\n");
      out.write("\t \t\t\t<button class=\"btn btn-primary\" type=\"submit\">Run</button>\r\n");
      out.write("\t \t\t\t<button class=\"btn btn-default\" type=\"reset\">Reset</button>\r\n");
      out.write("\t \t\t</div>\r\n");
      out.write("\t \t</div>\r\n");
      out.write("\t</form>\r\n");
      out.write("</div>");
      out.write("\n");
      out.write("\t\t\t\t\t");
 } 
      out.write("\n");
      out.write("\t\t\t\t</div>\n");
      out.write("\t\t\t</div>\n");
      out.write("\t\t</div>\n");
      out.write("\t</body>\n");
      out.write("</html>");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try {
            if (response.isCommitted()) {
              out.flush();
            } else {
              out.clearBuffer();
            }
          } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
