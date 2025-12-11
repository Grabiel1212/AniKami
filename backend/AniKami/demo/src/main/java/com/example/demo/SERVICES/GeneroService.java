package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Generos;
import com.example.demo.repository.GeneroRepository;

@Service
public class GeneroService {

    @Autowired
    GeneroRepository generoRepository;

    public List<Generos> listarGeneros() {
        return generoRepository.findAll();
    }

}
