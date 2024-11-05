package lk.kushan.sms.view.tm;

import javafx.scene.control.Button;

public class ProgramTM {
    private long id;
    private String title;
    private int credit;
    private Button programDeleteBtn;

    public ProgramTM() {
    }

    public ProgramTM(long id, String title, int credit, Button programDeleteBtn) {
        this.id = id;
        this.title = title;
        this.credit = credit;
        this.programDeleteBtn = programDeleteBtn;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public Button getProgramDeleteBtn() {
        return programDeleteBtn;
    }

    public void setProgramDeleteBtn(Button programDeleteBtn) {
        this.programDeleteBtn = programDeleteBtn;
    }
}
