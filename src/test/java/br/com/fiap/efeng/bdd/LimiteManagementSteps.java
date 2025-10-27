package br.com.fiap.efeng.bdd;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

import br.com.fiap.efeng.EfengApplication;
import br.com.fiap.efeng.config.security.VerificarToken;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = EfengApplication.class)
@ActiveProfiles("test")
public class LimiteManagementSteps {

  @Autowired
  private TestRestTemplate restTemplate;

  @MockBean
  private VerificarToken verificarToken;

  @LocalServerPort
  private int port;

  private ResponseEntity<?> response;
  private HttpHeaders headers;

  @Given("I am authenticated as an administrator for limit management")
  public void i_am_authenticated_as_an_administrator_for_limit_management() {
    headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
  }

  @Given("there is a limit with ID {int}")
  public void there_is_a_limit_with_id(int id) {
    // This step is for context
  }

  @When("I request the list of consumption limits")
  public void i_request_the_list_of_consumption_limits() {
    String url = "http://localhost:" + port + "/api/limites";
    response = restTemplate.getForEntity(url, Object.class);
  }

  @When("I request limit details for ID {int}")
  public void i_request_limit_details_for_id(int id) {
    String url = "http://localhost:" + port + "/api/limites/" + id;
    response = restTemplate.getForEntity(url, Object.class);
  }

  @When("I search for limits at location {string}")
  public void i_search_for_limits_at_location(String location) {
    String url = "http://localhost:" + port + "/api/limites/localizacao?localizacao=" + location;
    response = restTemplate.getForEntity(url, Object.class);
  }

  @Then("the limit endpoint should respond")
  public void the_limit_endpoint_should_respond() {
    assertNotNull(response, "Limit endpoint should respond");
    assertTrue(response.getStatusCode().is2xxSuccessful() || response.getStatusCode().is3xxRedirection(),
        "Expected successful response but got: " + response.getStatusCode());
  }
}
