package com.tecsup.petclinic.webs;

import static org.hamcrest.CoreMatchers.is;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@AutoConfigureMockMvc
@SpringBootTest
@Slf4j
public class TypesControllerTest {

    private static final ObjectMapper om = new ObjectMapper();

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void testFindAllTypes() throws Exception {

		int ID_EXPECTED = 1;

		this.mockMvc.perform(get("/types"))
				.andExpect(status().isOk())
				.andExpect(content()
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$[?(@.id == " + ID_EXPECTED + ")].id").exists());
	}
	

	@Test
	public void testFindTypeOK() throws Exception {

		String TYPE_NAME = "cat";
		int TYPE_ID = 1;

		this.mockMvc.perform(get("/types/1"))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(TYPE_ID)))
				.andExpect(jsonPath("$.name", is(TYPE_NAME)));
	}

	@Test
	public void testFindTypeKO() throws Exception {

		mockMvc.perform(get("/types/666"))
				.andExpect(status().isNotFound());

	}
	
	@Test
	public void testCreateType() throws Exception {
		// TODO: Completar aquí
	}

	@Test
	public void testDeleteType() throws Exception {
		// TODO: Completar aquí
	}

	@Test
	public void testDeleteTypeKO() throws Exception {
		// TODO: Completar aquí
	}

	@Test
	public void testUpdateType() throws Exception {
		// TODO: Completar aquí
	}

}
