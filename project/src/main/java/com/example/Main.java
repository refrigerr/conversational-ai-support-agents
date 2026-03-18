package com.example;

import java.util.List;

public class Main 
{
    public static void main( String[] args )
    {
        

        DocLoader docLoader = new DocLoader();
        List<String> chunks = docLoader.getChunks();

        for (String string : chunks) {
            System.out.println("Chunk:");
            System.out.println(string);
        }

        
    }
}
