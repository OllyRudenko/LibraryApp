package ua.olharudenko.libraryapp.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextListener implements ServletContextListener {

    private static final Logger log = LogManager.getLogger(ContextListener.class);

    public void contextDestroyed(ServletContextEvent event) {
    }

    public void contextInitialized(ServletContextEvent event) {
        log.info("Servlet context initialization starts");

        ServletContext servletContext = event.getServletContext();
        initCommandContainer();

        log.info("Servlet context initialization finished");
    }

    private void initCommandContainer() {
        try {
            Class.forName("ua.olharudenko.libraryapp.web.command.CommandContainer");
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }

}

