package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.IntStream;

@RestController
@RequestMapping("calculation")
public class CalculationController {

    @GetMapping("/sum")
    public int calculateSum() {
        return IntStream.rangeClosed(1, 1_000_000)
                .parallel()  // Использование параллельного потока для ускорения
                .reduce(0, Integer::sum);
    }
}