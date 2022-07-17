package com.app.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.app.repository.es")
public class ESConfig {

	@Value("${spring.data.elasticsearch.host}")
	private String esHost;

	@Value("${spring.data.elasticsearch.port}")
	private int esPort;

	@Value("${spring.data.elasticsearch.username:}")
	private String username;

	@Value("${spring.data.elasticsearch.password:}")
	private String password;

	@Value("${spring.data.elasticsearch.ssl-enabled:false}")
	private boolean sslEnabled;

	private static final Logger LOG = LoggerFactory.getLogger(ESConfig.class);

	@Bean
	public RestHighLevelClient clientRest() {
		ClientConfiguration clientConfiguration;
		ClientConfiguration.TerminalClientConfigurationBuilder terminalClientConfigurationBuilder = null;
		ClientConfiguration.MaybeSecureClientConfigurationBuilder client = ClientConfiguration.builder()
				.connectedTo(esHost + ":" + esPort);

		if (sslEnabled) {
			terminalClientConfigurationBuilder = client.usingSsl();
		}

		clientConfiguration = terminalClientConfigurationBuilder == null ?
				client.withBasicAuth(username, password).build() :
				terminalClientConfigurationBuilder.withBasicAuth(username, password).build();

		LOG.info("Connection to ElasticSearch started!");

		return RestClients.create(clientConfiguration).rest();
	}

	@Bean
	public ElasticsearchRestTemplate elasticsearchTemplate() {
		return new ElasticsearchRestTemplate(clientRest());
	}

}
