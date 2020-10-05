package ru.rest.task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@SpringBootApplication
public class TaskApplication {

    public static void main(String[] args) {

//        SpringApplication.run(TaskApplication.class, args);
        String URL ="http://91.241.64.178:7081/api/users";
        StringBuilder result = new StringBuilder();
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = template.getForEntity(URL, String.class);
        System.out.println(resp.getBody());
        HttpHeaders head = resp.getHeaders();
        final List<String> cookie = head.get("Set-Cookie");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.setContentType(MediaType.APPLICATION_JSON);
        assert cookie != null;
        headers.set("Cookie", String.join(";", cookie));
        System.out.println(head);
        User us3 = new User(3L, "James", "Brown", (byte) 31);
        HttpEntity<User> request = new HttpEntity<>(us3, headers);
        ResponseEntity<String> response1 = template.postForEntity(URL, request, String.class);
        result.append(response1.getBody());
        us3.setName("Thomas");
        us3.setLastName("Shelby");
        HttpEntity<User> putRequest = new HttpEntity<>(us3, headers);
        ResponseEntity<String> response2 = template.exchange(URL, HttpMethod.PUT, putRequest, String.class);
        result.append(response2.getBody());
        ResponseEntity<String> response3 = template.exchange(URL + "/3", HttpMethod.DELETE, putRequest, String.class);
        result.append(response3.getBody());
        System.out.println(result);
    }

}
