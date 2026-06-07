package com.workintech.s18d1.controller;

import com.workintech.s18d1.dao.BurgerDaoImpl;
import com.workintech.s18d1.entity.BreadType;
import com.workintech.s18d1.entity.Burger;
import com.workintech.s18d1.exceptions.BurgerException;
import com.workintech.s18d1.util.BurgerValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/workintech/burgers")
public class BurgerController {

    private final BurgerDaoImpl burgerDao;

    public BurgerController(BurgerDaoImpl burgerDao) {
        this.burgerDao = burgerDao;
    }

    @GetMapping
    public List<Burger> findAll() {
        return burgerDao.findAll();
    }

    @GetMapping("/{id}")
    public Burger findById(@PathVariable Long id) {
        Burger burger = burgerDao.findById(id);

        if (burger == null) {
            throw new BurgerException("Burger not found", HttpStatus.NOT_FOUND);
        }

        return burger;
    }

    @PostMapping
    public Burger save(@RequestBody Burger burger) {
        BurgerValidation.validateBurger(burger);
        return burgerDao.save(burger);
    }

    @PutMapping("/{id}")
    public Burger update(@PathVariable Long id, @RequestBody Burger burger) {
        Burger existing = burgerDao.findById(id);

        if (existing == null) {
            throw new BurgerException("Burger not found", HttpStatus.NOT_FOUND);
        }

        burger.setId(id);
        BurgerValidation.validateBurger(burger);

        return burgerDao.update(burger);
    }

    @DeleteMapping("/{id}")
    public Burger delete(@PathVariable Long id) {
        Burger burger = burgerDao.remove(id);

        if (burger == null) {
            throw new BurgerException("Burger not found", HttpStatus.NOT_FOUND);
        }

        return burger;
    }

    @GetMapping("/findByPrice")
    public List<Burger> findByPrice(@RequestBody Double price) {
        return burgerDao.findByPrice(price);
    }

    @GetMapping("/findByBreadType")
    public List<Burger> findByBreadType(@RequestBody BreadType breadType) {
        return burgerDao.findByBreadType(breadType);
    }

    @GetMapping("/findByContent")
    public List<Burger> findByContent(@RequestBody String content) {
        return burgerDao.findByContent(content);
    }
}