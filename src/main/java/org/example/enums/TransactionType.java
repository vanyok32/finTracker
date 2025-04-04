package org.example.enums;

public enum TransactionType {
    PLUS ("+"),
    MINUS ("-");
    private final String name;
    TransactionType(String name) {this.name = name;}
    public String getName() {return name;}
}
