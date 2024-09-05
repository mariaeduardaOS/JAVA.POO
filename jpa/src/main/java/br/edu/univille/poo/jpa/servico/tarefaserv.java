package br.edu.univille.poo.jpa.servico;

import br.edu.univille.poo.jpa.entidade.tarefa;
import br.edu.univille.poo.jpa.repositorio.tarefaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class tarefaserv {

    @Autowired
    private tarefaRepository tarefaRepository;

    public tarefa salvar(@Valid tarefa tarefa) {
        return tarefaRepository.save(tarefa);
    }

    public List<tarefa> listarTodas() {
        return tarefaRepository.findAll();
    }

    public Optional<tarefa> buscarPorId(Long id) {
        return tarefaRepository.findById(id);
    }

    public tarefa atualizar(Long id, @Valid tarefa novatarefa) {
        tarefa tarefaExistente = buscarPorId(id).orElseThrow(() -> new RuntimeException("tarefa não encontrada"));
        if (tarefaExistente.isFinalizado()) {
            throw new RuntimeException("tarefa finalizada não pode ser modificada");
        }
        tarefaExistente.setTitulo(novatarefa.getTitulo());
        tarefaExistente.setDescricao(novatarefa.getDescricao());
        tarefaExistente.setDataPrevistaFinalizacao(novatarefa.getDataPrevistaFinalizacao());
        tarefaExistente.setDataFinalizacao(novatarefa.getDataFinalizacao());
        tarefaExistente.setFinalizado(novatarefa.isFinalizado());
        return tarefaRepository.save(tarefaExistente);
    }

    public void deletar(Long id) {
        tarefa tarefa = buscarPorId(id).orElseThrow(() -> new RuntimeException("tarefa não encontrada"));
        if (tarefa.isFinalizado()) {
            throw new RuntimeException("tarefa finalizada não pode ser excluída");
        }
        tarefaRepository.deleteById(id);
    }

    public List<tarefa> listarNaoFinalizadas() {
        return tarefaRepository.findByFinalizado(false);
    }

    public List<tarefa> listarFinalizadas() {
        return tarefaRepository.findByFinalizado(true);
    }

    public List<tarefa> listarAtrasadas() {
        return tarefaRepository.findByFinalizadoFalseAndDataPrevistaFinalizacaoBefore(LocalDate.now());
    }

    public List<tarefa> listarNaoFinalizadasEntreDatas(LocalDate startDate, LocalDate endDate) {
        return tarefaRepository.findByFinalizadoFalseAndDataPrevistaFinalizacaoBetween(startDate, endDate);
    }

    public tarefa finalizartarefa(Long id) {
        tarefa tarefa = buscarPorId(id).orElseThrow(() -> new RuntimeException("tarefa não encontrada"));
        if (tarefa.isFinalizado()) {
            throw new RuntimeException("tarefa já finalizada");
        }
        tarefa.setFinalizado(true);
        tarefa.setDataFinalizacao(LocalDate.now());
        return tarefaRepository.save(tarefa);
    }
}

