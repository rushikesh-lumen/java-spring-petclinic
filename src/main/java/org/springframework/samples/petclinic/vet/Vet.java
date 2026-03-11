/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.vet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator; // MIGRATED: Added Comparator to replace deprecated PropertyComparator
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Entity; // MIGRATED: Updated to Jakarta EE 10 namespace
import jakarta.persistence.FetchType; // MIGRATED: Updated to Jakarta EE 10 namespace
import jakarta.persistence.JoinColumn; // MIGRATED: Updated to Jakarta EE 10 namespace
import jakarta.persistence.JoinTable; // MIGRATED: Updated to Jakarta EE 10 namespace
import jakarta.persistence.ManyToMany; // MIGRATED: Updated to Jakarta EE 10 namespace
import jakarta.persistence.Table; // MIGRATED: Updated to Jakarta EE 10 namespace
import jakarta.xml.bind.annotation.XmlElement; // MIGRATED: Updated to Jakarta EE 10 namespace (javax.xml.bind -> jakarta.xml.bind)

import org.springframework.samples.petclinic.model.Person;

/**
 * Simple JavaBean domain object representing a veterinarian.
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Arjen Poutsma
 */
@Entity
@Table(name = "vets")
public class Vet extends Person {

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "vet_specialties", joinColumns = @JoinColumn(name = "vet_id"),
			inverseJoinColumns = @JoinColumn(name = "specialty_id"))
	private Set<Specialty> specialties;

	protected Set<Specialty> getSpecialtiesInternal() {
		if (this.specialties == null) {
			this.specialties = new HashSet<>();
		}
		return this.specialties;
	}

	protected void setSpecialtiesInternal(Set<Specialty> specialties) {
		this.specialties = specialties;
	}

	@XmlElement
	public List<Specialty> getSpecialties() {
		List<Specialty> sortedSpecs = new ArrayList<>(getSpecialtiesInternal());
		// MIGRATED: Replaced deprecated PropertyComparator/MutableSortDefinition with
		// Java Comparator
		sortedSpecs.sort(Comparator.comparing(Specialty::getName));
		return Collections.unmodifiableList(sortedSpecs);
	}

	public int getNrOfSpecialties() {
		return getSpecialtiesInternal().size();
	}

	public void addSpecialty(Specialty specialty) {
		getSpecialtiesInternal().add(specialty);
	}

}
