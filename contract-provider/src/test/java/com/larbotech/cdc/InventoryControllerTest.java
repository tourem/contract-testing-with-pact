package com.larbotech.cdc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import au.com.dius.pact.provider.junit.PactRunner;
import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.VerificationReports;
import au.com.dius.pact.provider.junit.loader.PactBroker;
import au.com.dius.pact.provider.junit.target.HttpTarget;
import au.com.dius.pact.provider.junit.target.Target;
import au.com.dius.pact.provider.junit.target.TestTarget;
import au.com.dius.pact.provider.spring.SpringRestPactRunner;
import au.com.dius.pact.provider.spring.target.MockMvcTarget;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

/**
 *
 * Contract tests require @TestTarget annotated Target interface implementation,
 * which implementation should throw an exception on unexpected response.
 * MockMvcTarget is out-of-the-box implementation that verifies controller responses.
 * It must be defined if you use SpringRestPactRunner and all tested controllers need to be set,
 * otherwise they won’t be checked and test will fail with Not found error.
 *
 */
@RunWith(SpringRestPactRunner.class)
@WebMvcTest(InventoryController.class)
@ActiveProfiles("test")
@PactBroker(host="localhost",port="8500")
@Provider("inventory_provider")
@VerificationReports({"console", "markdown"})
//@PactFolder("contracts/")
public class InventoryControllerTest {
	
  @MockBean
  private InventoryService inventoryService;

  @TestTarget
  public final MockMvcTarget target = new MockMvcTarget();

  @Autowired
  private InventoryController inventoryController;

  @Before
  public void setupBefore() {
    MockitoAnnotations.initMocks(this);
    target.setControllers(inventoryController);
  }

  /**
   * We define @State annotated method. It’s our place to define our mocks and stubs for the chosen contract state.
   */
  @State(value="create inventory")
  @SneakyThrows
  public void createInventoryState() {
	  
	  Inventory inventory=new Inventory("TV", "CHENNAI", 100);
	  when(inventoryService.saveInventory(any(Inventory.class))).thenReturn(inventory) ;
  }
}