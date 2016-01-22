package be.vdab.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import be.vdab.entities.GastenboekEntry;

public class GastenboekEntryDAO extends AbstractDAO {
	private static final String BEGIN_SELECT = "select id, naam, bericht from gastenboek";
	private static final String INSERT_MESSAGE = "INSERT INTO gastenboek(naam, bericht) values (?,?)";	
	
	private GastenboekEntry resultSetRijNaarBericht(ResultSet resultset) throws SQLException{
		return new GastenboekEntry(resultset.getLong("id"), resultset.getString("naam"), resultset.getString("bericht"));
	}	
	
	public List<GastenboekEntry> findAll(){
		try (Connection connection = dataSource.getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(BEGIN_SELECT)){
			List<GastenboekEntry> gastenboek = new ArrayList<>();
			while (resultSet.next()){
				gastenboek.add(resultSetRijNaarBericht(resultSet));
			}
			return gastenboek;			
		} catch (SQLException ex){
			throw new DAOException(ex);
		}
	}
	
	public void create(GastenboekEntry bericht){
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(
						INSERT_MESSAGE, Statement.RETURN_GENERATED_KEYS)) {
			statement.setString(1, bericht.getNaam());
			statement.setString(2, bericht.getBericht());
			statement.executeUpdate();
			try (ResultSet resultSet = statement.getGeneratedKeys()) {
				resultSet.next();
				bericht.setId(resultSet.getLong(1));
			}
		} catch (SQLException ex) {
			throw new DAOException(ex);
		}
	}
}
