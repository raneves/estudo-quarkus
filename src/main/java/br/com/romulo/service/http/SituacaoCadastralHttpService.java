package br.com.romulo.service.http;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import br.com.romulo.domain.http.AGenciaHttp;

@Path("/situacao-cadastral")
@RegisterRestClient(configKey = "situacao-cadastral-api")
public interface SituacaoCadastralHttpService {
	@GET
    @Path("{cnpj}")
    AGenciaHttp buscarPorCnpj(@PathParam("cnpj")String cnpj);
}
