package org.jesko.picture.sucker;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.jesko.picture.sucker.config.WebConfig;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class Main {

	public static final int PORT = 61829; 
	
	public static void main(String[] args) throws Exception {
		
		AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
		appContext.register(WebConfig.class);
		
		ServletHolder servletHolder = new ServletHolder(new DispatcherServlet(appContext));
		ServletContextHandler context = new ServletContextHandler();
		context.setContextPath("/");
		context.addServlet(servletHolder, "/*");
		
		Server server = new Server(PORT);
		server.setHandler(context);
		
		server.start();
		server.join();
	}

}
