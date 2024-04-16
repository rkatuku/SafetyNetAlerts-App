package com.safetynet.alerts.service;

import com.safetynet.alerts.model.AllInfo;
import com.safetynet.alerts.model.FireStation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;
import static org.mockito.Mockito.when;

/**
 * FireStationService units tests class
 */
@WebMvcTest(FireStationService.class)
@ExtendWith(MockitoExtension.class)
class TestFireStationService {

	@InjectMocks
	private FireStationService fireStationService;
	@Mock
	private AllInfo allInformations;

	@Test
	@Tag("CREATE")
	@DisplayName("CREATE FireStation OK with Two Address")
	void createFireStationOkWithTwoAddress() {
		FireStation firestation1 = new FireStation("1");
		firestation1.addAddress("908 73rd St");
		firestation1.addAddress("644 Gershwin Cir");
		Map<String, FireStation> firestationsList = new HashMap<String, FireStation>();
		firestationsList.put("1", firestation1);
		Map<String, String> firestationMappingToCreate = new HashMap<String, String>();
		firestationMappingToCreate.put("station", "1");
		firestationMappingToCreate.put("address", "new address");
		when(allInformations.getFirestations()).thenReturn(firestationsList);
		boolean isAdded = fireStationService.addAddressForFirestation(firestationMappingToCreate);
		assertThat(isAdded).isTrue();
		assertThat(firestationsList.get("1").getAddresses().contains("new address")).isTrue();
	}

	@Test
	@Tag("CREATE")
	@DisplayName("CREATE FireStation ERROR - Bad FireStation number")
	void createFireStationErrorBadNumber() {
		FireStation firestation1 = new FireStation("1");
		firestation1.addAddress("908 73rd St");
		firestation1.addAddress("644 Gershwin Cir");
		Map<String, FireStation> fireStationList = new HashMap<String, FireStation>();
		fireStationList.put("1", firestation1);

		Map<String, String> firestationMappingToCreate = new HashMap<String, String>();
		firestationMappingToCreate.put("station", "488");
		firestationMappingToCreate.put("address", "new address");

		when(allInformations.getFirestations()).thenReturn(fireStationList);
		assertThatNullPointerException().isThrownBy(() -> {
			fireStationService.addAddressForFirestation(firestationMappingToCreate);
		});
	}

	@Test
	@Tag("CREATE")
	@DisplayName("Create ERROR - Address alreadyExisting")
	void createErrorAddressAlreadyExisting() {

		FireStation firestation1 = new FireStation("1");
		firestation1.addAddress("908 73rd St");
		firestation1.addAddress("644 Gershwin Cir");

		FireStation firestation2 = new FireStation("2");
		firestation2.addAddress("29 15th St");
		Map<String, FireStation> firestationsList = new HashMap<String, FireStation>();
		firestationsList.put("1", firestation1);
		firestationsList.put("2", firestation2);

		Map<String, String> firestationMappingToCreate = new HashMap<String, String>();
		firestationMappingToCreate.put("station", "2");
		firestationMappingToCreate.put("address", "908 73rd St");
		when(allInformations.getFirestations()).thenReturn(firestationsList);
		boolean isAdded = fireStationService.addAddressForFirestation(firestationMappingToCreate);
		assertThat(isAdded).isFalse();
		assertThat(firestationsList.get("2").getAddresses().contains("908 73rd St")).isFalse();
	}

	@Test
	@Tag("UPDATE")
	@DisplayName("UPDATE Address OK - Add Address already exist")
	void updateAddressOkAddAddressAlreadyExist() {

		FireStation firestation1 = new FireStation("1");
		firestation1.addAddress("908 73rd St");
		firestation1.addAddress("644 Gershwin Cir");

		FireStation firestation2 = new FireStation("2");
		firestation2.addAddress("29 15th St");
		Map<String, FireStation> firestationsList = new HashMap<String, FireStation>();
		firestationsList.put("1", firestation1);
		firestationsList.put("2", firestation2);

		Map<String, String> firestationMappingToUpdate = new HashMap<String, String>();
		firestationMappingToUpdate.put("station", "2");
		firestationMappingToUpdate.put("address", "908 73rd St");
		when(allInformations.getFirestations()).thenReturn(firestationsList);
		boolean isUpdated = fireStationService.updateAddressForFireStation(firestationMappingToUpdate);
		assertThat(isUpdated).isTrue();
		assertThat(firestationsList.get("1").getAddresses().contains("908 73rd St")).isFalse();
		assertThat(firestationsList.get("2").getAddresses().contains("908 73rd St")).isTrue();
	}

	@Test
	@Tag("UPDATE")
	@DisplayName("UPDATE OK - Browse three address")
	void updateAddressOkBrowseThreeAddress() {

		FireStation firestation1 = new FireStation("1");
		firestation1.addAddress("644 Gershwin Cir");

		FireStation firestation2 = new FireStation("2");
		firestation2.addAddress("29 15th St");

		FireStation firestation3 = new FireStation("3");
		firestation3.addAddress("908 73rd St");

		Map<String, FireStation> firestationsList = new HashMap<String, FireStation>();
		firestationsList.put("1", firestation1);
		firestationsList.put("2", firestation2);
		firestationsList.put("3", firestation3);
		Map<String, String> firestationMappingToUpdate = new HashMap<String, String>();
		firestationMappingToUpdate.put("station", "2");
		firestationMappingToUpdate.put("address", "908 73rd St");

		when(allInformations.getFirestations()).thenReturn(firestationsList);
		boolean isUpdated = fireStationService.updateAddressForFireStation(firestationMappingToUpdate);
		assertThat(isUpdated).isTrue();
		assertThat(firestationsList.get("3").getAddresses().contains("908 73rd St")).isFalse();
		assertThat(firestationsList.get("2").getAddresses().contains("908 73rd St")).isTrue();
	}

}
