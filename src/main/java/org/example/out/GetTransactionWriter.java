package org.example.out;

public class GetTransactionWriter {
    public void allOrOne(){
        System.out.println("""
                Если знаете id вашей транзакции введите его,
                или введите all чтобы увидеть информацию обо всех транзакциях""");
    }
    public void transactionNotFound(){
        System.out.println("Транзакция с таким ID не найдена, либо ID некорректный");
    }

}
