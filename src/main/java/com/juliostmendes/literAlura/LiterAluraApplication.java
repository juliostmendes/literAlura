package com.juliostmendes.literAlura;

import com.juliostmendes.literAlura.main.Main;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiterAluraApplication implements CommandLineRunner {



	//@Autowired
	//private SerieRepository repositorio;

	public static void main(String[] args) {
		SpringApplication.run(LiterAluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//Main main = new Main(repositorio);

		Main main = new Main();
		main.showMenu();
	}
}
