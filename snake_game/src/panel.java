import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Random;

public class panel extends JPanel implements ActionListener {

static int width= 1200;
static int height=600;
static int unit=50;
Timer timer;
static int delay=160;
Random random;

// food coordinates
    int fx, fy;
    // body length of snake initially
    int body = 3;
    char dir='R';
    int score;
    // to keep a check on the state of the game
    boolean flag= false;

    // arrays to keep the coordinate of the snake
    int xsnake[]=new int[288];
    int ysnake[]=new int[288];

    panel(){
        // sets size of the panel
        this.setPreferredSize(new Dimension(width,height));
        this.setBackground(Color.BLACK);
        // to enable keyboard input
        this.setFocusable(true);
        // adding a keylistener , it tells what key is pressed
        this.addKeyListener(new mykey());

        random = new Random();
        gamestart();

    }

public void gamestart(){
        flag=true;
        spawnfood();
        timer = new Timer(delay,this);
        timer.start();
}

public void spawnfood(){

        fx=random.nextInt((int) width/unit) * unit;
        fy=random.nextInt((int) height/unit)* unit;

}

public void paintComponent (Graphics graphic){
        super.paintComponent(graphic);
        draw(graphic);
}
public void draw(Graphics graphic){
        // spawned the food particle
        if(flag==true){
            graphic.setColor(Color.orange);
            graphic.fillOval(fx ,fy ,unit ,unit);

            // spawing the snake
            for(int i=0;i<body;i++){
                if(i==0){
                    // this is the head of the snake
                    graphic.setColor(Color.red);
                    graphic.fillRect(xsnake[i], ysnake[i], unit, unit);
                }
                else{
                    //the body of the snake
                    graphic.setColor(Color.green);
                    graphic.fillRect(xsnake[i], ysnake[i], unit, unit);
                }
            }
            // spawing the score display
            graphic.setColor(Color.cyan);
            graphic.setFont(new Font("comic sans", Font.BOLD, 40));
            FontMetrics fme= graphic.getFontMetrics(graphic.getFont());
            graphic.drawString("Score:"+ score,(width-fme.stringWidth("Score:"+score))/2,graphic.getFont().getSize() );
        }
        else {
            gameover(graphic);
        }
}

public void gameover(Graphics graphic){
        //drawing the score
    graphic.setColor(Color.cyan);
    //setting the font
    graphic.setFont(new Font("comic sans", Font.BOLD, 40));
    FontMetrics fme= graphic.getFontMetrics(graphic.getFont());
    graphic.drawString("Score:"+ score,(width-fme.stringWidth("Score:"+score))/2,graphic.getFont().getSize() );

    //drawing the gameover text
    graphic.setColor(Color.red);
    //setting the font
    graphic.setFont(new Font("comic sans", Font.BOLD, 80));
    FontMetrics fme1= graphic.getFontMetrics(graphic.getFont());
    graphic.drawString("GAME OVER",(width-fme1.stringWidth("GAME OVER"))/2,height/2 );

    // drawing the replay font
    graphic.setColor(Color.green);
    //setting the font
    graphic.setFont(new Font("comic sans", Font.BOLD, 40));
    FontMetrics fme2= graphic.getFontMetrics(graphic.getFont());
    graphic.drawString("Press R to replay",(width-fme2.stringWidth("Press R to replay"))/2,height/2 - 150);
}


public void move(){
        // for updating rest of the body rather head
        for(int i=body;i>0;i--){
            xsnake[i]=xsnake[i-1];
            ysnake[i]=ysnake[i-1];
        }
        // updating the head of the snake
        switch(dir){
            case 'R':
                xsnake[0]=xsnake[0]+unit;
            break;
            case 'L':
                xsnake[0]=xsnake[0]-unit;
                break;
            case 'U':
                ysnake[0]=ysnake[0]-unit;
                break;
            case 'D':
                ysnake[0]=ysnake[0]+unit;
                break;

        }
}

public void check(){
        if(xsnake[0]<0){
            flag= false;
        }
        else if(xsnake[0]>width){
            flag= false;
        }
        else if(ysnake[0]<0){
            flag = false;
        }
        else if(ysnake[0]>height){
            flag=false;
        }
        //checking hit with body

        for(int i=body ;i >0 ; i--){
if((xsnake[0]==xsnake[i]) && (ysnake[0]==ysnake[i])){
    flag=false;
}
        }
        if(flag==false){
            timer.stop();
        }
    }

    public void eat(){
        if((xsnake[0]==fx) && (ysnake[0]==fy)) {
            body++;
            score++;
            spawnfood();
        }
    }



public class mykey extends KeyAdapter{

        public void keyPressed(KeyEvent e){
            switch(e.getKeyCode()){
                case KeyEvent.VK_UP:
                    if(dir!= 'D'){
                        dir='U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(dir!= 'U'){
                        dir='D';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(dir!= 'L'){
                        dir='R';
                    }
                    break;
                case KeyEvent.VK_LEFT:
                    if(dir!= 'R'){
                        dir='L';
                    }
                    break;
                case KeyEvent.VK_R:
                     if(!flag){
                         score=0;
                         body=3;
                         dir='R';
                         Arrays.fill(xsnake, 0);
                         Arrays.fill(ysnake, 0);
                         gamestart();
                     }
                    break;
            }
        }
}

    public void actionPerformed(ActionEvent e){
if(flag){
    move();
    eat();
    check();
}
repaint();
    }
}
