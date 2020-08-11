package dao;

import entidades.Aluguel;

import java.sql.Connection;
import java.util.Collection;

public interface AluguelDAO {

    void insert(Aluguel aluguel) throws Exception;

    Integer getNextId() throws Exception;

    void edit(Aluguel aluguel) throws Exception;

    void delete(Aluguel aluguel) throws Exception;

    Aluguel find(Integer idAluguel) throws Exception;

    Collection<Aluguel> list() throws Exception;

}