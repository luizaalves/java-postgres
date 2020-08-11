package dao;

import entidades.Cliente;

import java.sql.Connection;
import java.util.Collection;

public interface ClienteDAO {

    void insert(Cliente cliente) throws Exception;

    Integer getNextId() throws Exception;

    void edit(Cliente cliente) throws Exception;

    void delete(Integer idCliente) throws Exception;

    Cliente find(Integer idCliente) throws Exception;

    Collection<Cliente> list() throws Exception;

}