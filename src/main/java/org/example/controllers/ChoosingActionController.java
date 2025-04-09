package org.example.controllers;

import lombok.RequiredArgsConstructor;
import org.example.commands.CommandsEnum;
import org.example.commands.ModelEnum;
import org.example.commands.Parser;
import org.example.controllers.transactions.AddTransactionController;
import org.example.controllers.transactions.DeleteTransactionController;
import org.example.controllers.transactions.GetTransactionController;
import org.example.controllers.transactions.UpdateTransactionController;
import org.example.controllers.user.DeleteUserController;
import org.example.controllers.user.GetUserController;
import org.example.controllers.user.UpdateUserController;
import org.example.entrypoint.Identification;
import org.example.in.Reader;
import org.example.out.ChoosingActionWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequiredArgsConstructor

public class ChoosingActionController {
    private final ChoosingActionWriter writer;
    private final Parser parser;
    private final Reader reader;
    private final Identification identification;
    private final Logger logger = LoggerFactory.getLogger(ChoosingActionController.class);
    private final AddTransactionController addTrController;
    private final UpdateTransactionController updateTrController;
    private final DeleteTransactionController deleteTrController;
    private final DeleteUserController deleteUserController;
    private final UpdateUserController updateUserController;
    private final GetTransactionController getTransactionController;
    private final GetUserController getUserController;

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
            return;
        }

        switch (command){
            case ADD:
                if (model != ModelEnum.TRANSACTION){
                    writer.invalidInput();
                    start();
                }
                addTrController.add();
                break;
            case UPDATE:
                if (model == ModelEnum.TRANSACTION){
                    updateTrController.update();
                    break;
                }
                if (model == ModelEnum.USER){
                    updateUserController.update();
                    break;
                }
            case DELETE:
                if (model == ModelEnum.TRANSACTION){
                    deleteTrController.delete();
                    break;
                }
                if (model == ModelEnum.USER){
                    deleteUserController.delete();
                    break;
                }
            case GET:
                if (model == ModelEnum.TRANSACTION){
                    getTransactionController.get();
                    break;
                }
                if (model == ModelEnum.USER){
                   getUserController.get();
                    break;
                }
                /**
                 * get user
                 */

        }

    }
    public void start(){
        writer.actions();
        String answer = reader.read();
        try {
            chooseAction(parser.parseCommand(answer), parser.parseModel(answer));
        }catch (IllegalArgumentException e){
            writer.invalidInput();
            start();
        }
    }
}
