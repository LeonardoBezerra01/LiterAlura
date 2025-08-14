package br.com.leonardo.LiterAlura.repositorio;

import br.com.leonardo.LiterAlura.modelo.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepositorioAutores extends JpaRepository<Autor, Long> {
    List<Autor> findByBirthYearLessThanEqualAndDeathYearGreaterThanEqual(int ano, int ano1);

    List<Autor> findByNameContainingIgnoreCaseOrderByNameAsc(String nomeAutor);

    List<Autor> findAllByOrderByNameAsc();
}
