package com.danidemi.europrice.screenscraping;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PhantomjsFactory implements ScrapeContextFactory {
		
	final Logger log = LoggerFactory.getLogger(PhantomjsFactory.class);	
	
	
	private String proxy;
	private Integer port;
	private boolean enableProxy = false;

	private File pathToPhantomJsExecutable = null;
	
	public PhantomjsFactory() {

	}
	
	public void setPathToPhantomJsExecutable(String pathToPhantomJsExecutable) {
		final File maybeExecutable = new File( pathToPhantomJsExecutable );
		
		if(!maybeExecutable.exists()){
			throw new IllegalStateException("Does not exist: " + maybeExecutable);
		}
		if(!maybeExecutable.canExecute()){
			throw new IllegalStateException("Cannot execute " + maybeExecutable);
		}
		
		this.pathToPhantomJsExecutable = maybeExecutable;
		
		
		
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
                
        final PhantomJSDriver driver 
        	//= driverTheCommonWay(capabilities);
        	= driverTheHardWay(capabilities);
        


        
        
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
	

	private PhantomJSDriver driverTheCommonWay(DesiredCapabilities capabilities) {
		return new PhantomJSDriver(capabilities);
	}
	
	private PhantomJSDriver driverTheHardWay(DesiredCapabilities capabilities) {

		PhantomJSDriverService.Builder builder = new PhantomJSDriverService.Builder();
		builder.usingPhantomJSExecutable(this.pathToPhantomJsExecutable);
		//builder..usingGhostDriver(ghostDriverfile) --> whant's that for ? In null uses embedded ghostdriver.
		if(this.port != null){
			builder.usingPort(this.port);			
		}
		if(this.enableProxy){
			Proxy theProxy = new Proxy();
			theProxy.setHttpProxy(this.proxy);
			builder.withProxy( theProxy );			
		}
		//builder.withLogFile(new File("/tmp/")); --> doh! I don't want file

		//    --cookies-file=<val>                 Sets the file name to store the persistent cookies
		//	  --config=<val>                       Specifies JSON-formatted configuration file
		//	  --debug=<val>                        Prints additional warning and debug message: 'true' or 'false' (default)
		//	  --disk-cache=<val>                   Enables disk cache: 'true' or 'false' (default)
		//	  --ignore-ssl-errors=<val>            Ignores SSL errors (expired/self-signed certificate errors): 'true' or 'false' (default)
		//	  --load-images=<val>                  Loads all inlined images: 'true' (default) or 'false'
		//	  --local-storage-path=<val>           Specifies the location for offline local storage
		//	  --local-storage-quota=<val>          Sets the maximum size of the offline local storage (in KB)
		//	  --local-to-remote-url-access=<val>   Allows local content to access remote URL: 'true' or 'false' (default)
		//	  --max-disk-cache-size=<val>          Limits the size of the disk cache (in KB)
		//	  --output-encoding=<val>              Sets the encoding for the terminal output, default is 'utf8'
		//	  --remote-debugger-port=<val>         Starts the script in a debug harness and listens on the specified port
		//	  --remote-debugger-autorun=<val>      Runs the script in the debugger immediately: 'true' or 'false' (default)
		//	  --proxy=<val>                        Sets the proxy server, e.g. '--proxy=http://proxy.company.com:8080'
		//	  --proxy-auth=<val>                   Provides authentication information for the proxy, e.g. ''-proxy-auth=username:password'
		//	  --proxy-type=<val>                   Specifies the proxy type, 'http' (default), 'none' (disable completely), or 'socks5'
		//	  --script-encoding=<val>              Sets the encoding used for the starting script, default is 'utf8'
		//	  --web-security=<val>                 Enables web security, 'true' (default) or 'false'
		//	  --ssl-protocol=<val>                 Sets the SSL protocol (supported protocols: 'SSLv3' (default), 'SSLv2', 'TLSv1', 'any')
		//	  --ssl-certificates-path=<val>        Sets the location for custom CA certificates (if none set, uses system default)
		//	  --webdriver=<val>                    Starts in 'Remote WebDriver mode' (embedded GhostDriver): '[[<IP>:]<PORT>]' (default '127.0.0.1:8910') 
		//	  --webdriver-logfile=<val>            File where to write the WebDriver's Log (default 'none') (NOTE: needs '--webdriver') 
		//	  --webdriver-loglevel=<val>           WebDriver Logging Level: (supported: 'ERROR', 'WARN', 'INFO', 'DEBUG') (default 'INFO') (NOTE: needs '--webdriver') 
		//	  --webdriver-selenium-grid-hub=<val>  URL to the Selenium Grid HUB: 'URL_TO_HUB' (default 'none') (NOTE: needs '--webdriver') 
		//	  -w,--wd                              Equivalent to '--webdriver' option above
		//	  -h,--help                            Shows this message and quits
		//	  -v,--version                         Prints out PhantomJS version
		
		Map<String, String> paramToValue = new HashMap<>();
		

		builder.usingCommandLineArguments( new String[] {"--ignore-ssl-errors=true", "--load-images=false"} );
		return new PhantomJSDriver(builder.build(), capabilities);
	}
	
	

}
