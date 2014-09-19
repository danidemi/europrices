/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tmp;

import com.danidemi.europrice.pricegrabber.screenscraping.action.ScrapedProductCallback;
import com.danidemi.europrice.pricegrabber.screenscraping.shops.Request;

/**
 *
 * @author danidemi
 */
public interface Grabber {
    
    public void run(Request request, ScrapedProductCallback callback);
    
}
