package pedro.prueba.configuration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import static org.junit.Assert.assertNotNull;

/**
 * Unit test for ApiGatewayConfiguration
 */
@RunWith(MockitoJUnitRunner.class)
public class ApiGatewayConfigurationTest {

    /**
     * Unit test for addCorsMappings
     */
    @Test
    public void addCorsMappings() {
        ApiGatewayConfiguration config = new ApiGatewayConfiguration();
        CorsRegistry reg = new CorsRegistry();
        config.addCorsMappings(reg);
        assertNotNull("Must not be null", reg);
    }
}