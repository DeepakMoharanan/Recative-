package com.reactive.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "varieties")
public class Varieties {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "disease_pest_name")
    private String diseasePestName;

    @Column(name = "disease_pest_scientific_name")
    private String diseasePestScientificName;

    @Column(name = "disease_pest_code")
    private int diseasePestCode;

    private String source;

    @Column(name = "disease_pest_type")
    private String diseasePestType;

    @Column(name = "uploaded_image")
    private String uploadedImage;

    @Column(name = "about_disease_pest")
    private String aboutDiseasePest;

    @Column(name = "mark_of_identification")
    private String markOfIdentification;

    @Column(name = "control_measurement")
    private String controlMeasurement;

    @Column(name = "causes_of_disease_pest")
    private String causesOfDiseasePest;

    @Column(name = "crop_id")
    private Long cropId;

}

