package com.reactive.service;

import com.reactive.entity.Crop;
import com.reactive.repository.CropRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CropService {

    private CropRepository cropRepository;

    public CropService(CropRepository cropRepository) {
        this.cropRepository = cropRepository;
    }

//    public Flux<Crop> getAllCrops() {
//        return cropRepository.findAll();
//    }
    public Flux<Crop> getAllCrops(Pageable pageable) {
        return cropRepository.findAllBy(pageable);
    }
    public Flux<Crop> searchCrops(String keyword, Pageable pageable) {
        return cropRepository.findByCropNameContainingIgnoreCase(keyword, pageable);
    }

    public Mono<Crop> getCropById(Long id) {
        return cropRepository.findById(id);
    }

    public Mono<Boolean> createCrop(Crop crop) {
        return cropRepository.existsByCropNameAndCropType(crop.getCropName(), crop.getCropType())
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.just(true);
                    } else {
                        return cropRepository.save(crop)
                                .thenReturn(false);
                    }
                });
    }
    public Mono<Crop> updateCrop(Long id, Crop crop) {
        return cropRepository.findById(id)
                .flatMap(existingCrop -> {
                    existingCrop.setCropName(crop.getCropName());
                    existingCrop.setCropType(crop.getCropType());
                    existingCrop.setCropCategory(crop.getCropCategory());
                    return cropRepository.save(existingCrop);
                })
                .switchIfEmpty(Mono.empty());
    }

    public Mono<Boolean> deleteCropById(Long id) {
        return cropRepository.existsById(id)
                .flatMap(exists -> {
                    if (exists) {
                        return cropRepository.deleteById(id)
                                .then(Mono.just(true));
                    } else {
                        return Mono.just(false);
                    }
                });
    }
}
