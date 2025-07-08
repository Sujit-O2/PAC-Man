import javax.swing.JFrame;

public class App {
    public static void main(String[] args) throws Exception {
        int rowC=21;
        int coulC=19;
        int Tilesize=32;
        int BWid=coulC*Tilesize;
        int bHei=rowC*Tilesize;
        JFrame frame=new JFrame("PAC MAN");
        frame.setSize(BWid,bHei);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        pacMan pac=new pacMan();
        frame.add(pac);
        pac.requestFocus();
        frame.pack();
    }
}
