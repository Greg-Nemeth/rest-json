package org.acme.rest.json;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.*;
import java.util.stream.Collectors;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.acme.rest.json.Vehicle;
import static org.acme.rest.json.Gzip.*;

public class Routes extends RouteBuilder {

    private final Set<Vehicle> vehicles = Collections.synchronizedSet(new LinkedHashSet<>());
    JacksonDataFormat processing = new JacksonDataFormat();

    @Override
    public void configure() throws Exception {
	restConfiguration() 
            .component("platform-http")
            .host("localhost")
            .port("8080")
            .bindingMode(RestBindingMode.json);
	
	from("platform-http:/items")
		.setExchangePattern(ExchangePattern.InOut)
		.to("direct:getdata");

	from("direct:getdata")
                .process(new Processor() {
                @Override
                public void process(Exchange exchange) throws Exception {
                        HttpClient client = HttpClient.newHttpClient();
                        HttpRequest request = HttpRequest.newBuilder()
                                .header("accept", "application/json")
                                .uri(URI.create("http://data.foli.fi/siri/vm"))
                                .build();
                        var response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());
                        String content = decompress(response.body().readAllBytes());
                        exchange.getIn().setBody(content);
                }})
                .setBody().jsonpath("$.result.vehicles")
                .filter().jsonpath("$..[?(@.lineref)]")
                .setBody().jsonpath("$..['lineref', 'publishedlinename', 'originname', 'destinationname', 'longitude', 'latitude', 'vehicleref']")
                .log("body here : + ${body}")
                .end()
		.process(new Processor() {
		@Override
		public void process(Exchange exchange) throws Exception {
			var body = exchange.getMessage().getBody();
			ObjectMapper mapper = new ObjectMapper();
			String jsonArray = mapper.writeValueAsString(body);
			Vehicle[] asArray = mapper.readValue(jsonArray, Vehicle[].class);
			List<OutPutVehicleModel> outArray = Arrays.stream(asArray).map(v -> new OutPutVehicleModel(v)).collect(Collectors.toList());
			String outJson = mapper.writeValueAsString(outArray);
			System.out.println(outJson);
			exchange.getIn().setBody(outJson);
		}});
    }
}
