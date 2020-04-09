package com.cache.Redis.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.cache.Redis.model.Programmer;
import com.cache.Redis.service.ProgrammerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class Controller {

	@Autowired
	ProgrammerService programmerService;
	
	@PostMapping("/progrmmer-string")
	public void addToTopic(@RequestBody Programmer programmer) throws JsonProcessingException{
		ObjectMapper mapper = new ObjectMapper();
		programmerService.setProgrammerAsString(String.valueOf(programmer.getId()), mapper.writeValueAsString(programmer));
	}
	
	@GetMapping("/programmer-string/{idKey}")
	public String readString(@PathVariable String idKey) {
		return programmerService.getProgrammerAsString(idKey);
	}
	
	@PostMapping("/programmer-list")
	public void addToProgrammerList(@RequestBody Programmer programmer){
//		programmerService.addToProgrammerList(programmer);
		
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.postForEntity("localhost:9001/programmer-list", programmer, null);
		
	}
	
	@GetMapping("/programmer-list")
	public List<Programmer> getProgrammerListMembers() {
		return programmerService.getProgrammerListMembers();
	}
	
	@GetMapping("/programmer-list/count")
	public Long getProgrmmerListCount() {
		return programmerService.getProgrmmerListCount();
	}
	
	@PostMapping("/programmer-set")
	public void addToProgrammerSet(@RequestBody Programmer programmer){
		programmerService.addToProgrammerSet(programmer);
	}
	
	@GetMapping("/programmer-set")
	public Set<Programmer> getProgrammerSetMembers() {
		return programmerService.getProgrammerSetMembers();
	}
	
	@PostMapping("/programmer-set/isEmpty")
	public boolean isSerMember(@RequestBody Programmer programmer) {
		return programmerService.isSerMember(programmer);
	}
	
	
}
