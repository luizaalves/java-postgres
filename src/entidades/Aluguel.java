package entidades;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Aluguel {
    private Integer idAluguel;
    private List<Filme> filmes;
    private Cliente cliente;
    private Date dataAluguel;
    private float valor;

    public Aluguel() {
    }

    public Aluguel(Integer idAluguel, List<Filme> filmes, Cliente cliente, Date dataAluguel, float valor) {
        this.idAluguel = idAluguel;
        this.filmes = filmes;
        this.cliente = cliente;
        this.dataAluguel = dataAluguel;
        this.valor = valor;
    }

    public Integer getIdAluguel() {
        return idAluguel;
    }

    public Aluguel setIdAluguel(Integer idAluguel) {
        this.idAluguel = idAluguel;
        return this;
    }

    public List<Filme> getFilmes() {
        return filmes;
    }

    public Aluguel setFilmes(List<Filme> filmes) {
        this.filmes = filmes;
        return this;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Aluguel setCliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public Date getDataAluguel() {
        return dataAluguel;
    }

    public Aluguel setDataAluguel(Date dataAluguel) {
        this.dataAluguel = dataAluguel;
        return this;
    }

    public float getValor() {
        return valor;
    }

    public Aluguel setValor(float valor) {
        this.valor = valor;
        return this;
    }
    @Override
    public String toString() {
    	return String.format("|%-5d|%-20s|%-15s|%-6s|%-50s|",idAluguel,cliente.getNome(),dataAluguel.toString(),valor,toStringFilmes());
    }
    
    public String toStringFilmes(){
        StringBuilder sb = new StringBuilder();
        for (Filme filme:filmes){
            sb.append(filme.getNome()).append(", ");
            
        }
        return sb.toString();
    }

}
