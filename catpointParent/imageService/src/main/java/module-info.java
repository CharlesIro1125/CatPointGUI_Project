module com.catpoint.jpnd.imageService {
    requires software.amazon.awssdk.services.rekognition;
    requires software.amazon.awssdk.regions;
    requires software.amazon.awssdk.core;
    requires software.amazon.awssdk.auth;
    requires java.desktop;
    requires slf4j.api;
    exports com.catpoint.jpnd.imageService.Service;
}