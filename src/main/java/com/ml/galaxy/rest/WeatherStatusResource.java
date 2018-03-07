package com.ml.galaxy.rest;

import java.net.HttpURLConnection;
import java.sql.SQLException;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import com.ml.galaxy.Dao;
import com.ml.galaxy.PeriodBuilder;

/**
 * Created by Esteban Orue on 07/03/2018.
 */
@Path("/weather")
public class WeatherStatusResource {
	@Inject
    private Dao dao;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String get(@QueryParam("day") Integer day) throws SQLException {
        if (day == null) {
            throw new WebApplicationException(
                    Response.status(HttpURLConnection.HTTP_BAD_REQUEST)
                            .entity("day parameter is mandatory")
                            .build()
            );
        }

        PeriodBuilder.Period period = dao.getPeriod(day);

        JSONObject json = new JSONObject();

        json.put("day", day);
        json.put("forecast", dao.get(day));

        if (period != null) {
            JSONObject jsonPeriod = new JSONObject();
            jsonPeriod.put("dayFrom", period.getDayFrom());
            jsonPeriod.put("dayTo", period.getDayTo());

            json.put("period", jsonPeriod);
        }

        return json.toString();
    }
}
