import com.toedter.calendar.JDateChooser;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

class changeDate implements ActionListener { // This action listener is used for all of the JButtons and JTextfields.
    private JDateChooser userDate1; // Declares all of the variables.
    private JLabel thumb1;
    private BufferedImage newimg21;
    JTextField hour1;
    JTextField minute1;
    JTextField seconds1;
    JButton currentButton1;
    public static boolean checkAuto = false;
    public static boolean checkAnimate = false;
    private int clicks;
    JDateChooser selectDate1;
    int widthOfImage1;
    int heightOfImage1;
    JComboBox hoursCombo1;
    JComboBox minutesCombo1;
    JComboBox secondsCombo1;
    JComboBox ampmCombo1;

    changeDate(JDateChooser userDate, JLabel thumb, JTextField hour, JTextField minute, JTextField seconds, JDateChooser selectDate, int widthOfImage, int heightOfImage, JComboBox hoursCombo, JComboBox minutesCombo, JComboBox secondsCombo, JComboBox ampmCombo) {
        userDate1 = userDate; // Initializes all of the variables base on the listener.
        thumb1 = thumb;
        hour1 = hour;
        minute1 = minute;
        seconds1 = seconds;
        selectDate1 = selectDate;
        widthOfImage1 = widthOfImage;
        heightOfImage1 = heightOfImage;
        hoursCombo1 = hoursCombo;
        minutesCombo1 = minutesCombo;
        secondsCombo1 = secondsCombo;
        ampmCombo1 = ampmCombo;
    }
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Enter")) { // If the user enters the "Enter" jButton. This is a action listener after the user inputs the date and time.

            try { // This makes sure that the image is unchanged.
                newimg21 = ImageIO.read(new FileInputStream("resources/land_shallow_topo_2048.jpg/"));
            } catch (IOException err) {
                err.printStackTrace();
            }
            DayNightFilter1 filter1 = DayAndNight.filter1; // Initializes the filter.
            Calendar temp = userDate1.getCalendar(); // Gets the calendar from the JDateChooser, which how the user inputs the date.

            JComboBox ampm1 = hoursCombo1;
            String ampm1S= (String)ampm1.getSelectedItem();


            JComboBox hr1 = hoursCombo1;
            String hr1S= (String)hr1.getSelectedItem();
            int hr1I=Integer.parseInt(hr1S);
            if (ampm1S.equals("pm")){
                hr1I=hr1I+12;
            }

            JComboBox min1 = minutesCombo1;
            String min1S= (String)min1.getSelectedItem();
            int minI=Integer.parseInt(min1S);

            JComboBox sec1 = secondsCombo1;
            String sec1S= (String)sec1.getSelectedItem();
            int secI=Integer.parseInt(sec1S);


            Calendar temp2 = new GregorianCalendar(temp.get(Calendar.YEAR), temp.get(Calendar.MONTH), temp.get(Calendar.DATE), hr1I, minI, secI);
            filter1.updateWidthTable(temp2); // This sends the calendar to the equation where it gets the y value for the curve.
            for (int i = 0; i < widthOfImage1; i++) { // First loop for all of the width value.
                for (int j = filter1.wtab_[i]; j < heightOfImage1; j++) { // Second loop starts at the y value for the curve. Ends at the height of the image, which is bottom of the image.
                    int rgb = newimg21.getRGB(i, j); // Gets the rgb for the current pixel.
                    int rgb2 = filteringColor.filterRGB(i, j, rgb); // This gets the rgb value for the lower opacity of the current pixel.
                    newimg21.setRGB(i, j, rgb2); // Sets the rgb of the image as the lower opacity which is rgb2.
                }
            }

