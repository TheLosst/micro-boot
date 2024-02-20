package ru.thelosst.testservice.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@Table(name = "test1")
@RequiredArgsConstructor
@AllArgsConstructor
public class PC {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "charfiled")
    private String charfiled;
    @Column(name = "intfield")
    private int intfield;
}
