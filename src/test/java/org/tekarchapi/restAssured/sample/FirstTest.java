package org.tekarchapi.restAssured.sample;

import org.testng.annotations.Test;

import io.restassured.RestAssured;

public class FirstTest {
	@Test
	public void FirstTestRequest() {
		RestAssured
		.given()
		.when()
			.get("https://fakerestapi.azurewebsites.net//api/v1/Activities")
		.then()
			.statusCode(200);
	}
}