package com.cognizant.pas.policy.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.pas.policy.feign.AuthorisingClient;
import com.cognizant.pas.policy.payload.request.CreatePolicyRequest;
import com.cognizant.pas.policy.payload.response.ConsumerBusinessDetails;
import com.cognizant.pas.policy.payload.response.MessageResponse;
import com.cognizant.pas.policy.repository.ConsumerPolicyRepository;
import com.cognizant.pas.policy.service.PolicyServiceImpl;

import lombok.extern.slf4j.Slf4j;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = PolicyController.class)
class PolicyControllerTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private AuthorisingClient authorisingClient;
	
	@MockBean
	private PolicyServiceImpl policyService;
	
	@MockBean
	private ConsumerPolicyRepository consumerPolicyRepository;
	
	private ConsumerBusinessDetails mockConsumerBusinessDetails;
	
	@BeforeEach
	public void setup() {
		mockConsumerBusinessDetails = new ConsumerBusinessDetails("fname", "lname", "dob", "bname", "pan", "email",
				"phone", "website", "bo", "validity", "aname", (long) 1, (long) 1, (long) 1, "bcat", "type", (long) 12,
				(long) 13, (long) 4, (long) 15, (long) 11);
		
	}

	@Test
	@DisplayName("Test Authorising client")
	void testClientNotNull() {
		assertThat(authorisingClient).isNotNull();
	}

	@Test
	@DisplayName("Test Mock MVC client")
	void testMockMvcNotNull() {
		assertThat(mockMvc).isNotNull();
	}

	@Test
	@DisplayName("Test PolicyServiceImpl client")
	void testServiceNotNull() {
		assertThat(policyService).isNotNull();
	}
	
	/*@Test
	void testCreatePolicy() {
		when(authorisingClient.authorizeTheRequest("@uthoriz@tionToken123")).thenReturn(true);
		
		MessageResponse messageResponse = new MessageResponse("Policy Has been Created with Policyconsumer Id : " + 1
		+ " .Thank You Very Much!!");
		String exampleCourseJson = "{\"consumerId\": \"1\",\"acceptedquotes\": \"54000 INR\"}";
		Mockito.when(policyService.createPolicy(Mockito.any(CreatePolicyRequest.class), )
		.thenReturn(messageResponse);
	}*/

}
