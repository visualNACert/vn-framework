package com.visualnacert.reto;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RetoApplicationTests {

	@LocalServerPort
	private int port;

	private static String baseUrl = "http://localhost:";

	@Test
	public void testConcurrentRequests() throws InterruptedException {
		int numThreads = 10; // Number of concurrent threads
		ExecutorService executorService = Executors.newFixedThreadPool(numThreads);

		for (int i = 0; i < numThreads; i++) {
			executorService.execute(() -> {
				RestTemplate restTemplate = new RestTemplate();
				String response = restTemplate.getForObject(baseUrl + port + "/reto", String.class);
				assertEquals("OK", response);
			});
		}

		// Espera a que todas las solicitudes concurrentes terminen
		executorService.shutdown();
		executorService.awaitTermination(30, TimeUnit.SECONDS);
	}
}