module com.example.op_sch {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;


    opens com.example.op_sch to javafx.fxml, org.hibernate.orm.core, javafx.graphics;
    exports com.example.op_sch;
    exports com.example.op_sch.GUI.AllScreens;
    exports com.example.op_sch.GUI;
    opens com.example.op_sch.GUI.AllScreens to javafx.fxml, javafx.graphics, org.hibernate.orm.core;
    exports com.example.op_sch.Features;
    opens com.example.op_sch.Features to javafx.fxml, javafx.graphics, org.hibernate.orm.core;
}