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
@Slf4j
public abstract class GenericServiceImpl<E, REQ, RES, R extends JpaRepository<E, Long>> implements GenericService<E, REQ, RES> {
    private final R repository;

    public RES get(Long id) {
        log.info("GenericService.get({}) -> Getting entity by id on repository", id);
        return respond(repository.findById(id)
                .orElseThrow(() -> new CustomException(
                        HttpStatus.NOT_FOUND, "Not found Entity with id "+ id)));
    }

    public List<RES> getAll() {
        log.info("GenericService.getAll() -> Getting all entities on repository");
        return repository.findAll().stream().map(this::respond).toList();
    }

    public RES create(REQ request) {
        log.info("GenericService.create(REQ entity) -> Creating entity based on given Request");
        log.info("SERVICE equalProperties() -> ( Chamada: create(...) ) Transferindo dados da Requisição para uma Entidade própria");
        E entitySave = equalProperties(newEntity(), request);
        return respond(repository.save(entitySave));
    }

    public RES alter(Long id, REQ request) {
        log.info("GenericService.alter({}, REQ request) -> Altering by id baseado na Requisição dada", id);
        E entityFound = repository.findById(id)
                .orElseThrow(
                        () -> new CustomException(
                                HttpStatus.NOT_FOUND,
                                "Not found entity with id " + id));
        log.info("SERVICE equalProperties() -> ( Chamada: alter(...) ) Transferindo dados da Requisição para uma Entidade própria");
        return respond(repository.save(equalProperties(entityFound, request)));
    }

    public String delete(Long id) {
        log.info("GenericService.delete({}) -> Deletando entidade com id informado", id);
        E entity = repository.findById(id)
                .orElseThrow(() -> new CustomException(
                        HttpStatus.NOT_FOUND, "Not found entity with id " + id));
        repository.delete(entity);
        return "Entity with ID: "+ id +" successfully deleted!";
    }

    public abstract E equalProperties(E entity, REQ request);

    public abstract RES respond(E entity);

    public abstract E newEntity();
}
