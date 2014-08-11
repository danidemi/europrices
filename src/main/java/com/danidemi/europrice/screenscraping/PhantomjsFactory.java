package com.danidemi.europrice.screenscraping;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PhantomjsFactory implements ScrapeContextFactory {
	
	final Logger log = LoggerFactory.getLogger(PhantomjsFactory.class);	
	
	private String proxy;
	private int port;
	private boolean enableProxy;
	
	public void setEnableProxy(boolean enabled) {
		this.enableProxy = enabled;
	}
	
	public void setProxy(String proxy) {
		this.proxy = proxy;
	}
	
	public void setPort(int port) {
		this.port = port;
	}

	@Override
	public ScrapeContext getScrapeContext() {
		
        DesiredCapabilities capabilities = DesiredCapabilities.phantomjs();
        capabilities.setCapability("phantomjs.binary.path", "/opt/phantomjs/phantomjs/bin/phantomjs");
        
        if(enableProxy) {
        	Proxy proxy = new Proxy();
        	proxy.setHttpProxy(this.proxy + ":" + port);
        	capabilities.setCapability("proxy", proxy);
        }
        
        
        final PhantomJSDriver driver = new PhantomJSDriver(capabilities);
        
        
        Runtime.getRuntime().addShutdownHook(new Thread(){
        	@Override
        	public void run() {
        		log.info("Quitting PhantomJSDriver");
        		driver.quit();
        		log.info("PhantomJSDriver quitted");
        	}
        });
        
        ScrapeContext scrapeContext = new ScrapeContext(driver);
        
		return scrapeContext;
	    
	}
	
	

}
