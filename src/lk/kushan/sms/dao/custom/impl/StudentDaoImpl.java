package lk.kushan.sms.dao.custom.impl;

import lk.kushan.sms.dao.custom.StudentDao;
import lk.kushan.sms.entity.Student;
import lk.kushan.sms.exceptions.NotFoundException;
import lk.kushan.sms.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Query;

import java.sql.SQLException;
import java.util.List;

public class StudentDaoImpl implements StudentDao {
    @Override
    public void save(Student student) throws SQLException, ClassNotFoundException {
        try(Session session= HibernateUtil.getInstance().openSession()){
            session.beginTransaction();
            session.save(student);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(Student student) throws SQLException, ClassNotFoundException {
        try(Session session= HibernateUtil.getInstance().openSession()){
            session.beginTransaction();
            Student selectedStudent=find(student.getId());
            if(selectedStudent!=null){
                selectedStudent.setName(student.getName());
                selectedStudent.setContact(student.getContact());
                session.update(selectedStudent);
                session.getTransaction().commit();
                return;
            }
            throw new NotFoundException("Can't find Data");
        }
    }

    @Override
    public Student find(Long id) throws SQLException, ClassNotFoundException {
        try(Session session= HibernateUtil.getInstance().openSession()){
            String hql="FROM Student WHERE id=:provideId";
            Query<Student> query=session.createQuery(hql,Student.class);
            query.setParameter("provideId",id);
            return query.uniqueResult();
        }
    }

    @Override
    public void delete(Long id) throws SQLException, ClassNotFoundException {
        try(Session session= HibernateUtil.getInstance().openSession()){
            session.beginTransaction();
            Query query = session.createQuery("DELETE FROM Student WHERE id=:selectedId");
            query.setParameter("selectedId",id);
            query.executeUpdate();
        }
    }

    @Override
    public List<Student> findAll() throws SQLException, ClassNotFoundException {
        try(Session session= HibernateUtil.getInstance().openSession()){
            return session.createQuery("FROM Student",Student.class).list();
        }

    }
}
