import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
public class pacMan extends JPanel implements ActionListener,KeyListener{
       private int rowC=21;
       private int coulC=19;
       private int Tilesize=32;
       private int bWid=coulC*Tilesize;
       private int bHei=rowC*Tilesize;
       private Image wallImage;
       private Image bgho,ogho,pgho,rgho;
       private Image pUp,pDo,pLe,pRi;
       char dirr[]={'U','D','L','R'};
       
           class PBlock {
                int x;
                int y;
                int wid;
                int hei;
                Image img;
                int StartY;
                int StartX;
                int veloX=0;
                int veloY=0;
                char c='U';
                PBlock(Image img,int x,int y,int wid,int hei){
                    this.x=x;
                    this.y=y;
                    this.img=img;
                    this.wid=wid;
                    this.hei=hei;
                    this.StartX=x;
                    this.StartY=y;
                }
                void updatedir(char c){
                    char prv=this.c;

                    this.c=c;
                    updateVelo();
                    this.x+=veloX;
                    this.y+=veloY;
                    for(PBlock wall:Walles){
                        if(collosion(this,wall)){
                            this.x-=veloX;
                            this.y-=veloY;
                            this.c=prv;
                            updateVelo();
                        }
                    }
                }
                
                void updateVelo(){
                    if(this.c=='U'){
                        veloX=0;
                        veloY=-Tilesize/4;
                    }
                    else if(this.c=='D'){
                        veloX=0;
                        veloY=Tilesize/4;
                    }
                    else if(this.c=='L'){
                        veloY=0;
                        veloX=-Tilesize/4;
                    }
                    else if(this.c=='R'){
                        veloY=0;
                        veloX=Tilesize/4;
                    }
                    }
                    void reset(){
                        this.x=StartX;
                        this.y=StartY;
                    }
                }
           HashSet<PBlock> Walles;
           HashSet<PBlock> foods;
           HashSet<PBlock> ghosts;
           PBlock pacman;
           String TileStyle[]={
        "XXXXXXXXXXXXXXXXXXX",
        "X        X        X",
        "X XX XXX X XXX XX X",
        "X                 X",
        "X XX X XXXXX X XX X",
        "X    X       X    X",
        "XXXX XXXX XXXX XXXX",
        "OOOX X       X XOOO",
        "XXXX X XXrXX X XXXX",
        "O       bpo       O",
        "XXXX X XXXXX X XXXX",
        "OOOX X       X XOOO",
        "XXXX X XXXXX X XXXX",
        "X        X        X",
        "X XX XXX X XXX XX X",
        "X  X     P     X  X",
        "XX X X XXXXX X X XX",
        "X    X   X   X    X",
        "X XXXXXX X XXXXXX X",
        "X                 X",
        "XXXXXXXXXXXXXXXXXXX" 
    };
    Timer gameLoop;
    int Score=0;
    int Lives=3;
    Boolean GameOver=false;

