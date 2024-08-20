package application.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import application.model.Tarefa;
import application.repository.TarefaRepository;

@RestController
@RequestMapping("/tarefa")
public class TarefaController {

    @Autowired
    private TarefaRepository tarefaRepo;

    // Método GET para listar todas as tarefas
    @GetMapping
    public Iterable<Tarefa> list() {
        return tarefaRepo.findAll();
    }

    // Método GET para listar uma tarefa específica por ID
    @GetMapping("/{id}")
    public Tarefa details(@PathVariable long id) {
        Optional<Tarefa> resultado = tarefaRepo.findById(id);
        if (resultado.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Tarefa não encontrada");
        }
        return resultado.get();
    }

    // Método POST para adicionar uma nova tarefa
    @PostMapping
    public Tarefa insert(@RequestBody Tarefa tarefa) {
        return tarefaRepo.save(tarefa);
    }

    // Método PUT para atualizar uma tarefa existente
    @PutMapping("/{id}")
    public Tarefa put(@PathVariable long id, @RequestBody Tarefa novosDados) {
        Optional<Tarefa> resultado = tarefaRepo.findById(id);
        if (resultado.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Tarefa não encontrada");
        }

        Tarefa tarefaExistente = resultado.get();
        tarefaExistente.setDescricao(novosDados.getDescricao());
        tarefaExistente.setConcluido(novosDados.isConcluido());

        return tarefaRepo.save(tarefaExistente);
    }

    // Método DELETE para remover uma tarefa
    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        if (!tarefaRepo.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Tarefa não encontrada");
        }
        tarefaRepo.deleteById(id);
    }
}
