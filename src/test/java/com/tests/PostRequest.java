package com.tests;

import static io.restassured.RestAssured.*;

import com.github.javafaker.Faker;
import com.pojo.Employee;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class PostRequest {

    //1. Passing the Json as a string in the body
    @Test
    public void postRequest(){
       Response response= given().contentType(ContentType.JSON).baseUri("http://localhost:3000")
                .body("{\n" +
                        "\t  \"id\":158,\n" +
                        "\t  \"jobTitleName\":\"Senior Associate\",\n" +
                        "\t  \"firstName\":\"Debasmita\",\n" +
                        "\t  \"lastName\":\"Basu\",\n" +
                        "\t  \"emailAddress\":\"b.debasmita@gmail.com\"\n" +
                        "\t}")
                .log().all()
                .when()
                .post("/Employees/")
                .then().extract().response();
       response.prettyPrint();
       Assert.assertEquals(response.getStatusCode(),201);
       System.out.println(response.getHeader("Content-Type"));
    }

    //Reading from external file and passing it as String in Body
    @Test
    public void postRequestFile() throws IOException {
        String reqBody = new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir") + "/src/test/resources/JSONRequest/Request.json")));
        String finalBody=reqBody.replace("1", String.valueOf(new Faker().number().numberBetween(10,100))).replace("Amanda", new Faker().name().firstName());

        Response response=given().header("Content-Type","application/json").baseUri("http://localhost:3000")
                .body(finalBody)
                .log().all()
                .when()
                .post("/Employees/")
                .then().extract().response();
        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(),201);
    }

    //Using JsonObject and JsonArray and sending them as String/Map
    @Test
    public void postRequestJson(){
        JSONObject obj= new JSONObject();
        obj.put("id", new Faker().number().numberBetween(100,1000));
        obj.put("fname","Amanda");
        obj.put("lname","Rosley");
        obj.put("email","ama.rosley@test.com");
        obj.accumulate("email","ama23.rose@test.com");

        JSONArray listOfJobs= new JSONArray();
        listOfJobs.put("tester");
        listOfJobs.put("content writer");

        obj.put("jobs",listOfJobs);

        Response response=given().header("Content-Type","application/json").baseUri("http://localhost:3000")
                .body(obj.toMap())
                .log().all()
                .when()
                .post("/Employees/")
                .then().extract().response();
        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(),201);
    }

    //using POJO
    @Test
    public void postRequestPOJO(){
        Employee employee=new Employee(400,"Jose","Moorey","jose.more@test.com", Arrays.asList("blogger","photographer"));
        Response response=given().header("Content-Type","application/json").baseUri("http://localhost:3000")
                .body(employee)
                .log().all()
                .when()
                .post("/Employees/")
                .then().extract().response();
        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(),201);

        System.out.println(response.jsonPath().getString("email"));
        List<String> jobs=response.jsonPath().getList("jobs");
        System.out.println("The jobs are:");
        jobs.forEach(System.out::println);

        //validating json schema
        response.then().body(JsonSchemaValidator.matchesJsonSchema(new File(System.getProperty("user.dir")+"/src/test/resources/Schema/schema.json")));
    }
}
