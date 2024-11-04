package lk.kushan.sms.dao.custom.impl;

import lk.kushan.sms.dao.custom.LaptopDao;
import lk.kushan.sms.entity.Laptop;
import lk.kushan.sms.entity.Student;
import lk.kushan.sms.util.HibernateUtil;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class LaptopDaoImpl  implements LaptopDao {
    @Override
    public void save(Laptop laptop) throws SQLException, ClassNotFoundException {

    }

    @Override
    public void update(Laptop laptop) throws SQLException, ClassNotFoundException {

    }

    @Override
    public Laptop find(Long aLong) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public void delete(Long id) throws SQLException, ClassNotFoundException {
        try(Session session= HibernateUtil.getInstance().openSession()){
            session.beginTransaction();
            org.hibernate.Query query = session.createQuery("DELETE FROM Laptop WHERE student_id=:selectedId");
            query.setParameter("selectedId",id);
            query.executeUpdate();
        }
    }

    @Override
    public List<Laptop> findAll() throws SQLException, ClassNotFoundException {
        try(Session session= HibernateUtil.getInstance().openSession()){
            return session.createQuery("FROM Laptop",Laptop.class).list();
        }
    }

    @Override
    public void saveLaptopWithStudentId(long studentId, Laptop laptop) {
        try(Session session= HibernateUtil.getInstance().openSession()){
            session.beginTransaction();
            Query<Student> query=session.createQuery("FROM Student WHERE student_id=:sId", Student.class);
            query.setParameter("sId", studentId);
            Student student=query.uniqueResult();
            if(student==null){
                throw new RuntimeException("Student not found");
            }
            laptop.setStudent(student);
            session.save(laptop);
            session.getTransaction().commit();

        }
    }
}
