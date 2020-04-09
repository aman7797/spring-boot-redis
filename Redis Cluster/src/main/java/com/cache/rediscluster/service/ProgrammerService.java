package com.cache.rediscluster.service;

import java.util.List;
import java.util.Set;

import com.cache.rediscluster.model.Programmer;


public interface ProgrammerService {

	/*******************************************************************************************************************
	 * String Operation
	 *******************************************************************************************************************/
	void setProgrammerAsString(String idKey, String programmer);

	String getProgrammerAsString(String idKey);

	/*******************************************************************************************************************
	 * List Operation
	 *******************************************************************************************************************/
	void addToProgrammerList(Programmer programmer);

	List<Programmer> getProgrammerListMembers();

	Long getProgrmmerListCount();

	/*******************************************************************************************************************
	 * Set Operation
	 *******************************************************************************************************************/

	void addToProgrammerSet(Programmer... programmer);

	Set<Programmer> getProgrammerSetMembers();

	boolean isSerMember(Programmer programmer);

	/*******************************************************************************************************************
	 * Flush All
	 *******************************************************************************************************************/

	void deleteString(String idKey);

	void deleteAllList();

	void deleteAllSet();
}
