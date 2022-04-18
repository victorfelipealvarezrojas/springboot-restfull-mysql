package com.curso.springbootapirestbackend.models.dao;

import com.curso.springbootapirestbackend.models.entity.Cliente;
import org.springframework.data.repository.CrudRepository;

public interface IClienteDao extends CrudRepository<Cliente,Long> { }
