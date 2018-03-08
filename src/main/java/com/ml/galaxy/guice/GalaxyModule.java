package com.ml.galaxy.guice;

import java.util.HashMap;
import java.util.Map;

import com.google.inject.servlet.ServletModule;
import com.ml.galaxy.Dao;
import com.ml.galaxy.GalaxyModel;
import com.ml.galaxy.PeriodBuilder;
import com.ml.galaxy.PlanetaryThread;
import com.ml.galaxy.rest.PeriodsResource;
import com.ml.galaxy.rest.WeatherStatusResource;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

/**
 * Created by Esteban Orue on 07/03/2018.
 */
public class GalaxyModule extends ServletModule{
	@Override
    protected void configureServlets() {
        bind(GalaxyModel.class).asEagerSingleton();
        bind(Dao.class).asEagerSingleton();
        bind(PeriodBuilder.class).asEagerSingleton();
        bind(PlanetaryThread.class).asEagerSingleton();

        bind(WeatherStatusResource.class).asEagerSingleton();
        bind(PeriodsResource.class).asEagerSingleton();
        
        Map<String, String> params = new HashMap<String, String>();
        serve("/*").with(GuiceContainer.class, params);
    }
}
