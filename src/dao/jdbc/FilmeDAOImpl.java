package dao.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

import dao.FilmeDAO;
import entidades.Filme;

public class FilmeDAOImpl implements FilmeDAO  {

	@Override
	public void insert(Connection conn, entidades.Filme filme) throws Exception {
		PreparedStatement myStmt = conn.prepareStatement("insert into en_filme (id_filme, data_lancamento, nome, descricao) values (?, ?, ?, ?)");

        Integer idFilme = this.getNextId(conn);

        myStmt.setInt(1, idFilme);
        myStmt.setDate(2, new java.sql.Date( filme.getDataLancamento().getTime()));
        myStmt.setString(3, filme.getNome());
        myStmt.setString(4, filme.getDescricao());

        myStmt.execute();
        conn.commit();


        filme.setIdFilme(idFilme);
		
	}

	@Override
	public Integer getNextId(Connection conn) throws Exception {
		PreparedStatement myStmt = conn.prepareStatement("select nextval('seq_en_filme')");
        ResultSet rs = myStmt.executeQuery();
        rs.next();
        return rs.getInt(1);
	}

	@Override
	public void edit(Connection conn, entidades.Filme filme) throws Exception {
		PreparedStatement myStmt = conn.prepareStatement("update en_filme set nome = (?), data_lancamento = (?), "
				+ " descricao = (?) where id_filme = (?)");

        myStmt.setString(1, filme.getNome());
        myStmt.setDate(2,(Date) filme.getDataLancamento());
        myStmt.setString(3, filme.getDescricao());
		myStmt.setInt(4, filme.getIdFilme());
		
        myStmt.execute();
        conn.commit();
		
	}

	//deletar também aluguel e re_aluguel relacionados ao filme
	@Override
	public void delete(Connection conn, Integer idFilme) throws Exception {
		PreparedStatement myStmt = conn.prepareStatement("delete from en_filme where id_filme = ?");

        myStmt.setInt(1, idFilme);

        myStmt.execute();
        conn.commit();

		
	}

	@Override
	public entidades.Filme find(Connection conn, Integer idFilme) throws Exception {
		PreparedStatement myStmt = conn.prepareStatement("select * from en_filme where id_filme = ?");

        myStmt.setInt(1, idFilme);

        ResultSet myRs = myStmt.executeQuery();

        if (!myRs.next()) {
            return null;
        }

        String nome = myRs.getString("nome");
        java.util.Date dataLancamento = myRs.getDate("data_lancamento");
        String descricao = myRs.getString("descricao");
        return new Filme(idFilme, dataLancamento, nome, descricao);
	}

	@Override
	public Collection<entidades.Filme> list(Connection conn) throws Exception {
		PreparedStatement myStmt = conn.prepareStatement("select * from en_filme order by nome");
        ResultSet myRs = myStmt.executeQuery();

        Collection<Filme> items = new ArrayList<>();

        while (myRs.next()) {
            Integer idFilme = myRs.getInt("id_filme");
            String nome = myRs.getString("nome");
            java.util.Date dataLancamento = myRs.getDate("data_lancamento");
            String descricao = myRs.getString("descricao");
            
            items.add(new Filme(idFilme, dataLancamento, nome, descricao));
        }

        return items;
	}

}
