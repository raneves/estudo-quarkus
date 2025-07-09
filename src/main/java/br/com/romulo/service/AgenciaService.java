package br.com.romulo.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import br.com.romulo.domain.Agencia;
import br.com.romulo.domain.http.AGenciaHttp;
import br.com.romulo.domain.http.SituacaoCadastral;
import br.com.romulo.exception.AgenciaNaoAtivaOuNaoEncontradaException;
import br.com.romulo.repository.AgenciaRepository;
import br.com.romulo.service.http.SituacaoCadastralHttpService;
import jakarta.transaction.Transactional;
import io.quarkus.logging.Log;
import io.micrometer.core.instrument.MeterRegistry;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class AgenciaService {
	@Inject
	@RestClient
    SituacaoCadastralHttpService situacaoCadastralHttpService;
	private final AgenciaRepository agenciaRepository;
    private final List<Agencia> agencias = new ArrayList<>();
    private final MeterRegistry meterRegistry;
    
    AgenciaService(AgenciaRepository agenciaRepository, MeterRegistry meterRegistry) {
        this.agenciaRepository = agenciaRepository;
        this.meterRegistry = meterRegistry;
    }

    @Transactional
    public void cadastrar(Agencia agencia) {
        //AGenciaHttp agenciaExistente  = situacaoCadastralHttpService.buscarPorCnpj(agencia.getCnpj());
        // Se já existe uma agência ativa cadastrada com o mesmo CNPJ, não deixar cadastrar
        //if (agenciaExistente != null && agenciaExistente.getSituacaoCadastral() == SituacaoCadastral.ATIVO) {
        //    throw new AgenciaNaoAtivaOuNaoEncontradaException();
        //}
        //if (agenciaExistente == null) {
    		Log.info("Agencia com CNPJ " + agencia.getCnpj() + " foi adicionada");
    		 this.meterRegistry.counter("agencia_adicionada_count").increment();
        	agenciaRepository.persist(agencia);
        //}
    }

    public Agencia buscarPorId(Long id) {
        //return agencias.stream().filter(agencia -> agencia.getId().equals(id)).toList().getFirst();
    	return agenciaRepository.findById(id);
    }

    @Transactional
    public void deletar(Long id) {
        //agencias.removeIf(agencia -> agencia.getId().equals(id));
    	Log.info("A agência foi deletada");
    	agenciaRepository.deleteById(id);
    }

	/*
	 * public void alterar(Agencia agencia) { agencias.add(agencia); }
	 */
    
    @Transactional
    public void alterar(Agencia agencia) {
    	Log.info("A agência com CNPJ " + agencia.getCnpj() + " foi alterada");
        agenciaRepository.update("nome = ?1, razaoSocial = ?2, cnpj = ?3 where id = ?4", agencia.getNome(), agencia.getRazaoSocial(), agencia.getCnpj(), agencia.getId());
    }
}
