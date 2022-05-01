package com.example.we_care;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.util.Log;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.tensorflow.lite.Interpreter;
import org.tensorflow.lite.gpu.GpuDelegate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public  class Face_recognition {

    //import interpreter
    private Interpreter interpreter;
    //define input size model
    private int INPUT_SIZE;
    //define height and width of frame
    private int height=0;
    private int width=0;
    //define GPUdelegate
    private GpuDelegate gpuDelegate=null;
    //define CascadeClassifer
    private CascadeClassifier cascadeClassifier;
    public static String Val="";

    Face_recognition(AssetManager assetManager, Context context ,String modelpath,int input_size ) throws IOException {

        //get inputsize
        INPUT_SIZE=input_size;
        //start GPU in the intrpreter
        Interpreter.Options options = new Interpreter.Options();
        gpuDelegate=new GpuDelegate();

        //load model
        //Add some threads

        options.setNumThreads(4);
        interpreter=new Interpreter(loadmodel(assetManager,modelpath),options);

        //when model is loaded successfully , load Face recognition class

        Log.d("Face_recognition","Model is loaded successfuly");

        //Load haarCascade model
        try {
            //define input stream for reading haarcascade file
            InputStream inputStream = context.getResources().openRawResource(R.raw.haarcascade_frontalface_alt);
            //create new folder to save classifier
            File cascadeDir=context.getDir("cascade",Context.MODE_PRIVATE);
            //Create new cascade file in the same folder
            File mCascadeFile=new File(cascadeDir,"haarcascade_frontalface_alt");
            //define output stream to save haarcascade_frontalface_alt in mCascadeFile
            FileOutputStream outputStream = new FileOutputStream(mCascadeFile);
            //Create empty byte buffer to store bytes
            byte[] buffer = new byte[4096];
            int byteread;
            //read byte in loop
            while ((byteread=inputStream.read(buffer))!=-1){
                outputStream.write(buffer,0,byteread);
            }
              //when reading file complete//
               inputStream.close();
               outputStream.close();

               //load cascade classifier
            cascadeClassifier=new CascadeClassifier(mCascadeFile.getAbsolutePath());
            //if cascade classifier loaded successfully
            Log.d("Face_recognition","Classifier loaded");


        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    //calling this function in cameraFrame in CameraActivity2
    public Mat recognizeImage(Mat mat_image){
        //rotate mat_image by 90 degree
        Mat a =mat_image.t();
        Core.flip(a,mat_image,1);
        a.release();
        //convert mat_image to grayscale
        Mat grayscaleimage=new Mat();
        //                input        output           type if convert
        Imgproc.cvtColor(mat_image,grayscaleimage,Imgproc.COLOR_RGB2GRAY);
        //define hight and width
        height=grayscaleimage.height();
        width=grayscaleimage.width();
        //define mini height and width of face in frame
        int absoluteFaceSize=(int)(height*0.1);
        //Store all faces
        MatOfRect faces=new MatOfRect();
        //Check if cascadeclassifier is loaded or not
        if(cascadeClassifier !=null) {
            //detect face in frame
            //                                  input         output   scale of faces
            cascadeClassifier.detectMultiScale(grayscaleimage,faces,1.1,2,2,
                    new Size(absoluteFaceSize,absoluteFaceSize),new Size());
            //                   Minimum size os face
        }
        //convert faces to array
        Rect[] faceArray=faces.toArray();
        //loop through each faces
        for (int i=0 ; i<faceArray.length;i++){
            //draw reactangle around faces
            //                           Starting point      end point                 R  G  B  Alpha
            Imgproc.rectangle(mat_image,faceArray[i].tl(),faceArray[i].br(),new Scalar(0,255,0,255),2);
            //                input-output
            //                 starting x coordiinate         starting y coordinate
            Rect roi=new Rect((int)faceArray[i].tl().x , (int)faceArray[i].tl().y,
                    ((int)faceArray[i].br().x)-((int)faceArray[i].tl().x),
                    ((int)faceArray[i].br().y)-((int)faceArray[i].tl().y));
            //roi is used to crop faces from images
            Mat cropped_rgb=new Mat(mat_image,roi);
            //convert cropped_rgb to bitmap 
            Bitmap bitmap=null;
            bitmap=Bitmap.createBitmap(cropped_rgb.cols(),cropped_rgb.rows(),Bitmap.Config.ARGB_8888);
            Utils.matToBitmap(cropped_rgb,bitmap);
            //scale bitmap to model input size 96
            Bitmap scaledBitmap=Bitmap.createScaledBitmap(bitmap,INPUT_SIZE,INPUT_SIZE,false);
            //convert bitmap to bytebuffer
            ByteBuffer byteBuffer=convertBitmapToByteBuffer(scaledBitmap);
            //create output
            float [][] face_value=new float[1][1];
            interpreter.run(byteBuffer,face_value);
            //showing face_value
            Log.d("face_recognition","the output is :    "+ Array.get(Array.get(face_value,0),0));

            //read face_value
            float read_face=(float) Array.get(Array.get(face_value,0),0);
            String face_name=get_face_name(read_face);
            //put text on frame

            Imgproc.putText(mat_image,""+face_name,
                    new Point((int)faceArray[i].tl().x+10,(int)faceArray[i].tl().y+20),
                    1,1.5,new Scalar(255,255,255,150),2);

        }

        //Rotate back by -90 before returning
        Mat b= mat_image.t();
        Core.flip(b,mat_image,0);
        b.release();

        return mat_image;
    }

    //Getting face name
   private String get_face_name(float read_face) {


        if (read_face>=0 & read_face<0.5){
            Val="Courteney Cox";
        }
        else if (read_face>=0.5 & read_face<1.5){
            Val="Arnold Schwarzenegger";
        }
        else if (read_face>=1.5 & read_face<2.5){
            Val="bhuvan_bam";
        }
        else if (read_face>=2.5 & read_face<3.5){
            Val="hardik_pandya";
        }
        else if (read_face>=3.5 & read_face<4.5){
            Val="David_Schwimmer";
        }
        else if (read_face>=4.5 & read_face<5.5){
            Val="Matt_LeBlanc";
        }
        else if (read_face>=5.5 & read_face<6.5){
            Val="Simon_Helberg";
        }
        else if (read_face>=6.5 & read_face<7.5){
            Val="scarlett_johansson";
        }
        else if (read_face>=7.5 & read_face<8.5){
            Val="Pankaj_Tripathi";
        }
        else if (read_face>=8.5 & read_face<9.5){
            Val="Matthew_Perry";
        }
        else if (read_face>=9.5 & read_face<10.5){
            Val="sylvester_stallone";
        }
        else if (read_face>=10.5 & read_face<11.5){
            Val="messi";
        }
        else if (read_face>=11.5 & read_face<12.5){
            Val="Jim_Parsons";
        }
        else if (read_face>=12.5 & read_face<13.5){
            Val="random_person";
        }
        else if (read_face>=13.5 & read_face<14.5){
            Val="Lisa_Kudrow";
        }
        else if (read_face>=14.5 & read_face<15.5){
            Val="mohamed_ali";
        }
        else if (read_face>=15.5 & read_face<16.5){
            Val="brad_pitt";
        }
        else if (read_face>=16.5 & read_face<17.5){
            Val="ronaldo";
        }
        else if (read_face>=17.5 & read_face<18.5){
            Val="virat_kohli";
        }
        else if (read_face>=18.5 & read_face<19.5){
            Val="angelina_jolie";
        }
        else if (read_face>=19.5 & read_face<20.5){
            Val="Kunal_Nayya";
        }
        else if (read_face>=20.5 & read_face<21.5){
            Val="manoj_bajpayee";
        }
        else if (read_face>=21.5 & read_face<22.5){
            Val="Sachin_Tendulka";
        }
        else if (read_face>=22.5 & read_face<23.5){
            Val="Jennifer_Aniston";
        }
        else if (read_face>=23.5 & read_face<24.5){
            Val="dhoni";
        }
        else if (read_face>=24.5 & read_face<25.5){
            Val="pewdiepie";
        }
        else if (read_face>=25.5 & read_face<26.5){
            Val="aishwarya_rai";
        }
        else if (read_face>=26.5 & read_face<27.5){
            Val="Johnny_Galeck";
        }
        else if (read_face>=28.5 & read_face<29.5){
            Val="ROHIT_SHARMA";
        }
        else {
            Val="suresh_raina";
        }
        return Val;



    }

    private ByteBuffer convertBitmapToByteBuffer(Bitmap scaledBitmap) {

        //define ByteBuffer
        ByteBuffer byteBuffer;
        //define input size
        int input_size=INPUT_SIZE;
        //multiply by 4 if input of model is float
        //multiply by 3 if input is RGB
        // if input is Gray 3->1
        byteBuffer=ByteBuffer.allocateDirect(4*1*input_size*input_size*3);
        byteBuffer.order(ByteOrder.nativeOrder());
        int[] intValues=new int [input_size*input_size];
        scaledBitmap.getPixels(intValues,0,scaledBitmap.getWidth(),0,0,scaledBitmap.getWidth()
        , scaledBitmap.getHeight());

        int pixels=0;
        //loop through each pixel
        for (int i=0;i<input_size;++i){
            for (int j=0;j<input_size;++j){
                //each pixel value
                final int val=intValues[pixels++];
                //put pixel value in bytebuffer & scaling pixels from 0-255 to 0-1
                byteBuffer.putFloat((((val>>16)&0xFF))/255.0f);
                byteBuffer.putFloat((((val>>8)&0xFF))/255.0f);
                byteBuffer.putFloat(((val&0xFF))/255.0f);
                //placing RGB to MSB to LSB

            }
        }
        return byteBuffer;

    }


    //load model function
    private MappedByteBuffer loadmodel(AssetManager assetManager, String modelpath) throws IOException {

        //give description of model path
        AssetFileDescriptor assetFileDescriptor=assetManager.openFd(modelpath);
        //create inputstream to read model path
        FileInputStream inputStream =new FileInputStream(assetFileDescriptor.getFileDescriptor());
        FileChannel fileChannel =inputStream.getChannel();
        long startOffset =assetFileDescriptor.getStartOffset();
        long declaredlength =assetFileDescriptor.getDeclaredLength();

        return fileChannel.map(FileChannel.MapMode.READ_ONLY,startOffset,declaredlength);

    }


}
