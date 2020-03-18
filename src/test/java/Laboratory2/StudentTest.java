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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StudentTest {
    private StudentValidator studentValidator = new StudentValidator();
    private TemaValidator temaValidator = new TemaValidator();
    private String filenameStudent = "fisiere/Studenti.xml";
    private String filenameTema = "fisiere/Teme.xml";
    private String filenameNota = "fisiere/Note.xml";

    StudentXMLRepo studentXMLRepository = new StudentXMLRepo(filenameStudent);
    TemaXMLRepo temaXMLRepository = new TemaXMLRepo(filenameTema);
    NotaValidator notaValidator = new NotaValidator(studentXMLRepository, temaXMLRepository);
    NotaXMLRepo notaXMLRepository = new NotaXMLRepo(filenameNota);
    private Service serviceTest = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);

    private int getSizeOfIterable(Iterable<Student> arr) {
        int cnt = 0;
        for (Student s : arr) {
            cnt++;
        }
        return cnt;
    }

    @Test
    public void addStudentCorrect() {
        Student newStudent = new Student("1000", "Andreea", 921, "aaa@scs.com");
        assertEquals(6, getSizeOfIterable(serviceTest.getAllStudenti()));
        serviceTest.addStudent(newStudent);
        assertEquals(7, getSizeOfIterable(serviceTest.getAllStudenti()));
    }

    @Test(expected = ValidationException.class)
    public void addStudentIncorrect() {
        Student newStudent = new Student("", "Andreea", 921, "aaa@scs.com");
        assertEquals(6, getSizeOfIterable(serviceTest.getAllStudenti()));
        serviceTest.addStudent(newStudent);
        assertEquals(6, getSizeOfIterable(serviceTest.getAllStudenti()));
    }

    @After
    public void after() {
        serviceTest.deleteStudent("1000");

    }
}
