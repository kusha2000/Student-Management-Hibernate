package lk.kushan.sms.dao.custom;

import lk.kushan.sms.dao.CrudDao;
import lk.kushan.sms.entity.Program;
import lk.kushan.sms.entity.Registration;

import java.util.List;

public interface ProgramDao extends CrudDao<Program,Long> {
    public List<Long> findAllProgramIds();
    List<Registration> findAllRegistrations();
    void register(long studentId, long programId);
}
