package com.reactive.controller;

import com.reactive.entity.Crop;
import com.reactive.service.CropService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/crops")
public class CropController {

    private CropService cropService;

    public CropController(CropService cropService) {
        this.cropService = cropService;
    }

//    @GetMapping
//    public Flux<Crop> getAllCrops() {
//        return cropService.getAllCrops();
//    }
    @GetMapping
    public Flux<Crop> getAllCrops(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(defaultValue = "id") String sortBy,
                                  @RequestParam(defaultValue = " ") String keyword) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        return keyword.trim().isEmpty() ?
                cropService.getAllCrops(pageable) :
                cropService.searchCrops(keyword, pageable);
    }
    //http://localhost:8080/crops/2
    @GetMapping("/{id}")
    public Mono<ResponseEntity<String>> getCropById(@PathVariable Long id) {
        return cropService.getCropById(id)
                .map(crop -> ResponseEntity.ok().body(crop.toString()))
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Crop not found with id: " + id)));
    }
   // http://localhost:8080/crops/crops
    @PostMapping("/crops")
    public Mono<ResponseEntity<String>> createCrop(@RequestBody Crop crop) {
        return cropService.createCrop(crop)
                .flatMap(existsByCropName -> {
                    if (existsByCropName) {
                        return Mono.just(ResponseEntity.ok().body("Crop Name Already Exists"));
                    } else {
                        return Mono.just(ResponseEntity.ok().body(crop.toString()));
                    }
                });
    }
    //http://localhost:8080/crops/2
    @PutMapping("/{id}")
    public Mono<ResponseEntity<String>> updateCrop(@PathVariable Long id, @RequestBody Crop crop) {
        return cropService.updateCrop(id, crop)
                .flatMap(updatedCrop -> {
                    if (updatedCrop != null) {
                        return Mono.just(ResponseEntity.ok()
                                .body("Crop updated successfully with id: " + id));
                    } else {
                        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body("Crop not found with id: " + id));
                    }
                });
    }
    //http://localhost:8080/crops/2
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Object>> deleteCrop(@PathVariable Long id) {
        return cropService.deleteCropById(id)
                .flatMap(deleted -> {
                    if (deleted) {
                        return Mono.just(ResponseEntity.ok().body("Crop deleted with id: " + id));
                    } else {
                        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body("Crop not found with id: " + id));
                    }
                });
    }
}