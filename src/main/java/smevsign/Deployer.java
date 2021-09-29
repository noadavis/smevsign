package smevsign;

import ru.CryptoPro.JCP.JCP;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.security.Security;

public class Deployer implements ServletContextListener {
    ServletContext context;
    final String jcpProviderName = JCP.PROVIDER_NAME;

    public void contextInitialized(ServletContextEvent contextEvent) {
        System.out.println("Context Created");
        context = contextEvent.getServletContext();
        if (Security.getProvider(jcpProviderName) == null) {
            System.out.println("add jcp provider");
            Security.addProvider(new JCP());
        }
        if (!org.apache.xml.security.Init.isInitialized()) {
            System.out.println("init xml apache library");
            org.apache.xml.security.Init.init();
        }
        // set variable to servlet context
        //context.setAttribute("TEST", "TEST_VALUE");
    }

    public void contextDestroyed(ServletContextEvent contextEvent) {
        context = contextEvent.getServletContext();
        if (Security.getProvider(jcpProviderName) != null) {
            System.out.println("remove jcp provider");
            Security.removeProvider(jcpProviderName);
        }
        System.out.println("Context Destroyed");
    }
}
