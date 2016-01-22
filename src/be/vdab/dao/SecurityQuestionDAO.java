package be.vdab.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import be.vdab.entities.SecurityQuestion;

public class SecurityQuestionDAO extends AbstractDAO  {
	private static final String SELECT_SECURITY_QUESTION_COUNT = "select count(*) from gastenboek_security_questions";
	private static final String SELECT_SECURITY_QUESTION = "select vraag, antwoord from gastenboek_security_questions where id = ?";
	
	public SecurityQuestion getSecurityQuestion(int id){			
		try (Connection connection = dataSource.getConnection();
				Statement countstatement = connection.createStatement();
				PreparedStatement statement = connection.prepareStatement(SELECT_SECURITY_QUESTION)){
			statement.setInt(1, id);
			try (ResultSet resultSet = statement.executeQuery()){
				resultSet.next();
				return new SecurityQuestion(id, resultSet.getString("vraag"),  resultSet.getString("antwoord"));								
			}			
		} catch (SQLException ex){
			throw new DAOException(ex);
		}				
	}
	
	public int getCount(){
		try (Connection connection = dataSource.getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(SELECT_SECURITY_QUESTION_COUNT)){
			resultSet.next();
			return resultSet.getInt(1);
			
		} catch (SQLException ex){
			throw new DAOException(ex);
		}
	}

}
