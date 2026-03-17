package com.example;

public class Main 
{
    public static void main( String[] args ) throws Exception
    {
        HttpClientService clientService = new HttpClientService();
        String getResponse = clientService.get("https://jsonplaceholder.typicode.com/posts/1");
        System.out.println(getResponse);

        String body = """
                {
                    "title": "Hello",
                    "body": "This is a test",
                    "userId": 1
                }
                """;
        String postResponse = clientService.post("https://jsonplaceholder.typicode.com/posts", body);
        System.out.println("POST Response: " + postResponse);
    }
}
