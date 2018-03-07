package com.ml.galaxy.rest;

import java.sql.SQLException;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONArray;
import org.json.JSONObject;

import com.ml.galaxy.Dao;
import com.ml.galaxy.PeriodBuilder.Period;

/**
 * Created by Esteban Orue on 07/03/2018.
 */
@Path("/periods")
public class PeriodsResource {

	@Inject
    private Dao dao;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String get() throws SQLException {
        JSONArray arr = new JSONArray();
        
        for (Period period : dao.getPeriods()) {
        	JSONObject jsonPeriod = new JSONObject();
            
        	jsonPeriod.put("dayFrom", period.getDayFrom());
        	jsonPeriod.put("dayTo", period.getDayTo());
        	jsonPeriod.put("forecast", period.getForecast().name());
            
            arr.put(jsonPeriod);
		}
        
        return arr.toString();
    }
}
