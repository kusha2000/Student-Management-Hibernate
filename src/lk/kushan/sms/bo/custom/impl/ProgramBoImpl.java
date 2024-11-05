package lk.kushan.sms.bo.custom.impl;

import lk.kushan.sms.bo.custom.ProgramBo;
import lk.kushan.sms.dao.DaoFactory;
import lk.kushan.sms.dao.custom.ProgramDao;
import lk.kushan.sms.dto.CustomRegistrationData;
import lk.kushan.sms.dto.ProgramDto;
import lk.kushan.sms.entity.Program;
import lk.kushan.sms.entity.Registration;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProgramBoImpl implements ProgramBo {

    private final ProgramDao programDao= DaoFactory.getInstance().getDao(DaoFactory.DaoType.PROGRAM);


    @Override
    public void saveProgram(ProgramDto dto) throws SQLException, ClassNotFoundException {
        Program program = new Program();
        program.setTitle(dto.getTitle());
        program.setCredit(dto.getCredit());
        programDao.save(program);
    }

    @Override
    public List<ProgramDto> findAllProgram() throws SQLException, ClassNotFoundException {
        ArrayList<ProgramDto> dtos=new ArrayList<>();
        for(Program p:programDao.findAll()){
            ProgramDto dto=new ProgramDto(p.getId(), p.getTitle(),p.getCredit());
            dto.setTitle(p.getTitle());
            dto.setCredit(p.getCredit());
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public void deleteProgramById(long id) throws SQLException, ClassNotFoundException, ClassNotFoundException {
        programDao.delete(id);
    }

    @Override
    public void updateProgram(ProgramDto dto) throws SQLException, ClassNotFoundException, ClassNotFoundException {
        Program program = new Program();
        program.setId(dto.getId());
        program.setCredit(dto.getCredit());
        program.setTitle(dto.getTitle());
        programDao.update(program);
    }

    @Override
    public List<Long> findAllStudentIds() {
        return programDao.findAllProgramIds();
    }

    @Override
    public void register(long studentId, long programId) {
        programDao.register(studentId,programId);
    }

    @Override
    public List<CustomRegistrationData> findAllRegistrations() {
        List<CustomRegistrationData> data = new ArrayList<>();
        for (Registration temp:programDao.findAllRegistrations()
        ) {
            data.add(
                    new CustomRegistrationData(
                            temp.getRegDate(),
                            temp.getStudent().getName(),
                            temp.getProgram().getTitle()
                    )
            );
        }
        return data;
    }
}
