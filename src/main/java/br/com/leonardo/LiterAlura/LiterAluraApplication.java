package br.com.leonardo.LiterAlura;

import br.com.leonardo.LiterAlura.principal.Principal;
import br.com.leonardo.LiterAlura.repositorio.RepositorioLivros;
import br.com.leonardo.LiterAlura.repositorio.RepositorioAutores;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiterAluraApplication implements CommandLineRunner {

    @Autowired
    private RepositorioLivros repositorioLivros;
    @Autowired
    private RepositorioAutores repositorioAutores;

    public static void main(String[] args) {
        SpringApplication.run(LiterAluraApplication.class, args);
    }

    @Override
    public void run(String... args) {
        Principal principal = new Principal(repositorioLivros, repositorioAutores);
        principal.exibirMenu();
    }
}
