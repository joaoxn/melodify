package com.melodify.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.melodify.infra.exception.CustomException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@RequiredArgsConstructor
@Slf4j //TODO: translate logs from portuguese to english
public abstract class GenericServiceImpl<E, DTO, R extends JpaRepository<E, Long>> implements GenericService<E, DTO> {
    private final R repository;

    public E get(Long id) {
        log.info("GenericService.get({}) -> Buscando entidade por id no repositório", id);
        return repository.findById(id)
                .orElseThrow(() -> new CustomException(
                        HttpStatus.NOT_FOUND, "Entidade não encontrada com ID: " + id));
    }

    public List<E> getAll() {
        log.info("GenericService.getAll() -> Buscando todas as entidades no repositório");
        return repository.findAll();
    }

    public E create(DTO request) {
        log.info("GenericService.create(DTO entity) -> Criando entidade baseado na Requisição dada");
        log.info("SERVICE equalProperties() -> ( Chamada: create(...) ) Transferindo dados da Requisição para uma Entidade própria");
        E entitySave = equalProperties(newEntity(), request);
        return repository.save(entitySave);
    }

    public E alter(Long id, DTO request) {
        log.info("GenericService.alter({}, DTO request) -> Alterando por id baseado na Requisição dada", id);
        E entityFound = repository.findById(id)
                .orElseThrow(
                        () -> new CustomException(
                                HttpStatus.NOT_FOUND,
                                "Entidade não encontrada com ID: " + id));
        log.info("SERVICE equalProperties() -> ( Chamada: alter(...) ) Transferindo dados da Requisição para uma Entidade própria");
        return repository.save(equalProperties(entityFound, request));
    }

    public String delete(Long id) {
        log.info("GenericService.delete({}) -> Deletando entidade com id informado", id);
        E entity = repository.findById(id)
                .orElseThrow(() -> new CustomException(
                        HttpStatus.NOT_FOUND, "Entidade não encontrada com ID: " + id));
        repository.delete(entity);
        return "Entidade com ID: "+ id +" deletado com sucesso!";
    }

    public abstract E equalProperties(E entity, DTO request);

    public abstract E newEntity();
}
