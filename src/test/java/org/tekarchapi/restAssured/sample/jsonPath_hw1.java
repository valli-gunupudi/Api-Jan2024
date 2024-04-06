package org.tekarchapi.restAssured.sample;

import java.io.File;
import java.util.List;

import org.testng.annotations.Test;
import io.restassured.path.json.JsonPath;

public class jsonPath_hw1 {
	@Test
	public void fileExtract() {
		File file = new File("./src/test/resources/RequestData/universities.json");
		JsonPath path = new JsonPath(file);
		
		//find value of username 
		List<String> data = path.getList("findAll().username");
		System.out.println(data);
		//find values of all students id
		List<Integer> stud_ids = path.getList("findAll().students.id");
		System.out.println(stud_ids);
		//find last value of students id
		int last_stud_id = path.get("findAll().students[1].id[1]");
		System.out.println(last_stud_id);
		//find username of 1st university
		String un = path.get("[0].username");
		System.out.println(un);
		//find name of 2nd university 
		String unv2Name = path.get("[1].name");
		System.out.println(unv2Name);
		//find number of universities in the list
		int size = path.get("size()");
		System.out.println(size);
		//find all marks of second student of 1st university
		List<Integer> stud_marks = path.getList("[0].students[1].marks");
		System.out.println(stud_marks);
		//find the second state(in the address) value of first student of 1st university
		String state = path.get("[0].students[0].adresss[1].state");
		System.out.println(state);
		//find the second contact value of second student of 2nd university 
		String contact = path.get("[1].students[1].contact[1]");
		System.out.println(contact);
		//find all cities of second student of 2nd university
		List<String> cities = path.get("[1].students[1].adresss.findAll().state");
		System.out.println(cities);
		//find names of all students
		List<String> stud_names = path.get("findAll().students.name");
		System.out.println(stud_names);
		//find contacts of all students
		List<String> contacts = path.get("findAll().students.contact");
		System.out.println(contacts);
		//find addresses of first student of 1st university
		List<String> addrses = path.get("[0].students[0].adresss");
		System.out.println(addrses);
		//find number of addresses of second student of 2nd university 
		int sizeaddr = path.get("[1].students[1].adresss.size()");
		System.out.println(sizeaddr);
		//find the number of student records of 2nd university
		int studRecSize = path.get("[1].students.size()");
		System.out.println(studRecSize);
		//find the number of contacts of second student of 1st university
		int numCntcs = path.get("[0].students[1].contact.size()");
		System.out.println(numCntcs);		
	}
}
