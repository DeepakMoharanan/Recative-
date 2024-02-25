package com.reactive.controller;

import com.reactive.entity.Varieties;
import com.reactive.service.VarietiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/varieties")
public class VarietiesController {

    private final VarietiesService varietiesService;

    public VarietiesController(VarietiesService varietiesService) {
        this.varietiesService = varietiesService;
    }

    @PostMapping("/create")
    public Mono<ResponseEntity<Varieties>> createVarieties(@RequestBody Varieties varieties) {
        return varietiesService.createVarieties(varieties)
                .map(createdVariety -> new ResponseEntity<>(createdVariety, HttpStatus.CREATED));
    }
    @GetMapping
    public ResponseEntity<Flux<Varieties>> getAllVarieties() {
        Flux<Varieties> allVarieties = varietiesService.getAllVarieties();
        return ResponseEntity.ok().body(allVarieties);
    }
    @PutMapping("/{id}")
    public Mono<ResponseEntity<Varieties>> updateVarieties(@PathVariable Long id, @RequestBody Varieties updatedVarieties) {
        return varietiesService.updateVarieties(id, updatedVarieties)
                .map(updatedEntity -> new ResponseEntity<>(updatedEntity, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteVarieties(@PathVariable Long id) {
        return varietiesService.deleteVarieties(id)
                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)))
                .onErrorReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
