package lk.kushan.sms.view.tm;


import javafx.scene.control.Button;

public class LaptopTM {
    private long id;
    private String brand;
    private Button laptopDeleteBtn;

    public LaptopTM() {
    }

    public LaptopTM(long id, String brand, Button laptopDeleteBtn) {
        this.id = id;
        this.brand = brand;
        this.laptopDeleteBtn = laptopDeleteBtn;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Button getLaptopDeleteBtn() {
        return laptopDeleteBtn;
    }

    public void setLaptopDeleteBtn(Button laptopDeleteBtn) {
        this.laptopDeleteBtn = laptopDeleteBtn;
    }
}
