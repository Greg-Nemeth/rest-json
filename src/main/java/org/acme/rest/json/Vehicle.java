package org.acme.rest.json;

import java.util.Objects;
import java.time.LocalTime;
import io.quarkus.runtime.annotations.RegisterForReflection;

/**
 * A REST entity representing a vehicle.
 */
@RegisterForReflection // Lets Quarkus register this class for reflection during the native build
public record Vehicle(String lineref,
		      String publishedlinename,
		      String originname,
		      String destinationname,
		      float longitude,
		      float latitude,
		      int vehicleref) {}
