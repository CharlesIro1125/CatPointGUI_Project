module com.catpoint.jpnd.securityService{
    requires java.desktop;
    requires com.miglayout.swing;
    requires com.catpoint.jpnd.imageService;
    requires com.google.gson;
    requires com.google.common;
    requires java.prefs;
    exports com.catpoint.jpnd.securityService.application;
    exports com.catpoint.jpnd.securityService.data;
    opens com.catpoint.jpnd.securityService.service;
    opens com.catpoint.jpnd.securityService.data;
    opens com.catpoint.jpnd.securityService.application;
}