/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.danidemi.europrice.poc.pricegrabber;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.gargoylesoftware.htmlunit.javascript.JavaScriptEngine;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author danidemi
 */
public class Grabber {
    
    //Sony Xperia Z2
    //Samsung Galaxy S3 Blanco 16GB (I9300)
    //Samsung Galaxy S3 I9300
    
    private void run(){
        
        try {
            
            
            System.out.println( "a" );
            
            final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_24);
            
            System.out.println( "b" );
            
            final HtmlPage page = webClient.getPage("http://www.oselection.es/");
            
            System.out.println( "c" );
            
            
            final String xpath = "//input[@class=buscador-text]";
            Object firstByXPath = page.getFirstByXPath(xpath);
            if(! (HtmlTextInput.class.isAssignableFrom( firstByXPath.getClass() ))) throw new RuntimeException("Not an input");
            
            HtmlTextInput input = (HtmlTextInput) firstByXPath;
            
            input.type( "Samsung" );
            
            firstByXPath = page.getFirstByXPath("//input[@class=buscador-submit]");
            if(! (firstByXPath instanceof HtmlSubmitInput)) throw new RuntimeException("Not an input");
            
            HtmlSubmitInput submit = (HtmlSubmitInput) firstByXPath;
            
            submit.click();
            
            
                    
        } catch (IOException | FailingHttpStatusCodeException ex) {
            ex.printStackTrace();
        }
        
    }
        
    public static void main(String[] args) {        
        new Grabber().run();
    }
    

}
