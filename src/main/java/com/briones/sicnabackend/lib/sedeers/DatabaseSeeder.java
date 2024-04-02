package com.briones.sicnabackend.lib.sedeers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import com.briones.sicnabackend.models.*;
import com.briones.sicnabackend.repositories.*;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private CareerRepository careerRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private SpaceRepository spaceRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) {
        try {
            seedPerson();
            seedUser();
            seedAreas();
            seedCareer();
            seedDepartment();
            seedSpace();
            seedProduct();
            seedReport();
        } catch (DataAccessException e) {
            System.out.println("Error al acceder a la base de datos: " + e.getMessage());
        }
    }

    private void seedAreas() {
        if (areaRepository.count() == 0) {
            saveArea("Laboratorio de Cómputo 1", "Laboratorio de cómputo equipado con computadoras y software especializado");
            saveArea("Laboratorio de Cómputo 2", "Laboratorio de cómputo equipado con computadoras y software especializado");
            saveArea("Laboratorio de Cómputo 3", "Laboratorio de cómputo equipado con computadoras y software especializado");
            saveArea("Laboratorio de Cómputo 4", "Laboratorio de cómputo equipado con computadoras y software especializado");
        }
    }

    private void seedCareer() {
        if (careerRepository.count() == 0) {
            saveCareer("Desarrollo de Software Multiplataforma", "Carrera enfocada al desarrollo de software",1L);
            saveCareer("Entornos Virtuales y Negocios Digitales", "Carrera enfocada al marketing digital y animación 3D", 1L);
        }
    }

    private void seedDepartment() {
        if (departmentRepository.count() == 0) {
            saveDepartment("TI Docencia 1", "Departamento de TI", 1L, 1L);
        }
    }

    private void seedPerson() {
        if (personRepository.count() == 0) {
            try {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date birthDate1 = formatter.parse("1991-03-13");
                savePerson("Joan", "Salas", "Vera", birthDate1, "joan@gmail.com", "764 111 4132", "2201337", "Docente", 1L, 1L);
                
                Date birthDate2 = formatter.parse("2004-01-11");
                savePerson("Luis Octavio", "Lopez", "Martinez", birthDate2, "octavio@gmail.com", "776 106 4678", "220134", "Estudiante", 1L, 1L);
                
                Date birthDate3 = formatter.parse("2004-10-22");
                savePerson("Jose Alejandro", "Briones", "Arroyo", birthDate3, "briones@gmail.com", "775 127 2647", "220103", "Estudiante", 1L, 1L);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    private void seedProduct() {
        if (productRepository.count() == 0) {
            saveProduct("Monitor", "Samsung", 300.00, "Electrónica", "Monitor de 24 pulgadas", "monitor.jpg", 1L);
            saveProduct("Teclado", "Logitech", 50.00, "Periférico", "Teclado inalámbrico", "keyboard.jpg", 1L);
            saveProduct("Ratón", "Razer", 30.00, "Periférico", "Ratón óptico", "mouse.jpg", 1L);
            saveProduct("CPU", "Xtrem", 800.00, "Equipo de Cómputo", "Computadora de escritorio", "cpu.jpg", 1L);
        }
    }


    private void seedReport() {
        if (reportRepository.count() == 0) {
            try {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date startDate1 = formatter.parse("2024-01-22");
                Date endDate1 = formatter.parse("2024-01-25");
                saveReport(startDate1, endDate1, "Entregado a tiempo", 1L, 1L, 2L);
                
                Date startDate2 = formatter.parse("2024-02-13");
                Date endDate2 = formatter.parse("2024-02-20");
                saveReport(startDate2, endDate2, "Entregado a tiempo", 2L, 1L, 2L);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    private void seedSpace() {
        if (spaceRepository.count() == 0) {
            saveSpace("Laboratorio 1", "Laboratorio de Cómputo 1", 1L, 1L);
            saveSpace("Laboratorio 2", "Laboratorio de Cómputo 2", 1L, 1L);
            saveSpace("Laboratorio 3", "Laboratorio de Cómputo 3", 1L, 1L);
        }
    }

    private void seedUser() {
        if (userRepository.count() == 0) {
            saveUser("Docente", "1234", 1L);
        }
    }

    private void saveArea(String name, String description) {
        Area area = new Area();
        area.setName(name);
        area.setDescription(description);
        areaRepository.save(area);
    }

    private void saveCareer(String name, String description, Long area_id) {
        Career career =  new Career();
        career.setName(name);
        career.setDescription(description);
        career.setArea(areaRepository.findById(area_id).orElse(null));
        careerRepository.save(career);
    }

    private void saveDepartment(String name, String description, Long area_id, Long encargado_id) {
        Department department = new Department();
        department.setName(name);
        department.setDescription(description);
        department.setArea(areaRepository.findById(area_id).orElse(null));
        department.setResponsiblePerson(personRepository.findById(encargado_id).orElse(null));
        departmentRepository.save(department);
    }

    private void savePerson(String firstName, String lastName, String middleName, Date birthDate, String email, String phone, String registrationNumber, String personType, Long area_id, Long career_id) {
        Person person = new Person();
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setMiddleName(middleName);
        person.setBirthDate(birthDate);
        person.setEmail(email);
        person.setPhone(phone);
        person.setRegistrationNumber(registrationNumber);
        person.setPersonType(personType);
        person.setArea(areaRepository.findById(area_id).orElse(null));
        person.setCareer(careerRepository.findById(career_id).orElse(null));
        personRepository.save(person);
    }

    private void saveProduct(String name, String brand, double price, String category, String description, String image, Long pertenencia_id) {
        Product product = new Product();
        product.setName(name);
        product.setBrand(brand);
        product.setPrice(price);
        product.setCategory(category);
        product.setDescription(description);
        product.setImage(image);
        product.setSpace(spaceRepository.findById(pertenencia_id).orElse(null));
        productRepository.save(product);
    }

    private void saveReport(Date loanDate, Date returnDateLimit, String status, Long producto_prestado_id, Long prestamista_id, Long prestatario_id) {
        Report report = new Report();
        report.setLoanDate(loanDate);
        report.setReturnDateLimit(returnDateLimit);
        report.setStatus(status);
        report.setBorrowedProduct(productRepository.findById(producto_prestado_id).orElse(null));
        report.setLender(personRepository.findById(prestamista_id).orElse(null));
        report.setBorrower(personRepository.findById(prestatario_id).orElse(null));
        reportRepository.save(report);
    }

    private void saveSpace(String name, String description, Long area_superior_id, Long persona_encargada_id) {
        Space space = new Space();
        space.setName(name);
        space.setDescription(description);
        space.setDepartment(departmentRepository.findById(area_superior_id).orElse(null));
        space.setResponsiblePerson(personRepository.findById(persona_encargada_id).orElse(null));
        spaceRepository.save(space);
    }

    private void saveUser(String userType, String password, Long persona_id) {
        User user = new User();
        user.setUserType(userType);
        user.setPassword(password);
        user.setPerson(personRepository.findById(persona_id).orElse(null));
        userRepository.save(user);
    }
}