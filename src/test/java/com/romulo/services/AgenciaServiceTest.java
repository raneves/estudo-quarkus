package com.romulo.services;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import br.com.romulo.domain.Agencia;
import br.com.romulo.domain.Endereco;
import br.com.romulo.domain.http.AGenciaHttp;
import br.com.romulo.exception.AgenciaNaoAtivaOuNaoEncontradaException;
import br.com.romulo.repository.AgenciaRepository;
import br.com.romulo.service.AgenciaService;
import br.com.romulo.service.http.SituacaoCadastralHttpService;

@QuarkusTest //eh aqui que indica que eh uma classe de teste do quarkus
public class AgenciaServiceTest {
	@InjectMock
    @RestClient
    private SituacaoCadastralHttpService situacaoCadastralHttpService;

    @InjectMock
    private AgenciaRepository agenciaRepository;

    @Inject
    private AgenciaService agenciaService;

    @Test
    public void deveNaoCadastrarQuandoClientRetornarNull() {
        Agencia agencia = criarAgencia();
        Mockito.when(situacaoCadastralHttpService.buscarPorCnpj("123")).thenReturn(null);

        Assertions.assertThrows(AgenciaNaoAtivaOuNaoEncontradaException.class, () -> agenciaService.cadastrar(agencia));

        Mockito.verify(agenciaRepository, Mockito.never()).persist(agencia);
    }

    @Test
    public void deveCadastrarQuandoClientRetornarSituacaoCadastralAtivo() {
        Agencia agencia = criarAgencia();
        Mockito.when(situacaoCadastralHttpService.buscarPorCnpj("123")).thenReturn(criarAgenciaHttp());

        agenciaService.cadastrar(agencia);

        Mockito.verify(agenciaRepository).persist(agencia);
    }

    private Agencia criarAgencia() {
        Endereco endereco = new Endereco(1, "Rua de teste", "Logradouro de teste", "Complemento de teste", 1);
        return new Agencia(1, "Agencia Teste", "Razao social da Agencia Teste", "123", endereco);
    }

    private AGenciaHttp criarAgenciaHttp() {
        return new AGenciaHttp("Agencia Teste", "Razao social da Agencia Teste", "123", "ATIVO");
    }
}
