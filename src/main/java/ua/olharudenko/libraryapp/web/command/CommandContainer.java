package ua.olharudenko.libraryapp.web.command;

import ua.olharudenko.libraryapp.web.command.author.*;
import ua.olharudenko.libraryapp.web.command.book.AddNewBookCommand;
import ua.olharudenko.libraryapp.web.command.book.DeleteBookCommand;
import ua.olharudenko.libraryapp.web.command.book.ViewAllBooksCommand;
import ua.olharudenko.libraryapp.web.command.book.ViewBookProfileCommand;
import ua.olharudenko.libraryapp.web.command.order.ChangeAdminOrderStatusCommand;
import ua.olharudenko.libraryapp.web.command.order.DeleteOrderCommand;
import ua.olharudenko.libraryapp.web.command.order.MakeOrderCommand;
import ua.olharudenko.libraryapp.web.command.order.ViewAllOrdersCommand;
import ua.olharudenko.libraryapp.web.command.publishHouse.AddNewLocalizedPublishingHouseCommand;
import ua.olharudenko.libraryapp.web.command.publishHouse.DeletePublishHouseCommand;
import ua.olharudenko.libraryapp.web.command.publishHouse.ViewAllPublishHousesCommand;
import ua.olharudenko.libraryapp.web.command.publishHouse.ViewHouseProfileCommand;
import ua.olharudenko.libraryapp.web.command.user.ChangeUserRoleCommand;
import ua.olharudenko.libraryapp.web.command.user.DeleteUserCommand;
import ua.olharudenko.libraryapp.web.command.user.UpdateUserCommand;
import ua.olharudenko.libraryapp.web.command.user.ViewAllUsersCommand;

import java.util.Map;
import java.util.TreeMap;

public class CommandContainer {
    private static Map<String, Command> commands = new TreeMap<String, Command>();

    static {

        commands.put("login", new LoginCommand());
        commands.put("logout", new LogoutCommand());
        commands.put("registration", new RegistrationCommand());
        commands.put("updateUser", new UpdateUserCommand());
        commands.put("viewAllUsers", new ViewAllUsersCommand());
        commands.put("deleteUser", new DeleteUserCommand());
        commands.put("changeUserRole", new ChangeUserRoleCommand());
        commands.put("noCommand", new NoCommand());
        commands.put("viewAllBooks", new ViewAllBooksCommand());

        commands.put("viewAllAuthors", new ViewAllAuthorsCommand());
        commands.put("viewAuthorProfile", new ViewAuthorProfileCommand());
        commands.put("updateAuthorProfile", new UpdateAuthorProfileCommand());
        commands.put("deleteAuthorProfile", new DeleteAuthorProfileCommand());
        commands.put("addNewAuthor", new AddNewAuthorCommand());

        commands.put("viewAllBooks", new ViewAllBooksCommand());
        commands.put("viewBookProfile", new ViewBookProfileCommand());
        commands.put("deleteBook", new DeleteBookCommand());
        commands.put("addNewBook", new AddNewBookCommand());

        commands.put("viewAllPublishHouses", new ViewAllPublishHousesCommand());
        commands.put("viewHouseProfile", new ViewHouseProfileCommand());
        commands.put("deletePublishHouse", new DeletePublishHouseCommand());
        commands.put("addNewLocalizedPublishingHouse", new AddNewLocalizedPublishingHouseCommand());

        commands.put("listUserOrders", new ListUserOrdersCommand());
        commands.put("makeOrder", new MakeOrderCommand());
        commands.put("viewAllOrders", new ViewAllOrdersCommand());
        commands.put("deleteOrder", new DeleteOrderCommand());
        commands.put("changeAdminOrderStatus", new ChangeAdminOrderStatusCommand());

        //todo
        commands.put("listUnconfirmedOrders", new ListUnconfirmedOrdersCommand());
    }

    public static Command get(String commandName) {
        if (commandName == null || !commands.containsKey(commandName)) {
            return commands.get("noCommand");
        }
        return commands.get(commandName);
    }

}
