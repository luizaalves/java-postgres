import dao.AluguelDAO;
import dao.ClienteDAO;
import dao.FilmeDAO;
import dao.jdbc.AluguelDAOImpl;
import dao.jdbc.ClienteDAOImpl;
import dao.jdbc.FilmeDAOImpl;
import entidades.Aluguel;
import entidades.Cliente;
import entidades.Filme;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import blocs.ClienteBlocs;


public class Main {

    public static void main(String[] args) {
    	ClienteBlocs.excluir();
    	System.out.println(ClienteBlocs.listar());
        Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://localhost/postgres", "postgres", "123");
            conn.setAutoCommit(false);

            //Demonstrar o funcionamento aqui
            ClienteDAO clienteDAO = new ClienteDAOImpl();
            FilmeDAO filmeDAO = new FilmeDAOImpl();
            AluguelDAO aluguelDAO = new AluguelDAOImpl();
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
            Date date= (Date)formatter.parse("09/08/2020");
            
            

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Fim do teste.");
    }
}