package kubiak.lofapp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class LofappApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void checkUserRegistration() throws Exception{
		MockHttpServletRequestBuilder registerUser = post("/register")
				.param("username","jnowak")
				.param("password","password!")
				.param("matchingPassword","password!")
				.param("mail","jnowak@yahoo.com")
				.param("firstName","Jan")
				.param("lastName","Nowak");

		mockMvc.perform(registerUser)
				.andExpect(model().hasNoErrors());
	}
	@Test
	public void checkUserRegistrationWhenPasswordIsTooShort() throws Exception{
		MockHttpServletRequestBuilder registerUser = post("/register")
				.param("username","jnowak")
				.param("password","pas")
				.param("matchingPassword","pas")
				.param("mail","jnowak@yahoo.com")
				.param("firstName","Jan")
				.param("lastName","Nowak");

		mockMvc.perform(registerUser)
				.andExpect(model().hasErrors());
	}

}
