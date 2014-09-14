import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import controlP5.*; 
import processing.pdf.*; 
import java.util.List; 
import java.util.LinkedList; 
import java.awt.Frame; 
import java.util.Comparator; 
import java.util.Collections; 
import java.util.Arrays; 
import java.util.HashSet; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class TriangulateDF extends PApplet {

/* 

Triangulate v1.0 Delaunay Edition (c) 2014 (GNU GPL 3.0 | CC BY-SA 3.0)
  
\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2557  \u2588\u2588\u2588\u2588\u2588\u2557         \u2588\u2588\u2588\u2588\u2588\u2588\u2557  \u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2557 \u2588\u2588\u2557   \u2588\u2588\u2557
\u2588\u2588\u2554\u2550\u2550\u2550\u2550\u255d \u2588\u2588\u2554\u2550\u2550\u2588\u2588\u2557        \u2588\u2588\u2554\u2550\u2550\u2588\u2588\u2557 \u2588\u2588\u2554\u2550\u2550\u2550\u2550\u255d \u2588\u2588\u2551   \u2588\u2588\u2551
\u2588\u2588\u2551      \u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2551 \u2588\u2588\u2588\u2588\u2588\u2557 \u2588\u2588\u2551  \u2588\u2588\u2551 \u2588\u2588\u2588\u2588\u2588\u2557   \u2588\u2588\u2551   \u2588\u2588\u2551
\u2588\u2588\u2551      \u2588\u2588\u2554\u2550\u2550\u2588\u2588\u2551 \u255a\u2550\u2550\u2550\u2550\u255d \u2588\u2588\u2551  \u2588\u2588\u2551 \u2588\u2588\u2554\u2550\u2550\u255d   \u255a\u2588\u2588\u2557 \u2588\u2588\u2554\u255d
\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2557 \u2588\u2588\u2551  \u2588\u2588\u2551        \u2588\u2588\u2588\u2588\u2588\u2588\u2554\u255d \u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2557  \u255a\u2588\u2588\u2588\u2588\u2554\u255d 
 \u255a\u2550\u2550\u2550\u2550\u2550\u255d \u255a\u2550\u255d  \u255a\u2550\u255d        \u255a\u2550\u2550\u2550\u2550\u2550\u255d  \u255a\u2550\u2550\u2550\u2550\u2550\u2550\u255d   \u255a\u2550\u2550\u2550\u255d  
   
Developed by DPLockheart.
https://www.facebook.com/groups/DigitalArtistNexus

Original code by Ale Gonz\u00e1lez. 

Special thanks to Cliff Pottberg, Rob Mac, Adam Tuerff and the Glitch Artist Collective 
community for their contributions and support!
  
Just run and Delaunay!

*/

public void setup() {
  
  frameRate(30);
  noStroke();
  smooth();
  
  img = loadImage("Instructions.jpg");
  toolsBG = loadImage("Tools.jpg");
  
  size(img.width,img.height);

  cp5 = new ControlP5(this);
  cf = addControlFrame("Tools",320,750,0,0);
  
  cp5.addButton("button_facebookLink")
    .setLabel("Facebook")
    .setSize (50,20)
    .setPosition(940,480)
  ;
  
  insets = frame.getInsets();
  widthInsets = insets.left + insets.right;
  heightInsets = insets.top + insets.bottom;

  image(img,0,0);
    
  noLoop();
  
}

public void draw(){
    
// Are You Ready!?
  if (sauce == null) { 
    loop();
  } else {
    ready = true;
    cp5.hide();
  } 
  
  if (ready == true) {
    
    if(liveEdit == true){
      loop();
    } else {
      noLoop();
    }
    
    // Define Mesh
    if (mesh == null) { 
      meshSauce = sauce;
    } else {
      if (meshStretch == true) {
        tmpMesh = loadImage("data/tmp/tmpMesh.png");
        tmpMesh.resize(sauce.width,sauce.height);
        meshSauce = tmpMesh;
      } else {
        meshSauce = mesh;
      }
    }
    
    size(sauce.width, sauce.height);
    frame.setSize(sauce.width,sauce.height);
    
    W = sauce.width;
    H = sauce.height;
    
    background(255,255,255);
    
    switch(chooseBG) {
      case 0:
      break;
      case 1:
        image(sauce,0,0);
      break;
      case 2:
        if (bgImage == null) {
          // Nothing
        } else {
          if (meshStretch == true) {
            bgImage.resize(sauce.width,sauce.height);
            image(bgImage,0,0);
            saveFrame("data/tmp/" + qual + area + fillOpacity + edgeSize + "_tmpBG.jpg");
          } else {
            image(bgImageOrig,0,0);
            saveFrame("data/tmp/" + qual + area + fillOpacity + edgeSize + "_tmpBG.jpg");
          }
         }
      break;      
    }
    
    if (recordPDF == true) {
      beginRecord(PDF,"renders/vector.pdf"); 
    }
    
    begin(); 
    
    if (recordPDF == true) {
      endRecord(); 
      recordPDF = false; 
    }
    
    switch(setFilterMode) {
      case 0:
        // None
      break;
      case 1:
        filter(THRESHOLD);
      break;
      case 2:
        filter(GRAY);
      break;
      case 3:
        filter(INVERT);
      break;
      case 4:
        filter(POSTERIZE, setPosterizeValue);
      break;
      case 5:
        filter(BLUR, setBlurValue);
      break;
      case 6:
        filter(ERODE);
      break;
      case 7:
        filter(DILATE);
      break;
    }
    
    switch(saveImage) {
      case 0:
      break;
      case 1:
        saveFrame("renders/" + qual + area + fillOpacity + edgeSize + "_Composit.png");
        saveImage = 0;
      break;
      case 2:
        saveFrame("renders/" + qual + area + fillOpacity + edgeSize + "_Composit.jpg");
        saveImage = 0;
      break;
      case 3:
        saveFrame("renders/" + qual + area + fillOpacity + edgeSize + "_Composit.tif");
        saveImage = 0;
      break;
      case 4:
        recordPDF = true;
        saveImage = 0;
      break;
      case 5: 
        Canvas.save("renders/" + qual + area + fillOpacity + edgeSize + "Transparent_Composit.png");
        saveImage = 0;
      break;
    }      
  }
}

public void begin(){
  ArrayList<PVector> vertices = new ArrayList<PVector>();
  EdgeDetector.extractPoints(vertices, meshSauce, EdgeDetector.SOBEL, area, qual);
  
  for (float i = 0, h = 0, v = 0; i<=1 ; i+=.05f, h = W*i, v = H*i) {
    vertices.add(new PVector(h, 0));
    vertices.add(new PVector(h, H));
    vertices.add(new PVector(0, v));
    vertices.add(new PVector(W, v));
  }
  
  triangles = new ArrayList<Triangle>();
  new Triangulator().triangulate(vertices, triangles);
  
  Triangle t = new Triangle();
  
  for (int i=0; i < triangles.size(); i++) {
    t = triangles.get(i); 
    if (vertexOutside(t.p1) || vertexOutside(t.p2) || vertexOutside(t.p3)) triangles.remove(i);        
  }
  
  int tSize = triangles.size();
  colors = new int[tSize*3];
  PVector c = new PVector();
  
  for (int i = 0; i < tSize; i++) {
    
    c = triangles.get(i).center();
    
    if (meshColorSource == true){
      selectedsauce = meshSauce;
    } else {
      selectedsauce = sauce;
    }
    
    if (flipMode == true){
      colors[i] = ( selectedsauce.get(PApplet.parseInt(c.y), PApplet.parseInt(c.x)) != 0 ) ? selectedsauce.get(PApplet.parseInt(c.y), PApplet.parseInt(c.x)) : selectedsauce.get(PApplet.parseInt(c.y/2), PApplet.parseInt(c.x/2));
    } else {
      colors[i] = selectedsauce.get(PApplet.parseInt(c.x), PApplet.parseInt(c.y));
    }
  }
  
  buildMesh();
  
}

// Util function to prune triangles with vertices out of bounds  
public boolean vertexOutside(PVector v) { return v.x < 0 || v.x > width || v.y < 0 || v.y > height; }  

public int randomColor() {
  return color(random(crmRedMin,crmRedMax),random(crmGreenMin,crmGreenMax),random(crmBlueMin,crmBlueMax),random(crmAlphaMin,crmAlphaMax));
}

public void buildMesh() {
  Triangle t = new Triangle();
  int edge = edgePicker.getColorValue();
  int fill = fillPicker.getColorValue();
  
  Canvas = createGraphics(width,height);
  Canvas.beginDraw();
  Canvas.beginShape(TRIANGLES);
  
  for (int i = 0; i < triangles.size(); i++) {
    t = triangles.get(i); 
    
    // Normal logic
    if (CrazyRainbowModeOn==false){
      
      if (fill == color(0,0,0,0)){
        Canvas.fill(colors[i],fillOpacity);
      } else {
        Canvas.fill(fill);
      }
        
      if (edge == color(0,0,0,0)){
        Canvas.stroke(colors[i],fillOpacity);    
      } else {
        Canvas.stroke(edge);
      }
      
      } else {
        
      //Rainbow Mode logic
      int rand = randomColor();
      Canvas.fill(rand);
      
      if (edge == color(0,0,0,0)){
        Canvas.stroke(rand);      
      } else {
        Canvas.stroke(edge);}
      } 
      Canvas.vertex(t.p1.x, t.p1.y);
      Canvas.vertex(t.p2.x, t.p2.y);
      Canvas.vertex(t.p3.x, t.p3.y); 
      
      Canvas.strokeWeight(edgeSize); 
    } 
  
  Canvas.endShape();
  Canvas.endDraw();
  
  image(Canvas,0,0);
}






private ControlP5 cp5;

public java.awt.Insets insets;
public int widthInsets;
public int heightInsets;

// Graphics
PGraphics Canvas;
PImage img, toolsBG, sauce, sauce_scaled, tmpMesh, meshSauce, mesh, bgImage, bgImageOrig, selectedsauce;

// Triangulator Perms
int W, H;
int[] colors;

ArrayList<Triangle> triangles;

// GUI 
int textColor = color(255,255,255);
int accentColor = color(255,0,50);
int backgroundColor = color(22,22,22);

ControlFrame cf;
Textlabel facebookLabel, optionsLabel, modesLabel, drawLabel, edgePickerLable, fillPickerLable, blendModeLabel, filterModeLabel;
Toggle autoResizeToggle, liveEditToggle, stretchMeshToggel, meshColorToggle, flipModeToggel, CRMToggel, meshBGToggle,
altBGToggle, noBGToggle, blendBlendToggle, blendAddToggle, blendSubtractToggle, blendDarkestToggle,
blendLightestToggle, blendDifferenceToggle, blendExclusionToggle, blendMultiplyToggle, blendScreenToggle, blendReplaceToggle, 
blendOffToggle, filterThresholdToggle, filterGrayToggle, filterInvertToggle, filterPosterizeToggle, filterBlurToggle, 
blendHardLightToggle, blendSoftLightToggle, blendDodgeToggle, blendBurnToggle, blendOverlayToggle, filterErodeToggle, filterDilateToggle, filterOffToggle;
Slider fillOpacitySlider, strokeSizeSlider;
ColorPicker edgePicker, fillPicker;
Knob areaControl, qualityControl, blurControl, posterizeControl;
Textarea ta;

// Inital States
boolean runonce = true;
boolean autoResize = true;
boolean ready = false;
boolean liveEdit = true;
boolean recordPDF = false;
int chooseBG = 1;

// Keys
boolean winKeyL = false;
boolean winKeyR = false;
boolean comKey = false;
boolean ctrlKey = false;
boolean sKey = false;
boolean mKey = false;
boolean bKey = false;

// Modes
boolean meshStretch = true; // Default True
boolean meshColorSource = false; // Default False
boolean meshOverlay = false;
boolean flipMode = false; // Default false
boolean CrazyRainbowModeOn = false; // Default false

// Options
float fillOpacity = 255; // Default 255
int edge = color(0,0,0,0); // Default 0,0,0,0
float edgeSize = 1; // Default 1
int saveImage = 0;
int area = 300; // Default 300
int qual = 4; // Default 4 - lower = finer
int crmRedMin = 100; // CRM
int crmRedMax = 200; // CRM
int crmGreenMin = 100; // CRM
int crmGreenMax = 200; // CRM
int crmBlueMin = 100; // CRM
int crmBlueMax = 200; // CRM
int crmAlphaMin = 50; // CRM
int crmAlphaMax = 200; // CRM
int setBlendMode = 0; // Blend Mode
int setFilterMode = 0; // Filter Mode
int setPosterizeValue = 2; // Filter Value
int setBlurValue = 0; // Filter Value
/*
  An algorithm that uses a custom implementation of a Sobel/Scharr operator to get the significant points of a picture.
*/

static class EdgeDetector
{
    static final int [][][] OPERATOR  = new int[][][]  {
        { {2,  2, 0}, { 2, 0,  -2}, {0,  -2, -2}},  //Sobel kernel
        { {6, 10, 0}, {10, 0, -10}, {0, -10, -6}}   //Scharr kernel
    };
    static final int SOBEL = 0, SCHARR = 1;  //Indexes of the kernels in the previous array
    
    //This method add significant points of the given picture to a given list
    public static void extractPoints(List <PVector> vertices, PImage img, int op, int treshold, int res) 
    {     

            int col = 0, colSum = 0, W = img.width-1, H = img.height-1;
          
          //For any pixel in the image excepting borders            
          for (int Y = 1; Y < H; Y += res) for (int X = 1; X < W; X += res, colSum = 0) 
          {
              //Convolute surrounding pixels with desired operator       
              for (int y = -1; y <= 1; y++) for (int x = -1; x <= 1; x++, col = img.get((X+x), (Y+y))) 
                  colSum += OPERATOR[op][x+1][y+1] * ((col>>16 & 0xFF)+(col>>8 & 0xFF)+(col & 0xFF));               
              //And if the resulting sum is over the treshold add pixel position to the list
              if (abs(colSum) > treshold) vertices.add(new PVector(X, Y));                                  
          }  
     }    
}    
// Build The Frame
public ControlFrame addControlFrame(String theName, int theWidth, int theHeight, int theX, int theY) {
  Frame f = new Frame(theName);
  ControlFrame p = new ControlFrame(this, theWidth, theHeight);
  f.add(p);
  p.init();
  f.setTitle(theName);
  f.setSize(p.w, p.h);
  f.setLocation(theX, theY);
  f.setResizable(false);
  f.setVisible(true);
  return p;
}

// Initiate The Frame
public class ControlFrame extends PApplet {
  
  int w, h;
  ControlP5 cp5;
  Object parent;
  
  public void setup() {
    size(w,h);
    frameRate(25);
    
    cp5 = new ControlP5(this);
    
    cp5.addButton("button_chooseSauce")
      .setPosition(10,10)
      .setSize(90,20)
      .setColorLabel(textColor)
      .setColorBackground(accentColor)
      .plugTo(parent,"button_chooseSauce")
      .setLabel("Sauce...(CTRL+S)")
    ;
     
    cp5.addButton("button_chooseMesh")
      .setPosition(116,10)
      .setSize(90,20)
      .setColorLabel(textColor)
      .setColorBackground(accentColor)
      .plugTo(parent,"button_chooseMesh")
      .setLabel("Mesh...(CTRL+M)")
    ;

    cp5.addButton("button_chooseBG")
      .setPosition(222,10)
      .setSize(90,20)
      .setColorLabel(textColor)
      .setColorBackground(accentColor)
      .plugTo(parent,"button_chooseBG")
      .setLabel("BG...(CTRL+B)")
    ;
    
    optionsLabel = cp5.addTextlabel("label_options")
      .setText("GLOBAL OPTIONS")
      .setPosition(7,40);
    ;
     
    autoResizeToggle = cp5.addToggle("toggle_autoResize")
      .setPosition(10,60)
      .setSize(90,20)
      .setColorLabel(textColor)
      .setColorBackground(accentColor)
      .toggle()
      .plugTo(parent,"toggle_autoResize")
      .setLabel("Resize")
    ;
     
    autoResizeToggle.getCaptionLabel().getStyle().marginTop = -18;
    autoResizeToggle.getCaptionLabel().getStyle().marginLeft = 5;
    
    stretchMeshToggel = cp5.addToggle("toggle_stretchMesh")
      .setPosition(116,60)
      .setSize(90,20)
      .setColorLabel(textColor)
      .setColorBackground(accentColor)
      .toggle()
      .plugTo(parent,"toggle_stretchMesh")
      .setLabel("Flex Mesh")
    ;
    
    stretchMeshToggel.getCaptionLabel().getStyle().marginTop = -18;
    stretchMeshToggel.getCaptionLabel().getStyle().marginLeft = 5;
    
    liveEditToggle = cp5.addToggle("toggle_liveEdit")
      .setPosition(222,60)
      .setSize(90,20)
      .setColorLabel(textColor)
      .setColorBackground(accentColor)
      .toggle()
      .plugTo(parent,"toggle_liveEdit")
      .setLabel("Live Edit")
      .toggle()
    ;
     
    liveEditToggle.getCaptionLabel().getStyle().marginTop = -18;
    liveEditToggle.getCaptionLabel().getStyle().marginLeft = 5;
       
    modesLabel = cp5.addTextlabel("label_modes")
      .setText("MODES")
      .setPosition(7,90);
    ;
     
    meshColorToggle = cp5.addToggle("toggle_meshColorMode")
      .setPosition(10,110)
      .setSize(90,20)
      .setColorLabel(textColor)
      .setColorBackground(accentColor)
      .plugTo(parent,"toggle_meshColorMode")
      .setLabel("Mesh Color")
    ;
     
    meshColorToggle.getCaptionLabel().getStyle().marginTop = -18;
    meshColorToggle.getCaptionLabel().getStyle().marginLeft = 5;
  
    flipModeToggel = cp5.addToggle("toggle_flipMode")
      .setPosition(116,110)
      .setSize(90,20)
      .setColorLabel(textColor)
      .setColorBackground(accentColor)
      .plugTo(parent,"toggle_flipMode")
      .setLabel("Color Flip")
    ;
     
    flipModeToggel.getCaptionLabel().getStyle().marginTop = -18;
    flipModeToggel.getCaptionLabel().getStyle().marginLeft = 5;
     
    CRMToggel = cp5.addToggle("toggle_CRM")
      .setPosition(222,110)
      .setSize(90,20)
      .setColorLabel(textColor)
      .setColorBackground(accentColor)
      .plugTo(parent,"toggle_CRM")
      .setLabel("Rainbow")
    ;
     
    CRMToggel.getCaptionLabel().getStyle().marginTop = -18;
    CRMToggel.getCaptionLabel().getStyle().marginLeft = 5;
   
    drawLabel = cp5.addTextlabel("label_draw")
      .setText("DRAW OPTIONS")
      .setPosition(7,140);
    ;
    
    fillOpacitySlider = cp5.addSlider("slider_fillOpacity")
      .setRange(0,255)
      .setValue(255)
      .setDecimalPrecision(0)
      .setPosition(10,160)
      .setSize(140,20)
      .plugTo(parent,"slider_fillOpacity")
      .setLabel("FILL OPACITY")
    ;
      
    fillOpacitySlider.getCaptionLabel().getStyle().marginLeft = -120;
    
    strokeSizeSlider = cp5.addSlider("slider_strokeSize")
      .setRange(1,10)
      .setValue(1)
      .setDecimalPrecision(0)
      .setPosition(170,160)
      .setSize(140,20)
      .plugTo(parent,"slider_strokeSize")
      .setLabel("STROKE SIZE")
    ;
    
    strokeSizeSlider.getCaptionLabel().getStyle().marginLeft = -120;
    
    edgePickerLable = cp5.addTextlabel("label_edgePicker")
      .setText("EDGE COLOR")
      .setPosition(7,190)
    ;
    
    edgePicker = cp5.addColorPicker("RGB_edgePicker",10,210,140,20)
      .setColorValue(color(0,0,0,0))
    ; 
    
    fillPickerLable = cp5.addTextlabel("label_fillPicker")
      .setText("FILL COLOR")
      .setPosition(167,190)
    ;
    
    fillPicker = cp5.addColorPicker("RGB_fillPicker",170,210,140,20)
     .setColorValue(color(0,0,0,0))
   ;
   
   areaControl = cp5.addKnob("knob_areaControl",100,2000,300,13,305,50)
     .setLabel("Area")
     .setDecimalPrecision(0)
     .plugTo(parent,"knob_areaControl")
   ;
   
   qualityControl = cp5.addKnob("knob_qualityControl",1,10,4,100,305,50)
     .setLabel("Quality")
     .setDecimalPrecision(0)
     .plugTo(parent,"knob_qualityControl")
   ;
   
   blurControl = cp5.addKnob("knob_blurControl",0,25,0,170,305,50)
     .setLabel("Blur")
     .setDecimalPrecision(0)
     .plugTo(parent,"knob_blurControl")
   ;
   
   posterizeControl = cp5.addKnob("knob_posterizeControl",2,255,2,260,305,50)
     .setLabel("Posterize")
     .setDecimalPrecision(0)
     .plugTo(parent,"knob_posterizeControl")
   ;
   
   Group bgGroup = cp5.addGroup("bgGroup")
     .activateEvent(true)
   ;
   
   meshBGToggle = cp5.addToggle("toggle_meshBG")
      .setPosition(10,380)
      .setSize(90,20)
      .setColorLabel(textColor)
      .setColorBackground(accentColor)
      .plugTo(parent,"toggle_meshBG")
      .setLabel("Mesh BG")
      .toggle()
    ;
     
    meshBGToggle.getCaptionLabel().getStyle().marginTop = -18;
    meshBGToggle.getCaptionLabel().getStyle().marginLeft = 5;
    
    altBGToggle = cp5.addToggle("toggle_altBG")
      .setPosition(116,380)
      .setSize(90,20)
      .setColorLabel(textColor)
      .setColorBackground(accentColor)
      .plugTo(parent,"toggle_altBG")
      .setLabel("Alt BG")
    ;
     
    altBGToggle.getCaptionLabel().getStyle().marginTop = -18;
    altBGToggle.getCaptionLabel().getStyle().marginLeft = 5;
    
    noBGToggle = cp5.addToggle("toggle_noBG")
      .setPosition(222,380)
      .setSize(90,20)
      .setColorLabel(textColor)
      .setColorBackground(accentColor)
      .plugTo(parent,"toggle_noBG")
      .setLabel("No BG")
    ;
     
    noBGToggle.getCaptionLabel().getStyle().marginTop = -18;
    noBGToggle.getCaptionLabel().getStyle().marginLeft = 5;
    
    /* BLEND MODES * UNDER CONSTRUCTION
    
    // Blend Mode Switchboard blend add subtract darkest lightest difference exclusion multiply screen replace
    
    blendModeLabel = cp5.addTextlabel("label_blendMode")
      .setText("BLEND SWITCHBOARD")
      .setPosition(7,410)
    ;
    
    blendBlendToggle = cp5.addToggle("toggle_blendBlend")
      .setPosition(10,430)
      .setSize(50,20)
      .setColorLabel(textColor)
      .setColorBackground(accentColor)
      .plugTo(parent,"toggle_blendBlend")
      .setLabel("BLEND")
    ;
    
    blendBlendToggle.getCaptionLabel().getStyle().marginTop = -18;
    blendBlendToggle.getCaptionLabel().getStyle().marginLeft = 5;
    
    blendAddToggle = cp5.addToggle("toggle_blendAdd")
      .setPosition(60,430)
      .setSize(50,20)
      .setColorLabel(textColor)
      .setColorBackground(accentColor)
      .plugTo(parent,"toggle_blendAdd")
      .setLabel("ADD")
    ;
    
    blendAddToggle.getCaptionLabel().getStyle().marginTop = -18;
    blendAddToggle.getCaptionLabel().getStyle().marginLeft = 5;
    
    blendSubtractToggle = cp5.addToggle("toggle_blendSubtract")
      .setPosition(110,430)
      .setSize(50,20)
      .setColorLabel(textColor)
      .setColorBackground(accentColor)
      .plugTo(parent,"toggle_blendSubtract")
      .setLabel("SUBTRACT")
    ;
    
    blendSubtractToggle.getCaptionLabel().getStyle().marginTop = -18;
    blendSubtractToggle.getCaptionLabel().getStyle().marginLeft = 5;
    
    blendDarkestToggle = cp5.addToggle("toggle_blendDarkest")
      .setPosition(160,430)
      .setSize(50,20)
      .setColorLabel(textColor)
      .setColorBackground(accentColor)
      .plugTo(parent,"toggle_blendDarkest")
      .setLabel("DARKEST")
    ;
    
    blendDarkestToggle.getCaptionLabel().getStyle().marginTop = -18;
    blendDarkestToggle.getCaptionLabel().getStyle().marginLeft = 5;

    blendLightestToggle = cp5.addToggle("toggle_blendLightest")
      .setPosition(210,430)
      .setSize(50,20)
      .setColorLabel(textColor)
      .setColorBackground(accentColor)
      .plugTo(parent,"toggle_blendLightest")
      .setLabel("LIGHTEST")
    ;
    
    blendLightestToggle.getCaptionLabel().getStyle().marginTop = -18;
    blendLightestToggle.getCaptionLabel().getStyle().marginLeft = 5;
    
    blendDifferenceToggle = cp5.addToggle("toggle_blendDifference")
      .setPosition(260,430)
      .setSize(50,20)
      .setColorLabel(textColor)
      .setColorBackground(accentColor)
      .plugTo(parent,"toggle_blendDifference")
      .setLabel("DIFFER...")
    ;
    
    blendDifferenceToggle.getCaptionLabel().getStyle().marginTop = -18;
    blendDifferenceToggle.getCaptionLabel().getStyle().marginLeft = 5;
    
    blendExclusionToggle = cp5.addToggle("toggle_blendExclusion")
      .setPosition(10,450)
      .setSize(50,20)
      .setColorLabel(textColor)
      .setColorBackground(accentColor)
      .plugTo(parent,"toggle_blendExclusion")
      .setLabel("EXCLUSION")
    ;
    
    blendExclusionToggle.getCaptionLabel().getStyle().marginTop = -18;
    blendExclusionToggle.getCaptionLabel().getStyle().marginLeft = 5;
    
    blendMultiplyToggle = cp5.addToggle("toggle_blendMultiply")
      .setPosition(60,450)
      .setSize(50,20)
      .setColorLabel(textColor)
      .setColorBackground(accentColor)
      .plugTo(parent,"toggle_blendMultiply")
      .setLabel("MULTIPLY")
    ;
    
    blendMultiplyToggle.getCaptionLabel().getStyle().marginTop = -18;
    blendMultiplyToggle.getCaptionLabel().getStyle().marginLeft = 5;
    
    blendScreenToggle = cp5.addToggle("toggle_blendScreen")
      .setPosition(110,450)
      .setSize(50,20)
      .setColorLabel(textColor)
      .setColorBackground(accentColor)
      .plugTo(parent,"toggle_blendScreen")
      .setLabel("SCREEN")
    ;
    
    blendScreenToggle.getCaptionLabel().getStyle().marginTop = -18;
    blendScreenToggle.getCaptionLabel().getStyle().marginLeft = 5;
    
    blendReplaceToggle = cp5.addToggle("toggle_blendReplace")
      .setPosition(160,450)
      .setSize(50,20)
      .setColorLabel(textColor)
      .setColorBackground(accentColor)
      .plugTo(parent,"toggle_blendReplace")
      .setLabel("REPLACE")
    ;
    
    blendReplaceToggle.getCaptionLabel().getStyle().marginTop = -18;
    blendReplaceToggle.getCaptionLabel().getStyle().marginLeft = 5;
    
    
    blendHardLightToggle = cp5.addToggle("toggle_blendHardLight")
      .setPosition(210,450)
      .setSize(50,20)
      .setColorLabel(textColor)
      .setColorBackground(accentColor)
      .plugTo(parent,"toggle_blendHardLight")
      .setLabel("H. Light")
    ;
    
    blendHardLightToggle.getCaptionLabel().getStyle().marginTop = -18;
    blendHardLightToggle.getCaptionLabel().getStyle().marginLeft = 5;
    
    blendSoftLightToggle = cp5.addToggle("toggle_blendSoftLight")
      .setPosition(260,450)
      .setSize(50,20)
      .setColorLabel(textColor)
      .setColorBackground(accentColor)
      .plugTo(parent,"toggle_blendSoftLight")
      .setLabel("S. Light")
    ;
    
    blendSoftLightToggle.getCaptionLabel().getStyle().marginTop = -18;
    blendSoftLightToggle.getCaptionLabel().getStyle().marginLeft = 5;

    blendOverlayToggle = cp5.addToggle("toggle_blendOverlay")
      .setPosition(10,470)
      .setSize(50,20)
      .setColorLabel(textColor)
      .setColorBackground(accentColor)
      .plugTo(parent,"toggle_blendOverlay")
      .setLabel("OVERLAY")
    ;
    
    blendOverlayToggle.getCaptionLabel().getStyle().marginTop = -18;
    blendOverlayToggle.getCaptionLabel().getStyle().marginLeft = 5;
    
    blendDodgeToggle = cp5.addToggle("toggle_blendDodge")
      .setPosition(60,470)
      .setSize(50,20)
      .setColorLabel(textColor)
      .setColorBackground(accentColor)
      .plugTo(parent,"toggle_blendDodge")
      .setLabel("DODGE")
    ;
    
    blendDodgeToggle.getCaptionLabel().getStyle().marginTop = -18;
    blendDodgeToggle.getCaptionLabel().getStyle().marginLeft = 5;

    blendBurnToggle = cp5.addToggle("toggle_blendBurn")
      .setPosition(110,470)
      .setSize(50,20)
      .setColorLabel(textColor)
      .setColorBackground(accentColor)
      .plugTo(parent,"toggle_blendBurn")
      .setLabel("Burn")
    ;
    
    blendBurnToggle.getCaptionLabel().getStyle().marginTop = -18;
    blendBurnToggle.getCaptionLabel().getStyle().marginLeft = 5;
    
    blendOffToggle = cp5.addToggle("toggle_blendOff")
      .setPosition(160,470)
      .setSize(50,20)
      .setColorLabel(textColor)
      .setColorBackground(accentColor)
      .plugTo(parent,"toggle_blendOff")
      .setLabel("Off")
      .toggle();
    ;
    
    blendOffToggle.getCaptionLabel().getStyle().marginTop = -18;
    blendOffToggle.getCaptionLabel().getStyle().marginLeft = 5;
    
    */
    
    // Filter Mode Switchboard threshold gray invert posterize blur erode dilate
    
    filterModeLabel = cp5.addTextlabel("label_filterMode")
      .setText("FILTER SWITCHBOARD")
      .setPosition(7,410);
    ;
    
    filterDilateToggle = cp5.addToggle("toggle_filterDilate")
      .setPosition(10,430)
      .setSize(50,20)
      .setColorLabel(textColor)
      .setColorBackground(accentColor)
      .plugTo(parent,"toggle_filterDilate")
      .setLabel("DILATE")
    ;
    
    filterDilateToggle.getCaptionLabel().getStyle().marginTop = -18;
    filterDilateToggle.getCaptionLabel().getStyle().marginLeft = 5;
    
    filterThresholdToggle = cp5.addToggle("toggle_filterThreshold")
      .setPosition(60,430)
      .setSize(50,20)
      .setColorLabel(textColor)
      .setColorBackground(accentColor)
      .plugTo(parent,"toggle_filterThreshold")
      .setLabel("THRES...")
    ;
    
    filterThresholdToggle.getCaptionLabel().getStyle().marginTop = -18;
    filterThresholdToggle.getCaptionLabel().getStyle().marginLeft = 5;
    
    filterGrayToggle = cp5.addToggle("toggle_filterGray")
      .setPosition(110,430)
      .setSize(50,20)
      .setColorLabel(textColor)
      .setColorBackground(accentColor)
      .plugTo(parent,"toggle_filterGray")
      .setLabel("GRAY")
    ;
    
    filterGrayToggle.getCaptionLabel().getStyle().marginTop = -18;
    filterGrayToggle.getCaptionLabel().getStyle().marginLeft = 5;
    
    filterInvertToggle = cp5.addToggle("toggle_filterInvert")
      .setPosition(160,430)
      .setSize(50,20)
      .setColorLabel(textColor)
      .setColorBackground(accentColor)
      .plugTo(parent,"toggle_filterInvert")
      .setLabel("INVERT")
    ;
    
    filterInvertToggle.getCaptionLabel().getStyle().marginTop = -18;
    filterInvertToggle.getCaptionLabel().getStyle().marginLeft = 5;
    
    filterPosterizeToggle = cp5.addToggle("toggle_filterPosterize")
      .setPosition(210,430)
      .setSize(50,20)
      .setColorLabel(textColor)
      .setColorBackground(accentColor)
      .plugTo(parent,"toggle_filterPosterize")
      .setLabel("POSTER...")
    ;
    
    filterPosterizeToggle.getCaptionLabel().getStyle().marginTop = -18;
    filterPosterizeToggle.getCaptionLabel().getStyle().marginLeft = 5;

    filterBlurToggle = cp5.addToggle("toggle_filterBlur")
      .setPosition(260,430)
      .setSize(50,20)
      .setColorLabel(textColor)
      .setColorBackground(accentColor)
      .plugTo(parent,"toggle_filterBlur")
      .setLabel("BLUR")
    ;
    
    filterBlurToggle.getCaptionLabel().getStyle().marginTop = -18;
    filterBlurToggle.getCaptionLabel().getStyle().marginLeft = 5;

    filterErodeToggle = cp5.addToggle("toggle_filterErode")
      .setPosition(10,450)
      .setSize(50,20)
      .setColorLabel(textColor)
      .setColorBackground(accentColor)
      .plugTo(parent,"toggle_filterErode")
      .setLabel("ERRODE")
    ;
    
    filterErodeToggle.getCaptionLabel().getStyle().marginTop = -18;
    filterErodeToggle.getCaptionLabel().getStyle().marginLeft = 5;
    
    filterOffToggle = cp5.addToggle("toggle_filterOff")
      .setPosition(60,450)
      .setSize(50,20)
      .setColorLabel(textColor)
      .setColorBackground(accentColor)
      .plugTo(parent,"toggle_filterOff")
      .setLabel("OFF")
      .toggle();
    ;
    
    filterOffToggle.getCaptionLabel().getStyle().marginTop = -18;
    filterOffToggle.getCaptionLabel().getStyle().marginLeft = 5;

    cp5.addButton("drawMesh")
     .setPosition(10,570)
     .setSize(300,20)
     .setColorLabel(textColor)
     .setColorBackground(accentColor)
     .plugTo(parent,"drawMesh")
     .setLabel("Triangulate!")
   ;
   
cp5.addButton("SavePNG")
      .setPosition(10,600)
      .setSize(60,20)
      .setColorLabel(textColor)
      .setColorBackground(accentColor)
      .plugTo(parent,"SavePNG")
      .setLabel("Write PNG")
   ;
      
   cp5.addButton("SaveJPG")
      .setPosition(90,600)
      .setSize(60,20)
      .setColorLabel(textColor)
      .setColorBackground(accentColor)
      .plugTo(parent,"SaveJPG")
      .setLabel("Write JPG")
    ;
      
    cp5.addButton("SaveTIF")
      .setPosition(170,600)
      .setSize(60,20)
      .setColorLabel(textColor)
      .setColorBackground(accentColor)
      .plugTo(parent,"SaveTIF")
      .setLabel("Write TIF")
    ;
    
    cp5.addButton("SavePDF")
      .setPosition(250,600)
      .setSize(60,20)
      .setColorLabel(textColor)
      .setColorBackground(accentColor)
      .plugTo(parent,"SavePDF")
      .setLabel("Write PDF")
      ;
   
   ta = cp5.addTextarea("txt")
      .setPosition(10,630)
      .setSize(300,85)
      .setLineHeight(14)
      .setColor(textColor)
      .setColorBackground(accentColor)
      .setText("Info Window")
    ;
   
  }
  
  public void draw() {
    
    background(backgroundColor);
    image(toolsBG,0,0);
    
  }
  
  private ControlFrame() {}

  public ControlFrame(Object theParent, int theWidth, int theHeight) {
    parent = theParent;
    w = theWidth;
    h = theHeight;
  }
  
  public ControlP5 control() {
    return cp5;
  }
}

public void controlEvent(ControlEvent theEvent) {
  
  if (theEvent.isGroup()) {
    
    println("got an event from group "+theEvent.getGroup().getName()+", isOpen? "+theEvent.getGroup().isOpen());
    // an event from a group e.g. scrollList
    println(theEvent.group().value()+" from "+theEvent.group());
    
  } else if (theEvent.isController()) { 
    
    print("control event from : "+theEvent.controller().name());
    println(", value : "+theEvent.controller().value());
    
    if(theEvent.controller().name()=="button_chooseSauce") {
      selectInput("Select Some Sauce:", "sauceFileSelect");
    }
    
    if(theEvent.controller().name()=="button_facebookLink") {
      link("https://www.facebook.com/groups/DigitalArtistNexus","_new");
    } 
    
    if(theEvent.controller().name()=="button_chooseMesh") {
      if (meshSauce == null) {
        ta.setText("Select Some Sauce First!"); 
      } else {
        selectInput("Select Some Mesh:", "meshFileSelect");
      }
    } 

    if(theEvent.controller().name()=="button_chooseBG") {
      if (meshSauce == null) {
        ta.setText("Select Some Sauce First!"); 
      } else {
        selectInput("Select A Background Image:", "bgFileSelect");
      }
    }
    
    if(theEvent.controller().name()=="toggle_autoResize") {
      if(theEvent.controller().value()==1) {
        autoResize = true;
        ta.setText("Auto Resize On.");
        
      } else {
        autoResize = false;
        ta.setText("Auto Resize Off.");
        
      }
    }
    
    if(theEvent.controller().name()=="toggle_liveEdit") {
      if(theEvent.controller().value()==1) {
        liveEdit = true;
        ta.setText("Live Edit On.");
        redraw();
      } else {
        liveEdit = false;
        ta.setText("Live Edit Off.");
        
      }
    }
    
    if(theEvent.controller().name()=="toggle_stretchMesh") {
      if(theEvent.controller().value()==1) { 
        meshStretch = true;
        ta.setText("Stretch Mesh On.");
        
      } else {                                 
        meshStretch = false;
        ta.setText("Stretch Mesh Off");
        
      }
    }
    
    if(theEvent.controller().name()=="toggle_meshColorMode") {
      if(theEvent.controller().value()==1) {
        meshColorSource = true;
        ta.setText("Mesh Color Mode On.");
        
      } else {
        meshColorSource = false;
        ta.setText("Mesh Color Mode Off.");
        
      }
    }
    
    if(theEvent.controller().name()=="toggle_flipMode") {
      if(theEvent.controller().value()==1) { 
        flipMode = true;
        ta.setText("Color Flip Mode On.");
        
      } else {                                 
        flipMode = false;
        ta.setText("Color Flip Mode Off.");
        
      }
    }

    if(theEvent.controller().name()=="toggle_CRM") {
      if(theEvent.controller().value()==1) { 
        CrazyRainbowModeOn = true;
        ta.setText("Crazy Rainbow Mode On.");
      } else {                                 
        CrazyRainbowModeOn = false;
        ta.setText("Crazy Rainbow Mode Off.");
      }
    }
    
    if(theEvent.controller().name()=="slider_fillOpacity") {
      fillOpacity = theEvent.controller().value();
      ta.setText("Opacity: " + theEvent.controller().value());
    }
    
    if(theEvent.controller().name()=="slider_strokeSize"){
      edgeSize = theEvent.controller().value();
    }
    
    if(theEvent.controller().name()=="knob_areaControl"){
      area = PApplet.parseInt(theEvent.controller().value());
      ta.setText("Area: " + theEvent.controller().value());
    }
    
    if(theEvent.controller().name()=="knob_qualityControl"){
      qual = PApplet.parseInt(theEvent.controller().value());
      ta.setText("Quality: " + theEvent.controller().value());
    }
    
    if(theEvent.controller().name()=="knob_posterizeControl"){
      setPosterizeValue = PApplet.parseInt(theEvent.controller().value());
      ta.setText("Posterize: " + theEvent.controller().value());
    }
    
    if(theEvent.controller().name()=="knob_blurControl"){
      setBlurValue = PApplet.parseInt(theEvent.controller().value());
      ta.setText("Blur: " + theEvent.controller().value());
    }
    
    if(theEvent.controller().name()=="toggle_meshBG" && theEvent.controller().value() == 1){
      altBGToggle.setValue(false);
      noBGToggle.setValue(false);
      chooseBG = 1;
    }
      
    if(theEvent.controller().name()=="toggle_altBG" && theEvent.controller().value() == 1){
      meshBGToggle.setValue(false);
      noBGToggle.setValue(false);
      chooseBG = 2;
    }
     
    if(theEvent.controller().name()=="toggle_noBG" && theEvent.controller().value() == 1){
      altBGToggle.setValue(false);
      meshBGToggle.setValue(false);
      chooseBG = 0;
    }
    
    /* BLEND MODES * UNDER CONSTRUCTION
    
    if(theEvent.controller().name()=="toggle_blendBlend" && theEvent.controller().value() == 1){
      setBlendMode = 1;
      blendAddToggle.setValue(false);
      blendSubtractToggle.setValue(false);
      blendDarkestToggle.setValue(false);
      blendLightestToggle.setValue(false);
      blendDifferenceToggle.setValue(false);
      blendExclusionToggle.setValue(false);
      blendMultiplyToggle.setValue(false);
      blendScreenToggle.setValue(false);
      blendReplaceToggle.setValue(false);
      blendHardLightToggle.setValue(false);
      blendSoftLightToggle.setValue(false);
      blendOverlayToggle.setValue(false);
      blendDodgeToggle.setValue(false);
      blendBurnToggle.setValue(false);
      blendOffToggle.setValue(false);
    }
    
    if(theEvent.controller().name()=="toggle_blendAdd" && theEvent.controller().value() == 1){
      setBlendMode = 2;
      blendBlendToggle.setValue(false);
      blendSubtractToggle.setValue(false);
      blendDarkestToggle.setValue(false);
      blendLightestToggle.setValue(false);
      blendDifferenceToggle.setValue(false);
      blendExclusionToggle.setValue(false);
      blendMultiplyToggle.setValue(false);
      blendScreenToggle.setValue(false);
      blendReplaceToggle.setValue(false);
      blendHardLightToggle.setValue(false);
      blendSoftLightToggle.setValue(false);
      blendOverlayToggle.setValue(false);
      blendDodgeToggle.setValue(false);
      blendBurnToggle.setValue(false);
      blendOffToggle.setValue(false);
    }
    
    if(theEvent.controller().name()=="toggle_blendSubtract" && theEvent.controller().value() == 1){
      setBlendMode = 3;
      blendAddToggle.setValue(false);
      blendBlendToggle.setValue(false);
      blendDarkestToggle.setValue(false);
      blendLightestToggle.setValue(false);
      blendDifferenceToggle.setValue(false);
      blendExclusionToggle.setValue(false);
      blendMultiplyToggle.setValue(false);
      blendScreenToggle.setValue(false);
      blendReplaceToggle.setValue(false);
      blendHardLightToggle.setValue(false);
      blendSoftLightToggle.setValue(false);
      blendOverlayToggle.setValue(false);
      blendDodgeToggle.setValue(false);
      blendBurnToggle.setValue(false);
      blendOffToggle.setValue(false);
    }
    
    if(theEvent.controller().name()=="toggle_blendDarkest" && theEvent.controller().value() == 1){
      setBlendMode = 4;
      blendAddToggle.setValue(false);
      blendSubtractToggle.setValue(false);
      blendBlendToggle.setValue(false);
      blendLightestToggle.setValue(false);
      blendDifferenceToggle.setValue(false);
      blendExclusionToggle.setValue(false);
      blendMultiplyToggle.setValue(false);
      blendScreenToggle.setValue(false);
      blendReplaceToggle.setValue(false);
      blendHardLightToggle.setValue(false);
      blendSoftLightToggle.setValue(false);
      blendOverlayToggle.setValue(false);
      blendDodgeToggle.setValue(false);
      blendBurnToggle.setValue(false);
      blendOffToggle.setValue(false);
    }
    
    if(theEvent.controller().name()=="toggle_blendLightest" && theEvent.controller().value() == 1){
      setBlendMode = 5;
      blendAddToggle.setValue(false);
      blendSubtractToggle.setValue(false);
      blendDarkestToggle.setValue(false);
      blendBlendToggle.setValue(false);
      blendDifferenceToggle.setValue(false);
      blendExclusionToggle.setValue(false);
      blendMultiplyToggle.setValue(false);
      blendScreenToggle.setValue(false);
      blendReplaceToggle.setValue(false);
      blendHardLightToggle.setValue(false);
      blendSoftLightToggle.setValue(false);
      blendOverlayToggle.setValue(false);
      blendDodgeToggle.setValue(false);
      blendBurnToggle.setValue(false);
      blendOffToggle.setValue(false);
    }
    
    if(theEvent.controller().name()=="toggle_blendDifference" && theEvent.controller().value() == 1){
      setBlendMode = 6;
      blendAddToggle.setValue(false);
      blendSubtractToggle.setValue(false);
      blendDarkestToggle.setValue(false);
      blendLightestToggle.setValue(false);
      blendBlendToggle.setValue(false);
      blendExclusionToggle.setValue(false);
      blendMultiplyToggle.setValue(false);
      blendScreenToggle.setValue(false);
      blendReplaceToggle.setValue(false);
      blendHardLightToggle.setValue(false);
      blendSoftLightToggle.setValue(false);
      blendOverlayToggle.setValue(false);
      blendDodgeToggle.setValue(false);
      blendBurnToggle.setValue(false);
      blendOffToggle.setValue(false);
    }
    
    if(theEvent.controller().name()=="toggle_blendExclusion" && theEvent.controller().value() == 1){
      setBlendMode = 7;
      blendAddToggle.setValue(false);
      blendSubtractToggle.setValue(false);
      blendDarkestToggle.setValue(false);
      blendLightestToggle.setValue(false);
      blendDifferenceToggle.setValue(false);
      blendBlendToggle.setValue(false);
      blendMultiplyToggle.setValue(false);
      blendScreenToggle.setValue(false);
      blendReplaceToggle.setValue(false);
      blendHardLightToggle.setValue(false);
      blendSoftLightToggle.setValue(false);
      blendOverlayToggle.setValue(false);
      blendDodgeToggle.setValue(false);
      blendBurnToggle.setValue(false);
      blendOffToggle.setValue(false);
    }
    
    if(theEvent.controller().name()=="toggle_blendMultiply" && theEvent.controller().value() == 1){
      setBlendMode = 8;
      blendAddToggle.setValue(false);
      blendSubtractToggle.setValue(false);
      blendDarkestToggle.setValue(false);
      blendLightestToggle.setValue(false);
      blendDifferenceToggle.setValue(false);
      blendExclusionToggle.setValue(false);
      blendBlendToggle.setValue(false);
      blendScreenToggle.setValue(false);
      blendReplaceToggle.setValue(false);
      blendHardLightToggle.setValue(false);
      blendSoftLightToggle.setValue(false);
      blendOverlayToggle.setValue(false);
      blendDodgeToggle.setValue(false);
      blendBurnToggle.setValue(false);
      blendOffToggle.setValue(false);
    }

    if(theEvent.controller().name()=="toggle_blendScreen" && theEvent.controller().value() == 1){
      setBlendMode = 9;
      blendAddToggle.setValue(false);
      blendSubtractToggle.setValue(false);
      blendDarkestToggle.setValue(false);
      blendLightestToggle.setValue(false);
      blendDifferenceToggle.setValue(false);
      blendExclusionToggle.setValue(false);
      blendMultiplyToggle.setValue(false);
      blendBlendToggle.setValue(false);
      blendReplaceToggle.setValue(false);
      blendOffToggle.setValue(false);
    }

    if(theEvent.controller().name()=="toggle_blendReplace" && theEvent.controller().value() == 1){
      setBlendMode = 10;
      blendAddToggle.setValue(false);
      blendSubtractToggle.setValue(false);
      blendDarkestToggle.setValue(false);
      blendLightestToggle.setValue(false);
      blendDifferenceToggle.setValue(false);
      blendExclusionToggle.setValue(false);
      blendMultiplyToggle.setValue(false);
      blendScreenToggle.setValue(false);
      blendBlendToggle.setValue(false);
      blendHardLightToggle.setValue(false);
      blendSoftLightToggle.setValue(false);
      blendOverlayToggle.setValue(false);
      blendDodgeToggle.setValue(false);
      blendBurnToggle.setValue(false);
      blendOffToggle.setValue(false);
    }
    
    if(theEvent.controller().name()=="toggle_blendHardLight" && theEvent.controller().value() == 1){
      setBlendMode = 11;
      blendAddToggle.setValue(false);
      blendSubtractToggle.setValue(false);
      blendDarkestToggle.setValue(false);
      blendLightestToggle.setValue(false);
      blendDifferenceToggle.setValue(false);
      blendExclusionToggle.setValue(false);
      blendMultiplyToggle.setValue(false);
      blendScreenToggle.setValue(false);
      blendBlendToggle.setValue(false);
      blendReplaceToggle.setValue(false);
      blendSoftLightToggle.setValue(false);
      blendOverlayToggle.setValue(false);
      blendDodgeToggle.setValue(false);
      blendBurnToggle.setValue(false);
      blendOffToggle.setValue(false);
    }
    
    if(theEvent.controller().name()=="toggle_blendSoftLight" && theEvent.controller().value() == 1){
      setBlendMode = 12;
      blendAddToggle.setValue(false);
      blendSubtractToggle.setValue(false);
      blendDarkestToggle.setValue(false);
      blendLightestToggle.setValue(false);
      blendDifferenceToggle.setValue(false);
      blendExclusionToggle.setValue(false);
      blendMultiplyToggle.setValue(false);
      blendScreenToggle.setValue(false);
      blendBlendToggle.setValue(false);
      blendHardLightToggle.setValue(false);
      blendReplaceToggle.setValue(false);
      blendOverlayToggle.setValue(false);
      blendDodgeToggle.setValue(false);
      blendBurnToggle.setValue(false);
      blendOffToggle.setValue(false);
    }
    
    if(theEvent.controller().name()=="toggle_blendOverlay" && theEvent.controller().value() == 1){
      setBlendMode = 13;
      blendAddToggle.setValue(false);
      blendSubtractToggle.setValue(false);
      blendDarkestToggle.setValue(false);
      blendLightestToggle.setValue(false);
      blendDifferenceToggle.setValue(false);
      blendExclusionToggle.setValue(false);
      blendMultiplyToggle.setValue(false);
      blendScreenToggle.setValue(false);
      blendBlendToggle.setValue(false);
      blendHardLightToggle.setValue(false);
      blendSoftLightToggle.setValue(false);
      blendReplaceToggle.setValue(false);
      blendDodgeToggle.setValue(false);
      blendBurnToggle.setValue(false);
      blendOffToggle.setValue(false);
    }
    
    if(theEvent.controller().name()=="toggle_blendDodge" && theEvent.controller().value() == 1){
      setBlendMode = 14;
      blendAddToggle.setValue(false);
      blendSubtractToggle.setValue(false);
      blendDarkestToggle.setValue(false);
      blendLightestToggle.setValue(false);
      blendDifferenceToggle.setValue(false);
      blendExclusionToggle.setValue(false);
      blendMultiplyToggle.setValue(false);
      blendScreenToggle.setValue(false);
      blendBlendToggle.setValue(false);
      blendHardLightToggle.setValue(false);
      blendSoftLightToggle.setValue(false);
      blendOverlayToggle.setValue(false);
      blendReplaceToggle.setValue(false);
      blendBurnToggle.setValue(false);
      blendOffToggle.setValue(false);
    }
    
    if(theEvent.controller().name()=="toggle_blendBurn" && theEvent.controller().value() == 1){
      setBlendMode = 15;
      blendAddToggle.setValue(false);
      blendSubtractToggle.setValue(false);
      blendDarkestToggle.setValue(false);
      blendLightestToggle.setValue(false);
      blendDifferenceToggle.setValue(false);
      blendExclusionToggle.setValue(false);
      blendMultiplyToggle.setValue(false);
      blendScreenToggle.setValue(false);
      blendBlendToggle.setValue(false);
      blendHardLightToggle.setValue(false);
      blendSoftLightToggle.setValue(false);
      blendOverlayToggle.setValue(false);
      blendDodgeToggle.setValue(false);
      blendReplaceToggle.setValue(false);
      blendOffToggle.setValue(false);
    }
    
    if(theEvent.controller().name()=="toggle_blendOff" && theEvent.controller().value() == 1){
      setBlendMode = 0;
      blendAddToggle.setValue(false);
      blendSubtractToggle.setValue(false);
      blendDarkestToggle.setValue(false);
      blendLightestToggle.setValue(false);
      blendDifferenceToggle.setValue(false);
      blendExclusionToggle.setValue(false);
      blendMultiplyToggle.setValue(false);
      blendScreenToggle.setValue(false);
      blendBlendToggle.setValue(false);
      blendHardLightToggle.setValue(false);
      blendSoftLightToggle.setValue(false);
      blendOverlayToggle.setValue(false);
      blendDodgeToggle.setValue(false);
      blendReplaceToggle.setValue(false);
      blendBurnToggle.setValue(false);
    }
    
    */
    
    // Filters

    if(theEvent.controller().name()=="toggle_filterThreshold" && theEvent.controller().value() == 1){
      setFilterMode = 1;
      filterGrayToggle.setValue(false);
      filterInvertToggle.setValue(false);
      filterPosterizeToggle.setValue(false);
      filterBlurToggle.setValue(false);
      filterErodeToggle.setValue(false);
      filterDilateToggle.setValue(false);
      filterOffToggle.setValue(false);
    }
    
    if(theEvent.controller().name()=="toggle_filterGray" && theEvent.controller().value() == 1){
      setFilterMode = 2;
      filterThresholdToggle.setValue(false);
      filterInvertToggle.setValue(false);
      filterPosterizeToggle.setValue(false);
      filterBlurToggle.setValue(false);
      filterErodeToggle.setValue(false);
      filterDilateToggle.setValue(false);
      filterOffToggle.setValue(false);
    }
    
    if(theEvent.controller().name()=="toggle_filterInvert" && theEvent.controller().value() == 1){
      setFilterMode = 3;
      filterGrayToggle.setValue(false);
      filterThresholdToggle.setValue(false);
      filterPosterizeToggle.setValue(false);
      filterBlurToggle.setValue(false);
      filterErodeToggle.setValue(false);
      filterDilateToggle.setValue(false);
      filterOffToggle.setValue(false);
    }
    
    if(theEvent.controller().name()=="toggle_filterPosterize" && theEvent.controller().value() == 1){
      setFilterMode = 4;
      filterGrayToggle.setValue(false);
      filterInvertToggle.setValue(false);
      filterThresholdToggle.setValue(false);
      filterBlurToggle.setValue(false);
      filterErodeToggle.setValue(false);
      filterDilateToggle.setValue(false);
      filterOffToggle.setValue(false);
    }
    
    if(theEvent.controller().name()=="toggle_filterBlur" && theEvent.controller().value() == 1){
      setFilterMode = 5;
      filterGrayToggle.setValue(false);
      filterInvertToggle.setValue(false);
      filterPosterizeToggle.setValue(false);
      filterThresholdToggle.setValue(false);
      filterErodeToggle.setValue(false);
      filterDilateToggle.setValue(false);
      filterOffToggle.setValue(false);
      liveEditToggle.setValue(false);
    }
    
    if(theEvent.controller().name()=="toggle_filterErode" && theEvent.controller().value() == 1){
      setFilterMode = 6;
      filterGrayToggle.setValue(false);
      filterInvertToggle.setValue(false);
      filterPosterizeToggle.setValue(false);
      filterBlurToggle.setValue(false);
      filterThresholdToggle.setValue(false);
      filterDilateToggle.setValue(false);
      filterOffToggle.setValue(false);
    }
    
    if(theEvent.controller().name()=="toggle_filterDilate" && theEvent.controller().value() == 1){
      setFilterMode = 7;
      filterGrayToggle.setValue(false);
      filterInvertToggle.setValue(false);
      filterPosterizeToggle.setValue(false);
      filterBlurToggle.setValue(false);
      filterErodeToggle.setValue(false);
      filterThresholdToggle.setValue(false);
    }

    if(theEvent.controller().name()=="toggle_filterOff" && theEvent.controller().value() == 1){
      setFilterMode = 0;
      filterGrayToggle.setValue(false);
      filterInvertToggle.setValue(false);
      filterPosterizeToggle.setValue(false);
      filterBlurToggle.setValue(false);
      filterErodeToggle.setValue(false);
      filterThresholdToggle.setValue(false);
    }
    
    if(theEvent.controller().name()=="drawMesh") {
      clear();
      redraw();
    }
    
    if(theEvent.controller().name()=="SavePNG") {
      if (meshSauce == null) {
        ta.setText("Select Some Sauce First!");
      } else {
        if (chooseBG == 0) {
          saveImage = 5;
          redraw();
        } else {
          saveImage = 1;
          redraw();
        }
        ta.setText("File Saved In Renders Folder");
      }
    }
    
    if(theEvent.controller().name()=="SaveJPG") {
      if (meshSauce == null) {
        ta.setText("Select Some Sauce First!");
      } else {
        saveImage = 2;
        redraw();
        ta.setText("File Saved In Renders Folder");
      }
    }
    
    if(theEvent.controller().name()=="SaveTIF") {
      if (meshSauce == null) {
        ta.setText("Select Some Sauce First!");
      } else {
        saveImage = 3;
        redraw();
        ta.setText("File Saved In Renders Folder");
      }
    }
    
    if(theEvent.controller().name()=="SavePDF") {
      if (meshSauce == null) {
        ta.setText("Select Some Sauce First!");
      } else {
        saveImage = 4;
        redraw();
        ta.setText("File Saved In Renders Folder");
      }
    }
   
  }  
}

// File Selection
public void sauceFileSelect(File selection) {
  
  if (selection == null) 
  {
    ta.setColor(color(255));
    ta.setText("Status." + "\n" + "Nothing selected, selection was cancelled.");
  } 
  
  else {
    
    if (selection.getName().endsWith("jpg") || selection.getName().endsWith("jpeg") || selection.getName().endsWith("png") || selection.getName().endsWith("gif") || selection.getName().endsWith("tga") ) {

      PImage checksauce = loadImage(selection.getAbsolutePath()); 
      // Check if loaded image is valid if invalid should return null or width/height -1
      if (checksauce != null && checksauce.width > 0 && checksauce.height > 0) { 
        
        sauce = checksauce.get();

        String Scaled = ""; 
        String extension = "";

        int q = selection.getAbsolutePath().lastIndexOf('.');
        int p = Math.max(selection.getAbsolutePath().lastIndexOf('/'), selection.getAbsolutePath().lastIndexOf('\\'));

        if (q > p) {
          extension = selection.getAbsolutePath().substring(q+1);
        }

        if (autoResize == true) {
          
          //check image is 60px less than the display
          if (sauce.width + 60 > displayWidth || sauce.height + 60 > displayHeight) {
          
            float ratio = PApplet.parseFloat(sauce.width)/PApplet.parseFloat(sauce.height);
            println(ratio);
            int targetHeight = 0;
            int targetWidth = 0;

            if (sauce.width + 60 > displayWidth)
            {
              targetHeight = PApplet.parseInt((displayWidth- 60.0f)/ratio);  
              targetWidth = displayWidth - 60;
            }
            if (sauce.height + 60 > displayHeight)
            {
              targetWidth = PApplet.parseInt((displayHeight - 60.0f) * ratio);  
              targetHeight = displayHeight - 60;
            }

            PGraphics scaledImage = createGraphics(targetWidth, targetHeight);

            scaledImage.beginDraw();
            scaledImage.background(0, 0, 0, 0);
            scaledImage.image(sauce, 0, 0, targetWidth, targetHeight);
            scaledImage.endDraw();

            //println(extension);
            //println(selection.getAbsolutePath().substring(0,q));     

            scaledImage.save("data/tmp/sauce_scaled." + extension);
            sauce = loadImage("data/tmp/sauce_scaled." + extension);
            Scaled = (" Original image was too large for your display - scaled to fit and saved as data/tmp/sauce_scaled." + extension);
          }// end if sauce is bigger than display
          
        }// end if autoResize
        
        //println(selection.getAbsolutePath());
        
        sauce_scaled = sauce.get();
        
        // size the window and show the image
        size(sauce.width + widthInsets, sauce.height + heightInsets);
        frame.setSize(sauce.width + widthInsets, sauce.height + heightInsets);     

        
        ta.setText("Success." + "\n" + "Image file is loaded." + Scaled);
      } 
      else {
        
        ta.setText("Error." + "\n" + "File chosen is not a valid image file.");
      }
    } else {
      
      ta.setText("Error." + "\n" + "Please choose an image file. (JPEG, JPG, PNG, TGA or GIF).");
    }
  }
}

public void meshFileSelect(File selection) {
  if (selection == null) {
    ta.setColor(color(255));
    ta.setText("Status." + "\n" + "Nothing selected, selection was cancelled.");
  } else {
    if (selection.getName().endsWith("jpg") || selection.getName().endsWith("jpeg") || selection.getName().endsWith("png") || selection.getName().endsWith("gif") || selection.getName().endsWith("tga") ) {
      PImage checkmesh = loadImage(selection.getAbsolutePath()); 
      tmpMesh = checkmesh.get();
      tmpMesh.save("data/tmp/tmpMesh.png");
      mesh = checkmesh.get();
    } 
  }
}

public void bgFileSelect(File selection) {
  if (selection == null) {
    ta.setColor(color(255));
    ta.setText("Status." + "\n" + "Nothing selected, selection was cancelled.");
  } else {
    if (selection.getName().endsWith("jpg") || selection.getName().endsWith("jpeg") || selection.getName().endsWith("png") || selection.getName().endsWith("gif") || selection.getName().endsWith("tga") ) {
      PImage checkbg = loadImage(selection.getAbsolutePath()); 
      bgImage = checkbg.get();
      bgImage.save("data/tmp/bg_tmp.png");
      bgImageOrig = loadImage("data/tmp/bg_tmp.png");
    } 
  }
}

// Convert Toggles To Radios

public void setToggles() {

  // Background Selection
  // Blend Switchboard
  // Filter Switchboard

}

// Key Control
public void keyPressed() {
  // The Keys
  if (keyCode == 91 && winKeyL == false) winKeyL = true;
  if (keyCode == 92 && winKeyR == false) winKeyR = true;
  if (keyCode == 157 && comKey == false) comKey = true;
  if (keyCode == 17 && ctrlKey == false) ctrlKey = true;
  if (PApplet.parseChar(keyCode) == 'S' && sKey == false) sKey = true;
  if (PApplet.parseChar(keyCode) == 'M' && mKey == false) mKey = true;
  if (PApplet.parseChar(keyCode) == 'B' && bKey == false) bKey = true;
  if (ctrlKey && sKey) {
    selectInput("Select Some Sauce:", "sauceFileSelect"); 
    ctrlKey = false;
    sKey = false;
  }
  if (ctrlKey && mKey) {
    selectInput("Select Some Mesh:", "meshFileSelect"); 
    ctrlKey = false;
    sKey = false;
  }
  if (ctrlKey && bKey) {
    selectInput("Select A Background:", "bgFileSelect"); 
    ctrlKey = false;
    bKey = false;
  }
}

public void keyReleased() {
  if (winKeyL == true) winKeyL = false;
  if (winKeyR == true) winKeyR = false;
  if (comKey == true) comKey = false;
  if (ctrlKey == true) ctrlKey = false;
  if (sKey == true) sKey = false;
  if (mKey == true) mKey = false;
  if (bKey == true) mKey = false;
}
/* 
  A custom refactoring of Triangulate library by Florian Jennet
  Only minor changes to adapt it to my coding tastes. Not much interesting for anyone else, I think. : )
*/ 






/*
    CircumCircle
    Calculates if a point (xp,yp) is inside the circumcircle made up of the points (x1,y1), (x2,y2), (x3,y3)
    The circumcircle centre is returned in (xc,yc) and the radius r. A point on the edge is inside the circumcircle
*/

public static class CircumCircle 
{ 
    public static boolean circumCircle(PVector p, Triangle t, PVector circle) 
    {
        float m1, m2, mx1, mx2, my1, my2;
        float dx, dy, rsqr, drsqr;
        
        /* Check for coincident points */
        if (abs(t.p1.y-t.p2.y) < EPSILON && abs(t.p2.y-t.p3.y) < EPSILON) {
          //println("CircumCircle: Points are coincident.");
          return false;
        }
    
        if (abs(t.p2.y-t.p1.y) < EPSILON) {
          m2 = - (t.p3.x-t.p2.x) / (t.p3.y-t.p2.y);
          mx2 = (t.p2.x + t.p3.x) * .5f;
          my2 = (t.p2.y + t.p3.y) * .5f;
          circle.x = (t.p2.x + t.p1.x) * .5f;
          circle.y = m2 * (circle.x - mx2) + my2;
        }
        else if (abs(t.p3.y-t.p2.y) < EPSILON) {
          m1 = - (t.p2.x-t.p1.x) / (t.p2.y-t.p1.y);
          mx1 = (t.p1.x + t.p2.x) * .5f;
          my1 = (t.p1.y + t.p2.y) * .5f;
          circle.x = (t.p3.x + t.p2.x) *.5f;
          circle.y = m1 * (circle.x - mx1) + my1;  
        }
        else {
          m1 = - (t.p2.x-t.p1.x) / (t.p2.y-t.p1.y);
          m2 = - (t.p3.x-t.p2.x) / (t.p3.y-t.p2.y);
          mx1 = (t.p1.x + t.p2.x) * .5f;
          mx2 = (t.p2.x + t.p3.x) * .5f;
          my1 = (t.p1.y + t.p2.y) * .5f;
          my2 = (t.p2.y + t.p3.y) * .5f;
          circle.x = (m1 * mx1 - m2 * mx2 + my2 - my1) / (m1 - m2);
          circle.y = m1 * (circle.x - mx1) + my1;
        }
  
        dx = t.p2.x - circle.x;
        dy = t.p2.y - circle.y;
        rsqr = dx*dx + dy*dy;
        circle.z = sqrt(rsqr);
        
        dx = p.x - circle.x;
        dy = p.y - circle.y;
        drsqr = dx*dx + dy*dy;
      
        return drsqr <= rsqr;
    }
}

//Calculates the intersection point between two line segments
//Port of Paul Bourke's C implementation of a basic algebra method

static class LineIntersector {
  
  //Epsilon value to perform accurate floating-point arithmetics
  static final float e = 1e-5f;

  //Check intersection and calculates intersection point, storing it in a reference passed to the method
  public static boolean intersect (float a_x1, float a_y1, float a_x2, float a_y2, 
                            float b_x1, float b_y1, float b_x2, float b_y2, 
                            PVector p) 
  { 
    //Check if lines are parallel
    float d  = ( (b_y2 - b_y1) * (a_x2 - a_x1) ) - ( (b_x2 - b_x1) * (a_y2 - a_y1) );
    if ( abs(d)<e ) return false;    
    
    //Check if lines intersect
    float na, nb, ma, mb;
    na = ( (b_x2 - b_x1) * (a_y1 - b_y1) ) - ( (b_y2 - b_y1) * (a_x1 - b_x1) );
    nb = ( (a_x2 - a_x1) * (a_y1 - b_y1) ) - ( (a_y2 - a_y1) * (a_x1 - b_x1) );
    ma = na/d;
    mb = nb/d;
    if ( ma<0 || ma>1 || mb<0 || mb>1) return false;
    p.x = a_x1 + ( ma * (a_x2 - a_x1));
    p.y = a_y1 + ( ma * (a_y2 - a_y1));
    return true;
  }

  //We know both lines intersect, so don't check anything, only calculate the intersection point
  public static PVector simpleIntersect (float a_x1, float a_y1, float a_x2, float a_y2, 
                                  float b_x1, float b_y1, float b_x2, float b_y2) 
  { 
    float 
    na = ( (b_x2 - b_x1) * (a_y1 - b_y1) ) - ( (b_y2 - b_y1) * (a_x1 - b_x1) ),
    d  = ( (b_y2 - b_y1) * (a_x2 - a_x1) ) - ( (b_x2 - b_x1) * (a_y2 - a_y1) ),
    ma = na/d;
    return new PVector (a_x1 + ( ma * (a_x2 - a_x1)), a_y1 + ( ma * (a_y2 - a_y1)));
  }
}


class Triangle 
{
    PVector p1, p2, p3;
  
    Triangle()  { p1 = p2 = p3 = null; }
  
    Triangle(PVector p1, PVector p2, PVector p3) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }
  
    Triangle(float x1, float y1, float x2, float y2, float x3, float y3) 
    {
        p1 = new PVector(x1, y1);
        p2 = new PVector(x2, y2);
        p3 = new PVector(x3, y3);
    } 
    
    public PVector center(){
        return LineIntersector.simpleIntersect(p1.x, p1.y, (p2.x + p3.x)*.5f, (p2.y + p3.y)*.5f, p2.x, p2.y, (p3.x + p1.x)*.5f, (p3.y + p1.y)*.5f);  
    }
}


