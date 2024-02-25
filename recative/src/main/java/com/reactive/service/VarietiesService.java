package com.reactive.service;

import com.reactive.entity.Varieties;
import com.reactive.repository.VarietiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class VarietiesService {

    private final VarietiesRepository varietiesRepository;

    public VarietiesService(VarietiesRepository varietiesRepository) {
        this.varietiesRepository = varietiesRepository;
    }


    public Mono<Varieties> createVarieties(Varieties varieties) {
        return varietiesRepository.save(varieties);
    }
    public Flux<Varieties> getAllVarieties() {
        return varietiesRepository.findAll();
    }
    public Mono<Varieties> updateVarieties(Long id, Varieties updatedVarieties) {
        return varietiesRepository.findById(id)
                .flatMap(existingVarieties -> {
                    existingVarieties.setDiseasePestName(updatedVarieties.getDiseasePestName());
                    existingVarieties.setDiseasePestScientificName(updatedVarieties.getDiseasePestScientificName());
                    existingVarieties.setDiseasePestCode(updatedVarieties.getDiseasePestCode());
                    existingVarieties.setSource(updatedVarieties.getSource());
                    return varietiesRepository.save(existingVarieties);
                });
    }

    public Mono<Void> deleteVarieties(Long id) {
        return varietiesRepository.deleteById(id);
    }
}