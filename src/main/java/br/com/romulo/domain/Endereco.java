package br.com.romulo.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Endereco {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
    private String rua;
    private String logradouro;
    private String complemento;
    private Integer numero;
    
    Endereco() {

    }
    
    public Endereco(Integer id, String rua, String logradouro, String complemento, Integer numero) {
        this.id = id;
        this.rua = rua;
        this.logradouro = logradouro;
        this.complemento = complemento;
        this.numero = numero;
    }

    public Integer getId() {
        return id;
    }

    public String getRua() {
        return rua;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public String getComplemento() {
        return complemento;
    }

    public Integer getNumero() {
        return numero;
    }
}
