package com.briones.sicnabackend.lib.sedeers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public void run(String... args) {
        try {
            seedAreas();
            seedCareer();
            seedPerson();
            seedUser();
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
            Area area = new Area();
            area.setName("Tecnologías de la Información");
            area.setDescription("Área especializada en el desarrollo de software");
            areaRepository.save(area);
        }
    }

    private void seedCareer() {
        if (careerRepository.count() == 0) {
            Optional<Area> optionalArea = areaRepository.findByName("Tecnologías de la Información");
            if (optionalArea.isPresent()) {
                Area area = optionalArea.get();
                saveCareer("Desarrollo de Software Multiplataforma", "Carrera enfocada al desarrollo de software", area);
                saveCareer("Entornos Virtuales y Negocios Digitales", "Carrera enfocada al marketing digital y animación 3D", area);
            }
        }
    }

    private void seedPerson() {
        if (personRepository.count() == 0) {
            try {
                Optional<Area> optionalArea = areaRepository.findByName("Tecnologías de la Información");
                if (optionalArea.isPresent()) {
                    Area area = optionalArea.get();
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    Date birthDate1 = formatter.parse("1991-03-13");
                    savePerson("Joan", "Salas", "Vera", birthDate1, "joan@gmail.com", "764 111 4132", "2201337", "Docente", area);

                    Date birthDate2 = formatter.parse("2004-01-11");
                    savePerson("Luis Octavio", "Lopez", "Martinez", birthDate2, "octavio@gmail.com", "776 106 4678", "220134", "Estudiante", area);

                    Date birthDate3 = formatter.parse("2004-10-22");
                    savePerson("Jose Alejandro", "Briones", "Arroyo", birthDate3, "briones@gmail.com", "775 127 2647", "220103", "Estudiante", area);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    private void seedUser() {
        if (userRepository.count() == 0) {
            Optional<Person> optionalPerson = personRepository.findByFirstName("Joan");
            if (optionalPerson.isPresent()) {
                Person person = optionalPerson.get();
                saveUser("admin", "12345", true, person);
            }
        }
    }

    private void seedDepartment() {
        if (departmentRepository.count() == 0) {
            Optional<Area> optionalArea = areaRepository.findById(1L); 
            Optional<Person> optionalEncargado = personRepository.findById(1L);
            if (optionalArea.isPresent() && optionalEncargado.isPresent()) {
                Area area = optionalArea.get();
                Person encargado = optionalEncargado.get();
                saveDepartment("TI Docencia 1", "Departamento de TI", area, encargado);
            }
        }
    }
    
    private void seedSpace() {
        if (spaceRepository.count() == 0) {
            Optional<Department> optionalDepartment = departmentRepository.findById(1L); 
            Optional<Person> optionalEncargado = personRepository.findById(1L); 
            if (optionalDepartment.isPresent() && optionalEncargado.isPresent()) {
                Department department = optionalDepartment.get();
                Person encargado = optionalEncargado.get();
                saveSpace("Laboratorio 1", "Laboratorio de Cómputo 1", department, encargado.getId()); 
                saveSpace("Laboratorio 2", "Laboratorio de Cómputo 2", department, encargado.getId());
                saveSpace("Laboratorio 3", "Laboratorio de Cómputo 3", department, encargado.getId());
            }
        }
    }    
    
    private void seedProduct() {
        if (productRepository.count() == 0) {
            Optional<Space> optionalSpace = spaceRepository.findById(1L); 
            if (optionalSpace.isPresent()) {
                Space space = optionalSpace.get();
                saveProduct("Monitor", "Samsung", 300.00, "Electrónica", "Monitor de 24 pulgadas", "monitor.jpg", space);
                saveProduct("Teclado", "Logitech", 50.00, "Periférico", "Teclado inalámbrico", "keyboard.jpg", space);
                saveProduct("Ratón", "Razer", 30.00, "Periférico", "Ratón óptico", "mouse.jpg", space);
                saveProduct("CPU", "Xtrem", 800.00, "Equipo de Cómputo", "Computadora de escritorio", "cpu.jpg", space);
            }
        }
    }
    
    private void seedReport() {
        if (reportRepository.count() == 0) {
            try {
                Optional<Person> optionalLender = personRepository.findById(1L); 
                Optional<Person> optionalBorrower = personRepository.findById(2L); 
                Optional<Product> optionalProduct = productRepository.findById(1L); 
                if (optionalLender.isPresent() && optionalBorrower.isPresent() && optionalProduct.isPresent()) {
                    Person lender = optionalLender.get();
                    Person borrower = optionalBorrower.get();
                    Product product = optionalProduct.get();
    
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    Date startDate1 = formatter.parse("2024-01-22");
                    Date endDate1 = formatter.parse("2024-01-25");
                    Date returnDateLimit1 = formatter.parse("2024-01-25"); 
                    saveReport(startDate1, endDate1, returnDateLimit1, "Entregado a tiempo", product, lender, borrower);
    
                    startDate1 = formatter.parse("2024-02-13");
                    endDate1 = formatter.parse("2024-02-20");
                    Date returnDateLimit2 = formatter.parse("2024-02-20"); 
                    saveReport(startDate1, endDate1, returnDateLimit2, "Entregado a tiempo", product, borrower, lender);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }      

    private void saveCareer(String name, String description, Area area) {
        Career career = new Career();
        career.setName(name);
        career.setDescription(description);
        career.setArea(area);
        careerRepository.save(career);
    }

    private void saveDepartment(String name, String description, Area area, Person encargado) {
        Department department = new Department();
        department.setName(name);
        department.setDescription(description);
        department.setArea(area);
        department.setResponsiblePerson(encargado);
        departmentRepository.save(department);
    }

    private void savePerson(String firstName, String lastName, String middleName, Date birthDate, String email,
            String phone, String registrationNumber, String personType, Area area) {
        Person person = new Person();
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setMiddleName(middleName);
        person.setBirthDate(birthDate);
        person.setEmail(email);
        person.setPhone(phone);
        person.setRegistrationNumber(registrationNumber);
        person.setPersonType(personType);
        person.setArea(area);
        personRepository.save(person);
    }

    private void saveProduct(String name, String brand, double price, String category, String description, String image, Space space) {
        Product product = new Product();
        product.setName(name);
        product.setBrand(brand);
        product.setPrice(price);
        product.setCategory(category);
        product.setDescription(description);
        product.setImage(image);
        product.setSpace(space);
        productRepository.save(product);
    }

    private void saveReport(Date startDate, Date endDate, Date returnDateLimit, String status, Product product, Person lender, Person borrower) {
        Report report = new Report();
        report.setLoanDate(startDate);
        report.setModificationDate(endDate);
        report.setReturnDateLimit(returnDateLimit); 
        report.setStatus(status);
        report.setBorrowedProduct(product);
        report.setLender(lender);
        report.setBorrower(borrower);
        reportRepository.save(report);
    }

    private void saveSpace(String name, String description, Department department, Long encargadoId) {
        Space space = new Space();
        space.setName(name);
        space.setDescription(description);
        space.setDepartment(department);
        
        Optional<Person> optionalEncargado = personRepository.findById(encargadoId);
        if (optionalEncargado.isPresent()) {
            Person encargado = optionalEncargado.get();
            space.setResponsiblePerson(encargado); 
        } else {
            System.out.println("Persona encargada con ID " + encargadoId + " no encontrada.");
        }
    
        spaceRepository.save(space);
    }    

    private void saveUser(String username, String password, boolean enabled, Person person) {
        User user = new User();
        user.setUserType(username);
        user.setPassword(password);
        user.setStatus(enabled);
        user.setPerson(person); 
        userRepository.save(user);
    }
}
