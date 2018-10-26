package com.prs;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.prs.business.product.Product;
import com.prs.business.purchaserequest.PurchaseRequest;
import com.prs.business.purchaserequest.PurchaseRequestLineItem;
import com.prs.business.user.User;
import com.prs.business.vendor.Vendor;
import com.prs.utility.JsonResponse;
import com.prs.utility.LocalDateJsonConverter;
import com.prs.utility.LocalDateTimeJsonConverter;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PRSUserTests extends PrsWebApplicationTests {
	
	//Tests all basic crud functions
	private static int uID = -1;
	private static int vID = -1;
	private static int pID = -1;
	private static int prID = -1;
	private static int prliID = -1;

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void testa1UserListRequest() throws Exception {
		JsonResponse j = this.restTemplate.getForObject("http://localhost:" + port + "/Users/List", JsonResponse.class);
		assertThat(j.getMessage()).contains(JsonResponse.SUCCESS);
	}

	@Test
	public void testb2UserAddRequest() throws Exception {
		// set headers
		User u1 = new User("username", "pwd", "fnam", "lname", "phone", "email", true, true);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		Gson gson = new Gson();
		String json = gson.toJson(u1); // serializes target to Json

		HttpEntity<String> entity = new HttpEntity<String>(json, headers);
		JsonResponse j = this.restTemplate.postForObject("http://localhost:" + port + "/Users/Add", entity,
				JsonResponse.class);
		assertThat(j.getMessage()).contains(JsonResponse.SUCCESS);
		uID = ((LinkedHashMap<String, Integer>) j.getData()).get("id");
	}

	@Test
	public void testc3UserGetRequest() throws Exception {
		JsonResponse j = this.restTemplate.getForObject("http://localhost:" + port + "/Users/Get/" + uID,
				JsonResponse.class);
		assertThat(j.getMessage()).contains(JsonResponse.SUCCESS);
	}

	@Test
	public void testd4UserUpdateRequest() throws Exception {
		// set headers
		User u1 = new User(uID, "username", "pwd", "fnam", "lname", "phone", "email", true, true);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		Gson gson = new Gson();
		String json = gson.toJson(u1); // serializes target to Json

		HttpEntity<String> entity = new HttpEntity<String>(json, headers);

		JsonResponse j = this.restTemplate.postForObject("http://localhost:" + port + "/Users/Change", entity,
				JsonResponse.class);
		assertThat(j.getMessage()).contains(JsonResponse.SUCCESS);
	}

	@Test
	public void teste5UserAuthenticateRequest() throws Exception {
		// set headers
		User u1 = new User("username", "pwd");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		Gson gson = new Gson();
		String json = gson.toJson(u1); // serializes target to Json

		HttpEntity<String> entity = new HttpEntity<String>(json, headers);

		JsonResponse j = this.restTemplate.postForObject("http://localhost:" + port + "/Users/Authenticate", entity,
				JsonResponse.class);
		assertThat(j.getMessage()).contains(JsonResponse.SUCCESS);
	}

	@Test
	public void testf6VendorListRequest() throws Exception {
		JsonResponse j = this.restTemplate.getForObject("http://localhost:" + port + "/Vendors/List",
				JsonResponse.class);
		assertThat(j.getMessage()).contains(JsonResponse.SUCCESS);
	}

	@Test
	public void testg7VendorAddRequest() throws Exception {
		// set headers
		Vendor v1 = new Vendor("code", "name", "address", "city", "st", "zip", "phonenumber", "email", true);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		Gson gson = new Gson();
		String json = gson.toJson(v1); // serializes target to Json

		HttpEntity<String> entity = new HttpEntity<String>(json, headers);
		JsonResponse j = this.restTemplate.postForObject("http://localhost:" + port + "/Vendors/Add", entity,
				JsonResponse.class);
		assertThat(j.getMessage()).contains(JsonResponse.SUCCESS);
		vID = ((LinkedHashMap<String, Integer>) j.getData()).get("id");
	}

	@Test
	public void testh8VendorGetRequest() throws Exception {
		JsonResponse j = this.restTemplate.getForObject("http://localhost:" + port + "/Vendors/Get/" + vID,
				JsonResponse.class);
		assertThat(j.getMessage()).contains(JsonResponse.SUCCESS);
	}

	@Test
	public void testi9VendorUpdateRequest() throws Exception {
		// set headers
		Vendor v1 = new Vendor(vID, "code", "name", "address", "city", "st", "zip", "phonenumber", "email", true);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		Gson gson = new Gson();
		String json = gson.toJson(v1); // serializes target to Json

		HttpEntity<String> entity = new HttpEntity<String>(json, headers);
		JsonResponse j = this.restTemplate.postForObject("http://localhost:" + port + "/Vendors/Change", entity,
				JsonResponse.class);
		assertThat(j.getMessage()).contains(JsonResponse.SUCCESS);
	}

	@Test
	public void testj10ProductListRequest() throws Exception {
		JsonResponse j = this.restTemplate.getForObject("http://localhost:" + port + "/Products/List",
				JsonResponse.class);
		assertThat(j.getMessage()).contains(JsonResponse.SUCCESS);
	}

	@Test
	public void testk11ProductAddRequest() throws Exception {
		// set headers
		Vendor v1 = new Vendor(vID, "code", "name", "address", "city", "st", "zip", "phonenumber", "email", true);
		Product p1 = new Product(v1, "partNumber", "name", 999.99);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		Gson gson = new Gson();
		String json = gson.toJson(p1); // serializes target to Json

		HttpEntity<String> entity = new HttpEntity<String>(json, headers);
		JsonResponse j = this.restTemplate.postForObject("http://localhost:" + port + "/Products/Add", entity,
				JsonResponse.class);
		assertThat(j.getMessage()).contains(JsonResponse.SUCCESS);
		pID = ((LinkedHashMap<String, Integer>) j.getData()).get("id");
	}

	@Test
	public void testl12ProductGetRequest() throws Exception {
		JsonResponse j = this.restTemplate.getForObject("http://localhost:" + port + "/Products/Get/" + pID,
				JsonResponse.class);
		assertThat(j.getMessage()).contains(JsonResponse.SUCCESS);
	}

	@Test
	public void testm13ProductUpdateRequest() throws Exception {
		// set headers
		Vendor v1 = new Vendor(vID, "code", "name", "address", "city", "st", "zip", "phonenumber", "email", true);
		Product p1 = new Product(pID, v1, "partNumber", "name", 999.99);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		Gson gson = new Gson();
		String json = gson.toJson(p1); // serializes target to Json

		HttpEntity<String> entity = new HttpEntity<String>(json, headers);
		JsonResponse j = this.restTemplate.postForObject("http://localhost:" + port + "/Products/Change", entity,
				JsonResponse.class);
		assertThat(j.getMessage()).contains(JsonResponse.SUCCESS);
	}

	@Test
	public void testn14PurchaseRequestListRequest() throws Exception {
		JsonResponse j = this.restTemplate.getForObject("http://localhost:" + port + "/PurchaseRequests/List",
				JsonResponse.class);
		assertThat(j.getMessage()).contains(JsonResponse.SUCCESS);
	}

	@Test
	public void testo15PurchaseRequestAddRequest() throws Exception {
		// set headers
		User u1 = new User(uID, "username", "pwd", "fnam", "lname", "phone", "email", true, true);
		PurchaseRequest pr1 = new PurchaseRequest(u1, "description", "justification", "2018-10-04", "deliveryMode",
				"status", 999.99, "2018-10-04T17:55:29", "rfr");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		// have to use this to parse the localdate and localdatetime
		Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls()
				.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeJsonConverter())
				.registerTypeAdapter(LocalDate.class, new LocalDateJsonConverter()).create();
		String json = gson.toJson(pr1); // serializes target to Json
		HttpEntity<String> entity = new HttpEntity<String>(json, headers);
		JsonResponse j = this.restTemplate.postForObject("http://localhost:" + port + "/PurchaseRequests/Add", entity,
				JsonResponse.class);
		assertThat(j.getMessage()).contains(JsonResponse.SUCCESS);
		prID = ((LinkedHashMap<String, Integer>) j.getData()).get("id");
	}

	@Test
	public void testp16PurchaseRequestGetRequest() throws Exception {
		JsonResponse j = this.restTemplate.getForObject("http://localhost:" + port + "/PurchaseRequests/Get/" + prID,
				JsonResponse.class);
		assertThat(j.getMessage()).contains(JsonResponse.SUCCESS);
	}

	@Test
	public void testq17PurchaseRequestUpdateRequest() throws Exception {
		// set headers
		User u1 = new User(uID, "username", "pwd", "fnam", "lname", "phone", "email", true, true);
		PurchaseRequest pr1 = new PurchaseRequest(prID, u1, "description", "justification", "2018-10-04",
				"deliveryMode", "status", 999.99, "2018-10-04T17:55:29", "rfr");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		// have to use this to parse the localdate and localdatetime
		Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls()
				.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeJsonConverter())
				.registerTypeAdapter(LocalDate.class, new LocalDateJsonConverter()).create();
		String json = gson.toJson(pr1); // serializes target to Json

		HttpEntity<String> entity = new HttpEntity<String>(json, headers);
		JsonResponse j = this.restTemplate.postForObject("http://localhost:" + port + "/PurchaseRequests/Change",
				entity, JsonResponse.class);
		assertThat(j.getMessage()).contains(JsonResponse.SUCCESS);
	}

	@Test
	public void testr18PRLIListRequest() throws Exception {
		JsonResponse j = this.restTemplate.getForObject("http://localhost:" + port + "/PurchaseRequestLineItems/List",
				JsonResponse.class);
		assertThat(j.getMessage()).contains(JsonResponse.SUCCESS);
	}

	@Test
	public void tests19PRLIAddRequest() throws Exception {
		// set headers
		User u1 = new User(uID, "username", "pwd", "fnam", "lname", "phone", "email", true, true);
		PurchaseRequest pr1 = new PurchaseRequest(prID, u1, "description", "justification", "2018-10-04",
				"deliveryMode", "status", 999.99, "2018-10-04T17:55:29", "rfr");
		Vendor v1 = new Vendor(vID, "code", "name", "address", "city", "st", "zip", "phonenumber", "email", true);
		Product p1 = new Product(pID, v1, "partNumber", "name", 999.99);
		PurchaseRequestLineItem prli1 = new PurchaseRequestLineItem(pr1, p1, 5);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		// have to use this to parse the localdate and localdatetime
		Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls()
				.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeJsonConverter())
				.registerTypeAdapter(LocalDate.class, new LocalDateJsonConverter()).create();
		String json = gson.toJson(prli1); // serializes target to Json

		HttpEntity<String> entity = new HttpEntity<String>(json, headers);
		JsonResponse j = this.restTemplate.postForObject("http://localhost:" + port + "/PurchaseRequestLineItems/Add",
				entity, JsonResponse.class);
		assertThat(j.getMessage()).contains(JsonResponse.SUCCESS);
		prliID = ((LinkedHashMap<String, Integer>) j.getData()).get("id");
	}

	@Test
	public void testt20PRLIGetRequest() throws Exception {
		JsonResponse j = this.restTemplate.getForObject(
				"http://localhost:" + port + "/PurchaseRequestLineItems/Get/" + prliID, JsonResponse.class);
		assertThat(j.getMessage()).contains(JsonResponse.SUCCESS);
	}

	@Test
	public void testu21PRLIUpdateRequest() throws Exception {
		// set headers
		User u1 = new User(uID, "username", "pwd", "fnam", "lname", "phone", "email", true, true);
		PurchaseRequest pr1 = new PurchaseRequest(prID, u1, "description", "justification", "2018-10-04",
				"deliveryMode", "status", 999.99, "2018-10-04T17:55:29", "rfr");
		Vendor v1 = new Vendor(vID, "code", "name", "address", "city", "st", "zip", "phonenumber", "email", true);
		Product p1 = new Product(pID, v1, "partNumber", "name", 999.99);
		PurchaseRequestLineItem prli1 = new PurchaseRequestLineItem(prliID, pr1, p1, 5);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		// have to use this to parse the localdate and localdatetime
		Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls()
				.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeJsonConverter())
				.registerTypeAdapter(LocalDate.class, new LocalDateJsonConverter()).create();
		String json = gson.toJson(prli1); // serializes target to Json

		HttpEntity<String> entity = new HttpEntity<String>(json, headers);
		JsonResponse j = this.restTemplate.postForObject(
				"http://localhost:" + port + "/PurchaseRequestLineItems/Change", entity, JsonResponse.class);
		assertThat(j.getMessage()).contains(JsonResponse.SUCCESS);
	}

	@Test
	public void testv22PRLIRemoveRequest() throws Exception {
		System.out.println(prliID);
		// set headers
		User u1 = new User(uID, "username", "pwd", "fnam", "lname", "phone", "email", true, true);
		PurchaseRequest pr1 = new PurchaseRequest(prID, u1, "description", "justification", "2018-10-04",
				"deliveryMode", "status", 999.99, "2018-10-04T17:55:29", "rfr");
		Vendor v1 = new Vendor(vID, "code", "name", "address", "city", "st", "zip", "phonenumber", "email", true);
		Product p1 = new Product(pID, v1, "partNumber", "name", 999.99);
		PurchaseRequestLineItem prli1 = new PurchaseRequestLineItem(prliID, pr1, p1, 5);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		// have to use this to parse the localdate and localdatetime
		Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls()
				.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeJsonConverter())
				.registerTypeAdapter(LocalDate.class, new LocalDateJsonConverter()).create();
		String json = gson.toJson(prli1); // serializes target to Json

		HttpEntity<String> entity = new HttpEntity<String>(json, headers);
		JsonResponse j = this.restTemplate.postForObject(
				"http://localhost:" + port + "/PurchaseRequestLineItems/Remove", entity, JsonResponse.class);
		assertThat(j.getMessage()).contains(JsonResponse.SUCCESS);
	}

	@Test
	public void testw23PurchaseRequestRemoveRequest() throws Exception {
		System.out.println(prID);
		// set headers
		User u1 = new User(uID, "username", "pwd", "fnam", "lname", "phone", "email", true, true);
		PurchaseRequest pr1 = new PurchaseRequest(prID, u1, "description", "justification", "2018-10-04",
				"deliveryMode", "status", 999.99, "2018-10-04T17:55:29", "rfr");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		// have to use this to parse the localdate and localdatetime
		Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls()
				.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeJsonConverter())
				.registerTypeAdapter(LocalDate.class, new LocalDateJsonConverter()).create();
		String json = gson.toJson(pr1); // serializes target to Json

		HttpEntity<String> entity = new HttpEntity<String>(json, headers);
		JsonResponse j = this.restTemplate.postForObject("http://localhost:" + port + "/PurchaseRequests/Remove",
				entity, JsonResponse.class);
		assertThat(j.getMessage()).contains(JsonResponse.SUCCESS);
	}

	@Test
	public void testx24ProductRemoveRequest() throws Exception {
		System.out.println(pID);
		// set headers
		Vendor v1 = new Vendor(vID, "code", "name", "address", "city", "st", "zip", "phonenumber", "email", true);
		Product p1 = new Product(pID, v1, "partNumber", "name", 999.99);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		Gson gson = new Gson();
		String json = gson.toJson(p1); // serializes target to Json

		HttpEntity<String> entity = new HttpEntity<String>(json, headers);
		JsonResponse j = this.restTemplate.postForObject("http://localhost:" + port + "/Products/Remove", entity,
				JsonResponse.class);
		assertThat(j.getMessage()).contains(JsonResponse.SUCCESS);
	}

	@Test
	public void testy25VendorRemoveRequest() throws Exception {
		System.out.println(vID);
		// set headers
		Vendor v1 = new Vendor(vID, "code", "name", "address", "city", "st", "zip", "phonenumber", "email", true);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		Gson gson = new Gson();
		String json = gson.toJson(v1); // serializes target to Json

		HttpEntity<String> entity = new HttpEntity<String>(json, headers);
		JsonResponse j = this.restTemplate.postForObject("http://localhost:" + port + "/Vendors/Remove", entity,
				JsonResponse.class);
		assertThat(j.getMessage()).contains(JsonResponse.SUCCESS);
	}

	@Test
	public void testz26UserRemoveRequest() throws Exception {
		System.out.println(uID);
		// set headers
		User u1 = new User(uID, "username", "pwd", "fnam", "lname", "phone", "email", true, true);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		Gson gson = new Gson();
		String json = gson.toJson(u1); // serializes target to Json

		HttpEntity<String> entity = new HttpEntity<String>(json, headers);

		JsonResponse j = this.restTemplate.postForObject("http://localhost:" + port + "/Users/Remove", entity,
				JsonResponse.class);
		assertThat(j.getMessage()).contains(JsonResponse.SUCCESS);
	}
}