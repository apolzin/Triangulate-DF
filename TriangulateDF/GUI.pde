// Build The Frame
ControlFrame addControlFrame(String theName, int theWidth, int theHeight, int theX, int theY) {
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

void controlEvent(ControlEvent theEvent) {
  
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
      area = int(theEvent.controller().value());
      ta.setText("Area: " + theEvent.controller().value());
    }
    
    if(theEvent.controller().name()=="knob_qualityControl"){
      qual = int(theEvent.controller().value());
      ta.setText("Quality: " + theEvent.controller().value());
    }
    
    if(theEvent.controller().name()=="knob_posterizeControl"){
      setPosterizeValue = int(theEvent.controller().value());
      ta.setText("Posterize: " + theEvent.controller().value());
    }
    
    if(theEvent.controller().name()=="knob_blurControl"){
      setBlurValue = int(theEvent.controller().value());
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
void sauceFileSelect(File selection) {
  
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
          
            float ratio = float(sauce.width)/float(sauce.height);
            println(ratio);
            int targetHeight = 0;
            int targetWidth = 0;

            if (sauce.width + 60 > displayWidth)
            {
              targetHeight = int((displayWidth- 60.0)/ratio);  
              targetWidth = displayWidth - 60;
            }
            if (sauce.height + 60 > displayHeight)
            {
              targetWidth = int((displayHeight - 60.0) * ratio);  
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

void meshFileSelect(File selection) {
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

void bgFileSelect(File selection) {
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

void setToggles() {

  // Background Selection
  // Blend Switchboard
  // Filter Switchboard

}

// Key Control
void keyPressed() {
  // The Keys
  if (keyCode == 91 && winKeyL == false) winKeyL = true;
  if (keyCode == 92 && winKeyR == false) winKeyR = true;
  if (keyCode == 157 && comKey == false) comKey = true;
  if (keyCode == 17 && ctrlKey == false) ctrlKey = true;
  if (char(keyCode) == 'S' && sKey == false) sKey = true;
  if (char(keyCode) == 'M' && mKey == false) mKey = true;
  if (char(keyCode) == 'B' && bKey == false) bKey = true;
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

void keyReleased() {
  if (winKeyL == true) winKeyL = false;
  if (winKeyR == true) winKeyR = false;
  if (comKey == true) comKey = false;
  if (ctrlKey == true) ctrlKey = false;
  if (sKey == true) sKey = false;
  if (mKey == true) mKey = false;
  if (bKey == true) mKey = false;
}
