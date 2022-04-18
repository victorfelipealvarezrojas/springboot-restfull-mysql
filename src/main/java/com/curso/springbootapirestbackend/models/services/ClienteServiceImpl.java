package com.curso.springbootapirestbackend.models.services;

import com.curso.springbootapirestbackend.models.dao.IClienteDao;
import com.curso.springbootapirestbackend.models.entity.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service //componente de servicio que sera inyectable, tiene componnt x debajo lo que le transforma en un beans
public class ClienteServiceImpl implements IClienteService {
    @Autowired //inyeccion de dependencia del dao
    private IClienteDao clienteDao;
    @Override
    @Transactional(readOnly = true)
    public List<Cliente> finAll() {
        return (List<Cliente>) clienteDao.findAll();
    }
}
