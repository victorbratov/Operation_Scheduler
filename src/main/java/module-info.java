module com.example.op_sch {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.op_sch to javafx.fxml;
    exports com.example.op_sch;
}