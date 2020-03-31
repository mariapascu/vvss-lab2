package Laboratory3;

import Laboratory2.domain.Student;
import Laboratory2.domain.Tema;
import Laboratory2.repository.NotaXMLRepo;
import Laboratory2.repository.StudentXMLRepo;
import Laboratory2.repository.TemaXMLRepo;
import Laboratory2.service.Service;
import Laboratory2.validation.NotaValidator;
import Laboratory2.validation.StudentValidator;
import Laboratory2.validation.TemaValidator;
import Laboratory2.validation.ValidationException;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AssignmentTest {
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

    private int getSizeOfIterable(Iterable< Student > arr) {
        int cnt = 0;
        for (Student s : arr) {
            cnt++;
        }
        return cnt;
    }
    @Test
    public void addAssignmentCorrect(){
        Tema newTema=new Tema("122","Tema noua",14,13);
        assertEquals(null,serviceTest.findTema("122"));
        serviceTest.addTema(newTema);
        assertEquals(newTema,serviceTest.findTema("122"));

    }

    @Test(expected = ValidationException.class)
    public void addAssignmentIncorrect(){
        Tema newTema=new Tema("","Tema noua",14,13);
        serviceTest.addTema(newTema);
    }

    @After
    public void deleteAdded(){
        serviceTest.deleteTema("122");
    }
}
