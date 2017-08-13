import com.toedter.calendar.JDateChooser;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.swing.ImageIcon;
import java.util.Calendar;
import java.util.GregorianCalendar;



class DayAndNight {

    static BufferedImage finImg; // finImg and filter 1 are initialized in the main.
    static DayNightFilter1 filter1;

    public static void main(String[] args) throws IOException {
        int widthOfImage = 2048; // Width and height of the image, it can be changed here if a different image is used.
        int heightOfImaage = 1024;


        JFrame frame = new JFrame("Day and Night of Earth"); // Title
        Container pane = frame.getContentPane();

        try { // This makes sure that the image is unchanged.
            finImg = ImageIO.read(new FileInputStream("resources/land_shallow_topo_2048.jpg/"));
        } catch (IOException err) {
            err.printStackTrace();

        }

        filter1 = new DayNightFilter1(widthOfImage, heightOfImaage, 0); // Filter is used for the equation day and night.


        JLabel thumb = new JLabel();
        thumb.setIcon(new ImageIcon(finImg)); //Thumb is the jlabel where the map is set onto.


        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 11)); // This 1 by 12 grid layout. 12 elements such as button below.


        JTextField minute = new JTextField();
        JTextField seconds = new JTextField();
        JTextField hour = new JTextField();


        JDateChooser selectDate = new JDateChooser(); // All the buttons and checkboxes are created here.
        JButton selectButton = new JButton("Enter");
        selectButton.setBackground(Color.GREEN);
        selectButton.setOpaque(true);
        JButton currentButton = new JButton("Current");
        JCheckBox refresh = new JCheckBox("Update Automatically");
        JCheckBox animateRefresh = new JCheckBox("Animate Automatically");
        JButton animate = new JButton("Animate");

        String[] hours={"0","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24"};
        JComboBox hoursCombo = new JComboBox(hours);

        String[] minutes= new String[61];
        for (int i=0;i<=60;i++){
            minutes[i]=Integer.toString(i);
        }
        JComboBox minutesCombo = new JComboBox(minutes);

        String[] second= new String[61];
        for (int i=0;i<=60;i++){
            second[i]=Integer.toString(i);
        }
        JComboBox secondsCombo = new JComboBox(second);

        String[] ampm = {"am","pm"};
        JComboBox ampmCombo = new JComboBox(ampm);

        changeDate listener = new changeDate(selectDate, thumb, hour, minute, seconds, selectDate, widthOfImage, heightOfImaage, hoursCombo, minutesCombo, secondsCombo, ampmCombo);


        selectButton.addActionListener(listener); // The listener for the buttons.
        currentButton.addActionListener(listener);
        animate.addActionListener(listener);


        updateAuto listener2 = new updateAuto(refresh, animateRefresh); // The listener for the checkbox. The animate automatically and update automatically listener are here.
        refresh.addActionListener(listener2);
        animateRefresh.addActionListener(listener2);




        panel.add(selectDate); // The calendar
        panel.add(new JLabel("am/pm", SwingConstants.RIGHT));
        panel.add(ampmCombo);
        panel.add(new JLabel("Hour",SwingConstants.RIGHT)); //Everything is added to the Jframe panel here.
        panel.add(hoursCombo);
        panel.add(new JLabel("Minute", SwingConstants.RIGHT));
        panel.add(minutesCombo);
        panel.add(new JLabel("Seconds",SwingConstants.RIGHT));
        panel.add(secondsCombo);
        panel.add(selectButton);
        panel.add(animate);
        panel.add(animateRefresh);
        panel.add(currentButton);
        panel.add(refresh);
        pane.add(panel, BorderLayout.SOUTH); // This container pane has all the buttons, checkboxes and Text field. It's at the south of the frame.
        pane.add(thumb, BorderLayout.CENTER); // This is the image, which is set to be on the center.


        frame.pack(); // Packs everything.
        frame.show();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // The program stops running when the window is closed.
        currentButton.doClick();

        while(true) { // The loop used to detect if the checkboxes are clicked.

            if (changeDate.checkAuto) { // Checks the boolean value for the update automatically checkbox.

                currentButton.doClick(); // Performs a click action for the current button.
                try {
                    Thread.sleep(1000); // To refresh for every second of the program.
                } catch(InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
            if (changeDate.checkAnimate) { // Checks the boolean value for the animate automatically checkbox.
                animate.doClick(); // // Performs a click action for the animate button.
            }
            else
            {
                System.out.println("check auto is " + changeDate.checkAuto);

            }

        }


    }

}






