import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.lang.*;
import java.math.*;
import java.awt.image.*;
import java.applet.*;
import javax.swing.border.*;
import javax.imageio.ImageIO;
import java.net.*; 
import java.util.*;
import java.awt.Color;
import java.awt.geom.Rectangle2D;
import javax.sound.sampled.*;
public class GameTemplate extends JPanel implements KeyListener,Runnable
{
	private float angle;
	public int num = 16;
	public int num2 = 13;
	public int num3 = 7;
	public int num4 = 27;
	public int jumpCounter,runCounter,rockCounter;
	private int x;
	private int y;
	int [] layerSpeed = new int[num3];
	String [] obstacles = new String[100];
	ArrayList <Obstacle> rockList = new ArrayList<>();
	private JFrame frame;
	Thread t;
	Boolean runBool = true;
	private boolean gameOn;
	BufferedImage heart,arrow;
	Image heartt,arroww;
	BufferedImage[] wolves=new BufferedImage[num];
	Image[] wolvess = new Image[num];
	BufferedImage[] rock=new BufferedImage[3];
	Image[] rocks = new Image[3];
	BufferedImage[] jump=new BufferedImage[num2];
	Image[] jumpp = new Image[num2];
	BufferedImage[] background = new BufferedImage[num3];
	Image[] backgroundd = new Image[num3];
	boolean restart=false;
	int imgCount=0;
	int imgCount2=0;
	int imgCount3=0;
	int rockSpeed = 0;
	int sizee = 50;
	int gameSpeed=10;
	Rectangle wolfBox,jumpBox;
	int liveCount = 3;
	int alreadyDone = 0;
	int levelCounter = 0;
	Boolean finish = false;

	public GameTemplate()
	{
		frame=new JFrame();
		x=100;
		y=355;
		gameOn=true;
		wolfBox = new Rectangle(x + 100,y+100,200,200);
		jumpBox = new Rectangle(x + 100,y+30,200,200);

		try {
			for(int x=0;x<num;x++){
				wolves[x]= ImageIO.read(new File("wolf/" + x +".png")); 
				wolvess[x] = wolves[x].getScaledInstance(400, 400, Image.SCALE_DEFAULT);
			}
			for(int x=0;x<num2;x++){
				int a = x + 50;
				jump[x]= ImageIO.read(new File("wolf/"+ a +".png"));
				jumpp[x] = jump[x].getScaledInstance(400, 400, Image.SCALE_DEFAULT);
			}
			for(int x=0;x<num3;x++){
				background[x]= ImageIO.read(new File("background/layer" + x +".png")); 
				backgroundd[x] = background[x].getScaledInstance(background[x].getWidth(),900, Image.SCALE_DEFAULT);
			}
			for(int x=0;x<num3;x++){
				layerSpeed[x] = 0;
				// 0 = grass
				// 1 = rock
				// 2 = black trees 
				// 3 = purple trees
				// 4 = gray trees
				// 5 = stars
				// 6 = moon
			}
			for(int x=0;x<3;x++){
				rock[x]= ImageIO.read(new File("gameObjects/rock"+ x +".png")); 
				rocks[x] = rock[x].getScaledInstance(sizee, sizee, Image.SCALE_DEFAULT);
			}
			heart= ImageIO.read(new File("gameObjects/heart.png")); 
			heartt= heart.getScaledInstance(sizee, sizee, Image.SCALE_DEFAULT);
			arrow= ImageIO.read(new File("gameObjects/arrow.png")); 
			arroww= arrow.getScaledInstance(sizee*3/2, sizee, Image.SCALE_DEFAULT);
		}
		catch (IOException e) {
			System.out.println(e+" what image?");
		}
		makeList();
		frame.addKeyListener(this);
		frame.add(this);
		frame.setSize(1500,900);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		t=new Thread(this);
		t.start();
	}
	public void music(){
		try{
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(GameTemplate.class.getResource("sound/wolf3.wav"));
			Clip clip = AudioSystem.getClip();
			clip.open(audioIn);
			clip.start();
		}catch(Exception ex){
			System.out.println("Error with playing sound");
			ex.printStackTrace();
		}
		
	}
	public void makeList(){
		obstacles = new String[100];
		File name = new File("Obstacles.txt");
		try
		{
			BufferedReader inputt = new BufferedReader(new FileReader(name));
			String text;
			while( (text=inputt.readLine())!= null)
			{
				obstacles = text.split("");
			}
						
		}
		catch (IOException io)
		{
			System.err.println("File error");
		}

		rockList = new ArrayList<>();
		for(int i = 0; i<obstacles.length; i++){
			if(obstacles[i].equals("1")){
				rockList.add(new Obstacle(rocks[imgCount3],false,1, x, y,sizee,sizee));
			}
			else if(obstacles[i].equals("2")){
				rockList.add(new Obstacle(heartt,false,2, x, y,sizee,sizee));
			}
			else if(obstacles[i].equals("3")){
				rockList.add(new Obstacle(arroww,false,3, x,y,sizee*3/2,sizee));
			}
			imgCount3++;
			if(imgCount3>(2)){
				imgCount3=0;
			}
		}
		rockList.get(0).setBool(true);
	}

