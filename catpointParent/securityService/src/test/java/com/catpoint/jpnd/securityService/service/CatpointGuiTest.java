package com.catpoint.jpnd.securityService.service;

import com.catpoint.jpnd.imageService.Service.FakeImageService;
import com.catpoint.jpnd.securityService.application.ControlPanel;
import com.catpoint.jpnd.securityService.application.DisplayPanel;
import com.catpoint.jpnd.securityService.application.ImagePanel;
import com.catpoint.jpnd.securityService.application.SensorPanel;
import com.catpoint.jpnd.securityService.data.PretendDatabaseSecurityRepositoryImpl;
import com.catpoint.jpnd.securityService.data.SecurityRepository;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class CatpointGuiTest extends JFrame {
    //private SecurityRepository securityRepository = new PretendDatabaseSecurityRepositoryImpl();
    //private FakeImageService imageService = new FakeImageService();
    //private SecurityService securityService = new SecurityService(securityRepository, imageService);
    private SecurityService securityService;
    private DisplayPanel displayPanel ;
    private ControlPanel controlPanel;
    private SensorPanel sensorPanel;
    private ImagePanel imagePanel;

    public CatpointGuiTest(SecurityService securityServ) {
        securityService = securityServ;
        displayPanel = new DisplayPanel(securityServ);
        controlPanel = new ControlPanel(securityServ);
        sensorPanel = new SensorPanel(securityServ);
        imagePanel = new ImagePanel(securityServ);

        setLocation(100, 100);
        setSize(600, 850);
        setTitle("Very Secure App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new MigLayout());
        mainPanel.add(displayPanel, "wrap");
        mainPanel.add(imagePanel, "wrap");
        mainPanel.add(controlPanel, "wrap");
        mainPanel.add(sensorPanel);

        getContentPane().add(mainPanel);

    }
    public SecurityService getSecurityService(){
        return this.securityService;
    }
}
