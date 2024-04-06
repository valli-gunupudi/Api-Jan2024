package org.tekarchapi.restAssured.pojo;

import java.util.HashMap;
import java.util.List;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class LoginTOtekarch {
	private static String mytoken=null;
	
	
	@BeforeTest
	public void setUp() {
		RestAssured.baseURI="https://us-central1-qa01-tekarch-accmanager.cloudfunctions.net/";
		//RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
	}
	
	@Test
	public void logintoTekarchRequest() {
		/*LoginRequestPOJO ob=new LoginRequestPOJO();
		ob.setUsername("divyashree@ta.com");
		ob.setPassword("divya");*/   //loading data as Java object
		
		HashMap<String, String> ob=new HashMap<String, String>(); // loading data as map object
		ob.put("username", "rajanachandu@tekarch.com");
		ob.put("password", "Admin123");
		
		Response response=RestAssured
		.given()
		.log().all()
		.contentType(ContentType.JSON)
		.body(ob)
		//.body(file)
		.when()
		.post("login");
		response.then().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("Schema/loginResponseSchema.json"));
		response.then().log().all();
		System.out.println("********started here*********");
		
		LoginResponsePOJO[] list= response.as(LoginResponsePOJO[].class);
		System.out.println("size="+list.length);
		System.out.println(list[0].getToken());
		System.out.println(list[0].getUserid());
		
		List<LoginResponsePOJO> list2=response.as(new TypeRef<List<LoginResponsePOJO>>(){});
		System.out.println(list2.get(0).getToken());
		System.out.println(list2.get(0).getUserid());
		response.prettyPrint();
		
		mytoken=list[0].getToken();
		System.out.println("token recieved="+mytoken);
	}
}