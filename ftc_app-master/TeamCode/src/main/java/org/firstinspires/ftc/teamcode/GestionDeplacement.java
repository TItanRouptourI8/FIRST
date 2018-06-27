package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;

public class GestionDeplacement implements Runnable {
    HardwareWeRobotJ robot;
    Gamepad gamepad1;
    protected double R1x, R1y;
    boolean killed;

public GestionDeplacement(HardwareWeRobotJ robot, Gamepad gamepad1){
    Thread thread = new Thread(this);
    this.robot = robot;
    this.gamepad1 = gamepad1;
    this.killed = false;
    initDeplacement();
    thread.start();
}

    public void initDeplacement()
    {
        R1x = gamepad1.right_stick_x;
        R1y = -gamepad1.right_stick_y;
    }

    /////////////////////////////FONCTION MOUVEMENTS ROBOTS////////////////////

    protected void avance(double vitesse){
        robot.AvDMoteur.setPower(vitesse);
        robot.AvGMoteur.setPower(1.1*vitesse);
        robot.ArDMoteur.setPower(vitesse);
        robot.ArGMoteur.setPower(1.1*vitesse);
    }

    protected void tourne(double vitesse){
        double v = 0.9*vitesse;
        robot.AvDMoteur.setPower(-v);
        robot.AvGMoteur.setPower(v);
        robot.ArDMoteur.setPower(-v);
        robot.ArGMoteur.setPower(v);
    }
    protected double convertPowerToCurve(double input){
        // return Range.clip(0.7*Math.pow(input, 3.0) + 0.4*input, -1.0, 1.0);
        return 0.3*0.5*Math.pow(input,3.0)+0.3*0.5*Math.pow(input,5.0)+input/Math.abs(input)*0.2;
    }
    @Override
    public void run(){
        while (!killed){
            initDeplacement();
            if ((R1y > Math.abs(R1x)) | (-R1y > Math.abs(R1x))) {
                avance(convertPowerToCurve(R1y));
            } else {
                tourne(convertPowerToCurve(R1x));
            }
        }
    }
    void kill() {
        killed = true;
    }
}