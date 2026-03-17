package com.example;

public class Main 
{
    public static void main( String[] args ) throws Exception
    {
        OpenAiService openAiService = new OpenAiService();
        System.out.println(openAiService.chat("say hello to me, my name is jan"));
    }
}
