package org.example.out;

public class AddTransactionWriter {
    public void askAmount(){
        System.out.println("""
                Вы попали в режим добавления транзакции
                Для начала введите сумму транзакции в формате
                +/- ваша_сумма
                """);
    }
    public void askCategory(){
        System.out.println("""
                        Введите категорию из доступных
                        SUPERMARKET SPORT TRANSPORT TRAVEL CASH PEOPLE RESTAURANTS OTHER"""
                );
    }
    public void askDescription(){
        System.out.println("Далее введите небольшое описание категории");
    }
    public void invalidInput(){
        System.out.println("Введены некорректные значения, попробуйте еще разок");
    }


}
