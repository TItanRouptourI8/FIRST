package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;


@TeleOp(name="CELA FONCTIONNNE", group="WR")
public class OpMOde_2018 extends LinearOpMode {

    DcMotor MOTOR;


    @Override
    public void runOpMode() throws InterruptedException {
        MOTOR = hardwareMap.dcMotor.get("AVG");
        waitForStart();
        while (opModeIsActive())
        {
            MOTOR.setPower(0.5);
        }
    }
}
