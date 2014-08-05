package com.danidemi.europrice.jetty;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.eclipse.jetty.webapp.WebAppContext;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;

public class JettyWebAppContextFactory implements FactoryBean<WebAppContext>, ApplicationContextAware {
	
	private ApplicationContext appctx;

	@Override
	public WebAppContext getObject() throws Exception {
		return jettyWebAppContext();
	}

	@Override
	public Class<?> getObjectType() {
		return WebAppContext.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

    public WebAppContext jettyWebAppContext() throws IOException {

        WebAppContext ctx = new WebAppContext();
        ctx.setContextPath("/");
        ctx.setWar(new ClassPathResource("webapp").getURI().toString());

        /* Disable directory listings if no index.html is found. */
        ctx.setInitParameter("org.eclipse.jetty.servlet.Default.dirAllowed",
                "false");

        /* Create the root web application context and set it as a servlet
         * attribute so the dispatcher servlet can find it. */
        GenericWebApplicationContext webApplicationContext =
                new GenericWebApplicationContext();
        webApplicationContext.setParent(appctx);
        webApplicationContext.refresh();
        ctx.setAttribute(
                WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE,
                webApplicationContext);

//        /*
//         * Set the attributes that the Metrics servlets require.  The Metrics
//         * servlet is added in the WebAppInitializer.
//         */
//        ctx.setAttribute(MetricsServlet.METRICS_REGISTRY,
//                metricRegistry);
//        ctx.setAttribute(HealthCheckServlet.HEALTH_CHECK_REGISTRY,
//                healthCheckRegistry);

        ctx.addEventListener(new WebAppInitializer(webApplicationContext));

        return ctx;
    }

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.appctx = applicationContext;
		
	}

	
}
