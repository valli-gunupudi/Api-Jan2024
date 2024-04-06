package org.tekarchapi.restAssured.sample;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class SettingPathParam {

	@Test
	public void pathParametersSetting() {
		RestAssured.baseURI = "https://fakerestapi.azurewebsites.net/";
		RestAssured.basePath = "api/v1/";

		RequestSpecification req = RestAssured.given().pathParam("idvalue", "20");
		//.queryParam("key","23245");
		
		Response res = req.when().get("Activities/{idvalue}");
		res.then().statusCode(200).time(Matchers.lessThan(4000L));
		res.body().prettyPrint();

	}
}