/*
 *  ported from p bourke's triangulate.c
 *  http://astronomy.swin.edu.au/~pbourke/modelling/triangulate/
 *  fjenett, 20th february 2005, offenbach-germany.
 *  contact: http://www.florianjenett.de/
 */

class Triangulator 
{

    class XComparator implements Comparator<PVector> 
    {   
        public int compare(PVector p1, PVector p2) 
        {
            if      (p1.x < p2.x) return -1;
            else if (p1.x > p2.x) return  1;
                                  return  0;     
        }
    }

   class Edge 
   {
        PVector p1, p2;
      
        Edge() { p1 = p2 = null; }
      
        Edge(PVector p1, PVector p2) 
        {
            this.p1 = p1;
            this.p2 = p2;
        }
    }

    public boolean sharedVertex(Triangle t1, Triangle t2) {
        return t1.p1 == t2.p2 || t1.p1 == t2.p2 || t1.p1 == t2.p3 ||
               t1.p2 == t2.p1 || t1.p2 == t2.p2 || t1.p2 == t2.p3 || 
               t1.p3 == t2.p1 || t1.p3 == t2.p2 || t1.p3 == t2.p3;
    }


    /*
      Triangulation subroutine
      Takes as input vertices (PVectors) in ArrayList pxyz
      Returned is a list of triangular faces in the ArrayList triangles 
      These triangles are arranged in a consistent clockwise order.
    */   
    public void triangulate(List<PVector> pxyz, List<Triangle> triangles) 
    { 
      // sort vertex array in increasing x values
      Collections.sort(pxyz, new XComparator());
          
      // Find the maximum and minimum vertex bounds. This is to allow calculation of the bounding triangle
      float 
      xmin = pxyz.get(0).x,
      ymin = pxyz.get(0).y,
      xmax = xmin,
      ymax = ymin;
        
      for (PVector p : pxyz) {
        if (p.x < xmin) xmin = p.x;
        else if (p.x > xmax) xmax = p.x;
        if (p.y < ymin) ymin = p.y;
        else if (p.y > ymax) ymax = p.y;
      }
      
      float 
      dx = xmax - xmin,
      dy = ymax - ymin,
      dmax = dx > dy ? dx : dy,
      two_dmax = dmax*2,
      xmid = (xmax+xmin) * .5f,
      ymid = (ymax+ymin) * .5f;
    
      HashSet<Triangle> complete = new HashSet<Triangle>(); // for complete Triangles
  
      /*
        Set up the supertriangle
        This is a triangle which encompasses all the sample points.
        The supertriangle coordinates are added to the end of the
        vertex list. The supertriangle is the first triangle in
        the triangle list.
      */
      Triangle superTriangle = new Triangle(xmid-two_dmax, ymid-dmax, xmid, ymid+two_dmax, xmid+two_dmax, ymid-dmax);
      triangles.add(superTriangle);
      
      //Include each point one at a time into the existing mesh
      ArrayList<Edge> edges = new ArrayList<Edge>();
      int ts;
      PVector circle;
      boolean inside;
  
      for (PVector p : pxyz) {
        edges.clear();
        
        //Set up the edge buffer. If the point (xp,yp) lies inside the circumcircle then the three edges of that triangle are added to the edge buffer and that triangle is removed.
        circle = new PVector();        
  
        for (int j = triangles.size()-1; j >= 0; j--) 
        {     
          Triangle t = triangles.get(j);
          if (complete.contains(t)) continue;
            
          inside = CircumCircle.circumCircle(p, t, circle);
          
          if (circle.x+circle.z < p.x) complete.add(t);
          if (inside) 
          {
              edges.add(new Edge(t.p1, t.p2));
              edges.add(new Edge(t.p2, t.p3));
              edges.add(new Edge(t.p3, t.p1));
              triangles.remove(j);
          }             
        }
  
        // Tag multiple edges. Note: if all triangles are specified anticlockwise then all interior edges are opposite pointing in direction.
        int eL = edges.size()-1, eL_= edges.size();
        Edge e1 = new Edge(), e2 = new Edge();
        
        for (int j=0; j<eL; e1= edges.get(j++)) for (int k=j+1; k<eL_; e2 = edges.get(k++)) 
            if (e1.p1 == e2.p2 && e1.p2 == e2.p1) e1.p1 = e1.p2 = e2.p1 = e2.p2 = null;
            
        //Form new triangles for the current point. Skipping over any tagged edges. All edges are arranged in clockwise order.
        for (Edge e : edges) {
          if (e.p1 == null || e.p2 == null) continue;
          triangles.add(new Triangle(e.p1, e.p2, p));
        }    
      }
        
      //Remove triangles with supertriangle vertices
      for (int i = triangles.size()-1; i >= 0; i--) if (sharedVertex(triangles.get(i), superTriangle)) triangles.remove(i);
    }
    
