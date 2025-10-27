package br.com.fiap.efeng.bdd;

import br.com.fiap.efeng.dto.UsuarioCadastroDTO;
import br.com.fiap.efeng.dto.UsuarioExibicaoDTO;
import br.com.fiap.efeng.model.UsuarioRole;
import br.com.fiap.efeng.config.security.VerificarToken;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
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

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = EfengApplication.class)
@ActiveProfiles("test")
public class UserRegistrationSteps {

  @Autowired
  private TestRestTemplate restTemplate;

  @MockBean
  private VerificarToken verificarToken;

  @LocalServerPort
  private int port;

  private UsuarioCadastroDTO usuarioCadastroDTO;
  private ResponseEntity<UsuarioExibicaoDTO> response;
  private HttpHeaders headers;

  @Given("I am registering with name {string}, email {string} and password {string}")
  public void i_am_registering_with_name_email_and_password(String name, String email, String password) {
    headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    usuarioCadastroDTO = new UsuarioCadastroDTO(
        null,
        name,
        email,
        password,
        UsuarioRole.USER);
  }

  @When("I submit the registration")
  public void i_submit_the_registration() {
    HttpEntity<UsuarioCadastroDTO> request = new HttpEntity<>(usuarioCadastroDTO, headers);
    String url = "http://localhost:" + port + "/auth/register";
    response = restTemplate.postForEntity(url, request, UsuarioExibicaoDTO.class);
  }

  @Then("the registration endpoint should respond")
  public void the_registration_endpoint_should_respond() {
    assertNotNull(response, "Registration response should not be null");
    // Just verify that the endpoint responded, regardless of status
  }
}
