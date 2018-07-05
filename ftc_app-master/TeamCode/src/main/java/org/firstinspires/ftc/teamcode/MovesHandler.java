package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.util.Range;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.ArrayList;

import static java.lang.Math.PI;
import static java.lang.Math.abs;
import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.tan;
import static java.lang.Thread.sleep;

public class MovesHandler implements Runnable {

    private HardwareInit robot;
    private Gamepad gamepad1;
    Thread thread;
    private float trigger =0;
    private float power = 0;

    private double L1x, L1y, R1x, R1y;
    private double alpha;
    private int minJoy = -3, maxJoy = 3;
    private Telemetry telem;
    private ArrayList<DcMotor> motors;

    MovesHandler(HardwareInit robot, Gamepad gamepad1, Telemetry telem) {
        this.robot = robot;
        this.telem = telem;
        this.gamepad1 = gamepad1;
        getValues();
        this.motors = this.robot.getMotors();


    }

    private void getValues() {
        this.R1x = gamepad1.right_stick_x;
        this.L1y = -gamepad1.left_stick_y;
        this.L1x = gamepad1.left_stick_x;
        this.R1y = gamepad1.right_stick_y;
        this.trigger = gamepad1.left_trigger;
        power = Math.max(0.7f,trigger);
    }



    @Override
    public void run() {

        double avg,avd,arg,ard,alpha;

            getValues();

            //region Works with angle
            if (!(L1x == 0 && L1y == 0))
            {
                alpha = atan2(this.L1y, this.L1x);

                if ((alpha < -3 * PI / 4) || (alpha > 3 * PI / 4)) {
                    this.L1x = -1;
                    this.L1y = sin(alpha);
                }
                if ((alpha <= 3 * PI / 4) && (alpha > PI / 4)) {
                    this.L1y = 1;
                    this.L1x = cos(alpha);
                }
                if ((alpha <= PI / 4) && (alpha > -PI / 4)) {
                    this.L1x = 1;
                    this.L1y = sin(alpha);
                }
                if ((alpha <= -PI / 4) && (alpha >= -3 * PI / 4)) {
                    this.L1y = -1;
                    this.L1x = cos(alpha);
                }
                if (this.R1x < 0) {
                    this.R1x = -1;
                }
                if (this.R1x > 0) {
                    this.R1x = 1;
                }
            }
            else
            {
                this.motors.get(0).setPower(0);
                this.motors.get(1).setPower(0);
                this.motors.get(2).setPower(0);
                this.motors.get(3).setPower(0);
            }
            //endregion

            //region Le Truc Cool
                avg = -this.L1y - this.L1x - this.R1x;
                avd = this.L1y - this.L1x - this.R1x;
                ard = this.L1y + this.L1x - this.R1x;
                arg = -this.L1y + this.L1x - this.R1x;
            //endregion

            //region Telemetry


            telem.addData("AVG", avg);
            telem.addData("AVD", avd);
            telem.addData("ARD", ard);
            telem.addData("ARG", arg);
            telem.addData("pression", trigger);
            telem.update();
//endregion

            //region Clip And Scale
            avg = Range.clip(avg,-2,2);
            avd = Range.clip(avd,-2,2);
            ard = Range.clip(ard,-2,2);
            arg = Range.clip(arg,-2,2);



            avg = Range.scale(avg,minJoy, maxJoy, -1,1);
            avd = Range.scale(avd,minJoy, maxJoy, -1,1);
            arg = Range.scale(arg,minJoy, maxJoy, -1,1);
            ard = Range.scale(ard,minJoy, maxJoy, -1,1);
            //endregion

            //region Coef And Motors
            avg *= power;
            avd *= power;
            arg *= power;
            ard *= power;
            avg *= power;


            this.motors.get(0).setPower(avd);
            this.motors.get(1).setPower(avg);
            this.motors.get(2).setPower(ard);
            this.motors.get(3).setPower(arg);
            Sleep(10);
            //endregion



        }


private void Sleep(long time)
{
    try
    {
        sleep(time);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
}

}
