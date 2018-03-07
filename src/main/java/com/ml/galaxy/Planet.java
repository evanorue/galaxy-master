package com.ml.galaxy;

import java.awt.geom.Point2D;

/**
 * Created by Orue Esteban - 07/03/18.
 */
public class Planet {
	public final double distance;
    public final double angularVelocity;

    public Planet(double distance, double angularVelocity) {
        this.distance = distance;
        this.angularVelocity = Math.toRadians(angularVelocity);
    }

    public Point2D getPosition(double days) {
        double x = Math.cos(angularVelocity * days) * distance;
        double y = Math.sin(angularVelocity * days) * distance;

        return new Point2D.Double(x, y);
    }
}
