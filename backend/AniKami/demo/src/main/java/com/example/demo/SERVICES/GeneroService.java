package com.example.demo.SERVICES;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Model.Generos;
import com.example.demo.repository.GeneroRepository;


@Service
public class GeneroService {

    @Autowired
    GeneroRepository generoRepository;

    public List<Generos> listarGeneros() {
        return generoRepository.findAll();
    }

}
