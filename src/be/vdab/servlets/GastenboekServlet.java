package be.vdab.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import be.vdab.dao.GastenboekEntryDAO;
import be.vdab.dao.SecurityQuestionDAO;
import be.vdab.entities.GastenboekEntry;
import be.vdab.entities.SecurityQuestion;

/**
 * Servlet implementation class GastenboekServlet
 */
@WebServlet("/index.htm")
public class GastenboekServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/JSP/gastenboek.jsp";

	private final transient GastenboekEntryDAO gastenboekEntryDAO = new GastenboekEntryDAO();
	private final transient SecurityQuestionDAO securityQuestionDAO = new SecurityQuestionDAO();

	@Resource(name = GastenboekEntryDAO.JNDI_NAME)
	void setDataSource(DataSource dataSource) {
		gastenboekEntryDAO.setDataSource(dataSource);
		securityQuestionDAO.setDataSource(dataSource);
		
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("gastenboek", gastenboekEntryDAO.findAll());	
		
		SecurityQuestion question = securityQuestionDAO.getSecurityQuestion(new Random().nextInt(securityQuestionDAO.getCount()) + 1);
		request.setAttribute("vraag", question);
		//request.setAttribute("vraagID", question.getId());
		request.getRequestDispatcher(VIEW).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String naam = request.getParameter("naam");
		String bericht = request.getParameter("bericht");		
		Map<String, String> fouten = new HashMap<>();
		
		SecurityQuestion question = securityQuestionDAO.getSecurityQuestion(Integer.parseInt(request.getParameter("vraagID")));	
		if (!question.isAntwoordJuist(request.getParameter("antwoord").trim())) {
			fouten.put("security",
					"Gelieve het juiste antwoord te geven in de security question!");
		}
		if (!GastenboekEntry.isNaamValid(naam) || !GastenboekEntry.isBerichtValid(bericht)) {
			fouten.put("entry", "Gelieve naam en bericht in te vullen aub!");
		}
		
		if (fouten.isEmpty()) {
			GastenboekEntry gastenboekEntry = new GastenboekEntry(naam, bericht);
			gastenboekEntryDAO.create(gastenboekEntry);
			response.sendRedirect(response.encodeRedirectURL(request
					.getRequestURI()));
		} else {
			request.setAttribute("vraag", question.getVraag());			
			request.setAttribute("vraagID", question.getId());
			request.setAttribute("fouten", fouten);
			request.setAttribute("gastenboek", gastenboekEntryDAO.findAll());
			request.getRequestDispatcher(VIEW).forward(request, response);
		}

	}

}
