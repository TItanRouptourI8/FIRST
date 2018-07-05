package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import java.util.ArrayList;


@TeleOp(name="Holonomic drive by F", group="WR")
public class OpModeChinebyF extends LinearOpMode {
    private HardwareInit hardwareInit;
    private ArrayList<DcMotor> motors;
    private MovesHandler movesHandler;
    private ButtonsHandler buttonsHandler;


    @Override
    public void runOpMode() {
        hardwareInit = new HardwareInit(hardwareMap);
        motors = hardwareInit.getMotors();
        waitForStart();
        movesHandler = new MovesHandler(hardwareInit,gamepad1, telemetry);
        buttonsHandler = new ButtonsHandler(hardwareInit,gamepad1,telemetry);

        while (opModeIsActive())
        {
//            motors.get(AVD).setPower(0.2);
//            motors.get(AVG).setPower(0.2);
//            motors.get(ARD).setPower(0.2);
//            motors.get(ARG).setPower(0.2);
//            motors.get(MOISSONEUSE).setPower(0.4);




            telemetry.update();
        }
        movesHandler.kill();
        buttonsHandler.kill();
    }
}
