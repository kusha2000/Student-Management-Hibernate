package lk.kushan.sms.dao.custom;

import lk.kushan.sms.dao.CrudDao;
import lk.kushan.sms.entity.Laptop;


public interface LaptopDao extends CrudDao<Laptop,Long> {
    public void saveLaptopWithStudentId(long studentId,Laptop laptop);
}
