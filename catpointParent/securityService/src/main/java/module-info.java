module com.catpoint.jpnd.securityService{
    requires java.desktop;
    requires com.miglayout.swing;
    requires com.catpoint.jpnd.imageService;
    requires com.google.gson;
    requires com.google.common;
    requires java.prefs;
    opens com.catpoint.jpnd.securityService.data to com.google.gson;

}