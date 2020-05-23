package br.com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import exception.AutorNaoEncontradoException;
import model.Autor;
import repository.AutorRepository;


@RestController
@RequestMapping("/autor")
public class AutorController {

    @Autowired
    private AutorRepository autorRepository;

    @PostMapping
    public ResponseEntity<Autor> salvarAutor(@RequestBody Autor autor){
        return ResponseEntity.ok(autorRepository.save(autor));
    }

    @GetMapping
    public ResponseEntity<List<Autor>> listarTodos(){
        if(autorRepository.findAll().isEmpty()){
            return new ResponseEntity<List<Autor>>(autorRepository.findAll(), HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(autorRepository.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Autor> ListarAutorPorId(@PathVariable Long id){
        verificarExistencia(id);
        return ResponseEntity.ok(autorRepository.findAutorById(id));
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<Autor> ListarAutorPorCpf(@PathVariable String cpf){
        if(autorRepository.findAutorByCpf(cpf)==null){
            throw new AutorNaoEncontradoException("Autor com cpf  "+cpf+ " nao encontrado" );
        }
        return ResponseEntity.ok(autorRepository.findAutorByCpf(cpf));
    }
    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<Autor>> ListarAutorPorNome(@PathVariable String nome){
        if(autorRepository.findAutorByNomeIgnoreCaseContaining(nome)==null){
            throw new AutorNaoEncontradoException("Autor "+nome+ " nao encontrado" );
        }
        return ResponseEntity.ok(autorRepository.findAutorByNomeIgnoreCaseContaining(nome));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarAutor(@PathVariable Long id){
        verificarExistencia(id);
        autorRepository.deleteById(id);
        return ResponseEntity.ok("livro com id "+id+" deletado");
    }
    @PutMapping("/{id}")
    public ResponseEntity<Autor> atualizarAutor(@PathVariable Long id, Autor autor){
        verificarExistencia(id);
        autor.setId(id);
        autorRepository.save(autor);
        return ResponseEntity.ok(autor);
    }

    public void verificarExistencia(Long id){
        if(autorRepository.findAutorById(id)==null){
            throw new AutorNaoEncontradoException("autor com id  " +id+ " não encontrado");
        }
    }
}