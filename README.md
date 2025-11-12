# Proyecto: JAVA0078_M6_ConsumoAPI

## Objetivo
Desarrollar un **cliente REST** con **Spring Boot** que consuma datos desde una **API protegida con JWT**, demostrando dominio en:
- Consumo de servicios REST.
- Manejo de autenticación mediante tokens JWT.
- Configuración y uso de `RestTemplate` para comunicación HTTP.
- Registro de logs en consola y archivo.

---

## Configuración del proyecto

**Archivo:** `application.properties`
```properties
spring.application.name=JAVA0078_M6_ConsumoAPI
server.port=8080
api.base-url=http://localhost:8081

# Nivel global
logging.level.root=info

# Nivel específico para controladores
logging.level.org.springframework.web=debug

# Log a archivo
logging.file.name=logs/ConsumoAPI.log
```

---

## Dependencias principales (`pom.xml`)
- `spring-boot-starter-web` → Para construir el cliente REST.
- `spring-boot-devtools` → Recarga automática en desarrollo.
- `spring-boot-starter-tomcat` → Contenedor embebido (modo WAR).
- `spring-boot-starter-test` → Pruebas unitarias.

Java versión: **21**  
Spring Boot versión: **3.5.7**

---

## Estructura simplificada del proyecto

```
JAVA0078_M6_ConsumoAPI/
├── pom.xml
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── cl/web/
│   │   │       ├── config
│   │   │       │   └── RestTemplateConfig.java
│   │   │       ├── controllers/
│   │   │       │   └── ApiClientController.java
│   │   │       └── services/
│   │   │           └── ApiClientService.java
│   │   ├── resources/
│   │   │   └── application.properties
└── README.md

```

---

## Componentes principales

### 🔹 `RestTemplateConfig`
Define el bean `RestTemplate` utilizado para las peticiones HTTP.

```java
@Configuration
public class RestTemplateConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
```

---

### 🔹 `ApiClientService`
Contiene la lógica para:
1. **Obtener el token JWT** desde `/auth/login`.
2. **Consumir un endpoint protegido** agregando el token en la cabecera `Authorization`.

Métodos principales:
- `obtenerToken(String usuario, String password)`
- `consumirApi(String token, String endpoint)`

---

### 🔹 `ApiClientController`
Expone el endpoint `/cliente/probar` para probar el consumo de la API externa.

**Ejemplo de uso:**
```
http://localhost:8080/cliente/probar?usuario=admin&password=1234
```

Flujo:
1. Se obtiene el token JWT desde la API externa.
2. Se consume el endpoint `/api/productos`.
3. Se muestran los resultados en consola o log.

---

## Ejemplo de salida en logs
```
INFO  c.w.c.ApiClientController : TOKEN: eyJhbGciOiJIUzI1...
INFO  c.w.c.ApiClientController : RESULTADO API: [{"id":1,"nombre":"Producto 1","detalle":"","cantidad":0,"precio":1500.0}, {""id":2,"nombre":"Televisor","detalle":"","cantidad":0,"precio":150000.0"}]
```

Los logs se guardan en:
```
logs/ConsumoAPI.log
```

---

## Ejecución
1. Asegúrate de tener la API protegida con JWT corriendo en el puerto `8081`.
2. Levanta este proyecto con:
   ```bash
   mvn spring-boot:run
   ```
3. Accede al endpoint de prueba:
   ```
   http://localhost:8080/cliente/probar?usuario=admin&password=1234
   ```

---

## Autor
-Laura Duhalde 

