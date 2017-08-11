import com.toedter.calendar.JDateChooser;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
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


        finImg = ImageIO.read(new File("/Users/shafihaque/IdeaProjects/DayAndNight/land_shallow_topo_2048.jpg/")); // Directory of the image.
        BufferedImage newimg2 = ImageIO.read(new File("/Users/shafihaque/IdeaProjects/DayAndNight/land_shallow_topo_2048.jpg/"));

        filter1 = new DayNightFilter1(widthOfImage, heightOfImaage, 0); // Filter is used for the equation day and night.


        JLabel thumb = new JLabel();
        thumb.setIcon(new ImageIcon(newimg2)); //Thumb is the jlabel where the map is set onto.


        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 12)); // This 1 by 12 grid layout. 12 elements such as button below.


        JTextField hour = new JTextField(); // Textfield is used for hour, minute and seconds.
        JTextField minute = new JTextField();
        JTextField seconds = new JTextField();


        JDateChooser selectDate = new JDateChooser(); // All the buttons and checkboxes are created here.
        JButton selectButton = new JButton("Enter");
        selectButton.setBackground(Color.GREEN);
        selectButton.setOpaque(true);
        JButton currentButton = new JButton("Current");
        JCheckBox refresh = new JCheckBox("Update Automatically");
        JCheckBox animateRefresh = new JCheckBox("Animate Automatically");
        JButton animate = new JButton("Animate");
        changeDate listener = new changeDate(selectDate, thumb, hour, minute, seconds, selectDate, widthOfImage, heightOfImaage);


        selectButton.addActionListener(listener); // The listener for the buttons.
        currentButton.addActionListener(listener);
        animate.addActionListener(listener);


        updateAuto listener2 = new updateAuto(refresh, animateRefresh); // The listener for the checkbox. The animate automatically and update automatically listener are here.
        refresh.addActionListener(listener2);
        animateRefresh.addActionListener(listener2);

        panel.add(selectDate); // The calendar
        panel.add(hour);
        panel.add(new JLabel("Hour")); //Everything is added to the Jframe panel here.
        panel.add(minute);
        panel.add(new JLabel("Minute"));
        panel.add(seconds);
        panel.add(new JLabel("Seconds"));
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

        while(true) { // The loop used to detect if the checkboxes are clicked.

            if (changeDate.checkAuto) { // Checks the boolean value for the update automatically checkbox.

                currentButton.doClick(); // Performs a click action for the current button.
                try {
                    Thread.sleep(1000);
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






