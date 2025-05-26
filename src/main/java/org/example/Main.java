package org.example;

import org.example.commands.AmountCategoryTypeParser;
import org.example.commands.Parser;
import org.example.controllers.ChoosingActionController;
import org.example.controllers.administration.BlockUserController;
import org.example.controllers.administration.UnblockUserController;
import org.example.controllers.transactions.AddTransactionController;
import org.example.controllers.transactions.DeleteTransactionController;
import org.example.controllers.transactions.GetTransactionController;
import org.example.controllers.transactions.UpdateTransactionController;
import org.example.controllers.user.DeleteUserController;
import org.example.controllers.user.GetUserController;
import org.example.controllers.user.UpdateUserController;
import org.example.entrypoint.Authentication;
import org.example.entrypoint.Identification;
import org.example.entrypoint.Registrations;
import org.example.in.Reader;
import org.example.mappers.TransactionMapper;
import org.example.mappers.UserMapper;
import org.example.out.*;
import org.example.repositories.implementations.TransactionRepositoryImpl;
import org.example.repositories.implementations.UserRepositoryImpl;
import org.example.service.implementations.TransactionServiceImpl;
import org.example.service.implementations.UserServiceImpl;
import org.example.service.interfaces.TransactionService;
import org.example.service.interfaces.UserService;
import org.example.validators.UserValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private final Logger logger = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {
        //reader, parser
        Reader reader = new Reader();
        Parser parser = new Parser();
        //validator
        UserValidator userValidator = UserValidator.getInstance();
        //writers
        AuthentificationWriter authentificationWriter = new AuthentificationWriter();
        RegistrationWriter registrationWriter = new RegistrationWriter();
        ChoosingActionWriter choosingActionWriter = new ChoosingActionWriter();
        IdentificationWriter identificationWriter = new IdentificationWriter();
        AddTransactionWriter addTransactionWriter = new AddTransactionWriter();
        UpdateTransactionWriter updateTransactionWriter = new UpdateTransactionWriter();
        DeleteUserWriter deleteUserWriter = new DeleteUserWriter();
        UpdateUserWriter updateUserWriter = new UpdateUserWriter();
        AdministrationWriter administrationWriter = new AdministrationWriter();
        GetTransactionWriter getTransactionWriter = new GetTransactionWriter();
        //amountCategoryTypeParser
        AmountCategoryTypeParser amountCategoryTypeParser = new AmountCategoryTypeParser(addTransactionWriter, reader);
        //services
        UserService UserService = UserServiceImpl.getInstance();
        TransactionService TransactionService = TransactionServiceImpl.getInstance();
        //Transaction and User information printers
        TransactionListPrinter transactionListPrinter = new TransactionListPrinter(TransactionService, updateTransactionWriter);
        UserInfoPrinter userInfoPrinter = new UserInfoPrinter(UserService);
        //entrypoint
        Authentication authentication = new Authentication(authentificationWriter,reader, UserService);
        Registrations registrations = new Registrations(registrationWriter,reader,UserService,userValidator, UserMapper.getInstance());
        Identification identification = new Identification(UserService,reader, authentication,
                registrations, identificationWriter, userValidator);
        //controllers
        AddTransactionController addTransactionController = new AddTransactionController(TransactionService, reader,
                addTransactionWriter, amountCategoryTypeParser);
        UpdateTransactionController updateTransactionController = new UpdateTransactionController(TransactionService, reader,
                updateTransactionWriter, transactionListPrinter, amountCategoryTypeParser);
        DeleteTransactionController deleteTransactionController = new DeleteTransactionController(TransactionService,
                transactionListPrinter, updateTransactionWriter, reader);
        DeleteUserController deleteUserController = new DeleteUserController(UserService, deleteUserWriter, reader, identification);
        UpdateUserController updateUserController = new UpdateUserController(UserService, reader, updateUserWriter, userInfoPrinter);
        GetUserController getUserController = new GetUserController(UserService);
        GetTransactionController getTransactionController = new GetTransactionController(TransactionService, getTransactionWriter, reader);
        BlockUserController blockUserController = new BlockUserController(UserService,administrationWriter,reader);
        UnblockUserController unblockUserController = new UnblockUserController(UserService, administrationWriter,reader);
        ChoosingActionController choosingActionController = new ChoosingActionController(choosingActionWriter, parser,reader,
                identification, addTransactionController, updateTransactionController, deleteTransactionController,
                deleteUserController, updateUserController, getTransactionController, getUserController, blockUserController,
                unblockUserController, administrationWriter);

        authentication.setChoosingActionController(choosingActionController);
        registrations.setChoosingActionController(choosingActionController);
        addTransactionController.setChoosingActionController(choosingActionController);
        updateTransactionController.setChoosingActionController(choosingActionController);
        deleteTransactionController.setChoosingActionController(choosingActionController);
        deleteUserController.setChoosingActionController(choosingActionController);
        updateUserController.setChoosingActionController(choosingActionController);
        getTransactionController.setActionController(choosingActionController);
        getUserController.setChoosingActionController(choosingActionController);
        blockUserController.setChoosingActionController(choosingActionController);
        unblockUserController.setChoosingActionController(choosingActionController);
        identification.start();
        //System.out.println(UserService.getUserByEmail("ivmerzlyakov@mail.ru"));



    }
}