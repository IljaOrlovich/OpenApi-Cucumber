package lt.eif.viko.iorlovic.library;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

/**
 * Configuration class for OpenAPI documentation.
 */
@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Ilja O",
                        email = "ilja.orlovic@stud.viko.lt",
                        url = "eif.viko.lt"
                ),
                description = "Open API documentation for Library Application",
                title = "Library Application REST",
                version = "1.0",
                license = @License(
                        name = "Free to use",
                        url = "eif.viko.lt"
                )
        ),
        servers = {
                @Server(
                        description = "Local DEV",
                        url = "localhost:7171"
                ),
                @Server(
                        description = "Test Server",
                        url = "localhost:7171"
                )
        }
)
public class OpenApiConfig {
}
