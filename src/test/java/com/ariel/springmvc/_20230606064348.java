package com.ariel.springmvc;

import org.apache.tomcat.util.json.JSONParser;
import org.junit.Test;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * [发生post请求](project\_20230606062647\src\test\java\com\ariel\springmvc\_20230606064348.java)
 * <a href='project\_20230606062647\src\test\java\com\ariel\springmvc\_20230606064348.java' style='color:green;font-weight:bold;'>运行一下</a>
 *
 */
public class _20230606064348 {

    @Test
    public void test() throws IOException, InterruptedException {
        HttpResponse<String> response = HttpClient.newHttpClient().send(
                HttpRequest.newBuilder()
                        .header("Content-type", "application/json")
                        .uri(URI.create("http://localhost:8080/test/hello"))
                        .POST(HttpRequest.BodyPublishers.ofString("{\"hello\":123}")).build(),
                HttpResponse.BodyHandlers.ofString());
        System.out.println("response.body() = " + response.body());
    }

}
