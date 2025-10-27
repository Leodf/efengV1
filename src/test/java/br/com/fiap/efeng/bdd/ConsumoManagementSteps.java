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
public class ConsumoManagementSteps {

  @Autowired
  private TestRestTemplate restTemplate;

  @MockBean
  private VerificarToken verificarToken;

  @LocalServerPort
  private int port;

  private ResponseEntity<?> response;
  private HttpHeaders headers;

  @Given("I am authenticated as an administrator for consumption management")
  public void i_am_authenticated_as_an_administrator_for_consumption_management() {
    headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
  }

  @Given("there is a consumption record with ID {int}")
  public void there_is_a_consumption_record_with_id(int id) {
    // This step is for context
  }

  @Given("there is a device with ID {int} for consumption")
  public void there_is_a_device_with_id_for_consumption(int id) {
    // This step is for context
  }

  @When("I request the list of consumption records")
  public void i_request_the_list_of_consumption_records() {
    String url = "http://localhost:" + port + "/api/consumos";
    response = restTemplate.getForEntity(url, Object.class);
  }

  @When("I request consumption details for ID {int}")
  public void i_request_consumption_details_for_id(int id) {
    String url = "http://localhost:" + port + "/api/consumos/" + id;
    response = restTemplate.getForEntity(url, Object.class);
  }

  @When("I request consumption records for device ID {int}")
  public void i_request_consumption_records_for_device_id(int deviceId) {
    String url = "http://localhost:" + port + "/api/consumos/dispositivo/" + deviceId;
    response = restTemplate.getForEntity(url, Object.class);
  }

  @Then("the consumption endpoint should respond")
  public void the_consumption_endpoint_should_respond() {
    assertNotNull(response, "Consumption endpoint should respond");
    assertTrue(response.getStatusCode().is2xxSuccessful() || response.getStatusCode().is3xxRedirection(),
        "Expected successful response but got: " + response.getStatusCode());
  }
}
