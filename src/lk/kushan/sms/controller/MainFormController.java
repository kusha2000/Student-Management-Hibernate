package lk.kushan.sms.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.kushan.sms.bo.BoFactory;
import lk.kushan.sms.bo.custom.LaptopBo;
import lk.kushan.sms.bo.custom.ProgramBo;
import lk.kushan.sms.bo.custom.StudentBo;
import lk.kushan.sms.dto.CreateLaptopDto;
import lk.kushan.sms.dto.ProgramDto;
import lk.kushan.sms.dto.StudentDto;

import javafx.event.ActionEvent;
import lk.kushan.sms.view.tm.LaptopTM;
import lk.kushan.sms.view.tm.ProgramTM;
import lk.kushan.sms.view.tm.StudentTM;

import java.sql.SQLException;
import java.util.Optional;


public class MainFormController {
    public TextField txtName;
    public TextField txtContact;
    public Button btnStudentSave;

    private final StudentBo studentBo= BoFactory.getInstance().getBo(BoFactory.BoType.STUDENT);
    private final LaptopBo laptopBo= BoFactory.getInstance().getBo(BoFactory.BoType.LAPTOP);
    private final ProgramBo programBo= BoFactory.getInstance().getBo(BoFactory.BoType.PROGRAM);


    public TableView<StudentTM> tblStudents;
    public TableColumn colStudentId;
    public TableColumn colStudentName;
    public TableColumn colContactNumber;
    public TableColumn colSeeMore;
    public TableColumn colDelete;
    public TextField txtLapBrand;
    public TextField txtLapSearch;
    public TableView tblLaptops;
    public TableColumn colLapId;
    public TableColumn colBrand;
    public TableColumn colLapDelete;
    public ComboBox<Long> cmbStudent;
    public Button btnLaptopSave;
    public TextField txtProgramTitle;
    public TextField txtProgramCredit;
    public TableView<ProgramTM>  tblPrograms;
    public TableColumn colProgramId;
    public TableColumn colProgramTitle;
    public TableColumn colProgramCredit;
    public TableColumn colProgramDelete;
    public Button btnProgramSave;

    private StudentTM selectedStudentId=null;
    private ProgramTM selectedProgramId=null;

