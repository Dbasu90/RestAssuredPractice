package com.tests;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

public class DeleteRequest {

    @Test
    public void deleteRequest(){
        Response response=given().baseUri("http://localhost:3000")
                .pathParam("id","345")
                .log().all()
                .when()
                .delete("/Employees/{id}")
                .then().extract().response();
        Assert.assertEquals(response.getStatusCode(),200);
    }
}
