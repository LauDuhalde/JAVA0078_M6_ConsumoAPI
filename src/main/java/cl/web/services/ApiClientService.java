package cl.web.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Objects;

@Service
public class ApiClientService {

    private final RestTemplate restTemplate;

    @Value("${api.base-url}")
    private String apiBaseUrl; // ej: http://localhost:8081

    public ApiClientService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String obtenerToken(String usuario, String password) {
        String url = apiBaseUrl + "/auth/login";

        Map<String, String> body = Map.of("usuario", usuario, "password", password);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                Object tokenObj = response.getBody().get("token");
                if (tokenObj == null) {
                    throw new RuntimeException("La respuesta no contiene el campo 'token'. Revisar API externa.");
                }
                return tokenObj.toString();
            } else {
                throw new RuntimeException("Error en login: " + response.getStatusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener token: " + e.getMessage(), e);
        }
    }

    public String consumirApi(String token, String endpoint) {
        String url = apiBaseUrl + endpoint;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(Objects.requireNonNull(token));
        headers.setAccept(MediaType.parseMediaTypes("application/json"));

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response =
                    restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            } else {
                throw new RuntimeException("Error al consumir API: " + response.getStatusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al consumir API: " + e.getMessage(), e);
        }
    }
}
