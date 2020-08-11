package blocs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

import dao.ClienteDAO;
import dao.jdbc.ClienteDAOImpl;
import entidades.Cliente;

public class ClienteBlocs {
	private static ClienteDAO clienteDAO = new ClienteDAOImpl();
	
	public static String listar() throws Exception {
		StringBuilder sb = new StringBuilder();
		StringBuilder firula = new StringBuilder();
		for(int i=0;i<28;i++) {
			firula.append('-');
		}
		sb.append(firula).append("\n");
        sb.append(String.format("|%-5s|%-20s|\n", "ID", "Nome"));
        sb.append(firula).append("\n");
        
        Collection<Cliente> colecaoClientes = clienteDAO.list();
        List<Cliente> clientes = new ArrayList<Cliente>();
        if(colecaoClientes!=null)clientes.addAll(colecaoClientes);
        for(Cliente cliente:clientes) {
        	sb.append(cliente.toString()).append("\n");
        	
        }
        if(!clientes.isEmpty())sb.append(firula).append("\n");

        return sb.toString();

    }
	
	public static void excluir() throws Exception {
		
        System.out.println(listar());
        Scanner sc = new Scanner(System.in);
        System.out.println("Digite o ID que deseja excluir: ");
        System.out.print("> ");
        int idCliente = sc.nextInt();
        clienteDAO.delete(idCliente);

	}
	
	public static Integer adicionar() throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("Digite o nome do cliente: ");
        System.out.print("> ");
        String nome = sc.nextLine();
        Cliente cliente = new Cliente(nome);
        clienteDAO.insert(cliente);
        return clienteDAO.getNextId();
	}
	
	public static void editar() throws Exception {
        System.out.println(listar());
        Scanner sc = new Scanner(System.in);
        System.out.println("Digite o ID do cliente que deseja editar: ");
        System.out.print("> ");
        int idCliente = sc.nextInt();
        sc.nextLine();
        Cliente cliente = clienteDAO.find(idCliente);
        if(cliente!=null) {
            System.out.println("Digite o novo nome do cliente: ");
            System.out.print("> ");
            String nome = sc.nextLine();
            cliente.setNome(nome);
            clienteDAO.edit(cliente);
        }
        else System.err.println("ID incorreto!");

	}
	
}
