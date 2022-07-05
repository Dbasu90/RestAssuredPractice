package com.tests;

import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

public class UpdateRequest {
    private static String userId;
    @Test
    public void updateRequest(){
        JSONObject obj= new JSONObject();
        obj.put("firstname","Amuthan");
        obj.put("email","minibytes@test.com");

        Response response=given().header("Content-Type","application/json").baseUri("http://localhost:3000")
                .pathParam("id","345")
                .body(obj.toMap())
                .log().all()
                .when()
                .put("/Employees/{id}")
                .then().extract().response();
        response.prettyPrint();
        userId=response.jsonPath().getString("id");
        System.out.println(userId);
        System.out.println("Response Time is "+response.getTime());
        Assert.assertEquals(response.getStatusCode(),200);
    }

    @Test
    public void validateUpdatedRequest(){
        given().baseUri("http://localhost:3000")
                .pathParam("id",userId)
                .log().all()
                .when()
                .get("/Employees/{id}")
                .then().extract().response().jsonPath().getString("firstname").equalsIgnoreCase("amuthan");
    }
}
