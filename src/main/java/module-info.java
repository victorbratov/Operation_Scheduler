module com.example.op_sch {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires org.testng;
    requires junit;
    requires org.junit.jupiter.api;


    opens com.example.op_sch to javafx.fxml, org.hibernate.orm.core, javafx.graphics;
    exports com.example.op_sch;
    exports com.example.op_sch.GUI.AllScreens;
    exports com.example.op_sch.GUI;
    opens com.example.op_sch.GUI.AllScreens to javafx.fxml, javafx.graphics, org.hibernate.orm.core;
    exports com.example.op_sch.diaryFeatures;
    opens com.example.op_sch.diaryFeatures to javafx.fxml, javafx.graphics, org.hibernate.orm.core;
    exports com.example.op_sch.customComponents;
    opens com.example.op_sch.customComponents to javafx.fxml, javafx.graphics, org.hibernate.orm.core;
    exports com.example.op_sch.professionals;
    opens com.example.op_sch.professionals to javafx.fxml, javafx.graphics, org.hibernate.orm.core;
    exports com.example.op_sch.patients;
    opens com.example.op_sch.patients to javafx.fxml, javafx.graphics, org.hibernate.orm.core;
    exports com.example.op_sch.diary;
    opens com.example.op_sch.diary to javafx.fxml, javafx.graphics, org.hibernate.orm.core;
    exports com.example.op_sch.generalClasses;
    opens com.example.op_sch.generalClasses to javafx.fxml, javafx.graphics, org.hibernate.orm.core;
    exports com.example.op_sch.authentication;
    opens com.example.op_sch.authentication to javafx.fxml, javafx.graphics, org.hibernate.orm.core;
    exports com.example.op_sch.tests;
    opens com.example.op_sch.tests to javafx.fxml, javafx.graphics, org.hibernate.orm.core;
}