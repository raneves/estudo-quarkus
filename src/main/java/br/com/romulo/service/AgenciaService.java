package br.com.romulo.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import br.com.romulo.domain.Agencia;
import br.com.romulo.domain.http.AGenciaHttp;
import br.com.romulo.domain.http.SituacaoCadastral;
import br.com.romulo.exception.AgenciaNaoAtivaOuNaoEncontradaException;
import br.com.romulo.service.http.SituacaoCadastralHttpService;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class AgenciaService {
	@RestClient
    SituacaoCadastralHttpService situacaoCadastralHttpService;

    private final List<Agencia> agencias = new ArrayList<>();

    public void cadastrar(Agencia agencia) {
        AGenciaHttp agenciaHttp = situacaoCadastralHttpService.buscarPorCnpj(agencia.getCnpj());
        if (agenciaHttp != null && agenciaHttp.getSituacaoCadastral() == SituacaoCadastral.ATIVO) {
            agencias.add(agencia);
        } else {
            throw new AgenciaNaoAtivaOuNaoEncontradaException();
        }
    }

    public Agencia buscarPorId(Integer id) {
        return agencias.stream().filter(agencia -> agencia.getId().equals(id)).toList().getFirst();
    }

    public void deletar(Integer id) {
        agencias.removeIf(agencia -> agencia.getId().equals(id));
    }

    public void alterar(Agencia agencia) {
        deletar(agencia.getId());
        agencias.add(agencia);
    }
}
