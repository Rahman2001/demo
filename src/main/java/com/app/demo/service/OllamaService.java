package com.app.demo.service;

import com.app.demo.models.LLMRequestDto;
import com.app.demo.models.LLMResponseDto;
import com.app.demo.models.Note;
import com.app.demo.repository.OllamaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Map;

@Service
public class OllamaService implements OllamaRepository {
    @Value("${ollama.model}")
    private String MODEL;
    @Value("${ollama.url}")
    private String URL;
    @Value("${ollama.stream}")
    private boolean STREAM;
    private final OkHttpClient client;

    public OllamaService() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.setConnectTimeout$okhttp(30000);
        builder.setReadTimeout$okhttp(30000);
        client = builder.build();
    }
    @Override
    public String sendPostRequest(Note note) throws IOException {
        String instruction = "Attention: Only answer by summarizing the given text! Text: ";
        LLMRequestDto requestDto = LLMRequestDto.builder()
                .model(MODEL)
                .stream(STREAM)
                .prompt(instruction + note.getRawText())
                .build();
        RequestBody body = RequestBody.create(getJsonFromObject(requestDto), MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(URL)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            assert response.body() != null;
            return response.body().string();
        }
    }

    public String getJsonFromObject(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }

    public LLMResponseDto getObjectFromJson(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map map = mapper.readValue(json, Map.class);
        return new LLMResponseDto((String) map.get("response"), (Timestamp) map.get("createdAt"));
    }
}
