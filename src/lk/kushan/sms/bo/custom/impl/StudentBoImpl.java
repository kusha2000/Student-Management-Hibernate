package lk.kushan.sms.bo.custom.impl;

import lk.kushan.sms.bo.custom.StudentBo;
import lk.kushan.sms.dao.DaoFactory;
import lk.kushan.sms.dao.custom.StudentDao;
import lk.kushan.sms.dto.StudentDto;
import lk.kushan.sms.entity.Student;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StudentBoImpl implements StudentBo {

    private final StudentDao studentDao= DaoFactory.getInstance().getDao(DaoFactory.DaoType.STUDENT);

    @Override
    public void saveStudent(StudentDto dto) throws SQLException, ClassNotFoundException {
        Student student = new Student();
        student.setName(dto.getName());
        student.setContact(dto.getContact());
        studentDao.save(student);
    }

    @Override
    public List<StudentDto> findAllStudents() throws SQLException, ClassNotFoundException {
        ArrayList<StudentDto> dtos=new ArrayList<>();
        for(Student s:studentDao.findAll()){
            StudentDto dto=new StudentDto(s.getId(),s.getName(),s.getContact());
            dto.setBooks(s.getBooks());
            dto.setLaptop(s.getLaptop());
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public void deleteStudentById(long id) throws SQLException, ClassNotFoundException, ClassNotFoundException {
        studentDao.delete(id);
    }

    @Override
    public void updateStudent(StudentDto dto) throws SQLException, ClassNotFoundException, ClassNotFoundException {
        Student student = new Student();
        student.setId(dto.getId());
        student.setName(dto.getName());
        student.setContact(dto.getContact());
        studentDao.update(student);
    }

}
