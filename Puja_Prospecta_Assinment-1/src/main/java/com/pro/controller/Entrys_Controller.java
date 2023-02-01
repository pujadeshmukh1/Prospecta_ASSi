package com.pro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.pro.model.Data;
import com.pro.model.Entry;
import com.pro.model.EntryDto;
import com.pro.repository.EntryRepository;

@RestController
public class Entrys_Controller {
	
	@Autowired
	private RestTemplate rest;
	
	@Autowired
	private EntryRepository entryRepo;
	
	
	
	
	@PostMapping("/Entrie")
	public ResponseEntity<Entry> saveEntries(@RequestBody EntryDto resultDto) {
		
		Data data= rest.getForObject("https://api.publicapis.org/entries", Data.class);
		
		Entry ent=data.getEntries().get(0);
		
		Entry newEntry=new Entry(resultDto.getTitle(),resultDto.getDescription(),ent.getAuth(),ent.ishTTPS(),ent.getCors(),ent.getLink(),ent.getCategory());
		
		Entry savedEntry=entryRepo.save(newEntry);
		
		return new ResponseEntity<Entry>(savedEntry,HttpStatus.OK);
	
	}
	
	@GetMapping("/Entrie/{Category}")
	public ResponseEntity<List<EntryDto>> getEntries(@PathVariable("category") String category){
		
		Data data= rest.getForObject("https://api.publicapis.org/entries", Data.class);
		
		List<Entry> entries= data.getEntries();
		
		List<EntryDto> list= entries.stream().filter(ent -> ent.getCategory().equalsIgnoreCase(category)).map(e -> new EntryDto(e.getApi(), e.getDescription())).toList();
		
		return new ResponseEntity<List<EntryDto>>(list,HttpStatus.OK);
		
	}
	
	
	
}
