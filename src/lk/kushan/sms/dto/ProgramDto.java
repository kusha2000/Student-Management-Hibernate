package lk.kushan.sms.dto;

public class ProgramDto {
    private long id;
    private String title;
    private int credit;

    public ProgramDto() {
    }

    public ProgramDto(long id, String title, int credit) {
        this.id = id;
        this.title = title;
        this.credit = credit;
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
}
