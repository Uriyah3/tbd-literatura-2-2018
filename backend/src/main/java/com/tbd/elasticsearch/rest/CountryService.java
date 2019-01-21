package com.tbd.elasticsearch.rest;


import com.tbd.elasticsearch.entities.Book;
import com.tbd.elasticsearch.repository.AuthorRepository;
import com.tbd.elasticsearch.repository.BookRepository;
import com.tbd.elasticsearch.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.tbd.elasticsearch.entities.Country;

@CrossOrigin(maxAge=3600)
@RestController
@RequestMapping("/country")
public class CountryService {

    @Autowired
    private CountryRepository countryRepository;


    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Iterable<Country> getAll() {
        return countryRepository.findAll();
    }


}
