package com.workintech.s18d1.util;
import com.workintech.s18d1.exceptions.BurgerException;
import com.workintech.s18d1.entity.Burger;
import org.springframework.http.HttpStatus;

public class BurgerValidation {

    public static void validateBurger(Burger burger) {

        if (burger == null) {
            throw new BurgerException(
                    "Burger cannot be null",
                    HttpStatus.BAD_REQUEST
            );
        }

        if (burger.getName() == null || burger.getName().isBlank()) {
            throw new BurgerException(
                    "Burger name is required",
                    HttpStatus.BAD_REQUEST
            );
        }

        if (burger.getPrice() <= 0) {
            throw new BurgerException(
                    "Price must be greater than 0",
                    HttpStatus.BAD_REQUEST
            );
        }

        if (burger.getBreadType() == null) {
            throw new BurgerException(
                    "Bread type is required",
                    HttpStatus.BAD_REQUEST
            );
        }
    }
}