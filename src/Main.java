import java.util.Scanner;

import blocs.AluguelBlocs;
import blocs.ClienteBlocs;
import blocs.FilmeBlocs;

public class Main {
	 private static String menu = "..:: Gestão de Aluguéis de filmes ::..\n"+
	            "1 - Cliente\n"+
	            "2 - Filmes\n"+
	            "3 - Alugueis de filmes\n"+
	            "4 - Sair do programa";
	 private static String cliente = "..:: Gestão de clientes ::..\n"+
			 	"1 - Cadastrar cliente\n"+
	            "2 - Excluir cliente\n"+
			 	"3 - Editar cliente específico\n"+
	            "4 - Listar todos os clientes\n"+
	            "5 - Voltar ao menu anterior";
	 private static String filme = "..:: Gestão de filmes ::..\n"+
			 	"1 - Cadastrar filme\n"+
	            "2 - Excluir filme\n"+
	            "3 - Editar filme específico\n"+
	            "4 - Listar todos os filmes\n"+
	            "5 - Voltar ao menu anterior";
	 private static String aluguel = "..:: Gestão de alugueis de filmes ::..\n"+
			 	"1 - Cadastrar aluguel de filmes\n"+
	            "2 - Excluir aluguel de filmes\n"+
	            "3 - Editar aluguel específico\n"+
	            "4 - Listar todos os alugueis de filmes\n"+
	            "5 - Voltar ao menu anterior";

    public static void main(String[] args) throws Exception {
    	Integer op = 0;
    	Integer opCliente = 0;
    	Integer opFilme = 0;
    	Integer opAluguel = 0;
    	
    	Scanner sc = new Scanner(System.in);
    	while(op!=4) {
    		System.out.println(menu);
    		System.out.print("> ");
    		op = sc.nextInt();
    		sc.nextLine();
    		switch (op) {
			case 1:
				while(opCliente!=5) {
					System.out.println(cliente);
					System.out.print("> ");
					opCliente = sc.nextInt();
					if(sc.hasNextLine()) sc.nextLine(); 
					menuCliente(opCliente);
				}
				break;

			case 2:
				while(opFilme!=5) {
					System.out.println(filme);
					System.out.print("> ");
					opFilme = sc.nextInt();
					if(sc.hasNextLine()) sc.nextLine();
					menuFilme(opFilme);
				}
				break;
			case 3:
				while(opAluguel!=5) {
					System.out.println(aluguel);
					System.out.print("> ");
					opAluguel = sc.nextInt();
					if(sc.hasNextLine()) sc.nextLine();
					menuAluguel(opAluguel);
				}
				
				break;
			}
    	}
    	
        System.out.println("Fim do teste.");
    }
    private static void menuAluguel(Integer op) throws Exception {
    	switch (op) {
			case 1:
				AluguelBlocs.adicionar();
				break;
	
			case 2:
				AluguelBlocs.excluir();
				break;
			
			case 3: 
				AluguelBlocs.editar();
				break;
			
			case 4:
				System.out.println(AluguelBlocs.listar());
				break;
		}
	}
	private static void menuFilme(Integer op) throws Exception {
    	switch (op) {
			case 1:
				FilmeBlocs.adicionar();
				break;
	
			case 2:
				FilmeBlocs.excluir();
				break;
				
			case 3:
				FilmeBlocs.editar();
				break;
			
			case 4:
				System.out.println(FilmeBlocs.listar());
				break;
		}
	}
	private static void menuCliente(Integer op) throws Exception {
    	switch (op) {
			case 1:
				ClienteBlocs.adicionar();
				break;
	
			case 2:
				ClienteBlocs.excluir();
				break;
				
			case 3:
				ClienteBlocs.editar();
				break;
			
			case 4:
				System.out.println(ClienteBlocs.listar());
				break;
    	}
	}
    
}