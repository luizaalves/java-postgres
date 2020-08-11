package dao.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import dao.AluguelDAO;
import dao.ClienteDAO;
import entidades.Aluguel;
import entidades.Cliente;
import entidades.Filme;

public class AluguelDAOImpl implements AluguelDAO{
	private ClienteDAOImpl clienteDAO = new ClienteDAOImpl();
	private FilmeDAOImpl filmeDAO = new FilmeDAOImpl();

	@Override
	public void insert(Connection conn, entidades.Aluguel aluguel) throws Exception {
		PreparedStatement myStmt = conn.prepareStatement("insert into en_aluguel (id_aluguel, id_cliente, data_aluguel, valor) values (?, ?, ?, ?)");

        Integer idAluguel = this.getNextId(conn);

        myStmt.setInt(1, idAluguel);
        myStmt.setInt(2, aluguel.getCliente().getIdCliente());
        myStmt.setDate(3,new java.sql.Date (aluguel.getDataAluguel().getTime()));
        myStmt.setFloat(4, aluguel.getValor());

        myStmt.execute();
        conn.commit();


        aluguel.setIdAluguel(idAluguel);
		
	}

	@Override
	public Integer getNextId(Connection conn) throws Exception {
		PreparedStatement myStmt = conn.prepareStatement("select nextval('seq_en_aluguel')");
        ResultSet rs = myStmt.executeQuery();
        rs.next();
        return rs.getInt(1);
	}
	//adicionar os filmes tbm!!!!
	@Override
	public void edit(Connection conn, entidades.Aluguel aluguel) throws Exception {
		for(Filme filme:aluguel.getFilmes()) {
			insertFilmesAlugados(conn, aluguel.getIdAluguel(), filme.getIdFilme());
		}
		PreparedStatement myStmt = conn.prepareStatement("update en_aluguel set id_cliente = (?), data_aluguel = (?), "
				+ "valor = (?) where id_aluguel = (?)");
	
		myStmt.setInt(1, aluguel.getCliente().getIdCliente());
        myStmt.setDate(2,(Date) aluguel.getDataAluguel());
        myStmt.setFloat(3, aluguel.getValor());
		myStmt.setInt(4, aluguel.getIdAluguel());
		
        myStmt.execute();
        conn.commit();
		
	}
	
	private void insertFilmesAlugados(Connection conn, Integer idAluguel, Integer idFilme) throws SQLException {
		PreparedStatement myStmt = conn.prepareStatement("insert into re_aluguel_filme (id_aluguel, id_filme) values (?, ?)");

        myStmt.setInt(1, idAluguel);
        myStmt.setInt(2, idFilme);

        myStmt.execute();
        conn.commit();
	}
	
	@Override
	public void delete(Connection conn, entidades.Aluguel aluguel) throws Exception {
		deleteFilmesAlugados(conn, aluguel.getIdAluguel());
		PreparedStatement myStmt = conn.prepareStatement("delete from en_aluguel where id_aluguel = ?");

        myStmt.setInt(1, aluguel.getIdAluguel());

        myStmt.execute();
        conn.commit();
		
	}
	
	private void deleteFilmesAlugados(Connection conn, Integer idAluguel) throws Exception {
		PreparedStatement myStmt = conn.prepareStatement("delete from re_aluguel_filme where id_aluguel = ?");

        myStmt.setInt(1, idAluguel);

        myStmt.execute();
        conn.commit();
		
	}
	
	@Override
	public entidades.Aluguel find(Connection conn, Integer idAluguel) throws Exception {
		PreparedStatement myStmt = conn.prepareStatement("select * from en_aluguel where id_aluguel = ?");

        myStmt.setInt(1, idAluguel);

        ResultSet myRs = myStmt.executeQuery();

        if (!myRs.next()) {
            return null;
        }
		
		Integer idCliente = myRs.getInt("id_cliente");
		Cliente cliente = clienteDAO.find(conn, idCliente);
		java.util.Date dataAluguel = myRs.getDate("data_aluguel");
		float valor = myRs.getFloat("valor");
		Collection<Filme> filmes = findMovies(conn, idAluguel);
		List<Filme> filmesAdd = new ArrayList<Filme>();
		if(filmes!=null)filmesAdd.addAll(filmes);
        return new Aluguel(idAluguel, filmesAdd, cliente, dataAluguel, valor);
	}

	@Override
	public Collection<entidades.Aluguel> list(Connection conn) throws Exception {
		PreparedStatement myStmt = conn.prepareStatement("select * from en_aluguel");
        ResultSet myRs = myStmt.executeQuery();

        Collection<Aluguel> items = new ArrayList<>();

        while (myRs.next()) {
            Integer idAluguel = myRs.getInt("id_aluguel");
            Integer idCliente = myRs.getInt("id_cliente");
    		Cliente cliente = clienteDAO.find(conn, idCliente);
    		java.util.Date dataAluguel = myRs.getDate("data_aluguel");
    		float valor = myRs.getFloat("valor");
    		Collection<Filme> filmes = findMovies(conn, idAluguel);
    		List<Filme> filmesAdd = new ArrayList<Filme>();
    		if(filmes!=null)filmesAdd.addAll(filmes);
    		
            items.add(new Aluguel(idAluguel, filmesAdd, cliente, dataAluguel, valor));
        }

        return items;
	}
	
	private Collection<entidades.Filme> findMovies(Connection conn, Integer idAluguel ) throws Exception {
		PreparedStatement myStmt = conn.prepareStatement("select * from re_aluguel_filme where id_aluguel = ?");
		myStmt.setInt(1, idAluguel);
		ResultSet myRs = myStmt.executeQuery();

        Collection<Filme> items = new ArrayList<>();

        while (myRs.next()) {

			Integer idFilme = myRs.getInt("id_filme");
			System.out.println(idAluguel);
			Filme filme = filmeDAO.find(conn, idFilme);
	        if(filme!=null) items.add(filme);
        }
        return items;
	}

}
