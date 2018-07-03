package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import java.util.ArrayList;

import static org.firstinspires.ftc.teamcode.HardwareInit.ARD;
import static org.firstinspires.ftc.teamcode.HardwareInit.ARG;
import static org.firstinspires.ftc.teamcode.HardwareInit.AVD;
import static org.firstinspires.ftc.teamcode.HardwareInit.AVG;
import static org.firstinspires.ftc.teamcode.HardwareInit.MOISSONEUSE;


@TeleOp(name="DEBUG", group="WR")
@Disabled
public class OpMOde_2018 extends LinearOpMode {
    HardwareInit hardwareInit;
    ArrayList<DcMotor> motors;
    GamepadHandler gamepadHandler;


    @Override
    public void runOpMode() throws InterruptedException {
        hardwareInit = new HardwareInit(hardwareMap);
        motors = hardwareInit.getMotors();
        waitForStart();
        gamepadHandler = new GamepadHandler(hardwareInit,gamepad1);

        while (opModeIsActive())
        {
//            motors.get(AVD).setPower(0.2);
//            motors.get(AVG).setPower(0.2);
//            motors.get(ARD).setPower(0.2);
//            motors.get(ARG).setPower(0.2);
//            motors.get(MOISSONEUSE).setPower(0.4);

            float pression = gamepad1.left_trigger;

            telemetry.addData("Pression", pression);
            telemetry.addData("Bouton",gamepad1.getRobocolMsgType());
            telemetry.addLine("BONSOIR");
            telemetry.update();
        }
        gamepadHandler.kill();
    }
}
