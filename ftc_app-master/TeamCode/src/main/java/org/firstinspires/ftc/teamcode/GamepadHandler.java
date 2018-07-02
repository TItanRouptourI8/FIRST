package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

import java.util.ArrayList;

import static java.lang.Math.abs;


public class GamepadHandler implements Runnable {

    private HardwareInit robot;
    private Gamepad gamepad1;
    private boolean killed = false;
    private double L1x, L1y, R1x, R1y;
    private double alpha;
    private ArrayList<DcMotor> motors;

    GamepadHandler(HardwareInit robot, Gamepad gamepad1) {
        this.robot = robot;
        this.gamepad1 = gamepad1;
        Thread thread = new Thread(this);
        getValues();
        this.motors = this.robot.getMotors();
        thread.start();

    }

    private void getValues() {
        this.R1x = gamepad1.right_stick_x;
        this.R1y = -gamepad1.right_stick_y;
        this.L1y = -gamepad1.left_stick_y;
        this.L1x = gamepad1.left_stick_x;
    }


    @Override
    public void run() {
        double avg,avd,arg,ard,puissance;
        puissance = -0.5;
        while (!killed) {
            getValues();
            if (abs(this.L1x) < 0.2) {
                if (abs(this.L1y) < 0.2){   // Joystick au repos
                    avg = 0;
                    avd = 0;
                    arg = 0;
                    ard = 0;
                }
                else {
                    if (this.L1y > 0) {     // En avant
                        avg = puissance;
                        arg = puissance;
                        avd = -puissance;
                        ard = -puissance;
                    } else {                // En arrière
                        avg = -puissance;
                        arg = -puissance;
                        avd = puissance;
                        ard = puissance;
                    }
                }
            }
            else {
                if (abs(this.L1y) < 0.2) {
                    if (this.L1x > 0){      // A droite
                        avd = puissance;
                        avg = puissance;
                        ard = -puissance;
                        arg = -puissance;
                    }
                    else{                   // A gauche
                        avd = -puissance;
                        avg = -puissance;
                        ard = puissance;
                        arg = puissance;
                    }
                }
                else {
                    if (this.L1x > 0) {
                        if (this.L1y > 0) {         // Diag avant droit
                            avg = 0.8 * puissance;
                            avd = -0.2 * puissance;
                            arg = 0.2 * puissance;
                            ard = 0.8 * puissance;
                        } else {                    // Diag arrière droit
                            avg = -0.2 * puissance;
                            avd = 0.8 * puissance;
                            arg = -0.8 * puissance;
                            ard = 0.2 * puissance;
                        }
                    }
                    else {
                        if (this.L1y>0){            // Diag avant gauche
                            avg = 0.2 * puissance;
                            avd = -0.8 * puissance;
                            arg = 0.8 * puissance;
                            ard = -0.2 * puissance;
                        }
                        else{                       // Diag arrière gauche
                            avg = -0.8 * puissance;
                            avd = 0.2 * puissance;
                            arg = -0.2 * puissance;
                            ard = 0.8 * puissance;
                        }
                    }

                }
            }
            this.motors.get(0).setPower(avd);
            this.motors.get(1).setPower(avg);
            this.motors.get(2).setPower(ard);
            this.motors.get(3).setPower(arg);
        }
    }

    public void kill(){
        this.killed = true;
    }
}
