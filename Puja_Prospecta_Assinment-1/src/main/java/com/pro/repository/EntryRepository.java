package com.pro.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pro.model.Entry;

public interface EntryRepository extends JpaRepository<Entry, Integer>{

}
