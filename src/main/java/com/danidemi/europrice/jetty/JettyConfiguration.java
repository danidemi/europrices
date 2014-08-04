package com.danidemi.europrice.jetty;

import java.io.IOException;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.webapp.WebAppContext;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;

/**
 * Configure the embedded Jetty server and the SpringMVC dispatcher servlet.
 */
@Configuration
public class JettyConfiguration implements ApplicationContextAware {

    private ApplicationContext springApplicationContext;

    private int jettyPort;
    
    private WebAppContext jettyWebAppContext;
    
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		springApplicationContext = applicationContext;
	}

	public void setJettyWebAppContext(WebAppContext jettyWebAppContext) {
		this.jettyWebAppContext = jettyWebAppContext;
	}


    /**
     * Jetty Server bean.
     * <p/>
     * Instantiate the Jetty server.
     */
    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server jettyServer() throws IOException {

        /* Create the server. */
        Server server = new Server();

        /* Create a basic connector. */
        ServerConnector httpConnector = new ServerConnector(server);
        httpConnector.setPort(jettyPort);
        server.addConnector(httpConnector);

        server.setHandler(jettyWebAppContext);

        return server;
    }





}
