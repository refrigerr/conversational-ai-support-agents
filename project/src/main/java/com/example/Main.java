package com.example;

import java.util.Scanner;

import com.example.agents.Agent;
import com.example.agents.BillingAgent;
import com.example.agents.OrchestratorAgent;
import com.example.agents.TechnicalAgent;
import com.example.documentsManager.DocLoader;
import com.example.documentsManager.DocRetriever;
import com.example.tools.ToolExecutor;

public class Main 
{
    public static void main( String[] args )
    {
        
        OpenAiService openAiService = new OpenAiService();
        CustomerData customerData = new CustomerData();
        ToolExecutor toolExecutor = new ToolExecutor(customerData);
        ConversationHistory conversationHistory = new ConversationHistory();
        DocLoader docLoader = new DocLoader();
        DocRetriever docRetriever = new DocRetriever(docLoader.getChunks(), openAiService);
        //DocRetriever docRetriever = new DocRetriever(docLoader.getRawDocuments(), openAiService);

        Agent orchestrator = new OrchestratorAgent(openAiService, conversationHistory);
        Agent billingAgent = new BillingAgent(openAiService, conversationHistory, toolExecutor);
        Agent technicalAgent = new TechnicalAgent(openAiService, conversationHistory, docRetriever);

        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome, type 'exit' to quit app.");

        while (true) {
            System.out.print("\nYou: ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Goodbye!");
                break;
            }

            String route = orchestrator.chat(input);
            //System.out.println("\n[Routing to: " + route + "]\n");
            String agentLabel = switch (route) {
                case "TECHNICAL" -> "Technical";
                case "BILLING"   -> "Billing";
                default          -> null;
            };

            String response = switch (route) {
                case "TECHNICAL" -> technicalAgent.chat(input);
                case "BILLING"   -> billingAgent.chat(input);
                default          -> "I'm sorry, I cannot assist with that. Please contact our general support team.";
            };

            System.out.print("\n");
            if (agentLabel != null) {
                System.out.print(agentLabel + " ");
            }

            System.out.println("Agent: " + response);
        }

        scanner.close();
        
    }
}