            thumb1.setIcon(new ImageIcon(newimg21)); // Sets the new image on the JLabel.
        }
        if (e.getActionCommand().equals("Current")) { // If the user enters the "Current" jButton. This shows the current date and time.
            try { // This makes sure that the image is unchanged.
                newimg21 = ImageIO.read(new FileInputStream("resources/land_shallow_topo_2048.jpg/"));
            } catch (IOException err) {
                err.printStackTrace();
            }
            DayNightFilter1 filter1 = DayAndNight.filter1; // Initializes the filter.

            Calendar getCurrentCal = Calendar.getInstance(); // getInstance is used to get the current date and time.

            int hourInampm = getCurrentCal.get(Calendar.HOUR_OF_DAY);
            if (hourInampm>12){
                hourInampm = hourInampm-12;
                ampmCombo1.setSelectedItem("pm");
            }


            selectDate1.setCalendar(getCurrentCal); // Sets the JDateChooser to the current date, so the user can see the current date.
            hoursCombo1.setSelectedItem(Integer.toString(hourInampm)); // Gets the hour minute and second.
            minutesCombo1.setSelectedItem(Integer.toString(getCurrentCal.get(Calendar.MINUTE)));
            secondsCombo1.setSelectedItem(Integer.toString(getCurrentCal.get(Calendar.SECOND)));


            filter1.updateWidthTable(); // updateWidthTable without parameter means it gets the current date and time.
            for (int i = 0; i < widthOfImage1; i++) { // Same algorithm as the "Enter" JButton above.
                for (int j = filter1.wtab_[i]; j < heightOfImage1; j++) {
                    int rgb = newimg21.getRGB(i, j);
                    int rgb2 = filteringColor.filterRGB(i, j, rgb);
                    newimg21.setRGB(i, j, rgb2);
                }
            }

            thumb1.setIcon(new ImageIcon(newimg21));
        }
        if (e.getActionCommand().equals("Animate")) { // If the user enters the "Animate" jButton. // This showcases the animation fo the program.

            try { // This makes sure that the image is unchanged.
                newimg21 = ImageIO.read(new FileInputStream("resources/land_shallow_topo_2048.jpg/"));
            } catch (IOException err) {
                err.printStackTrace();
            }

            clicks++; // Keeps track of the number of times the user enters the animate button.
            System.out.println(clicks);

            DayNightFilter1 filter1 = DayAndNight.filter1; // Initializes the filter.

            Calendar testTime = new GregorianCalendar(2013, 1, 28, clicks, 24, 56); // This is a time randomly chosen to showcase the animation of the program.

            filter1.updateWidthTable(testTime);
            for (int i = 0; i < widthOfImage1; i++) { // Same algorithm as the "Enter" JButton above.
                for (int j = filter1.wtab_[i]; j < heightOfImage1; j++) {
                    int rgb = newimg21.getRGB(i, j);
                    int rgb2 = filteringColor.filterRGB(i, j, rgb);
                    newimg21.setRGB(i, j, rgb2);
                }
            }

            thumb1.setIcon(new ImageIcon(newimg21));
        }
    }
}
class updateAuto implements ActionListener { // This action listener is used for animate automatically and update automatically checkboxes.
    JCheckBox refresh1; // Declares the variables.
    JCheckBox animateRefresh1;

    updateAuto(JCheckBox refresh, JCheckBox animateRefresh) {
        refresh1 = refresh; // Intializes the checkboxes.
        animateRefresh1 = animateRefresh;

    }

    public void actionPerformed(ActionEvent e) {
        if (refresh1.isSelected()) { // If the update automatically checkbox is selected.
            System.out.println("is selected");
            changeDate.checkAuto = true; // Changes the variable for the update automatically as on.
            System.out.println(changeDate.checkAuto);
        }

        if (!refresh1.isSelected()) { // If the update automatically checkbox is not selected.
            System.out.println("Not selected");
            changeDate.checkAuto = false; // Changes the variable for the update automatically as off.
            System.out.println(changeDate.checkAuto);
        }
        if (animateRefresh1.isSelected()){ // If the animate automatically checkbox is selected.
            System.out.println("animate refresh is selected");
            changeDate.checkAnimate = true; // Changes the variable for the update automatically as off.
        }
        if (!animateRefresh1.isSelected()){ // If the animate automatically checkbox is not selected.
            System.out.println("animate refresh is not selected");
            changeDate.checkAnimate = false; // Changes the variable for the update automatically as off.
        }
    }
}
