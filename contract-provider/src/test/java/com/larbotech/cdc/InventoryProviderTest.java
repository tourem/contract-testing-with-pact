package com.larbotech.cdc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.VerificationReports;
import au.com.dius.pact.provider.junit.loader.PactBroker;
import au.com.dius.pact.provider.junit.target.HttpTarget;
import au.com.dius.pact.provider.junit.target.Target;
import au.com.dius.pact.provider.junit.target.TestTarget;
import au.com.dius.pact.provider.spring.SpringRestPactRunner;
import lombok.SneakyThrows;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;


@RunWith(SpringRestPactRunner.class)
@SpringBootTest(classes=Application.class,properties={"spring.profiles.active=test","spring.cloud.config.enabled=false"},webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@PactBroker(host="${pact.host}",port="${pact.port}")
@Provider("inventory_provider")
@VerificationReports({"console", "markdown"})
public class InventoryProviderTest {
	
  @MockBean
  private InventoryService inventoryService;

  @TestTarget
  public final Target target = new HttpTarget(9050);


  @State(value="create inventory")
  @SneakyThrows
  public void createInventoryState() {
	  
	  Inventory inventory=new Inventory("TV", "CHENNAI", 100);
	  when(inventoryService.saveInventory(any(Inventory.class))).thenReturn(inventory) ;
  }
}