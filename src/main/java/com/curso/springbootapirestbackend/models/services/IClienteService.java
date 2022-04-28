package com.curso.springbootapirestbackend.models.services;

import com.curso.springbootapirestbackend.models.entity.Cliente;

import java.util.List;

public interface IClienteService  {

    public List<Cliente> finAll();

    public Cliente finById(Long Id);

    // insert and update
    public Cliente save(Cliente cliente);

    public void delete(Long id);

}
