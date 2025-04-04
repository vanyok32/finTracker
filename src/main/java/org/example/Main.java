package org.example;

import org.example.enums.TransactionCategory;
import org.example.enums.TransactionType;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        System.out.println(checkCategory("cash"));

    }
    private static boolean checkCategory(String category){
        for (TransactionCategory transactionCategory : TransactionCategory.values()){
            if (transactionCategory.name().equals(category.toUpperCase())) return true;
        }
        return false;
    }

}