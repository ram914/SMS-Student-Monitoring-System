/*
 * This is an open source project
 * Anybody can view, download this project
 *  Authors : Ram Prasad Gudiwada, 
 *                 Poorna Bindu Karuparthi
 */
package com.pvpsit;

/**
 *
 * @author ram bablu
 */

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.TextArea;
import java.awt.TextField;
import java.util.StringTokenizer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.KeyStroke;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class GraphicsLoader {
    private static JFrame frame;
    static JMenuBar mb;
    static JMenu homeMenu;
    static JMenu fileMenu;
    static JMenu detailsMenu;
    static JMenu deleteMenu;
    static JMenu viewMenu;
    static JMenu helpMenu;
    static JMenu analysisMenu;
    static JMenu[] smDetailsDepts;
    static JMenu[] smDeleteDepts;
    static JMenu[] smAnalysisDepts;
    static JMenu[] smDetailsYears;
    static JMenu[] smDeleteYears;
    static JMenu[] smAnalysisYears;
    static JMenu smSize;
    static JMenu smStyle;
    static JMenu smFont;
    static JMenuItem jmiNew;
    static JMenuItem jmiEdit;
    static JMenuItem jmiExit;
    static JMenuItem jmiCopy;
    static JMenuItem[] jmiSections;
    static JMenuItem[] jmiSize;
    static JMenuItem[] jmiStyle;
    static JMenuItem[] jmiFont;
    static JMenuItem jmiAbout;
    static JLabel jlName;
    static JLabel jlId;
    static JLabel jlCGPA;
    static JLabel jlYear;
    static JLabel jlAttendance;
    static JLabel jlGender;
    static JLabel jlDepartment;
    static JLabel jlAdmission;
    static JLabel jlAddress;
    static JLabel jlMobile;
    static TextField[] tf;
    static TextArea ta;
    static JButton btnSubmit;
    static JButton btnReset;
    static JButton btnEdit;
    static JPanel p;
    static JPanel welcomePanel;
    static JPanel genderPanel;
    static JPanel buttonPanel;
    static JRadioButton jrbMale;
    static JRadioButton jrbFemale;
    static JComboBox<String> jcbDepts;
    static JComboBox<String> jcbYears;
    static JComboBox<String> jcbAttendance;
    static JComboBox<String> jcbAdmissionType;
    static Insets insets;
    static final String DEFAULT_STYLE = "Callibri";
    static final int DEFAULT_SIZE = 15;
    static final int DEFAULT_FONT = 0;
    static String current_style;
    static int current_size;
    static int current_font;
    private static JPanel DEFAULT_PANEL;
    
    static {
        tf = new TextField[4];
        ta = new TextArea("Address Here.....", 2, 4);
        p  = new JPanel(new GridBagLayout());
        welcomePanel = new JPanel(new GridBagLayout());
        genderPanel = new JPanel(new GridLayout(1, 2, 1, 1));
        buttonPanel = new JPanel(new GridLayout(1, 3, 1, 1));
        jcbDepts = new JComboBox();
        jcbYears = new JComboBox();
        jcbAttendance = new JComboBox();
        jcbAdmissionType = new JComboBox();
        insets = new Insets(0, 0, 0, 0);
    }

    private GraphicsLoader(String text) {
        GraphicsLoader.init(text);
    }

    private GraphicsLoader() {}

    public static void init(String text) {
        frame = new JFrame(text);
        frame.setIconImage(((ImageIcon)DataModel.getIcon("/information.png")).getImage());
        DataModel.readDataFromTheModel();
        
        GraphicsLoader.creatingMenuBar();
        GraphicsLoader.createLabels();
        GraphicsLoader.createTextFields();
        GraphicsLoader.createComboBoxes();
        GraphicsLoader.createRadioButtons();
        GraphicsLoader.createButtons();
        
        current_style = "Callibri";
        current_size = 15;
        current_font = 0;
        DEFAULT_PANEL = welcomePanel;
        
        GraphicsLoader.setFontSizeStyle(current_style, current_font, current_size);
        GraphicsLoader.setColorsToComponents();
        GraphicsLoader.addToPanel();
        GraphicsLoader.customizeScreen();
    }

    private static void creatingMenuBar() {
        mb = new JMenuBar();
        homeMenu = new JMenu();
        fileMenu = new JMenu("File");
        detailsMenu = new JMenu("Select");
        deleteMenu = new JMenu("Delete");
        analysisMenu = new JMenu("Analysis");
        viewMenu = new JMenu("View");
        helpMenu = new JMenu("Help");
        homeMenu.setIcon(DataModel.getIcon("/home_icon.png"));
        homeMenu.addMenuListener(new MenuListener(){

            @Override
            public void menuSelected(MenuEvent e) {
                DEFAULT_PANEL = GraphicsLoader.welcomePanel;
                GraphicsLoader.customizeScreen();
            }

            @Override
            public void menuDeselected(MenuEvent e) {
            }

            @Override
            public void menuCanceled(MenuEvent e) {
            }
        });
        
        mb.add(homeMenu);
        mb.add(fileMenu);
        mb.add(detailsMenu);
        mb.add(deleteMenu);
        mb.add(analysisMenu);
        mb.add(viewMenu);
        mb.add(helpMenu);
        
        GraphicsLoader.createFileMenu();
        GraphicsLoader.createDetailsMenu();
        GraphicsLoader.createDeleteMenu();
        GraphicsLoader.createAnalysisMenu();
        GraphicsLoader.createViewMenu();
        GraphicsLoader.createHelpMenu();
    }

    private static void createFileMenu() {
        jmiNew = new JMenuItem("New");
        jmiNew.setAccelerator(KeyStroke.getKeyStroke('N', 2, false));
        jmiEdit = new JMenuItem("Edit Detail");
        jmiEdit.setAccelerator(KeyStroke.getKeyStroke('E', 2, false));
        jmiCopy = new JMenuItem("Copy File");
        jmiCopy.setAccelerator(KeyStroke.getKeyStroke('C', 2, false));
        jmiExit = new JMenuItem("Exit");
        jmiExit.setAccelerator(KeyStroke.getKeyStroke('Q', 2, false));
        
        fileMenu.add(jmiNew);
        fileMenu.add(jmiEdit);
        fileMenu.add(jmiCopy);
        fileMenu.add(jmiExit);
        
        jmiNew.addActionListener(e -> {
            GraphicsLoader.reset("");
            DEFAULT_PANEL = p;
            btnEdit.setEnabled(false);
            btnSubmit.setEnabled(true);
        });
        
        jmiEdit.addActionListener(e -> {
            GraphicsLoader.reset("");
            DEFAULT_PANEL = p;
            btnSubmit.setEnabled(false);
            btnEdit.setEnabled(true);
        });
        
        jmiCopy.addActionListener(e -> {
            GraphicsLoader.copySelected();
        });
        
        jmiExit.addActionListener(e -> {
            System.exit(0);
        });
        
        fileMenu.insertSeparator(3);
    }

    private static void createDetailsMenu() {
        StringTokenizer deptTok = new StringTokenizer(DataModel.getDeptsList(), ",");
        
        smDetailsDepts = new JMenu[8];
        smDetailsYears = new JMenu[5];
        jmiSections    = new JMenuItem[4];
        
        for (int i = 0; i < 8; ++i) {
            String deptName = deptTok.nextToken();
            GraphicsLoader.smDetailsDepts[i] = new JMenu(deptName);
            smDetailsDepts[i].setIcon(DataModel.getIcon("/dept_icon-" + i + ".png"));
            detailsMenu.add(smDetailsDepts[i]);
            StringTokenizer yrTok = new StringTokenizer(DataModel.getYearsList(), ",");
            
            for (int j = 0; j < 5 && i != 0; ++j) {
                String yrName = yrTok.nextToken();
                GraphicsLoader.smDetailsYears[j] = new JMenu(yrName);
                smDetailsYears[j].setIcon(DataModel.getIcon("/year_icon-" + j + ".png"));
                smDetailsDepts[i].add(smDetailsYears[j]);
                StringTokenizer secTok = new StringTokenizer(DataModel.getSectionList(), ",");
                
                for (int k = 0; k < 4 && j != 0; ++k) {
                    String secName = secTok.nextToken();
                    GraphicsLoader.jmiSections[k] = new JMenuItem(secName, DataModel.getIcon("/sec_icon_" + k + ".png"));
                    smDetailsYears[j].add(jmiSections[k]);
                    int temp = k;
                    if (k <= 0) continue;
                    
                    jmiSections[k].addActionListener(e -> {
                        if (temp == 1) {
                            DEFAULT_PANEL = p;
                            GraphicsLoader.customizeScreen();
                        }
                        GraphicsLoader.getDetailsMenuSelected("std_data/" + deptName + "/" + yrName + "/" + secName);
                    });
                }
                if (j != 0 && (i == 1 || i == 2) ) {
                    jmiSections[3].setEnabled(false);
                }
                smDetailsYears[j].insertSeparator(1);
            }
            smDetailsDepts[i].setFont(new Font(DEFAULT_STYLE, DEFAULT_FONT, DEFAULT_SIZE));
            smDetailsDepts[i].insertSeparator(1);
        }
        detailsMenu.insertSeparator(1);
    }

    private static void createDeleteMenu() {
        StringTokenizer deptTok = new StringTokenizer(DataModel.getDeptsList(), ",");

        smDeleteDepts = new JMenu[8];
        smDeleteYears = new JMenu[5];
        jmiSections = new JMenuItem[4];
        
        for (int i = 0; i < 8; ++i) {
            String deptName = deptTok.nextToken();
            GraphicsLoader.smDeleteDepts[i] = new JMenu(deptName);
            smDeleteDepts[i].setIcon(DataModel.getIcon("/dept_icon-" + i + ".png"));
            deleteMenu.add(smDeleteDepts[i]);
            StringTokenizer yrTok = new StringTokenizer(DataModel.getYearsList(), ",");
            
            for (int j = 0; j < 5 && i != 0; ++j) {
                String yrName = yrTok.nextToken();
                GraphicsLoader.smDeleteYears[j] = new JMenu(yrName);
                smDeleteYears[j].setIcon(DataModel.getIcon("/year_icon-" + j + ".png"));
                smDeleteDepts[i].add(smDeleteYears[j]);
                StringTokenizer secTok = new StringTokenizer(DataModel.getSectionList(), ",");
                
                for (int k = 0; k < 4 && j != 0; ++k) {
                    String secName = secTok.nextToken();
                    GraphicsLoader.jmiSections[k] = new JMenuItem(secName, DataModel.getIcon("/sec_icon_" + k + ".png"));
                    smDeleteYears[j].add(jmiSections[k]);
                    if (k <= 0) continue;
                    jmiSections[k].addActionListener(e -> {
                        GraphicsLoader.deleteDetailsMenuSelected("std_data/" + deptName + "/" + yrName + "/" + secName);
                    }
                    );
                }
                if (j != 0 && (i == 1 || i == 2)) {
                    jmiSections[3].setEnabled(false);
                }
                smDeleteYears[j].insertSeparator(1);
            }
            smDeleteDepts[i].insertSeparator(1);
        }
        deleteMenu.insertSeparator(1);
    }

    private static void createAnalysisMenu() {
        StringTokenizer DeptTok = new StringTokenizer(DataModel.getDeptsList(), ",");
        
        smAnalysisDepts = new JMenu[8];
        smAnalysisYears = new JMenu[5];
        jmiSections = new JMenuItem[4];
        
        for (int i = 0; i < 8; ++i) {
            String deptName = DeptTok.nextToken();
            GraphicsLoader.smAnalysisDepts[i] = new JMenu(deptName);
            smAnalysisDepts[i].setIcon(DataModel.getIcon("/dept_icon-" + i + ".png"));
            analysisMenu.add(smAnalysisDepts[i]);
            StringTokenizer YrTok = new StringTokenizer(DataModel.getYearsList(), ",");
            
            for (int j = 0; j < 5 && i != 0; ++j) {
                String yrName = YrTok.nextToken();
                GraphicsLoader.smAnalysisYears[j] = new JMenu(yrName);
                smAnalysisYears[j].setIcon(DataModel.getIcon("/year_icon-" + j + ".png"));
                smAnalysisDepts[i].add(smAnalysisYears[j]);
                StringTokenizer SecTok = new StringTokenizer(DataModel.getSectionList(), ",");
                SecTok.nextToken();
                SecTok.nextToken();
                
                for (int k = 0; k < 2 && j != 0; ++k) {
                    String secName = SecTok.nextToken();
                    GraphicsLoader.jmiSections[k] = new JMenuItem(secName, DataModel.getIcon("/sec_icon_" + k + ".png"));
                    smAnalysisYears[j].add(jmiSections[k]);
                    jmiSections[k].addActionListener(e -> {
                        Message.error(frame, "Function Not Supported Yet");
                    });
                }
                if (j == 0 || i != 1 && i != 2) continue;
                jmiSections[1].setEnabled(false);
            }
            smAnalysisDepts[i].insertSeparator(1);
        }
        analysisMenu.insertSeparator(1);
    }

    private static void createViewMenu() {
        int i;
        jmiSize  = new JMenuItem[6];
        jmiFont  = new JMenuItem[3];
        jmiStyle = new JMenuItem[18];
        smSize   = new JMenu("Size");
        smStyle  = new JMenu("Style");
        smFont   = new JMenu("Font");
        viewMenu.add(smSize);
        viewMenu.add(smStyle);
        viewMenu.add(smFont);
        
        StringTokenizer x = new StringTokenizer(DataModel.getFontStylesList(), ",");
        StringTokenizer y = new StringTokenizer(DataModel.getFontNameList(), ",");
        x.nextToken();
        y.nextToken();
        for (i = 0; i < 18; ++i) {
            String StyleSt = x.nextToken();
            GraphicsLoader.jmiStyle[i] = new JMenuItem(StyleSt);
            jmiStyle[i].addActionListener(e -> {
                current_style = e.getActionCommand();
                GraphicsLoader.setFontSizeStyle(current_style, current_font, current_size);
            });
            smStyle.add(jmiStyle[i]);
            jmiStyle[i].setFont(new Font(StyleSt, 0, 15));
        }
        for (i = 4; i < 10; ++i) {
            GraphicsLoader.jmiSize[i - 4] = new JMenuItem("" + i * 5 + "");
            jmiSize[i - 4].addActionListener(e -> {
                current_size = Integer.parseInt(e.getActionCommand());
                GraphicsLoader.setFontSizeStyle(current_style, current_font, current_size);
            });
            smSize.add(jmiSize[i - 4]);
        }
        for (i = 0; i < 3; ++i) {
            int font = i;
            GraphicsLoader.jmiFont[i] = new JMenuItem(y.nextToken());
            smFont.add(jmiFont[i]);
            jmiFont[i].addActionListener(e -> {
                current_font = font;
                GraphicsLoader.setFontSizeStyle(current_style, current_font, current_size);
            });
        }
        jmiFont[0].setFont(new Font(DEFAULT_STYLE, 0, DEFAULT_SIZE));
        jmiFont[1].setFont(new Font(DEFAULT_STYLE, 1, DEFAULT_SIZE));
        jmiFont[2].setFont(new Font(DEFAULT_STYLE, 2, DEFAULT_SIZE));
    }

    private static void createHelpMenu() {
        jmiAbout = new JMenuItem("About", DataModel.getIcon("/info.png"));
        jmiAbout.addActionListener(e -> {
            About aboutMnuItmFrame = new About("About SMS");
        });
        helpMenu.add(jmiAbout);
    }

    private static void createLabels() {
        jlId = new JLabel("ID:");
        jlName = new JLabel("Name:");
        jlGender = new JLabel("Gender:");
        jlDepartment = new JLabel("Department:");
        jlYear = new JLabel("Year:");
        jlCGPA = new JLabel("CGPA:");
        jlAttendance = new JLabel("Attendance(%):");
        jlAdmission = new JLabel("Admission:");
        jlAddress = new JLabel("Address:");
        jlMobile = new JLabel("Mobile No.:");
    }

    private static void createTextFields() {
        for (int i = 0; i < 4; ++i) {
            GraphicsLoader.tf[i] = new TextField("--none--", 20);
            tf[i].addActionListener(e -> {});
        }
    }

    private static void createComboBoxes() {
        int i;
        StringTokenizer tok = new StringTokenizer(DataModel.getDeptsList(), ",");
        tok.nextToken();
        jcbDepts.addItem("--select--");
        for (i = 0; i < 7; ++i) {
            jcbDepts.addItem(tok.nextToken());
        }
        tok = new StringTokenizer(DataModel.getYearsList(), ",");
        tok.nextToken();
        jcbYears.addItem("--select--");
        for (i = 0; i < 4; ++i) {
            jcbYears.addItem(tok.nextToken());
        }
        tok = new StringTokenizer(DataModel.getAttendancelist(), ",");
        tok.nextToken();
        jcbAttendance.addItem("--select--");
        while (tok.hasMoreTokens()) {
            jcbAttendance.addItem(tok.nextToken());
        }
        tok = new StringTokenizer(DataModel.getAdmissionTypeList(), ",");
        tok.nextToken();
        jcbAdmissionType.addItem("--select--");
        for (i = 0; i < 3; ++i) {
            jcbAdmissionType.addItem(tok.nextToken());
        }
        
        jcbDepts.addActionListener(e -> {});
        jcbYears.addActionListener(e -> {});
        jcbAttendance.addActionListener(e -> {});
        jcbAdmissionType.addActionListener(e -> {});
        
    }

    private static void createRadioButtons() {
        jrbFemale = new JRadioButton("Female");
        jrbMale = new JRadioButton("Male");
        jrbFemale.addActionListener(e -> {
            jrbMale.setSelected(false);
        });
        jrbMale.addActionListener(e -> {
            jrbFemale.setSelected(false);
        });
    }

    private static void createButtons() {
        btnSubmit = new JButton("Submit");
        btnReset  = new JButton("Reset");
        btnEdit   = new JButton("Edit");
        
        btnEdit.setEnabled(false);
        btnSubmit.setEnabled(false);
        
        btnSubmit.addActionListener(e -> {
            GraphicsLoader.submit();
        });
        btnReset.addActionListener(e -> {
            if (!btnSubmit.isEnabled() && !btnEdit.isEnabled()) {
                GraphicsLoader.reset("--none--");
            } else {
                GraphicsLoader.reset("");
            }
        });
        btnEdit.addActionListener(e -> {
            GraphicsLoader.editSelected();
        });
        
        buttonPanel.add(btnSubmit);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnReset);
    }

    private static void addToPanel() {
        genderPanel.add(jrbMale);
        genderPanel.add(jrbFemale);
        GraphicsLoader.addComponent(p, jlId, 0, 1, 1, 1, 10, 1);
        GraphicsLoader.addComponent(p, tf[0], 1, 1, 1, 1, 10, 1);
        GraphicsLoader.addComponent(p, jlName, 0, 2, 1, 1, 10, 1);
        GraphicsLoader.addComponent(p, tf[1], 1, 2, 1, 1, 10, 1);
        GraphicsLoader.addComponent(p, jlGender, 2, 1, 1, 1, 10, 1);
        GraphicsLoader.addComponent(p, genderPanel, 2, 2, 1, 1, 10, 1);
        GraphicsLoader.addComponent(p, jlDepartment, 0, 3, 1, 1, 10, 1);
        GraphicsLoader.addComponent(p, jcbDepts, 1, 3, 1, 1, 10, 1);
        GraphicsLoader.addComponent(p, jlYear, 0, 4, 1, 1, 10, 1);
        GraphicsLoader.addComponent(p, jcbYears, 1, 4, 1, 1, 10, 1);
        GraphicsLoader.addComponent(p, jlCGPA, 0, 6, 1, 1, 10, 1);
        GraphicsLoader.addComponent(p, tf[2], 1, 6, 1, 1, 10, 1);
        GraphicsLoader.addComponent(p, jlAttendance, 2, 3, 1, 1, 10, 1);
        GraphicsLoader.addComponent(p, jcbAttendance, 2, 4, 1, 1, 10, 1);
        GraphicsLoader.addComponent(p, jlAddress, 0, 7, 1, 1, 10, 1);
        GraphicsLoader.addComponent(p, ta, 1, 7, 2, 1, 10, 1);
        GraphicsLoader.addComponent(p, jlAdmission, 0, 5, 1, 1, 10, 1);
        GraphicsLoader.addComponent(p, jcbAdmissionType, 1, 5, 1, 1, 10, 1);
        GraphicsLoader.addComponent(p, jlMobile, 2, 5, 1, 1, 10, 1);
        GraphicsLoader.addComponent(p, tf[3], 2, 6, 1, 1, 10, 1);
        GraphicsLoader.addComponent(p, buttonPanel, 0, 8, 3, 1, 10, 1);
        GraphicsLoader.addComponent(welcomePanel, new JLabel(DataModel.getIcon("/welcome_page_image.png")), 0, 1, 3, 3, 10, 1);
    }

    private static void addComponent(Container cont, Component comp, int x, int y, int w, int h, int anchor, int fill) {
        GridBagConstraints gbc = new GridBagConstraints(x, y, w, h, 1.0, 1.0, anchor, fill, insets, 0, 0);
        cont.add(comp, gbc);
    }

    public static void customizeScreen() {
        frame.setVisible(true);
        GraphicsLoader.addToScreen(DEFAULT_PANEL);
        frame.setDefaultCloseOperation(3);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
    }

    private static void addToScreen(JPanel screen) {
        frame.getContentPane().removeAll();
        GraphicsLoader.addComponent(screen, mb, 0, 0, 3, 1, 10, 1);
        frame.add(screen);
    }

    public static void setFontSizeStyle(String Style, int FontSize, int Size1) {
        int i;
        int Size3 = Size1 + 10;
        int Size2 = Size1 + 5;
        int FontX = 3;
        if (FontSize == 0) {
            FontX = FontSize;
        }
        fileMenu.setFont(new Font(Style, FontSize, Size2));
        detailsMenu.setFont(new Font(Style, FontSize, Size2));
        deleteMenu.setFont(new Font(Style, FontSize, Size2));
        analysisMenu.setFont(new Font(Style, FontSize, Size2));
        viewMenu.setFont(new Font(Style, FontSize, Size2));
        helpMenu.setFont(new Font(Style, FontSize, Size2));
        smSize.setFont(new Font(Style, FontSize, Size1));
        smStyle.setFont(new Font(Style, FontSize, Size1));
        smFont.setFont(new Font(Style, FontSize, Size1));
        jmiNew.setFont(new Font(Style, FontSize, Size1));
        jmiEdit.setFont(new Font(Style, FontSize, Size1));
        jmiCopy.setFont(new Font(Style, FontSize, Size1));
        jmiExit.setFont(new Font(Style, FontSize, Size1));
        jmiAbout.setFont(new Font(Style, FontSize, Size1));
        for (i = 0; i < 4; ++i) {
            tf[i].setFont(new Font(Style, FontX, Size3));
        }
        ta.setFont(new Font(Style, FontX, Size3));
        for (i = 0; i < 8; ++i) {
            smDetailsDepts[i].setFont(new Font(Style, FontSize, Size1));
            smDeleteDepts[i].setFont(new Font(Style, FontSize, Size1));
            smAnalysisDepts[i].setFont(new Font(Style, FontSize, Size1));
        }
        for (i = 0; i < 5; ++i) {
            smDetailsYears[i].setFont(new Font(Style, FontSize, Size1));
            smDeleteYears[i].setFont(new Font(Style, FontSize, Size1));
            smAnalysisYears[i].setFont(new Font(Style, FontSize, Size1));
        }
        for (i = 0; i < 6; ++i) {
            jmiSize[i].setFont(new Font(Style, FontSize, Size1));
        }
        jlId.setFont(new Font(Style, FontX, Size3));
        jlName.setFont(new Font(Style, FontX, Size3));
        jlGender.setFont(new Font(Style, FontX, Size3));
        jlDepartment.setFont(new Font(Style, FontX, Size3));
        jlYear.setFont(new Font(Style, FontX, Size3));
        jlCGPA.setFont(new Font(Style, FontX, Size3));
        jlAttendance.setFont(new Font(Style, FontX, Size3));
        jlAdmission.setFont(new Font(Style, FontX, Size3));
        jlAddress.setFont(new Font(Style, FontX, Size3));
        jlMobile.setFont(new Font(Style, FontX, Size3));
        jrbFemale.setFont(new Font(Style, 1, Size3));
        jrbMale.setFont(new Font(Style, 1, Size3));
        jcbDepts.setFont(new Font(Style, FontX, Size3));
        jcbYears.setFont(new Font(Style, FontX, Size3));
        jcbAttendance.setFont(new Font(Style, FontX, Size3));
        jcbAdmissionType.setFont(new Font(Style, FontX, Size3));
        btnSubmit.setFont(new Font(Style, FontX, Size3));
        btnReset.setFont(new Font(Style, FontX, Size3));
        btnEdit.setFont(new Font(Style, FontX, Size3));
        GraphicsLoader.customizeScreen();
    }

    private static void setColorsToComponents() {
        mb.setBackground(Color.PINK);
        p.setBackground(Color.WHITE);
        jlId.setForeground(Color.MAGENTA);
        jlName.setForeground(Color.MAGENTA);
        jlGender.setForeground(Color.MAGENTA);
        jlDepartment.setForeground(Color.MAGENTA);
        jlYear.setForeground(Color.MAGENTA);
        jlCGPA.setForeground(Color.MAGENTA);
        jlAttendance.setForeground(Color.MAGENTA);
        jlAdmission.setForeground(Color.MAGENTA);
        jlAddress.setForeground(Color.MAGENTA);
        jlMobile.setForeground(Color.MAGENTA);
    }

    private static void getDetailsMenuSelected(String path) {
        block10 : {
            try {
                String fileName = path.replaceAll("Student", "");
                System.out.println("Get File Path :" + fileName);
                if (path.endsWith("Student")) {
                    AllStudentDetails asd = new AllStudentDetails();
                    Student s = new Student();
                    s = asd.modifyFile(3, s, fileName);
                    if (s.result == 0) {
                        Message.error(frame, "Unable to select given section");
                    } else {
                        for (int i = 0; i < 4; ++i) {
                            tf[i].getText();
                        }
                        tf[0].setText(s.id);
                        tf[1].setText(s.name);
                        jcbDepts.setSelectedItem(s.dept);
                        jcbYears.setSelectedItem(s.stdyear);
                        tf[2].setText("" + s.cgpa + "");
                        jcbAttendance.setSelectedItem(s.stdatndnce);
                        jcbAdmissionType.setSelectedItem(s.stdAdm);
                        ta.setText(s.address);
                        tf[3].setText(s.phone);
                        if (s.gender.equals("Male")) {
                            jrbMale.setSelected(true);
                            jrbFemale.setSelected(false);
                        } else {
                            jrbMale.setSelected(false);
                            jrbFemale.setSelected(true);
                        }
                    }
                    GraphicsLoader.setEnableDisable(false);
                    break block10;
                }
                try {
                    AllStudentDetails asd = new AllStudentDetails(fileName + ".csv", true);
                }
                catch (Exception em) {
                    Message.error(frame, "Exception :" + em.toString());
                }
                GraphicsLoader.reset("--none--");
                GraphicsLoader.setEnableDisable(false);
            }
            catch (Exception d) {
                Message.error(frame, "Exception :" + d.toString());
            }
        }
    }

    private static void deleteDetailsMenuSelected(String path) {
        block8 : {
            try {
                String fileName = path.replaceAll("Student", "");
                System.out.println("Delete File Path :" + fileName);
                if (path.endsWith("Student")) {
                    AllStudentDetails asd = new AllStudentDetails();
                    Student s = asd.modifyFile(1, new Student(), fileName);
                    if (s.result == 0) {
                        Message.error(frame, "No record is deleted");
                    } else if (s.result == 1) {
                        Message.acknowledge(frame, "Record Deleted");
                    }
                    GraphicsLoader.setEnableDisable(false);
                    break block8;
                }
                try {
                    AllStudentDetails asd = new AllStudentDetails(fileName + ".csv", false);
                }
                catch (Exception em) {
                    Message.error(frame, "Unable to delete given section");
                }
                GraphicsLoader.reset("--none--");
                GraphicsLoader.setEnableDisable(false);
            }
            catch (Exception d) {
                Message.error(frame, "Exception : " + d.toString());
            }
        }
    }

    private static void copySelected() {
        try {
            CopyFileToDB dBConnection = new CopyFileToDB("Select file to copy.....");
        }
        catch (Exception e) {
            Message.error(frame, "Exception :" + e.toString());
        }
    }

    private static void editSelected() {
        try {
            AllStudentDetails asd = new AllStudentDetails();
            asd.assignValues(tf[0].getText(), tf[1].getText(), (String)jcbDepts.getSelectedItem(), jrbMale.isEnabled() ? "Male" : "Female", (String)jcbYears.getSelectedItem(), tf[2].getText(), (String)jcbAttendance.getSelectedItem(), (String)jcbAdmissionType.getSelectedItem(), tf[3].getText(), ta.getText());
            Student s = asd.modifyFile(2, new Student(), "");
            if (s.result == 1) {
                Message.acknowledge(frame, "Record " + tf[0].getText() + " has been edited");
            } else if (s.result == 0) {
                Message.error(frame, tf[0].getText() + " record doesn't exist");
            }
        } catch (Exception ex) {
            Message.error(frame, "Exception : " + ex.toString());
        }
        GraphicsLoader.reset("--none--");
        GraphicsLoader.setEnableDisable(false);
    }

    private static void reset(String text) {
        for (int i = 0; i < 4; ++i) {
            tf[i].getText();
            tf[i].setText(text);
        }
        ta.getText();
        ta.setText(text);
        jrbFemale.setSelected(false);
        jrbMale.setSelected(false);
        jcbDepts.setSelectedIndex(0);
        jcbYears.setSelectedIndex(0);
        jcbAttendance.setSelectedIndex(0);
        jcbAdmissionType.setSelectedIndex(0);
        GraphicsLoader.setEnableDisable(text.equals(""));
        btnSubmit.setEnabled(text.equals("") && btnSubmit.isEnabled());
        btnEdit.setEnabled(text.equals("") && btnEdit.isEnabled());
        DEFAULT_PANEL = text.equals("--none--") ? welcomePanel : p;
        GraphicsLoader.customizeScreen();
    }

    private static void submit() {
        Student s = new Student(  tf[0].getText(), tf[1].getText(), (String)jcbDepts.getSelectedItem(), 
                jrbMale.isEnabled() ? "Male" : "Female", (String)jcbYears.getSelectedItem(), 
                Double.parseDouble("0" + tf[2].getText()), (String)jcbAttendance.getSelectedItem(),
                (String)jcbAdmissionType.getSelectedItem(), tf[3].getText(), ta.getText(), true);
        if (s.result == 0) {
            GraphicsLoader.reset("--none--");
            GraphicsLoader.setEnableDisable(false);
            Message.acknowledge(frame, "Student added");
        } else {
            Message.error(frame, "Failed to Add Student to the Database");
        }
    }

    public static void setEnableDisable(boolean value) {
        for (int i = 0; i < 4; ++i) {
            tf[i].setEditable(value);
        }
        jcbDepts.setEnabled(value);
        jcbYears.setEnabled(value);
        jcbAttendance.setEnabled(value);
        jcbAdmissionType.setEnabled(value);
        ta.setEditable(value);
        frame.pack();
    }   
}
