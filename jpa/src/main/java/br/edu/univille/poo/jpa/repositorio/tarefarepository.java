package br.edu.univille.poo.jpa.repositorio;

import br.edu.univille.poo.jpa.entidade.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface tarefarepository extends JpaRepository<Tarefa, Long> {
    List<tarefa> findByFinalizado(boolean finalizado);
    List<tarefa> findByFinalizadoFalseAndDataPrevistaFinalizacaoBetween(LocalDate startDate, LocalDate endDate);
    List<tarefa> findByFinalizadoFalseAndDataPrevistaFinalizacaoBefore(LocalDate date);
}
