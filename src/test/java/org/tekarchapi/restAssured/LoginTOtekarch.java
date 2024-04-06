package org.tekarchapi.restAssured;

import java.io.File;

import static org.hamcrest.Matchers.*;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import io.restassured.response.Response;

public class LoginTOtekarch {
	private static String mytoken = null;

	@BeforeTest
	public void setUp() {
		RestAssured.baseURI = "https://us-central1-qa01-tekarch-accmanager.cloudfunctions.net/";
	}

	@Test
	public void logintoTekarchRequest() {
		File file = new File("./src/test/resources/RequestData/loginData.json");

		Response response = RestAssured.given().contentType(ContentType.JSON)
				// .body("{\"username\":\"divyashree@ta.com\",\"password\":\"divya@123\"}")
				.body(file).when().post("login");

		response.then().statusCode(201).contentType(ContentType.JSON).time(lessThan(4000L));
		response.prettyPrint();

		mytoken = response.body().jsonPath().get("[0].token");
		System.out.println("token recieved=" + mytoken);

	}

}
