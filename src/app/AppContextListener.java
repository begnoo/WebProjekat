package app;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import repository.DbContext;

@WebListener
public class AppContextListener implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		// TODO Auto-generated method stub
		System.out.println("WebProjekat ENDED");
	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		// TODO Auto-generated method stub
		ServletContext servletContext = servletContextEvent.getServletContext();
		DbContext dbContext = new DbContext();
		servletContext.setAttribute("DbContext", dbContext);
		
		System.out.println("WebProjekat STARTED");
		
		//Seeder seeder = new Seeder(dbContext);
		//seeder.run();
	}

}
