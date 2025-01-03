package lk.kushan.sms.bo;

import lk.kushan.sms.bo.custom.impl.LaptopBoImpl;
import lk.kushan.sms.bo.custom.impl.ProgramBoImpl;
import lk.kushan.sms.bo.custom.impl.StudentBoImpl;


public class BoFactory {
    private static BoFactory boFactory;
    private BoFactory(){

    }
    public enum BoType{
        BOOK,STUDENT,PROGRAM,LAPTOP
    }
    public static BoFactory getInstance(){
        return boFactory==null?boFactory=new BoFactory():boFactory;
    }

    public <T> T getBo(BoType type){
        switch (type){
            case STUDENT:
                return (T) new StudentBoImpl();
            case BOOK:
                return null;
            case PROGRAM:
                return (T) new ProgramBoImpl();
            case LAPTOP:
                return (T) new LaptopBoImpl();
            default:
                return null;

        }
    }
}
