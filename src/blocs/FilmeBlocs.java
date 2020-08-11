package blocs;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import dao.FilmeDAO;
import dao.jdbc.FilmeDAOImpl;
import entidades.Filme;

public class FilmeBlocs {
	private static FilmeDAO filmeDAO = new FilmeDAOImpl();
	
	public static String listar() throws Exception {
		StringBuilder sb = new StringBuilder();
		StringBuilder firula = new StringBuilder();
		for(int i=0;i<105;i++) {
			firula.append('-');
		}
		sb.append(firula).append("\n");
        sb.append(String.format("|%-5s|%-15s|%-35s|%-45s|\n", "ID", "Data de lançam.","Nome","Descrição"));
        sb.append(firula).append("\n");
        
        Collection<Filme> colecaoFilmes = filmeDAO.list();
        List<Filme> filmes = new ArrayList<Filme>();
        if(colecaoFilmes!=null)filmes.addAll(colecaoFilmes);
        for(Filme filme:filmes) {
        	sb.append(filme.toString()).append("\n");
        }
        if(!filmes.isEmpty())sb.append(firula).append("\n");
        
        return sb.toString();

    }
	
	public static void excluir() throws Exception {
        System.out.println(listar());
        Scanner sc = new Scanner(System.in);
        System.out.println("Digite o ID que deseja excluir: ");
        System.out.print("> ");
        int idFilme = sc.nextInt();
        filmeDAO.delete(idFilme);
	}
	
	public static Integer adicionar() throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("Digite o nome do filme: ");
        System.out.print("> ");
        String nome = sc.nextLine();
        System.out.println("Data de lançamento (ex. dd/MM/yyyy): ");
        System.out.print("> ");
        String data = sc.nextLine();
        System.out.println("Descrição do filme: ");
        System.out.print("> ");
        String descricao = sc.nextLine();
        
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
        Date date = (Date)formatter.parse(data);
        
        
        Filme filme = new Filme(1,date,nome,descricao);
        filmeDAO.insert(filme);
        return filmeDAO.getNextId();
	}
	
	public static void editar() throws Exception {
        System.out.println(listar());
        Scanner sc = new Scanner(System.in);
        System.out.println("Digite o ID do filme que deseja editar: ");
        System.out.print("> ");
        int idFilme = sc.nextInt();
        sc.nextLine();
        Filme filme = filmeDAO.find(idFilme);
        if(filme!=null) {
        	Integer op = 0;
        	while(op!=4) {
        		System.out.println("Digite a opção do que deseja alterar:\n1. Nome\n2. Data;\n3. Descrição;\n4. Gravar e sair.");
        		System.out.print("> ");
        		op = sc.nextInt();
        		sc.nextLine();
        		
        		switch (op) {
					case 1:
						System.out.println("Digite o nome do filme: ");
						System.out.print("> ");
						String nome = sc.nextLine();
						filme.setNome(nome);
						break;
					case 2:
						System.out.println("Digite a nova data de lançamento (ex. dd/MM/yyyy): ");
						System.out.print("> ");
						String data = sc.nextLine();
						SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
				        Date date = (Date)formatter.parse(data);
				        filme.setDataLancamento(date);
				        
						break;
					case 3:
						System.out.println("Descrição do filme: ");
						System.out.print("> ");
						String descricao = sc.nextLine();
						filme.setDescricao(descricao);
						break;
					
        		}
        		filmeDAO.edit(filme);
        	}
        }
        else System.err.println("ID incorreto!");
	}
	
}
