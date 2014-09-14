import controlP5.*;
import processing.pdf.*;
import java.util.List;
import java.util.LinkedList;
import java.awt.Frame;

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
color textColor = color(255,255,255);
color accentColor = color(255,0,50);
color backgroundColor = color(22,22,22);

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
color edge = color(0,0,0,0); // Default 0,0,0,0
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
