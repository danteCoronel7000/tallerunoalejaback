package com.taller1.colegioJMA2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taller1.colegioJMA2.model.PersonalModel; 


public interface PersonalRepo extends JpaRepository<PersonalModel, Integer>{

}
