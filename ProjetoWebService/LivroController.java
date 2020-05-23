package br.com.gerenciamentoDeLivros.controller;


import br.com.gerenciamentoDeLivros.exception.AutorNaoEncontradoException;
import br.com.gerenciamentoDeLivros.exception.LivroNaoEncontradoException;
import br.com.gerenciamentoDeLivros.model.Autor;
import br.com.gerenciamentoDeLivros.model.Livro;
import br.com.gerenciamentoDeLivros.repository.AutorRepository;
import br.com.gerenciamentoDeLivros.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/livro")
public class LivroController {

    @Autowired
    private LivroRepository livroRepository;
    @Autowired
    private AutorController autorController;
    @Autowired
    private AutorRepository autorRepository;

    @PostMapping
    public ResponseEntity<Livro> salvarLivro(@RequestBody Livro livro){
        return ResponseEntity.ok(livroRepository.save(livro));
    }

    @GetMapping
    public ResponseEntity<List<Livro>> listarTodos(){
        if(livroRepository.findAll().isEmpty()){
            return new ResponseEntity<List<Livro>>(livroRepository.findAll(), HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(livroRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Livro> encontrarLivroPorId(@PathVariable Long id){
        verificarExistencia(id);
        return ResponseEntity.ok(livroRepository.findLivroById(id));
    }
    @GetMapping("isbn/{isbn}")
    public ResponseEntity<Livro> encontrarLivroPorIsbn(@PathVariable String isbn){
        if(livroRepository.findLivroByIsbn(isbn)==null){
            throw new LivroNaoEncontradoException("livro com isbn "+isbn+ " nao encontrado");
        }
        return ResponseEntity.ok(livroRepository.findLivroByIsbn(isbn));
    }

    @GetMapping("titulo/{titulo}")
    public ResponseEntity<Livro> encontrarLivroPorTitulo(@PathVariable String titulo){
        if(livroRepository.findLivroByTituloIgnoreCaseContaining(titulo)==null){
            throw new LivroNaoEncontradoException("livro com titulo "+titulo+ " nao encontrado");
        }
        return ResponseEntity.ok(livroRepository.findLivroByTituloIgnoreCaseContaining(titulo));
    }
    @GetMapping("/autor/{id}")
    public ResponseEntity<List<Livro>> listarLivroPorAutor(@PathVariable Long id){
        autorController.verificarExistencia(id);
        Autor autor = autorRepository.findAutorById(id);
        return ResponseEntity.ok(autor.getLivros());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarLivro(@PathVariable Long id){
        verificarExistencia(id);
        livroRepository.deleteById(id);
        return ResponseEntity.ok("livro com id "+id+ "deletado");
    }
    @PutMapping("/{id}")
    public ResponseEntity<Livro> atualizarLivro(@PathVariable Long id, Livro livro){
        verificarExistencia(id);
        livro.setId(id);
        livroRepository.save(livro);
        return ResponseEntity.ok(livro);
    }

    public void verificarExistencia(Long id){
        if(livroRepository.findLivroById(id)==null){
            throw new LivroNaoEncontradoException("livro com id  "+id+" nao encontrado");
        }
    }
}