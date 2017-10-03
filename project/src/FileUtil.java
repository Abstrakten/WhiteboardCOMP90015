import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    private User user;
    private static boolean saved = false;
    private static File existFile;

//    public FileUtil(User user){
//        this.user = user;
//    JOptionPane.showMessageDialog(null, "IP and PORT can not be null!", "whiteBoard1.4",
//    JOptionPane.INFORMATION_MESSAGE);
// FileNameExtensionFilter filter = new FileNameExtensionFilter(

                //"Images, WB", "jpg", "wb");
//    }

    public static void save(List<User> users) {
        if (!saved) {
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter wbFilter = new FileNameExtensionFilter("WhiteBoard File(*.wb)", "wb");
            chooser.setFileFilter(wbFilter);
            int option = chooser.showDialog(null, "Save");
            if(option == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                String name = chooser.getName(file);
                try {
                    if (!name.contains(".wb")) {
                        file = new File(chooser.getCurrentDirectory(), name + ".wb");
                    }
                    FileOutputStream fos = new FileOutputStream(file);
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    oos.writeObject(users);
                    oos.close();
                    fos.close();

                    existFile = file;
                    saved = true;
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "IO Exception catch, Save fail",
                            "whiteBoard1.4", JOptionPane.INFORMATION_MESSAGE);
                    e.printStackTrace();

                }
            }
        } else {
            try {
                FileOutputStream fos = new FileOutputStream(existFile);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(users);
                oos.close();
                fos.close();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "IO Exception catch, Save fail",
                        "whiteBoard1.4", JOptionPane.INFORMATION_MESSAGE);
            }
        }

    }

    public static void saveAs(List<User> users, JPanel jPanel){
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter wbFilter = new FileNameExtensionFilter("WhiteBoard File(*.wb)", "wb");
        FileNameExtensionFilter imageFilter = new FileNameExtensionFilter("Image File(*.jpg)", "jpg");
        chooser.setFileFilter(wbFilter);
        chooser.setFileFilter(imageFilter);
        int option = chooser.showDialog(null, "Save");
        if(option == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            String name = chooser.getName(file);
            FileOutputStream fos;
            ObjectOutputStream oos;
            try {
                if(chooser.getFileFilter() == wbFilter) {
                    if(!name.contains(".wb")) {
                        file = new File(chooser.getCurrentDirectory(), name + ".wb");
                    }
                    fos = new FileOutputStream(file);
                    oos = new ObjectOutputStream(fos);
                    oos.writeObject(users);
                    oos.close();
                    fos.close();

                    existFile = file;
                    saved = true;

                } else if(chooser.getFileFilter() == imageFilter) {
                    if(!name.contains(".jpg")){
                        file = new File(chooser.getCurrentDirectory(), name + ".jpg");
                    }
                    BufferedImage image = new BufferedImage(jPanel.getWidth(), jPanel.getHeight(),
                            BufferedImage.TYPE_3BYTE_BGR);
                    Graphics2D graphics2D = image.createGraphics();
                    jPanel.paintAll(graphics2D);

                    ImageIO.write(image, "jpg", file);

                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "IO Exception catch, Save fail",
                        "whiteBoard1.4", JOptionPane.INFORMATION_MESSAGE);
                e.printStackTrace();

            }
        }
    }

    public static List<User> load() {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter wbFilter = new FileNameExtensionFilter("WhiteBoard File(*.wb)", "wb");
        chooser.setFileFilter(wbFilter);
        int option = chooser.showDialog(null, "Open");
        List<User> users = new ArrayList<>();
        if(option == JFileChooser.APPROVE_OPTION) {
            try {
                File file = chooser.getSelectedFile();
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);
                users = (List<User>) ois.readObject();
                fis.close();
                ois.close();

                saved = true;
                existFile = file;
            } catch (IOException | ClassNotFoundException e) {
                JOptionPane.showMessageDialog(null, "Load fail",
                        "whiteBoard1.4", JOptionPane.INFORMATION_MESSAGE);
                e.printStackTrace();
            }
        }
        return users;
    }

    public static File getExistFile() {
        return existFile;
    }

    public static void main(String[] args) {
    }
}
