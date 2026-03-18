package com.example;

import com.example.CustomerData.BillingRecord;

public class Main 
{
    public static void main( String[] args )
    {
        
        CustomerData customerData = new CustomerData();

        System.out.println(customerData.getBillingHistory("customer_001"));
        customerData.addBillingRecord("customer_001", new BillingRecord("2026-06-06", "Test Plan", 100.21));
        System.out.println(customerData.getBillingHistory("customer_001"));

    }
}
