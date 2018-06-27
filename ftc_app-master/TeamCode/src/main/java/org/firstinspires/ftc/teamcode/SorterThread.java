package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.lynx.LynxI2cColorRangeSensor;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

class SorterThread implements Runnable{
    private DcMotor       shuffleMotor;
    private LynxI2cColorRangeSensor centerColor;
    boolean running = false;
    private boolean killed = false;
    private boolean tour = true;

    public LynxI2cColorRangeSensor getCenterColor() {
        return centerColor;
    }

    SorterThread(DcMotor tri, LynxI2cColorRangeSensor distcol){
        Thread thread = new Thread(this);
        shuffleMotor = tri;
        centerColor  = distcol;


        thread.start();
    }

    public void run(){
       // this.shuffleMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.shuffleMotor.setTargetPosition(WeRobotJ_mainV1_Linear.rtPosCible);
        this.shuffleMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.shuffleMotor.setPower(1);
        running = true;
        while (!killed) {
            /*while (running) {*/
                switch (WeRobotJ_mainV1_Linear.modeTri) {
                    case DROITE:
                        if (centerColor.getDistance(DistanceUnit.MM) < 60) {this.triDC(1);}
                        break;
                    case GAUCHE:
                        if (centerColor.getDistance(DistanceUnit.MM) < 60) {this.triDC(-1);}
                        break;
                    case UNI:
                        if (centerColor.getDistance(DistanceUnit.MM) < 60){
                            if (tour){
                                WeRobotJ_mainV1_Linear.rtPosCible -= 96;
                            }
                            else {
                                WeRobotJ_mainV1_Linear.rtPosCible += 96;

                            }
                            tour = !tour;
                            this.shuffleMotor.setTargetPosition(WeRobotJ_mainV1_Linear.rtPosCible);
                            while (this.shuffleMotor.isBusy()) {
                                if (!WeRobotJ_mainV1_Linear.opModActif) {
                                    break;
                                }
                            }
                        }
                        break;
                    case MANUEL:
                        break;
                }
            /*}*/

                sleep(10);
        }

            stopMotors();
            sleep(10);
    }


    void kill(){
        running = false;
        killed = true;

        stopMotors();
        WeRobotJ_mainV1_Linear.telemetryProxy.addLine("Sorter thread has been killed!");
        /*FirstOpMode.telemetryProxy.update();*/
    }

    private void triDC(int sens) {
        /*if (this.centerColor.blue() < this.centerColor.red()){this.rtPosCible -= 96;}
        else {this.rtPosCible += 96;}*/
        if (this.centerColor.blue()<this.centerColor.red()) {
            WeRobotJ_mainV1_Linear.rtPosCible -= 96*sens;
        } else {
            WeRobotJ_mainV1_Linear.rtPosCible += 96*sens;
        }
        this.shuffleMotor.setTargetPosition(WeRobotJ_mainV1_Linear.rtPosCible);
        while (this.shuffleMotor.isBusy()) {
            if (!WeRobotJ_mainV1_Linear.opModActif) {
                WeRobotJ_mainV1_Linear.telemetryProxy.addLine("Stucked in isBusy");
                WeRobotJ_mainV1_Linear.telemetryProxy.update();
                break;
            }
        }
    }




    private void sleep(int t){
        try {
            Thread.sleep(t);
        } catch (InterruptedException e) {
           WeRobotJ_mainV1_Linear.telemetryProxy.addData("Interrupted Exception occured", "Line %d", e.getStackTrace()[0].getLineNumber());
            WeRobotJ_mainV1_Linear.telemetryProxy.update();

        }
    }

    private void stopMotors(){
        //shuffleMotor.setPower(0);
    }

}