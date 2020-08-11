package blocs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

import dao.ClienteDAO;
import dao.jdbc.ClienteDAOImpl;
import entidades.Cliente;

public class ClienteBlocs {
	private static ClienteDAO clienteDAO = new ClienteDAOImpl();
	
	public static String listar() {
		Connection conn = null;
		StringBuilder sb = new StringBuilder();
		StringBuilder firula = new StringBuilder();
		for(int i=0;i<28;i++) {
			firula.append('-');
		}
		sb.append(firula).append("\n");
        sb.append(String.format("|%-5s|%-20s|\n", "ID", "Nome"));
        sb.append(firula).append("\n");
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://localhost/postgres", "postgres", "123");
            conn.setAutoCommit(false);
            Collection<Cliente> colecaoClientes = clienteDAO.list(conn);
            List<Cliente> clientes = new ArrayList<Cliente>();
            if(colecaoClientes!=null)clientes.addAll(colecaoClientes);
            for(Cliente cliente:clientes) {
            	sb.append(cliente.toString()).append("\n");
            	
            }
            if(!clientes.isEmpty())sb.append(firula).append("\n");
        }catch (Exception e) {
            e.printStackTrace();

        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();

    }
	
	public static void excluir() {
		Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://localhost/postgres", "postgres", "123");
            conn.setAutoCommit(false);
            System.out.println(listar());
            Scanner sc = new Scanner(System.in);
            System.out.println("Digite o ID que deseja excluir: ");
            int idCliente = sc.nextInt();
            clienteDAO.delete(conn, idCliente);
        }catch (Exception e) {
            e.printStackTrace();

        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
	}
	
	public static Integer adicionar() {
		Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://localhost/postgres", "postgres", "123");
            conn.setAutoCommit(false);
            Scanner sc = new Scanner(System.in);
            System.out.println("Digite o nome do cliente: ");
            String nome = sc.nextLine();
            Cliente cliente = new Cliente(nome);
            clienteDAO.insert(conn, cliente);
            return clienteDAO.getNextId(conn);
        }catch (Exception e) {
            e.printStackTrace();

        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
	}
	
	public void editar() {
		Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://localhost/postgres", "postgres", "123");
            conn.setAutoCommit(false);
            System.out.println(listar());
            Scanner sc = new Scanner(System.in);
            System.out.println("Digite o ID do cliente que deseja editar: ");
            int idCliente = sc.nextInt();
            Cliente cliente = clienteDAO.find(conn, idCliente);
            if(cliente!=null) {
	            System.out.println("Digite o novo nome do cliente: ");
	            String nome = sc.nextLine();
	            cliente.setNome(nome);
	            clienteDAO.edit(conn, cliente);
            }
            else System.err.println("ID incorreto!");
            
        }catch (Exception e) {
            e.printStackTrace();

        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }	
	}
	
}
