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
            // Create employees
            Employee bilbo = new Employee("Emele-Onu Favour", "Mobile dev");
            Employee frodo = new Employee("Fagbola Peter", "Cybersecurity analyst");
            Employee gandalf = new Employee("Abolarin Tobi", "Web dev");
            Employee aragorn = new Employee("Adejola James", "Designer");
            Employee legolas = new Employee("Ibidamitan Busayo", "Web dev");

            // Save employees to the repository
            repository.save(bilbo);
            repository.save(frodo);
            repository.save(gandalf);
            repository.save(aragorn);
            repository.save(legolas);

            // Set boss relationships
            frodo.setBoss(bilbo);
            aragorn.setBoss(gandalf);
            legolas.setBoss(aragorn);

            // Save updated employees to establish boss relationships
            repository.save(frodo);
            repository.save(aragorn);
            repository.save(legolas);

            // Log the saved employees
            log.info("Preloading " + bilbo);
            log.info("Preloading " + frodo);
            log.info("Preloading " + gandalf);
            log.info("Preloading " + aragorn);
            log.info("Preloading " + legolas);
        };
    }
}
