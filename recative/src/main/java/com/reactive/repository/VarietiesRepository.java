package com.reactive.repository;

import com.reactive.entity.Varieties;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface VarietiesRepository extends R2dbcRepository<Varieties, Long> {
    Mono<Boolean> existsById(Long id);

}
