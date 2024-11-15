package com.studenthub.api;



import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiApplication {


	public static void main(String[] args) {

		Dotenv dotenv = Dotenv.load();

		// Ler variáveis com fallback para evitar valores nulos
		String BDUrl = dotenv.get("BD_URL");
		String BDUsername = dotenv.get("BD_USERNAME");
		String BDPassword = dotenv.get("BD_PASSWORD");

		// Debug: Verificar se as variáveis estão sendo carregadas
		System.out.println("BD_URL: " + BDUrl);
		System.out.println("BD_USERNAME: " + BDUsername);
		System.out.println("BD_PASSWORD: " + BDPassword);

		// Validar antes de definir no System
		if (BDUrl == null || BDUsername == null || BDPassword == null) {
			throw new RuntimeException("Erro: Variáveis de ambiente não foram carregadas corretamente.");
		}

		// Adicionar ao System
		System.setProperty("BD_URL", BDUrl);
		System.setProperty("BD_USERNAME", BDUsername);
		System.setProperty("BD_PASSWORD", BDPassword);

		SpringApplication.run(ApiApplication.class, args);
	}

}
