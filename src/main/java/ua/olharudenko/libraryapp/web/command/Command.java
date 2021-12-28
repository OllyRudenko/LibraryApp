package ua.olharudenko.libraryapp.web.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;

public abstract class Command {
    private static Map<String, Command> commands = new TreeMap<String, Command>();

    public abstract String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, SQLException;

    @Override
    public final String toString() {
        return getClass().getSimpleName();
    }


}
