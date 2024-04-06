package org.tekarchapi.restAssured.pojo;

import static org.hamcrest.Matchers.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;


public class CRUDUsingPOJO {
	private static String mytoken=null;
	String user_id;
	String id;
	String upd_user_id;
	String upd_id;
	
	@BeforeClass
	public void init() {
		RestAssured.baseURI="https://us-central1-qa01-tekarch-accmanager.cloudfunctions.net";
	}
	

	@Test
	public void logintoTekarchRequest() {
		LoginRequestPOJO ob=new LoginRequestPOJO();
		ob.setUsername("rajanachandu@tekarch.com");
		ob.setPassword("Admin123");   //loading data as Java object
		
		Response response=RestAssured
		.given()
		.contentType(ContentType.JSON)
		.body(ob)
		.when()
		.post("login");
		response.then().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("Schema/loginResponseSchema.json"));
		
		LoginResponsePOJO[] list= response.as(LoginResponsePOJO[].class);
		System.out.println("size="+list.length);
		System.out.println(list[0].getToken());
		System.out.println(list[0].getUserid());
		
		mytoken=list[0].getToken();
		System.out.println("token recieved="+mytoken);
	}
	
	@Test(dependsOnMethods = "logintoTekarchRequest")
	public void getUserData() {
		Header tokenHeader=new Header("token", mytoken);		
		
		Response res= RestAssured
		.given()
			.header(tokenHeader)
		.when()
		 .get("/getdata");
		res.then().statusCode(200);
		//res.prettyPrint();
		res.then().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("Schema/GetUserResponseSchema.json"));
		
		GetResponsePOJO[] list= res.as(GetResponsePOJO[].class);
		System.out.println("****POJO way****");
		System.out.println("size="+list.length);
		System.out.println(list[0].getAccountno());
		System.out.println(list[0].getDepartmentno());
		System.out.println(list[0].getSalary());
		System.out.println(list[0].getPincode());
		System.out.println(list[0].getUserid());
		System.out.println(list[0].getId());
		System.out.println("****JSON way****");
		System.out.println("total records="+res.body().jsonPath().get("size()"));
		System.out.println("first record account no="+res.body().jsonPath().get("[0].accountno"));
		System.out.println("first record dept num="+res.body().jsonPath().get("[0].departmentno"));
		System.out.println("first record salary="+res.body().jsonPath().get("[0].salary"));
		System.out.println("first record user id="+res.body().jsonPath().get("[0].userid"));
		System.out.println("first record id="+res.body().jsonPath().get("[0].id"));
	}
	
	@Test(dependsOnMethods = "getUserData")
	public void addUserData() {	
		Header tokenHeader=new Header("token", mytoken);
		
		CreateReqPOJO createOb = new CreateReqPOJO();
		createOb.setAccountno("TA-Balli01");
		createOb.setDepartmentno("1");
		createOb.setSalary("5000");
		createOb.setPincode("5555");
		
		Response res= RestAssured
		.given()
			.contentType(ContentType.JSON)
			//.body(addData)
			.body(createOb)
			.header(tokenHeader)
		.when()
		 .post("/addData");
		res.then().statusCode(201);
		res.prettyPrint();
		res.then().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("Schema/SuccessResponseSchema.json"));
		//res.then().body("status",is("success"));		
	}
	
	@Test(dependsOnMethods = "addUserData")
	public void getUserDataAfterAdd() {
		Header tokenHeader=new Header("token", mytoken);		
		
		Response res= RestAssured
		.given()
			.header(tokenHeader)
		.when()
		 .get("/getdata");
		res.then().statusCode(200);
		//res.prettyPrint();
		res.then().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("Schema/GetUserResponseSchema.json"));
		
		GetResponsePOJO[] list= res.as(GetResponsePOJO[].class);
		System.out.println("****POJO way****");
		System.out.println("size="+list.length);
		System.out.println(list[0].getAccountno());
		System.out.println(list[0].getDepartmentno());
		System.out.println(list[0].getSalary());
		System.out.println(list[0].getPincode());
		System.out.println(list[0].getUserid());
		System.out.println(list[0].getId());
		
		user_id = list[0].getUserid();
		id = list[0].getId();
	}
	
	@Test(dependsOnMethods = "getUserDataAfterAdd")
	public void updateUserData() {
		UpdReqPOJO updOb = new UpdReqPOJO();
		updOb.setAccountno("TA-Galli01");
		updOb.setDepartmentno("1");
		updOb.setSalary("5000");
		updOb.setPincode("5555");
		updOb.setUserid(user_id);
		updOb.setId(id);
		
		Header tokenHeader=new Header("token", mytoken);
		Response res = RestAssured
				.given()
					.contentType(ContentType.JSON)
					.body(updOb)
					.header(tokenHeader)
				.when()
					.put("/updateData");
		res.then().statusCode(200);
		res.prettyPrint();
		res.then().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("Schema/SuccessResponseSchema.json"));
	}
	
	@Test(dependsOnMethods = "updateUserData")
	public void getUserDataAfterUpd() {
		Header tokenHeader=new Header("token", mytoken);		
		
		Response res= RestAssured
		.given()
			.header(tokenHeader)
		.when()
		 .get("/getdata");
		res.then().statusCode(200);
		//res.prettyPrint();
		res.then().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("Schema/GetUserResponseSchema.json"));
		
		GetResponsePOJO[] list= res.as(GetResponsePOJO[].class);
		System.out.println("****POJO way****");
		System.out.println("size="+list.length);
		System.out.println(list[0].getAccountno());
		System.out.println(list[0].getDepartmentno());
		System.out.println(list[0].getSalary());
		System.out.println(list[0].getPincode());
		System.out.println(list[0].getUserid());
		System.out.println(list[0].getId());
		
		upd_user_id = list[0].getUserid();
		upd_id = list[0].getId();
	}
	
	
	@Test(dependsOnMethods = "getUserDataAfterUpd")
	public void deleteUserData() {
		Header tokenHeader = new Header("token", mytoken);
		UpdReqPOJO delOb = new UpdReqPOJO();
		delOb.setUserid(upd_user_id);
		delOb.setId(upd_id);
		
		Response res = RestAssured
				.given()
					.header(tokenHeader)
					.contentType(ContentType.JSON)
					.body(delOb)
				.when()
					.delete("/deleteData");
		res.then().statusCode(200);
		res.prettyPrint();
		res.then().body("status", is("success"));
	}
	
	@Test(dependsOnMethods = "deleteUserData")
	public void getUserDataAfterDel() {
		Header tokenHeader=new Header("token", mytoken);		
		
		Response res= RestAssured
		.given()
			.header(tokenHeader)
		.when()
		 .get("/getdata");
		res.then().statusCode(200);
		//res.prettyPrint();
		res.then().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("Schema/GetUserResponseSchema.json"));
		
		GetResponsePOJO[] list= res.as(GetResponsePOJO[].class);
		System.out.println("****POJO way****");
		System.out.println("size="+list.length);
		System.out.println(list[0].getAccountno());
		System.out.println(list[0].getDepartmentno());
		System.out.println(list[0].getSalary());
		System.out.println(list[0].getPincode());
		System.out.println(list[0].getUserid());
		System.out.println(list[0].getId());
	}
}