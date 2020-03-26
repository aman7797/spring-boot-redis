package com.cache.Redis.service;

import java.util.List;
import java.util.Set;

import com.cache.Redis.model.Programmer;

public interface ProgrammerService {

	void setProgrammerAsString(String idKey, String programmer);

	String getProgrammerAsString(String idKey);

	// List
	void addToProgrammerList(Programmer programmer);

	List<Programmer> getProgrammerListMembers();

	Long getProgrmmerListCount();

	// Set

	void addToProgrammerSet(Programmer... programmer);

	Set<Programmer> getProgrammerSetMembers();

	boolean isSerMember(Programmer programmer);
}