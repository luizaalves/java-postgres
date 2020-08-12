package blocs;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import dao.AluguelDAO;
import dao.ClienteDAO;
import dao.FilmeDAO;
import dao.jdbc.AluguelDAOImpl;
import dao.jdbc.ClienteDAOImpl;
import dao.jdbc.FilmeDAOImpl;
import entidades.Aluguel;
import entidades.Cliente;
import entidades.Filme;


public class AluguelBlocs {
	private static AluguelDAO aluguelDAO = new AluguelDAOImpl();
	private static FilmeDAO filmeDAO = new FilmeDAOImpl();
	private static ClienteDAO clienteDAO = new ClienteDAOImpl();
	
	public static String listar() throws Exception {
		StringBuilder sb = new StringBuilder();
		StringBuilder firula = new StringBuilder();
		for(int i=0;i<102;i++) {
			firula.append('-');
		}
		sb.append(firula).append("\n");
        sb.append(String.format("|%-5s|%-20s|%-15s|%-6s|%-50s|\n", "ID", "Cliente","Data","Valor","Filmes alugados"));
        sb.append(firula).append("\n");
    
        Collection<Aluguel> colecaoAlugueis = aluguelDAO.list();
        List<Aluguel> alugueis = new ArrayList<Aluguel>();
        if(colecaoAlugueis!=null)alugueis.addAll(colecaoAlugueis);
        for(Aluguel aluguel:alugueis) {
        	sb.append(aluguel.toString()).append("\n");
        }
        if(!alugueis.isEmpty())sb.append(firula).append("\n");
        return sb.toString();

    }
	
	public static void excluir() throws Exception {
        System.out.println(listar());
        Scanner sc = new Scanner(System.in);
        System.out.println("Digite o ID que deseja excluir: ");
        System.out.print("> ");
        int idAluguel = sc.nextInt();
        Aluguel aluguel = aluguelDAO.find(idAluguel);
        if(aluguel!=null) aluguelDAO.delete(aluguel);
        else System.err.println("ID incorreto!");
        
	}
	
	public static Integer adicionar() throws Exception {		
        Scanner sc = new Scanner(System.in);
        System.out.println(ClienteBlocs.listar());
        System.out.println("Digite o ID do cliente: ");
        System.out.print("> ");
        Integer idCliente = sc.nextInt();
        sc.nextLine();
        Cliente cliente = clienteDAO.find(idCliente);
        while(cliente==null) {
        	System.out.println(ClienteBlocs.listar());
            System.out.println("ID incorreto\nDigite novamente o ID do cliente: ");
            System.out.print("> ");
            idCliente = sc.nextInt();
            sc.nextLine();
            cliente = clienteDAO.find(idCliente);
        }
        System.out.println("Data do aluguel (ex. dd/MM/yyyy): ");
        System.out.print("> ");
        String data = sc.nextLine();
        System.out.println("Valor da locação (ex. 1.99): ");
        System.out.print("> ");
        String valor = sc.nextLine();
    	System.out.println("Qual a quantidade de filmes que serão locados?");
    	System.out.print("> ");
    	Integer qtia = sc.nextInt();
    	sc.nextLine();
    	System.out.println(FilmeBlocs.listar());
    	List<Filme> filmes = new ArrayList<Filme>();
    	for(int i=0;i<qtia;i++) {
    		System.out.println(String.format("Digite o %dº ID do filme: ",i+1));
    		System.out.print("> ");
    		Integer idFilme = sc.nextInt();
        	sc.nextLine();
        	Filme filmeLocado = filmeDAO.find(idFilme);
    		if(filmeLocado!=null)filmes.add(filmeLocado);
    	}
    	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
        Date date = (Date)formatter.parse(data);
        System.out.println(filmes.size());
        aluguelDAO.insert(new Aluguel(aluguelDAO.getNextId()+1,filmes,cliente,date,Float.parseFloat(valor)));
        return aluguelDAO.getNextId()+1;
	}
	
	public static void editar() throws Exception {
        System.out.println(listar());
        Scanner sc = new Scanner(System.in);
        System.out.println("Digite o ID do aluguel que deseja editar: ");
        System.out.print("> ");
        int idAluguel = sc.nextInt();
        sc.nextLine();
        Aluguel aluguel = aluguelDAO.find(idAluguel);
        List<Filme> filmes = aluguel.getFilmes();
        if(filmes==null||filmes.isEmpty()) filmes=new ArrayList<Filme>();
        
        if(aluguel!=null) {
        	Integer op = 0;
        	while(op!=5) {
        		System.out.println("Digite a opção do que deseja alterar:\n1. Cliente\n2. Data;\n3. Valor;\n4. Filmes;\n5. Gravar e sair.");
        		System.out.print("> ");
        		op = sc.nextInt();
        		sc.nextLine();
        		
        		switch (op) {
					case 1:
						System.out.println(ClienteBlocs.listar());
						System.out.println("Digite o ID do cliente novo: ");
						System.out.print("> ");
						Integer idCliente = sc.nextInt();
						sc.nextLine();
						Cliente cliente = clienteDAO.find(idCliente);
						if(cliente!=null) aluguel.setCliente(cliente);
						break;
					case 2:
						System.out.println("Digite a nova data (ex. dd/MM/yyyy): ");
						System.out.print("> ");
						String data = sc.nextLine();
						SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
				        Date date = (Date)formatter.parse(data);
				        aluguel.setDataAluguel(date);
				        System.out.println(aluguel.getDataAluguel()+" "+aluguel.getValor());
						break;
					case 3:
						System.out.println("Digite o novo valor (ex. 1.99): ");
						System.out.print("> ");
						String valor = sc.nextLine();
						aluguel.setValor(Float.parseFloat(valor));
						break;
					case 4:
						System.out.println(FilmeBlocs.listar());
						System.out.println("Digite o ID do filme desejado: ");
						System.out.print("> ");
						Integer idFilme = sc.nextInt();
						sc.nextLine();
						Filme filme = filmeDAO.find(idFilme);
						if(filme!=null)filmes.add(filme);
						break;
        		}
        		aluguelDAO.edit(aluguel);
        	}
        	
        }
        else System.err.println("ID incorreto!");
	}
	
}
