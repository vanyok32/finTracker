package org.example.controllers.transactions;

import lombok.RequiredArgsConstructor;
import org.example.commands.CommandsEnum;
import org.example.commands.ModelEnum;
import org.example.commands.Parser;
import org.example.entrypoint.Identification;
import org.example.in.Reader;
import org.example.out.ChoosingActionWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequiredArgsConstructor

public class ChoosingActionController {
    private final ChoosingActionWriter writer = new ChoosingActionWriter();
    private final Parser parser = new Parser();
    private final Reader reader;
    private final Identification identification;
    private final Logger logger = LoggerFactory.getLogger(ChoosingActionController.class);
    AddTransactionController controller;

    public void chooseAction(CommandsEnum command, ModelEnum model) {
        if (command == CommandsEnum.EXIT) {
            if (model != null){
                writer.invalidInput();
                start();
                return;
            }
            identification.start();
            return;
        }
        if (model == null) {
            writer.invalidInput();
            start();
        }

        switch (command){
            case ADD:
                if (model != ModelEnum.TRANSACTION){
                    writer.invalidInput();
                    start();
                }



        }

    }
    public void start(){
        writer.actions();
        String answer = reader.read();
        chooseAction(parser.parseCommand(answer), parser.parseModel(answer));
    }
}
