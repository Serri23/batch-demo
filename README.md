# Batch Demo

Este proyecto es una demo de Spring Boot Batch que genera reportes de clientes y los envía por correo electrónico.

## Requisitos
- Java 17 o superior
- Maven 3.6+

## Configuración

Edita el archivo `src/main/resources/application.properties` para configurar:
- Base de datos H2 (por defecto en memoria)
- Parámetros de correo SMTP (por ejemplo, Gmail)
- Ruta de salida de reportes

Ejemplo de configuración SMTP para Gmail:
```
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=tu_usuario@gmail.com
spring.mail.password=tu_contraseña	spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

## Ejecución

1. Clona el repositorio y accede al directorio del proyecto.
2. Compila el proyecto:
   ```
   ./mvnw clean package
   ```
3. Ejecuta la aplicación:
   ```
   java -jar target/batch-demo-0.0.1-SNAPSHOT.jar
   ```

## Funcionalidad
- Genera un reporte diario de clientes en formato CSV.
- Envía el reporte por correo electrónico usando los parámetros configurados.

## Personalización
- Modifica la lógica de generación de reportes en `CustomerReportJob` y los pasos en `step/`.
- Cambia el asunto y remitente del correo en las propiedades `customer.report.email.subject` y `customer.report.email.from`.

## Notas
- Si usas Gmail, puede que necesites una contraseña de aplicación y habilitar el acceso a apps menos seguras.
- Para otros proveedores SMTP, ajusta los parámetros según la documentación oficial.

## Licencia
MIT

