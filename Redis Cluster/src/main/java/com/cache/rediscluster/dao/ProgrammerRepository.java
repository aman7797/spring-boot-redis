package com.cache.rediscluster.dao;

import java.util.List;
import java.util.Set;

import com.cache.rediscluster.model.Programmer;


public interface ProgrammerRepository {

	/*******************************************************************************************************************
	 * String Operations
	 *******************************************************************************************************************/
	void setProgrammerAsString(String idKey, String programmer);

	String getProgrammerAsString(String idKey);

	/*******************************************************************************************************************
	 * List Operations
	 *******************************************************************************************************************/

	void addToProgrammerList(Programmer programmer);

	List<Programmer> getProgrammerListMembers();

	Long getProgrmmerListCount();

	/*******************************************************************************************************************
	 * Set Operations
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
