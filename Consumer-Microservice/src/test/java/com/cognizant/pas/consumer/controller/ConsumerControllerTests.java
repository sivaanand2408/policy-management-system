package com.cognizant.pas.consumer.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.cognizant.pas.consumer.feign.AuthorisingClient;
import com.cognizant.pas.consumer.model.Consumer;
import com.cognizant.pas.consumer.model.Property;
import com.cognizant.pas.consumer.payload.request.BusinessPropertyRequest;
import com.cognizant.pas.consumer.payload.request.ConsumerBusinessRequest;
import com.cognizant.pas.consumer.payload.response.BusinessPropertyDetails;
import com.cognizant.pas.consumer.payload.response.ConsumerBusinessDetails;
import com.cognizant.pas.consumer.payload.response.MessageResponse;
import com.cognizant.pas.consumer.repository.BusinessRepository;
import com.cognizant.pas.consumer.repository.ConsumerRepository;
import com.cognizant.pas.consumer.repository.PropertyRepository;
import com.cognizant.pas.consumer.service.ConsumerServiceImpl;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = ConsumerController.class)
class ConsumerControllerTests {

	@MockBean
	AuthorisingClient authorisingClient;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ConsumerServiceImpl consumerService;

	@MockBean
	private BusinessRepository businessRepository;

	@MockBean
	private ConsumerRepository consumerRepository;

	@MockBean
	private PropertyRepository propertyRepository;

	private ConsumerBusinessDetails mockConsumerBusinessDetails;
	private Property property;
	private Consumer consumer;

	@BeforeEach
	public void setup() {
		mockConsumerBusinessDetails = new ConsumerBusinessDetails("fname", "lname", "dob", "bname", "pan", "email",
				"phone", "website", "bo", "validity", "aname", (long) 1, (long) 1, (long) 1, "bcat", "type", (long) 12,
				(long) 13, (long) 4, (long) 15, (long) 11);
		
		property = new Property((long) 1, (long) 1, (long) 1, "Fire", "Building", "string", "Owner", "string", (long) 2,
				(long) 10000, (long) 5, (long) 1, (long) 1);
		
		consumer = new Consumer((long) 1,"fname", "lname", "dob", "bname", "pan", "email",
				"phone", "website", "bo", "validity", "aname", (long) 1);
		
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
	@DisplayName("Test ConsumerServiceImpl client")
	void testServiceNotNull() {
		assertThat(consumerService).isNotNull();
	}

	@Test
	public void getConsumerBusinessNoConsumerFoundWithValidToken() throws Exception {
		when(authorisingClient.authorizeTheRequest("@uthoriz@tionToken123")).thenReturn(true);

		Mockito.when(consumerService.viewConsumerBusiness(Mockito.anyLong())).thenReturn(mockConsumerBusinessDetails);
		this.mockMvc
				.perform(get("/viewConsumerBusiness/{consumerid}", (long) 1).header("Authorization",
						"@uthoriz@tionToken123"))
				.andExpect(status().isBadRequest())
				.andExpect(content().string("{\"message\":\"Sorry!!, No Consumer Found!!\"}"));
	}
	
	@Test
	public void getConsumerBusinessWithInvalidToken() throws Exception {
		when(authorisingClient.authorizeTheRequest("wrongToken")).thenReturn(false);
		
		this.mockMvc
				.perform(get("/viewConsumerBusiness/{consumerid}", (long) 1).header("Authorization",
						"wrongToken"))
				.andExpect(status().isForbidden());
	}
	
	

	@Test
	public void getConsumerPropertyNoPropertyFound() throws Exception {
		when(authorisingClient.authorizeTheRequest("@uthoriz@tionToken123")).thenReturn(true);

		Mockito.when(consumerService.viewConsumerProperty(Mockito.anyLong(), Mockito.anyLong())).thenReturn(property);
		this.mockMvc
				.perform(get("/viewConsumerProperty/{consumerid}/{propertyid}", (long) 1, (long) 1)
						.header("Authorization", "@uthoriz@tionToken123"))
				.andExpect(status().isBadRequest())
				.andExpect(content().string("{\"message\":\"Sorry!!, No Property Found!!\"}"));
	}
	
	@Test
	public void getConsumerPropertyWithInvalidToken() throws Exception {
		when(authorisingClient.authorizeTheRequest("wrongToken")).thenReturn(false);

		this.mockMvc
				.perform(get("/viewConsumerProperty/{consumerid}/{propertyid}", (long) 1, (long) 1)
						.header("Authorization", "wrongToken"))
				.andExpect(status().isForbidden());
	}

	@Test
	public void createConsumerBusiness() throws Exception {
		when(authorisingClient.authorizeTheRequest("@uthoriz@tionToken123")).thenReturn(true);

		MessageResponse messageResponse = new MessageResponse(
				"SuccessFully Created Consumer with Consumer ID " + "1" + "and Business ID " + "1" + ".Thank you!");
		String exampleCourseJson = "{\"agentid\": \"1\",\"agentname\": \"sankit\",\"businessage\": \"3\",\"businesscategory\": \"Consultant\",\"businessname\": \"SankitConsultant\",\"businessoverview\": \"SankitConsultant\",\"businessturnover\": \"15\",\"businesstype\": \"Service Business\",\"capitalinvested\": \"1\",\"dob\": \"1999/11/21\",\"email\": \"akshitchandora9812@gmail.com\",\"firstname\": \"Akshit\",\"lastname\": \"Kumar\",\"pandetails\": \"BHYOC2000F\",\"phone\": \"9915560741\",\"totalemployees\": \"75\",\"validity\": \"1 year\",\"website\": \"akshit.com\"}";
		Mockito.when(consumerService.createConsumerBusiness(Mockito.any(ConsumerBusinessRequest.class)))
				.thenReturn(messageResponse);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/createConsumerBusiness")
				.accept(MediaType.APPLICATION_JSON).content(exampleCourseJson).contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "@uthoriz@tionToken123");
		this.mockMvc.perform(requestBuilder).andExpect(status().isOk())
				.andExpect(content().string("{\"message\":\"Sorry!!, You are Not Eligibile for Insurance\"}"));
	}
	