    public void initialize() throws SQLException, ClassNotFoundException {

        colStudentId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colStudentName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colContactNumber.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colSeeMore.setCellValueFactory(new PropertyValueFactory<>("seeMoreBtn"));
        colDelete.setCellValueFactory(new PropertyValueFactory<>("deleteBtn"));

        colLapId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        colLapDelete.setCellValueFactory(new PropertyValueFactory<>("laptopDeleteBtn"));

        colProgramId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colProgramTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colProgramCredit.setCellValueFactory(new PropertyValueFactory<>("credit"));
        colProgramDelete.setCellValueFactory(new PropertyValueFactory<>("programDeleteBtn"));

        loadAllStudents();
        loadAllStudentsForLaptopSection();
        loadAllLaptops();
        loadAllPrograms();

        tblStudents.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!=null) {
                selectedStudentId = newValue;
                txtName.setText(newValue.getName());
                txtContact.setText(newValue.getContact());
                btnStudentSave.setText("Update Student");
            }
        });

        tblPrograms.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!=null) {
                selectedProgramId = newValue;
                txtProgramCredit.setText(String.valueOf(newValue.getCredit()));
                txtProgramTitle.setText(newValue.getTitle());
                btnProgramSave.setText("Update Program");
            }
        });

    }

    private void loadAllStudentsForLaptopSection() throws SQLException, ClassNotFoundException {
        ObservableList<Long> obList=FXCollections.observableArrayList();
        for(StudentDto dto:studentBo.findAllStudents()){
            obList.add(dto.getId());
        }
        cmbStudent.setItems(obList);
    }

    private void loadAllStudents() throws SQLException, ClassNotFoundException {
        ObservableList<StudentTM> tmList= FXCollections.observableArrayList();
        for (StudentDto dto:studentBo.findAllStudents()){
            Button deleteBtn = new Button("Delete");
            deleteBtn.setStyle("-fx-background-color: #c8392b");
            Button seeMoreBtn = new Button("See More");
            seeMoreBtn.setStyle("-fx-background-color: #2988b9");


            StudentTM tm=new StudentTM(dto.getId(),dto.getName(),dto.getContact(),deleteBtn,seeMoreBtn);
            tmList.add(tm);

            deleteBtn.setOnAction(e->{
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you",ButtonType.YES,ButtonType.NO);
                Optional<ButtonType> selectedButtonData = alert.showAndWait();
                if(selectedButtonData.get().equals(ButtonType.YES)){
                    try {
                        studentBo.deleteStudentById(tm.getId());
                        new Alert(Alert.AlertType.INFORMATION,"Student Deleted").show();
                        loadAllStudents();
                        loadAllStudentsForLaptopSection();
                    } catch (Exception ex) {
                        new Alert(Alert.AlertType.ERROR,"Try Again").show();

                    }
                }
            });

        }
        tblStudents.setItems(tmList);
    }
    private void loadAllPrograms() throws SQLException, ClassNotFoundException {
        ObservableList<ProgramTM> tmList= FXCollections.observableArrayList();
        for (ProgramDto dto:programBo.findAllProgram()){
            Button deleteBtn = new Button("Delete");
            deleteBtn.setStyle("-fx-background-color: #c8392b");

            ProgramTM tm=new ProgramTM(dto.getId(),dto.getTitle(),dto.getCredit(),deleteBtn);
            tmList.add(tm);

            deleteBtn.setOnAction(e->{
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you",ButtonType.YES,ButtonType.NO);
                Optional<ButtonType> selectedButtonData = alert.showAndWait();
                if(selectedButtonData.get().equals(ButtonType.YES)){
                    try {
                        programBo.deleteProgramById(tm.getId());
                        new Alert(Alert.AlertType.INFORMATION,"Program Deleted").show();
                        loadAllPrograms();
                    } catch (Exception ex) {
                        new Alert(Alert.AlertType.ERROR,"Try Again").show();

                    }
                }
            });

        }
        tblPrograms.setItems(tmList);
    }

    public void btnSaveStudentOnAction(ActionEvent actionEvent) {
        StudentDto dto=new StudentDto();
        dto.setName(txtName.getText());
        dto.setContact(txtContact.getText());

        if(btnStudentSave.getText().equals("Update Student")) {
            if(selectedStudentId==null) {
                new Alert(Alert.AlertType.ERROR,"Try Again").show();
                return;
            }
            try{
                dto.setId(selectedStudentId.getId());
                studentBo.updateStudent(dto);;
                new Alert(Alert.AlertType.INFORMATION, "Student Updated").show();
                selectedStudentId=null;
                btnStudentSave.setText("Save Student");
                loadAllStudents();
                loadAllStudentsForLaptopSection();
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Try Again").show();

            }
        }else{
            try{
                studentBo.saveStudent(dto);
                new Alert(Alert.AlertType.INFORMATION, "Student Saved").show();
                loadAllStudents();
                loadAllStudentsForLaptopSection();
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Try Again").show();

            }
        }
    }

    public void newStudentOnAction(ActionEvent actionEvent) {
        selectedStudentId=null;
        btnStudentSave.setText("Save Student");
    }


    public void btnSaveLaptopOnAction(ActionEvent actionEvent) {
        try{
            laptopBo.saveLaptop(new CreateLaptopDto(cmbStudent.getValue(), txtLapBrand.getText()));
            new Alert(Alert.AlertType.INFORMATION, "Laptop Saved").show();
            loadAllLaptops();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Try Again").show();

        }
    }

    private void loadAllLaptops() throws SQLException, ClassNotFoundException {
        ObservableList<LaptopTM> tmList= FXCollections.observableArrayList();
        for (CreateLaptopDto dto:laptopBo.findAllLaptops()){
            Button deleteBtn = new Button("Delete");
            deleteBtn.setStyle("-fx-background-color: #c8392b");

            LaptopTM tm=new LaptopTM(dto.getStudentId(),dto.getBrand(),deleteBtn);
            tmList.add(tm);

            deleteBtn.setOnAction(e->{
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you",ButtonType.YES,ButtonType.NO);
                Optional<ButtonType> selectedButtonData = alert.showAndWait();
                if(selectedButtonData.get().equals(ButtonType.YES)){
                    try {
                        laptopBo.deleteLaptopById(tm.getId());
                        new Alert(Alert.AlertType.INFORMATION,"Laptop Deleted").show();
                        loadAllLaptops();
                    } catch (Exception ex) {
                        new Alert(Alert.AlertType.ERROR,"Try Again").show();

                    }
                }
            });

        }
        tblLaptops.setItems(tmList);
    }

    public void btnRegisterOnAction(ActionEvent actionEvent) {
    }

    public void btnSaveProgramOnAction(ActionEvent actionEvent) {
        ProgramDto dto=new ProgramDto();
        dto.setCredit(Integer.parseInt(txtProgramCredit.getText()));
        dto.setTitle(txtProgramTitle.getText());

        if(btnProgramSave.getText().equals("Update Program")) {
            if(selectedProgramId==null) {
                new Alert(Alert.AlertType.ERROR,"Try Again").show();
                return;
            }
            try{
                dto.setId(selectedProgramId.getId());
                programBo.updateProgram(dto);
                new Alert(Alert.AlertType.INFORMATION, "Program Updated").show();
                selectedProgramId=null;
                btnProgramSave.setText("Save Program");
                loadAllPrograms();

            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Try Again").show();

            }
        }else{
            try{
                programBo.saveProgram(dto);
                new Alert(Alert.AlertType.INFORMATION, "Program Saved").show();
                loadAllPrograms();
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Try Again").show();

            }
        }
    }

    public void newProgramOnAction(ActionEvent actionEvent) {
        selectedProgramId=null;
        btnProgramSave.setText("Save Program");
    }
}
