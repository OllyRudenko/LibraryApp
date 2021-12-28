package ua.olharudenko.libraryapp.web.command;

import ua.olharudenko.libraryapp.web.command.author.*;
import ua.olharudenko.libraryapp.web.command.user.DeleteUserCommand;
import ua.olharudenko.libraryapp.web.command.user.UpdateUserCommand;
import ua.olharudenko.libraryapp.web.command.user.ViewAllUsersCommand;

import java.util.Map;
import java.util.TreeMap;

public class CommandContainer {
    private static Map<String, Command> commands = new TreeMap<String, Command>();

    static {

        commands.put("login", new LoginCommand());
//        commands.put("logout", new LogoutCommand());
        commands.put("registration", new RegistrationCommand());
        commands.put("updateUser", new UpdateUserCommand());
        commands.put("viewAllUsers", new ViewAllUsersCommand());
        commands.put("deleteUser", new DeleteUserCommand());
        commands.put("noCommand", new NoCommand());
        commands.put("viewAllBooks", new ViewAllBooksCommand());

        commands.put("viewAllAuthors", new ViewAllAuthorsCommand());
        commands.put("viewAuthorProfile", new ViewAuthorProfileCommand());
        commands.put("updateAuthorProfile", new UpdateAuthorProfileCommand());
        commands.put("deleteAuthorProfile", new DeleteAuthorProfileCommand());
        commands.put("addNewAuthor", new AddNewAuthorCommand());


        commands.put("listUserOrders", new ListUserOrdersCommand());

        commands.put("listUnconfirmedOrders", new ListUnconfirmedOrdersCommand());
    }

    public static Command get(String commandName) {
        if (commandName == null || !commands.containsKey(commandName)) {
            return commands.get("noCommand");
        }
        return commands.get(commandName);
    }

}
