package br.com.leonardo.LiterAlura.repositorio;

import br.com.leonardo.LiterAlura.modelo.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RepositorioLivros extends JpaRepository<Livro, Long> {

    Optional<Livro> findByTitle(String title);

    @Query(value = "SELECT * FROM book WHERE :languages = ANY(languages)", nativeQuery = true)
    List<Livro> buscarPorIdioma(String languages);

    List<Livro> findTop10ByOrderByDownloadCountDesc();
}
