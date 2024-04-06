package org.tekarchapi.restAssured;

import java.io.File;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;

public class GetDataTest {
	String newtoken;

	@BeforeClass
	public void init() {
		RestAssured.baseURI = "https://us-central1-qa01-tekarch-accmanager.cloudfunctions.net";
	}

	@Test
	public void logintoApi() {
		File file = new File("./src/test/resources/RequestData/loginData.json");
		Response res = RestAssured.given().contentType(ContentType.JSON).body(file).when().post("/login");
		res.then().statusCode(201);
		res.prettyPrint();
		newtoken = res.body().jsonPath().getString("[0].token");

	}

	@Test(dependsOnMethods = "logintoApi")
	public void getUserData() {
		Header tokenHeader = new Header("token", newtoken);

		Response res = RestAssured.given().header(tokenHeader).when().get("/getdata");
		res.then().statusCode(200);
		// res.prettyPrint();

		System.out.println("total records=" + res.body().jsonPath().get("size()"));
		System.out.println("first record account no=" + res.body().jsonPath().get("[0].accountno"));
		System.out.println("first record dept num=" + res.body().jsonPath().get("[0].departmentno"));
		System.out.println("first record salary=" + res.body().jsonPath().get("[0].salary"));
		System.out.println("first record user id=" + res.body().jsonPath().get("[0].userid"));
		System.out.println("first record id=" + res.body().jsonPath().get("[0].id"));

		/*
		 * ArrayList<String>
		 * data=res.body().jsonPath().get("findAll{it->it.accountno=='DA-eclipse'}");
		 * System.out.println(data);
		 * System.out.println("total number of records with DA-eclipse account num="
		 * +data.size());
		 * 
		 * 
		 * 
		 * System.out.println("newly added record user id="+
		 * res.body().jsonPath().get("findAll{it->it.accountno=='DA-eclipse'}[0].userid"
		 * )); System.out.println("newly added record id="+
		 * res.body().jsonPath().get("findAll{it->it.accountno=='DA-eclipse'}[0].id"));
		 */

	}

}
