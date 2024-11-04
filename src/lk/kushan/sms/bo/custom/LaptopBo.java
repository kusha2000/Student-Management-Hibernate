package lk.kushan.sms.bo.custom;

import lk.kushan.sms.dto.CreateLaptopDto;
import lk.kushan.sms.dto.StudentDto;

import java.sql.SQLException;
import java.util.List;

public interface LaptopBo {
    public void saveLaptop(CreateLaptopDto dto) throws SQLException, ClassNotFoundException;
    public List<CreateLaptopDto> findAllLaptops() throws SQLException, ClassNotFoundException;
    public void deleteLaptopById(long id) throws SQLException, ClassNotFoundException, ClassNotFoundException;

}
