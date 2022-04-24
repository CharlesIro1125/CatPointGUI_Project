package com.catpoint.jpnd.securityService.application;

import javax.swing.*;

import com.catpoint.jpnd.imageService.Service.ImageService;
import com.catpoint.jpnd.securityService.data.PretendDatabaseSecurityRepositoryImpl;
import com.catpoint.jpnd.securityService.data.SecurityRepository;
import com.catpoint.jpnd.imageService.Service.FakeImageService;
import com.catpoint.jpnd.securityService.service.SecurityService;
import net.miginfocom.swing.MigLayout;

/**
 * This is the primary JFrame for the application that contains all the top-level JPanels.
 *
 * We're not using any dependency injection framework, so this class also handles constructing
 * all our dependencies and providing them to other classes as necessary.
 */

public class CatpointGui extends JFrame{
    private SecurityRepository securityRepository = new PretendDatabaseSecurityRepositoryImpl();
    private ImageService imageService = new FakeImageService();
    private SecurityService securityService = new SecurityService(securityRepository, imageService);
    private DisplayPanel displayPanel = new DisplayPanel(securityService);
    private SensorPanel sensorPanel = new SensorPanel(securityService);
    private ControlPanel controlPanel = new ControlPanel(securityService,sensorPanel);
    private ImagePanel imagePanel = new ImagePanel(securityService);

    public CatpointGui() {
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
}
