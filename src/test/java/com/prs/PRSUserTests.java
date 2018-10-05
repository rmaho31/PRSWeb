package com.prs;

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
public class PRSUserTests extends PrsWebApplicationTests{
	
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
        assertNotNull(this.restTemplate.getForObject("http://localhost:" + port + "/Users/List",
                	  JsonResponse.class));
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
    	JsonResponse u2 = null;
    	u2 = this.restTemplate.postForObject("http://localhost:" + port + "/Users/Add", entity,
             JsonResponse.class);
        assertNotNull(u2);
        uID = u2.getObjectID();
    }
    
    @Test
    public void testc3UserGetRequest() throws Exception {
        assertNotNull(this.restTemplate.getForObject("http://localhost:" + port + "/Users/"+uID,
                	  User.class));
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
    	
        assertNotNull(this.restTemplate.postForObject("http://localhost:" + port + "/Users/Change", entity,
                	  User.class));
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
    	
        assertNotNull(this.restTemplate.postForObject("http://localhost:" + port + "/Users/Authenticate", entity,
                	  User.class));
    }
    
    @Test
    public void testf6VendorListRequest() throws Exception {
        assertNotNull(this.restTemplate.getForObject("http://localhost:" + port + "/Vendors/List",
                	  Iterable.class));
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
    	Vendor v2 = this.restTemplate.postForObject("http://localhost:" + port + "/Vendors/Add", entity,
    				Vendor.class);
        assertThat(v2.getCode()).contains("code");
        vID = v2.getId();
    }
    
    @Test
    public void testh8VendorGetRequest() throws Exception {
    	Vendor v2 = this.restTemplate.getForObject("http://localhost:" + port + "/Vendors/Get?id="+vID,
                	Vendor.class);
    	assertThat(v2.getId()).isEqualTo(vID);
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
    	
        Vendor v2 = this.restTemplate.postForObject("http://localhost:" + port + "/Vendors/Change", entity,
        			Vendor.class);
        assertThat(v2.getCode()).contains("code");
    }
    
    @Test
    public void testj10ProductListRequest() throws Exception {
        assertNotNull(this.restTemplate.getForObject("http://localhost:" + port + "/Products/List",
                	  Iterable.class));
    }
    
    @Test
    public void testk11ProductAddRequest() throws Exception {
    	// set headers
    	Product p1 = new Product(this.restTemplate.getForObject("http://localhost:" + port + "/Vendors/Get?id="+vID,
    							 Vendor.class), "partNumber", "name", 999.99);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Gson gson = new Gson(); 
        String json = gson.toJson(p1); // serializes target to Json

        HttpEntity<String> entity = new HttpEntity<String>(json, headers);
    	Product p2 = this.restTemplate.postForObject("http://localhost:" + port + "/Products/Add", entity,
          	  		 Product.class);
        assertThat(p2.getPartNumber()).contains("partNumber");
        pID = p2.getId();
    }
    
    @Test
    public void testl12ProductGetRequest() throws Exception {
        assertNotNull(this.restTemplate.getForObject("http://localhost:" + port + "/Products/Get?id="+pID,
                	  Product.class));
    }
    
    @Test
    public void testm13ProductUpdateRequest() throws Exception {
    	// set headers
    	Product p1 = new Product(pID, this.restTemplate.getForObject("http://localhost:" + port + "/Vendors/Get?id="+vID,
				 				 Vendor.class), "partNumber", "name", 999.99);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Gson gson = new Gson(); 
        String json = gson.toJson(p1); // serializes target to Json

        HttpEntity<String> entity = new HttpEntity<String>(json, headers);
    	Product p2 = this.restTemplate.postForObject("http://localhost:" + port + "/Products/Change", entity,
                     Product.class);
        assertThat(p2.getId()).isEqualTo(pID);
    }
    
    @Test
    public void testn14PurchaseRequestListRequest() throws Exception {
        assertNotNull(this.restTemplate.getForObject("http://localhost:" + port + "/PurchaseRequests/List",
        			  Iterable.class));
    }
    
    @Test
    public void testo15PurchaseRequestAddRequest() throws Exception {
    	// set headers
    	PurchaseRequest pr1 = new PurchaseRequest(this.restTemplate.getForObject("http://localhost:" + port + "/Users/Get?id="+uID,
    							 				  User.class), "description", "justification", "2018-10-04", "deliveryMode", "status",
    											  999.99, "2018-10-04T17:55:29", "rfr");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        //have to use this to parse the localdate and localdatetime
        Gson gson = new GsonBuilder()
						.setPrettyPrinting()
						.serializeNulls()
						.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeJsonConverter())
						.registerTypeAdapter(LocalDate.class, new LocalDateJsonConverter())
						.create();
        String json = gson.toJson(pr1); // serializes target to Json
        HttpEntity<String> entity = new HttpEntity<String>(json, headers);
    	PurchaseRequest pr2 = this.restTemplate.postForObject("http://localhost:" + port + "/PurchaseRequests/Add", entity,
          	  				  PurchaseRequest.class);
        assertThat(pr2.getDescription()).contains("description");
        prID = pr2.getId();
    }
    
    @Test
    public void testp16PurchaseRequestGetRequest() throws Exception {
        assertNotNull(this.restTemplate.getForObject("http://localhost:" + port + "/PurchaseRequests/Get?id="+prID,
                	  PurchaseRequest.class));
    }
    
    @Test
    public void testq17PurchaseRequestUpdateRequest() throws Exception {
    	// set headers
    	PurchaseRequest pr1 = new PurchaseRequest(prID, this.restTemplate.getForObject("http://localhost:" + port + "/Users/Get?id="+uID,
				  								  User.class), "description", "justification", "2018-10-04", "deliveryMode", "status",
				  								  999.99, "2018-10-04T17:55:29", "rfr");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        //have to use this to parse the localdate and localdatetime
        Gson gson = new GsonBuilder()
						.setPrettyPrinting()
						.serializeNulls()
						.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeJsonConverter())
						.registerTypeAdapter(LocalDate.class, new LocalDateJsonConverter())
						.create();
        String json = gson.toJson(pr1); // serializes target to Json

        HttpEntity<String> entity = new HttpEntity<String>(json, headers);
    	PurchaseRequest pr2 = this.restTemplate.postForObject("http://localhost:" + port + "/PurchaseRequests/Change", entity,
                			  PurchaseRequest.class);
        assertThat(pr2.getId()).isEqualTo(prID);
    }
    
    @Test
    public void testr18PRLIListRequest() throws Exception {
        assertNotNull(this.restTemplate.getForObject("http://localhost:" + port + "/PurchaseRequestLineItems/List",
        			  Iterable.class));
    }
    
    @Test
    public void tests19PRLIAddRequest() throws Exception {
    	// set headers
    	PurchaseRequestLineItem prli1 = 
    			new PurchaseRequestLineItem(this.restTemplate.getForObject("http://localhost:" + port + "/PurchaseRequests/Get?id="+prID,
    							 			PurchaseRequest.class), this.restTemplate.getForObject("http://localhost:" + port + "/Products/Get?id="+pID,
    	    							 	Product.class), 5);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        //have to use this to parse the localdate and localdatetime
        Gson gson = new GsonBuilder()
        				.setPrettyPrinting()
        				.serializeNulls()
        				.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeJsonConverter())
        				.registerTypeAdapter(LocalDate.class, new LocalDateJsonConverter())
        				.create();
        String json = gson.toJson(prli1); // serializes target to Json

        HttpEntity<String> entity = new HttpEntity<String>(json, headers);
    	PurchaseRequestLineItem prli2 = this.restTemplate.postForObject("http://localhost:" + port + "/PurchaseRequestLineItems/Add", entity,
    									PurchaseRequestLineItem.class);
        assertThat(prli2.getQuantity()).isEqualTo(5);
        prliID = prli2.getId();
    }
    
    @Test
    public void testt20PRLIGetRequest() throws Exception {
        assertNotNull(this.restTemplate.getForObject("http://localhost:" + port + "/PurchaseRequestLineItems/Get?id="+prliID,
                	  PurchaseRequestLineItem.class));
    }
    
    @Test
    public void testu21PRLIUpdateRequest() throws Exception {
    	// set headers
    	PurchaseRequestLineItem prli1 = 
    			new PurchaseRequestLineItem(prliID, this.restTemplate.getForObject("http://localhost:" + port + "/PurchaseRequests/Get?id="+prID,
    							 			PurchaseRequest.class), this.restTemplate.getForObject("http://localhost:" + port + "/Products/Get?id="+pID,
    	    							 	Product.class), 5);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
     	//have to use this to parse the localdate and localdatetime
        Gson gson = new GsonBuilder()
						.setPrettyPrinting()
						.serializeNulls()
						.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeJsonConverter())
						.registerTypeAdapter(LocalDate.class, new LocalDateJsonConverter())
						.create(); 
        String json = gson.toJson(prli1); // serializes target to Json

        HttpEntity<String> entity = new HttpEntity<String>(json, headers);
    	PurchaseRequestLineItem prli2 = this.restTemplate.postForObject("http://localhost:" + port + "/PurchaseRequestLineItems/Change", entity,
                						PurchaseRequestLineItem.class);
        assertThat(prli2.getId()).isEqualTo(prliID);
    }
    
    @Test
    public void testv22PRLIRemoveRequest() throws Exception {
    	System.out.println(prliID);
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/PurchaseRequestLineItems/Remove?id="+prliID,
                   String.class)).contains("Deleted");
    }
    
    @Test
    public void testw23PurchaseRequestRemoveRequest() throws Exception {
    	System.out.println(prID);
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/PurchaseRequests/Remove?id="+prID,
                String.class)).contains("Deleted");
    }
    
    @Test
    public void testx24ProductRemoveRequest() throws Exception {
    	System.out.println(pID);
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/Products/Remove?id="+pID,
        		   String.class)).contains("Deleted");
    }
    
    @Test
    public void testy25VendorRemoveRequest() throws Exception {
    	System.out.println(vID);
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/Vendors/Remove?id="+vID,
                   String.class)).contains("Deleted");
    }
    
    @Test
    public void testz26UserRemoveRequest() throws Exception {
    	System.out.println(uID);
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/Users/Remove?id="+uID,
                   String.class)).contains("Deleted");
    }
}