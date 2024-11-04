package lk.kushan.sms.bo.custom.impl;

import lk.kushan.sms.bo.custom.LaptopBo;
import lk.kushan.sms.dao.DaoFactory;
import lk.kushan.sms.dao.custom.LaptopDao;
import lk.kushan.sms.dto.CreateLaptopDto;
import lk.kushan.sms.dto.StudentDto;
import lk.kushan.sms.entity.Laptop;
import lk.kushan.sms.entity.Student;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LaptopBoImpl implements LaptopBo {
    LaptopDao laptopDao= DaoFactory.getInstance().getDao(DaoFactory.DaoType.LAPTOP);

    @Override
    public void saveLaptop(CreateLaptopDto dto) throws SQLException, ClassNotFoundException {
        Laptop laptop = new Laptop();
        laptop.setBrand(dto.getBrand());
        laptopDao.saveLaptopWithStudentId(dto.getStudentId(), laptop);

    }

    @Override
    public List<CreateLaptopDto> findAllLaptops() throws SQLException, ClassNotFoundException {
        ArrayList<CreateLaptopDto> dtos=new ArrayList<>();
        for(Laptop l:laptopDao.findAll()){
            CreateLaptopDto dto=new CreateLaptopDto(l.getStudent().getId(), l.getBrand());
            dto.setStudentId(l.getStudent().getId());
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public void deleteLaptopById(long id) throws SQLException, ClassNotFoundException, ClassNotFoundException {
        laptopDao.delete(id);
    }
}
