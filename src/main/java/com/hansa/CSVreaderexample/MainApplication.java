package com.hansa.CSVreaderexample;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.dataformat.BindyType;

public class MainApplication {

	public static void main(String[] args) throws Exception {
		CamelContext camelctx = new DefaultCamelContext();
		camelctx.addRoutes(new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				from("file://src/main/resources?fileName=basicinfo.csv&noop=true").unmarshal()
						.bindy(BindyType.Csv, User.class).process(new Processor() {

							public void process(Exchange exchange) throws Exception {
								System.out.println(exchange);
								User list = (User) exchange.getIn().getBody();
								System.out.println(list);
							}
						}).end();
			}
		});

		camelctx.start();
		Thread.sleep(3000);
		camelctx.stop();

	}

}
