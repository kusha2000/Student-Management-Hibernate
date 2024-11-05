package lk.kushan.sms.bo.custom;

import lk.kushan.sms.dto.ProgramDto;

import java.sql.SQLException;
import java.util.List;

public interface ProgramBo {
    public void saveProgram(ProgramDto dto) throws SQLException, ClassNotFoundException;
    public List<ProgramDto> findAllProgram() throws SQLException, ClassNotFoundException;

    public void deleteProgramById(long id) throws SQLException, ClassNotFoundException, ClassNotFoundException;
    public void updateProgram(ProgramDto dto) throws SQLException, ClassNotFoundException, ClassNotFoundException;
}
