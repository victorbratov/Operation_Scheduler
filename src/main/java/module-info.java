module com.example.op_sch {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;


    opens com.example.op_sch to javafx.fxml, org.hibernate.orm.core;
    exports com.example.op_sch;
}