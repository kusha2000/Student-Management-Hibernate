package lk.kushan.sms.dao.custom.impl;

import lk.kushan.sms.dao.custom.ProgramDao;
import lk.kushan.sms.entity.Program;
import lk.kushan.sms.entity.Registration;
import lk.kushan.sms.entity.Student;
import lk.kushan.sms.exceptions.NotFoundException;
import lk.kushan.sms.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ProgramDaoImpl implements ProgramDao {
    @Override
    public void save(Program program) throws SQLException, ClassNotFoundException {
        try(Session session= HibernateUtil.getInstance().openSession()){
            session.beginTransaction();
            session.save(program);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(Program program) throws SQLException, ClassNotFoundException {
        try(Session session= HibernateUtil.getInstance().openSession()){
            session.beginTransaction();
            Program selectedProgram=find(program.getId());
            if(selectedProgram!=null){
                selectedProgram.setCredit(program.getCredit());
                selectedProgram.setTitle(program.getTitle());
                session.update(selectedProgram);
                session.getTransaction().commit();
                return;
            }
            throw new NotFoundException("Can't find Data");
        }
    }

    @Override
    public Program find(Long id) throws SQLException, ClassNotFoundException {
        try(Session session= HibernateUtil.getInstance().openSession()){
            String hql="FROM Program WHERE program_id=:provideId";
            Query<Program> query=session.createQuery(hql,Program.class);
            query.setParameter("provideId",id);
            return query.uniqueResult();
        }
    }

    @Override
    public void delete(Long id) throws SQLException, ClassNotFoundException {
        try(Session session= HibernateUtil.getInstance().openSession()){
            session.beginTransaction();
            Query query = session.createQuery("DELETE FROM Program WHERE program_id=:selectedId");
            query.setParameter("selectedId",id);
            query.executeUpdate();
        }
    }

    @Override
    public List<Program> findAll() throws SQLException, ClassNotFoundException {
        try(Session session= HibernateUtil.getInstance().openSession()){
            return session.createQuery("FROM Program",Program.class).list();
        }
    }

    @Override
    public List<Long> findAllProgramIds() {
        /* try(Session session = HibernateUtil.getInstance().openSession()){
            String hql="SELECT p.programId FROM Program p";
            Query query = session.createQuery(hql);
            List list = query.list();
            System.out.println(list);
        }*/
        List<Long> list = new ArrayList<>();
        try(Session session = HibernateUtil.getInstance().openSession()){
            String hql="FROM Program";
            Query<Program> query = session.createQuery(hql, Program.class);
            for (Program p :query.list()
            ) {
                list.add(p.getId());
            }
        }
        return list;
    }

    @Override
    public List<Registration> findAllRegistrations() {
        try (Session session = HibernateUtil.getInstance().openSession()) {
            String hql = "FROM Registration";
            Query<Registration> query = session.createQuery(hql, Registration.class);
            return query.list();
        }
    }

    @Override
    public void register(long studentId, long programId) {
        try (Session session = HibernateUtil.getInstance().openSession()) {
            Query<Student> query =
                    session.createQuery("FROM Student WHERE student_id=:sId", Student.class);
            query.setParameter("sId", studentId);
            Student student = query.uniqueResult();
            if (student == null) {
                throw new RuntimeException("Student not found");
            }
            //-----------------------------
            Query<Program> pQuery =
                    session.createQuery("FROM Program WHERE program_id=:pId", Program.class);
            pQuery.setParameter("pId", programId);
            Program program = pQuery.uniqueResult();
            if (program == null) {
                throw new RuntimeException("program not found");
            }
            session.beginTransaction();
            Registration registration = new Registration();
            registration.setProgram(program);
            registration.setStudent(student);
            registration.setRegDate(new Date());
            session.save(registration);
            session.getTransaction().commit();
        }


    }

}
