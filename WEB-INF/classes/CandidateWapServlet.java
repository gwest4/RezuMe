

import java.io.*;
import java.util.*;
import java.lang.ClassCastException;

import javax.servlet.*;
import javax.servlet.http.*;

import database.*;
import provider.*;
/**
 * CandidateWapServlet.
 * 
 * @author Adrian Baran, Aditya Narravula, George West
 */
public class CandidateWapServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		String questions = 
				//CheckBox Questions
				"<p><h3>Setting long terms goals is something that you enjoy doing.</h3><input type=\"radio\" name=\"q1\" value=\"0\">Strongly Disagree<input type=\"radio\" name=\"q1\" value=\"1\">Disagree<input type=\"radio\" name=\"q1\" value=\"2\" >Unsure<input type=\"radio\" name=\"q1\" value=\"3\">Agree<input type=\"radio\" name=\"q1\" value=\"4\">Strongly Agree</p>"+
				"<p><h3>You are able to lead a project team without any prior preparation.</h3><input type=\"radio\" name=\"q2\" value=\"0\">Strongly Disagree<input type=\"radio\" name=\"q2\" value=\"1\">Disagree<input type=\"radio\" name=\"q2\" value=\"2\" >Unsure<input type=\"radio\" name=\"q2\" value=\"3\">Agree<input type=\"radio\" name=\"q2\" value=\"4\">Strongly Agree</p>"+
				"<p><h3>When it comes to groups you are more likely to lead the group.</h3><input type=\"radio\" name=\"q3\" value=\"0\">Strongly Disagree<input type=\"radio\" name=\"q3\" value=\"1\">Disagree<input type=\"radio\" name=\"q3\" value=\"2\" >Unsure<input type=\"radio\" name=\"q3\" value=\"3\">Agree<input type=\"radio\" name=\"q3\" value=\"4\">Strongly Agree</p>"+
				"<p><h3>Trying out new activities is no problem for you.</h3><input type=\"radio\" name=\"q4\" value=\"0\">Strongly Disagree<input type=\"radio\" name=\"q4\" value=\"1\">Disagree<input type=\"radio\" name=\"q4\" value=\"2\" >Unsure<input type=\"radio\" name=\"q4\" value=\"3\">Agree<input type=\"radio\" name=\"q4\" value=\"4\">Strongly Agree</p>"+
				"<p><h3>When you make a mistake you are able to accept it and move on.</h3><input type=\"radio\" name=\"q5\" value=\"0\">Strongly Disagree<input type=\"radio\" name=\"q5\" value=\"1\">Disagree<input type=\"radio\" name=\"q5\" value=\"2\" >Unsure<input type=\"radio\" name=\"q5\" value=\"3\">Agree<input type=\"radio\" name=\"q5\" value=\"4\">Strongly Agree</p>"+
				"<p><h3>When asked to work extra hours to complete an assignment you always accept.</h3><input type=\"radio\" name=\"q6\" value=\"0\">Strongly Disagree<input type=\"radio\" name=\"q6\" value=\"1\">Disagree<input type=\"radio\" name=\"q6\" value=\"2\" >Unsure<input type=\"radio\" name=\"q6\" value=\"3\">Agree<input type=\"radio\" name=\"q6\" value=\"4\">Strongly Agree</p>"+
				"<p><h3>If asked to work off the clock for a job, you would do it.</h3><input type=\"radio\" name=\"q7\" value=\"0\">Strongly Disagree<input type=\"radio\" name=\"q7\" value=\"1\">Disagree<input type=\"radio\" name=\"q7\" value=\"2\" >Unsure<input type=\"radio\" name=\"q7\" value=\"3\">Agree<input type=\"radio\" name=\"q7\" value=\"4\">Strongly Agree</p>"+
				"<p><h3>You do not need help starting up when given a job.</h3><input type=\"radio\" name=\"q8\" value=\"0\">Strongly Disagree<input type=\"radio\" name=\"q8\" value=\"1\">Disagree<input type=\"radio\" name=\"q8\" value=\"2\" >Unsure<input type=\"radio\" name=\"q8\" value=\"3\">Agree<input type=\"radio\" name=\"q8\" value=\"4\">Strongly Agree</p>"+
				"<p><h3>Travelling and staying active is something that you enjoy doing.</h3><input type=\"radio\" name=\"q9\" value=\"0\">Strongly Disagree<input type=\"radio\" name=\"q9\" value=\"1\">Disagree<input type=\"radio\" name=\"q9\" value=\"2\" >Unsure<input type=\"radio\" name=\"q9\" value=\"3\">Agree<input type=\"radio\" name=\"q9\" value=\"4\">Strongly Agree</p>"+
				"<p><h3>You like implementing new ideas more than thinking of them.</h3><input type=\"radio\" name=\"q10\" value=\"0\">Strongly Disagree<input type=\"radio\" name=\"q10\" value=\"1\">Disagree<input type=\"radio\" name=\"q10\" value=\"2\" >Unsure<input type=\"radio\" name=\"q10\" value=\"3\">Agree<input type=\"radio\" name=\"q10\" value=\"4\">Strongly Agree</p>"+
				"<p><h3>When talking to a group of people it comes easily to you.</h3><input type=\"radio\" name=\"q11\" value=\"0\">Strongly Disagree<input type=\"radio\" name=\"q11\" value=\"1\">Disagree<input type=\"radio\" name=\"q11\" value=\"2\" >Unsure<input type=\"radio\" name=\"q11\" value=\"3\">Agree<input type=\"radio\" name=\"q11\" value=\"4\">Strongly Agree</p>"+
				"<p><h3>When you come up with an idea you are able to express your ideas easily to people.</h3><input type=\"radio\" name=\"q12\" value=\"0\">Strongly Disagree<input type=\"radio\" name=\"q12\" value=\"1\">Disagree<input type=\"radio\" name=\"q12\" value=\"2\" >Unsure<input type=\"radio\" name=\"q12\" value=\"3\">Agree<input type=\"radio\" name=\"q12\" value=\"4\">Strongly Agree</p>"+
				"<p><h3>You like routine office jobs.</h3><input type=\"radio\" name=\"q13\" value=\"0\">Strongly Disagree<input type=\"radio\" name=\"q13\" value=\"1\">Disagree<input type=\"radio\" name=\"q13\" value=\"2\" >Unsure<input type=\"radio\" name=\"q13\" value=\"3\">Agree<input type=\"radio\" name=\"q13\" value=\"4\">Strongly Agree</p>"+
				"<p><h3>You will ask for a longer deadline if you know your work is not up to company.</h3><input class=\"radio-quiz\" type=\"radio\" name=\"q14\" value=\"0\">Strongly Disagree<input type=\"radio\" name=\"q14\" value=\"1\">Disagree<input type=\"radio\" name=\"q14\" value=\"2\" >Unsure<input type=\"radio\" name=\"q14\" value=\"3\">Agree<input type=\"radio\" name=\"q14\" value=\"4\">Strongly Agree</p>"+
				"<p><h3>Other people&#39;s feelings are more important to you than your own.</h3><input class=\"radio-quiz\" type=\"radio\" name=\"q15\" value=\"0\">Strongly Disagree<input type=\"radio\" name=\"q15\" value=\"1\">Disagree<input type=\"radio\" name=\"q15\" value=\"2\" >Unsure<input type=\"radio\" name=\"q15\" value=\"3\">Agree<input type=\"radio\" name=\"q15\" value=\"4\">Strongly Agree</p>"+
				"<p><h3>If you saw a co-worker stealing you would report them right away.</h3><input class=\"radio-quiz\" type=\"radio\" name=\"q16\" value=\"0\">Strongly Disagree<input type=\"radio\" name=\"q16\" value=\"1\">Disagree<input type=\"radio\" name=\"q16\" value=\"2\" >Unsure<input type=\"radio\" name=\"q16\" value=\"3\">Agree<input type=\"radio\" name=\"q16\" value=\"4\">Strongly Agree</p>"+
				"<p><h3>When deadlines are coming up you are able to remain calm.</h3><input class=\"radio-quiz\" type=\"radio\" name=\"q17\" value=\"0\">Strongly Disagree<input type=\"radio\" name=\"q17\" value=\"1\">Disagree<input type=\"radio\" name=\"q17\" value=\"2\" >Unsure<input type=\"radio\" name=\"q17\" value=\"3\">Agree<input type=\"radio\" name=\"q17\" value=\"4\">Strongly Agree</p>"+
				"<p><h3>You would rather help a person with their work than let them do it alone.</h3><input class=\"radio-quiz\" type=\"radio\" name=\"q18\" value=\"0\">Strongly Disagree<input type=\"radio\" name=\"q18\" value=\"1\">Disagree<input type=\"radio\" name=\"q18\" value=\"2\" >Unsure<input type=\"radio\" name=\"q18\" value=\"3\">Agree<input type=\"radio\" name=\"q18\" value=\"4\">Strongly Agree</p>"+
				"<p><h3>You like working alone.</h3><input type=\"radio\" name=\"q19\" value=\"0\">Strongly Disagree<input class=\"radio-quiz\" type=\"radio\" name=\"q19\" value=\"1\">Disagree<input type=\"radio\" name=\"q19\" value=\"2\" >Unsure<input type=\"radio\" name=\"q19\" value=\"3\">Agree<input type=\"radio\" name=\"q19\" value=\"4\">Strongly Agree</p>"+
				"<p><h3>If a co-worker needed help on their project but you had your own work already, you would still try to help them out.</h3><input class=\"radio-quiz\" type=\"radio\" name=\"q20\" value=\"0\">Strongly Disagree<input type=\"radio\" name=\"q20\" value=\"1\">Disagree<input type=\"radio\" name=\"q20\" value=\"2\" >Unsure<input type=\"radio\" name=\"q20\" value=\"3\">Agree<input type=\"radio\" name=\"q20\" value=\"4\">Strongly Agree</p>"+
				"<p><h3>You tend to get more stressed out when working in an unfamiliar area.</h3><input class=\"radio-quiz\" type=\"radio\" name=\"q21\" value=\"0\">Strongly Disagree<input type=\"radio\" name=\"q21\" value=\"1\">Disagree<input type=\"radio\" name=\"q21\" value=\"2\" >Unsure<input type=\"radio\" name=\"q21\" value=\"3\">Agree<input type=\"radio\" name=\"q21\" value=\"4\">Strongly Agree</p>"+
				"<p><h3>If a deadline is approaching, you tend to start rushing your work.</h3><input class=\"radio-quiz\" type=\"radio\" name=\"q22\" value=\"0\">Strongly Disagree<input type=\"radio\" name=\"q22\" value=\"1\">Disagree<input type=\"radio\" name=\"q22\" value=\"2\" >Unsure<input type=\"radio\" name=\"q22\" value=\"3\">Agree<input type=\"radio\" name=\"q22\" value=\"4\">Strongly Agree</p>"+
				"<p><h3>As long as I get my work done on time it is better than the quality of work.</h3><input class=\"radio-quiz\" type=\"radio\" name=\"q23\" value=\"0\">Strongly Disagree<input type=\"radio\" name=\"q23\" value=\"1\">Disagree<input type=\"radio\" name=\"q23\" value=\"2\" >Unsure<input type=\"radio\" name=\"q23\" value=\"3\">Agree<input type=\"radio\" name=\"q23\" value=\"4\">Strongly Agree</p>"+
				"<p><h3>If the boss is asking you to do something not in your job, you would still do it.</h3><input class=\"radio-quiz\" type=\"radio\" name=\"q24\" value=\"0\">Strongly Disagree<input type=\"radio\" name=\"q24\" value=\"1\">Disagree<input type=\"radio\" name=\"q24\" value=\"2\" >Unsure<input type=\"radio\" name=\"q24\" value=\"3\">Agree<input type=\"radio\" name=\"q24\" value=\"4\">Strongly Agree</p>"+
				"<p><h3>You are able to continue to perform a task if things change randomly.</h3><input class=\"radio-quiz\" type=\"radio\" name=\"q25\" value=\"0\">Strongly Disagree<input type=\"radio\" name=\"q25\" value=\"1\">Disagree<input type=\"radio\" name=\"q25\" value=\"2\" >Unsure<input type=\"radio\" name=\"q25\" value=\"3\">Agree<input type=\"radio\" name=\"q25\" value=\"4\">Strongly Agree</p>";

		@SuppressWarnings("unchecked")
		HashMap<String,Double> candidateWapScore = (HashMap<String,Double>) session.getAttribute("candidateWapScore");

		StringBuilder htmlStringBuilder = new StringBuilder(HtmlProvider.getInstance().getHtmlHead("candidate-wap.css"));

		if(request.getParameter("Submit1") != null) {
			try {
				double q1 = (double)Integer.parseInt(request.getParameter("q1"));
				double q2 = (double)Integer.parseInt(request.getParameter("q2"));
				double q3 = (double)Integer.parseInt(request.getParameter("q3"));
				double q4 = (double)Integer.parseInt(request.getParameter("q4"));
				double q5 = (double)Integer.parseInt(request.getParameter("q5"));
				double q6 = (double)Integer.parseInt(request.getParameter("q6"));
				double q7 = (double)Integer.parseInt(request.getParameter("q7"));
				double q8 = (double)Integer.parseInt(request.getParameter("q8"));
				double q9 = (double)Integer.parseInt(request.getParameter("q9"));
				double q10 = (double)Integer.parseInt(request.getParameter("q10"));
				double q11 = (double)Integer.parseInt(request.getParameter("q11"));
				double q12 = (double)Integer.parseInt(request.getParameter("q12"));
				double q13 = (double)Integer.parseInt(request.getParameter("q13"));
				double q14 = (double)Integer.parseInt(request.getParameter("q14"));
				double q15 = (double)Integer.parseInt(request.getParameter("q15"));
				double q16 = (double)Integer.parseInt(request.getParameter("q16"));
				double q17 = (double)Integer.parseInt(request.getParameter("q17"));
				double q18 = (double)Integer.parseInt(request.getParameter("q18"));
				double q19 = (double)Integer.parseInt(request.getParameter("q19"));
				double q20 = (double)Integer.parseInt(request.getParameter("q20"));
				double q21 = (double)Integer.parseInt(request.getParameter("q21"));
				double q22 = (double)Integer.parseInt(request.getParameter("q22"));
				double q23 = (double)Integer.parseInt(request.getParameter("q23"));
				double q24 = (double)Integer.parseInt(request.getParameter("q24"));
				double q25 = (double)Integer.parseInt(request.getParameter("q25"));


				double leader = (q1+q2+q3)/3.0;
				double creative = (q4+q5+q6+q7+q8+q9)/6.0;
				double communication = (q10+q11+q12+q13)/4.0;
				double adaptable = (q14+q15+q16+q17)/4.0;
				double collaboration = (q18+q19+q20)/3.0;
				double action = (q21+q22+q23+q24+q25)/5.0;

				HashMap<String,Double> wapScore = new HashMap<String,Double>();
				wapScore.put("Leadership",leader);
				wapScore.put("Creativity",creative);
				wapScore.put("Communication",communication);
				wapScore.put("Adaptibility",adaptable);
				wapScore.put("Collaboration",collaboration);
				wapScore.put("Action Oriented",action);
				session.setAttribute("candidateWapScore",wapScore);
				DatabaseController.getInstance().setCandidateWap((String) session.getAttribute("currentUser"),wapScore);

				CandidateHomeServlet servletObject = new CandidateHomeServlet();
				servletObject.doPost(request, response);
				response.sendRedirect(UrlProvider.getInstance().getBaseUrl(request)
						+ "/RezuMe/CandidateHomeServlet");
			} catch (NumberFormatException e) {
				System.out.println("WAP question not answered");
				htmlStringBuilder.append("<div id=\"body-WAPQuiz\">"+
						"<p class=\"body-WAPQuiz-header\">Work Attitudinal Profile Quiz<br><br></p>");
				htmlStringBuilder.append("<p class=\"body-skills-text\">Now it's time for you to assess your Work Attitudinal Profile.</p>\r\n\t\t\t<br>");
				htmlStringBuilder.append("\r\n\t\t\t<p class=\"body-skills-text\">Please answer the following questions as self-reflection.<br><br>Remember, there is no correct answer. Different organizations are looking for different traits.</p>");
				htmlStringBuilder.append("<span id=\"body-WAPQuiz-error\">One or more questions was not answered!</span></em></p>"+
						"<form action=\"CandidateWapServlet\" method=\"post\">"+
						"<div id=\"WAP-questions\">");
				htmlStringBuilder.append(questions);
				htmlStringBuilder.append("<br><br><input id=\"submit-button\" type=\"submit\" name=\"Submit1\" value=\"Submit\">"
						+"</div></form></div></div>");
			}
		} else if(candidateWapScore == null) {
			String skills = request.getParameter("skills");
			System.out.println("Setting newCandidateSkills to: " + skills);
			DatabaseController.getInstance().updateCandidateSkills((String) session.getAttribute("currentNewCandidate"), skills);
				htmlStringBuilder.append("<div id=\"body-WAPQuiz\">"+
						"<p class=\"body-WAPQuiz-header\">Work Attitudinal Profile Quiz<br><br></p>");
				htmlStringBuilder.append("<p class=\"body-skills-text\">Now it's time for you to assess your Work Attitudinal Profile.</p>\r\n\t\t\t<br>");
				htmlStringBuilder.append("\r\n\t\t\t<p class=\"body-skills-text\">Please answer the following questions as self-reflection.<br><br>Remember, there is no correct answer; different organizations are looking for different traits.<br><br></p>");
				htmlStringBuilder.append("<form action=\"CandidateWapServlet\" method=\"post\">"+
						"<div id=\"WAP-questions\">");
			htmlStringBuilder.append(questions);
			htmlStringBuilder.append("<br><br><input id=\"submit-button\" type=\"submit\" name=\"Submit1\" value=\"Submit\">"
					+"</div></form></div></div>");

		} else {
			CandidateHomeServlet servletObject = new CandidateHomeServlet();
			servletObject.doPost(request, response);
			response.sendRedirect(UrlProvider.getInstance().getBaseUrl(request)
					+ "/RezuMe/CandidateHomeServlet");
		}
		htmlStringBuilder.append(HtmlProvider.getInstance().getHtmlTail());
		out.println(htmlStringBuilder.toString());
		out.close();
	}

}
