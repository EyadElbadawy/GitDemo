package RequestsParam;


import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class basics {

      PutParameters placeIdClass = new PutParameters();




        RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addQueryParam("key", "qaclick123").
                setContentType(ContentType.JSON).build();




      ResponseSpecification respec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();


    @Test(priority = 1)
    protected void PostTest() {

        //.body(new String(Files.readAllBytes(paths.get("RealPath"))))    it's another way of retrieving json file directly through the text file


        RequestSpecification res = given().
                spec(req).body(PostBody.AddPlace());

               String response = res.when().
                     post("/maps/api/place/add/json").
                then().
                     spec(respec).extract().response().asString();

        System.out.println(response);

        JsonPath js = new JsonPath(response); //for parsing json
        placeIdClass.setPlaceId(js.getString("place_id"));

        System.out.println("\n\n\n\n\n\n"+ placeIdClass.getPlaceId() +"\n\n\n\n\n");


    }
    @Test(priority = 2)
    public void UpdateAddress (){
        //Update Place
       placeIdClass.step1();
        RequestSpecification res = given().
                spec(req).
                body(placeIdClass.getPlaceId());

               String response =  res.when().
                put("/maps/api/place/update/json").
                then().
                       spec(respec).extract().response().asString();

    }
    @Test(priority = 3)
    public void GetNewAddress(){
        //Get Address
       System.out.println("\n\n\n\n\n\n"+ placeIdClass.getPlaceId() +"\n\n\n\n\n");
        JsonPath jsonPath = new JsonPath(placeIdClass.getPlaceId());
        String Place_id = jsonPath.get("place_id");
        RequestSpecification res = given().
                spec(req).queryParam("place_id" ,Place_id);

        String response =  res.when().
                get("/maps/api/place/get/json").
               then()
                .spec(respec).extract().response().asString();


    }

    @Test(priority = 4)
    public void DeleteNewAddress(){
        //Delete Address
        System.out.println("\n\n\n\n\n\n"+ placeIdClass.getPlaceId() +"\n\n\n\n\n");
        JsonPath jsonPath = new JsonPath(placeIdClass.getPlaceId());
        String Place_id = jsonPath.get("place_id");
        RequestSpecification res = given().
                spec(req).body(placeIdClass.getPlaceId());

        String response =  res.when().
                delete("/maps/api/place/delete/json").
        then().
                spec(respec).extract().response().asString();

    }


    }
