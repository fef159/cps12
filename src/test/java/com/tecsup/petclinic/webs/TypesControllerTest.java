package com.tecsup.petclinic.webs;

import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import com.jayway.jsonpath.JsonPath;
import com.tecsup.petclinic.dtos.PetTypeDTO;
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
        String TYPE_DESCRIPTION = "Domestic feline";
        String TYPE_CARE_LEVEL= "medium";
		int TYPE_ID = 1;

		this.mockMvc.perform(get("/types/1"))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(TYPE_ID)))
				.andExpect(jsonPath("$.name", is(TYPE_NAME)))
                .andExpect(jsonPath("$.description", is(TYPE_DESCRIPTION)))
                .andExpect(jsonPath("$.careLevel", is(TYPE_CARE_LEVEL)));
	}

	@Test
	public void testFindTypeKO() throws Exception {

		mockMvc.perform(get("/types/666"))
				.andExpect(status().isNotFound());

	}
	
	@Test
    public void testCreateType() throws Exception {

        PetTypeDTO newPetTypeDTO = new PetTypeDTO();
        newPetTypeDTO.setName("Reptile");
        newPetTypeDTO.setDescription("Cold-blooded vertebrate");
        newPetTypeDTO.setCareLevel("medium");

        this.mockMvc.perform(post("/types")
                        .content(om.writeValueAsString(newPetTypeDTO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Reptile")))
                .andExpect(jsonPath("$.description", is("Cold-blooded vertebrate")))
                .andExpect(jsonPath("$.careLevel", is("medium")));
    }

	@Test
    public void testDeleteType() throws Exception {

        PetTypeDTO newPetTypeDTO = new PetTypeDTO();
        newPetTypeDTO.setName("Beetle");
        newPetTypeDTO.setDescription("Insect with hard shell");
        newPetTypeDTO.setCareLevel("low");

        ResultActions mvcActions = mockMvc.perform(post("/types")
                        .content(om.writeValueAsString(newPetTypeDTO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        String response = mvcActions.andReturn().getResponse().getContentAsString();
        Integer id = JsonPath.parse(response).read("$.id");

        mockMvc.perform(delete("/types/" + id))
                .andDo(print())
                .andExpect(status().isOk());
    }

	
}
