package com.danidemi.europrice.screenscraping;

import java.io.File;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.browserlaunchers.Proxies;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PhantomjsFactory implements ScrapeContextFactory {
		
	final Logger log = LoggerFactory.getLogger(PhantomjsFactory.class);	
	
	
	private String proxy;
	private int port;
	private boolean enableProxy = false;

	private String pathToPhantomJsExecutable = null;
	
	public PhantomjsFactory() {

	}
	
	public void setPathToPhantomJsExecutable(String pathToPhantomJsExecutable) {
		this.pathToPhantomJsExecutable = pathToPhantomJsExecutable;
	}
	
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
		
		if(pathToPhantomJsExecutable == null) {
			throw new IllegalStateException("Please, set pathToPhantomJsExecutable");
		}
		
        DesiredCapabilities capabilities = DesiredCapabilities.phantomjs();
		capabilities.setCapability("phantomjs.binary.path", pathToPhantomJsExecutable);
        
        
        if(enableProxy) {
        	Proxy proxy = new Proxy();
        	proxy.setHttpProxy(this.proxy + ":" + port);
        	capabilities.setCapability("proxy", proxy);
        }
        
        capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, new String[] { "localhost", "port=8080" });
        
        
        //final PhantomJSDriver driver = new PhantomJSDriver(capabilities);
        

        PhantomJSDriverService.Builder b = new PhantomJSDriverService.Builder();
        b.usingGhostDriver( new File(pathToPhantomJsExecutable) );
        b.usingCommandLineArguments(new String[]{"--ignore-ssl-errors=yes"});
        b.usingPhantomJSExecutable( new File(pathToPhantomJsExecutable) );
        b.usingPort(8080);
        PhantomJSDriverService service = b.build();
        
        final PhantomJSDriver driver = new PhantomJSDriver(service, capabilities);
        
//        // Look for Proxy configuration within the Capabilities
//        Proxy proxy = null;
//        if (capabilities != null) {
//        	proxy = Proxies.extractProxy(capabilities);
//        }
//        // Find PhantomJS executable
//        File phantomjsfile = findPhantomJS(
//        		capabilities, 
//        		PhantomJSDriverService.PHANTOMJS_DOC_LINK, 
//        		PhantomJSDriverService.PHANTOMJS_DOWNLOAD_LINK);
//
//        // Find GhostDriver main JavaScript file
//        File ghostDriverfile = findGhostDriver(capabilities, PhantomJSDriverService.GHOSTDRIVER_DOC_LINK, PhantomJSDriverService.GHOSTDRIVER_DOWNLOAD_LINK);
//
//        // Build & return service
//        return new Builder().usingPhantomJSExecutable(phantomjsfile)
//                .usingGhostDriver(ghostDriverfile)
//                .usingAnyFreePort()
//                .withProxy(proxy)
//                .withLogFile(new File(PhantomJSDriverService.PHANTOMJS_DEFAULT_LOGFILE))
//                .usingCommandLineArguments(
//                        findCLIArgumentsFromCaps(capabilities, PhantomJSDriverService.PHANTOMJS_CLI_ARGS))
//                .usingGhostDriverCommandLineArguments(
//                        findCLIArgumentsFromCaps(capabilities, PhantomJSDriverService.PHANTOMJS_GHOSTDRIVER_CLI_ARGS))
//                .build();
//        final PhantomJSDriver driver = new PhantomJSDriver(service, capabilities);
        
        
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
