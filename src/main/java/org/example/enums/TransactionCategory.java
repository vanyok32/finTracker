package org.example.enums;

public enum TransactionCategory {
    SUPERMARKET ("Магазины"),
    SPORT ("Спорт"),
    TRANSPORT ("Транспорт"),
    TRAVEL ("Путешествия"),
    CASH ("Наличные"),
    PEOPLE ("Люди"),
    RESTAURANTS ("Кафе, рестораны"),
    OTHER ("Другое");

    private final String name;
    private TransactionCategory(String name) {this.name = name;}
    public String getName() {return name;}
}
