package edu.iua.calculator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * Taxes Calculator returns a calculated total amount
 * based on taxes percentages
 */
public class TaxesCalculator
{
    public static void main( String[] args )
    {
        System.out.print( "Enter amount value: " );

        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader (isr);

        try {

            float amount = Float.parseFloat(br.readLine());
            HashMap<String, Float> calculatedTax = calculate(amount);

        } catch (IOException e) {
            System.out.println( "Sorry, an error has occurred. Please try again!" );
        } catch (NumberFormatException e) {
            System.out.println( "Invalid amount value. Please try again!" );
        }

    }


    public static HashMap<String, Float> calculate(float amount) {

        HashMap<String, Float> calculatedTaxesAmount = new HashMap<String, Float>();

        Taxes taxes = new Taxes();
        HashMap<String, Float> taxesPercentages = taxes.getTaxesPercentage();

        System.out.println("Applicable taxes:");
        float totalAmount = amount;
        for (String tax : taxesPercentages.keySet()) {
            float taxValue = taxesPercentages.get(tax);
            float applicableTax = amount * taxValue;
            calculatedTaxesAmount.put(tax, applicableTax);
            System.out.println("TAX: " + tax + " = " + applicableTax);
            totalAmount = totalAmount + applicableTax;
        }

        System.out.println("\nTOTAL Amount: " + totalAmount);


        return calculatedTaxesAmount;
    }
}
