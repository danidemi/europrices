/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.danidemi.europrice.pricegrabber.screenscraping.shops;

/**
 *
 * @author danidemi
 */
public class Request {

    private String searchTerm;

    public Request(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public String getSearchTerm() {
        return searchTerm;
    }
    
    @Override
    public String toString() {
    	return searchTerm;
    }
    
}