        pacMan(){
            addKeyListener(this);
            this.setFocusable(true);
            this.setPreferredSize(new Dimension(bWid,bHei));
            this.setBackground(Color.BLACK);
            wallImage=new ImageIcon(getClass().getResource("./img/wall.png")).getImage();

            bgho=new ImageIcon(getClass().getResource("./img/blueGhost.png")).getImage();
            ogho=new ImageIcon(getClass().getResource("./img/orangeGhost.png")).getImage();
            pgho=new ImageIcon(getClass().getResource("./img/pinkGhost.png")).getImage();
            rgho=new ImageIcon(getClass().getResource("./img/redGhost.png")).getImage();

            pUp=new ImageIcon(getClass().getResource("./img/pacmanUp.png")).getImage();
            pDo=new ImageIcon(getClass().getResource("./img/pacmanDown.png")).getImage();
            pLe=new ImageIcon(getClass().getResource("./img/pacmanLeft.png")).getImage();
            pRi=new ImageIcon(getClass().getResource("./img/pacmanRight.png")).getImage();
            
            LoadGame();
            for(PBlock ghost : ghosts){
                ghost.updatedir(dirr[new Random().nextInt(0,4)]);
            }
            gameLoop=new Timer(50,this);
            gameLoop.start();
            

        }
        public void LoadGame(){
            Walles=new HashSet<PBlock>();
            ghosts=new HashSet<PBlock>();
            foods=new HashSet<PBlock>();
            for(int i=0;i<rowC;i++){
                for(int j=0;j<coulC;j++){
                    int x=j*Tilesize;
                    int y=i*Tilesize;
                    char c=TileStyle[i].charAt(j);
                    if(c=='X'){
                    PBlock wall=new PBlock(wallImage,x,y,Tilesize,Tilesize);
                    Walles.add(wall);
                    }
                    else if(c=='b'){
                        PBlock Bg=new PBlock(bgho,x,y,Tilesize,Tilesize);
                        ghosts.add(Bg);
                        }
                        else if(c=='r'){
                            PBlock Rg=new PBlock(rgho,x,y,Tilesize,Tilesize);
                            ghosts.add(Rg);

                            }
                        else if(c=='o'){
                            PBlock Og=new PBlock(ogho,x,y,Tilesize,Tilesize);
                            ghosts.add(Og);
                                }
                        else if(c=='p'){
                            PBlock Pg=new PBlock(pgho,x,y,Tilesize,Tilesize);
                            ghosts.add(Pg);
                                } 
                        else if(c=='P'){
                            pacman=new PBlock(pRi,x,y,Tilesize,Tilesize);
                                }
                        else if(c==' '){
                            PBlock foBlock=new PBlock(null,x+14,y+14,4,4);
                            foods.add(foBlock);
                                }
                }
            }
        }
        public void move(){
            pacman.x+=pacman.veloX;
            pacman.y+=pacman.veloY;
            for(PBlock wall:Walles){
                if(collosion(pacman,wall)){
                    pacman.x-=pacman.veloX;
                    pacman.y-=pacman.veloY;
                    break;
                }
                else if(pacman.x-pacman.wid>bWid){
                    pacman.x=0;
                    pacman.updatedir(dirr[3]);

                }
                else if(pacman.x+pacman.wid<0){
                    pacman.x=bWid;
                    pacman.updatedir(dirr[2]);
                    
                }
            }
            for(PBlock ghost:ghosts){
                if(collosion(pacman,ghost)){
                    ResetPosition();
                }
                if(ghost.y==Tilesize*9&& ghost.c!='U'&&ghost.c!='D'){
                    ghost.updatedir(dirr[new Random().nextInt(2)]);
                }
                ghost.x+=ghost.veloX;
                ghost.y+=ghost.veloY;
                for(PBlock wall:Walles){
                    if(collosion(ghost,wall)){
                        ghost.x-=ghost.veloX;
                        ghost.y-=ghost.veloY;
                        ghost.updatedir(dirr[new Random().nextInt(4)]);
                        break;
                    }
                    else if(ghost.x-ghost.wid>bWid){
                        ghost.x=0;
                        ghost.updatedir(dirr[3]);
                    }
                    else if(ghost.x+ghost.wid<0){
                        ghost.x=bWid;
                        ghost.updatedir(dirr[2]);
                    }
            }
        }
        PBlock EatenFood=null;
          for(PBlock food:foods){
            if(collosion(pacman,food)){
                EatenFood=food;
                Score+=10;
                break;
            }
          }
          foods.remove(EatenFood);
          if(foods.isEmpty()){
            LoadGame();
            ResetPosition();
          }
    }
    private void ResetPosition() {
        pacman.reset();
        pacman.veloX=0;
        pacman.veloY=0;
        Lives-=1;
        for(PBlock ghost:ghosts){
            ghost.reset();
        }

        
    }
        public boolean collosion(PBlock A,PBlock B){
      
            return A.x<B.x+B.wid &&
                   A.x+A.wid>B.x &&
                   A.y<B.y+B.hei &&
                   A.y+A.hei>B.y;

        }
        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            
            g.drawImage(pacman.img,pacman.x,pacman.y,pacman.wid,pacman.hei,null);
            for(PBlock gh :Walles){
                g.drawImage(gh.img,gh.x,gh.y,gh.wid,gh.hei,null);

            }
            for(PBlock gh :ghosts){
                g.drawImage(gh.img,gh.x,gh.y,gh.wid,gh.hei,null);

            }
            g.setColor(Color.white);
            for(PBlock gh :foods){
            
                g.fillRect(gh.x,gh.y,gh.wid,gh.hei );

            }
            g.setFont(new Font("Arial",Font.BOLD,18));
            if(Lives>0){
                GameOver=false;
            }
            else{
                GameOver =true;
            }
            if(GameOver){
                g.drawString("GameOver Score : "+Score,Tilesize/2,Tilesize/2);
                gameLoop.stop();
            }
            else{
                g.drawString("Lives : "+Lives+"X Score : "+Score,Tilesize/2,Tilesize/2);
            }
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            move();
           this.repaint();
        }
        
        @Override
        public void keyPressed(KeyEvent e) {
        }
        @Override
        public void keyReleased(KeyEvent e) {
            if(GameOver){
                LoadGame();
                ResetPosition();
                Lives=3;
                Score=0;
                GameOver=false;
                gameLoop.start();

            }
          if(e.getKeyCode()==KeyEvent.VK_UP){
            pacman.updatedir('U');
          }
          else if(e.getKeyCode()==KeyEvent.VK_DOWN){
            pacman.updatedir('D');
          }
          else if(e.getKeyCode()==KeyEvent.VK_LEFT){
            pacman.updatedir('L');
          }
          else if(e.getKeyCode()==KeyEvent.VK_RIGHT){
            pacman.updatedir('R');
          }
          if(pacman.c=='U'){
            pacman.img=pUp;
          }
          else if(pacman.c=='D'){
            pacman.img=pDo;
          }
          else if(pacman.c=='L'){
            pacman.img=pLe;
          }
          else if(pacman.c=='R'){
            pacman.img=pRi;
          }
          
        }
        @Override
        public void keyTyped(KeyEvent e) {
            

        }
    }
