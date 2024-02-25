package com.reactive.repository;

import com.reactive.entity.Crop;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Repository
public interface CropRepository extends R2dbcRepository<Crop, Long> {
    Mono<Boolean> existsById(Long id);
    Mono<Boolean> existsByCropNameAndCropType(String cropName, String cropType);
    Flux<Crop> findAllBy(Pageable pageable);
    Flux<Crop> findByCropNameContainingIgnoreCase(String cropName, Pageable pageable);
}
