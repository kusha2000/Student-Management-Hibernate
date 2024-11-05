package lk.kushan.sms.dao;

import lk.kushan.sms.dao.custom.impl.LaptopDaoImpl;
import lk.kushan.sms.dao.custom.impl.ProgramDaoImpl;
import lk.kushan.sms.dao.custom.impl.StudentDaoImpl;

public class DaoFactory {
    private static DaoFactory daoFactory;
    private DaoFactory(){

    }
    public enum DaoType{
        BOOK,STUDENT,PROGRAM,LAPTOP
    }
    public static DaoFactory getInstance(){
        return daoFactory==null?daoFactory=new DaoFactory():daoFactory;
    }

    public <T> T getDao(DaoType type){
        switch (type){
            case STUDENT:
                return (T) new StudentDaoImpl();
            case BOOK:
                return null;
            case PROGRAM:
                return (T) new ProgramDaoImpl();
            case LAPTOP:
                return (T) new LaptopDaoImpl();
            default:
                return null;

        }
    }
}
