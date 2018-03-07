package com.ml.galaxy;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.google.inject.Inject;
import com.ml.galaxy.GalaxyModel.Forecast;

/**
 * Created by Esteban Orue on 07/03/2018.
 */
public class PlanetaryThread extends Thread {
	
	public static final Logger LOGGER = Logger.getLogger(PlanetaryThread.class);

    @Inject
    private Dao dao;

    @Inject
    private GalaxyModel galaxyModel;

    @Inject
    private PeriodBuilder periodBuilder;


    @Override
    public void run() {
        for (int day = 0; day < GalaxyModel.PREDICTION_DAYS; day++) {
            Forecast forecast = galaxyModel.predict(day);

            periodBuilder.addForecast(forecast);

            try {
                dao.save(day, forecast);
            }
            catch (SQLException e) {
                LOGGER.error("Exception when saving forecast", e);
            }
        }

        try {
            dao.setMaxRainDay(galaxyModel.getMaxDay());
        }
        catch (SQLException e) {
            LOGGER.error("Exception when saving max rain day", e);
        }

        for (PeriodBuilder.Period period : periodBuilder) {
            try {
                dao.savePeriod(period);
            }
            catch (SQLException e) {
                LOGGER.error("Exception when saving period " + period, e);
            }
        }

        for (Forecast forecast : Forecast.values()) {
            int count = periodBuilder.getCount(forecast);

            LOGGER.info("There are " + count + " periods of " + forecast + " forecast");
        }

        LOGGER.info("The most " + Forecast.RAINY + " day will be the #" +
                    galaxyModel.getMaxDay());
    }
}