     /*
      Triangulation subroutine
      Takes as input vertices (PVectors) in ArrayList pxyz
      Returned is a list of triangular faces in the ArrayList triangles 
      These triangles are arranged in a consistent clockwise order.
    */   
    public ArrayList<Triangle> triangulate(PVector[] vertices) 
    { 
      int len = vertices.length;
      
      // sort vertex array in increasing x values
      Arrays.sort(vertices, new XComparator());
          
      // Find the maximum and minimum vertex bounds. This is to allow calculation of the bounding triangle
      float 
      xmin = vertices[0].x,
      ymin = vertices[0].y,
      xmax = xmin,
      ymax = ymin;
        
      for (int i=0; i<len; i++) {
        if      (vertices[i].x < xmin) xmin = vertices[i].x;
        else if (vertices[i].x > xmax) xmax = vertices[i].x;
        if      (vertices[i].y < ymin) ymin = vertices[i].y;
        else if (vertices[i].y > ymax) ymax = vertices[i].y;
      }
      
      float 
      dx = xmax - xmin,
      dy = ymax - ymin,
      dmax = dx > dy ? dx : dy,
      two_dmax = dmax*2,
      xmid = (xmax+xmin) * .5f,
      ymid = (ymax+ymin) * .5f;
    
      ArrayList<Triangle> triangles = new ArrayList<Triangle>(); // for the Triangles
      HashSet<Triangle> complete = new HashSet<Triangle>(); // for complete Triangles
  
      /*
        Set up the supertriangle
        This is a triangle which encompasses all the sample points.
        The supertriangle coordinates are added to the end of the
        vertex list. The supertriangle is the first triangle in
        the triangle list.
      */
      Triangle superTriangle = new Triangle(xmid-two_dmax, ymid-dmax, xmid, ymid+two_dmax, xmid+two_dmax, ymid-dmax);
      triangles.add(superTriangle);
      
      //Include each point one at a time into the existing mesh
      ArrayList<Edge> edges = new ArrayList<Edge>();
      int ts;
      PVector circle;
      boolean inside;
  
      for (int v=0; v<len; v++) {
        edges.clear();
        
        //Set up the edge buffer. If the point (xp,yp) lies inside the circumcircle then the three edges of that triangle are added to the edge buffer and that triangle is removed.
        circle = new PVector();        
  
        for (int j = triangles.size()-1; j >= 0; j--) 
        {     
          Triangle t = triangles.get(j);
          if (complete.contains(t)) continue;
            
          inside = CircumCircle.circumCircle(vertices[v], t, circle);
          
          if (circle.x+circle.z < vertices[v].x) complete.add(t);
          if (inside) 
          {
              edges.add(new Edge(t.p1, t.p2));
              edges.add(new Edge(t.p2, t.p3));
              edges.add(new Edge(t.p3, t.p1));
              triangles.remove(j);
          }             
        }
  
        // Tag multiple edges. Note: if all triangles are specified anticlockwise then all interior edges are opposite pointing in direction.
        int eL = edges.size()-1, eL_= edges.size();
        Edge e1 = new Edge(), e2 = new Edge();
        
        for (int j=0; j<eL; e1= edges.get(j++)) for (int k=j+1; k<eL_; e2 = edges.get(k++)) 
            if (e1.p1 == e2.p2 && e1.p2 == e2.p1) e1.p1 = e1.p2 = e2.p1 = e2.p2 = null;
            
        //Form new triangles for the current point. Skipping over any tagged edges. All edges are arranged in clockwise order.
        for (Edge e : edges) {
          if (e.p1 == null || e.p2 == null) continue;
          triangles.add(new Triangle(e.p1, e.p2, vertices[v]));
        }    
      }
        
      //Remove triangles with supertriangle vertices
      for (int i = triangles.size()-1; i >= 0; i--) if (sharedVertex(triangles.get(i), superTriangle)) triangles.remove(i);
  
      return triangles;
    }
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "TriangulateDF" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
