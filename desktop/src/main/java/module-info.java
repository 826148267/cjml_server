module edu.jnu.gdbddesktop {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires rxcontrols;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires lombok;
    requires org.apache.commons.io;
    requires org.apache.httpcomponents.httpcore;
    requires org.apache.httpcomponents.httpclient;
    requires fastjson;
    requires java.sql;
    requires jpbc.api;
    requires jpbc.plaf;
    requires com.jfoenix;
    requires org.kordamp.ikonli.core;

    opens edu.jnu.gdbddesktop.controller to javafx.fxml;
    opens edu.jnu.gdbddesktop.controller.core to javafx.fxml;
    opens edu.jnu.gdbddesktop.entity to fastjson, jpbc.api, jpbc.plaf, org.apache.httpcomponents.httpclient, org.apache.httpcomponents.httpcore;

    exports edu.jnu.gdbddesktop;
    exports edu.jnu.gdbddesktop.entity;
}