package org.acme.rest.json;

import org.acme.rest.json.Vehicle;

public class OutPutVehicleModel {

        private float latitude;
        private String name;
        private String description;
        private String id;
        private float longitude;


        public OutPutVehicleModel() {
        }

        public OutPutVehicleModel(Vehicle model) {
                this.latitude = model.latitude();
                this.name = model.publishedlinename();
                this.description = this.name + ": " + model.originname() + " -> " + model.destinationname();
                this.id = String.valueOf(model.vehicleref());
                this.longitude = model.longitude();
        }

         
        public float getLatitude() {
                return this.latitude;
        }

         
        public void setLatitude(float latitude) {
                this.latitude = latitude;
        }

         
        public String getName() {
                return this.name;
        }

         
        public void setName(String name) {
                this.name = name;
        }

         
        public String getDescription() {
                return this.description;
        }

         
        public void setDescription(String description) {
                this.description = description;
        }

         
        public String getId() {
                return this.id;
        }

         
        public void SetId(String id) {
                this.id = id;
        }

         
        public float getLongitude() {
                return this.longitude;
        }

         
        public void setLongitude(float longitude) {
                this.longitude = longitude;
        }
}
