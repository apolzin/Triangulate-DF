/* 

███████╗  █████╗         ██████╗  ███████╗ ██╗   ██╗
██╔════╝ ██╔══██╗        ██╔══██╗ ██╔════╝ ██║   ██║
██║      ███████║ █████╗ ██║  ██║ █████╗   ██║   ██║
██║      ██╔══██║ ╚════╝ ██║  ██║ ██╔══╝   ╚██╗ ██╔╝
███████╗ ██║  ██║        ██████╔╝ ███████╗  ╚████╔╝ 
 ╚═════╝ ╚═╝  ╚═╝        ╚═════╝  ╚══════╝   ╚═══╝  

Triangulate v1.0 Delaunay Edition, Chaos Astrum (c) 2014 (GNU GPL 3.0 | CC BY-SA 3.0)

Developed by DPLockheart.

https://www.facebook.com/pages/Chaos-Astrum/259067527634739
https://www.facebook.com/groups/DigitalArtistNexus

Original code by Ale González. 

Special thanks to Cliff Pottberg, Rob Mac, Adam Tuerff and the Glitch Artist Collective 
community for their contributions and support!
  
Just run and Delaunay!

*/

void setup() {
  
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

void draw(){
    
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

void begin(){
  ArrayList<PVector> vertices = new ArrayList<PVector>();
  EdgeDetector.extractPoints(vertices, meshSauce, EdgeDetector.SOBEL, area, qual);
  
  for (float i = 0, h = 0, v = 0; i<=1 ; i+=.05, h = W*i, v = H*i) {
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
      colors[i] = ( selectedsauce.get(int(c.y), int(c.x)) != 0 ) ? selectedsauce.get(int(c.y), int(c.x)) : selectedsauce.get(int(c.y/2), int(c.x/2));
    } else {
      colors[i] = selectedsauce.get(int(c.x), int(c.y));
    }
  }
  
  buildMesh();
  
}

// Util function to prune triangles with vertices out of bounds  
boolean vertexOutside(PVector v) { return v.x < 0 || v.x > width || v.y < 0 || v.y > height; }  

color randomColor() {
  return color(random(crmRedMin,crmRedMax),random(crmGreenMin,crmGreenMax),random(crmBlueMin,crmBlueMax),random(crmAlphaMin,crmAlphaMax));
}

void buildMesh() {
  Triangle t = new Triangle();
  color edge = edgePicker.getColorValue();
  color fill = fillPicker.getColorValue();
  
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
      color rand = randomColor();
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
