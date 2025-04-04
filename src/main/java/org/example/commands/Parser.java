package org.example.commands;

public class Parser {

    public CommandsEnum parseCommand(String input) {
        String[] st = input.split(" ");
        return CommandsEnum.valueOf(st[0].toUpperCase());
    }
    public ModelEnum parseModel(String input) {
        String[] st = input.split(" ");
        return ModelEnum.valueOf(st[1].toUpperCase());
    }
    
}
