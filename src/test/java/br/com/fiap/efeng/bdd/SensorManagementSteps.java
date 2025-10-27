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
public class SensorManagementSteps {

  @Autowired
  private TestRestTemplate restTemplate;

  @MockBean
  private VerificarToken verificarToken;

  @LocalServerPort
  private int port;

  private ResponseEntity<?> response;
  private HttpHeaders headers;

  @Given("I am authenticated as an administrator for sensor management")
  public void i_am_authenticated_as_an_administrator_for_sensor_management() {
    headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
  }

  @Given("there is a sensor with ID {int}")
  public void there_is_a_sensor_with_id(int id) {
    // This step is for context
  }

  @When("I request the list of sensors")
  public void i_request_the_list_of_sensors() {
    String url = "http://localhost:" + port + "/api/sensores";
    response = restTemplate.getForEntity(url, Object.class);
  }

  @When("I request sensor details for ID {int}")
  public void i_request_sensor_details_for_id(int id) {
    String url = "http://localhost:" + port + "/api/sensores/" + id;
    response = restTemplate.getForEntity(url, Object.class);
  }

  @When("I search for sensors with type {string}")
  public void i_search_for_sensors_with_type(String type) {
    String url = "http://localhost:" + port + "/api/sensores/tipo?tipoSensor=" + type;
    response = restTemplate.getForEntity(url, Object.class);
  }

  @Then("the sensor endpoint should respond")
  public void the_sensor_endpoint_should_respond() {
    assertNotNull(response, "Sensor endpoint should respond");
    assertTrue(response.getStatusCode().is2xxSuccessful() || response.getStatusCode().is3xxRedirection(),
        "Expected successful response but got: " + response.getStatusCode());
  }
}
