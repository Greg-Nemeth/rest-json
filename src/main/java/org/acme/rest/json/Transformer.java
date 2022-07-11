package org.acme.rest.json;

import org.acme.rest.json.OutPutVehicleModel;
import org.acme.rest.json.Vehicle;
import java.util.List;
import java.util.stream.*;
import java.util.stream.Collectors;

public class Transformer {

	public static List<OutPutVehicleModel> transform(List<Vehicle> vehicles) {
		return vehicles.stream()
			.map(OutPutVehicleModel::new)
			.collect(Collectors.toList());
	}

	public static List<OutPutVehicleModel> transform(VehicleList vehicles) {
		return vehicles.getVehicles()
			.stream()
			.map(OutPutVehicleModel::new)
			.collect(Collectors.toList());
	}
}
