package com.example.demo.payroll;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(EmployeeRepository repository) {
        return args -> {
            Employee favour = new Employee("Emele-Onu Favour", "Mobile dev");
            Employee pogba = new Employee("Fagbola Peter", "Cybersecurity analyst");
            Employee abots = new Employee("Abolarin Tobi", "Web dev");
            Employee james = new Employee("Adejola James", "Designer");
            Employee busayo = new Employee("Ibidamitan Busayo", "Web dev");

            repository.save(favour);
            repository.save(pogba);
            repository.save(abots);
            repository.save(james);
            repository.save(busayo);

            pogba.setBoss(favour);
            james.setBoss(abots);
            busayo.setBoss(james);

            repository.save(pogba);
            repository.save(james);
            repository.save(busayo);

            log.info("Preloading " + favour);
            log.info("Preloading " + pogba);
            log.info("Preloading " + abots);
            log.info("Preloading " + james);
            log.info("Preloading " + busayo);
        };
    }
}
