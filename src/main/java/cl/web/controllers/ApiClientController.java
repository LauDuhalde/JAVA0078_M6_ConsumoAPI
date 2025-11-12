package cl.web.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cl.web.services.ApiClientService;

@RestController
@RequestMapping("/cliente")
public class ApiClientController {
	private static final Logger logger = LoggerFactory.getLogger(ApiClientController.class);
	
    private final ApiClientService apiClientService;

    public ApiClientController(ApiClientService apiClientService) {
        this.apiClientService = apiClientService;
    }
    
    //http://localhost:8080/cliente/probar?user=admin&pass=1234
    @GetMapping("/probar")
    public ResponseEntity<String> probar(
            @RequestParam String usuario,
            @RequestParam String password,
            @RequestParam(defaultValue = "/api/productos") String endpoint) {

        try {
        	String token = apiClientService.obtenerToken(usuario, password);
        	logger.info("TOKEN: "+token);
        	
            String resultado = apiClientService.consumirApi(token, endpoint);
            logger.info("RESULTADO API: "+resultado);
            
            return ResponseEntity.ok(resultado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
}
