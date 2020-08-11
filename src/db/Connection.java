package db;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import dao.AluguelDAO;
import dao.ClienteDAO;
import dao.FilmeDAO;
import dao.jdbc.AluguelDAOImpl;
import dao.jdbc.ClienteDAOImpl;
import dao.jdbc.FilmeDAOImpl;

public abstract class Connection {
	private static java.sql.Connection conn;
    
	public static synchronized java.sql.Connection getDBConnection() {
		try {
	        Class.forName("org.postgresql.Driver");
	        conn = DriverManager.getConnection("jdbc:postgresql://localhost/postgres", "postgres", "123");
	        conn.setAutoCommit(false);
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	    }
		return conn;
	}

}
