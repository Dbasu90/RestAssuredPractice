package com.tests;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

public class GetRequest {

    @Test
    public void getUserList(){
        Response response=given().baseUri("https://reqres.in")
                .when()
                .log().all()
                .get("/api/users")
                .then().extract().response();
        response.prettyPrint();
        System.out.println("The response time is :"+response.getTime());
        Assert.assertEquals(response.getStatusCode(),200);
        Assert.assertEquals(response.jsonPath().getString("data[0].email"),"george.bluth@reqres.in");
        Assert.assertEquals(response.jsonPath().getString("support.text"),"To keep ReqRes free, contributions towards server costs are appreciated!");
    }

    @Test
    public void getSpecificUser(){
        Response response=given().baseUri("https://reqres.in")
                .pathParams("id","2")
                .when()
                .log().all()
                .get("/api/users/{id}")
                .then().extract().response();
        response.prettyPrint();
        System.out.println("The response time is :"+response.getTime());
        Assert.assertEquals(response.getStatusCode(),200);
    }


}
