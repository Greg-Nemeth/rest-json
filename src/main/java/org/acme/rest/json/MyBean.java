package org.acme.rest.json;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;

import org.apache.camel.Exchange;
import org.acme.rest.json.OutPutVehicleModel;
import org.acme.rest.json.Vehicle;
import com.fasterxml.jackson.databind.ObjectMapper;

@ApplicationScoped
public class MyBean {

    public void process(Exchange exchange) throws Exception {
        var body = exchange.getMessage().getBody();
        ObjectMapper mapper = new ObjectMapper();
        String jsonArray = mapper.writeValueAsString(body);
        Vehicle[] asArray = mapper.readValue(jsonArray, Vehicle[].class);
        List<OutPutVehicleModel> outArray = Arrays.stream(asArray).map(v -> new OutPutVehicleModel(v)).collect(Collectors.toList());
        String outJson = mapper.writeValueAsString(outArray);
        exchange.getIn().setBody(outJson);
	}

}

