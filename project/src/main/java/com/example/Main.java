package com.example;

import com.example.CustomerData.BillingRecord;
import com.example.agents.Agent;
import com.example.agents.BillingAgent;
import com.example.tools.ToolExecutor;

public class Main 
{
    public static void main( String[] args )
    {
        
        OpenAiService openAiService = new OpenAiService();
        CustomerData customerData = new CustomerData();
        ToolExecutor toolExecutor = new ToolExecutor(customerData);
        ConversationHistory conversationHistory = new ConversationHistory();
        Agent agent = new BillingAgent(openAiService, conversationHistory, toolExecutor);
        // System.out.println(agent.chat("What is the billing history for customer_001?"));
        // System.out.println(agent.chat("Add a new billing for customer_001: date: 26.04.2004, amount: 25.99, for: new tv"));
        // System.out.println(agent.chat("What is the billing history for customer_001?"));
        System.out.println(agent.chat("What is the refund policy?"));
        
    }
}
