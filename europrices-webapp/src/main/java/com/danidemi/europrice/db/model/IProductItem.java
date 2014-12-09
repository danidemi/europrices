package com.danidemi.europrice.db.model;

import java.net.URL;

import com.danidemi.europrice.utils.Utils.Language;

public interface IProductItem {

	public abstract URL getDetailsURLAsURL();

	public abstract String getShortDescription();

	public abstract Language getLanguage();

	public abstract String getKeywordsBundle();

	public abstract Long getPriceInCent();

	public abstract String getDetailsURL();

	public abstract Shop getShop();

	public abstract Long getId();

}
