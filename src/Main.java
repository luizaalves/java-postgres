import dao.AluguelDAO;
import dao.ClienteDAO;
import dao.FilmeDAO;
import dao.jdbc.AluguelDAOImpl;
import dao.jdbc.ClienteDAOImpl;
import dao.jdbc.FilmeDAOImpl;
import entidades.Cliente;
import entidades.Filme;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Main {

    public static void main(String[] args) {
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
            Date date= (Date)formatter.parse("19/11/2022");
            //System.out.println(date);
            //Filme filme = new Filme(19, date, "a fuga das galinhas", "Classificação livre");
            //filmeDAO.insert(conn, filme);
            //filmeDAO.edit(conn, filme);
            Filme filmeId = filmeDAO.find(conn, 19);
            filmeId.setDescricao("Classificação livre");
            filmeId.setNome("Fuga das galinhas");
            filmeDAO.edit(conn, filmeId);
            
            //System.out.println(filmeId.getDataLancamento());
            System.out.println(aluguelDAO.find(conn, 3).toString());

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