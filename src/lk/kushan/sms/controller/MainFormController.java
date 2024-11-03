package lk.kushan.sms.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.kushan.sms.bo.BoFactory;
import lk.kushan.sms.bo.custom.StudentBo;
import lk.kushan.sms.dto.StudentDto;

import javafx.event.ActionEvent;
import lk.kushan.sms.view.tm.StudentTM;

import java.sql.SQLException;
import java.util.Optional;


public class MainFormController {
    public TextField txtName;
    public TextField txtContact;
    public Button btnStudentSave;

    private final StudentBo studentBo= BoFactory.getInstance().getBo(BoFactory.BoType.STUDENT);
    public TableView<StudentTM> tblStudents;
    public TableColumn colStudentId;
    public TableColumn colStudentName;
    public TableColumn colContactNumber;
    public TableColumn colSeeMore;
    public TableColumn colDelete;

    private StudentTM selectedStudentId=null;

    public void initialize() throws SQLException, ClassNotFoundException {

        colStudentId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colStudentName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colContactNumber.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colSeeMore.setCellValueFactory(new PropertyValueFactory<>("seeMoreBtn"));
        colDelete.setCellValueFactory(new PropertyValueFactory<>("deleteBtn"));

        loadAllStudents();

        tblStudents.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!=null) {
                selectedStudentId = newValue;
                txtName.setText(newValue.getName());
                txtContact.setText(newValue.getContact());
                btnStudentSave.setText("Update Student");
            }
        });
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
                    } catch (Exception ex) {
                        new Alert(Alert.AlertType.ERROR,"Try Again").show();

                    }
                }
            });

        }
        tblStudents.setItems(tmList);
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
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Try Again").show();

            }
        }else{
            try{
                studentBo.saveStudent(dto);
                new Alert(Alert.AlertType.INFORMATION, "Student Saved").show();
                loadAllStudents();
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Try Again").show();

            }
        }



    }

    public void newStudentOnAction(ActionEvent actionEvent) {
        selectedStudentId=null;
        btnStudentSave.setText("Save Student");
    }

    public void btnSaveProgram(ActionEvent actionEvent) {
    }

    public void btnSaveLaptopOnAction(ActionEvent actionEvent) {
    }

    public void btnRegisterOnAction(ActionEvent actionEvent) {
    }
}
