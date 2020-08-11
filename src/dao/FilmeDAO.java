package dao;

import entidades.Filme;

import java.sql.Connection;
import java.util.Collection;

public interface FilmeDAO {

    void insert(Filme filme) throws Exception;

    Integer getNextId() throws Exception;

    void edit(Filme filme) throws Exception;

    void delete(Integer idFilme) throws Exception;

    Filme find(Integer idFilme) throws Exception;

    Collection<Filme> list() throws Exception;

}