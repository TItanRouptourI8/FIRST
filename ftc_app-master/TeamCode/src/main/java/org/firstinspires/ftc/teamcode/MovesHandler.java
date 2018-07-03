package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.util.Range;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

import java.util.ArrayList;

import static java.lang.Math.abs;

public class MovesHandler implements Runnable {

    private HardwareInit robot;
    private Gamepad gamepad1;
    private boolean killed = false;
    private double L1x, L1y, R1x, R1y;
    private double alpha;
    private ArrayList<DcMotor> motors;

    MovesHandler(HardwareInit robot, Gamepad gamepad1) {
        this.robot = robot;
        this.gamepad1 = gamepad1;
        Thread thread = new Thread(this);
        getValues();
        this.motors = this.robot.getMotors();
        thread.start();

    }

    private void getValues() {
        this.R1x = gamepad1.right_stick_x;
        this.L1y = -gamepad1.left_stick_y;
        this.L1x = gamepad1.left_stick_x;
    }

    public void kill(){
        this.motors.get(0).setPower(0);
        this.motors.get(1).setPower(0);
        this.motors.get(2).setPower(0);
        this.motors.get(3).setPower(0);
        this.killed = true;
    }


    @Override
    public void run() {
        double avg,avd,arg,ard;
        while (!killed){
            getValues();
            avg = -this.L1y - this.L1x - this.R1x;
            avd = this.L1y - this.L1x - this.R1x;
            ard = this.L1y + this.L1x - this.R1x;
            arg = -this.L1y + this.L1x - this.R1x;

            avg = Range.clip(avg,-1,1);
            avd = Range.clip(avd,-1,1);
            ard = Range.clip(ard,-1,1);
            arg = Range.clip(arg,-1,1);

            this.motors.get(0).setPower(avd);
            this.motors.get(1).setPower(avg);
            this.motors.get(2).setPower(ard);
            this.motors.get(3).setPower(arg);
        }
    }
}
