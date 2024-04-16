package com.safetynet.alerts.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class TestMedicalRecordController {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private WebApplicationContext WebContext;

	@BeforeEach
	public void setupMockMvc() {
		mockMvc = MockMvcBuilders.webAppContextSetup(WebContext).build();
	}

	@Test
	@Tag("CREATE")
	@DisplayName("ERROR CREATE Unknown Person with MedicalRecord")
	void errorCreateUnknownPersonWithMedicalRecord() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/medicalRecord").contentType(APPLICATION_JSON)
						.content(" { \r\n" + "     \"firstName\":\"Raj\", \r\n" + "     \"lastName\":\"K\", \r\n"
								+ "     \"birthdate\":\"02/03/1989\", \r\n"
								+ "     \"medications\":[\"metmorfhin:500mg\", \"janumet:1000mg\"], \r\n"
								+ "     \"allergies\":[\"none\"] \r\n" + "     }")
						.accept(APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print()).andExpect(status().isConflict());
	}

	@Test
	@Tag("UPDATE")
	@DisplayName("UPDATE Person OK with MedicalRecord")
	void updatePersonOkWithMedicalRecordAndReturnIsOk() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.put("/medicalRecord").contentType(APPLICATION_JSON)
						.content(" { \r\n" + "     \"firstName\":\"test\", \r\n" + "     \"lastName\":\"test\", \r\n"
								+ "     \"birthdate\":\"03/03/1956\", \r\n"
								+ "     \"medications\":[\"test:500mg\", \"dolo650:1000mg\"], \r\n"
								+ "     \"allergies\":[\"NEW ALLERGY\"] \r\n" + "     }")
						.accept(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print()).andExpect(status().isOk());
	}

	@Test
	@Tag("UPDATE")
	@DisplayName("UPDATE Person ERROR with Unknown Person")
	void updatePersonErrorWithUnknownPerson() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.put("/medicalRecord").contentType(APPLICATION_JSON)
						.content(" { \r\n" + "     \"firstName\":\"test\", \r\n" + "     \"lastName\":\"test\", \r\n"
								+ "     \"birthdate\":\"20/12/1989\", \r\n"
								+ "     \"medications\":[\"testm1:10mg\", \"testm2:10mg\"], \r\n"
								+ "     \"allergies\":[\"NEW ALLERGY:araignees\"] \r\n" + "     }"))
				.andExpect(status().isNotFound());
	}

	@Test
	@Tag("DELETE")
	@DisplayName("DELETE Person OK when is in the list")
	void deletePersonOkWhenMedicalRecordIsDeleteAndInTheList() throws Exception {
		this.mockMvc.perform(
				MockMvcRequestBuilders.delete("/medicalRecord").param("firstName", "Raj").param("lastName", "K"))
				.andExpect(status().isOk());
	}

	@Test
	@Tag("DELETE")
	@DisplayName("DELETE Person ERROR when Unknown Person")
	void deletePersonErrorWhenUnknownPerson() throws Exception {
		this.mockMvc.perform(
				MockMvcRequestBuilders.delete("/medicalRecord").param("firstName", "test").param("lastName", "test"))
				.andExpect(status().isNotFound());
	}

}
