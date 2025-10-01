package br.edu.infnet.claudioapi.model.service;

import java.util.List;

public interface CrudService <T, ID> {
	
	T include(T entity);
	T change(ID id,T entity);
	T obtainPutId(ID id);
	void delete(ID id);
	List<T> obtainList();

}