package org.acme.rest.json;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.apache.camel.Exchange;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;

import org.acme.rest.json.MyBean;

@ApplicationScoped
public class Routes extends RouteBuilder {

    @Inject
    MyBean myBean;

    @Override
    public void configure() throws Exception {
        restConfiguration()
            .component("platform-http")
            .host("localhost")
            .port("8080")
            .bindingMode(RestBindingMode.json);

        from("platform-http:/items")
                .removeHeader(Exchange.HTTP_PATH)
                .to("rest:get:/siri/vm?host=http://data.foli.fi:80&bridgeEndpoint=true")
                .convertBodyTo(String.class)
                .filter().jsonpath("$..[?(@.lineref)]")
                .setBody().jsonpath("$..['lineref', 'publishedlinename', 'originname', 'destinationname', 'longitude', 'latitude', 'vehicleref']")
                .bean(myBean, "process")
                ;

	from("platform-http:/item")
           .to("telegram:bots?authorizationToken=5455421420:AAHleg2bec23_0pzWD3cMvAGmnT4FwV4tOk&chatId=5370273744");
            ;
    }
}
