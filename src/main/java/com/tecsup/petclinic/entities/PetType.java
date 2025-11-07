package com.tecsup.petclinic.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Set;

/**
 * 
 * @author jgomezm
 *
 */
@NoArgsConstructor
@Entity(name = "types")
@Data
public class PetType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "name")
	private String name;

    @Column(length = 100)
    private String description;

    @Column(name = "care_level", length = 20)
    private String careLevel;

//	@OneToMany(mappedBy = "type", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	@ToString.Exclude
//	@EqualsAndHashCode.Exclude
//	private Set<Pet> pets;

}