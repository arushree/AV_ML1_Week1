package utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.HashMap;


public class APIUtils {

    private static String baseUrl = "http://ec2-54-87-249-92.compute-1.amazonaws.com/api";


    public static Response get(String endPoint, HashMap<String, String> headers) {
        return (RestAssured.given().headers(headers).baseUri(baseUrl)).get(endPoint);
    }

    public static Response post(String endPoint, String jsonBody, HashMap<String, String> headers) {
        return (RestAssured.given().headers(headers).body(jsonBody).baseUri(baseUrl)).post(endPoint);
    }
}
