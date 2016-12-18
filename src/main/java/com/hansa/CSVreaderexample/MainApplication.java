package com.hansa.CSVreaderexample;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.dataformat.BindyType;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class MainApplication {

	public static void main(String[] args) throws Exception {
		CamelContext camelctx = new DefaultCamelContext();
		final XmlMapper xmlMapper = new XmlMapper();
		final File xmlFile = new File("user.xml");
		final List<User> wholeList = new ArrayList<User>();
		camelctx.addRoutes(new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				from("file://src/main/resources?fileName=CCEmail_12122016.csv&noop=true").split().tokenize("\n")
						.streaming().unmarshal().bindy(BindyType.Csv, User.class).process(new Processor() {

					public void process(Exchange exchange) throws Exception {
						User list = (User) exchange.getIn().getBody();
						wholeList.add(list);
						xmlMapper.writeValue(xmlFile, wholeList);
					}
				}).end();
			}

		});

		camelctx.start();
		Thread.sleep(3000);
		camelctx.stop();

	}

}
