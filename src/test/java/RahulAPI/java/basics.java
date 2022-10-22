package RahulAPI.java;


import Files.PostBody;
import Files.PutParameters;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class basics {

      PutParameters placeIdClass = new PutParameters();

    @Test(priority = 1)
    protected void PostTest() {

        //.body(new String(Files.readAllBytes(paths.get("RealPath"))))    it's another way of retrieving json file directly through the text file

        RestAssured.baseURI = "https://rahulshettyacademy.com" ;
        String response = given().
                     log().all().queryParam("key", "qaclick123").header("Content-Type" , "application/json")
                     .body(PostBody.AddPlace()).
                when().
                     post("/maps/api/place/add/json").
                then().
                     assertThat().statusCode(200).body("scope" , equalTo("APP"))
                    .header("Server" , "Apache/2.4.41 (Ubuntu)").extract().response().asString();

        System.out.println(response);

        JsonPath js = new JsonPath(response); //for parsing json
        placeIdClass.setPlaceId(js.getString("place_id"));

        System.out.println("\n\n\n\n\n\n"+ placeIdClass.getPlaceId() +"\n\n\n\n\n");


    }
    @Test(priority = 2)
    public void UpdateAddress (){
        //Update Place
       placeIdClass.step1();
        given().
                log().all().queryParam("key" , "qaclick123").header("Content-Type" , "application/json").
                body(placeIdClass.getPlaceId()).
                when().
                put("/maps/api/place/update/json").
                then().
                assertThat().log().all().statusCode(200).body("msg" , equalTo("Address successfully updated"));

    }
    @Test(priority = 3)
    public void GetNewAddress(){
        //Get Address
       System.out.println("\n\n\n\n\n\n"+ placeIdClass.getPlaceId() +"\n\n\n\n\n");
        JsonPath jsonPath = new JsonPath(placeIdClass.getPlaceId());
        String Place_id = jsonPath.get("place_id");
        given().
                log().all().queryParam("key" , "qaclick123").queryParam("place_id" ,Place_id).
                when().
                get("/maps/api/place/get/json").
               then().log().all().assertThat().statusCode(200).body("address",equalTo(jsonPath.get("address")));


    }

    @Test(priority = 4)
    public void DeleteNewAddress(){
        //Delete Address
        System.out.println("\n\n\n\n\n\n"+ placeIdClass.getPlaceId() +"\n\n\n\n\n");
        JsonPath jsonPath = new JsonPath(placeIdClass.getPlaceId());
        String Place_id = jsonPath.get("place_id");
        given().
                log().all().queryParam("key" , "qaclick123").body(placeIdClass.getPlaceId()).
        when().
                delete("/maps/api/place/delete/json").
        then().
                log().all().assertThat().statusCode(200).body("status" , equalTo("OK"));

    }


    }