	public void run()
	{
		while(true)
		{
			if(gameOn)
			{
				if(rockList.get(rockCounter).getBool() == false){
					rockCounter++;
					rockSpeed = 0;
					rockList.get(rockCounter).setBool(true);
				}

				if(runBool){
					runCounter++;
					if(runCounter % 1 == 0){
						imgCount++;
						if(imgCount>(num-1)){
							imgCount=0;
						}
					}
				}
				else {
					jumpCounter++;
					if(jumpCounter % 3 == 0){
						imgCount2++;
						if(imgCount2<7){
							y-=5;
						}
						else if(imgCount2>7){
							y+=5;
						}

						
						if(imgCount2>(num2-1)){
							imgCount2=0;
							runBool = true;
							imgCount = 6;
							jumpCounter=0;
						}
					}
				}
					if(runBool && wolfBox.intersects(rockList.get(rockCounter).getHitbox())){
						if(rockList.get(rockCounter).getId() == 1){
							liveCount--;
						}
						else if(rockList.get(rockCounter).getId() == 2 && liveCount<=2){
							liveCount++;
						}
						else if(rockList.get(rockCounter).getId() == 3){
							liveCount-=2;
						}
						rockList.get(rockCounter).setBool(false);
					}
					else if(!runBool && jumpBox.intersects(rockList.get(rockCounter).getHitbox())){
						if(rockList.get(rockCounter).getId() == 1){
							liveCount--;
						}
						else if(rockList.get(rockCounter).getId() == 2){
							liveCount++;
						}
						else if(rockList.get(rockCounter).getId() == 3){
							liveCount-=2;
						}
						rockList.get(rockCounter).setBool(false);
					}
				}
				
			if(restart)
			{
				makeList();
				rockCounter = 0;
				imgCount=0;
				imgCount2= 0;
				imgCount3= 0;
				gameSpeed= 10;
				rockSpeed = 0;
				liveCount = 3;
				gameOn = true;
				runBool = true;
				restart=false;
				alreadyDone = 0;
				finish = false;
				x=100;
				y=355;
			}
			try{
				t.sleep(20);
			}
			catch(InterruptedException e){}
			repaint();
		}
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		g2d.setColor(Color.MAGENTA);

		

		for(int x=(num3-1);x>=0;x--){
		g2d.drawImage(backgroundd[x],layerSpeed[x] - 960,0,null);
		g2d.drawImage(backgroundd[x],layerSpeed[x] + 960,0,null);
		}

		layerSpeed[0]-=gameSpeed;
		layerSpeed[1]-=gameSpeed;
		layerSpeed[2]-=gameSpeed;
		layerSpeed[3]-=gameSpeed/5;
		layerSpeed[4]-=gameSpeed/7;
		layerSpeed[5]-=gameSpeed/9;
		layerSpeed[6]-=gameSpeed/10;

		layerSpeed[0] = overlapFix(layerSpeed[0]);
		layerSpeed[1] = overlapFix(layerSpeed[1]);
		layerSpeed[2] = overlapFix(layerSpeed[2]);
		layerSpeed[3] = overlapFix(layerSpeed[3]);
		layerSpeed[4] = overlapFix(layerSpeed[4]);
		layerSpeed[5] = overlapFix(layerSpeed[5]);
		layerSpeed[6] = overlapFix(layerSpeed[6]);

		
		if(gameOn){
			if(runBool)
			{
				if(wolvess[imgCount]!=null)
					g2d.drawImage(wolvess[imgCount],x,y,null);
			}
			else if(jumpp[imgCount2]!=null)
			{
				g2d.setColor(Color.BLUE);
				//g2d.fill(jumpBox);
				g2d.drawImage(jumpp[imgCount2],x,y,null);
			}
			g2d.setColor(Color.YELLOW);
			if(rockList.get(rockCounter).getBool()){
				if(rockList.get(rockCounter).getId() == 3){
					rockList.get(rockCounter).setY(600);
					rockList.get(rockCounter).setX(rockSpeed*2 + 1920);
				}
				else{
					rockList.get(rockCounter).setY(630);
					rockList.get(rockCounter).setX(rockSpeed + 1920);
				}
				rockList.get(rockCounter).setHitbox();
				// g2d.fill(rockList.get(rockCounter).getHitbox());
				g2d.drawImage(rockList.get(rockCounter).getImage(),rockList.get(rockCounter).getX(),rockList.get(rockCounter).getY(),null);
				rockSpeed-=gameSpeed;
				if(rockSpeed + 1920<-50){
					rockList.get(rockCounter).setBool(false);
				}
			}
			if(rockCounter%5 == 0 && rockCounter != 0){
				g2d.setColor(Color.RED);
				g2d.setFont(new Font("Copperplate", Font.PLAIN, 20)); 
				g2d.drawString("Level Up...FASTER!!", 20, 20);
			}
			if(rockCounter%5 == 0 && rockCounter!=alreadyDone){
				music();
				gameSpeed +=5;
				alreadyDone = rockCounter;
				levelCounter++;
				if(levelCounter == 4){
					finish = true;
				}

			}
		}
		if(finish){
				g2d.setColor(Color.GREEN);
				g2d.setFont(new Font("Copperplate", Font.PLAIN, 50)); 
				g2d.drawString("You Won!! Click Enter to Play Again...", 200, 300);
				gameOn = false;
				runBool = false;
				g2d.drawImage(wolvess[imgCount],x,y,null);
				x+=5;
				imgCount++;
				if(imgCount>(num-1)){
					imgCount=0;
				}
			}
		if(liveCount>=3){
			g2d.drawImage(heartt,1000,100,null);
			g2d.drawImage(heartt,1100,100,null);
			g2d.drawImage(heartt,1200,100,null);
		}
		else if(liveCount==2){
			g2d.drawImage(heartt,1000,100,null);
			g2d.drawImage(heartt,1100,100,null);
		}
		else if(liveCount==1)
			g2d.drawImage(heartt,1000,100,null);

		else if(liveCount<=0){
			gameOn = false;
			runBool = false;
			rockList.get(rockCounter).setBool(false);
			g2d.setColor(Color.RED);
			g2d.setFont(new Font("Copperplate", Font.PLAIN, 50)); 
			g2d.drawString("You Done Died! To Respawn, Click Enter", 200, 300);
		}
	}
	public void keyPressed(KeyEvent key)
	{

		if(key.getKeyCode()==32)
		{
			runBool = false;
			
		}
		if(key.getKeyCode()==10)
			restart=true;
	}
	public void keyReleased(KeyEvent key)
	{
        
	}
	public void keyTyped(KeyEvent key)
	{
	}
	public int overlapFix(int x){
		if(x <= -960)
			x+=1920;

		if(x >= 960)
			x-=1920;

		return x;
	}
	public static void main(String args[])
	{
		GameTemplate app=new GameTemplate();
	}
}