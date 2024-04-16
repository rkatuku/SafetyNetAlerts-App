package com.safetynet.alerts.utils;

import com.safetynet.alerts.model.AllInfo;
import com.safetynet.alerts.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class TestDataLoader {

	private AllInfo allInformations;

	@Value("${info.data}")
	private String jsonFile;

	private Person person1;
	private Person person2;

	@BeforeEach
	private void setUp() throws IOException {
		person1 = new Person("Test", "Test", "1509 Culver St", "Culver", "97451", "841-874-6512", "testt@email.com");
		person2 = new Person("Test1", "Test1", "1509 Culver St", "Culver", "97451", "841-874-6513", "test1t@email.com");
	}

	@Test
	@Tag("ReadDataJson")
	@DisplayName("Read Data JSON - Null JSON File")
	void readDataJsonNullFile() {
		assertThatNullPointerException().isThrownBy(() -> {
			LoadData.readJsonFile(null);
		});
	}

	@Test
	@Tag("searchPerson")
	@DisplayName("Search Person Valid Person")
	void searchPersonIsValidPerson() {
		List<Person> personsList = new ArrayList<>();
		personsList.add(person1);
		personsList.add(person2);
		Person personFound = LoadData.searchPerson("Jacob", "Boyd", personsList);
		assertThat(personFound).isNotNull();
		assertThat(personsList).contains(personFound);
	}

	@Test
	@Tag("ReadDataJson")
	@DisplayName("Read Data JSON - FireStations")
	void readDataJsonFireStations() throws IOException {
		allInformations = LoadData.readJsonFile(jsonFile);
		assertThat(allInformations.getFirestations()).isNotNull();
	}

	@Test
	@Tag("ReadDataJson")
	@DisplayName("Read Data JSON - Households")
	void readDataJsonHouseholds() throws IOException {
		allInformations = LoadData.readJsonFile(jsonFile);
		assertThat(allInformations.getHouseholds()).isNotNull();
	}

	@Test
	@Tag("searchPerson")
	@DisplayName("Search Person Unknown Person")
	void searchPersonUnknownPerson() {
		List<Person> personsList = new ArrayList<>();
		personsList.add(person1);
		personsList.add(person2);
		assertThatIllegalArgumentException().isThrownBy(() -> {
			LoadData.searchPerson("Nicolas", "Gros", personsList);
		});
	}

	@Test
	@Tag("ReadDataJson")
	@DisplayName("Read Data JSON - Person")
	void readDataJsonPerson() throws IOException {
		allInformations = LoadData.readJsonFile(jsonFile);
		assertThat(allInformations.getPersonsList()).isNotNull();
	}

}
