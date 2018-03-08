package com.ml.galaxy;

import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceFilter;
import com.google.inject.servlet.GuiceServletContextListener;
import com.ml.galaxy.guice.GalaxyModule;

/**
 * Created by Esteban Orue on 07/03/2018.
 */
public class Main {
	private static final Logger LOGGER = Logger.getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        long startDeploy = System.currentTimeMillis();

        final Injector injector = Guice.createInjector(new GalaxyModule());

        Server server = new Server(8080);
        ServletContextHandler sch = new ServletContextHandler(server, "/");

        sch.addEventListener(new GuiceServletContextListener() {
            @Override
            protected Injector getInjector() {
                return injector;
            }
        });

        sch.addFilter(GuiceFilter.class, "/*", null);
        sch.addServlet(DefaultServlet.class, "/");

        LOGGER.info("Service started. - Deploy time: " + (System.currentTimeMillis() - startDeploy) + " ms.");

        server.start();

        PlanetaryThread worker = injector.getInstance(PlanetaryThread.class);
        worker.start();

        worker.join();
        server.join();
    }
}