	@Test
	public void createConsumerBusinessWithInvalidToken() throws Exception {
		when(authorisingClient.authorizeTheRequest("wrongToken")).thenReturn(false);

		String exampleCourseJson = "{\"agentid\": \"1\",\"agentname\": \"sankit\",\"businessage\": \"3\",\"businesscategory\": \"Consultant\",\"businessname\": \"SankitConsultant\",\"businessoverview\": \"SankitConsultant\",\"businessturnover\": \"15\",\"businesstype\": \"Service Business\",\"capitalinvested\": \"1\",\"dob\": \"1999/11/21\",\"email\": \"akshitchandora9812@gmail.com\",\"firstname\": \"Akshit\",\"lastname\": \"Kumar\",\"pandetails\": \"BHYOC2000F\",\"phone\": \"9915560741\",\"totalemployees\": \"75\",\"validity\": \"1 year\",\"website\": \"akshit.com\"}";

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/createConsumerBusiness")
				.accept(MediaType.APPLICATION_JSON).content(exampleCourseJson).contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "wrongToken");
		this.mockMvc.perform(requestBuilder).andExpect(status().isForbidden());
	}

	@Test
	public void createBusinessProperty() throws Exception {
		when(authorisingClient.authorizeTheRequest("@uthoriz@tionToken123")).thenReturn(true);

		MessageResponse messageResponse = new MessageResponse(
				"SuccessFully Created Business Property with Property Id " + "1" + ".Thank you!");
		String ExampleJson = "{\"buildingage\": 2,\"buildingsqft\": \"string\",\"buildingstoreys\": \"string\",\"buildingtype\": \"Owner\",\"businessId\": 1,\"consumerId\": 1,\"costoftheasset\": 5,\"insurancetype\": \"Fire\",\"propertytype\": \"Building\",\"salvagevalue\": 1,\"usefullifeoftheAsset\": 1}";
		Mockito.when(consumerService.createBusinessProperty(Mockito.any(BusinessPropertyRequest.class)))
				.thenReturn(messageResponse);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/createBusinessProperty")
				.accept(MediaType.APPLICATION_JSON).content(ExampleJson).contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "@uthoriz@tionToken123");
		this.mockMvc.perform(requestBuilder).andExpect(status().isOk())
				.andExpect(content().string("{\"message\":\"Sorry!!, No Consumer Found!!\"}"));
	}
	
	@Test
	public void createBusinessPropertyWithInvalidToken() throws Exception {
		when(authorisingClient.authorizeTheRequest("wrongToken")).thenReturn(false);

		String ExampleJson = "{\"buildingage\": 2,\"buildingsqft\": \"string\",\"buildingstoreys\": \"string\",\"buildingtype\": \"Owner\",\"businessId\": 1,\"consumerId\": 1,\"costoftheasset\": 5,\"insurancetype\": \"Fire\",\"propertytype\": \"Building\",\"salvagevalue\": 1,\"usefullifeoftheAsset\": 1}";
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/createBusinessProperty")
				.accept(MediaType.APPLICATION_JSON).content(ExampleJson).contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "wrongToken");
		this.mockMvc.perform(requestBuilder).andExpect(status().isForbidden());
	}
	
	@Test
	public void updateConsumerBusiness() throws Exception {
		when(authorisingClient.authorizeTheRequest("@uthoriz@tionToken123")).thenReturn(true);

		MessageResponse messageResponse = new MessageResponse(
				"Successfully Updated Consumer with Consumer ID: " + "1"
						+ "and Business ID: " + "1" + " . Thank you!");
		String exampleCourseJson = "{\"agentid\": \"1\",\"agentname\": \"sankit\",\"businessage\": \"3\",\"businesscategory\": \"Consultant\",\"businessname\": \"SankitConsultant\",\"businessoverview\": \"SankitConsultant\",\"businessturnover\": \"15\",\"businesstype\": \"Service Business\",\"capitalinvested\": \"1\",\"dob\": \"1999/11/21\",\"email\": \"akshitchandora9812@gmail.com\",\"firstname\": \"Akshit\",\"lastname\": \"Kumar\",\"pandetails\": \"BHYOC2000F\",\"phone\": \"9915560741\",\"totalemployees\": \"75\",\"validity\": \"1 year\",\"website\": \"akshit.com\",\"businessid\": \"1\",\"consumerId\": \"1\",\"businessvalue\": \"1\"}";
		Mockito.when(consumerService.updateConsumerBusiness(Mockito.any(ConsumerBusinessDetails.class)))
				.thenReturn(messageResponse);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/updateConsumerBusiness")
				.accept(MediaType.APPLICATION_JSON).content(exampleCourseJson).contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "@uthoriz@tionToken123");
		this.mockMvc.perform(requestBuilder).andExpect(status().isOk())
				.andExpect(content().string("{\"message\":\"Sorry!!, No Consumer Found!!\"}"));
	}
	
	@Test
	public void updateConsumerBusinessWithInvalidToken() throws Exception {
		when(authorisingClient.authorizeTheRequest("wrongToken")).thenReturn(false);

		String exampleCourseJson = "{\"agentid\": \"1\",\"agentname\": \"sankit\",\"businessage\": \"3\",\"businesscategory\": \"Consultant\",\"businessname\": \"SankitConsultant\",\"businessoverview\": \"SankitConsultant\",\"businessturnover\": \"15\",\"businesstype\": \"Service Business\",\"capitalinvested\": \"1\",\"dob\": \"1999/11/21\",\"email\": \"akshitchandora9812@gmail.com\",\"firstname\": \"Akshit\",\"lastname\": \"Kumar\",\"pandetails\": \"BHYOC2000F\",\"phone\": \"9915560741\",\"totalemployees\": \"75\",\"validity\": \"1 year\",\"website\": \"akshit.com\",\"businessid\": \"1\",\"consumerId\": \"1\",\"businessvalue\": \"1\"}";

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/updateConsumerBusiness")
				.accept(MediaType.APPLICATION_JSON).content(exampleCourseJson).contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "wrongToken");
		this.mockMvc.perform(requestBuilder).andExpect(status().isForbidden());
	}

	@Test
	public void updateBusinessProperty() throws Exception {
		when(authorisingClient.authorizeTheRequest("@uthoriz@tionToken123")).thenReturn(true);

		MessageResponse messageResponse = new MessageResponse(
				"SuccessFully Updated Business Property with Property Id " + "1" + ".Thank you!");
		String ExampleJson = "{\"buildingage\": 2,\"buildingsqft\": \"string\",\"buildingstoreys\": \"string\",\"buildingtype\": \"Owner\",\"businessId\": 1,\"consumerId\": 1,\"costoftheasset\": 5,\"insurancetype\": \"Fire\",\"propertytype\": \"Building\",\"salvagevalue\": 1,\"usefullifeoftheAsset\": 1,\"propertyId\": 1}";
		Mockito.when(consumerService.updateBusinessProperty(Mockito.any(BusinessPropertyDetails.class)))
				.thenReturn(messageResponse);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/updateBusinessProperty")
				.accept(MediaType.APPLICATION_JSON).content(ExampleJson).contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "@uthoriz@tionToken123");
		this.mockMvc.perform(requestBuilder).andExpect(status().isOk())
				.andExpect(content().string("{\"message\":\"Sorry!!, No Property Found!!\"}"));
	}
	
	@Test
	public void updateBusinessPropertyWithInvalidToken() throws Exception {
		when(authorisingClient.authorizeTheRequest("wrongToken")).thenReturn(false);

		String ExampleJson = "{\"buildingage\": 2,\"buildingsqft\": \"string\",\"buildingstoreys\": \"string\",\"buildingtype\": \"Owner\",\"businessId\": 1,\"consumerId\": 1,\"costoftheasset\": 5,\"insurancetype\": \"Fire\",\"propertytype\": \"Building\",\"salvagevalue\": 1,\"usefullifeoftheAsset\": 1,\"propertyId\": 1}";

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/updateBusinessProperty")
				.accept(MediaType.APPLICATION_JSON).content(ExampleJson).contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "wrongToken");
		this.mockMvc.perform(requestBuilder).andExpect(status().isForbidden());
	}

	@Test
	public void viewConsumerBusinessByPolicy() throws Exception {
		when(authorisingClient.authorizeTheRequest("@uthoriz@tionToken123")).thenReturn(true);

		Mockito.when(consumerService.viewConsumerBusiness(Mockito.anyLong())).thenReturn(mockConsumerBusinessDetails);
		this.mockMvc
				.perform(get("/viewConsumerBusinessByPolicy/{consumerid}", (long) 1).header("Authorization",
						"@uthoriz@tionToken123"))
				.andExpect(status().isOk())
				.andExpect(content().string("{\"firstname\":\"fname\",\"lastname\":\"lname\",\"dob\":\"dob\",\"businessname\":\"bname\",\"pandetails\":\"pan\",\"email\":\"email\",\"phone\":\"phone\",\"website\":\"website\",\"businessoverview\":\"bo\",\"validity\":\"validity\",\"agentname\":\"aname\",\"agentid\":1,\"businessid\":1,\"consumerId\":1,\"businesscategory\":\"bcat\",\"businesstype\":\"type\",\"businessturnover\":12,\"capitalinvested\":13,\"totalemployees\":4,\"businessvalue\":15,\"businessage\":11}"));
	}
	
	@Test
	public void viewConsumerBusinessByPolicyWithInvalidToken() throws Exception {
		when(authorisingClient.authorizeTheRequest("wrongToken")).thenReturn(false);

		Mockito.when(consumerService.viewConsumerBusiness(Mockito.anyLong())).thenReturn(mockConsumerBusinessDetails);
		this.mockMvc
				.perform(get("/viewConsumerBusinessByPolicy/{consumerid}", (long) 1).header("Authorization",
						"wrongToken"))
				.andExpect(status().isForbidden());
	}

}