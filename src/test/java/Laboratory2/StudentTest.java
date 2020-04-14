package Laboratory2;

import Laboratory2.repository.NotaXMLRepo;
import Laboratory2.repository.StudentXMLRepo;
import Laboratory2.repository.TemaXMLRepo;
import Laboratory2.validation.NotaValidator;
import Laboratory2.validation.StudentValidator;
import Laboratory2.validation.TemaValidator;
import org.junit.Test;
import Laboratory2.service.Service;
import Laboratory2.domain.Student;
import org.junit.After;
import Laboratory2.validation.ValidationException;

import static org.junit.Assert.*;

public class StudentTest {
    private StudentValidator studentValidator = new StudentValidator();
    private TemaValidator temaValidator = new TemaValidator();
    private String filenameStudent = "fisiere/Studenti.xml";
    private String filenameTema = "fisiere/Teme.xml";
    private String filenameNota = "fisiere/Note.xml";

    private StudentXMLRepo studentXMLRepository = new StudentXMLRepo(filenameStudent);
    private TemaXMLRepo temaXMLRepository = new TemaXMLRepo(filenameTema);
    private NotaValidator notaValidator = new NotaValidator(studentXMLRepository, temaXMLRepository);
    private NotaXMLRepo notaXMLRepository = new NotaXMLRepo(filenameNota);
    private Service serviceTest = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);

    @Test
    public void addStudentValid() {
        Student newStudent = new Student("100", "maria", 935, "maria@scs.com");
        assertNull(serviceTest.findStudent("100"));
        Student result = serviceTest.addStudent(newStudent);
        assertEquals(newStudent, serviceTest.findStudent("100"));
        assertNull(result);
    }

    @Test(expected = ValidationException.class)
    public void addStudentInvalidId() {
        Student newStudent = new Student("", "maria", 935, "maria@scs.com");
        serviceTest.addStudent(newStudent);
    }

    @Test(expected = ValidationException.class)
    public void addStudentNullId() {
        Student newStudent = new Student(null, "maria", 935, "maria@scs.com");
        serviceTest.addStudent(newStudent);
    }

    @Test(expected = ValidationException.class)
    public void addStudentInvalidName() {
        Student newStudent = new Student("100", "", 935, "maria@scs.com");
        serviceTest.addStudent(newStudent);
    }

    @Test(expected = ValidationException.class)
    public void addStudentNullName() {
        Student newStudent = new Student("100", null, 935, "maria@scs.com");
        serviceTest.addStudent(newStudent);
    }

    @Test(expected = ValidationException.class)
    public void addStudentInvalidGroup() {
        Student newStudent = new Student("100", "maria", -1, "maria@scs.com");
        serviceTest.addStudent(newStudent);
    }

    @Test(expected = ValidationException.class)
    public void addStudentInvalidEmail() {
        Student newStudent = new Student("100", "maria", 935, "");
        serviceTest.addStudent(newStudent);
    }

    @Test(expected = ValidationException.class)
    public void addStudentNullEmail() {
        Student newStudent = new Student("100", "maria", 935, null);
        serviceTest.addStudent(newStudent);
    }

    @Test
    public void addStudentAlreadyExists() {
        Student newStudent = new Student("100", "maria", 935, "maria@scs.com");
        serviceTest.addStudent(newStudent);
        Student result = serviceTest.addStudent(newStudent);
        assertEquals(newStudent, serviceTest.findStudent("100"));
        assertEquals(newStudent, result);
    }

    @Test
    public void addStudentBoundaryValues() {
        Student newStudent = new Student("3", "m", 0, "a");
        Student result = serviceTest.addStudent(newStudent);
        assertEquals(newStudent, serviceTest.findStudent("3"));
        assertNull(result);
    }

    @After
    public void after() {
        serviceTest.deleteStudent("100");
        serviceTest.deleteStudent("3");

    }
}
