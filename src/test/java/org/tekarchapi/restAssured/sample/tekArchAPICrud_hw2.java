package org.tekarchapi.restAssured.sample;

import static org.hamcrest.Matchers.is;

import java.io.File;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;

public class tekArchAPICrud_hw2 {
	String newtoken;
	Header tokenHeader;
	String user_id;
	String id;
	String upd_user_id;
	String upd_id;

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

		tokenHeader = new Header("token", newtoken);
		Response res = RestAssured.given().header(tokenHeader).when().get("/getdata");
		res.then().statusCode(200);
		// res.prettyPrint();
		System.out.println("total records=" + res.body().jsonPath().get("size()"));
		System.out.println("first record account no=" + res.body().jsonPath().get("[0].accountno"));
		System.out.println("first record dept num=" + res.body().jsonPath().get("[0].departmentno"));
		System.out.println("first record salary=" + res.body().jsonPath().get("[0].salary"));
		System.out.println("first record user id=" + res.body().jsonPath().get("[0].userid"));
		System.out.println("first record id=" + res.body().jsonPath().get("[0].id"));

		user_id = res.body().jsonPath().get("[0].userid");
		id = res.body().jsonPath().get("[0].id");
		/*
		 * ArrayList<String>
		 * data=res.body().jsonPath().get("findAll{it->it.accountno=='DA-eclipse'}");
		 * System.out.println(data);
		 * System.out.println("total number of records with DA-eclipse account num="
		 * +data.size());
		 * 
		 * System.out.println("newly added record user id="+
		 * res.body().jsonPath().get("findAll{it->it.accountno=='DA-eclipse'}[0].userid"
		 * )); System.out.println("newly added record id="+
		 * res.body().jsonPath().get("findAll{it->it.accountno=='DA-eclipse'}[0].id"));
		 */
	}

	@Test(dependsOnMethods = "getUserData")
	public void addUserData() {
		File addData = new File("./src/test/resources/RequestData/addData.json");
		tokenHeader = new Header("token", newtoken);
		Response res = RestAssured.given().contentType(ContentType.JSON).body(addData).header(tokenHeader).when()
				.post("/addData");
		res.then().statusCode(201);
		res.prettyPrint();
		res.then().body("status", is("success"));
	}
	
	@Test(dependsOnMethods = "addUserData")
	public void getUserAddData() {

		tokenHeader = new Header("token", newtoken);
		Response res = RestAssured.given().header(tokenHeader).when().get("/getdata");
		res.then().statusCode(200);
		// res.prettyPrint();
		System.out.println("total records=" + res.body().jsonPath().get("size()"));
		System.out.println("first record account no=" + res.body().jsonPath().get("[0].accountno"));
		System.out.println("first record dept num=" + res.body().jsonPath().get("[0].departmentno"));
		System.out.println("first record salary=" + res.body().jsonPath().get("[0].salary"));
		System.out.println("first record user id=" + res.body().jsonPath().get("[0].userid"));
		System.out.println("first record id=" + res.body().jsonPath().get("[0].id"));

		user_id = res.body().jsonPath().get("[0].userid");
		id = res.body().jsonPath().get("[0].id");
		/*
		 * ArrayList<String>
		 * data=res.body().jsonPath().get("findAll{it->it.accountno=='DA-eclipse'}");
		 * System.out.println(data);
		 * System.out.println("total number of records with DA-eclipse account num="
		 * +data.size());
		 * 
		 * System.out.println("newly added record user id="+
		 * res.body().jsonPath().get("findAll{it->it.accountno=='DA-eclipse'}[0].userid"
		 * )); System.out.println("newly added record id="+
		 * res.body().jsonPath().get("findAll{it->it.accountno=='DA-eclipse'}[0].id"));
		 */
	}

	@Test(dependsOnMethods = "getUserAddData")
	public void updateUserData() {
		// File updateData=new File("./src/test/resources/RequestData/updateData.json");

		tokenHeader = new Header("token", newtoken);
		Response res = RestAssured.given().contentType(ContentType.JSON).body(
				"{\"accountno\":\"TA-Balli01\",\"departmentno\":\"5\",\"salary\":\"1000\",\"pincode\": \"111111\",\"userid\":\""
						+ user_id + "\",\"id\":\"" + id + "\"}")
				.header(tokenHeader).when().put("/updateData");
		res.then().statusCode(200);
		res.prettyPrint();
		res.then().body("status", is("success"));

		// get the total num of records
		// get all account no for the userid= JBFBUgrJ3VQaudaTXM0r
		// validate size=4 for the previous validation
	}

	@Test(dependsOnMethods = "updateUserData")
	public void getUserUpdData() {

		tokenHeader = new Header("token", newtoken);
		Response res = RestAssured.given().header(tokenHeader).when().get("/getdata");
		res.then().statusCode(200);
		// res.prettyPrint();
		System.out.println("total records=" + res.body().jsonPath().get("size()"));
		System.out.println("first record account no=" + res.body().jsonPath().get("[0].accountno"));
		System.out.println("first record dept num=" + res.body().jsonPath().get("[0].departmentno"));
		System.out.println("first record salary=" + res.body().jsonPath().get("[0].salary"));
		System.out.println("first record user id=" + res.body().jsonPath().get("[0].userid"));
		System.out.println("first record id=" + res.body().jsonPath().get("[0].id"));

		upd_user_id = res.body().jsonPath().get("[0].userid");
		upd_id = res.body().jsonPath().get("[0].id");
		/*
		 * ArrayList<String>
		 * data=res.body().jsonPath().get("findAll{it->it.accountno=='DA-eclipse'}");
		 * System.out.println(data);
		 * System.out.println("total number of records with DA-eclipse account num="
		 * +data.size());
		 * 
		 * System.out.println("newly added record user id="+
		 * res.body().jsonPath().get("findAll{it->it.accountno=='DA-eclipse'}[0].userid"
		 * )); System.out.println("newly added record id="+
		 * res.body().jsonPath().get("findAll{it->it.accountno=='DA-eclipse'}[0].id"));
		 */
	}

	@Test(dependsOnMethods = "getUserUpdData")
	public void deleteUserData() {
		tokenHeader = new Header("token", newtoken);
		Response res = RestAssured.given().header(tokenHeader).contentType(ContentType.JSON)
				.body("{\"userid\": \"" + upd_user_id + "\",\"id\": \"" + upd_id + "\"}").when().delete("/deleteData");
		res.then().statusCode(200);
		res.prettyPrint();
		res.then().body("status", is("success"));
		// get the total num of records
		// get all account no for the userid= JBFBUgrJ3VQaudaTXM0r
		// validate size=4 for the previous validation
	}
	
	@Test(dependsOnMethods = "deleteUserData")
	public void getUserDelData() {

		tokenHeader = new Header("token", newtoken);
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
		 * System.out.println("newly added record user id="+
		 * res.body().jsonPath().get("findAll{it->it.accountno=='DA-eclipse'}[0].userid"
		 * )); System.out.println("newly added record id="+
		 * res.body().jsonPath().get("findAll{it->it.accountno=='DA-eclipse'}[0].id"));
		 */
	}
}
