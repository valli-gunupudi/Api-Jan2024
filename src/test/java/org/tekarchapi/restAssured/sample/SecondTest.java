package org.tekarchapi.restAssured.sample;

import java.util.List;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class SecondTest {
	@Test
	public void methodOne() {
		RestAssured.baseURI="https://fakerestapi.azurewebsites.net/";
		RestAssured.basePath="api/v1/";
		
		RestAssured
		.given()
		.when()
			.get("Activities")
		.then()
			.statusCode(200)
			.time(Matchers.lessThan(4000L))
		.body("[0].title",Matchers.is("Activity 1"))
		.body("[10].id",Matchers.greaterThan(4));
	}
	
	@Test
	public void methodTwo() {
		RestAssured.baseURI="https://fakerestapi.azurewebsites.net/";
		RestAssured.basePath="api/v1/";
		
		RequestSpecification req = RestAssured.given();
		Response res = req.when().get("Activities");
		res.then().statusCode(200).time(Matchers.lessThan(4000L));
	
		String ContentType=res.contentType();
		System.out.println(ContentType);
		System.out.println(res.getStatusCode());
		
		//System.out.println(res.body().asString());
		res.body().prettyPrint();
		
		String due = res.body().jsonPath().getString("[1].dueDate");
		System.out.println("due date="+due);
		List<Integer> data = res.body().jsonPath().getList("findAll().id");
		System.out.println(data);
		int size = res.body().jsonPath().get("size()");
		System.out.println(size);
		
		List<Integer> dataId = res.body().jsonPath().getList("findAll(){it->it.id<10}");
		System.out.println(dataId);
	}

}
