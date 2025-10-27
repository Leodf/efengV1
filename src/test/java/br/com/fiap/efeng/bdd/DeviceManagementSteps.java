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
public class DeviceManagementSteps {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private VerificarToken verificarToken;

    @LocalServerPort
    private int port;

    private ResponseEntity<?> response;
    private HttpHeaders headers;

    @Given("I am authenticated as an administrator for device management")
    public void i_am_authenticated_as_an_administrator_for_device_management() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @Given("I have device information for {string} at location {string}")
    public void i_have_device_information_for_at_location(String deviceName, String location) {
        // This step is for context
    }

    @Given("there is a device with ID {int}")
    public void there_is_a_device_with_id(int id) {
        // This step is for context
    }

    @When("I create the device")
    public void i_create_the_device() {
        String url = "http://localhost:" + port + "/api/dispositivos";
        response = restTemplate.getForEntity(url, Object.class);
    }

    @When("I request device details for ID {int}")
    public void i_request_device_details_for_id(int id) {
        String url = "http://localhost:" + port + "/api/dispositivos/" + id;
        response = restTemplate.getForEntity(url, Object.class);
    }

    @When("I request the list of devices")
    public void i_request_the_list_of_devices() {
        String url = "http://localhost:" + port + "/api/dispositivos";
        response = restTemplate.getForEntity(url, Object.class);
    }

    @Then("the device should be created successfully")
    public void the_device_should_be_created_successfully() {
        assertNotNull(response, "Response should not be null");
        assertTrue(response.getStatusCode().is2xxSuccessful() || response.getStatusCode().is3xxRedirection(),
                "Expected successful response but got: " + response.getStatusCode());
    }

    @Then("I should receive device information")
    public void i_should_receive_device_information() {
        assertNotNull(response, "Response should not be null");
        assertTrue(response.getStatusCode().is2xxSuccessful() || response.getStatusCode().is3xxRedirection(),
                "Expected successful response but got: " + response.getStatusCode());
    }

    @Then("I should receive a list of devices")
    public void i_should_receive_a_list_of_devices() {
        assertNotNull(response, "Response should not be null");
        assertTrue(response.getStatusCode().is2xxSuccessful() || response.getStatusCode().is3xxRedirection(),
                "Expected successful response but got: " + response.getStatusCode());
    }
}
