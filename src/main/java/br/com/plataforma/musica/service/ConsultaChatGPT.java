package br.com.plataforma.musica.service;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;

public class ConsultaChatGPT {

    public static String pesquisarArtista(String texto) {
        OpenAiService service = new OpenAiService(System.getenv("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"));

        CompletionRequest requisicao = CompletionRequest.builder()
                .model("text-davinci-003")
                .prompt("pesquisar dados do artista: " + texto)
                .maxTokens(1000)
                .temperature(0.7)
                .build();


        var resposta = service.createCompletion(requisicao);
        return resposta.getChoices().get(0).getText();
    }
}
