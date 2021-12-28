package pedro.prueba.interfaces;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/** The Class InterfacesConfig. */
@Configuration
@EnableFeignClients("pedro.prueba.interfaces")
public class InterfacesConfig {}
