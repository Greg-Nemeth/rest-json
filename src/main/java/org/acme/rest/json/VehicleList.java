package org.acme.rest.json;

import java.util.List;
import org.acme.rest.json.Vehicle;


public class VehicleList {

    private List<Vehicle> vehicles;

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }
}
