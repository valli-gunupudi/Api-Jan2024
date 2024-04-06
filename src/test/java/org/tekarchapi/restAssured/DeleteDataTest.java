package org.tekarchapi.restAssured;

import static org.hamcrest.Matchers.*;

import java.io.File;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;

public class DeleteDataTest {

	@BeforeClass
	public void init() {
		RestAssured.baseURI = "https://us-central1-qa01-tekarch-accmanager.cloudfunctions.net";
	}

	public String logintoApi() {
		File file = new File("./src/test/resources/RequestData/loginData.json");

		Response res = RestAssured.given().contentType(ContentType.JSON).body(file).when().post("/login");
		res.then().statusCode(201);
		res.prettyPrint();
		String token = res.body().jsonPath().getString("[0].token");
		System.out.println("token=" + token);
		return token;

	}

	@Test()
	public void deleteUserData() {
		File deleteData = new File("./src/test/resources/RequestData/deleteData.json");

		Header tokenHeader = new Header("token", logintoApi());
		Response res = RestAssured.given().header(tokenHeader).contentType(ContentType.JSON).body(deleteData).when()
				.delete("/deleteData");
		res.then().statusCode(200);
		res.prettyPrint();
		res.then().body("status", is("success"));

		// get the total num of records
		// get all account no for the userid= JBFBUgrJ3VQaudaTXM0r
		// validate size=4 for the previous validation

	}

}
