package com.curso.springbootapirestbackend.controllers;

import com.curso.springbootapirestbackend.models.entity.Cliente;
import com.curso.springbootapirestbackend.models.services.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class ClienteRestController {
    //dependency injection
    @Autowired
    private IClienteService clienteService;

    @GetMapping("/clientes")
    @ResponseStatus(HttpStatus.OK)
    public List<Cliente> index() {
        return clienteService.finAll();
    }

    @GetMapping("/clientes/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> show(@PathVariable Long id) {
        Cliente cli = null;
        Map<String, Object> response = new HashMap<String, Object>();
        try {
            cli = clienteService.finById(id);

            if (cli == null) {
                response.put("message", "El cliente con Id: ".concat(id.toString()).concat(" no existe"));
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<Cliente>(cli, HttpStatus.OK);
        } catch (DataAccessException e) {
            response.put("message", "Error en Base de datos");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/clientes")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@Valid  @RequestBody Cliente cliente, BindingResult result) {
        Cliente cli = null;
        Map<String, Object> response = new HashMap<>();

        if(result.hasErrors()) {

            /**
             *       List error = new ArrayList<>();
             *       for (FieldError err : result.getFieldErrors()) {
             *          error.add(err);
             *       }
             *       response.put("", error);
             */

            List errors = result.getFieldErrors()
                    .stream()
                    .map(e -> e.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            cli = clienteService.save(cliente);
            if (cli == null) {
                response.put("message", "No se pudo registrar el usuario");
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            response.put("message", "Cliente creado de forma exitosa");
            response.put("cliente", cli);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

        } catch (DataAccessException e) {
            response.put("message", "Error en Base de datos");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/clientes/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> update(@Valid @RequestBody Cliente cliente, BindingResult result, @PathVariable Long id) {
        Cliente cli = null;
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {

            List errors = result.getFieldErrors()
                    .stream()
                    .map(e -> e.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        try {

            cli = clienteService.finById(id);

            if (cli == null) {
                response.put("message", "El cliente con Id: ".concat(id.toString()).concat(" no existe"));
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
            }

            cli.setNombre(cliente.getNombre());
            cli.setApellido(cliente.getApellido());
            cli.setEmail(cliente.getEmail());

            cli = clienteService.save(cli);

            response.put("message", "Cliente actualizado de forma exitosa");
            response.put("cliente", cli);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

        } catch (DataAccessException e) {
            response.put("message", "Error en Base de datos");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/clientes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> delete(@PathVariable Long id) {

        Cliente cli = null;
        Map<String, Object> response = new HashMap<String, Object>();

        try {
            cli = clienteService.finById(id);

            if (cli == null) {
                response.put("message", "El cliente con Id: ".concat(id.toString()).concat(" no existe"));
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
            }

            clienteService.delete(cli.getId());
            response.put("message", "Cliente eliminado de forma exitosa");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

        } catch (DataAccessException e) {
            response.put("message", "Error en Base de datos");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
