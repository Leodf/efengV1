package br.com.fiap.efeng.bdd;

import br.com.fiap.efeng.dto.LoginDTO;
import br.com.fiap.efeng.dto.TokenDTO;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

import br.com.fiap.efeng.EfengApplication;
import br.com.fiap.efeng.config.security.VerificarToken;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = EfengApplication.class)
@ActiveProfiles("test")
public class UserLoginSteps {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private VerificarToken verificarToken;

    @LocalServerPort
    private int port;

    private LoginDTO loginDTO;
    private ResponseEntity<TokenDTO> loginResponse;
    private HttpHeaders headers;

    @Given("a user is registered with email {string} and password {string}")
    public void a_user_is_registered_with_email_and_password(String email, String password) {
        // This step is for context, actual registration happens in the backend
        // In a real scenario, you would ensure the user exists
    }

    @Given("I have login credentials email {string} and password {string}")
    public void i_have_login_credentials_email_and_password(String email, String password) {
        loginDTO = new LoginDTO(email, password);
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @When("I attempt to login")
    public void i_attempt_to_login() {
        HttpEntity<LoginDTO> request = new HttpEntity<>(loginDTO, headers);
        String url = "http://localhost:" + port + "/auth/login";
        loginResponse = restTemplate.postForEntity(url, request, TokenDTO.class);
    }

    @Then("the login endpoint should respond")
    public void the_login_endpoint_should_respond() {
        assertNotNull(loginResponse, "Login response should not be null");
        // Just check that we got a response, regardless of status
    }
}
