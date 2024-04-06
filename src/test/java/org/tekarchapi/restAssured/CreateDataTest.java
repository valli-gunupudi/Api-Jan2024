package org.tekarchapi.restAssured;

import static org.hamcrest.Matchers.*;

import java.io.File;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;

public class CreateDataTest {

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
		return token;

	}

	@Test()
	public void addUserData() {
		File addData = new File("./src/test/resources/RequestData/addData.json");

		Header tokenHeader = new Header("token", logintoApi());
		Response res = RestAssured.given().contentType(ContentType.JSON).body(addData).header(tokenHeader).when()
				.post("/addData");
		res.then().statusCode(201);
		res.prettyPrint();
		res.then().body("status", is("success"));

	}

}
