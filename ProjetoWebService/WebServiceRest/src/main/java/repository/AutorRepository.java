package repository;

import model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    Autor findAutorById(Long id);
    Autor findAutorByCpf(String cpf);
    List<Autor> findAutorByNomeIgnoreCaseContaining(String nome);
    List<Autor> findAutorByNome(String nome);
}