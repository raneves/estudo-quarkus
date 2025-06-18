package br.com.romulo.service.http;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import br.com.romulo.domain.http.AGenciaHttp;

@Path("/situacao-cadastral")
@RegisterRestClient(configKey = "situacao-cadastral-api")
public interface SituacaoCadastralHttpService {
	@GET
    @Path("{cnpj}")
    AGenciaHttp buscarPorCnpj(String cnpj);
}
