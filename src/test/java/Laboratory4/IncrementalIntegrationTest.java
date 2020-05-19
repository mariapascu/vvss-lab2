package Laboratory4;

import Laboratory2.domain.Student;
import Laboratory2.domain.Tema;
import Laboratory2.domain.Nota;
import Laboratory2.repository.NotaXMLRepo;
import Laboratory2.repository.StudentXMLRepo;
import Laboratory2.repository.TemaXMLRepo;
import Laboratory2.service.Service;
import Laboratory2.validation.NotaValidator;
import Laboratory2.validation.StudentValidator;
import Laboratory2.validation.TemaValidator;
import org.junit.After;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class IncrementalIntegrationTest {
    private String filenameStudent = "fisiere/Studenti.xml";
    private String filenameTema = "fisiere/Teme.xml";
    private String filenameNota = "fisiere/Note.xml";
    private StudentXMLRepo studentXMLRepository = new StudentXMLRepo(filenameStudent);
    private TemaXMLRepo temaXMLRepository = new TemaXMLRepo(filenameTema);
    private NotaXMLRepo notaXMLRepository = new NotaXMLRepo(filenameNota);
    private StudentValidator studentValidator = new StudentValidator();
    private TemaValidator temaValidator = new TemaValidator();
    private NotaValidator notaValidator = new NotaValidator(studentXMLRepository, temaXMLRepository);
    private Service serviceTest = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);

    @Test
    public void addStudentTest() {
        Student newStudent = new Student("1000", "Andreea", 921, "aaa@scs.com");
        assertNull(serviceTest.findStudent("1000"));
        serviceTest.addStudent(newStudent);
        assertEquals(newStudent, serviceTest.findStudent("1000"));
    }

    @Test
    public void addStudentAddAssignmentTest() {
        // student
        Student newStudent = new Student("1000", "Andreea", 921, "aaa@scs.com");
        assertNull(serviceTest.findStudent("1000"));
        serviceTest.addStudent(newStudent);
        assertEquals(newStudent, serviceTest.findStudent("1000"));

        // assignment
        Tema newTema=new Tema("122","Tema noua",2,1);
        assertNull(serviceTest.findTema("122"));
        serviceTest.addTema(newTema);
        assertEquals(newTema,serviceTest.findTema("122"));
    }

    @Test
    public void addStudentAddAssignmentAddGradeTest() {
        // student
        Student newStudent = new Student("1000", "Andreea", 921, "aaa@scs.com");
        assertNull(serviceTest.findStudent("1000"));
        serviceTest.addStudent(newStudent);
        assertEquals(newStudent, serviceTest.findStudent("1000"));

        // assignment
        Tema newTema=new Tema("122","Tema noua",2,1);
        assertNull(serviceTest.findTema("122"));
        serviceTest.addTema(newTema);
        assertEquals(newTema,serviceTest.findTema("122"));

        // grade
        Nota newNota = new Nota("1234", "1000", "122", 10, LocalDate.of(2018, 10, 10));
        assertNull(serviceTest.findNota("1234"));
        serviceTest.addNota(newNota, "good");
        assertEquals(newNota, serviceTest.findNota("1234"));
    }

    @After
    public void remove() {
        serviceTest.deleteStudent("1000");
        serviceTest.deleteTema("122");
        serviceTest.deleteNota("1234");
    }

}
