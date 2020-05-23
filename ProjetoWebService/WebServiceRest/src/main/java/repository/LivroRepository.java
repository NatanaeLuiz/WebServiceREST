package repository;

import model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivroRepository extends JpaRepository<Livro, Long> {

    Livro findLivroById(Long id);
    Livro findLivroByIsbn(String isbn);
    Livro findLivroByTituloIgnoreCaseContaining(String titulo);
}