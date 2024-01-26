package DemoGame;
import javax.swing.JFrame;

public class Mainclass {

	public static void main(String[] args) {
		JFrame f=new JFrame();
		f.setTitle("Brick Breaker");
		f.setSize(700, 600);
		f.setLocationRelativeTo(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		f.setResizable(false);
		
		GamePlay gameplay=new GamePlay();
		//adding this class to frame
		f.add(gameplay);
	}

}
