package edu.iua.calculator.business;

import edu.iua.calculator.model.Billings;
import edu.iua.calculator.model.Taxes;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Taxes Calculator returns a calculated total amount
 * based on taxes percentages
 */
public class TaxesCalculator
{
    private static SessionFactory factory;

    public static void main( String[] args )
    {
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }

        System.out.print( "Enter amount value: " );

        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader (isr);

        try {

            float amount = Float.parseFloat(br.readLine());
            HashMap<String, Float> calculatedTax = calculate(amount);


            Integer createdBill = saveBill(amount);
            if (createdBill != null ){
                System.out.println( "Bill with id: " + createdBill + " has been created" );
            }

        } catch (IOException e) {
            System.out.println( "Sorry, an error has occurred. Please try again!" );
        } catch (NumberFormatException e) {
            System.out.println( "Invalid amount value. Please try again!" );
        }

    }


    public static HashMap<String, Float> calculate(float amount) {

        HashMap<String, Float> calculatedTaxesAmount = new HashMap<String, Float>();

        Taxes taxes = new Taxes();
        HashMap<String, Float> taxesPercentages = getTaxesPercentage();

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



    private static HashMap<String, Float> getTaxesPercentage() {
        Session session = factory.openSession();
        Transaction tx = null;
        HashMap<String, Float> taxesPercentage = new HashMap<String, Float>();


        try {
            tx = session.beginTransaction();
            List taxes = session.createQuery("FROM Taxes").list();
            for (Iterator iterator = taxes.iterator(); iterator.hasNext();){
                Taxes tax = (Taxes) iterator.next();
                taxesPercentage.put(tax.getTaxName(), tax.getTaxPercentage());
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return taxesPercentage;

    }

    private static int saveBill(float amount){
        Session session = factory.openSession();
        Transaction tx = null;
        Integer billId = null;

        try {
            tx = session.beginTransaction();
            Billings bill = new Billings();
            bill.setAmount(amount);
            bill.setDate(new Date(new java.util.Date().getTime()));
            billId = (Integer) session.save(bill);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return billId;
    }
}
